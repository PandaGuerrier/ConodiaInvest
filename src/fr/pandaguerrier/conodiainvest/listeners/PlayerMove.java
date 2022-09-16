package fr.pandaguerrier.conodiainvest.listeners;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.utils.Utils;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class PlayerMove implements Listener {
    private final HashMap hashInvest = ConodiaInvest.getInstance.getHashInvest();
    @SuppressWarnings({ "unused" })
	@EventHandler
    public void onMove(PlayerMoveEvent event) {
         Player player = event.getPlayer();

        if(Utils.playerZone(player)) {
            if(hashInvest.get(player) == null) {
                Utils.sendTitle(player, "§4⚠ §cAucun investissement §4⚠", "§bVous pouvez en faire un en faisant §9/invest");
            }
        }
    }
}
