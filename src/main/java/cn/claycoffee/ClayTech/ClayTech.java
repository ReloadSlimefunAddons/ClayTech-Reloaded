package cn.claycoffee.ClayTech;

import cn.claycoffee.ClayTech.api.ClayTechManager;
import cn.claycoffee.ClayTech.api.Planet;
import cn.claycoffee.ClayTech.implementation.Planets.Earth;
import cn.claycoffee.ClayTech.implementation.Planets.Mars;
import cn.claycoffee.ClayTech.implementation.Planets.Moon;
import cn.claycoffee.ClayTech.implementation.items.*;
import cn.claycoffee.ClayTech.implementation.resources.ClayFuel;
import cn.claycoffee.ClayTech.listeners.*;
import cn.claycoffee.ClayTech.utils.*;
import cn.claycoffee.clayapi.utils.ConfigUtils;
import cn.claycoffee.clayapi.utils.DataYML;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClayTech extends JavaPlugin implements SlimefunAddon {
    protected static ClayTech plugin;
    private static String locale;
    private static DataYML currentLangYML;
    private static DataYML planetYML;
    private static String highrailspeed;
    private static final List<Planet> planetList = new ArrayList<>();
    private static String overworld = "";
    private static DataYML planetDataYML;
    private static ClayTechUpdater updater;
    private static boolean spacetravelneedperm;
    private static String updateBranch;
    private static FileConfiguration config;
    private static DataYML defaultLangYML;
    private static boolean worldBorderEnabled;

    public static ClayTech getInstance() {
        return plugin;
    }

    public static String getLocale() {
        return locale;
    }

    public static DataYML getLangYML() {
        return currentLangYML;
    }

    public static boolean isSpaceTravelNeedPerm() {
        return spacetravelneedperm;
    }

    public static String getHighRailSpeed() {
        return highrailspeed;
    }

    public static ClayTechUpdater getUpdater() {
        return updater;
    }

    public static List<Planet> getPlanets() {
        return planetList;
    }

    public static String getOverworld() {
        return overworld;
    }

    public static DataYML getPlanetYML() {
        return planetYML;
    }

    public static DataYML getPlanetDataYML() {
        return planetDataYML;
    }

    public static String getUpdateBranch() {
        return updateBranch;
    }

    public static DataYML getDefaultLangYML() {
        return defaultLangYML;
    }

    public static boolean isWorldBorderEnabled() {
        return worldBorderEnabled;
    }

    @SuppressWarnings({"unused"})
    @Override
    public void onEnable() {
        plugin = this;
        // 当前研究ID: 9936
        DataYML configDYML = new DataYML("config.yml", this);
        config = configDYML.getCustomConfig();
        configDYML.saveCustomConfig();
        config = this.getConfig();
        locale = config.getString("Locale");
        if (locale == null)
            locale = "en-US";
        highrailspeed = config.getString("highrailspeed");
        if (highrailspeed == null)
            highrailspeed = "3";
        if (!new File(getDataFolder() + "/" + locale + ".yml").exists()) {
            Bukkit.getLogger().info("§cLoading Error: Locale not found.Disabling plugin..");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        ConfigUtils.updateConfig(configDYML);
        configDYML.saveCustomConfig();
        configDYML.reloadCustomConfig(this);
        overworld = config.getString("overworld");
        currentLangYML = new DataYML(locale + ".yml", this);
        currentLangYML.saveCDefaultConfig();
        currentLangYML.reloadCustomConfig(this);
        FileConfiguration currentLang = currentLangYML.getCustomConfig();
        defaultLangYML = new DataYML("en-US.yml", this);
        FileConfiguration defaultLang = defaultLangYML.getCustomConfig();
        currentLangYML.saveCustomConfig();
        currentLangYML.reloadCustomConfig(this);
        defaultLangYML.saveCustomConfig();
        defaultLangYML.reloadCustomConfig(this);

        Metrics mt = new Metrics(this, 6887);
        mt.addCustomChart(new Metrics.SimplePie("language", () -> languageCodeToLanguage(locale)));

        planetYML = new DataYML("planets.yml", this);
        planetYML.saveCDefaultConfig();
        planetYML.reloadCustomConfig(this);
        planetDataYML = new DataYML("planetsdata.yml", this);
        planetDataYML.saveCDefaultConfig();
        planetDataYML.reloadCustomConfig(this);
        Bukkit.getLogger().info(Lang.readGeneralText("startTip"));

        saveDefaultConfig();
        saveConfig();

        Bukkit.getLogger().info(Lang.readGeneralText("registeringItems"));
        try {
            registerSlimefun();
            registerPlanets();
            registerResources();
        } catch (Exception e) {
            Bukkit.getLogger().info(Lang.readGeneralText("registeringError"));
            e.printStackTrace();
        }
        if (this.getServer().getPluginManager().isPluginEnabled("WorldBorder")) {
            Bukkit.getLogger().info(Lang.readGeneralText("WorldBorder"));
            worldBorderEnabled = true;
        }

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemUseListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodEatListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeaponListener(), this);
        Bukkit.getPluginManager().registerEvents(new RailwayListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlanetListener(), this);
        Bukkit.getPluginManager().registerEvents(new RocketLauncherListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlanetBaseListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockUseListener(), this);

        this.getCommand("claytech").setExecutor(new ClayTechCommands());

        spacetravelneedperm = config.getBoolean("space-travel-need-perm");

        ClayTechData.currentVersion = this.getDescription().getVersion();
        new BukkitRunnable() {

            @Override
            public void run() {
                // Updater
                updateBranch = config.getString("update-branch");
                updater = new ClayTechUpdater();
                if (!getConfig().getBoolean("disable-auto-updater")) {

                    updater.tryUpdate();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            updater.tryUpdate();
                        }

                    }.runTaskTimerAsynchronously(ClayTech.getInstance(), 1728000, 1728000);
                } else {
                    Bukkit.getLogger().info(ChatColor.YELLOW + Lang.readGeneralText("Info_1"));
                    Bukkit.getLogger().info(ChatColor.YELLOW + Lang.readGeneralText("Auto-updater_disabled"));
                    Bukkit.getLogger().info(ChatColor.YELLOW + Lang.readGeneralText("Info_6"));
                }
                List<String> Authors = plugin.getDescription().getAuthors();
                Bukkit.getLogger().info(ChatColor.GREEN + Lang.readGeneralText("Info_1"));
                Bukkit.getLogger().info(ChatColor.GREEN + Lang.readGeneralText("Info_2").replaceAll("\\{version}",
                        plugin.getDescription().getVersion()));
                Bukkit.getLogger().info(ChatColor.GREEN + Lang.readGeneralText("Info_3").replaceAll("\\{author}",
                        ListUtils.toString(Authors)));
                Bukkit.getLogger().info(ChatColor.GREEN + Lang.readGeneralText("Info_4"));
                Bukkit.getLogger().info(ChatColor.GREEN
                        + Lang.readGeneralText("Info_5").replaceAll("\\{issue_tracker}", plugin.getBugTrackerURL()));
                Bukkit.getLogger().info(ChatColor.GREEN + Lang.readGeneralText("Info_6"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Planet p = PlanetUtils.getPlanet(player.getWorld());
                    if (p != null) {
                        if (!p.getHabitable()) {
                            World PreviousWorld = player.getWorld();
                            new BukkitRunnable() {
                                @SuppressWarnings("deprecation")
                                @Override
                                public void run() {
                                    if (!PreviousWorld.equals(player.getWorld()) || !player.isOnline()) {
                                        this.cancel();
                                        return;
                                    }
                                    if (!(ClayTechManager.isSpaceSuit(player.getInventory().getHelmet())
                                            && ClayTechManager.isSpaceSuit(player.getInventory().getChestplate())
                                            && ClayTechManager.isSpaceSuit(player.getInventory().getLeggings())
                                            && ClayTechManager.isSpaceSuit(player.getInventory().getBoots()))) {
                                        // 扣血
                                        player.sendTitle(Lang.readGeneralText("SpaceSuitError"),
                                                Lang.readGeneralText("SpaceSuitError_Sub"));
                                        player.damage(5);

                                    } else {
                                        if (!(RocketUtils.getOxygen(player.getInventory().getHelmet()) > 0
                                                && RocketUtils.getOxygen(player.getInventory().getChestplate()) > 0
                                                && RocketUtils.getOxygen(player.getInventory().getLeggings()) > 0
                                                && RocketUtils.getOxygen(player.getInventory().getBoots()) > 0)) {
                                            // 扣血
                                            player.sendTitle(Lang.readGeneralText("SpaceSuitError"),
                                                    Lang.readGeneralText("SpaceSuitError_Sub"));
                                            player.damage(5);
                                        } else {
                                            int harmlevel = p.getHarmLevel();
                                            if (RocketUtils
                                                    .getProtectLevel(player.getInventory().getHelmet()) < harmlevel
                                                    || RocketUtils.getProtectLevel(
                                                    player.getInventory().getChestplate()) < harmlevel
                                                    || RocketUtils.getProtectLevel(
                                                    player.getInventory().getLeggings()) < harmlevel
                                                    || RocketUtils.getProtectLevel(
                                                    player.getInventory().getBoots()) < harmlevel) {
                                                // 扣血
                                                player.sendTitle(Lang.readGeneralText("SpaceSuitError"),
                                                        Lang.readGeneralText("SpaceSuitError_Sub"));
                                                player.damage(5);
                                            }
                                        }
                                    }
                                }

                            }.runTaskTimer(ClayTech.getInstance(), 20, 20);
                        }
                    }
                }
            }

        }.runTaskAsynchronously(this);

    }

    @Override
    public void onDisable() {
        if (ClayTech.getInstance().getConfig().getBoolean("replace-when-server-stops")) {
            if (ClayTechData.jarLocation != null & ClayTechData.updateJar != null) {
                try {
                    FileOutputStream os = new FileOutputStream(ClayTechData.jarLocation);
                    os.write(ClayTechData.updateJar);
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String languageCodeToLanguage(String code) {
        return switch (code.toUpperCase()) {
            case "ZH-CN" -> "Simplified Chinese";
            case "ZH-TW" -> "Traditional Chinese";
            case "EN-US" -> "English(US)";
            default -> code;
        };
    }

    private void registerSlimefun() {
        new Machines();

        new Clay_basic();
        new PotionAffect_Weapons();
        new Golden_things();
        new Skulls();
        new Armors();
        new DrinkMakingStaff();
        new Drinks();
        new FoodMakingStaff();
        new Foods();
        new MachineMakingBasic();
        new Elements();
        new Railways();
        new EffectItems();
        new Ingots();
        new Tools();
        new ClayFuelResource();
        new RocketMakings();
        new Rockets();
        new Spacethings();
    }


    @Override
    public @NotNull JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public @NotNull File getFile() {
        return super.getFile();
    }

    @Override
    @NotNull
    public String getBugTrackerURL() {
        return "https://github.com/ClayCoffee/ClayTech/issues";
    }

    private void registerPlanets() {
        // Earth(Overworld) 地球(主世界)
        new Earth();
        // Moon 月球
        new Moon();
        // Mars 火星
        new Mars();
    }

    private void registerResources() {
        new ClayFuel();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id) {
        List<String> PlanetNameList = new ArrayList<>();
        List<Planet> PlanetList = getPlanets();
        for (Planet p : PlanetList) {
            PlanetNameList.add(p.getPlanetWorldName());
        }
        if (ListUtils.existsInStringList(PlanetNameList, id)) {
            return PlanetList.get(PlanetNameList.indexOf(id)).getPlanetGenerator();
        }
        return Objects.requireNonNull(Bukkit.getWorld(getOverworld())).getGenerator();
    }
}
