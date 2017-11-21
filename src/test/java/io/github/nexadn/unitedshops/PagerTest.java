package io.github.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.nexadn.unitedshops.ui.Pager;
import io.github.nexadn.unitedshops.ui.PagerItem;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({ JavaPluginLoader.class, PluginDescriptionFile.class })
public class PagerTest {

    public static Inventory mockInventory;
    public static Pager     testPager;
    public static Player    mockPlayer;

    @Test
    public void testPager ()
    {
        TestUtil.init();

        List<PagerItem> menuItems = new ArrayList<PagerItem>();
        for (int i = 0; i < 19; ++i)
        {
            menuItems.add(PowerMockito.spy(new PagerTestItem(i)));
        }

        int menuButtonFlags = Pager.MenuButton.PREV | Pager.MenuButton.CLOSE | Pager.MenuButton.NEXT
                | Pager.MenuButton.UP;
        testPager = PowerMockito.spy(new Pager(menuItems, menuButtonFlags, "Pager Test", 1));
        assertEquals(3, testPager.getInventoryCount());
        assertEquals(3, Pager.calculateInventoryCount(19, 1));
        assertEquals(1, Pager.calculateInventoryCount(3, 1));
        assertEquals(1, Pager.calculateInventoryCount(9, 1));
        assertEquals(2, Pager.calculateInventoryCount(10, 1));
        assertEquals(4, Pager.calculateInventoryCount(35, 1));
        assertEquals(5, Pager.calculateInventoryCount(37, 1));

        mockInventory = PowerMockito.spy(Bukkit.createInventory(null, 9, "Mock Inventory"));

        mockPlayer = PowerMockito.mock(Player.class);
        InventoryView mockView = PowerMockito.spy(new InventoryView() {

            @Override
            public InventoryType getType ()
            {
                return InventoryType.CHEST;
            }

            @Override
            public Inventory getTopInventory ()
            {
                return testPager.getInventorys().get(1);
            }

            @Override
            public HumanEntity getPlayer ()
            {
                return mockPlayer;
            }

            @Override
            public Inventory getBottomInventory ()
            {
                return PagerTest.mockInventory;
            }
        });

        InventoryClickEvent mockEventNormal = PowerMockito.spy(
                new InventoryClickEvent(mockView, SlotType.CONTAINER, 4, ClickType.LEFT, InventoryAction.PICKUP_ONE));
        InventoryClickEvent mockEventMenuButton = PowerMockito.spy(
                new InventoryClickEvent(mockView, SlotType.CONTAINER, 9, ClickType.LEFT, InventoryAction.PICKUP_ONE));

        testPager.onInventoryClick(mockEventNormal);
        testPager.onInventoryClick(mockEventMenuButton);

        verify(testPager).onInventoryClick(mockEventNormal);
        verify(menuItems.get(13)).call(mockEventNormal);

        verify(testPager).onInventoryClick(mockEventMenuButton);
        verify(mockPlayer).openInventory(testPager.getInventorys().get(0));
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
