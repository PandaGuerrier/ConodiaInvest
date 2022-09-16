package fr.pandaguerrier.conodiainvest.listeners;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

public class InventoryClick implements Listener {
    @SuppressWarnings({"unused"})
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (inventory.getName().equals("§9➜ §bInvest")) {
            event.setCancelled(true);
            switch (clicked.getItemMeta().getDisplayName()) {
                case "§e10k":
                    JSONObject obj = new JSONObject();

                    obj.put("time", 100);
                    obj.put("currentTime", 0);
                    obj.put("money", 10000);

                    ConodiaInvest.getInstance.getHashInvest().putIfAbsent(player, obj);
                    player.closeInventory();
                    player.sendMessage("§aVous avez investi §e10k §a!");
                    break;
            }
        }
    }
}
