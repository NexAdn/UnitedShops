package me.nexadn.unitedshops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nexadn.unitedshops.exception.InvalidValueException;

public abstract class Util {
    public static ItemStack getBlank() {
        return getItem(new Pair<>(Material.THIN_GLASS, (short) 0), 1, " ");
    }

    public static ItemStack getItem(Pair<Material, Short> type, int amount, String... nameAndLore) {
        ItemStack item = new ItemStack(type.first, amount, type.second);
        ItemMeta meta = item.getItemMeta();
        List<String> lores = Arrays.asList(nameAndLore);
        if (lores.size() > 0) {
            meta.setDisplayName(lores.get(0));
            List<String> lore = new ArrayList<>();
            for (int i = 1; i < lores.size(); i++) {
                lore.add(lores.get(i));
            }
            if (lore.size() > 0)
                meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static Pair<Material, Short> parseItemType(String s) {
        Material material = null;
        short damage = -1;
        material = Material.matchMaterial(s);
        if (material != null)
            return new Pair<>(material, (short) 0);
        Matcher matcher = Pattern.compile("^([A-Za-z_]+):([0-9+])").matcher(s);
        if (matcher.matches()) {
            material = Material.matchMaterial(matcher.group(1));
            damage = Short.parseShort(matcher.group(2));
        }
        if (material == null || damage < 0) {
            throw new InvalidValueException("Malformed item type string: " + s);
        }
        return new Pair<>(material, damage);
    }
}
