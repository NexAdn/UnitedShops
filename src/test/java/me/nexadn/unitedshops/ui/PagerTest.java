package me.nexadn.unitedshops.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
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
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import me.nexadn.unitedshops.Util;
import me.nexadn.unitedshops.testutil.TestUtil;

@RunWith(PowerMockRunner.class)
public class PagerTest {

    @Test
    public void test() {
        TestUtil.init();

        List<PagerItem> menuItems = PowerMockito.spy(new ArrayList<>());
        for (int i = 0; i < 19; i++) {
            menuItems.add(PowerMockito.spy(new PagerItem() {

                @Override
                public ItemStack getIcon() {
                    return Util.getBlank();
                }

                @Override
                public void call(InventoryClickEvent e) {
                    TestUtil.getPlugin().logInfo("Gotcha");
                }
            }));
        }

        Pager testPager = PowerMockito.spy(new Pager(TestUtil.getPlugin(), menuItems, "Pager Test", 1));
        assertEquals(3, testPager.getInventoryCount());

        Inventory inventory = Bukkit.createInventory(null, 9, "Mock Inventory");
        Player mockPlayer = PowerMockito.mock(Player.class);
        InventoryView mockView = PowerMockito.spy(new InventoryView() {

            @Override
            public InventoryType getType() {
                return InventoryType.CHEST;
            }

            @Override
            public Inventory getTopInventory() {
                return testPager.getFirstInventory();
            }

            @Override
            public HumanEntity getPlayer() {
                return mockPlayer;
            }

            @Override
            public Inventory getBottomInventory() {
                return inventory;
            }
        });

        InventoryClickEvent eventNormal = new InventoryClickEvent(mockView, SlotType.CONTAINER, 4, ClickType.LEFT,
                InventoryAction.PICKUP_ONE);
        InventoryClickEvent eventMenuButton = new InventoryClickEvent(mockView, SlotType.CONTAINER, 17, ClickType.LEFT,
                InventoryAction.PICKUP_ONE);

        testPager.onInventoryClick(eventNormal);
        testPager.onInventoryClick(eventMenuButton);

        verify(testPager).onInventoryClick(eventMenuButton);
        verify(menuItems.get(4)).call(eventNormal);

        verify(testPager).onInventoryClick(eventMenuButton);
        verify(mockPlayer).openInventory(testPager.getInventorys().get(1));
    }
}
