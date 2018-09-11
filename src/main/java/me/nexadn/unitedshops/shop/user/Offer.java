package me.nexadn.unitedshops.shop.user;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;
import me.nexadn.unitedshops.ui.PagerItem;

public class Offer implements PagerItem, Listener {

    private UnitedShops plugin;
    private Vendor owner;
    private Material item;
    private int itemAmount;
    private Inventory supplyInventory;
    private Inventory viewInventory;
    private double priceBuy;
    private double priceSell;
    private int mode;
    private HashMap<Player, Inventory> lastParent = new HashMap<>();

    /**
     * Create a new Offer object and show the creator an inventory to supply the
     * shop offer
     *
     * @param owner     The owning Vendor
     * @param creator   The creator to whom the supply view shall be shown
     * @param item      The type of item to be sold
     * @param priceBuy  The price on which players can buy items
     * @param priceSell The price on which players can sell items
     * @param mode      The mode of the offer (1 = buy, 2 = sell, 3 = buy/sell)
     */
    public Offer(UnitedShops plugin, Vendor owner, Player creator, Material item, double priceBuy, double priceSell,
            int mode) {
        this.plugin = plugin;
        this.owner = owner;
        this.item = item;
        this.itemAmount = 0;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.mode = mode;
        // TODO messages.yml
        this.supplyInventory = Bukkit.createInventory(null, 9 * 3, "Gelagerte items");

        this.updateSupply();
        this.recreateInventories();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    /**
     * A player buys items from this shop
     *
     * @param buyer  The buying player
     * @param amount The amount of items that are bought from the shop
     */
    public void buy(Player buyer, int amount) {
        if (this.itemAmount < amount) {
            // TODO
            return;
        } else {
            if (!this.plugin.getTradeManager().tradeOfferItemForMoney(buyer, new ItemStack(this.item, amount),
                    this.priceBuy * amount)) {
                this.plugin.sendMessage(buyer, this.plugin.getL10n().getMessage("generalError").str());
            } else {
                this.itemAmount -= amount;
                this.owner.storeMoney(this.priceBuy * amount);
                this.owner.onOfferBuy(this, amount);
                this.removeItemsFromSupplyViews(amount);
            }
        }
    }

    /**
     * A player sells items to this shop
     *
     * @param seller The selling player
     * @param amount The amount of item that are sold to the shop
     */
    public void sell(Player seller, int amount) {
        if (!this.owner.hasEnoughMoney(this.priceSell * amount)) {
            // TODO
            return;
        } else {
            if (!this.plugin.getTradeManager().tradeOfferMoneyForItem(seller, this.priceSell * amount,
                    new ItemStack(this.item, amount))) {
                this.plugin.sendMessage(seller, this.plugin.getL10n().getMessage("generalError").str());
            } else {
                this.itemAmount += amount;
                this.owner.withdrawMoney(this.priceSell * amount);
                this.owner.onOfferSell(this, amount);
                this.addItemsToSupplyViews(amount);
            }
        }
    }

    @Override
    public void call(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            this.lastParent.put((Player) e.getWhoClicked(), e.getInventory());
            e.getWhoClicked().openInventory(this.viewInventory);
        } else if (e.getWhoClicked() instanceof CommandSender) {
            this.plugin.sendMessage((CommandSender) e.getWhoClicked(),
                    this.plugin.getL10n().getMessage("playerOnly").str());
        }
    }

    public double getBuyPrice() {
        return this.priceBuy;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(this.item);
    }

    public int getMode() {
        return this.mode;
    }

    public double getSellPrice() {
        return this.priceSell;
    }

    public Inventory getSupplyGui() {
        return this.supplyInventory;
    }

    public Inventory getTradeGui() {
        return this.viewInventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            if (e.getWhoClicked() instanceof CommandSender)
                this.plugin.sendMessage((CommandSender) e.getWhoClicked(),
                        this.plugin.getL10n().getMessage("playerOnly").str());
            return;
        }

