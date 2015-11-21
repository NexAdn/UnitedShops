package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/** Basic class for config managers
 * @author NexAdn
 */
public class ConfigBase {
	// Object Properties
	private YamlConfiguration conf;					// Config file
	private String workkey;							// Main working key
	private File file;								// Speicherdatei
	
	/** Creates a new YamlConfiguration and loads file
	 * @param file - The file to be loaded
	 */
	public ConfigBase(File file)
	{
		this.conf = new YamlConfiguration();
		this.conf = YamlConfiguration.loadConfiguration(file);
		this.workkey = "config";
		this.file = file;
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
	public YamlConfiguration getConf()
	{
		return conf;
	}
	
	public String getWorkKey() { return this.workkey; }
}
