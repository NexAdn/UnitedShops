package me.nexadn.unitedshops.ui;

import java.util.List;

import org.bukkit.inventory.Inventory;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.parser.ShopConfigParser;
import me.nexadn.unitedshops.shop.ShopObject;

public class AdminShopGui {
    private UnitedShops plugin;

    Inventory parent;
    List<ShopObject> items;
    Pager ui;

    public AdminShopGui(UnitedShops plugin, ConfigFileHandler shopConfig, Inventory parent) {
	this.plugin = plugin;
	this.parent = parent;

	ShopConfigParser parser = new ShopConfigParser(this.plugin, shopConfig);
	parser.parse();
	this.items = parser.get();
    }

    public void init() {
	this.ui = new Pager(this.plugin, this.items, this.plugin.getL10n().getMessage("adminShopTitle").str(),
		this.parent);

	for (ShopObject item : this.items) {
	    item.init(this.getUi());
	}
    }

    public Inventory getUi() {
	return this.ui.getFirstInventory();
    }
}
