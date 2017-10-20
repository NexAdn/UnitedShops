package io.github.nexadn.unitedshops;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nexadn.unitedshops.command.*;
import io.github.nexadn.unitedshops.events.*;
import io.github.nexadn.unitedshops.shop.*;
import io.github.nexadn.unitedshops.tradeapi.*;

public class UnitedShops extends JavaPlugin 
{	
	public static UnitedShops plugin;
	
	@Override
	/** Enable the plugin */
	public void onEnable()
	{
		plugin = this;
		
		this.getLogger().log(Level.FINE, "Establishing economy hook...");
		if ( !EcoManager.initEco() ) {
			this.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
			this.setEnabled(false);
			return;
		}
		this.getLogger().log(Level.FINE, "Economy hook successful.");
		
		// config.yml
		try {
			if (!getDataFolder().exists())
			{
				getDataFolder().mkdirs();
			}
			File configyml = new File(getDataFolder(), "config.yml");
			if (!configyml.exists())
			{
				this.getLogger().log(Level.INFO, "config.yml not found, creating a new one just for you!");
				this.saveResource("config.yml", true);
			}
		} catch (Exception e)
		{
			this.getLogger().log(Level.INFO, e.getMessage());
			e.printStackTrace();
			this.setEnabled(false);
		}
		
		// Command executors
		this.getServer().getPluginCommand("ushop").setExecutor(new ShopGUIHandler());		// /ushop
		
		// Event handler
		this.getServer().getPluginManager().registerEvents(new GUIClick(), this);
		
		GUIContainer.initGUI();
	}
	
	@Override
	public void onDisable()
	{
		saveConfig();
	}
	
	public void log(Level loglevel, String message)
	{
		this.getLogger().log(loglevel, message);
	}
	
	public void sendMessage(CommandSender target, String message)
	{
		target.sendMessage("[" + this.getName() + "] " + message);
	}
}

/*  Copyright (C) 2015, 2016, 2017 Adrian Schollmeyer

This file is part of UnitedShops.

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
