package io.github.nexadn.unitedshops.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface PagerItem {
	void call (InventoryClickEvent e);

	ItemStack getIcon ();
}
