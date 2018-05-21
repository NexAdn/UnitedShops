package io.github.nexadn.unitedshops;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import io.github.nexadn.unitedshops.command.AutoSellHandler;
import io.github.nexadn.unitedshops.command.ShopGUIHandler;
import io.github.nexadn.unitedshops.config.ConfigMessages;
import io.github.nexadn.unitedshops.events.GUIClick;
import io.github.nexadn.unitedshops.events.OnInventoryClose;
import io.github.nexadn.unitedshops.shop.AutoSellManager;
import io.github.nexadn.unitedshops.shop.GUIContainer;
import io.github.nexadn.unitedshops.tradeapi.EcoManager;
import io.github.nexadn.unitedshops.tradeapi.TradeManager;
import io.github.nexadn.unitedshops.usershop.Vendor;

public class UnitedShops extends JavaPlugin {
    private boolean                                 unitTest = false;

    private HashMap<OfflinePlayer, AutoSellManager> autoSaleInventories;
    private HashMap<String, String>                 messages;

    protected TradeManager                          tradeManager;

    public static UnitedShops                       plugin;

    @SuppressWarnings ("unused")
    private Metrics                                 metrics;

    public UnitedShops()
    {
    }

    /** Unit Test constructor */
    public UnitedShops(JavaPluginLoader mockPluginLoader, PluginDescriptionFile pluginDescriptionFile, File pluginDir,
            File file)
    {
        super(mockPluginLoader, pluginDescriptionFile, pluginDir, file);
        this.log(Level.SEVERE, "Running in Unit testing mode!");
    }

    @Override
    /** Enable the plugin */
    public void onEnable ()
    {
        plugin = this;
        this.autoSaleInventories = new HashMap<>();

        this.getLogger().log(Level.FINE, "Establishing economy hook...");
        if (!EcoManager.initEco())
        {
            this.getLogger().log(Level.SEVERE, "The Economy hook couldn't be initialized. Is Vault missing?");
            if (!this.unitTest)
            {
                this.setEnabled(false);
                return;
            }
        }
        if (!this.unitTest)
            this.tradeManager = new TradeManager(this, EcoManager.getEconomy());
        this.getLogger().log(Level.FINE, "Economy hook successful.");

        // config.yml
        try
        {
            if (!this.getDataFolder().exists())
            {
                this.getDataFolder().mkdirs();
            }
            File configyml = new File(this.getDataFolder(), "config.yml");
            if (!configyml.exists())
            {
                this.getLogger().log(Level.INFO, "config.yml not found, creating a new one just for you!");
                this.saveResource("config.yml", true);
            }
            File messagesyml = new File(this.getDataFolder(), "messages.yml");
            if (!messagesyml.exists())
            {
                this.getLogger().log(Level.INFO, "messages.yml not found, creating a new one just for you!");
                this.saveResource("messages.yml", true);
            }
        } catch (Exception e)
        {
            this.getLogger().log(Level.INFO, e.getMessage());
            e.printStackTrace();
            this.setEnabled(false);
            this.getLogger().log(Level.SEVERE, "UnitedShops failed. Please send this Log file to the plugin's author.");
        }

        if (!this.unitTest && this.getConfig().getBoolean("stats"))
            this.metrics = new Metrics(this);

        ConfigMessages configMessages = new ConfigMessages(new File(this.getDataFolder(), "messages.yml"));
        configMessages.parseConfig();
        this.messages = configMessages.getMessages();

        if (!this.unitTest)
        {
            // Command executors
            this.getServer().getPluginCommand("ushop").setExecutor(new ShopGUIHandler()); // /ushop
            this.getServer().getPluginCommand("usell").setExecutor(new AutoSellHandler()); // /usell

            // Event handler
            this.getServer().getPluginManager().registerEvents(new GUIClick(), this);
            this.getServer().getPluginManager().registerEvents(new OnInventoryClose(), this);
        }

        GUIContainer.initGUI();

        if (this.unitTest)
            UnitedShops.plugin.log(Level.SEVERE, "Running in unit testing mode!");
    }

    public void onUnitTest ()
    {
        this.unitTest = true;
    }

    @Override
    public void onDisable ()
    {
        this.saveConfig();
    }

    public void log (Level loglevel, String message)
    {
        this.getLogger().log(loglevel, message);
    }

    public void sendMessage (CommandSender target, String message)
    {
        target.sendMessage("[" + this.getName() + "] " + message);
    }

