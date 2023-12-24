package cn.claycoffee.ClayTech.implementation.items;

import cn.claycoffee.ClayTech.*;
import cn.claycoffee.ClayTech.api.Planet;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.MetadataUtil;
import cn.claycoffee.ClayTech.utils.PlanetUtils;
import cn.claycoffee.ClayTech.utils.SlimefunUtils;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class Rockets {
    private static final int[] BORDER = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 26, 27, 35, 36, 44, 45, 47, 48, 49,
            50, 51, 53};
    private static final int[] BORDER_2 = {10, 11, 12, 14, 15, 16};
    private static final ItemStack BORDER_ITEM = new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Lang.readMachinesText("SPLIT_LINE"));
    private static final ItemStack OTHERBORDER_ITEM = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
            Lang.readMachinesText("SPLIT_LINE"));
    private static int currentPage = 1;

    public Rockets() {
        // 平台
        ItemStack[] rocketlauncher = {ClayTechItems.CLAY_FUSION_INGOT, ClayTechItems.CLAY_FUSION_INGOT,
                ClayTechItems.CLAY_FUSION_INGOT, ClayTechItems.CLAY_FUSION_INGOT, SlimefunItems.PROGRAMMABLE_ANDROID,
                ClayTechItems.CLAY_FUSION_INGOT, ClayTechItems.CLAY_FUSION_INGOT, ClayTechItems.CLAY_FUSION_INGOT,
                ClayTechItems.CLAY_FUSION_INGOT};

        SlimefunUtils.registerItem(ClayTechItems.C_MACHINES, ClayTechItems.ROCKET_LAUNCHER,
                RecipeType.ENHANCED_CRAFTING_TABLE, rocketlauncher,
                new ItemHandler[]{new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(BlockPlaceEvent blockPlaceEvent) {
                        StorageCacheUtils.setData(blockPlaceEvent.getBlock().getLocation(), "owner", blockPlaceEvent.getPlayer().getName(), true);
                    }

                }, (BlockUseHandler) ev -> {
                    PlayerInteractEvent e = ev.getInteractEvent();
                    if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Block b = e.getClickedBlock();
                        if (BlockStorage.checkID(b) != null) {
                            if (BlockStorage.checkID(b).equalsIgnoreCase("ROCKET_LAUNCHER")) {
                                if (e.hasItem()) {
                                    if (!Slimefun.hasUnlocked(e.getPlayer(), e.getItem(), true)) {
                                        return;
                                    }
                                }
                                if (!Slimefun.hasUnlocked(e.getPlayer(), ClayTechItems.ROCKET_LAUNCHER, true)) {
                                    return;
                                }
                                Map<String, String> jbj = StrUtils.parseJSON(BlockStorage.getBlockInfoAsJson(b));
                                String ownerName = jbj.get("owner");
                                if (ownerName.equalsIgnoreCase(e.getPlayer().getName())) {
                                    Planet current = PlanetUtils.getPlanet(b.getWorld());
                                    if (current == null) {
                                        e.getPlayer().sendMessage(Lang.readGeneralText("NotAtAPlanet"));
                                        return;
                                    }
                                    if (MetadataUtil.getMetadata(b, "currentPage") != null) {
                                        currentPage = Objects.requireNonNull(MetadataUtil.getMetadata(b, "currentPage")).asInt();
                                    }
                                    Inventory Preset = Bukkit.createInventory(null, 54,
                                            Lang.readMachinesText("ROCKET_LAUNCHER"));
                                    if (!ClayTechData.RunningLaunchersG.containsKey(Preset)) {
                                        ClayTechData.RunningLaunchersG.put(Preset, b);
                                    }
                                    Preset.setItem(5, BORDER_ITEM);
                                    for (int eachID : BORDER) {
                                        Preset.setItem(eachID, BORDER_ITEM);
                                    }
                                    for (int eachID : BORDER_2) {
                                        Preset.setItem(eachID, OTHERBORDER_ITEM);
                                    }
                                    Preset.setItem(5, BORDER_ITEM);

                                    Preset = PlanetUtils.renderLauncherMenu(current, Preset, currentPage);

                                    e.getPlayer().openInventory(Preset);
                                } else {
                                    e.getPlayer().sendMessage(Lang.readGeneralText("notOwner"));
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }});

        Research ms2 = new Research(new NamespacedKey(ClayTech.getInstance(), "CLAYTECH_UNIVERSE_MACHINE_2"), 9929,
                Lang.readResearchesText("CLAYTECH_UNIVERSE_MACHINE_II"), 55);
        ms2.addItems(SlimefunItem.getByItem(ClayTechItems.ROCKET_LAUNCHER));
        ms2.register();

        // 火箭一阶
        SlimefunUtils.registerItem(ClayTechItems.C_OTHER, "ROCKET_1", ClayTechItems.ROCKET,
                ClayTechRecipeType.CLAY_ROCKET_ASSEMBLING_MACHINE, ClayTechMachineRecipes.ROCKET_1);
    }
}
