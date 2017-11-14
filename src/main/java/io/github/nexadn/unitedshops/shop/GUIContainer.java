package io.github.nexadn.unitedshops.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.config.ConfigShopMain;
import io.github.nexadn.unitedshops.tradeapi.MoneyTrade;
import io.github.nexadn.unitedshops.ui.Pager;

public final class GUIContainer {
	private static Inventory			guiCategories;
	private static List<ShopInventory>	guiMap;
	private static Pager				guiPager;
	private static final int			menuButtons	= Pager.MenuButton.PREV | Pager.MenuButton.NEXT
			| Pager.MenuButton.CLOSE;

	public static void initGUI ()
	{
		UnitedShops.plugin.log(Level.INFO, "Intializing GUI");
		ConfigShopMain conf = new ConfigShopMain();
		UnitedShops.plugin.log(Level.FINE, "Started config parser.");
		conf.parseConfig();
		guiMap = conf.getMenus();
		for (ShopInventory i : guiMap)
		{
			UnitedShops.plugin.log(Level.FINER, "Init " + i.getOrderNumber() + "/" + i.getTitle());
			i.initInventory();
		}

		guiPager = new Pager(guiMap, menuButtons, "Shop - Kategorien", 2);
		
		for (ShopInventory i : guiMap)
			i.setParent(guiPager.getFirstInventory());
	}

	public static ItemStack getBlank ()
	{
		ItemStack i = new ItemStack(Material.THIN_GLASS, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(" ");
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack getItem (Material mat, String display)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ret.getItemMeta().setDisplayName(display);
		return ret;
	}

	public static ItemStack getFunctionalItem (Material mat, String display, String... funcLore)
	{
		ItemStack ret = new ItemStack(mat, 1);
		if (mat != Material.AIR)
		{
			ItemMeta meta = ret.getItemMeta();
			if (display != null)
				meta.setDisplayName(display);
			List<String> lore = new ArrayList<String>();
			for (String s : funcLore)
				lore.add(s);
			meta.setLore(lore);
			ret.setItemMeta(meta);
		}
		return ret;
	}

	public static Inventory getMenuGui ()
	{
		return guiPager.getFirstInventory();
	}

	public static List<ShopInventory> getGuiMap ()
	{
		return guiMap;
	}

	public static boolean isGuiInventory (Inventory inv)
	{
		if (inv.equals(guiCategories)/* || inv.equals(guiBuySell) */ )
		{
			return true;
		}
		for (ShopInventory i : GUIContainer.guiMap)
		{
			if (inv.equals(i.getInventory()))
			{
				return true;
				// break;
			}
			for (Inventory in : i.getGuisBuySell())
			{
				if (inv.equals(in))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static void handleClickEvents (InventoryClickEvent event)
	{
		Inventory inv = event.getInventory();
		for (int i = 0; i < guiMap.size(); i++)
		{
			if (guiMap.get(i).getInventory().equals(inv))
			{
				break;
			} else
			{
				for (Inventory in : guiMap.get(i).getGuisBuySell())
				{
					if (inv.equals(in))
					{
						// Klick Kauf- und Verkaufsansicht
						// TODO
						for (ShopObject o : guiMap.get(i).getShopObjects())
						{
							if (o.getBuySellGui().equals(in))
								handleBuySellGUI(event, o);
						}
					}
				}
			}
		}

		return;
	}

	public static void handleBuySellGUI (InventoryClickEvent event, ShopObject object)
	{
		Player p = (Player) event.getWhoClicked();
		boolean success = false;
		ItemStack is = object.getItem();
		int amount = 1;
		switch (event.getSlot()) {
		case 0: // Kauf 1
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy() * amount);
			break;

		case 1: // Kauf 16
			amount = 16;
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy() * amount);
			break;

		case 2: // Kauf 64
			amount = 64;
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy() * amount);
			break;
			
		case 4: // Zurück
			event.getWhoClicked().openInventory(object.getParent());
			success = true;
			break;

		case 6: // Verkauf 1
			amount = 1;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell() * amount, is);
			break;

		case 7: // Verkauf 16
			amount = 16;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell() * amount, is);
			break;

		case 8: // Verkauf 64
			amount = 64;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell() * amount, is);
			break;

		default:
			success = true;
			break;
		}
		if (!success)
		{
			UnitedShops.plugin.sendMessage(p, "You don't have the resources to do this transaction!");
		}
	}
}

/*
 * Copyright (C) 2015, 2016, 2017 Adrian Schollmeyer
 * 
 * This file is part of UnitedShops.
 * 
 * UnitedShops is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */