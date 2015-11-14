package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import io.github.nexadn.unitedshops.shop.ShopInventory;

/** Container for the shop config file
 * @author NexAdn
 *
 */
public class ConfigShopMain extends ConfigBase {
	private List<ShopInventory> menus;		// Menu container
	
	public ConfigShopMain(File file) {
		super(file);
	}
	public ConfigShopMain(File file, String mainTag)
	{
		super(file, mainTag);
	}
	// parse the Config File 
	public void parseConfig()
	{
		Set<String> kies = super.getSubKeys(false);
		for( String s:kies )
		{
			this.menus.add( new ShopInventory());
			String title = super.getMainSection().getString(s + ".title");
			Material icon = Material.getMaterial(super.getMainSection().getString(s + ".iconitem")); 
		}
	}
	
	public List<ShopInventory> getMenus() { return this.menus; }
}
