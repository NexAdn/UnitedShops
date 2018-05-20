package io.github.nexadn.unitedshops.usershop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.nexadn.unitedshops.TestUtil;
import io.github.nexadn.unitedshops.UnitedShops;

@RunWith (PowerMockRunner.class)
public class OfferTest {

    @Test
    public void offerTest ()
    {
        TestUtil.init();
        UnitedShops plugin = TestUtil.getPlugin();

        Player mockPlayer = PowerMockito.mock(Player.class);
        when(mockPlayer.getUniqueId()).thenReturn(UUID.randomUUID());

        Vendor mockVendor = PowerMockito.spy(new Vendor(mockPlayer, "TestVendor", plugin.getDataFolder()));
        Offer testOffer = PowerMockito.spy(new Offer(mockVendor, mockPlayer, Material.COBBLESTONE, 10., 5., 3));

        Inventory tradeGui = testOffer.getTradeGui();
        assertEquals(Material.COBBLESTONE, tradeGui.getItem(36).getType());
        assertEquals(1, tradeGui.getItem(36).getAmount());
        assertEquals(10, tradeGui.getItem(37).getAmount());
        assertEquals(64, tradeGui.getItem(38).getAmount());
        assertEquals(1, tradeGui.getItem(42).getAmount());
        assertEquals(10, tradeGui.getItem(43).getAmount());
        assertEquals(64, tradeGui.getItem(44).getAmount());

        Inventory supplyGui = testOffer.getSupplyGui();
        assertEquals(null, supplyGui.getItem(0));
        supplyGui.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
        supplyGui.setItem(1, new ItemStack(Material.COBBLESTONE, 32));
        supplyGui.setItem(2, new ItemStack(Material.LOG, 32));
        assertNotNull(testOffer.getSupplyGui().getItem(0));
        testOffer.updateSupply();
        testOffer.recreateInventories();
        supplyGui = testOffer.getSupplyGui();

        tradeGui = testOffer.getTradeGui();
        assertEquals(Material.COBBLESTONE, tradeGui.getItem(0).getType());
        assertEquals(64, tradeGui.getItem(0).getAmount());
        assertEquals(32, tradeGui.getItem(1).getAmount());

        Player mockCustomer = PowerMockito.mock(Player.class);
        InventoryView buySellView = TestUtil.getInventoryClickEventMockView(tradeGui, mockCustomer);
        InventoryClickEvent buyTestEvent = new InventoryClickEvent(buySellView, InventoryType.SlotType.CONTAINER, 36,
                ClickType.LEFT, InventoryAction.PICKUP_ALL);
        testOffer.onInventoryClick(buyTestEvent);
        verify(testOffer).removeItemsFromSupplyViews(1);
        assertEquals(31, testOffer.getTradeGui().getItem(1).getAmount());

        InventoryClickEvent sellTestEvent = new InventoryClickEvent(buySellView, InventoryType.SlotType.CONTAINER, 42,
                ClickType.LEFT, InventoryAction.PICKUP_ALL);
        testOffer.onInventoryClick(sellTestEvent);
        verify(testOffer).addItemsToSupplyViews(1);
        assertEquals(32, tradeGui.getItem(1).getAmount());
    }

}
