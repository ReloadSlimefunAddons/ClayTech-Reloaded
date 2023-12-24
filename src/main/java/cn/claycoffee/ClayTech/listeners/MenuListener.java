package cn.claycoffee.ClayTech.listeners;

import cn.claycoffee.ClayTech.utils.ListUtils;
import cn.claycoffee.clayapi.guis.GUIHolder;
import cn.claycoffee.clayapi.handlers.ItemProtectHandler;
import cn.claycoffee.clayapi.handlers.MenuActionHandler;
import cn.claycoffee.clayapi.handlers.MenuBlockHandler;
import cn.claycoffee.clayapi.handlers.MenuClickHandler;
import cn.claycoffee.clayapi.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * @Project: ClayAPI
 * @Author: ClayCoffee
 * @Date: 2020/7/29 9:51
 * @Email: 1020757140@qq.com
 * AGPL 3.0
 */

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GUIHolder holder) {
            if (holder.getGUI().isProtected()) {
                e.setCancelled(true);
            }
            for (MenuBlockHandler mbh : holder.getGUI().getHandlers()) {
                if (mbh instanceof MenuClickHandler) {
                    if (MenuUtils.isSame(mbh.getMenu(), e.getClickedInventory(), (Player) e.getWhoClicked())) {
                        if (ListUtils.existsInArray(((MenuClickHandler) mbh).getSlots(), e.getRawSlot())) {
                            if (e.isLeftClick())
                                ((MenuClickHandler) mbh).onLeftClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getInventory());
                            if (e.isRightClick())
                                ((MenuClickHandler) mbh).onRightClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getInventory());
                            return;
                        }
                    }
                } else if (mbh instanceof ItemProtectHandler) {
                    if (MenuUtils.isSame(mbh.getMenu(), e.getClickedInventory(), (Player) e.getWhoClicked())) {
                        ItemProtectHandler iph = (ItemProtectHandler) mbh;
                        if (ListUtils.existsInArray(iph.getSlot(), e.getRawSlot())) e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMenuClosed(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof GUIHolder holder) {
            for (MenuBlockHandler mbh : holder.getGUI().getHandlers()) {
                if (mbh instanceof MenuActionHandler) {
                    if (MenuUtils.isSame(mbh.getMenu(), e.getInventory(), (Player) e.getPlayer())) {
                        MenuActionHandler mah = (MenuActionHandler) mbh;
                        mah.onClose((Player) e.getPlayer(), e.getInventory());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMenuOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof GUIHolder holder) {
            for (MenuBlockHandler mbh : holder.getGUI().getHandlers()) {
                if (mbh instanceof MenuActionHandler) {
                    if (MenuUtils.isSame(mbh.getMenu(), e.getInventory(), (Player) e.getPlayer())) {
                        MenuActionHandler mah = (MenuActionHandler) mbh;
                        mah.onOpen((Player) e.getPlayer(), e.getInventory());
                    }
                }
            }
        }
    }
}
