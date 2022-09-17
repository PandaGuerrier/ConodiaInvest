package fr.pandaguerrier.conodiainvest.listeners;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Player implements Listener {
    ConodiaInvest plugin = ConodiaInvest.getInstance;
    @SuppressWarnings({"unused"})
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getCustomConfig().get("invest." + event.getPlayer().getName()) != null) {

            HashMap<String, Object> hashInvest = new HashMap<>();
            hashInvest.put("currentTime", plugin.getCustomConfig().get("invest." + event.getPlayer().getName() + ".currentTime"));
            hashInvest.put("time", plugin.getCustomConfig().get("invest." + event.getPlayer().getName() + ".time"));
            hashInvest.put("money", plugin.getCustomConfig().get("invest." + event.getPlayer().getName() + ".money"));

            plugin.getHashInvest().put(event.getPlayer(), hashInvest);
        }
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent event) {
        if (plugin.getHashInvest().get(event.getPlayer()) != null) {
            HashMap<String, Object> hashInvest = (HashMap) plugin.getHashInvest().get(event.getPlayer());

            plugin.getCustomConfig().set("invest." + event.getPlayer().getName() + ".currentTime", hashInvest.get("currentTime"));
            plugin.getCustomConfig().set("invest." + event.getPlayer().getName() + ".time", hashInvest.get("time"));
            plugin.getCustomConfig().set("invest." + event.getPlayer().getName() + ".money", hashInvest.get("money"));

            plugin.saveCustomConfig();
            plugin.getHashInvest().remove(event.getPlayer());
        }

    }
}
