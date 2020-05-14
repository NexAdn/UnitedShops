package me.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import me.nexadn.unitedshops.exception.InvalidValueException;
import me.nexadn.unitedshops.testutil.TestUtil;

public class UtilTest {

    @Test
    public void getItemTest() {
        TestUtil.init();

        ItemStack result = Util.getItem(new Pair<>(Material.STONE, (short) 1), 32, "DISPLAY", "LORE");

        assertEquals(1, result.getDurability());
        assertEquals(Material.STONE, result.getType());
        assertEquals("DISPLAY", result.getItemMeta().getDisplayName());
        assertEquals("LORE", result.getItemMeta().getLore().get(0));
    }

    @Test
    public void getItemTypeTest() {
        TestUtil.init();

        Pair<Material, Short> result = Util.parseItemType("STONE:1");
        assertEquals(Material.STONE, result.first);
        assertEquals(1, result.second.shortValue());

        result = Util.parseItemType("WOOL:12");
        assertEquals(Material.WOOL, result.first);
        assertEquals(12, result.second.shortValue());
    }

    @Test(expected = InvalidValueException.class)
    public void getInvalidItemTypeTest() {
        TestUtil.init();

        // Invalid value should throw
        Util.parseItemType("STOXXXNE:1");
    }

}
