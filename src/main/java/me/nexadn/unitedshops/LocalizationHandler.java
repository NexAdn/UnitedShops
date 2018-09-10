package me.nexadn.unitedshops;

import java.io.File;

public class LocalizationHandler {
    private UnitedShops plugin;
    private ConfigFileHandler l10nFile;

    public LocalizationHandler(UnitedShops plugin, ConfigFileHandler configFile) {
	this.plugin = plugin;
	String l10nFileNameBase = configFile.readString("locale");
	File folder = new File(this.plugin.getDataFolder(), "locale");
	folder.mkdirs();
	this.l10nFile = new ConfigFileHandler(this.plugin, this.plugin.getResource("locale/en_US.yml"),
		new File(folder, l10nFileNameBase + ".yml"));
    }

    public RichString getMessage(String key) {
	if (this.l10nFile.readString("messages." + key) == null)
	    return new RichString("(" + key + ")");
	return new RichString(this.l10nFile.readString("messages." + key));
    }
}
