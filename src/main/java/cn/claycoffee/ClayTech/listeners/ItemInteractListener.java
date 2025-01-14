package cn.claycoffee.ClayTech.listeners;

import cn.claycoffee.ClayTech.utils.ItemUtil;
import cn.claycoffee.ClayTech.utils.Lang;
import cn.claycoffee.ClayTech.utils.Utils;
import cn.claycoffee.clayapi.utils.ListUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        if (ListUtils.existsInStringList(ItemUtil.getLore(e.getItemInHand()), Lang.readGeneralText("CantPlaceLore"))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Lang.readGeneralText("CantPlace"));
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if (e.hasItem()) {
            if (e.getItem().hasItemMeta() && !e.isCancelled()) {
                if (ListUtils.existsInStringList(ItemUtil.getLore(e.getItem()), Lang.readGeneralText("CantEat"))) {
                    e.getPlayer().sendMessage(Lang.readGeneralText("CantEatMessage"));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.ExitsInList(Lang.readGeneralText("CantInteract"), Utils.getLore(e.getItem()))
                        && e.hasBlock()) {
                    e.getPlayer().sendMessage(Lang.readGeneralText("CantInteractMessage"));
                    e.setCancelled(true);
                }
            }
        }
    }
}
