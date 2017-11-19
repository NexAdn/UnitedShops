package io.github.nexadn.unitedshops.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.shop.AutoSellManager;

public class OnInventoryClose implements Listener {
    @EventHandler
    public void onInventoryClick (InventoryCloseEvent event)
    {
        Player ep = (Player) event.getPlayer();
        OfflinePlayer op = (OfflinePlayer) ep;
        if (UnitedShops.plugin.hasAutoSellManager(op))
        {
            AutoSellManager m = UnitedShops.plugin.getAutoSellManager(op);
            if (event.getInventory().equals(m.getInventory()))
            {
                m.fetchInventory();
            }
        }
    }
}

/*
 * Copyright (C) 2017 Adrian Schollmeyer
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