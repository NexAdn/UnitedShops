package me.nexadn.unitedshops.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nexadn.unitedshops.LocalizationHandler;
import me.nexadn.unitedshops.Pair;
import me.nexadn.unitedshops.UnitedShops;
import me.nexadn.unitedshops.Util;

public class Pager implements Listener {
    private UnitedShops plugin;

    private List<? extends PagerItem> items = new ArrayList<>();
    private List<Inventory> uiInventorys = new ArrayList<>();
    private String title;
    private Inventory parent;
    private int menuButtonSettings = MenuButton.CLOSE;
    private int rowsPerPage;
    /** Slots reserved for menu Buttons (add 9 per row, only increment if needed) */
    private final int menuRowSlots = 9;

    /**
     * Create and initialize a pager object
     * 
     * @param plugin
     * @param pagerItems The pager's content
     * @param title      Inventory title
     */
    public Pager(UnitedShops plugin, List<? extends PagerItem> pagerItems, String title) {
        this(plugin, pagerItems, title, null);
    }

    /**
     * Create and initialize a pager object
     * 
     * @param plugin
     * @param pagerItems The pager's content
     * @param title      Inventory title
     * @param parent     The parent inventory (opened when clicking menu button
     *                   "UP")
     */
    public Pager(UnitedShops plugin, List<? extends PagerItem> pagerItems, String title, Inventory parent) {
        this(plugin, pagerItems, title, 3, parent);
    }

    /**
     * Create and initialize a pager object
     * 
     * @param plugin
     * @param pagerItems  The pager's content
     * @param title       Inventory title
     * @param rowsPerPage Rows of content items per page
     */
    public Pager(UnitedShops plugin, List<? extends PagerItem> pagerItems, String title, int rowsPerPage) {
        this(plugin, pagerItems, title, rowsPerPage, null);
    }

