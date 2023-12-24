package cn.claycoffee.ClayTech.implementation.abstractMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.inventory.ItemStack;

public abstract class ANewContainer extends AContainer {
    public ANewContainer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType,
                         ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }
}