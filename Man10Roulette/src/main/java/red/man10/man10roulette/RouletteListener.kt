package red.man10.man10roulette

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.plugin.Plugin
import java.util.*

class RouletteListener : Listener {

    //var plugin: Man10Roulette = Man10Roulette().getPlugin()
    companion object {
        var mainClass: Man10Roulette? = null
    }

    fun RouletteListener(plugin: Man10Roulette) : RouletteListener {
        mainClass = plugin
        //if (this.plugin !is Man10Roulette)
        return RouletteListener()
    }

    var prefix = "§e§l[§8§lMan10§4§lRoulette§e§l]§f§l "

    var cards = arrayOf("§a§l賭けたいマスにドラッグで§b§l1万§e§lベット", "§a§l賭けたいマスにドラッグで§b§l10万§e§lベット", "§a§l賭けたいマスにドラッグで§b§l100万§e§lベット", "§a§l賭けたいマスにドラッグで§b§l1000万§e§lベット")

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val p = e.whoClicked as Player
        Bukkit.broadcastMessage("EVENTCALLED")
        if (e.inventory.name == prefix + "§0§lBet Menu") {
            //ベットメニュー時
            Bukkit.broadcastMessage("BETMENU")
            var CancelEvent: Boolean = true
            if (e.currentItem != null) { //currentアイテムある
                if (e.currentItem.itemMeta != null) { //そのcurrentアイテムにmetaある
                    if (Arrays.asList(*cards).contains(e.currentItem.itemMeta.displayName)) {
                        //currentアイテムの表示名がcards[]
                        CancelEvent = false
                        Bukkit.broadcastMessage("NOEVENTCANCEL")
                        //ならイベントキャンセルしない
                    }
                }
            }
            if (e.cursor != null) { //カーソルにアイテムある
                if (e.cursor.itemMeta != null) { //そのアイテムにメタある
                    if (e.cursor.itemMeta.lore != null) {
                        if (e.cursor.itemMeta.lore != null) {
                            Bukkit.broadcastMessage(e.cursor.itemMeta.lore[0])
                            if (e.cursor.itemMeta.lore[0] == "§e§l通貨カード§a§lをここにドラッグしてベット") {
                                //カーソルアイテムがベットのマス目
                                Bukkit.broadcastMessage("BETCLICKED")
                            }
                        }
                    }
                }
            }

            Bukkit.broadcastMessage(CancelEvent.toString())
            e.isCancelled = CancelEvent
            Bukkit.broadcastMessage("BETMENUEND")

        } else if (e.inventory.name == prefix + "§0§lSpinning") {
            e.isCancelled = true
        } else {
            p.sendMessage("NONSPECIALCLICK")
        }
    }

    @EventHandler
    fun onInventoryMoveItem(e: InventoryMoveItemEvent) {
        Bukkit.broadcastMessage("AAAAAA")
    }

    //インベ開いた
    @EventHandler
    fun onInventoryOpen(e: InventoryOpenEvent) {
        if (e.inventory.name == prefix + "§0§lSpinning") {
            mainClass!!.openingPlayers++
            Bukkit.broadcastMessage(Integer.toString(mainClass!!.openingPlayers))
            Bukkit.broadcastMessage(e.player.name + "OPENED")
        }

    }

    //インベ閉じた
    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.inventory.name == prefix + "§0§lSpinning") {
            mainClass!!.openingPlayers--
            Bukkit.broadcastMessage(Integer.toString(mainClass!!.openingPlayers))
            Bukkit.broadcastMessage(e.player.name + "CLOSED")
//            if (mainClass!!.openingPlayers == 0){
//                Bukkit.getScheduler().cancelTask(mainClass!!.gui.spinLoop);
//            }
        }
    }
}
