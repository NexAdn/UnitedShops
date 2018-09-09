package io.github.nexadn.unitedshops.shop;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.junit.Test;

import io.github.nexadn.unitedshops.Pair;
import io.github.nexadn.unitedshops.TestUtil;
import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.config.ConfigShopMain;

public class ParserTest {

    @Test
    public void test ()
    {
        TestUtil.init();
        UnitedShops plugin = TestUtil.getPlugin();

        ConfigShopMain testConfig = new ConfigShopMain();
        testConfig.parseConfig();
        List<ShopInventory> invs = testConfig.getMenus();
        plugin.log(Level.INFO, "Shop inventories: " + invs.toString());
        for (ShopInventory s : invs)
        {
            plugin.log(Level.INFO, "Shop " + s.getOrderNumber());
            for (ShopObject object : s.getShopObjects())
            {
                plugin.log(Level.INFO, "Object " + object.getItemType().toString());
            }
        }

        Pair<Material, Short> item = invs.get(0).getShopObjects().get(2).getItemType();
        assertEquals(Material.COBBLESTONE.toString(), item.first.toString());
        assertEquals(1, item.second.shortValue());
    }

}
