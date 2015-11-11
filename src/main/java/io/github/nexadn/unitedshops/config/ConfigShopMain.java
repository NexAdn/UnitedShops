package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.HashMap;

public class ConfigShopMain extends ConfigBase {

	private HashMap<String,String> categoryList;		// List of categories
		// <String inventoryId,String categoryKey>
	
	public ConfigShopMain(File file) {
		super(file);
	}
	
}
