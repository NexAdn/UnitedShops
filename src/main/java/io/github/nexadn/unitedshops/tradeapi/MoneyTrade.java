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
package io.github.nexadn.unitedshops.tradeapi;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class MoneyTrade {
	
	private static Economy eco = EcoManager.getEconomy();
	
	public static boolean tradeItemForMoney( Player player, ItemStack offer, double want )
	{
		EconomyResponse eReturn = null;
		double bal = eco.getBalance(player);
		if(want > bal) {
			// Weniger Geld als verlangt
			return false;
		} else {
			// Genug Geld, Trade ausführen
			eReturn = eco.withdrawPlayer(player, want);
			if( eReturn.transactionSuccess() ) {
				player.getInventory().addItem(offer);
				UnitedShops.plugin.sendMessage(player, "Trade: " + offer.toString() + " -> $" + want);
				return true;
			}
		}
		return false;
	}
	
	public static boolean tradeMoneyForItem( Player player, double offer, ItemStack want )
	{
		UnitedShops.plugin.log(Level.INFO, Integer.toString(want.getAmount()));
		EconomyResponse eReturn = null;
		Inventory playerinv = player.getInventory();
		// TODO: if-Abfrage beheben (true nur, wenn ItemStack exakt so groß wie gefordert anstatt Summe aller Stacks)
		if ( playerinv.containsAtLeast(want, want.getAmount())) {
			// Spieler hat das Zeugs
			eReturn = eco.depositPlayer(player, offer);
			if( eReturn.transactionSuccess() ) {
				if (!removeItems(playerinv, want))
				{
					eco.withdrawPlayer(player, offer);
					UnitedShops.plugin.sendMessage(player, ChatColor.RED + "Transaction failed");
					return false;
				}
				UnitedShops.plugin.sendMessage(player, "Trade: $" + offer + " -> " + want.toString());
				return true;
			}
		} else 
		{
			return false;
		}
		return false;
	}
	
	public static boolean removeItems( Inventory inventory, ItemStack items )
	{
		int remaining = items.getAmount();
		UnitedShops.plugin.log(Level.INFO, Integer.toString(remaining));
		for (int i=0; i<inventory.getSize(); ++i)
		{
			if (inventory.getItem(i) != null && inventory.getItem(i).getType().equals(items.getType()))
			{
				ItemStack is = inventory.getItem(i);
				if (is.getAmount() > remaining)
				{
					is.setAmount(is.getAmount() - remaining);
					inventory.setItem(i, is);
					return true;
				} else
				{
					remaining -= is.getAmount();
					inventory.setItem(i,null);
					if (is.getAmount() == 0)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
