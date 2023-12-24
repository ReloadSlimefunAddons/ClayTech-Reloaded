package cn.claycoffee.ClayTech.implementation.items;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechItems;
import cn.claycoffee.ClayTech.ClayTechMachineRecipes;
import cn.claycoffee.ClayTech.ClayTechRecipeType;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.NamespacedKey;

public class RocketMakings {
    public RocketMakings() {
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "MOTOR_CORE", ClayTechItems.MOTOR_CORE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.MOTOR_CORE);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "TEMPERATURE_RESISTANCE_OBSIDIAN",
                ClayTechItems.TEMPERATURE_RESISTANCE_OBSIDIAN,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.TEMPERATURE_RESISTANCE_OBSIDIAN);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_ENGINE_SHELL", ClayTechItems.ROCKET_ENGINE_SHELL,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_ENGINE_SHELL
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "FUEL_TANK", ClayTechItems.FUEL_TANK,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.FUEL_TANK);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_ENGINE", ClayTechItems.ROCKET_ENGINE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_ENGINE);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_ANTENNA", ClayTechItems.ROCKET_ANTENNA,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_ANTENNA
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_CPU", ClayTechItems.ROCKET_CPU,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_CPU);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_CONTROL_CORE", ClayTechItems.ROCKET_CONTROL_CORE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_CONTROL_CORE
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_FUEL_TANK", ClayTechItems.ROCKET_FUEL_TANK,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_FUEL_TANK
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_GLASS", ClayTechItems.ROCKET_GLASS,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_GLASS);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "ROCKET_STEEL_PLATE", ClayTechItems.ROCKET_STEEL_PLATE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.ROCKET_STEEL_PLATE
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "MIXED_ROCKET_FUEL", ClayTechItems.MIXED_ROCKET_FUEL,
                ClayTechRecipeType.CLAY_ROCKET_FUEL_GENERATOR,
                ClayTechMachineRecipes.MIXED_ROCKET_FUEL);

        Research rk1 = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_ROCKETS_1"), 9926,
                Lang.readResearchesText("CLAYTECH_ROCKETS_I"), 85);
        rk1.addItems(SlimefunItem.getByItem(ClayTechItems.MOTOR_CORE),
                SlimefunItem.getByItem(ClayTechItems.TEMPERATURE_RESISTANCE_OBSIDIAN),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_ENGINE_SHELL),
                SlimefunItem.getByItem(ClayTechItems.FUEL_TANK), SlimefunItem.getByItem(ClayTechItems.ROCKET_ENGINE),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_ANTENNA), SlimefunItem.getByItem(ClayTechItems.ROCKET_CPU),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_CONTROL_CORE),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_FUEL_TANK),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_GLASS),
                SlimefunItem.getByItem(ClayTechItems.ROCKET_STEEL_PLATE),
                SlimefunItem.getByItem(ClayTechItems.MIXED_ROCKET_FUEL));
        rk1.register();

        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "OXYGEN_TANK", ClayTechItems.OXYGEN_TANK,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.OXYGEN_TANK);
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "SPACESUIT_OXYGEN_TANK",
                ClayTechItems.SPACESUIT_OXYGEN_TANK, ClayTechRecipeType.CLAY_CRAFTING_TABLE,
                ClayTechMachineRecipes.SPACESUIT_OXYGEN_TANK);

        SlimefunUtils.registerItem(ClayTechItems.C_ARMORS, "SPACESUIT_HELMET", ClayTechItems.SPACESUIT_HELMET,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.SPACESUIT_HELMET
        );
        SlimefunUtils.registerItem(ClayTechItems.C_ARMORS, "SPACESUIT_CHESTPLATE", ClayTechItems.SPACESUIT_CHESTPLATE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.SPACESUIT_CHESTPLATE
        );
        SlimefunUtils.registerItem(ClayTechItems.C_ARMORS, "SPACESUIT_LEGGINGS", ClayTechItems.SPACESUIT_LEGGINGS,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.SPACESUIT_LEGGINGS
        );
        SlimefunUtils.registerItem(ClayTechItems.C_ARMORS, "SPACESUIT_BOOTS", ClayTechItems.SPACESUIT_BOOTS,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.SPACESUIT_BOOTS
        );

        Research ss1 = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_SPACESUIT_1"), 9927,
                Lang.readResearchesText("CLAYTECH_SPACESUIT_I"), 55);
        ss1.addItems(SlimefunItem.getByItem(ClayTechItems.OXYGEN_TANK),
                SlimefunItem.getByItem(ClayTechItems.SPACESUIT_OXYGEN_TANK),
                SlimefunItem.getByItem(ClayTechItems.SPACESUIT_HELMET),
                SlimefunItem.getByItem(ClayTechItems.SPACESUIT_CHESTPLATE),
                SlimefunItem.getByItem(ClayTechItems.SPACESUIT_LEGGINGS),
                SlimefunItem.getByItem(ClayTechItems.SPACESUIT_BOOTS));
        ss1.register();

        Research ms1 = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_UNIVERSE_MACHINE_1"), 9928,
                Lang.readResearchesText("CLAYTECH_UNIVERSE_MACHINE_I"), 70);
        ms1.addItems(SlimefunItem.getByItem(ClayTechItems.CLAY_ROCKET_ASSEMBLING_MACHINE),
                SlimefunItem.getByItem(ClayTechItems.CLAY_ROCKET_FUEL_GENERATOR),
                SlimefunItem.getByItem(ClayTechItems.CLAY_ROCKET_FUEL_INJECTOR),
                SlimefunItem.getByItem(ClayTechItems.CLAY_SPACESUIT_OXYGEN_INJECTOR));
        ms1.register();

        SlimefunUtils.registerItem(ClayTechItems.C_OTHER, "PLANET_BASE_SIGNER", ClayTechItems.PLANET_BASE_SIGNER,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.PLANET_BASE_SIGNER
        );
        SlimefunUtils.registerItem(ClayTechItems.C_MATERIALS, "TUBE", ClayTechItems.TUBE,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.TUBE);
        SlimefunUtils.registerItem(ClayTechItems.C_OTHER, "OXYGEN_DISTRIBUTER", ClayTechItems.OXYGEN_DISTRIBUTER,
                ClayTechRecipeType.CLAY_CRAFTING_TABLE, ClayTechMachineRecipes.OXYGEN_DISTRIBUTER
        );

        Research bs1 = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_BASE_1"), 9930,
                Lang.readResearchesText("CLAYTECH_BASE_I"), 50);
        bs1.addItems(SlimefunItem.getByItem(ClayTechItems.PLANET_BASE_SIGNER),
                SlimefunItem.getByItem(ClayTechItems.TUBE), SlimefunItem.getByItem(ClayTechItems.OXYGEN_DISTRIBUTER));
        bs1.register();

    }
}
