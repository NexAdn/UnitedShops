package io.github.nexadn.unitedshops.usershop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.ui.Pager;
import io.github.nexadn.unitedshops.ui.PagerItem;

public class Vendor implements PagerItem {
    private Player                          owner;
    private String                          label;
    private float                           rating;
    private double                          buyVolume;
    private double                          sellVolume;
    private HashMap<Material, Offer>        offers;
    private File                            saveFile;
    private double                          storedMoney;

    private Pager                           vendorOfferMenu;

    private static Pager                    globalOfferMenu;
    private static HashMap<Material, Pager> globalOfferMenus           = new HashMap<>();
    private static Pager                    globalVendorMenu;
    // Global icon for all Vendors in the Pager
    private static final ItemStack          icon                       = new ItemStack(Material.CHEST);
    private static HashMap<Player, Vendor>  vendors                    = new HashMap<>();

    private static double                   globalBuyVolume            = 0.;
    private static double                   globalSellVolume           = 0.;

    private static final int                pagerFlagsGlobalOfferMenu  = Pager.MenuButton.PREV | Pager.MenuButton.NEXT
            | Pager.MenuButton.CLOSE;
    private static final int                pagerFlagsGlobalOfferMenus = Pager.MenuButton.PREV | Pager.MenuButton.NEXT
            | Pager.MenuButton.CLOSE | Pager.MenuButton.UP;
    private static final int                pagerFlagsGlobalVendorMenu = Pager.MenuButton.PREV | Pager.MenuButton.NEXT
            | Pager.MenuButton.CLOSE;
    private static final int                pagerFlagsVendorMenu       = Pager.MenuButton.PREV | Pager.MenuButton.NEXT
            | Pager.MenuButton.CLOSE;

    /**
     * Create a new Vendor
     *
     * @param creator
     *            The creator and initial owner
     * @param vendorLabel
     *            The label of the
     * @param vendorDataFolder
     *            The data folder where the vendor's yml file will be saved
     */
    public Vendor(Player creator, String vendorLabel, File vendorDataFolder)
    {
        this.buyVolume = 0.;
        this.sellVolume = 0.;
        this.rating = 0f;
        this.owner = creator;
        this.label = vendorLabel;
        this.offers = new HashMap<>();
        this.saveFile = new File(vendorDataFolder, creator.getUniqueId().toString() + ".yml");

        vendors.put(creator, this);

        this.updateVendorOfferMenu();

        updateGlobalOfferMenu();
        updateGlobalVendorMenu();
    }

    /**
     * Load an existing Vendor YAML file
     *
     * @param dataFile
     *            The data file PLAYERUUID.yml
     */
    public Vendor(File dataFile)
    {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(dataFile);
        try
        {
            Player player = Bukkit.getPlayer(UUID.fromString(configuration.getString("vendor.name")));
        } catch (NullPointerException e)
        {
            UnitedShops.plugin.log(Level.SEVERE, "Failed to load user config!");
        }
    }

    public void init ()
    {
        // TODO: Init all Offers
    }

    public void addOffer (Offer o)
    {
        // TODO
    }

    @Override
    public void call (InventoryClickEvent e)
    {
        // TODO
    }

    @Override
    public ItemStack getIcon ()
    {
        return icon;
    }

    public HashMap<Material, Offer> getOffers ()
    {
        return this.offers;
    }

    public Player getPlayer ()
    {
        return this.owner;
    }

    public UUID getPlayerUUID ()
    {
        return this.owner.getUniqueId();
    }

    public float getRating ()
    {
        return this.rating;
    }

    public Inventory getVendorMenu ()
    {
        return this.vendorOfferMenu.getFirstInventory();
    }

    public void onOfferBuy (Offer o, int amount)
    {
        double volume = o.getBuyPrice() * amount;
        this.buyVolume += volume;
        globalBuyVolume += volume;
        this.updateRating();
    }

    public void onOfferSell (Offer o, int amount)
    {
        double volume = o.getSellPrice() * amount;
        this.sellVolume += volume;
        globalSellVolume += volume;
        this.updateRating();
    }

