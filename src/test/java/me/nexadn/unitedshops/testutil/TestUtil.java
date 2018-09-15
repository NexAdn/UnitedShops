package me.nexadn.unitedshops.testutil;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

import me.nexadn.unitedshops.UnitedShops;

public class TestUtil {
    private static JavaPluginLoader javaPluginLoader;
    private static boolean initialized = false;
    private static Server server;
    private static UnitedShops plugin;
    private static File pluginDir = new File("target/test/unitedshops");

    public static void init() {
        initialized = true;
        pluginDir.mkdirs();

        server = PowerMockito.spy(new TestServer());
        Bukkit.setServer(server);
        javaPluginLoader = new JavaPluginLoader(server);
        Whitebox.setInternalState(javaPluginLoader, "server", server);
        Logger.getLogger("Minecraft").setParent(server.getLogger());
        PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile("UnitedShops", "-1",
                "me.nexadn.unitedshops.UnitedShops");
        plugin = new TestUnitedShops(javaPluginLoader, pluginDescriptionFile, pluginDir,
                new File(pluginDir, "config.yml"));

        plugin.onEnable();
    }

    public UnitedShops getPlugin() {
        return plugin;
    }

    public Server getServer() {
        return server;
    }

}
