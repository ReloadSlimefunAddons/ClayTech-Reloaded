package cn.claycoffee.ClayTech.utils;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechItems;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.LockedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SlimefunUtils {
    public static void registerArmors(ItemGroup category, String nameprefix, ItemStack[] ItemStack,
                                      RecipeType Recipetype, ItemStack MaterialStack) {

        SlimefunItemStack HELMET = new SlimefunItemStack(nameprefix + "_HELMET", ItemStack[0]);
        SlimefunItem HELMET_I = new SlimefunItem(category, HELMET, Recipetype, getArmorsStack(1, MaterialStack));
        HELMET_I.register(ClayTech.getInstance());
        SlimefunItemStack CHESTPLATE = new SlimefunItemStack(nameprefix + "_CHESTPLATE", ItemStack[1]);
        SlimefunItem CHESTPLATE_I = new SlimefunItem(category, CHESTPLATE, Recipetype,
                getArmorsStack(2, MaterialStack));
        CHESTPLATE_I.register(ClayTech.getInstance());
        SlimefunItemStack LEGGINGS = new SlimefunItemStack(nameprefix + "_LEGGINGS", ItemStack[2]);
        SlimefunItem LEGGINGS_I = new SlimefunItem(category, LEGGINGS, Recipetype, getArmorsStack(3, MaterialStack));
        LEGGINGS_I.register(ClayTech.getInstance());
        SlimefunItemStack BOOTS = new SlimefunItemStack(nameprefix + "_BOOTS", ItemStack[3]);
        SlimefunItem BOOTS_I = new SlimefunItem(category, BOOTS, Recipetype, getArmorsStack(4, MaterialStack));
        BOOTS_I.register(ClayTech.getInstance());
    }

    public static ItemStack[] getArmorsStack(int type, ItemStack Material) {
        if (type == 1) {
            return new ItemStack[]{Material, Material, Material, Material, null, Material, null, null, null};
        }
        if (type == 2) {
            return new ItemStack[]{Material, null, Material, Material, Material, Material, Material, Material,
                    Material};
        }
        if (type == 3) {
            return new ItemStack[]{Material, Material, Material, Material, null, Material, Material, null, Material};
        }
        return new ItemStack[]{null, null, null, Material, null, Material, Material, null, Material};
    }

    public static void registerResource(GEOResource res) {
        res.register();
        Slimefun.getRegistry().getGEOResources().add(res);
    }

    public static void doAirlock(Block plate, BlockFace face) {
        Block a1 = plate.getRelative(face);
        int waitTime = 5;
        if (StorageCacheUtils.hasBlock(plate.getLocation()) && StorageCacheUtils.getData(plate.getLocation(), "wait-time") != null)
            waitTime = Integer.parseInt(StorageCacheUtils.getData(plate.getLocation(), "wait-time"));
        if (BlockStorage.checkID(a1) != null && BlockStorage.checkID(a1).equals("CLAY_AIR_LOCK_BLOCK")) {
            List<Block> block = new ArrayList<>();
            List<Block> blocks = new ArrayList<>();
            int[] range = new int[]{0, 1, 2, 3, 4};
            if (face == BlockFace.NORTH || face == BlockFace.SOUTH) {
                int l = 0;
                boolean bo = false;
                for (int x : range) {
                    Block a2 = a1.getRelative(BlockFace.WEST, x);
                    Block a3 = a1.getRelative(BlockFace.EAST, x);
                    if (BlockStorage.checkID(a2) != null && BlockStorage.checkID(a2).equals("CLAY_AIR_LOCK_BLOCK")) {
                        l++;
                        block.add(a2);
                        bo = true;
                    }
                    if (BlockStorage.checkID(a3) != null && BlockStorage.checkID(a3).equals("CLAY_AIR_LOCK_BLOCK") && x != 0) {
                        l++;
                        block.add(a3);
                        bo = true;
                    } else if (bo && x != 0) {
                        break;
                    }
                }
                if (l > 0) {
                    blocks.addAll(block);
                    for (Block b : block) {
                        for (int i = 1; i < l; i++) {
                            Block a2 = b.getRelative(BlockFace.UP, i);
                            if (BlockStorage.checkID(a2) != null && !BlockStorage.checkID(a2).equals("CLAY_AIR_LOCK_BLOCK") || BlockStorage.checkID(a2) == null)
                                return;
                            blocks.add(a2);
                        }
                    }
                }
            } else if (face == BlockFace.EAST || face == BlockFace.WEST) {
                int l = 0;
                boolean bo = false;
                for (int x : range) {
                    Block a2 = a1.getRelative(BlockFace.NORTH, x);
                    Block a3 = a1.getRelative(BlockFace.SOUTH, x);
                    if (BlockStorage.checkID(a2) != null && BlockStorage.checkID(a2).equals("CLAY_AIR_LOCK_BLOCK")) {
                        l++;
                        block.add(a2);
                        bo = true;
                    }
                    if (BlockStorage.checkID(a3) != null && BlockStorage.checkID(a3).equals("CLAY_AIR_LOCK_BLOCK") && x != 0) {
                        l++;
                        block.add(a3);
                        bo = true;
                    } else if (bo && x != 0) {
                        break;
                    }
                }
                if (l > 0) {
                    blocks.addAll(block);
                    for (Block b : block) {
                        for (int i = 1; i < l; i++) {
                            Block a2 = b.getRelative(BlockFace.UP, i);
                            if (BlockStorage.checkID(a2) != null && !BlockStorage.checkID(a2).equals("CLAY_AIR_LOCK_BLOCK") || BlockStorage.checkID(a2) == null)
                                return;
                            blocks.add(a2);
                        }
                    }
                }
            }
            if (blocks.size() > 1) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block b : blocks) {
                            b.setType(Material.AIR);
                        }
                    }
                }.runTask(ClayTech.getInstance());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block b : blocks) {
                            b.setType(ClayTechItems.CLAY_AIR_LOCK_BLOCK.getType());
                        }
                    }
                }.runTaskLater(ClayTech.getInstance(), waitTime * 20L);
            }
        }

    }

    public static void registerItem(ItemGroup category, String name, ItemStack[] recipe, RecipeType recipetype, ItemStack MaterialStack) {
        SlimefunItemStack si = new SlimefunItemStack(name, MaterialStack);
        SlimefunItem item = new SlimefunItem(category, si, recipetype, recipe);
        category.add(item);
        category.register(ClayTech.getInstance());
    }

    public static void registerItem(ItemGroup category, String id, ItemStack stack, RecipeType recipeType, ItemStack[] recipe) {
        registerItem(category, id, recipe, recipeType, stack);
    }

    public static void registerItem(ItemGroup category, SlimefunItemStack stack, RecipeType recipeType, ItemStack[] recipe, ItemHandler[] itemHandlers) {
        SlimefunItem item = new SlimefunItem(category, stack, recipeType, recipe);
        item.addItemHandler(itemHandlers);
        category.add(item);
        category.register(ClayTech.getInstance());
    }
}