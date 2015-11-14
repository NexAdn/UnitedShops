package io.github.nexadn.unitedshops.shop;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIContainer {
	private static Inventory guiCategories;					// Container for category menu
	private static HashMap<Integer,ShopInventory> guiMap;	// Container for item listing inventories
	
	// Init the GUI
	public static void initGUI()
	{
		// Inventar initalisieren:
		// 3 Reihen á 9 Slots -> 27 Slots
		// Leere Felder mit Glasscheibe initialisieren
		guiCategories = Bukkit.createInventory(null, 27, "Shop-dhf01");
		for( int i=0; i<27; i++) {
			guiCategories.setItem(i, getBlank());
		}
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
		if(inv.equals(guiCategories) || inv.equals(guiTradeMobs) || inv.equals(guiTradeBlocks) || inv.equals(guiTradeFood) || inv.equals(guiTradeNether) || inv.equals(guiTradeOres) || inv.equals(guiTradeRedstone) || inv.equals(guiTradeSpecial)) {
			return true;
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
