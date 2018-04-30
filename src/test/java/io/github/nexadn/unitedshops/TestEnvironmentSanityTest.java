package io.github.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.nexadn.unitedshops.shop.GUIContainer;

@RunWith (PowerMockRunner.class)
public class TestEnvironmentSanityTest {

    @Test
    public void test ()
    {
        TestUtil.init();
        UnitedShops plugin = TestUtil.getPlugin();

        assertTrue(UnitedShops.plugin instanceof TestUnitedShops);
        assertEquals(TestUtil.getPlugin(), UnitedShops.plugin);
        assertTrue(plugin.getTradeManager() instanceof TestTradeManager);
        assertEquals(TestUtil.getTradeManager(), plugin.getTradeManager());
    }
}
