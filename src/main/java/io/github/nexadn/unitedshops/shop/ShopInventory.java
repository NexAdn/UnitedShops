package io.github.nexadn.unitedshops.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.ui.Pager;
import io.github.nexadn.unitedshops.ui.PagerItem;

public class ShopInventory implements PagerItem {
    private Inventory        inv;
    private ItemStack        icon;
    private int              order;
    private String           title;
    private List<ShopObject> content;
    private Pager            pager;
    private final int        menuButtons = Pager.MenuButton.PREV | Pager.MenuButton.NEXT | Pager.MenuButton.CLOSE
            | Pager.MenuButton.UP;

    public ShopInventory()
    {
        this.order = 0;
        this.title = "null";
        this.icon = new ItemStack(Material.BARRIER);
        this.content = new ArrayList<>();
    }

    public ShopInventory(String title, ItemStack icon, int id)
    {
        this.icon = icon;
        this.title = title;
        this.order = id;
        this.content = new ArrayList<>();
    }

    public void initInventory ()
    {
        for (ShopObject o : this.content)
        {
            o.init();
        }
        this.pager = new Pager(this.content, this.menuButtons, this.title);
        for (ShopObject o : this.content)
        {
            o.setParent(this.pager.getFirstInventory());
        }

        ItemMeta im = this.icon.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + this.title);
        this.icon.setItemMeta(im);
    }

    public Pager getPager ()
    {
        return this.pager;
    }

    public boolean handleTrades (int index, Player player, boolean isSell, int amount)
    {
        ShopObject tmp = this.content.get(index);
        ItemStack item = tmp.getItem();
        item.setAmount(amount);
        double buy1 = tmp.getBuy();
        double sell1 = tmp.getSell();

        if (!isSell)
        {
            // Buy items
            return UnitedShops.plugin.getTradeManager().tradeItemForMoney(player, item, buy1 * amount);
        } else if (isSell)
        {
            // Sell items
            return UnitedShops.plugin.getTradeManager().tradeMoneyForItem(player, sell1 * amount, item);
        }

        return false;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public void setParent (Inventory parent)
    {
        this.pager.setParent(parent);
    }

    public void addContent (ShopObject object)
    {
        this.content.add(object);
    }

    public void setContent (List<ShopObject> contents)
    {
        this.content = contents;
    }

    public void setIcon (Material icon)
    {
        this.icon = new ItemStack(icon, 1);
    }

    public String getTitle ()
    {
        return this.title;
    }

    public int getOrderNumber ()
    {
        return this.order;
    }

    @Override
    public ItemStack getIcon ()
    {
        return this.icon;
    }

    public Inventory getInventory ()
    {
        return this.pager.getFirstInventory();
    }

    public List<ShopObject> getShopObjects ()
    {
        return this.content;
    }

    public List<Inventory> getGuisBuySell ()
    {
        List<Inventory> gui = new ArrayList<>();
        for (ShopObject o : this.content)
        {
            gui.add(o.getBuySellGui());
        }
        return gui;
    }

    @Override
    public void call (InventoryClickEvent e)
    {
        if (this.pager.getFirstInventory() == null)
        {
            UnitedShops.plugin.log(Level.SEVERE, "Unitialized ShopInventory!");
        }
        e.getWhoClicked().openInventory(this.pager.getFirstInventory());
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
