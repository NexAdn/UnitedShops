/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.nexadn.unitedshops.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.tradeapi.EcoManager;
import io.github.nexadn.unitedshops.tradeapi.MoneyTrade;
import net.milkbowl.vault.economy.Economy;

public class UShopDebug implements CommandExecutor {

	public UShopDebug() {};
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] sArgv) {
		if( commandSender instanceof Player ) {
			// Spieler
			Player player = (Player) commandSender;
			if( player.hasPermission("unitedshops.admin") && sArgv.length >= 1 ) {
				// Permissions gegeben
				/*
				 * Struktur
				 * -----------------
				 * Argument 1: Debugbefehl
				 * Argument 2-n: Debug Argumente
				*/
				if( sArgv[0].equalsIgnoreCase("eco") ) {
					// Vault tests
					Economy eco = EcoManager.getEconomy();
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
				if( sArgv[0].equalsIgnoreCase("testtrade") ) {
					if( sArgv[1].equalsIgnoreCase("itemformoney") )
					{
						double val = Double.parseDouble( sArgv[2] );
						ItemStack stack = new ItemStack(Material.COBBLESTONE, 64);
						MoneyTrade.tradeItemForMoney(player, stack, val);
					} else {
						double val = Double.parseDouble( sArgv[2] );
						ItemStack stack = new ItemStack(Material.COBBLESTONE, 32);
						if(!MoneyTrade.tradeMoneyForItem(player, val, stack))
						{
							commandSender.sendMessage("Fehler");
						}
					}
				}
				return true;
			} else {
				// Permissions nachricht
				commandSender.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung " + ChatColor.BLUE +"unitedshops.admin" + ChatColor.RED + "!");
				return true;
			}
		} else {
			commandSender.sendMessage("Dieser Befehl kann nur von einem Spieler genutzt werden.");
			return true;
		}
		//return false;
	}
	
}
