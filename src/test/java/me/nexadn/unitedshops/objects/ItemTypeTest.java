package me.nexadn.unitedshops.objects;

import me.nexadn.unitedshops.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTypeTest
{
    @Test
    public void testSaneCreationMaterialOnly()
    {
        ItemType itemType = new ItemType(Material.COBBLESTONE);
    }

    @Test
    public void testCorruptedCreationMaterialOnly()
    {
        try {
            Material m = null;
            ItemType itemType = new ItemType(m);
            fail("Missing IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    @Test
    public void testSaneCreationMaterialAndDamage()
    {
        ItemType itemType = new ItemType(Material.COBBLESTONE, (short) 1);
    }

    @Test
    public void testCorruptedCreationMaterialAndDamage()
    {
        try {
            ItemType itemType = new ItemType(null, (short) 5);
            fail("Missing IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    @Test
    public void testSaneCreationItemTypePair()
    {
        ItemType itemType = new ItemType(new Pair<>(Material.COBBLE_WALL, (short) 1));
    }

    @Test
    public void testCorruptedCreationItemTypePair()
    {
        try {
            ItemType itemType = new ItemType(new Pair<>(null, (short) 1));
            fail("Missing IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    @Test
    public void testGetAsItemStack()
    {
        ItemType it = new ItemType(Material.COBBLESTONE, (short) 0);
        ItemStack is = it.getAsItemStack(32);
        assertEquals(32, is.getAmount());
        assertEquals(0, is.getDurability());
        assertEquals(Material.COBBLESTONE, is.getType());

        it = new ItemType(Material.COBBLESTONE, (short) -1);
        is = it.getAsItemStack();
        assertEquals(1, is.getAmount());
        assertEquals(0, is.getDurability());
        assertEquals(Material.COBBLESTONE, is.getType());

        try {
            it = new ItemType(Material.COBBLESTONE, (short) -1);
            is = it.getAsItemStack(0);
            fail("Missing IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    @Test
    public void testGetAsPair()
    {
        ItemType it = new ItemType(Material.COBBLESTONE, (short) 0);
        Pair<Material, Short> itp = it.getAsItemTypePair();
        assertEquals(it.material, itp.first);
        assertEquals(it.damage, itp.second.shortValue());
    }

    @Test
    public void testIsAnyDamage()
    {
        ItemType it1 = new ItemType(Material.COBBLESTONE, (short) 0);
        ItemType it2 = new ItemType(Material.COBBLESTONE, (short) -1);
        assertFalse(it1.isAnyDamage());
        assertTrue(it2.isAnyDamage());
    }

    @Test
    public void testMatches()
    {
        ItemType lhs = new ItemType(Material.COBBLESTONE, (short) 0);
        ItemType rhs1 = new ItemType(Material.COBBLESTONE, (short) 1);
        ItemType rhs2 = new ItemType(Material.COBBLESTONE, (short) -1);
        Pair<Material, Short> rhs3 = new Pair<>(Material.COBBLE_WALL, (short) 0);
        Pair<Material, Short> rhs4 = new Pair<>(Material.COBBLE_WALL, (short) -1);

        assertTrue(lhs.matches(lhs));
        assertFalse(lhs.matches(rhs1));
        assertTrue(lhs.matches(rhs2));
        assertFalse(lhs.matches(rhs3));
        assertFalse(lhs.matches(rhs4));
    }

    @Test
    public void testEquals()
    {
        ItemType lhs = new ItemType(Material.COBBLESTONE, (short) 0);
        ItemType rhs1 = new ItemType(Material.COBBLESTONE, (short) 1);
        ItemType rhs2 = new ItemType(Material.COBBLESTONE, (short) -1);
        Pair<Material, Short> rhs3 = new Pair<>(Material.COBBLE_WALL, (short) 0);
        Pair<Material, Short> rhs4 = new Pair<>(Material.COBBLE_WALL, (short) -1);
        ItemType rhs5 = new ItemType(Material.COBBLESTONE, (short) 0);
        Pair<Material, Short> rhs6 = new Pair<>(Material.COBBLESTONE, (short) 0);

        assertTrue(lhs.equals(lhs));
        assertFalse(lhs.equals(rhs1));
        assertFalse(lhs.equals(rhs2));
        assertFalse(lhs.equals(rhs3));
        assertFalse(lhs.equals(rhs4));
        assertTrue(lhs.equals(rhs5));
        assertTrue(lhs.equals(rhs6));
    }
}
