package me.nexadn.unitedshops.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;

public class GlobalMenuGui implements Listener {
    private UnitedShops plugin;
    private AdminShopGui adminShopGui;
    private UserShopGui userShopGui;

    private Inventory ui;

    public GlobalMenuGui(UnitedShops plugin, ConfigFileHandler adminShopConfig) {
	this.plugin = plugin;
	this.ui = Bukkit.createInventory(null, 9, this.plugin.getL10n().getMessage("globalMenuTitle").str());
	this.adminShopGui = new AdminShopGui(this.plugin, adminShopConfig, this.ui);
    }

    public void init() {
	this.adminShopGui.init();
	// FIXME CHANGE SLOT TO 2 WHEN ADDING USER SHOPS
	this.ui.setItem(4, Util.getItem(this.plugin.getConf().readItemType("icons.adminShop"), 1,
		this.plugin.getL10n().getMessage("adminShopTitle").str()));

	// TODO USER SHOP (on slot 6)

	this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public Inventory getUi() {
	return this.ui;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
	if (e.getInventory().equals(this.ui)) {
	    e.setCancelled(true);
	    switch (e.getSlot()) {
	    case 4: // FIXME CHANGE SLOT TO 2 WHEN ADDING USER SHOPS
		e.getWhoClicked().openInventory(this.adminShopGui.getUi());
		break;
	    // TODO USER SHOP (on slot 6)
	    default:
		break;
	    }
	}
    }
}
