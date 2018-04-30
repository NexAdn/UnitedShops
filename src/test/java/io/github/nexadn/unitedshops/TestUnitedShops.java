package io.github.nexadn.unitedshops;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

public class TestUnitedShops extends UnitedShops {
    public TestUnitedShops(JavaPluginLoader mpl, PluginDescriptionFile pdf, File pd, File file)
    {
        super(mpl, pdf, pd, file);
        this.tradeManager = new TestTradeManager(this);
    }
    
    @Override
    public void sendMessage(CommandSender target, String message)
    {
        this.log(Level.INFO, "Message to " + target.getName() + ": " + message);
    }
}
