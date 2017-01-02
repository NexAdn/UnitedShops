/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer
    
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
package io.github.nexadn.unitedshops.tradeapi;

import org.bukkit.plugin.RegisteredServiceProvider;

import io.github.nexadn.unitedshops.UnitedShops;
import net.milkbowl.vault.economy.Economy;

public class EcoManager {
	private static Economy economy;
	
	public static boolean initEco()
	{
		RegisteredServiceProvider<Economy> economyProvider = UnitedShops.plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
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
