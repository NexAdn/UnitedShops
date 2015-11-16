package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/** Basic class for config managers
 * @author DerHerrFuchss
 */
public class ConfigBase {
	// Object Properties
	private YamlConfiguration conf;					// Config file
	private String workkey;							// Main working key
	
	/** Creates a new YamlConfiguration and loads file
	 * @param file - The file to be loaded
	 */
	public ConfigBase(File file)
	{
		this.conf = new YamlConfiguration();
		this.conf = YamlConfiguration.loadConfiguration(file);
		this.workkey = "config";
	}
	/** Creates a new YamlConfiguration and loads file
	 * Also sets the mainkey
	 * @param file - The file to be loaded
	 * @param mainKey - The main key to use for work
	 */
	public ConfigBase(File file, String mainKey)
	{
		this.conf = new YamlConfiguration();
		this.conf = YamlConfiguration.loadConfiguration(file);
		this.workkey = mainKey;
	}
	
	// Return the subkeys of the main configuration section
	public Set<String> getSubKeys(boolean recursive)
	{
		return conf.getConfigurationSection(workkey).getKeys(recursive);
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
	public YamlConfiguration getConf()
	{
		return conf;
	}
}
