package io.github.nexadn.unitedshops;

import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nexadn.unitedshops.command.UShopDebug;
import io.github.nexadn.unitedshops.config.ConfigShopMain;
import io.github.nexadn.unitedshops.tradeapi.EcoManager;

public class UnitedShops extends JavaPlugin {
	
	public static Server server;
	public static FileConfiguration conf;
	public static ConfigShopMain shopconf;
	
	@Override
	public void onEnable()
	{
		UnitedShops.server = getServer();
		UnitedShops.conf = getConfig();
		
		// Hook into Vault
		if ( !EcoManager.initEco() ) {
			// Economy nicht eingestellt...
			UnitedShops.server.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
		}
		
		// Commande executors
		UnitedShops.server.getPluginCommand("ushopdebug").setExecutor(new UShopDebug());										// /ushopdebug
		//UnitedShops.server.getPluginCommand("ushop").setExecutor(new io.github.nexadn.unitedshops.command.ShopGUIHandler());	// /ushop
		
	}
	
	@Override
	public void onDisable()
	{
		saveConfig();
	}
}

/*
	TODO: 
	- [DONE] Testbefehl hinzufügen
	- [DONE] Testbefehl Executor registrieren
	- GUI vervollständigen
	- EventHandler einstellen
	- [WIP/ERROR] CommandExecutor setzen
	- [DONE] Permissions 
*/