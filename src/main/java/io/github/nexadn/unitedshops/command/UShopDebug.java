package io.github.nexadn.unitedshops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

public class UShopDebug implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] sArgv) {
		
		if( commandSender instanceof Player ) {
			// Spieler
			Player player = (Player) commandSender;
			if( player.hasPermission("unitedshops.admin") ) {
				// Permissions gegeben
				/*
				 * Struktur
				 * -----------------
				 * Argument 1: Debugbefehl
				 * Argument 2-n: Debug Argumente
				*/
				if( sArgv[0].equalsIgnoreCase("eco") ) {
					// Vault tests
					Economy eco = null;
					if( sArgv[1].equalsIgnoreCase("take") ) {
						// Geld abziehen
						double val = Double.parseDouble( sArgv[2] );
						eco.withdrawPlayer(player, val);
					} else if( sArgv[1].equalsIgnoreCase("give") ) {
						// Geld geben
						double val = Double.parseDouble( sArgv[2] );
						eco.depositPlayer(player, val);
					} else {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			commandSender.sendMessage("Dieser Befehl kann nur von einem Spieler genutzt werden.");
			return true;
		}
		//return false;
	}
	
}