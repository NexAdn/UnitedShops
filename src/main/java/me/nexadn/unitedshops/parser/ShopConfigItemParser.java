package me.nexadn.unitedshops.parser;

import org.bukkit.Material;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;
import me.nexadn.unitedshops.exception.InvalidConfigException;
import me.nexadn.unitedshops.shop.admin.AdminShopItem;

public class ShopConfigItemParser {
    private UnitedShops plugin;
    private ConfigFileHandler config;

    private AdminShopItem item;

    private final String baseKey;
    private final String itemString;

    public ShopConfigItemParser(UnitedShops plugin, ConfigFileHandler config, String parentBaseKey, String childKey) {
	this.plugin = plugin;
	this.baseKey = parentBaseKey + "." + childKey;
	this.config = config;
	this.itemString = childKey;
    }

    public void parse() {
	double buy = this.config.readDouble(this.baseKey + ".buy", true);
	double sell = this.config.readDouble(this.baseKey + ".sell", false);
	Pair<Material, Short> itemType = Util.parseItemType(this.itemString);
	if (itemType.first != null && itemType.second >= 0)
	    this.item = new AdminShopItem(this.plugin, itemType, buy, sell);
	else
	    throw new InvalidConfigException(this.baseKey);
    }

    public AdminShopItem get() {
	return this.item;
    }
}
