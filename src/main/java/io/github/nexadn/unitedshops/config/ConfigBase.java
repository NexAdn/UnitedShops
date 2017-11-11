package io.github.nexadn.unitedshops.config;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.nexadn.unitedshops.UnitedShops;

/**
 * Basic class for config managers
 * 
 * @author NexAdn
 */
public class ConfigBase {
	// Object Properties
	private FileConfiguration	conf;
	private String				workkey;

	public ConfigBase()
	{
		this.conf = UnitedShops.plugin.getConfig();
		this.workkey = "config";
	}

	public ConfigBase(String mainKey)
	{
		this.conf = UnitedShops.plugin.getConfig();
		this.workkey = mainKey;
	}

	public Set<String> getSubKeys () throws NullPointerException
	{
		if (this.conf == null)
		{
			this.conf = UnitedShops.plugin.getConfig();
		}
		Set<String> all = this.conf.getConfigurationSection(this.workkey).getKeys(false);
		return all;
	}

	public ConfigurationSection getMainSection ()
	{
		return conf.getConfigurationSection(workkey);
	}

	public void setWorkKey (String tag)
	{
		this.workkey = tag;
	}

	public FileConfiguration getConf ()
	{
		return this.conf;
	}

	public String getWorkKey ()
	{
		return this.workkey;
	}
}

/*
 * Copyright (C) 2015, 2016, 2017 Adrian Schollmeyer
 * 
 * This file is part of UnitedShops.
 * 
 * UnitedShops is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
