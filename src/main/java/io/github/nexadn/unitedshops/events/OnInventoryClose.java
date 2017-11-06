package io.github.nexadn.unitedshops.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.shop.AutoSellManager;

public class OnInventoryClose implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryCloseEvent event)
	{
		Player ep = (Player) event.getPlayer();
		OfflinePlayer op = (OfflinePlayer) ep;
		if (UnitedShops.plugin.hasAutoSellManager(op))
		{
			AutoSellManager m = UnitedShops.plugin.getAutoSellManager(op);
			if (event.getInventory().equals(m.getInventory()))
			{
				m.fetchInventory();
			}
		}
	}
}
