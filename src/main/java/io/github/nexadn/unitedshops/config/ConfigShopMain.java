package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.shop.ShopInventory;
import io.github.nexadn.unitedshops.shop.ShopObject;

/** Container for the shop config file
 * @author NexAdn
 */
public class ConfigShopMain extends ConfigBase {
	private HashMap<String, ShopInventory> menus;		// Menu container
	
	public ConfigShopMain(File file) {
		super(file);
		menus = new HashMap<String, ShopInventory>();
	}
	public ConfigShopMain(File file, String mainTag)
	{
		super(file, mainTag);
		menus = new HashMap<String, ShopInventory>();
	}
	// parse the Config File 
	public void parseConfig()
	{
		Set<String> kies = super.getSubKeys();
		for( String s:kies )
		{
			String title = super.getMainSection().getString(s + ".title"); // shops.[key].title
			Material icon = Material.getMaterial(super.getMainSection().getString(s + ".iconitem")); // shops.[key].iconitem
			this.menus.put(s, new ShopInventory(title, new ItemStack(icon, 1)) );
			String sect = super.getWorkKey() + "." + s + "." + "items";
			Set<String> subkies = super.getConf().getConfigurationSection(sect).getKeys(false);
			for( String sub:subkies ) // shops.[key].items.[key2]
			{
				String path = super.getWorkKey() + "." + s + "." + "items" + "." + sub; // replacement for sec
				//ConfigurationSection sec = super.getConf().getConfigurationSection(sub);
				//Material mat = Material.getMaterial(sec.getString("item")); // shops.[key].items.[key2].item // ERROR ??? //
				Material mat = Material.getMaterial(sub);
				ShopObject cont = new ShopObject(mat, super.getConf().getDouble(path + ".buy"), super.getConf().getDouble("sell")); // Shop Contents
				this.menus.get(s).addContent(cont);
			}
		}
	}
	
	public List<ShopInventory> getMenus() 
	{
		List<ShopInventory> temp = null;
		Collection<ShopInventory> inv = this.menus.values();
		temp = (List<ShopInventory>) inv;
		return temp;
	}
}
