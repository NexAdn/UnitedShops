package io.github.nexadn.unitedshops.tradeapi;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class TradeManager {

    private Economy     economy;
    private UnitedShops plugin;

    public TradeManager(UnitedShops plugin, Economy economy)
    {
        this.economy = economy;
        this.plugin = plugin;
    }

    /**
     * Buy items
     */
    public boolean tradeItemForMoney (Player player, ItemStack offer, double want)
    {
        EconomyResponse eReturn = null;
        double bal = this.economy.getBalance(player);
        if (want > bal)
        {
            return false;
        } else
        {
            eReturn = this.economy.withdrawPlayer(player, want);
            if (eReturn.transactionSuccess())
            {
                player.getInventory().addItem(offer);
                this.plugin.sendMessage(player, UnitedShops.plugin.getMessage("tradePrefix")
                        + offer.getType().toString() + " x" + offer.getAmount() + " -> $" + want);
                return true;
            }
        }
        return false;
    }

    /**
     * Sell items
     */
    public boolean tradeMoneyForItem (Player player, double offer, ItemStack want)
    {
        EconomyResponse eReturn = null;
        Inventory transactionInv = player.getInventory();
        if (transactionInv.containsAtLeast(want, want.getAmount()))
        {
            eReturn = this.economy.depositPlayer(player, offer);
            if (eReturn.transactionSuccess())
            {
                if (!this.removeItems(transactionInv, want))
                {
                    this.economy.withdrawPlayer(player, offer);
                    this.plugin.sendMessage(player, ChatColor.RED + UnitedShops.plugin.getMessage("transactionFailed"));
                    return false;
                }
                this.plugin.sendMessage(player,
                        "Trade: $" + offer + " -> " + want.getType().toString() + " x" + want.getAmount());
                return true;
            }
        } else
        {
            return false;
        }
        return false;
    }

    public boolean removeItems (Inventory inventory, ItemStack items)
    {
        int remaining = items.getAmount();
        for (int i = 0; i < inventory.getSize(); ++i)
        {
            if (inventory.getItem(i) != null && inventory.getItem(i).getType().equals(items.getType()))
            {
                ItemStack is = inventory.getItem(i);
                if (is.getAmount() > remaining)
                {
                    is.setAmount(is.getAmount() - remaining);
                    inventory.setItem(i, is);
                    return true;
                } else
                {
                    remaining -= is.getAmount();
                    inventory.setItem(i, null);
                    if (remaining == 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

/*
 * Copyright (C) 2015-2018 Adrian Schollmeyer
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
