package io.github.nexadn.unitedshops.shop;

import java.util.List;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	
	/** Initialize the GUI
	 */
	public static void initGUI()
	{
		UnitedShops.shopconf.setWorkKey("shops");
		UnitedShops.shopconf.parseConfig();
		guiMap = UnitedShops.shopconf.getMenus();
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
	 * @return Is the Inventory a GUI Inventory?
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
		
		return;
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
