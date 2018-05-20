package io.github.nexadn.unitedshops;

import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.Returns;
import org.powermock.api.mockito.PowerMockito;

import com.avaje.ebean.config.ServerConfig;

public class TestServer implements Server {

    private PluginManager   pluginManager;
    private ServicesManager servicesManager;
    private ItemFactory     itemFactory;

    public TestServer()
    {
        this.pluginManager = new PluginManager() {

            @Override
            public boolean useTimings ()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void unsubscribeFromPermission (String permission, Permissible permissible)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void unsubscribeFromDefaultPerms (boolean op, Permissible permissible)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void subscribeToPermission (String permission, Permissible permissible)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void subscribeToDefaultPerms (boolean op, Permissible permissible)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void removePermission (String name)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void removePermission (Permission perm)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void registerInterface (Class<? extends PluginLoader> loader) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void registerEvents (Listener listener, Plugin plugin)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void registerEvent (Class<? extends Event> event, Listener listener, EventPriority priority,
                    EventExecutor executor, Plugin plugin, boolean ignoreCancelled)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void registerEvent (Class<? extends Event> event, Listener listener, EventPriority priority,
                    EventExecutor executor, Plugin plugin)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void recalculatePermissionDefaults (Permission perm)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public Plugin[] loadPlugins (File directory)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Plugin loadPlugin (File file)
                    throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isPluginEnabled (Plugin plugin)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isPluginEnabled (String name)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Plugin[] getPlugins ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Plugin getPlugin (String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Permission> getPermissions ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Permissible> getPermissionSubscriptions (String permission)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Permission getPermission (String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Permission> getDefaultPermissions (boolean op)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Permissible> getDefaultPermSubscriptions (boolean op)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void enablePlugin (Plugin plugin)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void disablePlugins ()
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void disablePlugin (Plugin plugin)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void clearPlugins ()
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void callEvent (Event event) throws IllegalStateException
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void addPermission (Permission perm)
            {
                // TODO Auto-generated method stub

            }
        };
        this.servicesManager = new ServicesManager() {

            @Override
            public void unregisterAll (Plugin plugin)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void unregister (Class<?> service, Object provider)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void unregister (Object provider)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public <T> void register (Class<T> service, T provider, Plugin plugin, ServicePriority priority)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public <T> T load (Class<T> service)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T> boolean isProvidedFor (Class<T> service)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public <T> Collection<RegisteredServiceProvider<T>> getRegistrations (Class<T> service)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<RegisteredServiceProvider<?>> getRegistrations (Plugin plugin)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T> RegisteredServiceProvider<T> getRegistration (Class<T> service)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<Class<?>> getKnownServices ()
            {
                // TODO Auto-generated method stub
                return null;
            }
        };
        this.itemFactory = PowerMockito.mock(ItemFactory.class);
        when(this.itemFactory.getItemMeta((Material) Mockito.any())).thenReturn(PowerMockito.mock(ItemMeta.class));
        Bukkit.setServer(this);
    }

    @Override
    public void sendPluginMessage (Plugin source, String channel, byte[] message)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> getListeningPluginChannels ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVersion ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getBukkitVersion ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMaxPlayers ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPort ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getViewDistance ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getIp ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServerName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServerId ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWorldType ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getGenerateStructures ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowEnd ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowNether ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasWhitelist ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWhitelist (boolean value)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reloadWhitelist ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int broadcastMessage (String message)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getUpdateFolder ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getUpdateFolderFile ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getConnectionThrottle ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTicksPerAnimalSpawns ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTicksPerMonsterSpawns ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Player getPlayer (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player getPlayerExact (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Player> matchPlayer (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player getPlayer (UUID id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginManager getPluginManager ()
    {
        return this.pluginManager;
    }

    @Override
    public BukkitScheduler getScheduler ()
    {
        return new BukkitScheduler() {

            @Override
            public int scheduleSyncRepeatingTask (Plugin plugin, BukkitRunnable task, long delay, long period)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleSyncRepeatingTask (Plugin plugin, Runnable task, long delay, long period)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleSyncDelayedTask (Plugin plugin, BukkitRunnable task, long delay)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleSyncDelayedTask (Plugin plugin, Runnable task, long delay)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleSyncDelayedTask (Plugin plugin, BukkitRunnable task)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleSyncDelayedTask (Plugin plugin, Runnable task)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleAsyncRepeatingTask (Plugin plugin, Runnable task, long delay, long period)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleAsyncDelayedTask (Plugin plugin, Runnable task, long delay)
            {
                task.run();
                return 0;
            }

            @Override
            public int scheduleAsyncDelayedTask (Plugin plugin, Runnable task)
            {
                task.run();
                return 0;
            }

            @Override
            public BukkitTask runTaskTimerAsynchronously (Plugin plugin, BukkitRunnable task, long delay, long period)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskTimerAsynchronously (Plugin plugin, Runnable task, long delay, long period)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskTimer (Plugin plugin, BukkitRunnable task, long delay, long period)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskTimer (Plugin plugin, Runnable task, long delay, long period)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskLaterAsynchronously (Plugin plugin, BukkitRunnable task, long delay)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskLaterAsynchronously (Plugin plugin, Runnable task, long delay)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskLater (Plugin plugin, BukkitRunnable task, long delay)
                    throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskLater (Plugin plugin, Runnable task, long delay) throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskAsynchronously (Plugin plugin, BukkitRunnable task) throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTaskAsynchronously (Plugin plugin, Runnable task) throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTask (Plugin plugin, BukkitRunnable task) throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public BukkitTask runTask (Plugin plugin, Runnable task) throws IllegalArgumentException
            {
                task.run();
                return null;
            }

            @Override
            public boolean isQueued (int taskId)
            {
                return false;
            }

            @Override
            public boolean isCurrentlyRunning (int taskId)
            {
                return false;
            }

            @Override
            public List<BukkitTask> getPendingTasks ()
            {
                return new ArrayList<>();
            }

            @Override
            public List<BukkitWorker> getActiveWorkers ()
            {
                return new ArrayList<>();
            }

            @Override
            public void cancelTasks (Plugin plugin)
            {
            }

            @Override
            public void cancelTask (int taskId)
            {
            }

            @Override
            public void cancelAllTasks ()
            {
            }

            @Override
            public <T> Future<T> callSyncMethod (Plugin plugin, Callable<T> task)
            {
                try
                {
                    task.call();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public ServicesManager getServicesManager ()
    {
        return this.servicesManager;
    }

    @Override
    public List<World> getWorlds ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public World createWorld (WorldCreator creator)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean unloadWorld (String name, boolean save)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean unloadWorld (World world, boolean save)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public World getWorld (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public World getWorld (UUID uid)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MapView getMap (short id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MapView createMap (World world)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reload ()
    {
        // TODO Auto-generated method stub

    }

    public void reloadData ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Logger getLogger ()
    {
        return Logger.getGlobal();
    }

    @Override
    public PluginCommand getPluginCommand (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void savePlayers ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean dispatchCommand (CommandSender sender, String commandLine) throws CommandException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addRecipe (Recipe recipe)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Recipe> getRecipesFor (ItemStack result)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<Recipe> recipeIterator ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clearRecipes ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetRecipes ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, String[]> getCommandAliases ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSpawnRadius ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSpawnRadius (int value)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getOnlineMode ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAllowFlight ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isHardcore ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void shutdown ()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int broadcast (String message, String permission)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public OfflinePlayer getOfflinePlayer (String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer (UUID id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getIPBans ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void banIP (String address)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void unbanIP (String address)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<OfflinePlayer> getOperators ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameMode getDefaultGameMode ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultGameMode (GameMode mode)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ConsoleCommandSender getConsoleSender ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getWorldContainer ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HelpMap getHelpMap ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Inventory createInventory (InventoryHolder owner, int size) throws IllegalArgumentException
    {
        return this.createInventory(null, size, "");
    }

    @Override
    public Inventory createInventory (InventoryHolder owner, final int size, String title)
            throws IllegalArgumentException
    {
        if (size % 9 != 0)
            throw new IllegalArgumentException("Inventory size must be multiple of 9.");

        return new Inventory() {

            private ItemStack[] itemStacks = new ItemStack[size];

            public void setStorageContents (ItemStack[] items) throws IllegalArgumentException
            {
                if (items.length != this.itemStacks.length)
                    throw new IllegalArgumentException("Size of given array does not match the inventory's one!");

                this.itemStacks = items;
            }

            @Override
            public void setMaxStackSize (int size)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void setItem (int index, ItemStack item)
            {
                if (index < this.itemStacks.length && index >= 0)
                {
                    this.itemStacks[index] = item;
                }
            }

            @Override
            public void setContents (ItemStack[] items) throws IllegalArgumentException
            {
                this.setStorageContents(items);
            }

            @Override
            public HashMap<Integer, ItemStack> removeItem (ItemStack... items) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void remove (ItemStack item)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void remove (Material material) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void remove (int materialId)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public ListIterator<ItemStack> iterator (int index)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ListIterator<ItemStack> iterator ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<HumanEntity> getViewers ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public InventoryType getType ()
            {
                // TODO Auto-generated method stub
                return InventoryType.CHEST;
            }

            @Override
            public String getTitle ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            public ItemStack[] getStorageContents ()
            {
                return this.itemStacks;
            }

            @Override
            public int getSize ()
            {
                return this.itemStacks.length;
            }

            @Override
            public String getName ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getMaxStackSize ()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            public Location getLocation ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ItemStack getItem (int index)
            {
                if (index < this.itemStacks.length && index >= 0)
                    return this.itemStacks[index];

                return null;
            }

            @Override
            public InventoryHolder getHolder ()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ItemStack[] getContents ()
            {
                return this.getStorageContents();
            }

            @Override
            public int firstEmpty ()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int first (ItemStack item)
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int first (Material material) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int first (int materialId)
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean containsAtLeast (ItemStack item, int amount)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (ItemStack item, int amount)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (Material material, int amount) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (int materialId, int amount)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (ItemStack item)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (Material material) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains (int materialId)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear (int index)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void clear ()
            {
                // TODO Auto-generated method stub

            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all (ItemStack item)
            {
                return null;
            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all (Material material) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HashMap<Integer, ? extends ItemStack> all (int materialId)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HashMap<Integer, ItemStack> addItem (ItemStack... items) throws IllegalArgumentException
            {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    /*
     * public Merchant createMerchant (String title) { // TODO Auto-generated method
     * stub return null; }
     */

    @Override
    public int getMonsterSpawnLimit ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getAnimalSpawnLimit ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getWaterAnimalSpawnLimit ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getAmbientSpawnLimit ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isPrimaryThread ()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getMotd ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getShutdownMessage ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WarningState getWarningState ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemFactory getItemFactory ()
    {
        // TODO Auto-generated method stub
        return this.itemFactory;
    }

    @Override
    public ScoreboardManager getScoreboardManager ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon getServerIcon ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon (File file) throws IllegalArgumentException, Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon (BufferedImage image) throws IllegalArgumentException, Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setIdleTimeout (int threshold)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int getIdleTimeout ()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * public ChunkData createChunkData (World world) { // TODO Auto-generated
     * method stub return null; }
     *
     * public BossBar createBossBar (String title, BarColor color, BarStyle style,
     * BarFlag... flags) { // TODO Auto-generated method stub return null; }
     */

    public Entity getEntity (UUID uuid)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * public Advancement getAdvancement (NamespacedKey key) { // TODO
     * Auto-generated method stub return null; }
     */

    /*
     * public Iterator<Advancement> advancementIterator () { // TODO Auto-generated
     * method stub return null; }
     */

    @Override
    @SuppressWarnings ("deprecation")
    public UnsafeValues getUnsafe ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BanList getBanList (Type type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Messenger getMessenger ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Inventory createInventory (InventoryHolder owner, InventoryType type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Inventory createInventory (InventoryHolder owner, InventoryType type, String title)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player[] _INVALID_getOnlinePlayers ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void configureDbConfig (ServerConfig config)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean useExactLoginLocation ()
    {
        // TODO Auto-generated method stub
        return false;
    }

}

/*
 * Copyright (C) 2017 Adrian Schollmeyer
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