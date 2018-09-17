package me.nexadn.unitedshops.testutil;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import me.nexadn.unitedshops.UnitedShops;

public class TestUnitedShops extends UnitedShops {
    public TestUnitedShops(JavaPluginLoader mpl, PluginDescriptionFile pdf, File pd, File file) {
        super(mpl, pdf, pd, file);
        this.unitTest = true;
    }

    @Override
    public void sendMessage(CommandSender recv, String message) {
        this.log(Level.INFO, "Message to " + recv.getName() + ": " + message);
    }
}
