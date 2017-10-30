package io.github.nexadn.unitedshops.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.events.GUIClick;

public class Pager implements Listener {
	private List<PagerItem> 	items;
	private List<Inventory>		uiInventorys;
	private String				title;
	private Inventory			parent;
	private int 				extraButtonSettings;
	private int					rowsPerPage;
	private final int			extraRowSlots = 9; // Slots reserved for extra Buttons (add 9 per row, only increment if needed) 
	
	public Pager (List<PagerItem> pagerItems, int extraButtons, String title)
	{
		this.items = pagerItems;
		this.setExtraButtonSettings(extraButtons);
		this.rowsPerPage = 3;
		this.parent = null;
		this.title = title;
		init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}
	
	public Pager (List<PagerItem> pagerItems, int extraButtons, String title, Inventory parent)
	{
		this.items = pagerItems;
		this.setExtraButtonSettings(extraButtons);
		this.rowsPerPage = 3;
		this.parent = parent;
		this.title = title;
		init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}
	
	public Pager (List<PagerItem> pagerItems, int extraButtons, String title, int rowsPerPage)
	{
		this.items = pagerItems;
		this.setExtraButtonSettings(extraButtons);
		this.rowsPerPage = rowsPerPage;
		this.parent = null;
		this.title = title;
		init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}
	
	public Pager (List<PagerItem> pagerItems, int extraButtons, String title, int rowsPerPage, Inventory parent)
	{
		this.items = pagerItems;
		this.setExtraButtonSettings(extraButtons);
		this.rowsPerPage = rowsPerPage;
		this.parent = parent;
		this.title = title;
		init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}
	
	public void init()
	{
		int inventoryCount = Math.floorDiv(this.items.size(), 9*this.rowsPerPage) + 1;
		if (this.uiInventorys == null)
			this.uiInventorys = new ArrayList<Inventory>();
		this.uiInventorys.clear();
		for (int i = 0; i < inventoryCount; ++i)
		{
			this.uiInventorys.add(Bukkit.createInventory(null, 9*this.rowsPerPage + this.extraRowSlots, this.title));
		}
		for (int i = 0; i < this.items.size(); ++i)
		{
			int page = Math.floorDiv(i*this.rowsPerPage, 9);
			this.uiInventorys.get( page ).setItem( i - page*9*this.rowsPerPage, this.items.get(i).getIcon());
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
				int row = (event.getSlot() - event.getSlot()%9) / 9; 
				if (row >= this.rowsPerPage)
				{
					// extra buttons
					int col = event.getSlot() % 9;
					int button = 1 << col;
					if ((button & ExtraButton.PREV) > 0)
					{
						// Previous
						event.getWhoClicked().openInventory(this.uiInventorys.get(this.uiInventorys.lastIndexOf(i)-1));
					} else if ((button & ExtraButton.UP) > 0)
					{
						// Up
						if (this.parent != null)
						{
							event.getWhoClicked().openInventory(this.parent);
						}
					} else if ((button & ExtraButton.CLOSE) > 0)
					{
						// Close
						event.getWhoClicked().closeInventory();
					} else if ((button & ExtraButton.NEXT) > 0)
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
					callItem (slot, event);
				}
			}
		}
	}
	
	public int getExtraButtonSettings() {
		return extraButtonSettings;
	}

	public void setExtraButtonSettings(int extraButtonSettings) {
		this.extraButtonSettings = extraButtonSettings;
		init();
	}

	/** Flags for the extraButton */
	public class ExtraButton {
		// 1 << slotnummer → Flag
		final public static int		PREV	= 0x01; // 0
		final public static int		UP		= 0x08; // 3
		final public static int		CLOSE	= 0x20; // 5
		final public static int		NEXT	= 0x0100; // 8
	}
}
