package io.github.nexadn.unitedshops.usershop;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
    public void offerCreationAndSupplyTest ()
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
        testOffer.recreateInventories();
        supplyGui = testOffer.getSupplyGui();
        assertNotNull(supplyGui.getItem(0));

        tradeGui = testOffer.getTradeGui();
        assertEquals(Material.COBBLESTONE, tradeGui.getItem(0).getType());
        assertEquals(64, tradeGui.getItem(0).getAmount());
    }

}
