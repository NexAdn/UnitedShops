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

	public void call (InventoryClickEvent e)
	{
		System.out.println(this.i);
	}

	public ItemStack getIcon ()
	{
		return new ItemStack(Material.COBBLESTONE);
	}

}

/*
 * Copyright (C) 2017 Adrian Schollmeyer
 * 
 * This file is part of UnitedShops.
 * 
 * UnitedShops is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
