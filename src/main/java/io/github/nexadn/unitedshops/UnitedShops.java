/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

    This program is free software: you can redistribute it and/or modify
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
import org.bukkit.Server;
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
public class UnitedShops extends JavaPlugin {
	
	public static Server server; /** Public variable to access the Server object */
	private ConfigShopMain shopconf; /** Access to the shop configuration section of config.yml */
	
	@Override
	/** Enable the plugin */
	public void onEnable()
	{
		UnitedShops.server = getServer();
		this.shopconf = new ConfigShopMain( this );
		this.getConfig();
		this.reloadConfig();
		
		// Hook into Vault
		if ( !EcoManager.initEco() ) {
			// Economy nicht eingestellt...
			this.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
		}
		
		// Commande executors
		this.getServer().getPluginCommand("ushopdebug").setExecutor(new UShopDebug());		// /ushopdebug
		this.getServer().getPluginCommand("ushop").setExecutor(new ShopGUIHandler());		// /ushop
		
		GUIContainer.setPlugin(this);
		GUIContainer.initGUI();
		
		/*if(!new File(getDataFolder(), "config.yml").exists())
		{
			// Save an example config file if no config.yml exists.
			this.saveDefaultConfig();
		}*/
	}
	
	@Override
	/** Disable the plugin */
	public void onDisable()
	{
		saveConfig();
	}
	
	public void logMessage(Level loglevel, String message)
	{
		this.getLogger().log(loglevel, message);
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
	
	public ConfigShopMain getShopConf() { return this.shopconf; }
}

/*
	TODO: 
	- [DONE] Testbefehl hinzufügen
	- [DONE] Testbefehl Executor registrieren
	- [DONE] GUI vervollständigen
	- [WIP] EventHandler einstellen
	- [DONE] CommandExecutor setzen
	- [DONE] Permissions 
	- [DONE/ERR] Konfigurationssystem anpassen (Standard config.yml in resources einbauen -> saveDefaultconfig())
*/
