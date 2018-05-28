package io.github.nexadn.unitedshops.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        this(material, (short) 0, buy);
    }

    public ShopObject(Material material, short damage, double buy)
    {
        this.itemstack = new ItemStack(material, 1, damage);
        this.pricebuy = buy;
        this.pricesell = buy / 3;
    }

    public ShopObject(Material material, double buy, double sell)
    {
        this(material, (short) 0, buy, sell);
    }

    public ShopObject(Material material, short damage, double buy, double sell)
    {
        this.itemstack = new ItemStack(material, 1, damage);
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
            switch (i) {
            case 0: // Kauf 1
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("buyAmount") + "1",
                        UnitedShops.plugin.getMessage("price") + this.pricebuy * 1);
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 1: // Kauf 16
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("buyAmount") + "16",
                        UnitedShops.plugin.getMessage("price") + this.pricebuy * 16);
                it.setAmount(16);
                this.buysellgui.setItem(i, it);
                break;
            case 2: // Kauf 64
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("buyAmount") + "64",
                        UnitedShops.plugin.getMessage("price") + this.pricebuy * 64);
                it.setAmount(64);
                this.buysellgui.setItem(i, it);
                break;
            case 3: // Blank
                this.buysellgui.setItem(i, GUIContainer.getBlank());
                break;
            case 4: // Zurück
                it = GUIContainer.getFunctionalItem(Material.BARRIER, UnitedShops.plugin.getMessage("back"));
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 5: // Blank
                this.buysellgui.setItem(i, GUIContainer.getBlank());
                break;
            case 6: // Verkauf 1
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("sellAmount") + "1",
                        UnitedShops.plugin.getMessage("price") + this.pricesell * 1);
                it.setAmount(1);
                this.buysellgui.setItem(i, it);
                break;
            case 7: // Verkauf 16
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("sellAmount") + "16",
                        UnitedShops.plugin.getMessage("price") + this.pricesell * 16);
                it.setAmount(16);
                this.buysellgui.setItem(i, it);
                break;
            case 8: // Verkauf 64
                it = GUIContainer.getFunctionalItem(this.itemstack.getType(), this.itemstack.getDurability(),
                        UnitedShops.plugin.getMessage("sellAmount") + "64",
                        UnitedShops.plugin.getMessage("price") + this.pricesell * 64);
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

    @Override
    public void call (InventoryClickEvent e)
    {
        e.getWhoClicked().openInventory(this.buysellgui);
    }

    @Override
    public ItemStack getIcon ()
    {
        return new ItemStack(this.itemstack.getType(), 1, this.itemstack.getDurability());
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
