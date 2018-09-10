package me.nexadn.unitedshops.tradeapi;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.nexadn.unitedshops.UnitedShops;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class TradeManager {
    private UnitedShops plugin;
    private Economy     economy;

    private boolean     initialized = false;

    public TradeManager(UnitedShops plugin)
    {
        this.plugin = plugin;

        RegisteredServiceProvider<Economy> economyProvider = this.plugin.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider == null)
        {
            this.initialized = false;
            this.plugin.logSevere("Failed to initialize economy hook. Is Vault missing?");
        } else
        {
            this.initialized = true;
            this.economy = economyProvider.getProvider();
        }
    }

    public boolean tradeOfferItemForMoney (Player player, ItemStack offer, double want)
    {
        if (!this.initialized)
        {
            this.plugin.sendMessage(player, this.plugin.getL10n().getMessage("transactionFailed").str());
            return false;
        }

        EconomyResponse response = null;
        double bal = this.economy.getBalance(player);
        if (want > bal)
        {
            return false;
        } else
        {
            response = this.economy.withdrawPlayer(player, want);
            if (response.transactionSuccess())
            {
                player.getInventory().addItem(offer);
                this.plugin.sendMessage(player,
                        this.plugin.getL10n().getMessage("transactionBuy")
                                .arg("item", offer.getType().toString() + ":" + offer.getDurability())
                                .arg("price", "" + want).str());
                return true;
            }
        }

        return false;
    }

    public boolean tradeOfferMoneyForItem (Player player, double offer, ItemStack want)
    {
        if (!this.initialized)
        {
            this.plugin.sendMessage(player, this.plugin.getL10n().getMessage("transactionFailed").str());
            return false;
        }

        EconomyResponse response = null;
        Inventory transactionInv = player.getInventory();
        if (transactionInv.containsAtLeast(want, want.getAmount()))
        {
            response = this.economy.depositPlayer(player, offer);
            if (response.transactionSuccess())
            {
                if (!this.removeItems(transactionInv, want))
                {
                    this.economy.withdrawPlayer(player, offer);
                    this.plugin.sendMessage(player, this.plugin.getL10n().getMessage("transactionFailed").str());
                } else
                {
                    this.plugin.sendMessage(player,
                            this.plugin.getL10n().getMessage("transactionSell")
                                    .arg("item", want.getType().toString() + ":" + want.getDurability())
                                    .arg("price", "" + offer).str());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeItems (Inventory inventory, ItemStack items)
    {
        int remaining = items.getAmount();

        if (!inventory.containsAtLeast(items, items.getAmount()))
            return false;

        for (int i = 0; i < inventory.getSize(); i++)
        {
            if (inventory.getItem(i) != null && inventory.getItem(i).getType().equals(items.getType())
                    && inventory.getItem(i).getDurability() == items.getDurability())
            {

                ItemStack iStack = inventory.getItem(i);
                if (iStack.getAmount() > remaining)
                {
                    iStack.setAmount(iStack.getAmount() - remaining);
                    inventory.setItem(i, iStack);
                    return true;
                } else
                {
                    remaining -= iStack.getAmount();
                    inventory.setItem(i, null);
                    if (remaining <= 0)
                        return true;
                }
            }
        }
        return false;
    }
}
