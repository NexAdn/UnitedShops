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

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Server;
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
		
		// Hook into Vault
		if ( !EcoManager.initEco() ) {
			// Economy nicht eingestellt...
			UnitedShops.server.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
		}
		
		// Commande executors
		UnitedShops.server.getPluginCommand("ushopdebug").setExecutor(new UShopDebug());		// /ushopdebug
		UnitedShops.server.getPluginCommand("ushop").setExecutor(new ShopGUIHandler());			// /ushop
		
		GUIContainer.setPlugin(this);
		GUIContainer.initGUI();
		
		if(!new File(getDataFolder(), "config.yml").exists())
		{
			// Save an example config file if no config.yml exists.
			this.saveDefaultConfig();
		}
	}
	
	@Override
	/** Disable the plugin */
	public void onDisable()
	{
		saveConfig();
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
	- Konfigurationssystem anpassen (Standard config.yml in resources einbauen -> saveDefaultconfig())
*/
