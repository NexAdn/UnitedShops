package me.nexadn.unitedshops.objects;

import me.nexadn.unitedshops.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A simple item type representation as used throughout UnitedShops
 * <p>
 * This class stores the basic information belonging to an item type.
 * It can be used to generate {@link ItemStack} representations as well as check equality with and without wildcard
 * support against the old {@link Pair} type as well as against other instances of this class.
 */
public class ItemType
{
    public Material material;
    public short damage;

    /**
     * Create a new instance with the given material and a damage value of 0.
     *
     * @param material The material
     * @throws IllegalArgumentException If the material is null
     */
    public ItemType(Material material)
    {
        this(material, (short) 0);
    }

    /**
     * Create a new instance from the data contained in the item type {@link Pair}
     *
     * @param itemType The item type {@link Pair}
     * @throws IllegalArgumentException If the material is null
     */
    public ItemType(Pair<Material, Short> itemType)
    {
        this(itemType.first, itemType.second);
    }

    /**
     * Create a new instance with the given material and damage value.
     *
     * @param material The material
     * @param damage   The damage value. If negative, this instance becomes an item type with wildcard damage value.
     *                 Representations as returned by {@link #getAsItemStack()} will have a damage of 0. A call to
     *                 {@link #matches(ItemType)} or {@link #matches(Pair)} will skip the check for equality of
     *                 damage values.
     */
    public ItemType(Material material, short damage)
    {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null.");
        }
        this.material = material;
        this.damage = damage;
    }

    /**
     * Creates an {@link ItemStack} using this instance's item type data.
     *
     * @return The created {@link ItemStack} with one item.
     */
    public ItemStack getAsItemStack()
    {
        return this.getAsItemStack(1);
    }

    /**
     * Creates an {@link ItemStack} using this instance's item type data.
     *
     * @param amount The amount of items of the returned {@link ItemStack}
     * @return The created {@link ItemStack} with the given amount of items.
     */
    public ItemStack getAsItemStack(int amount)
    {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        return new ItemStack(this.material, amount, this.isAnyDamage() ? 0 : this.damage);
    }

    /**
     * Creates an item type {@link Pair} using this instance's item type data.
     *
     * @return The created {@link Pair}.
     */
    public Pair<Material, Short> getAsItemTypePair()
    {
        return new Pair<>(this.material, this.damage);
    }

    /**
     * Returns, whether or not this instance has a wildcard damage value
     *
     * @return true, if it has a wildcard damage value; false otherwise
     */
    public boolean isAnyDamage()
    {
        return this.damage < 0;
    }

    /**
     * Creates an {@link ItemType} from the given {@link Pair} and calls {@link #matches(ItemType)}
     *
     * @param itemType The {@link Pair}
     */
    public boolean matches(Pair<Material, Short> itemType)
    {
        return this.matches(new ItemType(itemType.first, itemType.second));
    }

    /**
     * Checks against equality under consideration of a possibly present wildcard damage value.
     * <p>
     * If either one of the two {@link ItemType} instances (this or the parameter) contain a wildcard damage (if
     * {@link #isAnyDamage()} returns true for one of these), only the materials are checked for equality, otherwise
     * the damage values are checked for equality, too.
     *
     * @param itemType The right-hand-side {@link ItemType}
     * @return true, if the item types match under consideration of possible wildcard values; false, otherwise
     */
    public boolean matches(ItemType itemType)
    {
        return (this.material.equals(itemType.material)) && ((this.isAnyDamage() || itemType.isAnyDamage()) ? true :
                (this.damage == itemType.damage));
    }

    /**
     * Overridden and adjusted equals-method from {@link Object} for checking value equality of item type
     * {@link Pair}s and other {@link ItemType}s.
     * <p>
     * This method checks against equality without consideration of a possibliy presend wildcard damage value, if the
     * right hand side object is either a {@link Pair} or an {@link ItemType}.
     * This means, that both material and damage are checked against equality regardless of the presence of wildcard
     * values in either one of the two objects.
     * <p>
     * If the right hand side object is neither an instance of {@link Pair} nor of {@link ItemType}, the standard
     * {@link Object#equals(Object)} is used.
     *
     * @param rhs The right hand side object to be checked against.
     * @return true, if the criteria for equality are fulfilled (see method description); false otherwise
     */
    @Override
    public boolean equals(Object rhs)
    {
        if (rhs instanceof Pair) {
            Pair p = (Pair) rhs;
            return (this.material.equals(p.first) && p.second.equals(this.damage));
        } else if (rhs instanceof ItemType) {
            ItemType it = (ItemType) rhs;
            return (it.material.equals(this.material) && it.damage == this.damage);
        } else {
            return super.equals(rhs);
        }
    }
}
