package fr.pandaguerrier.conodiainvest.listeners;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class InventoryClickListener implements Listener {
    ConodiaInvest plugin = ConodiaInvest.getInstance;
    @SuppressWarnings({"unused"})
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (inventory.getName().equals("§9➜ §bInvest") && clicked.hasItemMeta()) {
            event.setCancelled(true);

            if (clicked.getItemMeta().hasDisplayName()) {
                switch (clicked.getItemMeta().getDisplayName()) {
                    case "§e10k":
                        if (plugin.getEconomy().getBalance(player) >= 10000) {
                            //ConodiaInvest.getInstance.getEconomy().withdrawPlayer(player, 10000);

                            addToHashMap(player, 1800, 10000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§e100k":
                        if (plugin.getEconomy().getBalance(player) >= 50000) {
                            addToHashMap(player, 5400, 100000);
                            plugin.getEconomy().withdrawPlayer(player, 50000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§e300k":
                        if (plugin.getEconomy().getBalance(player) >= 200000) {
                            addToHashMap(player, 8100, 300000);
                            plugin.getEconomy().withdrawPlayer(player, 200000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§e500k":
                        if (plugin.getEconomy().getBalance(player) >= 350000) {
                            addToHashMap(player, 10800, 500000);
                            plugin.getEconomy().withdrawPlayer(player, 350000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§e1M":
                        if (plugin.getEconomy().getBalance(player) >= 650000) {
                            addToHashMap(player, 18000, 1000000);
                            plugin.getEconomy().withdrawPlayer(player, 650000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§e3M":
                        if (plugin.getEconomy().getBalance(player) >= 1600000) {
                            addToHashMap(player, 18000, 3000000);
                            plugin.getEconomy().withdrawPlayer(player, 1600000);
                        } else {
                            player.sendMessage("§bConodiaInvest §7» §cVous n'avez pas assez d'argent !");
                        }
                        break;
                    case "§cCliquez ici pour annuler (Vous recevrez uniquement 75% de votre argent investie).":
                        int moneyToGive;
                        HashMap hash = (HashMap) ConodiaInvest.getInstance.getHashInvest().get(player);
                        switch (hash.get("money").toString()) {
                            case "10000":
                                moneyToGive = 0;
                                break;
                            case "100000":
                                moneyToGive = 37500;
                                break;
                            case "300000":
                                moneyToGive = 150000;
                                break;
                            case "500000":
                                moneyToGive = 262500;
                                break;
                            case "1000000":
                                moneyToGive = 487500;
                                break;
                            case "3000000":
                                moneyToGive = 1200000;
                                break;
                            default:
                                moneyToGive = 0;
                                break;
                        }

                        ConodiaInvest.getInstance.getHashInvest().remove(player);
                        player.sendMessage("§cVous avez annulé votre investissement.");
                        player.sendMessage("§cVous avez reçu §e" + moneyToGive + "§c.");
                        player.closeInventory();
                        Bukkit.getScheduler().runTask(ConodiaInvest.getInstance, () -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + moneyToGive));
                }
            }
        }
    }

    private void addToHashMap(Player player, int time, int money) {

        JSONObject obj = new JSONObject();

        obj.put("time", time);
        obj.put("currentTime", 0);
        obj.put("money", money);

        ConodiaInvest.getInstance.getHashInvest().putIfAbsent(player, obj);

        player.closeInventory();
        player.sendMessage("§bVous avez investi pour recevoir: §9" + money + "$§b.");
    }
}