    public AutoSellManager getAutoSellManager (OfflinePlayer player)
    {
        if ( (!this.autoSaleInventories.containsKey(player)) || this.autoSaleInventories.get(player) == null)
        {
            this.autoSaleInventories.put(player, new AutoSellManager(player));
        }
        return this.autoSaleInventories.get(player);
    }

    public TradeManager getTradeManager ()
    {
        return this.tradeManager;
    }

    public boolean hasAutoSellManager (OfflinePlayer player)
    {
        return this.autoSaleInventories.containsKey(player);

    }

    public boolean isUnitTest ()
    {
        return this.unitTest;
    }

    public String getMessage (String key)
    {
        if (!this.unitTest)
        {
            return this.messages.get(key);
        } else
        {
            return key;
        }
    }

    @Override
    public boolean onCommand (CommandSender commandSender, Command command, String label, String[] sArgv)
    {
        if (command.getName().equalsIgnoreCase("ushopreload"))
        {
            if (!commandSender.hasPermission("unitedshops.admin"))
            {
                this.sendMessage(commandSender, this.getMessage("missingPermission"));
                return false;
            }

            this.getLogger().log(Level.INFO, "Reloading config");
            this.sendMessage(commandSender, "Reloading UnitedShops...");

            this.reloadConfig();

            ConfigMessages configMessages = new ConfigMessages(new File(this.getDataFolder(), "messages.yml"));
            configMessages.parseConfig();
            this.messages = configMessages.getMessages();

            GUIContainer.initGUI();
            this.getLogger().log(Level.FINE, "Reload successful");
            this.sendMessage(commandSender, "Reload successful");
            return true;
        } else if (command.getName().equalsIgnoreCase("ushopoffer"))
        {
            if (!commandSender.hasPermission("unitedshops.admin"))
            {
                this.sendMessage(commandSender, this.getMessage("missingPermission"));
                return false;
            }

            if (! (commandSender instanceof Player))
            {
                this.sendMessage(commandSender, this.getMessage("playerOnly"));
                return false;
            }

            Player player = (Player) commandSender;

            if (player.getItemInHand() == null)
                return false;

            if (player.getItemInHand().getType() == Material.AIR)
                return false;

            this.log(Level.INFO, player.getItemInHand().toString());

            Vendor vendor = Vendor.getOrCreateVendor(player);
            vendor.getOrCreateOffer(player.getItemInHand().getType());
            player.openInventory(vendor.getVendorMenu());
        } else if (command.getName().equalsIgnoreCase("uvmenu"))
        {
            if (!commandSender.hasPermission("unitedshops.admin"))
            {
                this.sendMessage(commandSender, this.getMessage("missingPermission"));
                return false;
            }

            if (! (commandSender instanceof Player))
            {
                this.sendMessage(commandSender, this.getMessage("playerOnly"));
                return false;
            }

            Player player = (Player) commandSender;

            player.openInventory(Vendor.getGlobalVendorMenu());
        } else if (command.getName().equalsIgnoreCase("uomenu"))
        {
            if (!commandSender.hasPermission("unitedshops.admin"))
            {
                this.sendMessage(commandSender, this.getMessage("missingPermission"));
                return false;
            }

            if (! (commandSender instanceof Player))
            {
                this.sendMessage(commandSender, this.getMessage("playerOnly"));
                return false;
            }

            Player player = (Player) commandSender;

            player.openInventory(Vendor.getGlobalOfferMenu());
        } else if (command.getName().equalsIgnoreCase("uosupply"))
        {
            if (!commandSender.hasPermission("unitedshops.admin"))
            {
                this.sendMessage(commandSender, this.getMessage("missingPermission"));
                return false;
            }

            if (! (commandSender instanceof Player))
            {
                this.sendMessage(commandSender, this.getMessage("playerOnly"));
                return false;
            }

            Player player = (Player) commandSender;

            if (player.getItemInHand() == null)
                return false;

            if (player.getItemInHand().getType() == Material.AIR)
                return false;

            player.openInventory(
                    Vendor.getOrCreateVendor(player).getOrCreateOffer(player.getItemInHand().getType()).getSupplyGui());
        }
        return false;
    }
}

/*
 * Copyright (C) 2015-2018 Adrian Schollmeyer
 *
 * This file is part of UnitedShops.
 *
 * UnitedShops is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
