package cn.claycoffee.ClayTech.api;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.ClayTechItems;
import cn.claycoffee.ClayTech.utils.Lang;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;

/**
 * the ClayTech API manager. 粘土科技API管理器
 */

// TODO: refactor
public class ClayTechManager {
    /**
     * @param item The item.物品.
     * @return the ItemStack is ClayTech item or not. 这个ItemStack是不是粘土科技物品.
     */
    public static boolean isClayTechItem(ItemStack item) {
        Field[] fields = ClayTechItems.class.getDeclaredFields();
        for (Field field : fields) {
            ItemStack is;
            try {
                is = (ItemStack) field.get(null);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return false;
            }

            if (is != null && is.isSimilar(item)) {
                return true;
            } else if (isRocket(is) || isSpaceSuit(is) || isOxygenDistributer(is)) return true;
        }
        return false;
    }

    public static boolean isRocket(ItemStack item) {
        if (item == null)
            return false;
        if (item.hasItemMeta()) {
            return item.getItemMeta().getDisplayName().startsWith(Lang.readGeneralText("Rocket"));
        }
        return false;
    }

    public static boolean isSpaceSuit(ItemStack item) {
        if (item == null)
            return false;
        if (item.hasItemMeta()) {
            return item.getItemMeta().getDisplayName().startsWith(Lang.readGeneralText("SpaceSuit"));
        }
        return false;
    }

    public static boolean isOxygenDistributer(ItemStack item) {
        if (item == null)
            return false;
        if (item.hasItemMeta()) {
            return item.getItemMeta().getDisplayName().startsWith(Lang.readGeneralText("OxygenDistributer"));
        }
        return false;
    }

    public static void allowSpaceTeleportOnce(Player p) {
        p.setMetadata("allowSpaceTeleport", new FixedMetadataValue(ClayTech.getInstance(), true));
    }
}
