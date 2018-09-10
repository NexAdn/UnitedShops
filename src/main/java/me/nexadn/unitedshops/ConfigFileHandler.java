package me.nexadn.unitedshops;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFileHandler {
    private UnitedShops plugin;

    private File configurationFile;
    private YamlConfiguration template;
    private YamlConfiguration configuration;

    public ConfigFileHandler(UnitedShops plugin, InputStream template, File config) {
	this.plugin = plugin;

	this.configurationFile = config;
	this.template = YamlConfiguration.loadConfiguration(new InputStreamReader(template));
	if (config.exists()) {
	    this.configuration = YamlConfiguration.loadConfiguration(config);
	} else {
	    this.saveTemplateToConfig();
	    this.configuration = YamlConfiguration.loadConfiguration(config);
	}
    }

    private void saveTemplateToConfig() {
	try {
	    this.template.save(this.configurationFile);
	} catch (IOException e) {
	    // TODO
	    e.printStackTrace();
	}
    }

    public void save() {
	try {
	    this.configuration.save(this.configurationFile);
	} catch (IOException e) {
	    // TODO
	    e.printStackTrace();
	}
    }

    public ConfigurationSection readSection(String key) {
	ConfigurationSection confRes = this.configuration.getConfigurationSection(key);
	if (confRes == null)
	    return this.template.getConfigurationSection(key);
	return confRes;
    }

    public Set<String> readChildren(String key) {
	ConfigurationSection section = this.readSection(key);
	return section.getKeys(false);
    }

    public Set<String> readChildrenRecursive(String key) {
	ConfigurationSection section = this.readSection(key);
	return section.getKeys(true);
    }

    public String readString(String key) {
	String confRes = this.configuration.getString(key);
	if (confRes == null || confRes.equalsIgnoreCase(""))
	    return this.template.getString(key);
	return confRes;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> readList(String key) throws ClassCastException {
	List<T> confRes = (List<T>) this.configuration.getList(key);
	if (confRes == null) {
	    return (List<T>) this.template.getList(key);
	}
	return confRes;
    }

    public int readInt(String key, boolean notZero) {
	int confRes = this.configuration.getInt(key);
	if (notZero && confRes == 0) {
	    return this.template.getInt(key);
	}
	return confRes;
    }

    public double readDouble(String key, boolean notZero) {
	double confRes = this.configuration.getDouble(key);
	if (notZero && confRes == 0.) {
	    return this.template.getDouble(key);
	}
	return confRes;
    }

    public Pair<Material, Short> readItemType(String key) {
	String confRes = this.configuration.getString(key);
	if (confRes == "")
	    confRes = this.configuration.getString(key);

	return Util.parseItemType(confRes);
    }
}
