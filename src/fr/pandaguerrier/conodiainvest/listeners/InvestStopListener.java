package fr.pandaguerrier.conodiainvest.listeners;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.events.InvestStop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InvestStopListener implements Listener {
    @EventHandler
    public void onInvestStop(InvestStop event) {
        Player player = event.getPlayer();
        int money = event.getMoney();

        player.sendMessage("§bConodiaInvest §7» §aVotre investissement est terminé, vous avez donc gagné " + money + "$ !");

        Bukkit.getScheduler().runTask(ConodiaInvest.getInstance, () -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + money));
        ConodiaInvest.getInstance.getHashInvest().remove(player);

        ConodiaInvest.getInstance.getCustomConfig().set("invest." + player.getName(), null);
        ConodiaInvest.getInstance.saveCustomConfig();
    }
}
