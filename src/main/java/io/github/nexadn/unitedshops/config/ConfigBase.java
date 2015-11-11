package io.github.nexadn.unitedshops.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/** Basic class for config managers
 * @author DerHerrFuchss
 */
public class ConfigBase {
	// YAML Tag Container
	/*
	private String headtag;							// Kopftag
	private List<String> lista;						// Keyliste für Keys erster Ebene (A-Tags)
	private HashMap<String,List<String>> listb;		// HashMap für Tags zweiter Ebene (B-Tags)
		// Zuordnung: <A-Tag,Tagliste>*/
	
	private YamlConfiguration conf;
	
	public ConfigBase(String headertag, File file)
	{
		conf = new YamlConfiguration();
		conf = YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getConf()
	{
		return conf;
	}
}
