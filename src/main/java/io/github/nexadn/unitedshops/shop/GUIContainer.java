/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

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
package io.github.nexadn.unitedshops.shop;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.config.ConfigShopMain;
import io.github.nexadn.unitedshops.tradeapi.MoneyTrade;

/** Static container for all GUI Inventories and their handlers
 * @author NexAdn
 */
public class GUIContainer {
	private static Inventory guiCategories;					// Container for category menu
	private static List<ShopInventory> guiMap;				// Container for item listing inventories
	
	/** Initialize the GUI
	 */
	public static void initGUI()
	{
		UnitedShops.plugin.log(Level.INFO, "Intializing GUI");
		ConfigShopMain conf = new ConfigShopMain();
		UnitedShops.plugin.log(Level.FINE, "Started config parser.");
		conf.parseConfig();
		guiMap = conf.getMenus();
		for( ShopInventory i:guiMap ) 
		{
			UnitedShops.plugin.log(Level.FINER, "Init " + i.getOrderNumber() + "/" + i.getTitle());
			i.initInventory();
		}
		
		guiCategories = Bukkit.createInventory(null, guiMap.size() + (9-(guiMap.size()%9)), "Shop - Kategorien");
		
		// Init with glass panes
		for( int i=0; i<guiMap.size()+(9-(guiMap.size()%9)); i++) {
			guiCategories.setItem(i, getBlank());
		}
		// Insert shop icons
		for( int i = 0; i<guiMap.size(); i++ )
		{
			guiCategories.clear(i);
			ItemStack it = guiMap.get(i).getIcon();
			ItemMeta m = it.getItemMeta();
			m.setDisplayName(ChatColor.AQUA + guiMap.get(i).getTitle());
			it.setItemMeta(m);
			guiCategories.setItem(i, it);
		}
	}
	
	/** Create an item (unique item for blank slots)
	 * @return The ItemStack
	 */
	public static ItemStack getBlank()
	{
		ItemStack i = new ItemStack(Material.THIN_GLASS,1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(" ");
		i.setItemMeta(m);
		return i;
	}
	
	/** Create an item with material and display name
	 * @param mat - The Material
	 * @param display - The DisplayName
	 * @return The ItemStack
	 */
	public static ItemStack getItem(Material mat, String display)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ret.getItemMeta().setDisplayName(display);
		return ret;
	}
	
	/** Create a special item with Material, display name and lore
	 * @param mat - The Material
	 * @param display - The DisplayName
	 * @param funcLore - The Lore
	 * @return The ItemStack
	 */
	public static ItemStack getFunctionalItem(Material mat, String display, String funcLore)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ItemMeta meta = ret.getItemMeta();
		meta.setDisplayName(display);
		List<String> lore = new Vector<String>();
		lore.add(funcLore);
		meta.setLore(lore);
		ret.setItemMeta(meta);
		return ret;
	}
	
	/**
	 * @return the Menu GUI
	 */
	public static Inventory getMenuGui()
	{
		return guiCategories;
	}
	
	/** Check, whether the given Inventory is a GUI Inventory
	 * @param inv - The inventory
	 * @return true, if the given inventory equals one or more GUI inventories, false if not.
	 */
	public static boolean isGuiInventory(Inventory inv)
	{
		if(inv.equals(guiCategories)/* || inv.equals(guiBuySell)*/ ) {
			return true;
		}
		for( ShopInventory i : GUIContainer.guiMap) {
			if(inv.equals(i.getInventory())) {
				return true;
				//break;
			}
			for( Inventory in : i.getGuisBuySell()) {
				if( inv.equals(in) )
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/** General handler for InventoryClickEvents in GUI Inventories
	 * @param event - Event, that has been called
	 */
	public static void handleClickEvents(InventoryClickEvent event)
	{
		Inventory inv = event.getInventory();
		
		if( inv.equals(guiCategories) ) {
			// Klick innerhalb Kategorienübersicht
			handleEventsGuiCategories(event);
		}
		
		for( int i=0; i<guiMap.size(); i++ )
		{
			if( guiMap.get(i).getInventory().equals(inv) )
			{
				// Klick innerhalb Shopkategorie
				handleEventsShopGUI( event, i );
				break;
			} else 
			{
				for( Inventory in : guiMap.get(i).getGuisBuySell() )
				{
					if( inv.equals(in) )
					{
						// Klick Kauf- und Verkaufsansicht
						// TODO
						for ( ShopObject o : guiMap.get(i).getShopObjects() )
						{
							if (o.getBuySellGui().equals(in))
								handleBuySellGUI( event, o );
						}
					}
				}
			}
		}
		
		return;
	}
	
	public static void handleBuySellGUI(InventoryClickEvent event, ShopObject object)
	{
		Player p = (Player) event.getWhoClicked();
		boolean success = false;
		ItemStack is = object.getItem();
		int amount = 1;
		switch(event.getSlot())
		{
		case 0: // Kauf 1
			
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy()*amount);
			break;
			
		case 1: // Kauf 10
			amount = 10;
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy()*amount);
			break;
			
		case 2: // Kauf 64
			amount = 64;
			is.setAmount(amount);
			success = MoneyTrade.tradeItemForMoney(p, is, object.getBuy()*amount);
			break;
			
		case 6: // Verkauf 1
			amount = 1;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell()*amount, is);
			break;
			
		case 7: // Verkauf 10
			amount = 10;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell()*amount, is);
			break;
			
		case 8: // Verkauf 64
			amount = 64;
			is.setAmount(amount);
			success = MoneyTrade.tradeMoneyForItem(p, object.getSell()*amount, is);
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
	
	/** Handler for all Shop Menus
	 * @param event - Event, which has been called
	 * @param index - List index of the Shop
	 */
	public static void handleEventsShopGUI( InventoryClickEvent event, int index )
	{
		ItemStack clicked = event.getCurrentItem();
		ShopInventory used = guiMap.get(index);
		for( int i=0; i<used.getGuisBuySell().size(); i++ )
		{
			if( used.getShopObjects().get(i).getItem().getType().equals(clicked.getType()) ) {
				// gewünschtes Shopobjekt
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(used.getShopObjects().get(i).getBuySellGui());
				break;
			}
		}
		event.getWhoClicked().openInventory(used.getGuisBuySell().get(event.getSlot()));
	}
	
	/** Handler for the Categories Inventory
	 * @param event - Event, that has been called
	 */
	private static void handleEventsGuiCategories(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		event.getWhoClicked().closeInventory();
		event.getWhoClicked().openInventory(guiMap.get(slot).getInventory());
	}
}