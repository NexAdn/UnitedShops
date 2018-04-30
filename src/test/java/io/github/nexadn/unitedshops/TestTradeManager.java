package io.github.nexadn.unitedshops;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.nexadn.unitedshops.tradeapi.TradeManager;

public class TestTradeManager extends TradeManager {
    
    public boolean allTradeReturn = true;

    public TestTradeManager(UnitedShops plugin)
    {
        super(plugin, null);
    }
    
    @Override
    public boolean tradeItemForMoney (Player player, ItemStack offer, double want)
    {
        return this.allTradeReturn;
    }
    
    @Override
    public boolean tradeMoneyForItem (Player player, double offer, ItemStack want)
    {
        return this.allTradeReturn;
    }
    
    @Override
    public boolean removeItems (Inventory inventory, ItemStack items)
    {
        return this.allTradeReturn;
    }

}
