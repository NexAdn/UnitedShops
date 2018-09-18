package me.nexadn.unitedshops;

import java.io.File;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import me.nexadn.unitedshops.exception.InvalidConfigException;
import me.nexadn.unitedshops.exception.InvalidValueException;
import me.nexadn.unitedshops.tradeapi.TradeManager;
import me.nexadn.unitedshops.ui.AutoSellGui;
import me.nexadn.unitedshops.ui.GlobalMenuGui;

public class UnitedShops extends JavaPlugin {
    private ConfigFileHandler baseConfigHandler;
    private ConfigFileHandler shopConfigHandler;

    private GlobalMenuGui menuGui;

    private LocalizationHandler l10n;
    private TradeManager tradeManager;

    @SuppressWarnings("unused")
    private Metrics metrics;

    protected boolean unitTest = false;

    public UnitedShops() {
        super();
    }

    public UnitedShops(JavaPluginLoader mpl, PluginDescriptionFile pdf, File pd, File file) {
        super(mpl, pdf, pd, file);
    }

    @Override
    public void onEnable() {
        this.fetchConfigData();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("unitedshops")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("shop")) {
                    if (!(cs instanceof Player)) {
                        this.sendMessage(cs, this.getL10n().getMessage("playerOnly").str());
                        return true;
                    }

                    if (!cs.hasPermission("unitedshops.shop")) {
                        this.sendMessage(cs, this.getL10n().getMessage("missingPermission")
                                .arg("permission", "unitedshops.shop").str());
                        return true;
                    }

                    ((Player) cs).openInventory(this.menuGui.getUi());
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!cs.hasPermission("unitedshops.reload")) {
                        this.sendMessage(cs, this.getL10n().getMessage("missingPermission")
                                .arg("permission", "unitedshops.reload").str());
                        return true;
                    }
                    this.fetchConfigData();
                    this.sendMessage(cs, this.getL10n().getMessage("reloadSuccess").str());
                    return true;
                } else if (args[0].equalsIgnoreCase("sell")) {
                    if (!(cs instanceof Player)) {
                        this.sendMessage(cs, this.getL10n().getMessage("playerOnly").str());
                        return true;
                    }

                    if (!cs.hasPermission("unitedshops.sell")) {
                        this.sendMessage(cs, this.getL10n().getMessage("missingPermission")
                                .arg("permission", "unitedshops.shop").str());
                        return true;
                    }

                    ((Player) cs).openInventory(AutoSellGui.getGui(this, (Player) cs).getInventory());
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!cs.hasPermission("unitedshops.reload")) {
                        this.sendMessage(cs, this.getL10n().getMessage("missingPermission")
                                .arg("permission", "unitedshops.reload").str());
                        return true;
                    } else {
                        AutoSellGui.clearGuis();
                        this.fetchConfigData();
                    }
                }
            }
            return false;
        } else if (cmd.getName().equalsIgnoreCase("shop")) {
            if (cs instanceof Player) {
                ((Player) cs).performCommand("unitedshops shop");
            } else {
                this.sendMessage(cs, this.getL10n().getMessage("playeronly").str());
            }
        }
        return true;
    }

    private void fetchConfigData() {
        this.baseConfigHandler = new ConfigFileHandler(this, this.getResource("config.yml"),
                new File(this.getDataFolder(), "config.yml"));
        this.shopConfigHandler = new ConfigFileHandler(this, this.getResource("shops.yml"),
                new File(this.getDataFolder(), "shops.yml"));

        if (!this.unitTest && this.baseConfigHandler.readBoolean("stats"))
            this.metrics = new Metrics(this);

        this.l10n = new LocalizationHandler(this, this.baseConfigHandler);
        this.tradeManager = new TradeManager(this);

        try {
            this.menuGui = new GlobalMenuGui(this, this.shopConfigHandler);
            this.menuGui.init();
        } catch (InvalidConfigException e) {
            e.printStackTrace();
            this.logSevere(
                    "We've encountered some malformed config files. Please fix the shown errors before using UnitedShops!");
            this.menuGui = null;
        } catch (InvalidValueException e) {
            e.printStackTrace();
            this.logSevere(
                    "We've encountered some malformed config files. Please fix the shown errors before using UnitedShops!");
            this.menuGui = null;
        }
    }

    public ConfigFileHandler getConf() {
        return this.baseConfigHandler;
    }

    public LocalizationHandler getL10n() {
        return this.l10n;
    }

    public TradeManager getTradeManager() {
        return this.tradeManager;
    }

    public GlobalMenuGui getMenuGui() {
        return this.menuGui;
    }

    public void sendMessage(CommandSender recv, String message) {
        recv.sendMessage(this.l10n.getMessage("prefix") + " " + message);
    }

    public void log(Level loglevel, String message) {
        this.getLogger().log(loglevel, message);
    }

    public void logSevere(String message) {
        this.log(Level.SEVERE, message);
    }

    public void logInfo(String message) {
        this.log(Level.INFO, message);
    }

    public void logFine(String message) {
        this.log(Level.FINE, message);
    }

    public void logFiner(String message) {
        this.log(Level.FINER, message);
    }

    public void logFinest(String message) {
        this.log(Level.FINEST, message);
    }
}
