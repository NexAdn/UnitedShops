package io.github.nexadn.unitedshops.shop;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import org.mockito.Matchers;
import org.mockito.internal.matchers.Any;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.nexadn.unitedshops.TestTradeManager;
import io.github.nexadn.unitedshops.TestUnitedShops;
import io.github.nexadn.unitedshops.TestUtil;
import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.events.GUIClick;

@RunWith (PowerMockRunner.class)
public class ShopTest {

    Inventory mockInventory;
    Player    mockPlayer;

    @Test
    public void test ()
    {
        TestUtil.init();
        UnitedShops plugin = TestUtil.getPlugin();

        GUIContainer.initGUI();
        
        mockInventory = PowerMockito.spy(Bukkit.createInventory(null, 9));
        mockPlayer = PowerMockito.mock(Player.class);

        InventoryView rootGUIView = PowerMockito.spy(new InventoryView() {

            @Override
            public InventoryType getType ()
            {
                return InventoryType.CHEST;
            }

            @Override
            public Inventory getTopInventory ()
            {
                return GUIContainer.getMenuGui();
            }

            @Override
            public HumanEntity getPlayer ()
            {
                return mockPlayer;
            }

            @Override
            public Inventory getBottomInventory ()
            {
                return mockInventory;
            }
        });

        // Click in shop overview
        InventoryClickEvent eventClickFirstShop = PowerMockito.spy(new InventoryClickEvent(rootGUIView,
                SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ONE));
        InventoryClickEvent eventClickSecondShop = PowerMockito.spy(new InventoryClickEvent(rootGUIView,
                SlotType.CONTAINER, 1, ClickType.LEFT, InventoryAction.PICKUP_ONE));

        GUIContainer.getRootPager().onInventoryClick(eventClickFirstShop);
        GUIContainer.getRootPager().onInventoryClick(eventClickSecondShop);
        verify(mockPlayer).openInventory(GUIContainer.getGuiMap().get(0).getInventory());
        verify(mockPlayer).openInventory(GUIContainer.getGuiMap().get(1).getInventory());

        // Click first slot in first shop
        InventoryView firstShopView = PowerMockito.mock(InventoryView.class);
        when(firstShopView.getPlayer()).thenReturn(mockPlayer);
        when(firstShopView.getTopInventory()).thenReturn(GUIContainer.getGuiMap().get(0).getInventory());

        InventoryClickEvent eventClickFirstShopFirstItem = PowerMockito.spy(new InventoryClickEvent(firstShopView,
                SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ONE));
        GUIContainer.getGuiMap().get(0).getPager().onInventoryClick(eventClickFirstShopFirstItem);
        verify(mockPlayer).openInventory(GUIContainer.getGuiMap().get(0).getShopObjects().get(0).getBuySellGui());

        // Buy something, sell something
        InventoryView buySellView = PowerMockito.mock(InventoryView.class);
        when(buySellView.getPlayer()).thenReturn(mockPlayer);
        when(buySellView.getTopInventory())
                .thenReturn(GUIContainer.getGuiMap().get(0).getShopObjects().get(0).getBuySellGui());

        InventoryClickEvent eventClickBuy = PowerMockito.spy(new InventoryClickEvent(buySellView, SlotType.CONTAINER, 0,
                ClickType.LEFT, InventoryAction.PICKUP_ONE));
        GUIContainer.handleClickEvents(eventClickBuy);
        verify(plugin.getTradeManager()).tradeItemForMoney((Player) Matchers.any(), (ItemStack) Matchers.any(), Matchers.anyDouble());
    }
}
