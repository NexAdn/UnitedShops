package me.nexadn.unitedshops.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.exception.InvalidConfigException;
import me.nexadn.unitedshops.shop.ShopObject;
import me.nexadn.unitedshops.shop.admin.AdminShop;

public class ShopConfigShopParser {
    private UnitedShops plugin;
    private ConfigFileHandler config;

    private String title;
    private Pair<Material, Short> icon;
    private List<ShopObject> items = new ArrayList<>();

    private String baseKey;

    public ShopConfigShopParser(UnitedShops plugin, ConfigFileHandler config, String parentBaseKey, String childKey) {
	this.plugin = plugin;
	this.baseKey = parentBaseKey + "." + childKey;
	this.config = config;
    }

    public void parse() {
	this.title = this.config.readString(this.baseKey + ".title");
	if (this.title == null || this.title.equalsIgnoreCase(""))
	    throw new InvalidConfigException(this.baseKey + ".title");

	this.icon = this.config.readItemType(this.baseKey + ".icon");
	if (this.icon.first == null || this.icon.second < 0)
	    throw new InvalidConfigException(this.baseKey + ".icon");

	Set<String> subkeys = this.config.readChildren(this.baseKey + ".items");
	for (String k : subkeys) {
	    String type = this.config.readString(this.baseKey + ".items." + k + ".type");
	    if (type.equalsIgnoreCase("shop")) {
		ShopConfigShopParser parser = new ShopConfigShopParser(this.plugin, this.config,
			this.baseKey + ".items", k);
		parser.parse();
		this.items.add(parser.get());
	    } else if (type.equalsIgnoreCase("item")) {
		ShopConfigItemParser parser = new ShopConfigItemParser(this.plugin, this.config,
			this.baseKey + ".items", k);
		parser.parse();
		this.items.add(parser.get());
	    } else {
		throw new InvalidConfigException(this.baseKey + ".items." + k + ".type");
	    }
	}
    }

    public AdminShop get() {
	AdminShop shop = new AdminShop(this.plugin, this.title, this.icon);
	for (ShopObject o : this.items)
	    shop.addItem(o);

	return shop;
    }
}
