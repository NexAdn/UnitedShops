package me.nexadn.unitedshops.shop;

import org.bukkit.inventory.Inventory;

import me.nexadn.unitedshops.ui.PagerItem;

public interface ShopObject extends PagerItem {
    void init(Inventory parent);
}
