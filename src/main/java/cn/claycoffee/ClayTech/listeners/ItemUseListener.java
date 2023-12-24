package cn.claycoffee.ClayTech.listeners;

import cn.claycoffee.ClayTech.ClayTech;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.MetadataUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.*;

public class ItemUseListener implements Listener {
    public static Map<Block, String> player = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void EntityExplodeEvent(EntityExplodeEvent e) {
        List<Block> blockL = new ArrayList<>(e.blockList());
        boolean already = false;
        for (Block each : blockL) {
            for (MetadataValue eachv : each.getMetadata("isExplosionCreater")) {
                if (Objects.equals(eachv.getOwningPlugin(), ClayTech.getInstance())) {
                    Player p = Bukkit.getPlayer(player.get(each));
                    player.put(each, null);
                    if (p != null) {
                        if (e.isCancelled()) {
                            p.sendMessage(Lang.readGeneralText("TNT_EXPLOSION_CREATER_FATAL"));
                            return;
                        } else {
                            if (!already) {
                                p.sendMessage(Lang.readGeneralText("TNT_EXPLOSION_CREATER_SUCCESS"));
                                already = true;
                            }

                        }
                        if (eachv.asBoolean()) {
                            e.blockList().removeIf(next -> next.getType() == Material.CHEST || next.getType() == Material.FURNACE);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (MetadataUtil.getMetadata(e.getBlock(), "cantDestroy") != null) {
            boolean canDestroy = !Objects.requireNonNull(MetadataUtil.getMetadata(e.getBlock(), "cantDestroy")).asBoolean();
            if (!canDestroy) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Lang.readGeneralText("cantBreak"));
            }
        }
        if (MetadataUtil.getMetadataAsBoolean(e.getPlayer(), "inRocket")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Lang.readGeneralText("cantBreak"));
        }
    }
}
