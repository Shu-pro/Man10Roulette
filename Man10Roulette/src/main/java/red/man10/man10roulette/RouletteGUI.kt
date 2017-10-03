package red.man10.man10roulette

import org.apache.commons.lang.math.NumberUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import org.bukkit.Bukkit.getScheduler
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler



class RouletteGUI {

    companion object {
        var mainClass: Man10Roulette? = null
    }

    fun RouletteGUI(plugin: Man10Roulette) : RouletteGUI{
        mainClass = plugin
        return RouletteGUI()
    }

    var prefix = "§e§l[§8§lMan10§4§lRoulette§e§l]§f§l "

    var places = arrayOf(1, 2, 3, 10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39, 46, 47, 48)
    var ladderp = arrayOf(0, 9, 18, 27, 36, 45, 4, 13, 22, 31, 40, 49, 7, 16, 25, 34, 43, 52)
    var oddp = arrayOf(6, 15, 24, 33, 42, 51)
    var evenp = arrayOf(7, 16, 25, 34, 43, 52)

    var rouletteorders = arrayOf("17", "14", "13", "§e§l全員あたり!", "11", "4", "16", "8", "7", "15", "5", "10", "3", "B", "9", "12", "2", "18", "6", "1")
    var rouletteplaces = arrayOf(1, 2, 3, 4, 5, 6, 7, 16, 25, 34, 43, 42, 41, 40, 39, 38, 37, 28, 19, 10)
    var roulettefences = arrayOf(0, 9, 18, 27, 36, 8, 17, 26, 35, 44)

    var reds = arrayOf(1, 3, 5, 7, 11, 12, 14, 16, 18)

    var nolores: List<String> = ArrayList()

    var spinningInv = Bukkit.getServer().createInventory(null, 54, prefix + "§0§lSpinning")

    //ベット画面表示
    fun showBetMenu(sender: CommandSender?) {

        val p = sender as Player?
        val gameMenu = Bukkit.getServer().createInventory(null, 54, prefix + "§0§lBet Menu")

        val lores = ArrayList<String>()
        lores.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        lores.add("")
        lores.add("§bこの数字に§e§l120万§r§bベットされています")

        val oddlore = ArrayList<String>()
//        oddlore.add("§e§l1,3,5,7,9,11,13,15,17")
        oddlore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        oddlore.add("")
        oddlore.add("§bこの数字に§e§l120万§r§bベットされています")

        val evenlore = ArrayList<String>()
//        evenlore.add("§e§l2,4,6,8,10,12,14,16,18")
        evenlore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        evenlore.add("")
        evenlore.add("§bこの数字に§e§l120万§r§bベットされています")

        val redlore = ArrayList<String>()
//        redlore.add("§e§l1,3,5,7,11,12,14,16,18")
        redlore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        redlore.add("")
        redlore.add("§bこの数字に§e§l120万§r§bベットされています")

        val blacklore = ArrayList<String>()
//        blacklore.add("§e§l2,4,6,8,9,10,15,17")
        blacklore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        blacklore.add("")
        blacklore.add("§bこの数字に§e§l120万§r§bベットされています")

        val one6lore = ArrayList<String>()
//        one6lore.add("§e§l1,2,3,4,5,6")
        one6lore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        one6lore.add("")
        one6lore.add("§bこの数字に§e§l120万§r§bベットされています")

        val seven12lore = ArrayList<String>()
//        seven12lore.add("§e§l7,8,9,10,11,12")
        seven12lore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        seven12lore.add("")
        seven12lore.add("§bこの数字に§e§l120万§r§bベットされています")

        val thir18lore = ArrayList<String>()
//        thir18lore.add("§e§l13,14,15,16,17,18")
        thir18lore.add("§e§l通貨カード§a§lをここにドラッグしてベット")
        thir18lore.add("")
        thir18lore.add("§bこの数字に§e§l120万§r§bベットされています")

        for (i in 0..17) {
            val nowPlace = places[i]
            if (Arrays.asList(*reds).contains(i + 1)) {
                //even
                createItem(nowPlace, gameMenu, Material.STAINED_GLASS_PANE, 14.toShort(), 1, "§r§4§l●§f§l " + (i + 1).toString() + "番", lores)
            } else {
                //odd
                createItem(places[i], gameMenu, Material.STAINED_GLASS_PANE, 15.toShort(), 1, "§r§8§l●§f§l " + (i + 1).toString() + "番", lores)
            }
        }

        for (i in 0..(ladderp.size - 1)) {
            createItem(ladderp[i], gameMenu, Material.IRON_FENCE, 0.toShort(), 1, " ", nolores)
        }

        createItem(5, gameMenu, Material.WOOL, 5.toShort(), 1, "§a§l偶数§f§lの数字", oddlore)
        createItem(6, gameMenu, Material.WOOL, 3.toShort(), 1, "§b§l奇数§f§lの数字", evenlore)

        createItem(23, gameMenu, Material.STAINED_GLASS_PANE, 15.toShort(), 1, "§8§l黒§f§lの数字", redlore)
        createItem(24, gameMenu, Material.STAINED_GLASS_PANE, 14.toShort(), 1, "§4§l赤§f§lの数字", blacklore)

        createItem(41, gameMenu, Material.WOOL, 11.toShort(), 1, "§r§l§9§l1 - 6§f§lの数字", one6lore)
        createItem(42, gameMenu, Material.WOOL, 14.toShort(), 1, "§r§l§4§l7 - 12§f§lの数字", seven12lore)
        createItem(50, gameMenu, Material.WOOL, 4.toShort(), 1, "§e§l13 - 18§f§lの数字", thir18lore)

        createItem(8, gameMenu, Material.BLAZE_POWDER, 0.toShort(), 1, "§a§l賭けたいマスにドラッグで§b§l1万§e§lベット", nolores)
        createItem(17, gameMenu, Material.INK_SACK, 10.toShort(), 1, "§a§l賭けたいマスにドラッグで§b§l10万§e§lベット", nolores)
        createItem(26, gameMenu, Material.INK_SACK, 7.toShort(), 1, "§a§l賭けたいマスにドラッグで§b§l100万§e§lベット", nolores)
        createItem(35, gameMenu, Material.INK_SACK, 8.toShort(), 1, "§a§l賭けたいマスにドラッグで§b§l1000万§e§lベット", nolores)



        p!!.openInventory(gameMenu)
    }