    /**
     * Create and initialize a pager object
     * 
     * @param plugin
     * @param pagerItems  The pager's content
     * @param title       Inventory title
     * @param rowsPerPage Rows of content items per page
     * @param parent      The parent inventory (opened when clicking menu button
     *                    "UP")
     */
    public Pager(UnitedShops plugin, List<? extends PagerItem> pagerItems, String title, int rowsPerPage,
            Inventory parent) {
        this.plugin = plugin;
        this.items = pagerItems;
        this.rowsPerPage = rowsPerPage;
        this.parent = parent;
        this.title = title;
        this.init();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void setParent(Inventory parent) {
        this.parent = parent;
    }

    public int getInventoryCount() {
        return calculateInventoryCount(this.items.size(), this.rowsPerPage);
    }

    public static int calculateInventoryCount(int items, int rowsPerPage) {
        return (int) Math.ceil((items / (9 * (double) rowsPerPage)));
    }

    public int getRowsPerPage() {
        return this.rowsPerPage;
    }

    public void init() {
        try {
            int inventoryCount = this.getInventoryCount();
            if (inventoryCount > 1)
                this.menuButtonSettings |= MenuButton.PREV | MenuButton.NEXT;
            if (this.parent != null)
                this.menuButtonSettings |= MenuButton.UP;

            this.uiInventorys.clear();

            for (int i = 0; i < inventoryCount; ++i) {
                this.uiInventorys.add(Bukkit.createInventory(null, 9 * this.rowsPerPage + this.menuRowSlots, this.title
                        + (inventoryCount > 1 ? " (" + Integer.toString(i + 1) + "/" + inventoryCount + ")" : "")));
                List<ItemStack> bar = MenuButton.createIconBar(this.plugin.getL10n(), this.parent, i + 1,
                        inventoryCount, this.menuButtonSettings);
                for (int j = 0; j < this.menuRowSlots; ++j) {
                    this.uiInventorys.get(i).setItem(j + (this.rowsPerPage * 9), bar.get(j));
                }
            }

            for (int i = 0; i < this.items.size(); ++i) {
                int page = Math.floorDiv(i, 9 * this.rowsPerPage);
                if (this.items.get(i) != null)
                    this.uiInventorys.get(page).setItem(i - (page * 9 * this.rowsPerPage), this.items.get(i).getIcon());
            }
        } catch (ArithmeticException e) {
            this.plugin.log(Level.SEVERE, "Division by zero!");
            e.printStackTrace();
        }
    }

    public Inventory getFirstInventory() {
        if (this.uiInventorys.size() > 0) {
            return this.uiInventorys.get(0);
        } else {
            return null;
        }
    }

    private void callItem(int index, InventoryClickEvent event) {
        if (this.items.get(index) != null)
            this.items.get(index).call(event);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        for (Inventory i : this.uiInventorys) {
            if (i.equals(event.getInventory())) {
                event.setCancelled(true);
                int row = (event.getSlot() - event.getSlot() % 9) / 9;
                if (row >= this.rowsPerPage) {
                    // menu buttons
                    int col = event.getSlot() % 9;
                    int button = 1 << col;
                    if ((button & MenuButton.PREV & this.menuButtonSettings) > 0) {
                        // Previous
                        if (this.uiInventorys.lastIndexOf(event.getInventory()) > 0)
                            event.getWhoClicked()
                                    .openInventory(this.uiInventorys.get(this.uiInventorys.lastIndexOf(i) - 1));
                    } else if ((button & MenuButton.UP & this.menuButtonSettings) > 0) {
                        // Up
                        if (this.parent != null) {
                            event.getWhoClicked().openInventory(this.parent);
                        }
                    } else if ((button & MenuButton.CLOSE & this.menuButtonSettings) > 0) {
                        // Close
                        event.getWhoClicked().closeInventory();
                    } else if ((button & MenuButton.NEXT & this.menuButtonSettings) > 0) {
                        // Next
                        int nextindex = this.uiInventorys.lastIndexOf(i) + 1;
                        if (nextindex < this.uiInventorys.size()) {
                            event.getWhoClicked().openInventory(this.uiInventorys.get(nextindex));
                        }
                    }

                } else {
                    // UI item
                    int page = this.uiInventorys.lastIndexOf(i);
                    int slot = page * this.rowsPerPage * 9 + event.getSlot();
                    callItem(slot, event);
                }
            }
        }
    }

    public List<Inventory> getInventorys() {
        return this.uiInventorys;
    }

    /** Flags for the menuButton */
    public static class MenuButton {
        // 1 << Column → Flag
        final public static int PREV = 0x01; // 0
        final public static int UP = 0x08; // 3
        final public static int CLOSE = 0x20; // 5
        final public static int NEXT = 0x0100; // 8

        public static List<ItemStack> createIconBar(LocalizationHandler l10n, Inventory parent, int currentPage,
                int lastPage, int flags) {
            List<ItemStack> bar = new ArrayList<ItemStack>();
            for (int i = 0; i < 9; ++i) {
                bar.add(i, Util.getBlank());
            }
            if ((PREV & flags) > 0 && currentPage > 1) {
                bar.set(0, Util.getItem(new Pair<>(Material.PAPER, (short) 0), 1, "<-",
                        l10n.getMessage("pagerPrevLore").arg("prevPageNo", "" + (currentPage - 1)).str()));
            }
            if ((UP & flags) > 0 && parent != null) {
                bar.set(3, Util.getItem(new Pair<>(Material.PAPER, (short) 0), 1, l10n.getMessage("pagerUp").str(),
                        l10n.getMessage("pagerUpLore").arg("parentInvTitle", parent.getTitle()).str()));
            }
            if ((CLOSE & flags) > 0) {
                bar.set(5, Util.getItem(new Pair<>(Material.BARRIER, (short) 0), 1, l10n.getMessage("pagerClose").str(),
                        l10n.getMessage("pagerCloseLore").str()));
            }
            if ((NEXT & flags) > 0 && currentPage < lastPage) {
                bar.set(8, Util.getItem(new Pair<>(Material.PAPER, (short) 0), 1, "->",
                        l10n.getMessage("pagerNextLore").arg("nextPageNo", "" + (currentPage + 1)).str()));
            }
            return bar;
        }
    }
}
