package io.github.nexadn.unitedshops.shop;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopInventory {
	Inventory inv;				// Inventory Holder
	int order;					// Ordering number
	String title;				// Inventory title
	List<ShopObject> content;	// Inventory contents
	
	// Initialize the Object with null data
	public ShopInventory()
	{
		this.order = 0;
		this.title = "null";
	}
	// Initialize the Inventory Object and add the contents
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
		for(int i=0; i<size; i++)
		{
			inv.setItem(i, this.content.get(i).getItem());
		}
	}
	
	public boolean handleTrades(int index, Player player)
	{
		ShopObject tmp = this.content.get(index);
		double buy1 = tmp.getBuy();
		double buy10 = buy1*10;
		double buy64 = buy1*64;
		
		double sell1 = tmp.getSell();
		double sell10 = sell1*10;
		double sell64 = sell1*64;
		
		
		
		return false;
	}
	
	public void setTitle(String title) { this.title = title; }
	public void addContent(ShopObject object) { this.content.add(object); }
	public void setContent(List<ShopObject> contents) { this.content = contents; }
	
	public int getOrderNumber() { return this.order; }
}
