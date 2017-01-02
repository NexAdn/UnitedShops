/* UnitedShops - A Bukkit 1.8 plugin for shop menus.
    Copyright (C) 2015 Adrian Schollmeyer

    This program is free software: you can redistribute it and/or modify
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

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;

public class ShopObject {
	ItemStack itemstack;			// The ItemStack
	Inventory buysellgui;			// GUI zum Kauf/Verkauf
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
		//this.itemstack = new ItemStack(material, 1);
		Material m = Material.COBBLESTONE;
		this.itemstack = new ItemStack(m);
		UnitedShops.plugin.log(Level.INFO, "New ItemStack: " + this.itemstack.getType().toString());
		if( this.itemstack == null )
			UnitedShops.plugin.log(Level.INFO, "ItemStack is null");
		this.pricebuy = buy;
		this.pricesell = sell;
		if( !(this.pricebuy >= this.pricesell) ) 
		{
			this.pricesell = this.pricebuy/3;
		}
	}
	public ShopObject()
	{
		this.nobuy = true;
	}
	
	public void init()
	{
		UnitedShops.plugin.log(Level.INFO, "Shop Object: " + this.itemstack.getType().toString());
		this.buysellgui = Bukkit.createInventory(null, 9, this.itemstack.getType().toString());
		for( int i=0; i<9; i++ )
		{
			ItemStack it;
			switch(i)
			{
			case 0:
				this.buysellgui.setItem(i, GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 1", "ev-b-1"));
				break;
			case 1:
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 10", "ev-b-10");
				it.setAmount(10);
				this.buysellgui.setItem(i, it);
				break;
			case 2:
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 64", "ev-b-64");
				it.setAmount(64);
				this.buysellgui.setItem(i, it);
				break;
			case 3:
				this.buysellgui.setItem(i, new ItemStack(Material.THIN_GLASS));
				break;
			case 4:
				it = GUIContainer.getFunctionalItem(Material.BARRIER, "Zurück", "ev-iback");
				it.setAmount(1);
				this.buysellgui.setItem(i, it);
				break;
			case 5:
				this.buysellgui.setItem(i, new ItemStack(Material.THIN_GLASS));
				break;
			case 6:
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 1", "ev-s-1");
				it.setAmount(1);
				this.buysellgui.setItem(i, it);
				break;
			case 7:
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 10", "ev-s-10");
				it.setAmount(10);
				this.buysellgui.setItem(i, it);
				break;
			case 8:
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 64", "ev-s-64");
				it.setAmount(64);
				this.buysellgui.setItem(i, it);
				break;
			}
		}
	}
	
	// getters/setters
	public ItemStack getItem() { return this.itemstack; }
	public double getBuy() { return this.pricebuy; }
	public double getSell() { return this.pricesell; }
	public boolean notBuyable() { return this.nobuy; }
	public Inventory getBuySellGui() { return this.buysellgui; }
}
