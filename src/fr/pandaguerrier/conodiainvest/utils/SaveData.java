package fr.pandaguerrier.conodiainvest.utils;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SaveData {
    public static void save() {
        ConodiaInvest plugin = ConodiaInvest.getInstance;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : ConodiaInvest.getInstance.getHashInvest().keySet()) {
                HashMap hashInvest = (HashMap) plugin.getHashInvest().get(player);

                plugin.getCustomConfig().set("invest." + player.getName() + ".currentTime", hashInvest.get("currentTime"));
                plugin.getCustomConfig().set("invest." + player.getName() + ".time", hashInvest.get("time"));
                plugin.getCustomConfig().set("invest." + player.getName() + ".money", hashInvest.get("money"));

                System.out.println("Save data for " + player.getName() + "(" + hashInvest.get("currentTime") + "/" + hashInvest.get("time") + ", money: " + hashInvest.get("money") + ")");
            }
            plugin.saveCustomConfig();
        }, 0L, 20 * 60 * 1);

    }
}
