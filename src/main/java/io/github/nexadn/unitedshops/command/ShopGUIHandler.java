package io.github.nexadn.unitedshops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.nexadn.unitedshops.gui.GUIContainer;

public class ShopGUIHandler implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] sArgv) {
		GUIContainer.initGUI();
		if(commandSender instanceof Player) {
			// Nur für Spieler
			Player player = (Player)commandSender;
			if(player.hasPermission("unitedshops.useshop")) {
				player.openInventory(GUIContainer.getMenuGui()); // Menü GUI öffnen
				return true;
			} else {
				return false;
			}
		} else {
			commandSender.sendMessage("Du kannst dies nur als Spieler nutzen!");
			return true;
		}
		// return false;
	}
}
