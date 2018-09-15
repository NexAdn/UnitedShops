package me.nexadn.unitedshops.testutil;

import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.avaje.ebean.config.ServerConfig;

@SuppressWarnings("deprecation")
public class TestServer implements Server {

    private PluginManager pluginManager;
    private ServicesManager servicesManager;
    private ItemFactory itemFactory;

    public TestServer() {
        this.pluginManager = PowerMockito.mock(PluginManager.class);
        this.servicesManager = PowerMockito.mock(ServicesManager.class);
        this.itemFactory = PowerMockito.mock(ItemFactory.class);

        when(this.itemFactory.getItemMeta((Material) Mockito.any())).thenReturn(PowerMockito.spy(new ItemMeta() {

            private String displayName;
            private List<String> lore;

            @Override
            public ItemMeta clone() {
                return this;
            }

            @Override
            public Map<String, Object> serialize() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void setLore(List<String> lore) {
                this.lore = lore;
            }

            @Override
            public void setDisplayName(String name) {
                this.displayName = name;
            }

            @Override
            public void removeItemFlags(ItemFlag... itemFlags) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean removeEnchant(Enchantment ench) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean hasLore() {
                return this.lore != null && !this.lore.isEmpty();
            }

            @Override
            public boolean hasItemFlag(ItemFlag flag) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean hasEnchants() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean hasEnchant(Enchantment ench) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean hasDisplayName() {
                return this.displayName != null && !this.displayName.equalsIgnoreCase("");
            }

            @Override
            public boolean hasConflictingEnchant(Enchantment ench) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public List<String> getLore() {
                return this.lore;
            }

            @Override
            public Set<ItemFlag> getItemFlags() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Map<Enchantment, Integer> getEnchants() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getEnchantLevel(Enchantment ench) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getDisplayName() {
                return this.displayName;
            }

            @Override
            public void addItemFlags(ItemFlag... itemFlags) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
                // TODO Auto-generated method stub
                return false;
            }
        }));
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> getListeningPluginChannels() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getBukkitVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player[] _INVALID_getOnlinePlayers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMaxPlayers() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getViewDistance() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getIp() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServerName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServerId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWorldType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getGenerateStructures() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowEnd() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowNether() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasWhitelist() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWhitelist(boolean value) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reloadWhitelist() {
        // TODO Auto-generated method stub

    }

    @Override
    public int broadcastMessage(String message) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getUpdateFolder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getUpdateFolderFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getConnectionThrottle() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Player getPlayer(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player getPlayerExact(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Player> matchPlayer(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player getPlayer(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public BukkitScheduler getScheduler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Override
    public List<World> getWorlds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public World createWorld(WorldCreator creator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean unloadWorld(String name, boolean save) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean unloadWorld(World world, boolean save) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public World getWorld(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public World getWorld(UUID uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MapView getMap(short id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MapView createMap(World world) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub

    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("TestServer");
    }

    @Override
    public PluginCommand getPluginCommand(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void savePlayers() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void configureDbConfig(ServerConfig config) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Recipe> getRecipesFor(ItemStack result) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<Recipe> recipeIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clearRecipes() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetRecipes() {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSpawnRadius() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSpawnRadius(int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getOnlineMode() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowFlight() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isHardcore() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean useExactLoginLocation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

    @Override
    public int broadcast(String message, String permission) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getIPBans() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void banIP(String address) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unbanIP(String address) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BanList getBanList(Type type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameMode getDefaultGameMode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultGameMode(GameMode mode) {
        // TODO Auto-generated method stub

    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getWorldContainer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Messenger getMessenger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HelpMap getHelpMap() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, InventoryType type) {
        return this.createInventory(owner, type, "");
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
        return this.createInventory(owner, 3 * 9, title);
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
        return this.createInventory(owner, size, "Inventory");
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, final int size, final String title)
            throws IllegalArgumentException {
        return PowerMockito.spy(new Inventory() {

            private int storageSize = size;
            private ItemStack[] itemStacks = new ItemStack[this.storageSize];

            @Override
            public void setMaxStackSize(int size) {

            }

            @Override
            public void setItem(int index, ItemStack item) {
                this.itemStacks[index] = item;
            }

            @Override
            public void setContents(ItemStack[] items) throws IllegalArgumentException {
                if (items.length != this.itemStacks.length)
                    throw new IllegalArgumentException("Invalid amount of ItemStacks");

                this.itemStacks = items;
            }

            @Override
            public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void remove(ItemStack item) {
                for (int i = 0; i < this.itemStacks.length; i++) {
                    if (this.itemStacks[i].equals(item)) {
                        this.itemStacks[i] = null;
                    }
                }
            }

            @Override
            public void remove(Material material) throws IllegalArgumentException {
                for (int i = 0; i < this.itemStacks.length; i++) {
                    if (this.itemStacks[i].getType().equals(material)) {
                        this.itemStacks[i] = null;
                    }
                }
            }

            @Override
            public void remove(int materialId) {
                this.remove(Material.getMaterial(materialId));
            }

            @Override
            public ListIterator<ItemStack> iterator(int index) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ListIterator<ItemStack> iterator() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<HumanEntity> getViewers() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public InventoryType getType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public int getSize() {
                return this.storageSize;
            }

            @Override
            public String getName() {
                return title;
            }

            @Override
            public int getMaxStackSize() {
                return 64;
            }

            @Override
            public ItemStack getItem(int index) {
                return this.itemStacks[index];
            }

            @Override
            public InventoryHolder getHolder() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ItemStack[] getContents() {
                return this.itemStacks;
            }

            @Override
            public int firstEmpty() {
                for (int i = 0; i < this.itemStacks.length; i++) {
                    if (this.itemStacks[i] == null)
                        return i;
                }
                return 0;
            }

            @Override
            public int first(ItemStack item) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int first(Material material) throws IllegalArgumentException {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int first(int materialId) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean containsAtLeast(ItemStack item, int amount) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(ItemStack item, int amount) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(Material material, int amount) throws IllegalArgumentException {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(int materialId, int amount) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(ItemStack item) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(Material material) throws IllegalArgumentException {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(int materialId) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear(int index) {
                this.itemStacks[index] = null;
            }

            @Override
            public void clear() {
                this.itemStacks = new ItemStack[size];
            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all(int materialId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
                HashMap<Integer, ItemStack> itemMap = new HashMap<>();
                int j = 0;
                for (int i = 0; i < this.itemStacks.length; i++) {
                    if (j >= items.length)
                        break;
                    if (this.itemStacks[i] == null) {
                        this.itemStacks[i] = items[j];
                        itemMap.put(i, items[j]);
                    }
                }
                return itemMap;
            }
        });
    }

    @Override
    public int getMonsterSpawnLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getAnimalSpawnLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getAmbientSpawnLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isPrimaryThread() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getMotd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getShutdownMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WarningState getWarningState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemFactory getItemFactory() {
        return this.itemFactory;
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon getServerIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(File file) throws IllegalArgumentException, Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(BufferedImage image) throws IllegalArgumentException, Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setIdleTimeout(int threshold) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getIdleTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UnsafeValues getUnsafe() {
        // TODO Auto-generated method stub
        return null;
    }

}
