package io.github.nexadn.unitedshops.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIContainer {
	private static Inventory guiCategories;
	private static Inventory guiTradeBlocks;	// 1
	private static Inventory guiTradeOres;		// 2
	private static Inventory guiTradeMobs;		// 3
	private static Inventory guiTradeNether;	// 4
	private static Inventory guiTradeFood;		// 5
	//private static Inventory guiTradePotions;	// 6
	private static Inventory guiTradeRedstone;	// 7
	private static Inventory guiTradeSpecial;	// 8
	
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
		// Verwendete Slots überschreiben
		guiCategories.setItem(9, getItem(Material.COBBLESTONE, "Bloecke"));
		guiCategories.setItem(10, getItem(Material.IRON_INGOT, "Erze und Bodenschaetze"));
		guiCategories.setItem(11, getItem(Material.ROTTEN_FLESH, "Mobs"));
		guiCategories.setItem(12, getItem(Material.NETHER_BRICK_ITEM, "Nether Items"));
		guiCategories.setItem(13, getItem(Material.CAKE, "Nahrung"));
	}
	
	// Private getters
	private static ItemStack getBlank()
	{
		return new ItemStack(Material.THIN_GLASS,1);
	}
	
	private static ItemStack getItem(Material mat, String display)
	{
		ItemStack ret = new ItemStack(mat, 1);
		ret.getItemMeta().setDisplayName(display);
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
		
	}
}
