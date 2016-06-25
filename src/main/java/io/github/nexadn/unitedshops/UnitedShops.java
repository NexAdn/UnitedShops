/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

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
package io.github.nexadn.unitedshops;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nexadn.unitedshops.command.ShopGUIHandler;
import io.github.nexadn.unitedshops.command.UShopDebug;
import io.github.nexadn.unitedshops.config.ConfigShopMain;
import io.github.nexadn.unitedshops.shop.GUIContainer;
import io.github.nexadn.unitedshops.tradeapi.EcoManager;

/** Basic class for the plugin
 * @author NexAdn
 */
public class UnitedShops extends JavaPlugin 
{	
	private static ConfigShopMain shopconf; /** Shop config of config.yml */
	public static UnitedShops plugin = null;
	
	@Override
	/** Enable the plugin */
	public void onEnable()
	{
		UnitedShops.plugin = this;
		
		UnitedShops.shopconf = new ConfigShopMain();
		
		if ( !EcoManager.initEco() ) {
			this.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
			this.setEnabled(false);
			return;
		}
		this.getLogger().log(Level.FINE, "Successfully initialized connection to Vault.");
		
		// Command executors
		this.getServer().getPluginCommand("ushopdebug").setExecutor(new UShopDebug());		// /ushopdebug
		this.getServer().getPluginCommand("ushop").setExecutor(new ShopGUIHandler());		// /ushop
		
		GUIContainer.initGUI();
	}
	
	@Override
	/** Disable the plugin */
	public void onDisable()
	{
		saveConfig();
	}
	
	/** Send a message to the target with the Plugin prefix
	 * @param target - The target.
	 * @param message - The Message
	 */
	public void sendMessage(CommandSender target, String message)
	{
		target.sendMessage("[" + this.getName() + "] " + message);
	}
	/** Send a message to the target with the Plugin prefix an a special color.
	 * @param target - The target
	 * @param message - The message.
	 * @param color - The color.
	 */
	public void sendMessage(CommandSender target, String message, ChatColor color)
	{
		target.sendMessage(color + "[" + this.getName() + "] " + message);
	}
	
	public ConfigShopMain getShopConf()
	{
		return UnitedShops.shopconf;
	}
}

/*
	TODO: 
	- [DONE] GUI vervollständigen
	- [WIP] EventHandler einstellen
	- [DONE/ERR] Konfigurationssystem anpassen (Standard config.yml in resources einbauen -> saveDefaultconfig())
*/
