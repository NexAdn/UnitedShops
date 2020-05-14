package me.nexadn.unitedshops.shop.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;
import me.nexadn.unitedshops.shop.Shop;
import me.nexadn.unitedshops.shop.ShopObject;
import me.nexadn.unitedshops.ui.Pager;

public class AdminShop implements Shop {
    private UnitedShops plugin;

    private String title;
    private Pair<Material, Short> icon;
    private ItemStack iconItem;
    private List<ShopObject> items = new ArrayList<>();
    private Pager ui;

    public AdminShop(UnitedShops plugin, String title, Pair<Material, Short> icon) {
        this.plugin = plugin;
        this.title = ChatColor.BLUE + title;
        this.icon = icon;
        this.iconItem = Util.getItem(this.icon, 1, this.title);
    }

    public void addItem(ShopObject item) {
        this.items.add(item);
    }

    @Override
    public void init(Inventory parent) {
        this.ui = new Pager(this.plugin, this.items, this.title, parent);
        for (ShopObject item : this.items)
            item.init(this.ui.getFirstInventory());
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public List<ShopObject> getItems() {
        return this.items;
    }

    @Override
    public void call(InventoryClickEvent e) {
        if (this.ui != null) {
            e.getWhoClicked().openInventory(this.ui.getFirstInventory());
        }
    }

    @Override
    public ItemStack getIcon() {
        return this.iconItem;
    }
}
