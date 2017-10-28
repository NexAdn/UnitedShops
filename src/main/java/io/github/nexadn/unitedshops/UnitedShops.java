package io.github.nexadn.unitedshops;

import java.util.*;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nexadn.unitedshops.command.*;
import io.github.nexadn.unitedshops.events.*;
import io.github.nexadn.unitedshops.shop.*;
import io.github.nexadn.unitedshops.tradeapi.*;

public class UnitedShops extends JavaPlugin 
{	
	private HashMap<OfflinePlayer, AutoSellManager> autoSaleInventories;
	
	public static UnitedShops plugin;
	
	private Metrics metrics;
	
	@Override
	/** Enable the plugin */
	public void onEnable()
	{
		plugin = this;
		this.autoSaleInventories = new HashMap<OfflinePlayer, AutoSellManager>();
		this.metrics = new Metrics(this);
		
		if ( !EcoManager.initEco() ) {
			this.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
			this.setEnabled(false);
			return;
		}
		this.getLogger().log(Level.FINE, "Economy hook successful.");
		
		// Command executors
		this.getServer().getPluginCommand("ushop").setExecutor(new ShopGUIHandler());		// /ushop
		this.getServer().getPluginCommand("usell").setExecutor(new AutoSellHandler());		// /usell
		
		// Event handler
		this.getServer().getPluginManager().registerEvents(new GUIClick(), this);
		this.getServer().getPluginManager().registerEvents(new OnInventoryClose(), this);
		
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
	
	public AutoSellManager getAutoSellManager (OfflinePlayer player)
	{
		if ((!this.autoSaleInventories.containsKey(player)) || this.autoSaleInventories.get(player) == null)
		{
			this.autoSaleInventories.put(player, new AutoSellManager(player));
		}
		return this.autoSaleInventories.get(player);
	}
	
	public boolean hasAutoSellManager (OfflinePlayer player)
	{
		return this.autoSaleInventories.containsKey(player);
		
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
