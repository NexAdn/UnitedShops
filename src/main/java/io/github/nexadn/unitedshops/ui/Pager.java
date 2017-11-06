package io.github.nexadn.unitedshops.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.nexadn.unitedshops.UnitedShops;

public class Pager implements Listener {
	private List<PagerItem>	items;
	private List<Inventory>	uiInventorys;
	private String			title;
	private Inventory		parent;
	private int				menuButtonSettings;
	private int				rowsPerPage;
	/** Slots reserved for menu Buttons (add 9 per row, only increment if needed) */
	private final int		menuRowSlots	= 9;

	public Pager(List<PagerItem> pagerItems, int menuButtons, String title)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = 3;
		this.parent = null;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<PagerItem> pagerItems, int menuButtons, String title, Inventory parent)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = 3;
		this.parent = parent;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<PagerItem> pagerItems, int menuButtons, String title, int rowsPerPage)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = rowsPerPage;
		this.parent = null;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public Pager(List<PagerItem> pagerItems, int menuButtons, String title, int rowsPerPage, Inventory parent)
	{
		this.items = pagerItems;
		this.menuButtonSettings = menuButtons;
		this.rowsPerPage = rowsPerPage;
		this.parent = parent;
		this.title = title;
		this.init();
		UnitedShops.plugin.getServer().getPluginManager().registerEvents(this, UnitedShops.plugin);
	}

	public void init ()
	{
		try
		{
			int inventoryCount = (int) (Math.floor(this.items.size() / 9 * this.rowsPerPage) + 1);
			if (this.uiInventorys == null)
				this.uiInventorys = new ArrayList<Inventory>();
			this.uiInventorys.clear();
			for (int i = 0; i < inventoryCount; ++i)
			{
				this.uiInventorys
						.add(Bukkit.createInventory(null, 9 * this.rowsPerPage + this.menuRowSlots, this.title));
			}
			for (int i = 0; i < this.items.size(); ++i)
			{
				int page = Math.floorDiv(i * this.rowsPerPage, 9);
				this.uiInventorys.get(page).setItem(i - page * 9 * this.rowsPerPage, this.items.get(i).getIcon());
			}
		} catch (ArithmeticException e)
		{
			UnitedShops.plugin.log(Level.SEVERE, "Division by zero!");
			e.printStackTrace();
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
					if ( (button & MenuButton.PREV) > 0)
					{
						// Previous
						event.getWhoClicked()
								.openInventory(this.uiInventorys.get(this.uiInventorys.lastIndexOf(i) - 1));
					} else if ( (button & MenuButton.UP) > 0)
					{
						// Up
						if (this.parent != null)
						{
							event.getWhoClicked().openInventory(this.parent);
						}
					} else if ( (button & MenuButton.CLOSE) > 0)
					{
						// Close
						event.getWhoClicked().closeInventory();
					} else if ( (button & MenuButton.NEXT) > 0)
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
	
	public List<Inventory> getInventorys()
	{
		return this.uiInventorys;
	}

	/** Flags for the menuButton */
	public class MenuButton {
		// 1 << Spalte → Flag
		final public static int	PREV	= 0x01;		// 0
		final public static int	UP		= 0x08;		// 3
		final public static int	CLOSE	= 0x20;		// 5
		final public static int	NEXT	= 0x0100;	// 8
	}
}
