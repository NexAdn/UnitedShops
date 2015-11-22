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
		this.conf = plugin.getConfig();
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
