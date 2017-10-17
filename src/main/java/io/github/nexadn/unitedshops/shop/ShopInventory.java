/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

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
package io.github.nexadn.unitedshops.shop;

import java.util.List;
import java.util.Vector;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.tradeapi.MoneyTrade;

/** Container for an inventory with ShopObjects
 * @author NexAdn
 */
public class ShopInventory {
	private Inventory inv;				// Inventory Holder
	private ItemStack icon;				// Icon item
	private int order;					// Ordering number
	private String title;				// Inventory title
	private List<ShopObject> content;	// Inventory contents
	
	/** Create the Object with null data
	 */
	public ShopInventory()
	{
		this.order = 0;
		this.title = "null";
		this.content = new Vector<ShopObject>();
	}
	/** Create the Object with title and icon
	 */
	public ShopInventory(String title, ItemStack icon, int id)
	{
		this.icon = icon;
		this.title = title;
		this.order = id;
		this.content = new Vector<ShopObject>();
	}
	/** Initialize the Inventory Object and add the contents
	 */
	public void initInventory()
	{
		for(ShopObject o:this.content)
		{
			o.init();
		}
		int size = content.size();
		size += 9-(size%9);
		this.inv = Bukkit.createInventory(null, size, this.title);
		for(int i=0; i<content.size(); i++)
		{
			try {
				inv.setItem(i, this.content.get(i).getItem());
			}
			catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
			// Stop if the maximum number of objects per inventory is reached
			if(i==45)
			{
				break;
			}
		}
		
		this.icon.getItemMeta().setDisplayName(this.title);
	}
	
	/** Handles the trades of ShopObjects
	 * @param index - The item index
	 * @param player - The player
	 * @param isSell - Whether the item ist bought (false) or sold (true)
	 * @param amount - The amount of items
	 * @return true, if the trade was successful, false, if not.
	 */
	public boolean handleTrades(int index, Player player, boolean isSell, int amount)
	{
		ShopObject tmp = this.content.get(index);
		ItemStack item = tmp.getItem();
		item.setAmount(amount);
		double buy1 = tmp.getBuy();
		double sell1 = tmp.getSell();
		
		if( !isSell )
		{
			// Buy items
			return MoneyTrade.tradeItemForMoney(player, item, buy1*amount);
		} else if ( isSell )
		{
			// Sell items
			return MoneyTrade.tradeMoneyForItem(player, sell1*amount, item);
		}
		
		return false;
	}
	
	public void setTitle(String title) { this.title = title; }
	public void addContent(ShopObject object) { this.content.add(object); }
	public void setContent(List<ShopObject> contents) { this.content = contents; }
	public void setIcon(Material icon) { this.icon = new ItemStack(icon, 1); }
	
	public String getTitle() { return this.title; }
	public int getOrderNumber() { return this.order; }
	public ItemStack getIcon() { return this.icon; }
	public Inventory getInventory() { return this.inv; }
	public List<ShopObject> getShopObjects() { return this.content; }
	public List<Inventory> getGuisBuySell() {
		List<Inventory> gui = new Vector<Inventory>();
		for( ShopObject o:this.content ) {
			gui.add(o.getBuySellGui());
		}
		return gui;
	}
}
