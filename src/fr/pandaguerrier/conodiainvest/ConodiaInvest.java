package fr.pandaguerrier.conodiainvest;

import fr.pandaguerrier.conodiainvest.utils.registers.Commands;
import fr.pandaguerrier.conodiainvest.utils.registers.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ConodiaInvest extends JavaPlugin {
    public static ConodiaInvest getInstance;

    // Player: joueur, String: time
    private final HashMap<Player, Object> hashInvest = new HashMap<>();

    public void onEnable() {
        getInstance = this;

        Commands.load();
        Events.load();
        System.out.println("\n \n-------------------------\n \nLe ConodiaInvest est connecté !\n \n-------------------------\n \n");

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : hashInvest.keySet()) {
                    HashMap hash = (HashMap) hashInvest.get(player);
                    int currentTime = (int) hash.get("currentTime");
                    int time = (int) hash.get("time");

                    if (currentTime < time) {
                        currentTime++;
                        hash.put("currentTime", currentTime);
                        hashInvest.put(player, hash);
                    } else {
                        hashInvest.remove(player);
                        player.sendMessage("§bConodiaInvest §7» §aVotre investissement est terminé !");

                        Bukkit.getScheduler().runTask(ConodiaInvest.getInstance, () -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + hash.get("money")));
                    }
                }
            }
        }, 0L, 20L);
    }

    public void onDisable() {
        System.out.println("\n \n-------------------------\n \nLe ConodiaInvest est déconnecté !\n \n-------------------------\n \n");
    }

    public HashMap<Player, Object> getHashInvest() {
        return hashInvest;
    }
}