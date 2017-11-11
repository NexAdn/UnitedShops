package io.github.nexadn.unitedshops;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

public final class TestUtil {
	private static JavaPluginLoader	javaPluginLoader;
	private static Server			server;
	private static UnitedShops		plugin;
	private static boolean			initialized	= false;
	private static File				pluginDir	= new File("target/test/unitedshops");

	@SuppressWarnings ("deprecation")
	public static void init ()
	{
		if (!initialized)
		{
			initialized = true;
			pluginDir.mkdirs();
			server = PowerMockito.spy(new TestServer());
			javaPluginLoader = PowerMockito.spy(new JavaPluginLoader(server));
			Whitebox.setInternalState(javaPluginLoader, "server", server);
			Logger.getLogger("Minecraft").setParent(server.getLogger());
			PluginDescriptionFile pluginDescriptionFile = PowerMockito
					.spy(new PluginDescriptionFile("UnitedShops", "-1", "io.github.nexadn.unitedshops.UnitedShops"));
			when(pluginDescriptionFile.getAuthors()).thenReturn(new ArrayList<String>());
			plugin = PowerMockito.spy(new UnitedShops(javaPluginLoader, pluginDescriptionFile, pluginDir,
					new File(pluginDir, "testpluginfile")));
			plugin.onUnitTest();
			plugin.onEnable();
		}
	}

	public static Server getServer ()
	{
		return server;
	}

	public static UnitedShops getPlugin ()
	{
		return plugin;
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