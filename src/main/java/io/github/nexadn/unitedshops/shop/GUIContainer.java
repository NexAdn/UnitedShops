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
package io.github.nexadn.unitedshops.shop;

import java.util.List;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;

/** Static container for all GUI Inventories and their handlers
 * @author NexAdn
 */
public class GUIContainer {
	private static Inventory guiCategories;					// Container for category menu
	private static List<ShopInventory> guiMap;				// Container for item listing inventories
	@Deprecated
	private static Inventory guiBuySell;					// Container for buy/sell GUI
	private static UnitedShops plugin;						// Plugin class
	
	/** Set the Plugin class
	 * @param plugin - The UnitedShops class
	 */
	public static void setPlugin(UnitedShops plugin)
	{
		GUIContainer.plugin = plugin;
	}
	
	/** Initialize the GUI
	 */
	public static void initGUI()
	{
		if(plugin == null)
		{
			return;
		}
		plugin.getShopConf().setWorkKey("shops");
		plugin.getShopConf().parseConfig();
		guiMap = plugin.getShopConf().getMenus();
		for( ShopInventory i:guiMap ) { i.initInventory(); }
		
		if(guiMap.size()<=27) {
			guiCategories = Bukkit.createInventory(null, 27, "Shop - Kategorien");
		} else {
			int iCount = guiMap.size();
			while(iCount%9 != 0) {
				iCount++;
			}
			guiCategories = Bukkit.createInventory(null, iCount, "Shop - Kategorien");
		}
		for( int i=0; i<27; i++) {
			guiCategories.setItem(i, getBlank());
		}
		if(guiMap.size()<=9) {
			for(int i=9; i<18; i++)
			{
				guiCategories.setItem(i, guiMap.get(i).getIcon());
			}
		} else {
			for(int i=0; i<27; i++)
			{
				guiCategories.setItem(i, guiMap.get(i).getIcon());
			}
		}
		
		//guiBuySell = Bukkit.createInventory(null, 9, "Shop-dhf02");
	}
	
	/** Create an item (unique item for blank slots)
	 * @return The ItemStack
	 */
	public static ItemStack getBlank()
	{
		return new ItemStack(Material.THIN_GLASS,1);
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
		ret.getItemMeta().setDisplayName(display);
		List<String> lore = new Vector<String>();
		lore.add(funcLore);
		ret.getItemMeta().setLore(lore);
		return ret;
	}
	
	/**
	 * @return the Menu GUI
	 */
	public static Inventory getMenuGui()
	{
		return guiCategories;
	}
	
	/** Check, whetzer the given Inventory is a GUI Inventory
	 * @param inv - The inventory
	 * @return true, if the given inventory equals one or more GUI inventories, false if not.
	 */
	public static boolean isGuiInventory(Inventory inv)
	{
		if(inv.getName().equalsIgnoreCase(guiCategories.getName()) || inv.getName().equalsIgnoreCase(guiBuySell.getName()) ) {
			return true;
		}
		for( ShopInventory i : GUIContainer.guiMap) {
			if(inv.getName().equalsIgnoreCase(i.getInventory().getName())) {
				return true;
				//break;
			}
			for( Inventory in: i.getGuisBuySell()) {
				if( inv.getName().equalsIgnoreCase(in.getName()) );
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
		// Push the event to its specific handler
		if( inv.equals(guiCategories) ) {
			handleEventsGuiCategories(event);
		}
		// Ergänzen!
		// ggf. Iteration
		if( /* Inventar Teil der Shopliste */guiMap.contains(inv) ) {
			// index: Index des zu handlenden Inventars //
			for( int i=0; i<guiMap.size(); i++ )
			{
				if( guiMap.get(i).equals(inv) ) {
					// GUI
					handleEventsShopGUI( event, i );
					// So, alles fertig...
					break;
				}
			}
		}
		
		return;
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
				event.getWhoClicked().openInventory(used.getShopObjects().get(i).getBuySellGui());
				break;
			}
		}
	}
	
	/** Handler for the Categories Inventory
	 * @param event - Event, that has been called
	 */
	private static void handleEventsGuiCategories(InventoryClickEvent event)
	{
		ItemStack clicked = event.getCurrentItem();
		for( ShopInventory si:guiMap ) {
			if(si.getIcon().equals(clicked)) {
				// Inventar gefunden
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(si.getInventory());
				break;
			}
		}
	}
	
	
}
