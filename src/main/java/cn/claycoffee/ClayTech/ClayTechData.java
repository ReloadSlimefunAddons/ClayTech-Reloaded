package cn.claycoffee.ClayTech;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class ClayTechData {
    public static String currentVersion = "";
    public static String jarLocation;
    public static byte[] updateJar;
    public static Map<Inventory, Block> RunningInjectors;
    public static Map<Inventory, Block> RunningInjectorsOxygen;
}
