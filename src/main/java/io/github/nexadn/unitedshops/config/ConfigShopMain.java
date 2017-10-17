/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

    UnitedShops is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.shop.ShopInventory;
import io.github.nexadn.unitedshops.shop.ShopObject;

/** Container for the shop config file
 * @author NexAdn
 */
public class ConfigShopMain extends ConfigBase {
	private HashMap<String, ShopInventory> menus;		// Menu container
	
	public ConfigShopMain() {
		super("shops");
		this.menus = new HashMap<String, ShopInventory>();
	}
	
	/** Parse the config file and save all data in a HashMap */ 
	public void parseConfig()
	{
		UnitedShops.plugin.log(Level.FINE, "Loading config");
		UnitedShops.plugin.reloadConfig();
		Set<String> keys = null;
		try { 
			keys = super.getSubKeys(); 
		}
		catch( NullPointerException ex )
		{
			UnitedShops.plugin.getLogger().log(Level.WARNING, "Got NullPointerException in parseConfig()!");
			ex.printStackTrace();
			FileConfiguration conf = super.getConf();
			conf = UnitedShops.plugin.getConfig();
			conf.createSection("shops");
			conf.createSection("shops.exampleshop");
			conf.addDefault("shops.exampleshop.id", 0);
			conf.addDefault("shops.exampleshop.title", "Example shop");
			conf.addDefault("shops.exampleshop.iconitem", "COBBLESTONE");
			conf.createSection("shops.exampleshop.items");
			conf.createSection("shops.exampleshop.items.COBBLESTONE");
			conf.addDefault("shops.exampleshop.items.COBBLESTONE.buy", 10.0);
			conf.addDefault("shops.exampleshop.items.COBBLESTONE.sell", 1.0);
			keys = conf.getKeys(true);
			try {
				UnitedShops.plugin.log(Level.WARNING, "No configuration file found. Creating a new one just for you.");
				conf.save(new File(UnitedShops.plugin.getDataFolder(), "config.yml"));
				UnitedShops.plugin.reloadConfig();
			} catch (IOException e) {
				e.printStackTrace();
				UnitedShops.plugin.getLogger().log(Level.SEVERE, "Couldn't save config.yml");
			}
		}
		
		for( String s:keys )
		{
			String title = super.getMainSection().getString(s + ".title"); // shops.[key].title
			UnitedShops.plugin.log(Level.FINE, "Add shop: " + title);
			Material icon = Material.getMaterial(super.getMainSection().getString(s + ".iconitem")); // shops.[key].iconitem
			int id = super.getMainSection().getInt(s + ".id"); // shops.[key].id
			UnitedShops.plugin.log(Level.FINER, "key: " + s);
			UnitedShops.plugin.log(Level.FINER, "id: " + id);
			this.menus.put(s, new ShopInventory(title, new ItemStack(icon, 1), id) );
			
			String sect = super.getWorkKey() + "." + s + "." + "items";
			Set<String> subkeys = super.getConf().getConfigurationSection(sect).getKeys(false);
			for( String sub:subkeys ) // shops.[key].items.[key2]
			{
				UnitedShops.plugin.log(Level.FINEST, "Item: " + sub);
				String path = super.getWorkKey() + "." + s + "." + "items" + "." + sub; // shops.[key].items.[key2]
				Material mat = Material.getMaterial(sub);
				this.menus.get(s).addContent( new ShopObject(mat, super.getConf().getDouble(path + ".buy"), super.getConf().getDouble(path + ".sell")) );
			}
		}
	}
	
	public List<ShopInventory> getMenus() 
	{
		List<ShopInventory> temp = new Vector<ShopInventory>();
		Collection<ShopInventory> inv = this.menus.values();
		int highest = 0;
		for( ShopInventory i : inv )
		{
			if( i.getOrderNumber() > highest )
			{
				highest = i.getOrderNumber();
			}
		}
		
		// Transform the Collection into a List using the given ordering numbers
		for( int cnt = 0; cnt<=highest; cnt++ )
		{
			for( ShopInventory i : inv )
			{
				if( i.getOrderNumber() == cnt )
				{
					temp.add(i);
					break;
				}
			}
		}
		return temp;
	}
}
