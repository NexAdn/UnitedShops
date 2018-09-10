package me.nexadn.unitedshops.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.exception.InvalidConfigException;
import me.nexadn.unitedshops.shop.ShopObject;

public class ShopConfigParser {
    private UnitedShops plugin;
    private ConfigFileHandler configFileHandler;

    private List<ShopObject> objects = new ArrayList<>();

    private static final String baseKey = "shops";

    public ShopConfigParser(UnitedShops plugin, ConfigFileHandler configFileHandler) {
	this.plugin = plugin;
	this.configFileHandler = configFileHandler;
    }

    public void parse() {
	Set<String> keys = this.configFileHandler.readChildren(baseKey);
	for (String k : keys) {
	    String type = this.configFileHandler.readString(baseKey + "." + k + ".type");
	    if (type.equalsIgnoreCase("shop")) {
		ShopConfigShopParser parser = new ShopConfigShopParser(this.plugin, this.configFileHandler, baseKey, k);
		parser.parse();
		this.objects.add(parser.get());
	    } else if (type.equalsIgnoreCase("item")) {
		ShopConfigItemParser parser = new ShopConfigItemParser(this.plugin, this.configFileHandler, baseKey, k);
		parser.parse();
		this.objects.add(parser.get());
	    } else {
		throw new InvalidConfigException(baseKey + "." + k + ".type");
	    }
	}
    }

    public List<ShopObject> get() {
	return this.objects;
    }
}