    private void saveToDisk ()
    {
        YamlConfiguration yamlConf = YamlConfiguration.loadConfiguration(this.saveFile);
        yamlConf.createSection("vendor");
        yamlConf.set("vendor.name", this.owner.getUniqueId().toString());
        yamlConf.set("vendor.label", this.label);
        yamlConf.set("vendor.rating", this.rating);
        yamlConf.set("vendor.buyVolume", this.buyVolume);
        yamlConf.set("vendor.sellVolume", this.sellVolume);
        yamlConf.createSection("vendor.offers");
        for (Offer o : this.offers.values())
        {
            o.saveToConfig(yamlConf.createSection(o.getIcon().getType().toString()));
        }
    }

    public void storeMoney (double amount)
    {
        this.storedMoney += amount;
    }

    public boolean hasEnoughMoney (double amount)
    {
        return this.storedMoney >= amount;
    }

    public boolean withdrawMoney (double amount)
    {
        if (this.storedMoney < amount)
            return false;

        this.storedMoney += amount;
        return true;
    }

    private void updateRating ()
    {
        // RMS of share of global trade volumes
        this.rating = 100f * (float) Math.sqrt(
                (Math.pow(this.buyVolume / globalBuyVolume, 2) + Math.pow(this.sellVolume / globalSellVolume, 2)) / 2.);
    }

    private void updateVendorOfferMenu ()
    {
        List<Offer> offers = new ArrayList<>(this.offers.values());
        offers.sort(new OfferBuyComparator());

        this.vendorOfferMenu = new Pager(offers, pagerFlagsVendorMenu, this.owner.getDisplayName());
    }

    private static void updateGlobalOfferMenu ()
    {
        HashMap<Material, List<Offer>> offerMap = new HashMap<>();
        Material tmpM;
        for (Vendor vendor : vendors.values())
        {
            for (Offer offer : vendor.getOffers().values())
            {
                tmpM = offer.getIcon().getType();
                if (!offerMap.containsKey(tmpM))
                    offerMap.put(tmpM, new ArrayList<>());
                offerMap.get(tmpM).add(offer);
            }
        }

        // TODO figure out how to avoid using two containers
        // TODO sort items
        HashMap<Material, PagerItem> globalOfferMenuItemMap = new HashMap<>();
        List<PagerItem> globalOfferMenuItemList = new ArrayList<>();
        for (Material material : offerMap.keySet())
        {
            globalOfferMenuItemMap.put(material, new PagerItem() {
                @Override
                public ItemStack getIcon ()
                {
                    return new ItemStack(material);
                }

                @Override
                public void call (InventoryClickEvent e)
                {
                    e.getWhoClicked().openInventory(globalOfferMenus.get(material).getFirstInventory());
                }
            });
            globalOfferMenuItemList.add(globalOfferMenuItemMap.get(material));
        }

        globalOfferMenu = new Pager(globalOfferMenuItemList, pagerFlagsGlobalOfferMenu, "Offers by items");

        for (List<Offer> list : offerMap.values())
        {
            list.sort(new OfferBuyComparator());
            if (list.size() > 0)
            {
                // TODO Localized title, parent inventory
                globalOfferMenus.put(list.get(0).getIcon().getType(),
                        new Pager(list, pagerFlagsGlobalOfferMenus,
                                "TODO GlobalOfferBuyMenu – " + list.get(0).getIcon().getType().toString(), 3,
                                globalOfferMenu.getFirstInventory()));
            }
        }
    }

    private static void updateGlobalVendorMenu ()
    {
        List<Vendor> vendorList = new ArrayList<>(vendors.values());
        vendorList.sort( (Vendor lhs, Vendor rhs) -> {
            return lhs.getRating() > rhs.getRating() ? 1 : lhs.getRating() < rhs.getRating() ? -1 : 0;
        });
        globalVendorMenu = new Pager(vendorList,
                Pager.MenuButton.NEXT | Pager.MenuButton.PREV | Pager.MenuButton.CLOSE | Pager.MenuButton.UP,
                "Vendor menu", 5);
    }
}

/*
 * Copyright (C) 2017, 2018 Adrian Schollmeyer
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
