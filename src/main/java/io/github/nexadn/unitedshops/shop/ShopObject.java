package io.github.nexadn.unitedshops.shop;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		this.itemstack = new ItemStack(material, 1);
		//this.itemstack = new ItemStack(m);
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
		this.buysellgui = Bukkit.createInventory(null, 9, this.itemstack.getType().toString());
		for( int i=0; i<9; i++ )
		{
			ItemStack it;
			ItemMeta im;
			List<String> il;
			switch(i)
			{
			case 0: // Kauf 1
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 1", "ev-b-1");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricebuy*1);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(1);
				this.buysellgui.setItem(i, it);
				break;
			case 1: // Kauf 10
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 10", "ev-b-10");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricebuy*10);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(10);
				this.buysellgui.setItem(i, it);
				break;
			case 2: // Kauf 64
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Kaufe 64", "ev-b-64");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricebuy*10);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(64);
				this.buysellgui.setItem(i, it);
				break;
			case 3: // Blank
				//this.buysellgui.setItem(i, new ItemStack(Material.THIN_GLASS));
				this.buysellgui.setItem(i, GUIContainer.getBlank());
				break;
			case 4: // Zurück
				it = GUIContainer.getFunctionalItem(Material.BARRIER, "Zurück", "ev-iback");
				it.setAmount(1);
				this.buysellgui.setItem(i, it);
				break;
			case 5: // Blank
				this.buysellgui.setItem(i, GUIContainer.getBlank());
				break;
			case 6: // Verkauf 1
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 1", "ev-s-1");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricesell*1);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(1);
				this.buysellgui.setItem(i, it);
				break;
			case 7: // Verkauf 10
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 10", "ev-s-10");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricesell*10);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(10);
				this.buysellgui.setItem(i, it);
				break;
			case 8: // Verkauf 64
				it = GUIContainer.getFunctionalItem(this.itemstack.getType(), "Verkaufe 64", "ev-s-64");
				im = it.getItemMeta();
				il = im.getLore();
				il.add("Preis: " + this.pricesell*64);
				im.setLore(il);
				it.setItemMeta(im);
				it.setAmount(64);
				this.buysellgui.setItem(i, it);
			}
		}
	}
	
	public ItemStack getItem() { return this.itemstack; }
	public double getBuy() { return this.pricebuy; }
	public double getSell() { return this.pricesell; }
	public boolean notBuyable() { return this.nobuy; }
	public Inventory getBuySellGui() { return this.buysellgui; }
}

/*  Copyright (C) 2015, 2016, 2017 Adrian Schollmeyer

This file is part of UnitedShops.

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
