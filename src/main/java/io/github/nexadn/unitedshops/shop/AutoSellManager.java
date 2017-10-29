package io.github.nexadn.unitedshops.shop;

import org.bukkit.*;
import org.bukkit.inventory.*;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.tradeapi.MoneyTrade;

import org.bukkit.entity.*;

public class AutoSellManager {
	private Inventory inventory;
	private OfflinePlayer player;
	
	public AutoSellManager (OfflinePlayer p)
	{
		this.player = p;
		this.inventory = Bukkit.createInventory(null, 18, "Automatischer Verkauf: " + p.getName());
		this.inventory.clear();
	}
	
	public void startAutoSell ()
	{
		if (this.player.isOnline())
		{
			Player p = this.player.getPlayer();
			p.openInventory(this.inventory);
		}
	}
	
	public void fetchInventory()
	{
		boolean nextitem = false;
		for (ItemStack s : this.inventory.getContents())
		{
			nextitem = false;
			if (s != null)
			{
				for (ShopInventory si : GUIContainer.getGuiMap())
				{
					for (ShopObject so : si.getShopObjects())
					{
						if (so.getItem().getType().equals(s.getType()))
						{
							this.player.getPlayer().getInventory().addItem(new ItemStack(s.getType(), s.getAmount()));
							MoneyTrade.removeItems(this.inventory, s);
							if (MoneyTrade.tradeMoneyForItem(this.player.getPlayer(), so.getSell()*s.getAmount(), s))
							{
								nextitem = true;
								break;
							} else
							{
								UnitedShops.plugin.sendMessage(this.player.getPlayer(), "Failed to sell item " + s.getType().toString());
							}
						}
					}
					if (nextitem)
					{
						break;
					}
				}
			}
		}
	}
	
	public Inventory getInventory()
	{
		return this.inventory;
	}
}

/*  Copyright (C) 2017 Adrian Schollmeyer

This file is part of UnitedShops.

UnitedShops is free software: you can redistribute it and/or modify
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
