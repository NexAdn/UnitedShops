package io.github.nexadn.unitedshops;

import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nexadn.unitedshops.tradeapi.EcoManager;

public class UnitedShops extends JavaPlugin {
	
	public static Server server;
	
	@Override
	public void onEnable()
	{
		this.server = getServer();
		
		try 
		{
			Thread.sleep(5000);
		} 
		catch(InterruptedException ex) 
		{
			Thread.currentThread().interrupt();
		}
		
		if ( !EcoManager.initEco() ) {
			// Economy nicht eingestellt...
			this.server.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
		}
		
	}
	
	@Override
	public void onDisable()
	{
		
	}
}

/*
	TODO: 
	- [DONE] Testbefehl hinzufügen
	- GUI vervollständigen
	- EventHandler einstellen
	- CommandExecutor setzen
	- Permissions
	- UnitedShops.class: Variablen hinzufügen
*/