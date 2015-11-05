package io.github.nexadn.unitedshops.tradeapi;

import org.bukkit.plugin.RegisteredServiceProvider;

import io.github.nexadn.unitedshops.UnitedShops;
import net.milkbowl.vault.economy.Economy;

public class EcoManager {
	private static Economy economy;
	
	public static boolean initEco()
	{
		RegisteredServiceProvider<Economy> economyProvider = UnitedShops.server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if( economyProvider != null ) {
			economy = economyProvider.getProvider();
		}
		
		return (economy != null);
	}
	
	public static Economy getEconomy() {
		while( economy == null ) {
			initEco();
		}
		return economy; 
	}
}
