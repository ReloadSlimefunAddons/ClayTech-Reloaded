package cn.claycoffee.ClayTech.implementation.machines;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechData;
import cn.claycoffee.ClayTech.ClayTechItems;
import cn.claycoffee.ClayTech.api.ClayTechManager;
import cn.claycoffee.ClayTech.api.events.RocketInjectFuelEvent;
import cn.claycoffee.ClayTech.utils.ItemUtil;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.RocketUtils;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.AdvancedMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RocketFuelInjector extends SlimefunItem implements InventoryBlock, EnergyNetComponent, MachineProcessHolder<CraftingOperation> {
    public final static int[] inputSlots = new int[]{20, 24};
    public final static int[] outputSlots = new int[]{};
    private static final int[] BORDER_A = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 31, 17, 18, 26, 27, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 44};
    private static final int[] BORDER_B = {10, 11, 12, 19, 21, 28, 29, 30, 14, 15, 16, 23, 25, 32, 33, 34};
    private static final ItemStack BORDER_A_ITEM = new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Lang.readMachinesText("SPLIT_LINE"));
    private static final ItemStack BORDER_B_ITEM = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
            Lang.readMachinesText("SPLIT_LINE"));
    public static Map<Block, MachineRecipe> processing = new HashMap<>();
    public static Map<Block, Integer> progress = new HashMap<>();
    private static final Map<Block, ItemStack> item = new HashMap<>();
    private static final Map<Block, ItemStack> itemFuel = new HashMap<>();
    protected final List<MachineRecipe> recipes = new ArrayList<>();
    private final MachineProcessor<CraftingOperation> processor = new MachineProcessor<>(this);

    public RocketFuelInjector(ItemGroup category, SlimefunItemStack item, RecipeType recipeType,
                              ItemStack[] recipe) {

        super(category, item, recipeType, recipe);
        createPreset(this, getInventoryTitle(), this::constructMenu);

        addItemHandler(onBlockBreak());
    }

    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {

            @Override
            public void onBlockBreak(@NotNull Block b) {
                BlockMenu inv = StorageCacheUtils.getMenu(b.getLocation());

                if (inv != null) {
                    inv.dropItems(b.getLocation(), getInputSlots());
                    inv.dropItems(b.getLocation(), getOutputSlots());
                }

                processor.endOperation(b);
            }

        };
    }

    @Override
    public @NotNull EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 256;
    }

    @Override
    public int[] getInputSlots() {
        return inputSlots;
    }

    @Override
    public int[] getOutputSlots() {
        return outputSlots;
    }

    public String getInventoryTitle() {
        return Lang.readMachinesText("CLAY_ROCKET_FUEL_INJECTOR");
    }

    public ItemStack getProgressBar() {
        return new ItemStack(Material.REDSTONE_TORCH);
    }

    public int getEnergyConsumption() {
        return 64;
    }

    public int getSpeed() {
        return 1;
    }

    public String getMachineIdentifier() {
        return "CLAY_ROCKET_FUEL_INJECTOR";
    }

    public void constructMenu(BlockMenuPreset preset) {
        preset.addItem(43, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(43, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(5, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        for (int eachID : BORDER_A) {
            preset.addItem(eachID, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int eachID : BORDER_B) {
            preset.addItem(eachID, BORDER_B_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(22, new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "§9§l←", " "),
                ChestMenuUtils.getEmptyClickHandler());

        preset.addItem(5, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(43, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new AdvancedMenuClickHandler() {

                @Override
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor,
                                       ClickAction action) {
                    return cursor == null || cursor.getType() == Material.AIR;
                }
            });
        }
        preset.addItem(43, BORDER_A_ITEM.clone(), ChestMenuUtils.getEmptyClickHandler());
    }

    public MachineRecipe getProcessing(Block b) {
        return processing.get(b);
    }

    public boolean isProcessing(Block b) {
        return getProcessing(b) != null;
    }

    public void registerRecipe(MachineRecipe recipe) {
        recipe.setTicks(recipe.getTicks() / getSpeed());
        recipes.add(recipe);
    }

    public void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        registerRecipe(new MachineRecipe(seconds, input, output));
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            @Override
            public void tick(Block b, SlimefunItem sf, SlimefunBlockData data) {
                RocketFuelInjector.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
    }

    protected void tick(Block b) {
        BlockMenu inv = StorageCacheUtils.getMenu(b.getLocation());
        // 机器正在处理
        if (inv != null && isProcessing(b)) {
            // 剩余时间
            int timeleft = progress.get(b);

            if (timeleft > 0) {
                // 还在处理
                ChestMenuUtils.updateProgressbar(inv, 22, timeleft, processing.get(b).getTicks(), getProgressBar());

                if (isChargeable()) {
                    if (getCharge(b.getLocation()) < getEnergyConsumption())
                        return;
                    removeCharge(b.getLocation(), getEnergyConsumption());
                    progress.put(b, timeleft - 1);
                } else
                    progress.put(b, timeleft - 1);
            } else {
                // 处理结束
                inv.replaceExistingItem(22,
                        ItemUtil.addLore(new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "§9§l←"), " "));

                ItemStack rocket = item.get(b);
                RocketUtils.setFuel(rocket, Math.min(RocketUtils.getFuel(rocket) + 5, RocketUtils.getMaxFuel(rocket)));
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new RocketInjectFuelEvent(b, itemFuel.get(b), rocket));
                    }

                }.runTask(ClayTech.getInstance());
                inv.replaceExistingItem(20, rocket);
                ClayTechData.RunningInjectors.remove(inv.toInventory());
                progress.remove(b);
                processing.remove(b);
                item.remove(b);
                itemFuel.remove(b);
            }
        } else {
            // 没有在处理
            ItemStack rocket = inv.getItemInSlot(20);
            ItemStack fuel = inv.getItemInSlot(24);
            if (rocket != null && fuel != null) {
                if (ClayTechManager.isRocket(rocket)
                        && SlimefunUtils.isItemSimilar(fuel, ClayTechItems.MIXED_ROCKET_FUEL, true)
                        && rocket.getAmount() == 1) {
                    if (isChargeable()) {
                        if (getCharge(b.getLocation()) < getEnergyConsumption())
                            return;
                        removeCharge(b.getLocation(), getEnergyConsumption());
                    }
                    if (RocketUtils.getFuel(rocket) == RocketUtils.getMaxFuel(rocket))
                        return;
                    ItemStack f = fuel.clone();
                    f.setAmount(1);
                    itemFuel.put(b, f);

                    inv.consumeItem(24, 1);

                    MachineRecipe fuelinjectrecipe = new MachineRecipe(8, new ItemStack[]{rocket, fuel},
                            new ItemStack[]{});
                    item.put(b, rocket.clone());
                    inv.consumeItem(20, 1);
                    ClayTechData.RunningInjectors.put(inv.toInventory(), b);
                    inv.replaceExistingItem(20, new ItemStack(Material.BEDROCK));
                    processing.put(b, fuelinjectrecipe);
                    progress.put(b, fuelinjectrecipe.getTicks());
                }
            }
        }
    }

    @Override
    public @NotNull MachineProcessor<CraftingOperation> getMachineProcessor() {
        return processor;
    }
}
