package io.github.nexadn.unitedshops;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.ui.PagerItem;

public class PagerTestItem implements PagerItem {

	private int i;

	public PagerTestItem(int i)
	{
		this.i = i;
	}

	public void call (InventoryClickEvent e) {
		System.out.println (this.i);
	}

	public ItemStack getIcon () {
		return new ItemStack (Material.COBBLESTONE);
	}

}
