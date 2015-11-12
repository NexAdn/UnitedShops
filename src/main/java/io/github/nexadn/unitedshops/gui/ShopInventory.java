package io.github.nexadn.unitedshops.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ShopInventory {
	Inventory inv;				// Inventory Holder
	int order;					// Ordering number
	String title;				// Inventory title
	List<ShopObject> content;	// Inventory contents
	
	public ShopInventory()
	{
		this.order = 0;
		this.title = "null";
	}
	public void initInventory()
	{
		int size = content.size();
		if(size%9!=0)
		{
			content.clear();
			size = 9;
		}
		Bukkit.createInventory(null, size, this.title);
		for(int i=0; i<size; i++)
		{
			if(i%45==0)
			{
				
			}
		}
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	public void addContent(ShopObject object)
	{
		this.content.add(object);
	}
	public void setContent(List<ShopObject> contents)
	{
		this.content = contents;
	}
}
