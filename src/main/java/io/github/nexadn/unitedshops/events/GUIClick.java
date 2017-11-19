package io.github.nexadn.unitedshops.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.nexadn.unitedshops.shop.GUIContainer;

public class GUIClick implements Listener {
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event)
    {
        if (GUIContainer.isGuiInventory(event.getInventory()) && (event.getWhoClicked() instanceof Player))
        {
            event.setCancelled(true);
            // Event weiterleiten
            GUIContainer.handleClickEvents(event);
        }
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