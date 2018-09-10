package me.nexadn.unitedshops.ui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.shop.ShopObject;
import me.nexadn.unitedshops.shop.admin.AdminShop;
import me.nexadn.unitedshops.shop.admin.AdminShopItem;

public class AutoSellGui implements Listener {
    private UnitedShops plugin;
    private Player player;
    private Inventory inv;

    private static HashMap<Player, AutoSellGui> guis = new HashMap<>();

    private AutoSellGui(UnitedShops plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inv = Bukkit.createInventory(null, 18, this.plugin.getL10n().getMessage("autosellTitle")
                .arg("playername", this.player.getDisplayName()).str());

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this.inv)) {
            for (ItemStack i : e.getInventory().getContents()) {
                if (i == null)
                    continue;

                for (ShopObject object : this.plugin.getMenuGui().getAdminShop().getItems()) {
                    if (handleShopObject(this.plugin, this.player, i, this.inv, object))
                        break;
                }

                this.plugin.sendMessage(this.player, this.plugin.getL10n().getMessage("autosellItemFailed")
                        .arg("item", i.getType().toString() + ":" + i.getDurability()).str());
            }
        }
    }

    private static boolean handleShopObject(UnitedShops plugin, Player p, ItemStack currentItem, Inventory inv,
            ShopObject object) {
        if (object instanceof AdminShopItem) {
            AdminShopItem asi = (AdminShopItem) object;
            Pair<Material, Short> itemType = asi.getItemType();
            if (asi.getPriceSell() >= 0. && itemType.first.toString().equalsIgnoreCase(currentItem.getType().toString())
                    && itemType.second.shortValue() == currentItem.getDurability()) {
                if (plugin.getTradeManager().tradeOfferMoneyForItem(p, asi.getPriceSell() * currentItem.getAmount(),
                        currentItem, inv)) {
                    return true;
                }
            }
        } else if (object instanceof AdminShop) {
            for (ShopObject o : ((AdminShop) object).getItems()) {
                if (handleShopObject(plugin, p, currentItem, inv, o))
                    return true;
            }
        }
        return false;
    }

    public static AutoSellGui getGui(UnitedShops plugin, Player p) {
        if (guis.containsKey(p)) {
            return guis.get(p);
        } else {
            guis.put(p, new AutoSellGui(plugin, p));
            return getGui(plugin, p);
        }
    }
}
