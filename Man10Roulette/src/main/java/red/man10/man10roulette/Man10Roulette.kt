package red.man10.man10roulette

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import org.bukkit.event.EventHandler

import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

import org.apache.commons.lang.math.NumberUtils.isNumber
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.plugin.Plugin
import java.util.*

class Man10Roulette : JavaPlugin(){

    var prefix = "§e§l[§8§lMan10§4§lRoulette§e§l]§f§l "

    //他クラスと繋げる
    var gui = RouletteGUI().RouletteGUI(this)
    var listener = RouletteListener().RouletteListener(this)


//    companion object {
        var openingPlayers = 0
//    }

    override fun onEnable() {
        //Plugin startup logic
        logger.info("Loaded Man10Roulette")
        Bukkit.getPluginManager().registerEvents(listener, this)
        openingPlayers = 0

    }

    override fun onDisable() {
        // Plugin shutdown logic
        openingPlayers = 0
        //TODO: プレイヤーのインベ閉じ動作入れる
    }

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<String>?): Boolean {
        if (args!!.size == 0) {
            gui.showBetMenu(sender)
        } else if (args.size == 1) {
            when (args[0]) {
                else -> showHelp(sender)
            }
        } else {
            showHelp(sender)
        }

        return false

    }

    //ヘルプ表示
    public fun showHelp(sender: CommandSender?) {
        sender!!.sendMessage("§e§l----==[§8§lMan10§4§lRoulette§e§l]==----")
        sender.sendMessage("§d§lCommands:")
        sender.sendMessage("§l/mr §d§lゲーム画面を表示")
        sender.sendMessage("§l/mr help §d§lこのヘルプをだす")
        sender.sendMessage("§c§lVer.0.4")
        sender.sendMessage("§c§lCreated by Shupro")
        sender.sendMessage("§e§l------------------")
    }



}
