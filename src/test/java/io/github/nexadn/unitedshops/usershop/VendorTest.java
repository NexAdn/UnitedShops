package io.github.nexadn.unitedshops.usershop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
public class VendorTest {

    @Test
    public void test ()
    {
        TestUtil.init();
        UnitedShops plugin = TestUtil.getPlugin();

        Player mockVendor = PowerMockito.mock(Player.class);
        when(mockVendor.getUniqueId()).thenReturn(UUID.randomUUID());
        Player mockCustomer = PowerMockito.mock(Player.class);
        when(mockCustomer.getUniqueId()).thenReturn(UUID.randomUUID());

        Vendor vendor = Vendor.getOrCreateVendor(mockVendor);
        assertTrue(vendor.getRating() == 0.f);

        vendor.getOrCreateOffer(Material.COBBLESTONE);
        Offer testOffer = vendor.getOffers().get(Material.COBBLESTONE);
        Inventory supplyInventory = testOffer.getSupplyGui();

        supplyInventory.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
        supplyInventory.setItem(1, new ItemStack(Material.COBBLESTONE, 64));
        supplyInventory.setItem(5, new ItemStack(Material.COBBLESTONE, 64));
        InventoryView supplyView = TestUtil.getInventoryClickEventMockView(supplyInventory, mockVendor);
        InventoryCloseEvent supplyCloseEvent = PowerMockito.spy(new InventoryCloseEvent(supplyView));
        testOffer.onInventoryClose(supplyCloseEvent);

        Inventory tradeInventory = testOffer.getTradeGui();
        InventoryView tradeView = TestUtil.getInventoryClickEventMockView(tradeInventory, mockCustomer);
        InventoryClickEvent tradeBuyClickEvent = new InventoryClickEvent(tradeView, InventoryType.SlotType.CONTAINER,
                36, ClickType.LEFT, InventoryAction.PICKUP_ALL);
        testOffer.onInventoryClick(tradeBuyClickEvent);

        assertEquals(1., vendor.getStoredMoney(), 0.);
    }
}
