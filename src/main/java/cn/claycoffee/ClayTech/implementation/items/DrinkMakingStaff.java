package cn.claycoffee.ClayTech.implementation.items;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechItems;
import cn.claycoffee.ClayTech.ClayTechMachineRecipes;
import cn.claycoffee.ClayTech.ClayTechRecipeType;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class DrinkMakingStaff {
    public DrinkMakingStaff() {
        ItemStack[] recipea = {new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.COCOA_BEANS),
                new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.COCOA_BEANS), ClayTechItems.CLAY_STICK,
                new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.COCOA_BEANS),
                new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.COCOA_BEANS)};
        ItemStack[] recipeb = {null, null, null, null, SlimefunItems.OIL_BUCKET, null, null, null, null};
        ItemStack[] recipec = {ClayTechItems.PLASTIC, ClayTechItems.PLASTIC, ClayTechItems.PLASTIC,
                ClayTechItems.PLASTIC, ClayTechItems.MAGIC_CLAY, ClayTechItems.PLASTIC, ClayTechItems.PLASTIC,
                ClayTechItems.PLASTIC, ClayTechItems.PLASTIC};

        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "COCOA_BEAN", ClayTechItems.COCOA_BEAN,
                RecipeType.ENHANCED_CRAFTING_TABLE, recipea);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "PLASTIC", ClayTechItems.PLASTIC,
                RecipeType.ENHANCED_CRAFTING_TABLE, recipeb);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "DRINK_BOTTLE", ClayTechItems.DRINK_BOTTLE,
                RecipeType.ENHANCED_CRAFTING_TABLE, recipec);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "DIRTY_DRINK_BOTTLE",
                ClayTechItems.DIRTY_DRINK_BOTTLE, RecipeType.NULL, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "DIRTY_TEA", ClayTechItems.DIRTY_TEA,
                ClayTechRecipeType.HARVEST, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "RAW_TEA", ClayTechItems.RAW_TEA,
                ClayTechRecipeType.CLEANING, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "TEA_POWDER", ClayTechItems.TEA_POWDER,
                ClayTechRecipeType.CLAY_FOOD_CHALKING_MACHINE, ClayTechMachineRecipes.TEA_POWDER);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "LEMON_POWDER", ClayTechItems.LEMON_POWDER,
                ClayTechRecipeType.CLAY_FOOD_CHALKING_MACHINE, ClayTechMachineRecipes.LEMON_POWDER
        );

        Research foodmaterialsI = new Research(
                new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_DRINKMATERIALSBASIC"), 9908,
                Lang.readResearchesText("CLAYTECH_DRINK_MAKINGS_I"), 50);
        foodmaterialsI.addItems(SlimefunItem.getByItem(ClayTechItems.COCOA_BEAN),
                SlimefunItem.getByItem(ClayTechItems.PLASTIC), SlimefunItem.getByItem(ClayTechItems.DRINK_BOTTLE),
                SlimefunItem.getByItem(ClayTechItems.DIRTY_DRINK_BOTTLE));
        foodmaterialsI.register();

        Research foodmaterialsII = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_DRINKMATERIALSII"),
                9911, Lang.readResearchesText("CLAYTECH_DRINK_MAKINGS_II"), 50);
        foodmaterialsII.addItems(SlimefunItem.getByItem(ClayTechItems.DIRTY_TEA),
                SlimefunItem.getByItem(ClayTechItems.RAW_TEA), SlimefunItem.getByItem(ClayTechItems.LEMON_POWDER),
                SlimefunItem.getByItem(ClayTechItems.TEA_POWDER));
        foodmaterialsII.register();
    }
}
