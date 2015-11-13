package io.github.nexadn.unitedshops.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopObject {
	ItemStack itemstack;			// The ItemStack
	double pricebuy;				// price to buy an item
	double pricesell;				// price to sell an item
	boolean nobuy = false;			// for placeholder objects: true
	
	// Constructors
	public ShopObject(Material material, double buy)
	{
		this.itemstack = new ItemStack(material, 1);
		this.pricebuy = buy;
		this.pricesell = buy/3;
	}
	public ShopObject(Material material, double buy, double sell)
	{
		this.itemstack = new ItemStack(material, 1);
		this.pricebuy = buy;
		this.pricesell = sell;
		if(this.pricebuy < this.pricesell/2) {
			this.pricesell = this.pricebuy/3;
		}
	}
	public ShopObject()
	{
		this.nobuy = true;
	}
	
	// getters/setters
	public ItemStack getItem() { return this.itemstack; }
	public double getBuy() { return this.pricebuy; }
	public double getSell() { return this.pricesell; }
	public boolean getNobuy() { return this.nobuy; }
}
