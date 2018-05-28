package io.github.nexadn.unitedshops.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.UnitedShops;
import io.github.nexadn.unitedshops.usershop.Vendor;

public class UsershopCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args)
    {
        if (! (sender instanceof Player))
        {
            UnitedShops.plugin.sendMessage(sender, UnitedShops.plugin.getMessage("playerOnly"));
            return true;
        }
        Player player = (Player) sender;
        try
        {
            if (args[0].equalsIgnoreCase("vendors"))
            {
                if (!UnitedShops.plugin.requirePermission(sender, "unitedshops.usershop.use"))
                    return true;

                player.openInventory(Vendor.getGlobalVendorMenu());
            } else if (args[0].equalsIgnoreCase("offers"))
            {
                if (!UnitedShops.plugin.requirePermission(sender, "unitedshops.usershop.use"))
                    return true;

                player.openInventory(Vendor.getGlobalOfferMenu());
            } else if (args[0].equalsIgnoreCase("vendor"))
            {
                if (!UnitedShops.plugin.requirePermission(sender, "unitedshops.usershop.create.vendor"))
                    return true;

                player.openInventory(Vendor.getOrCreateVendor(player).getVendorMenu());
            } else if (args[0].equalsIgnoreCase("offer"))
            {
                if (!UnitedShops.plugin.requirePermission(sender, "unitedshops.usershop.create.offer"))
                    return true;

                ItemStack inHand = player.getItemInHand();
                if (inHand == null || inHand.getType().equals(Material.AIR))
                {
                    UnitedShops.plugin.sendMessage(sender, UnitedShops.plugin.getMessage("needAppropriateItemInHand"));
                    return true;
                }

                Material type = inHand.getType();

                if (args.length == 1 || args[1].equalsIgnoreCase("info"))
                {
                    UnitedShops.plugin.sendMessage(sender,
                            Vendor.getOrCreateVendor(player).getOrCreateOffer(type).toString());
                } else if (args[1].equalsIgnoreCase("mode"))
                {
                    Vendor.getOrCreateVendor(player).getOrCreateOffer(type).setMode(Integer.parseInt(args[2]));
                } else if (args[1].equalsIgnoreCase("setBuyPrice"))
                {
                    Vendor.getOrCreateVendor(player).getOrCreateOffer(type).setPriceBuy(Double.parseDouble(args[2]));
                } else if (args[1].equalsIgnoreCase("setSellPrice"))
                {
                    Vendor.getOrCreateVendor(player).getOrCreateOffer(type).setPriceSell(Double.parseDouble(args[2]));
                }
            } else
            {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
        return true;
    }
}
