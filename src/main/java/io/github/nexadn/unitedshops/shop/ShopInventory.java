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
	Inventory inv;				// Inventory Holder
	ItemStack icon;				// Icon item
	@Deprecated
	int order;					// Ordering number
	String title;				// Inventory title
	List<ShopObject> content;	// Inventory contents
	
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
	public ShopInventory(String title, ItemStack icon)
	{
		this.icon = icon;
		this.title = title;
		this.order = 0;
		this.content = new Vector<ShopObject>();
	}
	/** Initialize the Inventory Object and add the contents
	 */
	public void initInventory()
	{
		int size = content.size();
		if(size%9!=0)
		{
			while(size%9!=0)
			{
				size++;
			}
		}
		this.inv = Bukkit.createInventory(null, size, this.title);
		for(int i=0; i<size; ++i)
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
	 * @return Is the trade successful
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
	
	public int getOrderNumber() { return this.order; }
	public ItemStack getIcon() { return this.icon; }
	public Inventory getInventory() { return this.inv; }
	public List<Inventory> getGuisBuySell() {
		List<Inventory> gui = new Vector<Inventory>();
		for( ShopObject o:this.content ) {
			gui.add(o.getBuySellGui());
		}
		return gui;
	}
}
