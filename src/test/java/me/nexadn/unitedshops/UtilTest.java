package me.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import me.nexadn.unitedshops.testutil.TestUtil;

public class UtilTest {

    @Test
    public void test() {
        TestUtil.init();

        ItemStack result = Util.getItem(new Pair<>(Material.STONE, (short) 1), 32, "DISPLAY", "LORE");

        assertEquals(1, result.getDurability());
        assertEquals(Material.STONE, result.getType());
        assertEquals("DISPLAY", result.getItemMeta().getDisplayName());
        assertEquals("LORE", result.getItemMeta().getLore().get(0));
    }

}
