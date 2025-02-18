package cn.claycoffee.ClayTech.utils;

import cn.claycoffee.ClayTech.api.ClayTechManager;
import cn.claycoffee.clayapi.utils.StringUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

// TODO: refactor, read nbt
public class RocketUtils {
    public static int getFuel(ItemStack im) {
        if (ClayTechManager.isRocket(im)) {
            for (String str : ItemUtil.getLore(im)) {
                if (str.startsWith(Lang.readGeneralText("Fuel"))) {
                    str = StringUtils.getRightStr(str, "§6");
                    str = StringUtils.getLeftStr(str, "§9");
                    return Integer.parseInt(str);
                }
            }
        } else {
            return -1;
        }
        return -1;
    }

    public static int getMaxFuel(ItemStack im) {
        if (ClayTechManager.isRocket(im)) {
            for (String str : ItemUtil.getLore(im)) {
                if (str.startsWith(Lang.readGeneralText("Fuel"))) {
                    str = StringUtils.getRightStr(str, "§9/§a");
                    return Integer.parseInt(str);
                }
            }
        } else {
            return -1;
        }
        return -1;
    }

    public static void setFuel(ItemStack im, int fuel) {
        if (ClayTechManager.isRocket(im)) {
            int i = 0;
            List<String> lore = ItemUtil.getLore(im);
            for (String str : lore) {
                if (str.startsWith(Lang.readGeneralText("Fuel"))) {
                    str = str.replaceAll("§6" + getFuel(im), "§6" + fuel);
                    lore.set(i, str);
                }
                i++;
            }
            ItemUtil.setLore(im, lore);
        }
    }

    public static int getOxygen(ItemStack im) {
        if (ClayTechManager.isSpaceSuit(im) || ClayTechManager.isOxygenDistributer(im)) {
            for (String str : ItemUtil.getLore(im)) {
                if (str.startsWith(Lang.readGeneralText("Oxygen"))) {
                    str = StringUtils.getRightStr(str, "§6");
                    str = StringUtils.getLeftStr(str, "§9");
                    return Integer.parseInt(str);
                }
            }
        } else {
            return -1;
        }
        return -1;
    }

    public static void setOxygen(ItemStack im, int oxygen) {
        if (ClayTechManager.isSpaceSuit(im) || ClayTechManager.isOxygenDistributer(im)) {
            int i = 0;
            List<String> lore = ItemUtil.getLore(im);
            for (String str : lore) {
                if (str.startsWith(Lang.readGeneralText("Oxygen"))) {
                    str = str.replaceAll("§6" + getOxygen(im), "§6" + oxygen);
                    lore.set(i, str);
                }
                i++;
            }
            ItemUtil.setLore(im, lore);
        }
    }

    public static int getMaxOxygen(ItemStack im) {
        if (ClayTechManager.isSpaceSuit(im) || ClayTechManager.isOxygenDistributer(im)) {
            for (String str : ItemUtil.getLore(im)) {
                if (str.startsWith(Lang.readGeneralText("Oxygen"))) {
                    str = StringUtils.getRightStr(str, "§9/§a");
                    return Integer.parseInt(str);
                }
            }
        } else {
            return -1;
        }
        return -1;
    }

    public static int getProtectLevel(ItemStack im) {
        if (ClayTechManager.isSpaceSuit(im)) {
            for (String str : ItemUtil.getLore(im)) {
                if (str.startsWith(Lang.readGeneralText("ProtectLevel"))) {
                    str = StringUtils.getRightStr(str, "§6");
                    return Integer.parseInt(str);
                }
            }
        } else {
            return -1;
        }
        return -1;
    }
}
