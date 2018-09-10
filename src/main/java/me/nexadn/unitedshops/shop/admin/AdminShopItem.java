package me.nexadn.unitedshops.shop.admin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;
import me.nexadn.unitedshops.shop.ShopObject;
import me.nexadn.unitedshops.ui.Pager;
import me.nexadn.unitedshops.ui.PagerItem;

public class AdminShopItem implements ShopObject {
    private UnitedShops plugin;

    private Pager ui;

    private double priceBuy;
    private double priceSell;
    private Pair<Material, Short> itemType;

    public AdminShopItem(UnitedShops plugin, Pair<Material, Short> itemType, double buy, double sell) {
        this.plugin = plugin;
        this.priceBuy = buy;
        this.priceSell = sell;
        this.itemType = itemType;
    }

    @Override
    public void init(Inventory parent) {
        List<PagerItem> actionbar = Arrays.asList(new PagerItem[] { (this.priceBuy < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiBuy").arg("amount", "1").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceBuy * 1).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO BUY 1
                if (e.getWhoClicked() instanceof Player) {
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferItemForMoney((Player) e.getWhoClicked(),
                            new ItemStack(AdminShopItem.this.itemType.first, 1, AdminShopItem.this.itemType.second),
                            AdminShopItem.this.priceBuy * 1);
                }
            }
        }, (this.priceBuy < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiBuy").arg("amount", "16").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceBuy * 16).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO BUY 16
                if (e.getWhoClicked() instanceof Player)
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferItemForMoney((Player) e.getWhoClicked(),
                            new ItemStack(AdminShopItem.this.itemType.first, 16, AdminShopItem.this.itemType.second),
                            AdminShopItem.this.priceBuy * 16);

            }
        }, (this.priceBuy < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiBuy").arg("amount", "64").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceBuy * 64).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO BUY 64
                if (e.getWhoClicked() instanceof Player)
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferItemForMoney((Player) e.getWhoClicked(),
                            new ItemStack(AdminShopItem.this.itemType.first, 64, AdminShopItem.this.itemType.second),
                            AdminShopItem.this.priceBuy * 64);

            }
        }, null, null, null, (this.priceSell < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiSell").arg("amount", "1").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceSell * 1).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO SELL 1
                if (e.getWhoClicked() instanceof Player)
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferMoneyForItem((Player) e.getWhoClicked(),
                            AdminShopItem.this.priceSell * 1,
                            new ItemStack(AdminShopItem.this.itemType.first, 1, AdminShopItem.this.itemType.second));

            }
        }, (this.priceSell < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiSell").arg("amount", "16").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceSell * 16).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO SELL 16
                if (e.getWhoClicked() instanceof Player)
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferMoneyForItem((Player) e.getWhoClicked(),
                            AdminShopItem.this.priceSell * 16,
                            new ItemStack(AdminShopItem.this.itemType.first, 16, AdminShopItem.this.itemType.second));

            }
        }, (this.priceSell < 0.) ? null : new PagerItem() {

            @Override
            public ItemStack getIcon() {
                return Util.getItem(AdminShopItem.this.itemType, 1,
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiSell").arg("amount", "64").str(),
                        AdminShopItem.this.plugin.getL10n().getMessage("transactionGuiPrice")
                                .arg("price", "" + AdminShopItem.this.priceSell * 64).str());
            }

            @Override
            public void call(InventoryClickEvent e) {
                // TODO SELL 64
                if (e.getWhoClicked() instanceof Player)
                    AdminShopItem.this.plugin.getTradeManager().tradeOfferMoneyForItem((Player) e.getWhoClicked(),
                            AdminShopItem.this.priceSell * 64,
                            new ItemStack(AdminShopItem.this.itemType.first, 64, AdminShopItem.this.itemType.second));

            }
        } });
        this.ui = new Pager(this.plugin, actionbar, this.itemType.first.toString(), 1, parent);

    }

    public double getPriceBuy() {
        return this.priceBuy;
    }

    public double getPriceSell() {
        return this.priceSell;
    }

    public Pair<Material, Short> getItemType() {
        return this.itemType;
    }

    @Override
    public void call(InventoryClickEvent e) {
        if (this.ui != null) {
            e.getWhoClicked().openInventory(this.ui.getFirstInventory());
        }

    }

    @Override
    public ItemStack getIcon() {
        return Util.getItem(this.itemType, 1, "",
                this.plugin.getL10n().getMessage("transactionGuiPrice").arg("price", "" + this.priceBuy).str());
    }
}
