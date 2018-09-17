package me.nexadn.unitedshops.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.Test;

import me.nexadn.unitedshops.ConfigFileHandler;
import me.nexadn.unitedshops.shop.Shop;
import me.nexadn.unitedshops.shop.ShopObject;
import me.nexadn.unitedshops.testutil.TestUtil;

public class ParserTest {

    @Test
    public void shopConfigSaneParserTest() {
        TestUtil.init();

        ShopConfigParser shopConfigParser = new ShopConfigParser(TestUtil.getPlugin(),
                new ConfigFileHandler(TestUtil.getPlugin(), ClassLoader.getSystemResourceAsStream("shops1.yml"),
                        new File(TestUtil.getPlugin().getDataFolder(), "shops1.yml")));

        shopConfigParser.parse();
        List<ShopObject> objects = shopConfigParser.get();
        assertEquals(2, objects.size());
        Shop emptyShop = (Shop) objects.get(0);
        Shop myexampleshop = (Shop) objects.get(1);

        assertEquals(ChatColor.BLUE + "Empty shop", emptyShop.getTitle());
        assertEquals(ChatColor.BLUE + "The block shop", myexampleshop.getTitle());
        assertEquals(Material.LOG, emptyShop.getIcon().getType());
        assertEquals(0, emptyShop.getIcon().getDurability());
        assertEquals(Material.COBBLESTONE, myexampleshop.getIcon().getType());
        assertEquals(0, myexampleshop.getIcon().getDurability());
    }
}
