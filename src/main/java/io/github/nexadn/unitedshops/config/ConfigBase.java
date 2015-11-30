/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

    This program is free software: you can redistribute it and/or modify
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

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/** Basic class for config managers
 * @author NexAdn
 */
public class ConfigBase {
	// Object Properties
	private FileConfiguration conf;					// Config file
	private String workkey;							// Main working key
	private UnitedShops plugin;						// Plugin
	
	/** Creates a new YamlConfiguration and loads file
	 * @param file - The file to be loaded
	 */
	public ConfigBase(UnitedShops plugin)
	{
		this.plugin = plugin;
		this.conf = this.plugin.getConfig();
		this.workkey = "config";
	}
	/** Creates a new YamlConfiguration and loads file
	 * Also sets the mainkey
	 * @param file - The file to be loaded
	 * @param mainKey - The main key to use for work
	 */
	public ConfigBase(UnitedShops plugin, String mainKey)
	{
		this.conf = null;
		this.workkey = mainKey;
	}
	
	// Return the subkeys of the main configuration section
	public Set<String> getSubKeys()
	{
		if(this.conf == null)
		{
			this.conf = this.plugin.getConfig();
		}
		return this.conf.getConfigurationSection(workkey).getKeys(false);
		/*g> all = this.conf.getKeys(true);
		for( String s:all )
		{
			if(s.substring(0, this.workkey.length()).equalsIgnoreCase(this.workkey))
			{
				boolean childchild = false;
				// Check if the Key is a child of a child -> break if true
				for(int i=this.workkey.length()+1; i<s.length(); i++)
				{
					if(s.charAt(i) == '.')
					{
						childchild = true;
						break;
					}
				}
				if(childchild == true)
				{
					// s is a child of a child of the workkey -> ignore
					continue;
				} else {
					// s is a child of the workkey, but no child of a child
					subkeys.add(s);
				}
				
			}
		}
		return subkeys;*/
	}
	public ConfigurationSection getMainSection()
	{
		return conf.getConfigurationSection(workkey);
	}
	
	// Setters
	public void setWorkKey(String tag)
	{
		this.workkey = tag;
	}
	
	// Getters
	public FileConfiguration getConf()
	{
		return this.conf;
	}
	
	public String getWorkKey() { return this.workkey; }
}
