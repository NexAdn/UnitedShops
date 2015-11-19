package io.github.nexadn.unitedshops.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.nexadn.unitedshops.shop.GUIContainer;

public class ShopGUIHandler implements CommandExecutor {

	public ShopGUIHandler() {
		
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] sArgv) {
		if(commandSender instanceof Player) {
			// Nur für Spieler
			Player player = (Player)commandSender;
			if(player.hasPermission("unitedshops.useshop")) {
				player.openInventory(GUIContainer.getMenuGui()); // Menü GUI öffnen
				//
				return true;
			} else {
				commandSender.sendMessage(ChatColor.RED + "Du hast die Permission unitedshops.useshop nicht!");
				return true;
			}
		} else {
			commandSender.sendMessage("Du kannst dies nur als Spieler nutzen!");
			return true;
		}
		// return false;
	}
}
