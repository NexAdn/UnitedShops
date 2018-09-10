package me.nexadn.unitedshops.shop.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.bukkit.inventory.meta.ItemMeta;

import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.ui.Pager;
import me.nexadn.unitedshops.ui.PagerItem;

public class Vendor implements PagerItem {
    private UnitedShops plugin;
    private Player owner;
    private String label;
    private float rating;
    private double buyVolume;
    private double sellVolume;
    private HashMap<Material, Offer> offers;
    private File saveFile;
    private double storedMoney;
    private ItemStack icon = new ItemStack(Material.CHEST);

    private Pager vendorOfferMenu;

    private static Pager globalOfferMenu;
    private static HashMap<Material, Pager> globalOfferMenus = new HashMap<>();
    private static Pager globalVendorMenu;
    // Global icon for all Vendors in the Pager
    private static HashMap<Player, Vendor> vendors = new HashMap<>();

    private static double globalBuyVolume = 0.;
    private static double globalSellVolume = 0.;

    /**
     * Create a new Vendor
     *
     * @param creator          The creator and initial owner
     * @param vendorLabel      The label of the
     * @param vendorDataFolder The data folder where the vendor's yml file will be
     *                         saved
     */
    public Vendor(UnitedShops plugin, Player creator, String vendorLabel, File vendorDataFolder) {
        this.plugin = plugin;
        this.buyVolume = 0.;
        this.sellVolume = 0.;
        this.rating = 0f;
        this.owner = creator;
        this.label = vendorLabel;
        this.offers = new HashMap<>();
        this.saveFile = new File(vendorDataFolder, creator.getUniqueId().toString() + ".yml");

        vendors.put(creator, this);

        this.updateIcon();
        this.updateVendorOfferMenu();

        updateGlobalOfferMenu(this.plugin);
        updateGlobalVendorMenu(this.plugin);
    }

    /**
     * Load an existing Vendor YAML file
     *
     * @param dataFile The data file PLAYERUUID.yml
     */
    public Vendor(UnitedShops plugin, File dataFile) {
        this.plugin = plugin;
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(dataFile);
        try {
            Player player = Bukkit.getPlayer(UUID.fromString(configuration.getString("vendor.name")));
        } catch (NullPointerException e) {
            this.plugin.log(Level.SEVERE, "Failed to load user config!");
        }
    }

    public void init() {
        // TODO: Init all Offers
    }

    public void addOffer(Offer o) {
        // TODO
    }

    public Offer getOrCreateOffer(Material type) {
        // TODO Improve
        if (!this.offers.containsKey(type)) {
            Offer offer = new Offer(this.plugin, this, this.owner, type, 1., 1., 3);
            this.offers.put(type, offer);
            this.updateVendorOfferMenu();
            updateGlobalOfferMenu(this.plugin);
        }

        return this.offers.get(type);
    }

    @Override
    public void call(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(this.getVendorMenu());
    }

    @Override
    public ItemStack getIcon() {
        return this.icon;
    }

    public HashMap<Material, Offer> getOffers() {
        return this.offers;
    }

    public Player getPlayer() {
        return this.owner;
    }

    public UUID getPlayerUUID() {
        return this.owner.getUniqueId();
    }

    public float getRating() {
        return this.rating;
    }

    public Inventory getVendorMenu() {
        return this.vendorOfferMenu.getFirstInventory();
    }

    public void onOfferBuy(Offer o, int amount) {
        double volume = o.getBuyPrice() * amount;
        this.buyVolume += volume;
        globalBuyVolume += volume;
        this.updateRating();
        updateGlobalVendorMenu(this.plugin);
    }

    public void onOfferSell(Offer o, int amount) {
        double volume = o.getSellPrice() * amount;
        this.sellVolume += volume;
        globalSellVolume += volume;
        this.updateRating();
        this.updateIcon();
        updateGlobalVendorMenu(this.plugin);
    }

