package io.github.nexadn.unitedshops;

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

import org.bukkit.*;
import org.bukkit.advancement.*;
import org.bukkit.boss.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.permissions.*;
import org.bukkit.plugin.*;
import org.bukkit.BanList.Type;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;
import org.bukkit.Warning.WarningState;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

public class TestServer implements Server {

	private PluginManager	pluginManager;
	private ServicesManager	servicesManager;
	private ItemFactory		itemFactory;

	public TestServer()
	{
		this.pluginManager = new PluginManager() {

			public boolean useTimings ()
			{
				// TODO Auto-generated method stub
				return false;
			}

			public void unsubscribeFromPermission (String permission, Permissible permissible)
			{
				// TODO Auto-generated method stub

			}

			public void unsubscribeFromDefaultPerms (boolean op, Permissible permissible)
			{
				// TODO Auto-generated method stub

			}

			public void subscribeToPermission (String permission, Permissible permissible)
			{
				// TODO Auto-generated method stub

			}

			public void subscribeToDefaultPerms (boolean op, Permissible permissible)
			{
				// TODO Auto-generated method stub

			}

			public void removePermission (String name)
			{
				// TODO Auto-generated method stub

			}

			public void removePermission (Permission perm)
			{
				// TODO Auto-generated method stub

			}

			public void registerInterface (Class<? extends PluginLoader> loader) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub

			}

			public void registerEvents (Listener listener, Plugin plugin)
			{
				// TODO Auto-generated method stub

			}

			public void registerEvent (Class<? extends Event> event, Listener listener, EventPriority priority,
					EventExecutor executor, Plugin plugin, boolean ignoreCancelled)
			{
				// TODO Auto-generated method stub

			}

			public void registerEvent (Class<? extends Event> event, Listener listener, EventPriority priority,
					EventExecutor executor, Plugin plugin)
			{
				// TODO Auto-generated method stub

			}

			public void recalculatePermissionDefaults (Permission perm)
			{
				// TODO Auto-generated method stub

			}

			public Plugin[] loadPlugins (File directory)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Plugin loadPlugin (File file)
					throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
			{
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isPluginEnabled (Plugin plugin)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isPluginEnabled (String name)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public Plugin[] getPlugins ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Plugin getPlugin (String name)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Set<Permission> getPermissions ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Set<Permissible> getPermissionSubscriptions (String permission)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Permission getPermission (String name)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Set<Permission> getDefaultPermissions (boolean op)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public Set<Permissible> getDefaultPermSubscriptions (boolean op)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public void enablePlugin (Plugin plugin)
			{
				// TODO Auto-generated method stub

			}

			public void disablePlugins ()
			{
				// TODO Auto-generated method stub

			}

			public void disablePlugin (Plugin plugin)
			{
				// TODO Auto-generated method stub

			}

			public void clearPlugins ()
			{
				// TODO Auto-generated method stub

			}

			public void callEvent (Event event) throws IllegalStateException
			{
				// TODO Auto-generated method stub

			}

			public void addPermission (Permission perm)
			{
				// TODO Auto-generated method stub

			}
		};
		this.servicesManager = new ServicesManager() {

			public void unregisterAll (Plugin plugin)
			{
				// TODO Auto-generated method stub

			}

			public void unregister (Class<?> service, Object provider)
			{
				// TODO Auto-generated method stub

			}

			public void unregister (Object provider)
			{
				// TODO Auto-generated method stub

			}

			public <T> void register (Class<T> service, T provider, Plugin plugin, ServicePriority priority)
			{
				// TODO Auto-generated method stub

			}

			public <T> T load (Class<T> service)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public <T> boolean isProvidedFor (Class<T> service)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public <T> Collection<RegisteredServiceProvider<T>> getRegistrations (Class<T> service)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public List<RegisteredServiceProvider<?>> getRegistrations (Plugin plugin)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public <T> RegisteredServiceProvider<T> getRegistration (Class<T> service)
			{
				// TODO Auto-generated method stub
				return null;
			}

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

	public void sendPluginMessage (Plugin source, String channel, byte[] message)
	{
		// TODO Auto-generated method stub

	}

	public Set<String> getListeningPluginChannels ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getVersion ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getBukkitVersion ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends Player> getOnlinePlayers ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxPlayers ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPort ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getViewDistance ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getIp ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerId ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getWorldType ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getGenerateStructures ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getAllowEnd ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getAllowNether ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasWhitelist ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setWhitelist (boolean value)
	{
		// TODO Auto-generated method stub

	}

	public Set<OfflinePlayer> getWhitelistedPlayers ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void reloadWhitelist ()
	{
		// TODO Auto-generated method stub

	}

	public int broadcastMessage (String message)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUpdateFolder ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public File getUpdateFolderFile ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public long getConnectionThrottle ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTicksPerAnimalSpawns ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTicksPerMonsterSpawns ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Player getPlayer (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Player getPlayerExact (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Player> matchPlayer (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Player getPlayer (UUID id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PluginManager getPluginManager ()
	{
		return this.pluginManager;
	}

	public BukkitScheduler getScheduler ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ServicesManager getServicesManager ()
	{
		return this.servicesManager;
	}

	public List<World> getWorlds ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public World createWorld (WorldCreator creator)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean unloadWorld (String name, boolean save)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean unloadWorld (World world, boolean save)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public World getWorld (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public World getWorld (UUID uid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public MapView getMap (short id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public MapView createMap (World world)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void reload ()
	{
		// TODO Auto-generated method stub

	}

	public void reloadData ()
	{
		// TODO Auto-generated method stub

	}

	public Logger getLogger ()
	{
		return Logger.getGlobal();
	}

	public PluginCommand getPluginCommand (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void savePlayers ()
	{
		// TODO Auto-generated method stub

	}

	public boolean dispatchCommand (CommandSender sender, String commandLine) throws CommandException
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addRecipe (Recipe recipe)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public List<Recipe> getRecipesFor (ItemStack result)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Recipe> recipeIterator ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void clearRecipes ()
	{
		// TODO Auto-generated method stub

	}

	public void resetRecipes ()
	{
		// TODO Auto-generated method stub

	}

	public Map<String, String[]> getCommandAliases ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getSpawnRadius ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSpawnRadius (int value)
	{
		// TODO Auto-generated method stub

	}

	public boolean getOnlineMode ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getAllowFlight ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isHardcore ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void shutdown ()
	{
		// TODO Auto-generated method stub

	}

	public int broadcast (String message, String permission)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public OfflinePlayer getOfflinePlayer (String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public OfflinePlayer getOfflinePlayer (UUID id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getIPBans ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void banIP (String address)
	{
		// TODO Auto-generated method stub

	}

	public void unbanIP (String address)
	{
		// TODO Auto-generated method stub

	}

	public Set<OfflinePlayer> getBannedPlayers ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BanList getBanList (Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Set<OfflinePlayer> getOperators ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public GameMode getDefaultGameMode ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setDefaultGameMode (GameMode mode)
	{
		// TODO Auto-generated method stub

	}

	public ConsoleCommandSender getConsoleSender ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public File getWorldContainer ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public OfflinePlayer[] getOfflinePlayers ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Messenger getMessenger ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public HelpMap getHelpMap ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory createInventory (InventoryHolder owner, InventoryType type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory createInventory (InventoryHolder owner, InventoryType type, String title)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory createInventory (InventoryHolder owner, int size) throws IllegalArgumentException
	{
		return this.createInventory(null, size, "");
	}

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

			public void setMaxStackSize (int size)
			{
				// TODO Auto-generated method stub
			}

			public void setItem (int index, ItemStack item)
			{
				if (index < this.itemStacks.length && index >= 0)
				{
					this.itemStacks[index] = item;
				}
			}

			public void setContents (ItemStack[] items) throws IllegalArgumentException
			{
				this.setStorageContents(items);
			}

			public HashMap<Integer, ItemStack> removeItem (ItemStack... items) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return null;
			}

			public void remove (ItemStack item)
			{
				// TODO Auto-generated method stub

			}

			public void remove (Material material) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub

			}

			public void remove (int materialId)
			{
				// TODO Auto-generated method stub

			}

			public ListIterator<ItemStack> iterator (int index)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public ListIterator<ItemStack> iterator ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public List<HumanEntity> getViewers ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public InventoryType getType ()
			{
				// TODO Auto-generated method stub
				return InventoryType.CHEST;
			}

			public String getTitle ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public ItemStack[] getStorageContents ()
			{
				return this.itemStacks;
			}

			public int getSize ()
			{
				return this.itemStacks.length;
			}

			public String getName ()
			{
				// TODO Auto-generated method stub
				return null;
			}

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

			public ItemStack getItem (int index)
			{
				if (index < this.itemStacks.length && index >= 0)
					return this.itemStacks[index];

				return null;
			}

			public InventoryHolder getHolder ()
			{
				// TODO Auto-generated method stub
				return null;
			}

			public ItemStack[] getContents ()
			{
				return this.getStorageContents();
			}

			public int firstEmpty ()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			public int first (ItemStack item)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			public int first (Material material) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return 0;
			}

			public int first (int materialId)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			public boolean containsAtLeast (ItemStack item, int amount)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (ItemStack item, int amount)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (Material material, int amount) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (int materialId, int amount)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (ItemStack item)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (Material material) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains (int materialId)
			{
				// TODO Auto-generated method stub
				return false;
			}

			public void clear (int index)
			{
				// TODO Auto-generated method stub

			}

			public void clear ()
			{
				// TODO Auto-generated method stub

			}

			public HashMap<Integer, ? extends ItemStack> all (ItemStack item)
			{
				return null;
			}

			public HashMap<Integer, ? extends ItemStack> all (Material material) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return null;
			}

			public HashMap<Integer, ? extends ItemStack> all (int materialId)
			{
				// TODO Auto-generated method stub
				return null;
			}

			public HashMap<Integer, ItemStack> addItem (ItemStack... items) throws IllegalArgumentException
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	public Merchant createMerchant (String title)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getMonsterSpawnLimit ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAnimalSpawnLimit ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getWaterAnimalSpawnLimit ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAmbientSpawnLimit ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isPrimaryThread ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public String getMotd ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getShutdownMessage ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public WarningState getWarningState ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ItemFactory getItemFactory ()
	{
		// TODO Auto-generated method stub
		return this.itemFactory;
	}

	public ScoreboardManager getScoreboardManager ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public CachedServerIcon getServerIcon ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public CachedServerIcon loadServerIcon (File file) throws IllegalArgumentException, Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public CachedServerIcon loadServerIcon (BufferedImage image) throws IllegalArgumentException, Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setIdleTimeout (int threshold)
	{
		// TODO Auto-generated method stub

	}

	public int getIdleTimeout ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public ChunkData createChunkData (World world)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BossBar createBossBar (String title, BarColor color, BarStyle style, BarFlag... flags)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Entity getEntity (UUID uuid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Advancement getAdvancement (NamespacedKey key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Advancement> advancementIterator ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings ("deprecation")
	public UnsafeValues getUnsafe ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getName ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BanList getBanList (Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Messenger getMessenger ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory createInventory (InventoryHolder owner, InventoryType type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory createInventory (InventoryHolder owner, InventoryType type, String title)
	{
		// TODO Auto-generated method stub
		return null;
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