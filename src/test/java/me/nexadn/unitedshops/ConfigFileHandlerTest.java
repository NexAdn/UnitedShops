package me.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.bukkit.Material;
import org.junit.Test;

public class ConfigFileHandlerTest {

    @Test
    public void test ()
    {
        File shopsYml = new File("shops.yml");
        shopsYml.deleteOnExit();
        ConfigFileHandler handler = new ConfigFileHandler(null, ClassLoader.getSystemResourceAsStream("shops.yml"),
                shopsYml);

        Pair<Material, Short> expectedIcon = new Pair<>(Material.COBBLESTONE, (short) 0);
        Pair<Material, Short> actualIcon = handler.readItemType("shops.myexampleshop.icon");
        assertEquals(expectedIcon.first.toString(), actualIcon.first.toString());
        assertEquals(expectedIcon.second.shortValue(), actualIcon.second.shortValue());
    }

}