    private void saveToDisk() {
        YamlConfiguration yamlConf = YamlConfiguration.loadConfiguration(this.saveFile);
        yamlConf.createSection("vendor");
        yamlConf.set("vendor.name", this.owner.getUniqueId().toString());
        yamlConf.set("vendor.label", this.label);
        yamlConf.set("vendor.rating", this.rating);
        yamlConf.set("vendor.buyVolume", this.buyVolume);
        yamlConf.set("vendor.sellVolume", this.sellVolume);
        yamlConf.createSection("vendor.offers");
        for (Offer o : this.offers.values()) {
            o.saveToConfig(yamlConf.createSection(o.getIcon().getType().toString()));
        }
        try {
            yamlConf.save(this.saveFile);
        } catch (IOException e) {
            this.plugin.log(Level.SEVERE,
                    "Error whilst saving vendor file for user " + this.owner.getDisplayName() + "!");
            e.printStackTrace();
        }
    }

    public void storeMoney(double amount) {
        this.storedMoney += amount;
    }

    public double getStoredMoney() {
        return this.storedMoney;
    }

    public boolean hasEnoughMoney(double amount) {
        return this.storedMoney >= amount;
    }

    public boolean withdrawMoney(double amount) {
        if (this.storedMoney < amount)
            return false;

        this.storedMoney += amount;
        return true;
    }

    private void updateIcon() {
        ItemMeta iconMeta = this.icon.getItemMeta();
        iconMeta.setDisplayName(this.label);
        iconMeta.setLore(Arrays.asList("Rating: " + this.rating));
        this.icon.setItemMeta(iconMeta);
    }

    private void updateRating() {
        // RMS of share of global trade volumes
        this.rating = 100f * (float) Math.sqrt(
                (Math.pow(this.buyVolume / globalBuyVolume, 2) + Math.pow(this.sellVolume / globalSellVolume, 2)) / 2.);
        if (Float.isNaN(this.rating))
            this.rating = 0.f;
    }

    private void updateVendorOfferMenu() {
        List<Offer> offers = new ArrayList<>(this.offers.values());
        offers.sort(new OfferBuyComparator());

        this.vendorOfferMenu = new Pager(this.plugin, offers, this.owner.getDisplayName());
    }

    public static Inventory getGlobalOfferMenu() {
        return globalOfferMenu.getFirstInventory();
    }

    public static Inventory getGlobalVendorMenu() {
        return globalVendorMenu.getFirstInventory();
    }

    public static Vendor getOrCreateVendor(UnitedShops plugin, Player owner) {
        if (!vendors.containsKey(owner)) {
            vendors.put(owner,
                    new Vendor(plugin, owner, owner.getDisplayName(), new File(plugin.getDataFolder(), "vendors")));
            updateGlobalVendorMenu(plugin);
        }

        return vendors.get(owner);
    }

    public static void onDisable() {
        for (Vendor vendor : vendors.values()) {
            vendor.saveToDisk();
        }
    }

    private static void updateGlobalOfferMenu(UnitedShops plugin) {
        HashMap<Material, List<Offer>> offerMap = new HashMap<>();
        Material tmpM;
        for (Vendor vendor : vendors.values()) {
            for (Offer offer : vendor.getOffers().values()) {
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
        for (Material material : offerMap.keySet()) {
            globalOfferMenuItemMap.put(material, new PagerItem() {
                @Override
                public ItemStack getIcon() {
                    return new ItemStack(material);
                }

                @Override
                public void call(InventoryClickEvent e) {
                    e.getWhoClicked().openInventory(globalOfferMenus.get(material).getFirstInventory());
                }
            });
            globalOfferMenuItemList.add(globalOfferMenuItemMap.get(material));
        }

        globalOfferMenu = new Pager(plugin, globalOfferMenuItemList, "Offers by items");

        for (List<Offer> list : offerMap.values()) {
            list.sort(new OfferBuyComparator());
            if (list.size() > 0) {
                // TODO Localized title, parent inventory
                globalOfferMenus.put(list.get(0).getIcon().getType(),
                        new Pager(plugin, list,
                                "TODO GlobalOfferBuyMenu – " + list.get(0).getIcon().getType().toString(), 3,
                                globalOfferMenu.getFirstInventory()));
            }
        }
    }

    private static void updateGlobalVendorMenu(UnitedShops plugin) {
        List<Vendor> vendorList = new ArrayList<>(vendors.values());
        vendorList.sort((Vendor lhs, Vendor rhs) -> {
            return lhs.getRating() > rhs.getRating() ? 1 : lhs.getRating() < rhs.getRating() ? -1 : 0;
        });
        globalVendorMenu = new Pager(plugin, vendorList, "Vendor menu", 5);
    }
}