        if (e.getInventory().equals(this.viewInventory)) {
            e.setCancelled(true);
            switch (e.getSlot()) {
            case 36:
                this.buy((Player) e.getWhoClicked(), 1);
                break;

            case 37:
                this.buy((Player) e.getWhoClicked(), 10);
                break;

            case 38:
                this.buy((Player) e.getWhoClicked(), 64);
                break;

            case 40:
                e.getWhoClicked().openInventory(this.lastParent.get((Player) e.getWhoClicked()));
                break;

            case 42:
                this.sell((Player) e.getWhoClicked(), 1);
                break;

            case 43:
                this.sell((Player) e.getWhoClicked(), 10);
                break;

            case 44:
                this.sell((Player) e.getWhoClicked(), 64);
                break;
            }
        } else if (e.getInventory().equals(this.supplyInventory)) {
            if ((!(e.getCurrentItem().getType().equals(this.item)))
                    && (!(e.getCurrentItem().getType().equals(Material.AIR)))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this.supplyInventory)) {
            this.updateSupply();
            this.recreateInventories();
        }
    }

    /**
     * Save the offer to config
     *
     * @param section Where to save the offer
     * @return The modified {@link ConfigurationSection}
     */
    public ConfigurationSection saveToConfig(ConfigurationSection section) {
        section.set("owner", this.owner.getPlayerUUID().toString());
        section.set("item", this.item.toString());
        int supply = 0;
        for (ItemStack itemStack : this.supplyInventory) {
            if (itemStack.getType().equals(this.item))
                supply += itemStack.getAmount();
        }
        section.set("supply", supply);
        section.set("priceBuy", this.priceBuy);
        section.set("priceSell", this.priceSell);
        section.set("mode", this.mode);

        return section;
    }

    /**
     * Set this Offer's mode
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPriceBuy(double price) {
        this.priceBuy = price;
    }

    public void setPriceSell(double price) {
        this.priceSell = price;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.item.toString() + "\n");
        buf.append("Buy:" + this.priceBuy + "\n");
        buf.append("Sell: " + this.priceSell + "\n");
        buf.append("Stock:" + this.itemAmount + "\n");
        return buf.toString();
    }

    public void recreateInventories() {
        // TODO messages yml
        this.supplyInventory = Bukkit.createInventory(null, 9 * 3, "Gelagerte Items");
        this.viewInventory = Bukkit.createInventory(null, 9 * 5,
                this.item.toString() + " – " + this.owner.getPlayer().getDisplayName());

        int amount = this.itemAmount;
        int index = 0;
        while (amount > 0) {
            // TODO detect max stack size
            ItemStack stack = new ItemStack(this.item, amount > 64 ? 64 : amount);
            amount -= 64;
            this.viewInventory.setItem(index, stack);
            this.supplyInventory.setItem(index++, stack);
        }

        for (int i = 27; i < 36; i++) {
            this.viewInventory.setItem(i, Util.getBlank());
        }

        for (int i = 36; i < 45; i++) {
            ItemStack it;
            switch (i) {/*
                         * TODO case 36: // Buy 1 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("buyAmount") + "1", this.plugin.getMessage("price") +
                         * this.priceBuy * 1); it.setAmount(1); break;
                         *
                         * case 37: // Buy 10 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("buyAmount") + "10", this.plugin.getMessage("price") +
                         * this.priceBuy * 10); it.setAmount(10); break;
                         *
                         * case 38: // Buy 64 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("buyAmount") + "64", this.plugin.getMessage("price") +
                         * this.priceBuy * 64); it.setAmount(64); break;
                         *
                         * case 42: // Sell 1 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("sellAmount") + "1", this.plugin.getMessage("price") +
                         * this.priceSell * 1); it.setAmount(1); break;
                         *
                         * case 43: // Sell 10 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("sellAmount") + "10", this.plugin.getMessage("price")
                         * + this.priceSell * 10); it.setAmount(10); break;
                         *
                         * case 44: // Sell 64 it = GUIContainer.getFunctionalItem(this.item,
                         * this.plugin.getMessage("sellAmount") + "64", this.plugin.getMessage("price")
                         * + this.priceSell * 64); it.setAmount(64); break;
                         *
                         * case 40: // Back it = GUIContainer.getFunctionalItem(Material.BARRIER,
                         * this.plugin.getMessage("back")); break;
                         */
            default: // 39, 41
                it = Util.getBlank();
                break;
            }
            this.viewInventory.setItem(i, it);
        }
    }

    public void addItemsToSupplyViews(int amount) {
        // TODO dynamic max item stack size
        int remaining = amount;
        // Index value based on supply inventory size
        for (int i = 0; i < 27; i++) {
            if (this.supplyInventory.getItem(i) == null) {
                this.supplyInventory.setItem(i, new ItemStack(this.item, remaining <= 64 ? remaining : 64));
                if (remaining <= 64)
                    break;
                else
                    remaining -= 64;
            } else if (this.supplyInventory.getItem(i).getAmount() < 64) {
                int maxAddUp = 64 - this.supplyInventory.getItem(i).getAmount();
                this.supplyInventory.setItem(i, new ItemStack(this.item,
                        this.supplyInventory.getItem(i).getAmount() + (remaining <= maxAddUp ? remaining : maxAddUp)));
                if (remaining <= maxAddUp)
                    break;
                else
                    remaining -= maxAddUp;
            } else {
                continue;
            }
        }

        for (int i = 0; i < 26; i++)
            this.viewInventory.setItem(i, this.supplyInventory.getItem(i));
    }

    public void removeItemsFromSupplyViews(int amount) {
        int remaining = amount;
        // Index value based on supply inventory size (last slot index)
        for (int i = 26; i >= 0; i--) {
            if (this.supplyInventory.getItem(i) != null) {
                if (this.supplyInventory.getItem(i).getAmount() > remaining) {
                    this.supplyInventory.getItem(i).setAmount(this.supplyInventory.getItem(i).getAmount() - remaining);
                    break;
                } else {
                    remaining -= this.supplyInventory.getItem(i).getAmount();
                    this.supplyInventory.setItem(i, null);
                }
            }
        }

        for (int i = 0; i < 26; i++)
            this.viewInventory.setItem(i, this.supplyInventory.getItem(i));
    }

    public void updateSupply() {
        int amount = 0;
        for (ItemStack stack : this.supplyInventory.getContents()) {
            if (stack == null)
                continue;
            if (stack.getType() != this.item) {
                // FIXME DELETE ITEM; TO BE REPLACED BY SOME NICER CLEANUP
                this.plugin.getTradeManager().removeItems(this.supplyInventory, stack);
            } else {
                amount += stack.getAmount();
            }
        }
        this.itemAmount = amount;
    }
}