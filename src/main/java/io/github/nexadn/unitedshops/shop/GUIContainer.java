package io.github.nexadn.unitedshops.shop;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;

public class GUIContainer {
	private static Inventory guiCategories;					// Container for category menu
	private static List<ShopInventory> guiMap;				// Container for item listing inventories
	private static Inventory guiBuySell;					// Container for buy/sell GUI
	
	// Init the GUI
	public static void initGUI()
	{
		// Inventar initalisieren:
		// 3 Reihen á 9 Slots -> 27 Slots
		// Leere Felder mit Glasscheibe initialisieren
		UnitedShops.shopconf.setWorkKey("shops");
		UnitedShops.shopconf.parseConfig();
		guiMap = UnitedShops.shopconf.getMenus();
		
		guiCategories = Bukkit.createInventory(null, 27, "Shop-dhf01");
		for( int i=0; i<27; i++) {
			guiCategories.setItem(i, getBlank());
		}
		
		guiBuySell = Bukkit.createInventory(null, 9, "Shop-dhf02");
		// TODO:
		/*
		 * Konfiguration lesen, parsen und Inventare abholen.
		 */
	}
	
	// Item getters
	public static ItemStack getBlank()
	{
		return new ItemStack(Material.THIN_GLASS,1);
	}
	
	public static ItemStack getItem(Material mat, String display)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ret.getItemMeta().setDisplayName(display);
		return ret;
	}
	
	public static ItemStack getFunctionalItem(Material mat, String display, String funcLore)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ret.getItemMeta().setDisplayName(display);
		List<String> lore = null;
		lore.add(funcLore);
		ret.getItemMeta().setLore(lore);
		return ret;
	}
	
	// Public getters
	public static Inventory getMenuGui()
	{
		return guiCategories;
	}
	
	// is-Funcs
	public static boolean isGuiInventory(Inventory inv)
	{
		if(inv.equals(guiCategories) ) {
			return true;
		}
		for( ShopInventory i : GUIContainer.guiMap) {
			if(inv.equals(i)) {
				return true;
				//break;
			}
		}
		return false;
	}
	
	// ClickEvents handlen
	public static void handleClickEvents(InventoryClickEvent event)
	{
		Inventory inv = event.getInventory();
		// Push the event to its specific handler
		if( inv.equals(guiCategories) ) {
			handleEventsGuiCategories(event);
		}
		return;
	}
	
	// Inventory specific handlers
	private static void handleEventsGuiCategories(InventoryClickEvent even)
	{
		//
	}
}