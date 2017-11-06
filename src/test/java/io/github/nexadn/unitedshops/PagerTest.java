package io.github.nexadn.unitedshops;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.nexadn.unitedshops.ui.Pager;
import io.github.nexadn.unitedshops.ui.PagerItem;
import net.milkbowl.vault.economy.Economy;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({ JavaPluginLoader.class, PluginDescriptionFile.class })
public class PagerTest {

	public static Inventory	mockInventory;
	public static Pager		testPager;
	public static Player	mockPlayer;

	@Test
	public void testPager ()
	{
		TestUtil.init();

		List<PagerItem> menuItems = new ArrayList<PagerItem>();
		for (int i = 0; i < 18; ++i)
		{
			menuItems.add(PowerMockito.spy(new PagerTestItem(i)));
		}

		int menuButtonFlags = Pager.MenuButton.PREV | Pager.MenuButton.CLOSE | Pager.MenuButton.NEXT
				| Pager.MenuButton.UP;
		testPager = PowerMockito.spy(new Pager(menuItems, menuButtonFlags, "Pager Test", 1));
		//assertEquals(2, testPager.getInventorys().size());

		mockInventory = PowerMockito.spy(Bukkit.createInventory(null, 9, "Mock Inventory"));
		
		mockPlayer = PowerMockito.mock(Player.class);
		InventoryView mockView = PowerMockito.spy(new InventoryView() {

			@Override
			public InventoryType getType ()
			{
				return InventoryType.CHEST;
			}

			@Override
			public Inventory getTopInventory ()
			{
				return testPager.getInventorys().get(1);
			}

			@Override
			public HumanEntity getPlayer ()
			{
				return mockPlayer;
			}

			@Override
			public Inventory getBottomInventory ()
			{
				return PagerTest.mockInventory;
			}
		});

		InventoryClickEvent mockEventNormal = PowerMockito.spy(
				new InventoryClickEvent(mockView, SlotType.CONTAINER, 4, ClickType.LEFT, InventoryAction.PICKUP_ONE));
		InventoryClickEvent mockEventMenuButton = PowerMockito.spy(
				new InventoryClickEvent(mockView, SlotType.CONTAINER, 9, ClickType.LEFT, InventoryAction.PICKUP_ONE));

		testPager.onInventoryClick(mockEventNormal);
		testPager.onInventoryClick(mockEventMenuButton);

		verify(testPager).onInventoryClick(mockEventNormal);
		verify(menuItems.get(13)).call(mockEventNormal);
		
		verify(testPager).onInventoryClick(mockEventMenuButton);
		verify(mockPlayer).openInventory(testPager.getInventorys().get(0));
	}
}
