package cn.claycoffee.ClayTech.implementation.guis;

import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.clayapi.guis.GUI;
import cn.claycoffee.clayapi.handlers.MenuClickHandler;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @Project: ClayTech
 * @Author: ClayCoffee
 * @Date: 2020/8/23 15:24
 * @Email: 1020757140@qq.com
 * AGPL 3.0
 */

public class ClayAirLockerGUI extends GUI {
    private static final int[] border = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private final Block b;

    public ClayAirLockerGUI(int size, String title, boolean isProtected, Block b) {
        super(size, title, isProtected);
        
        this.b = b;
    }


    @Override
    public void init() {
        try {
            this.registerBlockHandler(new MenuClickHandler(this, new int[]{11, 15}) {
                @Override
                public void onLeftClick(Player player, int i, Inventory inv) {
                    int waitTime = Integer.parseInt(StorageCacheUtils.getData(b.getLocation(), "wait-time"));
                    if (i == 11) {
                        if (waitTime <= 1) return;
                        waitTime--;
                    } else {
                        waitTime++;
                    }
                    StorageCacheUtils.setData(b.getLocation(), "wait-time", waitTime + "");
                    show(player);
                }

                @Override
                public void onRightClick(Player player, int i, Inventory inventory) {

                }

            });
        } catch (cn.claycoffee.clayapi.exceptions.AlreadyProtectedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupMenu(Inventory inventory, Player player) {
        if (this.b != null) {
            int waitTime = 5;
            if (StorageCacheUtils.hasBlock(b.getLocation()) && StorageCacheUtils.getData(b.getLocation(), "wait-time") != null) {
                waitTime = Integer.parseInt(StorageCacheUtils.getData(b.getLocation(), "wait-time"));
            } else {
                StorageCacheUtils.setData(b.getLocation(), "wait-time", waitTime + "");
            }
            ItemStack borderItem = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, Lang.readMachinesText("SPLIT_LINE"));
            ItemStack waitTimeItem = new CustomItemStack(Material.CLOCK, Lang.readMachinesText("wait-time").replaceAll("%TIME%", waitTime + ""));
            ItemStack add = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, Lang.readMachinesText("add"));
            ItemStack sub = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, Lang.readMachinesText("sub"));
            for (int i : border) {
                inventory.setItem(i, borderItem);
            }
            inventory.setItem(11, sub);
            inventory.setItem(15, add);
            inventory.setItem(13, waitTimeItem);
        }
    }


    @Override
    public String getID() {
        return "CLAYTECH_AIR_LOCKER";
    }
}
