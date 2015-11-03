package io.github.nexadn.unitedshops;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class UnitedShops extends JavaPlugin {
	
	public static Server server;
	
	@Override
	public void onEnable()
	{
		this.server = getServer();
	}
	
	@Override
	public void onDisable()
	{
		
	}
}

/*
	TODO: 
	- Testbefehl hinzufügen
	- GUI vervollständigen
	- EventHandler einstellen
	- CommandExecutor setzen
	- Permissions
	- UnitedShops.class: Variablen hinzufügen
*/