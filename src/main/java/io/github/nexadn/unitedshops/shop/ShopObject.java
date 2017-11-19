package io.github.nexadn.unitedshops.shop;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.ui.PagerItem;

public class ShopObject implements PagerItem {
    ItemStack itemstack;
    Inventory buysellgui;
    Inventory parentShop;
    double    pricebuy;
    double    pricesell;
    boolean   nobuy = false;

    public ShopObject(Material material, double buy)
    {
        this.itemstack = new ItemStack(material, 1);
        this.pricebuy = buy;
        this.pricesell = buy / 3;
    }

    public ShopObject(Material material, double buy, double sell)
    {
        this.itemstack = new ItemStack(material, 1);
        this.pricebuy = buy;
        this.pricesell = sell;
        if (! (this.pricebuy >= this.pricesell))
        {
            this.pricesell = this.pricebuy / 3;
        }
    }

    public ShopObject()
    {
        this.nobuy = true;
    }

    public void init ()
    {
        this.buysellgui = Bukkit.createInventory(null, 9, this.itemstack.getType().toString());
        for (int i = 0; i < 9; i++)
        {
            ItemStack it;
            ItemMeta im;
            List<String> il;
            switch (i) {
            case 0: // Kauf 1
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("buyAmount") + "1", "ev-b-1");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricebuy * 1);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 1: // Kauf 16
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("buyAmount") + "16", "ev-b-16");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricebuy * 16);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(16);
                this.buysellgui.setItem(i, it);
                break;
            case 2: // Kauf 64
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("buyAmount") + "64", "ev-b-64");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricebuy * 64);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(64);
                this.buysellgui.setItem(i, it);
                break;
            case 3: // Blank
                this.buysellgui.setItem(i, GUIContainer.getBlank());
                break;
            case 4: // Zurück
                it = GUIContainer.getFunctionalItem(Material.BARRIER, UnitedShops.plugin.getMessage("back"),
                        "ev-iback");
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 5: // Blank
                this.buysellgui.setItem(i, GUIContainer.getBlank());
                break;
            case 6: // Verkauf 1
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("sellAmount") + "1", "ev-s-1");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricesell * 1);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 7: // Verkauf 16
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("sellAmount") + "16", "ev-s-16");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricesell * 16);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(16);
                this.buysellgui.setItem(i, it);
                break;
            case 8: // Verkauf 64
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(),
                        UnitedShops.plugin.getMessage("sellAmount") + "64", "ev-s-64");
                im = it.getItemMeta();
                il = im.getLore();
                il.add(UnitedShops.plugin.getMessage("price") + this.pricesell * 64);
                im.setLore(il);
                it.setItemMeta(im);
                it.setAmount(64);
                this.buysellgui.setItem(i, it);
            }
        }
    }

    public ItemStack getItem ()
    {
        return this.itemstack;
    }

    public double getBuy ()
    {
        return this.pricebuy;
    }

    public double getSell ()
    {
        return this.pricesell;
    }

    public boolean notBuyable ()
    {
        return this.nobuy;
    }

    public Inventory getBuySellGui ()
    {
        return this.buysellgui;
    }

    public Inventory getParent ()
    {
        return this.parentShop;
    }

    public void call (InventoryClickEvent e)
    {
        e.getWhoClicked().openInventory(this.buysellgui);
    }

    public ItemStack getIcon ()
    {
        return new ItemStack(this.itemstack.getType());
    }

    public void setParent (Inventory inventory)
    {
        this.parentShop = inventory;
    }
}

/*
 * Copyright (C) 2015, 2016, 2017 Adrian Schollmeyer
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
