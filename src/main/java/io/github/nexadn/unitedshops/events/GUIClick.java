package io.github.nexadn.unitedshops.events;

import io.github.nexadn.unitedshops.gui.GUIContainer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClick implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(GUIContainer.isGuiInventory(event.getInventory())) {
			event.setCancelled(true);
			
		}
	}
}
