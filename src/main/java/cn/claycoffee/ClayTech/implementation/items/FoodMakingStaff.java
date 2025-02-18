package cn.claycoffee.ClayTech.implementation.items;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechItems;
import cn.claycoffee.ClayTech.ClayTechMachineRecipes;
import cn.claycoffee.ClayTech.ClayTechRecipeType;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class FoodMakingStaff {
    public FoodMakingStaff() {
        ItemStack[] recipec = {null, new ItemStack(Material.BREAD), null, null, ClayTechItems.MAGIC_CLAY, null, null,
                new ItemStack(Material.BREAD), null};
        ItemStack[] reciped = {null, new ItemStack(Material.KELP), null, null, ClayTechItems.MAGIC_CLAY, null, null,
                SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_14)
                        ? new ItemStack(Material.BAMBOO)
                        : new ItemStack(Material.KELP),
                null};

        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "RAW_CHICKEN_FOOT", ClayTechItems.RAW_CHICKEN_FOOT,
                ClayTechRecipeType.CLAY_FOOD_CHALKING_MACHINE,
                ClayTechMachineRecipes.RAW_CHICKEN_FOOT);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "RAW_BREAD", ClayTechItems.RAW_BREAD,
                RecipeType.ENHANCED_CRAFTING_TABLE, recipec);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "RAW_VEGETABLE", ClayTechItems.RAW_VEGETABLE,
                RecipeType.ENHANCED_CRAFTING_TABLE, reciped);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "CLAY_LEMON", ClayTechItems.CLAY_LEMON,
                ClayTechRecipeType.PLUCKING, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "STARCH", ClayTechItems.STARCH,
                ClayTechRecipeType.HARVEST, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "FLOUR", ClayTechItems.FLOUR,
                ClayTechRecipeType.HARVEST, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "SNAIL_HEALTHY", ClayTechItems.SNAIL_HEALTHY,
                ClayTechRecipeType.FISHING, ClayTechItems.NORECIPE);
        SlimefunUtils.registerItem(ClayTechItems.C_FOODMATERIALS, "CLAY_SWEET_POTATO", ClayTechItems.CLAY_SWEET_POTATO,
                ClayTechRecipeType.HARVEST, ClayTechItems.NORECIPE);

        Research foodmaterialsI = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_FOODMATERIALSBASIC"),
                9910, Lang.readResearchesText("CLAYTECH_FOOD_MAKINGS_I"), 50);
        foodmaterialsI.addItems(SlimefunItem.getByItem(ClayTechItems.RAW_CHICKEN_FOOT),
                SlimefunItem.getByItem(ClayTechItems.RAW_BREAD), SlimefunItem.getByItem(ClayTechItems.RAW_VEGETABLE));
        foodmaterialsI.register();

        Research foodmaterialsIE = new Research(
                new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_FOODMATERIALSBASIC"), 9913,
                Lang.readResearchesText("CLAYTECH_FOOD_MAKINGS_II"), 50);
        foodmaterialsIE.addItems(SlimefunItem.getByItem(ClayTechItems.FLOUR),
                SlimefunItem.getByItem(ClayTechItems.STARCH), SlimefunItem.getByItem(ClayTechItems.SNAIL_HEALTHY));
        foodmaterialsIE.register();

        Research foodmaterialsII = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_FRUITBASIC"), 9912,
                Lang.readResearchesText("CLAYTECH_FRUIT_I"), 50);
        foodmaterialsII.addItems(SlimefunItem.getByItem(ClayTechItems.CLAY_LEMON));
        foodmaterialsII.register();
    }
}
