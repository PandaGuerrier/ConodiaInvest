package fr.pandaguerrier.conodiainvest.utils;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class Utils {


    public static boolean playerZone(Player player) {
        Location coord1 = new Location(player.getWorld(), 3962, 73, 4986);
        Location coord2 = new Location(player.getWorld(), 3965, 78, 4988);

        if ((player.getLocation().getBlockX() > coord1.getBlockX()) && (player.getLocation().getBlockX() < coord2.getBlockX())) {
            if ((player.getLocation().getBlockY() > coord1.getBlockY()) && (player.getLocation().getBlockY() < coord2.getBlockY())) {
                if ((player.getLocation().getBlockZ() > coord1.getBlockZ()) && (player.getLocation().getBlockZ() < coord2.getBlockZ())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void investProgress() {
        ConodiaInvest.getInstance.getServer().getScheduler().scheduleSyncRepeatingTask(ConodiaInvest.getInstance, new Runnable() {
            @Override
            public void run() {
                for (Player player : ConodiaInvest.getInstance.getHashInvest().keySet()) {
                    if (playerZone(player)) {
                        HashMap hash = (HashMap) ConodiaInvest.getInstance.getHashInvest().get(player);
                        int currentTime = (int) hash.get("currentTime");
                        int time = (int) hash.get("time");

                        if (currentTime < time) {
                            currentTime++;
                            hash.put("currentTime", currentTime);
                            ConodiaInvest.getInstance.getHashInvest().put(player, hash);
                        } else {
                            ConodiaInvest.getInstance.getHashInvest().remove(player);
                            player.sendMessage("§bConodiaInvest §7» §aVotre investissement est terminé !");

                            Bukkit.getScheduler().runTask(ConodiaInvest.getInstance, () -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + hash.get("money")));
                        }
                        // send a title and subtitle to the player

                    }
                }
            }
        }, 0L, 20L);
    }
    /*
    public static void progress(Player player, String job, double current) {
        HashMap obj = (HashMap) ConodiaInvest.instance.getHashJobs().get(player.getName());

        DecimalFormat df = new DecimalFormat("#.#");
        int max = ((int) obj.get("lvl" + job) + 1) * 2500;

        double percentage =  current / max;
        int progress = (int) Math.round((20 * percentage));
        int emptyProgress = 20 - progress;
        // Idée des signes a utiliser au cas où ça me plaît pas :D --> ┃┃┃┃┃┃┃┃▊▊ ▋ ▍▍ ▎▎ ▉▉ ▏▏ ●●
        String progressText = StringUtils.repeat("▍", progress);
        String emptyProgressText = StringUtils.repeat("▍", emptyProgress);


        //return "§b" + progressText + "§8" + emptyProgressText + " §9(" + df.format(percentage * 100) + "%)";
    }*/

    public static ItemStack createGuiItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
