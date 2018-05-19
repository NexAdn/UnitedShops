package io.github.nexadn.unitedshops.usershop;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.shop.GUIContainer;
import io.github.nexadn.unitedshops.ui.PagerItem;

public class Offer implements PagerItem, Listener {

    private Vendor                     owner;
    private Material                   item;
    private int                        itemAmount;
    private Inventory                  supplyInventory;
    private Inventory                  viewInventory;
    private double                     priceBuy;
    private double                     priceSell;
    private int                        mode;
    private HashMap<Player, Inventory> lastParent;

    /**
     * Create a new Offer object and show the creator an inventory to supply the
     * shop offer
     * 
     * @param owner
     *            The owning Vendor
     * @param creator
     *            The creator to whom the supply view shall be shown
     * @param item
     *            The type of item to be sold
     * @param priceBuy
     *            The price on which players can buy items
     * @param priceSell
     *            The price on which players can sell items
     * @param mode
     *            The mode of the offer (1 = buy, 2 = sell, 3 = buy/sell)
     */
    public Offer(Vendor owner, Player creator, Material item, double priceBuy, double priceSell, int mode)
    {
        this.owner = owner;
        this.item = item;
        this.itemAmount = 0;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.mode = mode;
        // TODO messages.yml
        this.supplyInventory = Bukkit.createInventory(null, 9 * 3, "Gelagerte items");
        creator.openInventory(this.supplyInventory);

        this.recreateInventories();
    }

    public void call (InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player)
        {
            this.lastParent.put((Player) e.getWhoClicked(), e.getInventory());
        } else if (e.getWhoClicked() instanceof CommandSender)
        {
            UnitedShops.plugin.sendMessage((CommandSender) e.getWhoClicked(),
                    UnitedShops.plugin.getMessage("playerOnly"));
        }
    }

    public double getBuyPrice ()
    {
        return this.priceBuy;
    }

    public ItemStack getIcon ()
    {
        return new ItemStack(this.item);
    }

    public int getMode ()
    {
        return this.mode;
    }

    public double getSellPrice ()
    {
        return this.priceSell;
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e)
    {
        if (e.getInventory().equals(this.viewInventory))
        {

        } else if (e.getInventory().equals(this.supplyInventory))
        {
            if (! (e.getCurrentItem().getType().equals(this.item)))
            {
                e.setCancelled(true);
            }
        }
    }

    /**
     * Save the offer to config
     * 
     * @param section
     *            Where to save the offer
     * @return The modified {@link ConfigurationSection}
     */
    public ConfigurationSection saveToConfig (ConfigurationSection section)
    {
        section.set("owner", this.owner.getPlayerUUID().toString());
        section.set("item", this.item.toString());
        int supply = 0;
        for (ItemStack itemStack : this.supplyInventory)
        {
            if (itemStack.getType().equals(this.item))
                supply += itemStack.getAmount();
        }
        section.set("supply", supply);
        section.set("priceBuy", this.priceBuy);
        section.set("priceSell", this.priceSell);
        section.set("mode", this.mode);

        return section;
    }

    private void recreateInventories ()
    {
        this.updateSupply();

        // TODO messages yml
        this.supplyInventory = Bukkit.createInventory(null, 9 * 3, "Gelagerte Items");

        int amount = this.itemAmount;
        while (amount > 0)
        {
            // TODO detect max stack size
            ItemStack stack = new ItemStack(this.item, amount > 64 ? 64 : amount);
            amount -= 64;
            this.supplyInventory.addItem(stack);
        }

        this.viewInventory = Bukkit.createInventory(null, 9 * 5,
                this.item.toString() + " – " + this.owner.getPlayer().getDisplayName());

        for (int i = 27; i < 36; i++)
        {
            this.viewInventory.setItem(i, GUIContainer.getItem(Material.BARRIER, ""));
        }

        for (int i = 36; i < 45; i++)
        {
            ItemStack it;
            switch (i) {
            case 36: // Buy 1
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("buyAmount") + "1",
                        UnitedShops.plugin.getMessage("price") + this.priceBuy * 1);
                it.setAmount(1);
                break;

            case 37: // Buy 10
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("buyAmount") + "10",
                        UnitedShops.plugin.getMessage("price") + this.priceBuy * 10);
                it.setAmount(10);
                break;

            case 38: // Buy 64
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("buyAmount") + "64",
                        UnitedShops.plugin.getMessage("price") + this.priceBuy * 64);
                it.setAmount(64);
                break;

            case 42: // Sell 1
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("sellAmount") + "1",
                        UnitedShops.plugin.getMessage("price") + this.priceSell * 1);
                it.setAmount(1);
                break;

            case 43: // Sell 10
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("sellAmount") + "10",
                        UnitedShops.plugin.getMessage("price") + this.priceSell * 10);
                it.setAmount(10);
                break;

            case 44: // Sell 64
                it = GUIContainer.getFunctionalItem(this.item, UnitedShops.plugin.getMessage("sellAmount") + "64",
                        UnitedShops.plugin.getMessage("price") + this.priceSell * 64);
                it.setAmount(64);
                break;

            case 40: // Back
                it = GUIContainer.getFunctionalItem(Material.BARRIER, UnitedShops.plugin.getMessage("back"));
                break;

            default: // 39, 41
                it = GUIContainer.getBlank();
                break;
            }
            this.viewInventory.setItem(i, it);
        }
    }

    private void updateSupply ()
    {
        int amount = 0;
        for (ItemStack stack : this.supplyInventory.getContents())
        {
            if (stack.getType() != this.item)
            {
                // FIXME DELETE ITEM; TO BE REPLACED BY SOME NICER CLEANUP
                UnitedShops.plugin.getTradeManager().removeItems(this.supplyInventory, stack);
            } else
            {
                amount += stack.getAmount();
            }
        }
        this.itemAmount = amount;
    }
}

/*
 * Copyright (C) 2017, 2018 Adrian Schollmeyer
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