    //スピン画面セットアップ&表示
    fun showSpinInv(p: Player) {
        if (mainClass!!.openingPlayers == 0) {
            for (i in 0..19) {
                if (NumberUtils.isNumber(rouletteorders[i])) {
                    val currentNumber = Integer.valueOf(rouletteorders[i])
                    if (Arrays.asList(*reds).contains(currentNumber)) {
                        createItem(rouletteplaces[i], spinningInv, Material.STAINED_GLASS_PANE, 14.toShort(), 1, "§4§l●§r§f§l " + rouletteorders[i] + "番", nolores)
                    } else {
                        createItem(rouletteplaces[i], spinningInv, Material.STAINED_GLASS_PANE, 15.toShort(), 1, "§8§l●§r§f§l " + rouletteorders[i] + "番", nolores)
                    }
                } else {
                    if (rouletteorders[i] == "§e§l全員あたり!") {
                        createItem(rouletteplaces[3], spinningInv, Material.RED_ROSE, 2.toShort(), 1, "§e§l全員あたり!", nolores)
                    } else {
                        createItem(rouletteplaces[13], spinningInv, Material.ROTTEN_FLESH, 0.toShort(), 1, "§9§l全員はずれ..", nolores)
                    }
                }
            }

            for (i in 0..9) {
                createItem(roulettefences[i], spinningInv, Material.IRON_FENCE, 0.toShort(), 1, " ", nolores)
            }
            createItem(13, spinningInv, Material.REDSTONE, 0.toShort(), 1, "§e§l針", nolores)
            p.openInventory(spinningInv)
            val spinLoop = mainClass!!.getServer().getScheduler()
            spinLoop.scheduleSyncRepeatingTask(mainClass, Runnable {
                // Do something
                spinRoulette()
            }, 0L, 20L)
        }
    }

    //インベアイテム設置function
    fun createItem(place: Int?, gui: Inventory, material: Material, itemtype: Short?, amount: Int?, itemName: String, loreList: List<String>) {
        val CIitemStack = ItemStack(material, amount!!, itemtype!!)
        val CIitemMeta = CIitemStack.itemMeta
        CIitemMeta.displayName = itemName
        CIitemMeta.lore = loreList
        CIitemStack.itemMeta = CIitemMeta
        gui.setItem(place!!, CIitemStack)
    }

    //ルーレット回転処理
    fun spinRoulette(){
        val inv = spinningInv
        val firstItem = spinningInv.getItem(1)
        for (i in 0..18) {
            inv.setItem(rouletteplaces[i], inv.getItem(rouletteplaces[i + 1]))
        }
        inv.setItem(10, firstItem)
    }
}
