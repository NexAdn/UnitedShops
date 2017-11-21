package io.github.nexadn.unitedshops.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.shop.GUIContainer;
import io.github.nexadn.unitedshops.shop.ShopInventory;

public class TestGui implements CommandExecutor {
    private Pager               pager;
    private List<ShopInventory> items;

    public TestGui()
    {
        this.items = new ArrayList<ShopInventory>();
        this.items.clear();
        for (int i = 0; i < 32; ++i)
        {
            ItemStack stack = GUIContainer.getFunctionalItem(Material.getMaterial(i), "bleey", "BLEEY");
            this.items.add(new ShopInventory(Integer.toString(i), stack, i));
        }

        pager = new Pager(this.items,
                Pager.MenuButton.CLOSE | Pager.MenuButton.NEXT | Pager.MenuButton.PREV | Pager.MenuButton.UP, "Menu", 2,
                Bukkit.createInventory(null, 9));
    }

    public boolean onCommand (CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player && (sender.hasPermission("ushop.admin") || sender.isOp()))
        {
            Player player = (Player) sender;
            player.openInventory(this.pager.getFirstInventory());
            return true;
        }
        return false;
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
