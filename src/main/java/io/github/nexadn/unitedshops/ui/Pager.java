package io.github.nexadn.unitedshops.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.shop.GUIContainer;

public class Pager implements Listener {
	private List<? extends PagerItem>	items;
	private List<Inventory>				uiInventorys;
	private String						title;
	private Inventory					parent;
	private int							menuButtonSettings;
	private int							rowsPerPage;
	/** Slots reserved for menu Buttons (add 9 per row, only increment if needed) */
	private final int					menuRowSlots	= 9;

	public Pager(List<? extends PagerItem> pagerItems, int menuButtons, String title)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = 3;
		this.parent = null;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<? extends PagerItem> pagerItems, int menuButtons, String title, Inventory parent)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = 3;
		this.parent = parent;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<? extends PagerItem> pagerItems, int menuButtons, String title, int rowsPerPage)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = rowsPerPage;
		this.parent = null;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<? extends PagerItem> pagerItems, int menuButtons, String title, int rowsPerPage, Inventory parent)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = rowsPerPage;
		this.parent = parent;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public int getInventoryCount ()
	{
		return calculateInventoryCount(this.items.size(), this.rowsPerPage);
	}

	public static int calculateInventoryCount (int items, int rowsPerPage)
	{
		return (int) Math.ceil( ((double) items / (9 * (double) rowsPerPage)));
	}

	public int getRowsPerPage ()
	{
		return this.rowsPerPage;
	}

	public void init ()
	{
		try
		{
			int inventoryCount = this.getInventoryCount();
			if (this.uiInventorys == null)
				this.uiInventorys = new ArrayList<Inventory>();
			this.uiInventorys.clear();

			for (int i = 0; i < inventoryCount; ++i)
			{
				this.uiInventorys.add(Bukkit.createInventory(null, 9 * this.rowsPerPage + this.menuRowSlots,
						this.title + " (" + Integer.toString(i + 1) + "/" + inventoryCount + ")"));
				List<ItemStack> bar = MenuButton.createIconBar(this.menuButtonSettings);
				for (int j = 0; j < this.menuRowSlots; ++j)
				{
					this.uiInventorys.get(i).setItem(j + (this.rowsPerPage * 9), bar.get(j));
				}
			}

			for (int i = 0; i < this.items.size(); ++i)
			{
				int page = Math.floorDiv(i, 9 * this.rowsPerPage);
				this.uiInventorys.get(page).setItem(i - (page * 9 * this.rowsPerPage), this.items.get(i).getIcon());
			}
		} catch (ArithmeticException e)
		{
			UnitedShops.plugin.log(Level.SEVERE, "Division by zero!");
			e.printStackTrace();
		}
	}

	public Inventory getFirstInventory ()
	{
		if (this.uiInventorys.size() > 0)
		{
			return this.uiInventorys.get(0);
		} else
		{
			return null;
		}
	}

	private void callItem (int index, InventoryClickEvent event)
	{
		if (this.items.get(index) != null)
			this.items.get(index).call(event);
	}

	@EventHandler
	public void onInventoryClick (InventoryClickEvent event)
	{
		for (Inventory i : this.uiInventorys)
		{
			if (i.equals(event.getInventory()))
			{
				event.setCancelled(true);
				int row = (event.getSlot() - event.getSlot() % 9) / 9;
				if (row >= this.rowsPerPage)
				{
					// menu buttons
					int col = event.getSlot() % 9;
					int button = 1 << col;
					if ( (button & MenuButton.PREV & this.menuButtonSettings) > 0)
					{
						// Previous
						if (this.uiInventorys.lastIndexOf(event.getInventory()) > 0)
							event.getWhoClicked()
									.openInventory(this.uiInventorys.get(this.uiInventorys.lastIndexOf(i) - 1));
					} else if ( (button & MenuButton.UP & this.menuButtonSettings) > 0)
					{
						// Up
						if (this.parent != null)
						{
							event.getWhoClicked().openInventory(this.parent);
						}
					} else if ( (button & MenuButton.CLOSE & this.menuButtonSettings) > 0)
					{
						// Close
						event.getWhoClicked().closeInventory();
					} else if ( (button & MenuButton.NEXT & this.menuButtonSettings) > 0)
					{
						// Next
						int nextindex = this.uiInventorys.lastIndexOf(i) + 1;
						if (nextindex < this.uiInventorys.size())
						{
							event.getWhoClicked().openInventory(this.uiInventorys.get(nextindex));
						}
					}

				} else
				{
					// UI item
					int page = this.uiInventorys.lastIndexOf(i);
					int slot = page * this.rowsPerPage * 9 + event.getSlot();
					callItem(slot, event);
				}
			}
		}
	}

	public int getmenuButtonSettings ()
	{
		return menuButtonSettings;
	}

	public void setmenuButtonSettings (int menuButtonSettings)
	{
		this.menuButtonSettings = menuButtonSettings;
		init();
	}

	public List<Inventory> getInventorys ()
	{
		return this.uiInventorys;
	}

	/** Flags for the menuButton */
	public static class MenuButton {
		// 1 << Spalte → Flag
		final public static int	PREV	= 0x01;		// 0
		final public static int	UP		= 0x08;		// 3
		final public static int	CLOSE	= 0x20;		// 5
		final public static int	NEXT	= 0x0100;	// 8

		public static List<ItemStack> createIconBar (int flags)
		{
			List<ItemStack> bar = new ArrayList<ItemStack>();
			for (int i = 0; i < 9; ++i)
			{
				bar.add(i, GUIContainer.getBlank());
			}
			if ( (PREV & flags) > 0)
			{
				bar.set(0, GUIContainer.getFunctionalItem(Material.PAPER, "<-", "Zurückblättern"));
			}
			if ( (UP & flags) > 0)
			{
				bar.set(3, GUIContainer.getFunctionalItem(Material.PAPER, "Nach oben", "Zum übergeordneten Menü"));
			}
			if ( (CLOSE & flags) > 0)
			{
				bar.set(5, GUIContainer.getFunctionalItem(Material.BARRIER, "Schließen", "---------"));
			}
			if ( (NEXT & flags) > 0)
			{
				bar.set(8, GUIContainer.getFunctionalItem(Material.PAPER, "->", "Weiterblättern"));
			}
			return bar;
		}
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
