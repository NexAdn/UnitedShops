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

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/** Basic class for config managers
 * @author NexAdn
 */
public class ConfigBase {
	// Object Properties
	private FileConfiguration conf;					// Config file
	private String workkey;							// Main working key
	private File file;								// Speicherdatei
	private UnitedShops plugin;						// Plugin
	
	/** Creates a new YamlConfiguration and loads file
	 * @param file - The file to be loaded
	 */
	public ConfigBase(UnitedShops plugin)
	{
		this.conf = plugin.getConfig();
		this.workkey = "config";
		this.file = file;
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
	
	public void saveConf()
	{
		try {
			conf.save(this.file);
		} catch (IOException except) {
			UnitedShops.server.getLogger().log(Level.SEVERE, "java.IOException thrown while saving file.", except);
		}
	}
	
	// Return the subkeys of the main configuration section
	public Set<String> getSubKeys()
	{
		return conf.getConfigurationSection(workkey).getKeys(false);
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
