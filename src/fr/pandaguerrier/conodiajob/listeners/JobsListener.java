package fr.pandaguerrier.conodiajob.listeners;

import com.massivecraft.factions.*;
import fr.pandaguerrier.conodiajob.Config;
import fr.pandaguerrier.conodiajob.ConodiaJobs;
import fr.pandaguerrier.conodiajob.jobs.JobsImplements;
import fr.pandaguerrier.conodiajob.utils.JobsEnum;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.material.Crops;

import java.util.Arrays;

public class JobsListener implements Listener {
    
    private final Config config = ConodiaJobs.instance.getJobsConfig();

    /**
     *
     * Event Bucheron
     *
     */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void bucheron(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        FLocation flocation = new FLocation(block.getLocation().getWorld().getName(), block.getLocation().getChunk().getX(), block.getLocation().getChunk().getZ());
        Faction faction = Board.getInstance().getFactionAt(flocation);

        if ((faction.getTag().equals(fplayer.getFaction().getTag()) || faction.getTag().equals("§2Wilderness")) && config.getObject("jobs.bucheron.xp").containsKey(block.getType().toString())) {
            double xpBlock = (double) config.getObject("jobs.bucheron.xp").getOrDefault(block.getType().toString(), 0.0);
            JobsImplements jobs = new JobsImplements();
            jobs.addXp(player.getName(), JobsEnum.BUCHERON, xpBlock);
        }
    }

    /**
     *
     * Event Farmeur
     *
     */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void farmer(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        FLocation flocation = new FLocation(block.getLocation().getWorld().getName(), block.getLocation().getChunk().getX(), block.getLocation().getChunk().getZ());
        Faction faction = Board.getInstance().getFactionAt(flocation);

        if ((faction.getTag().equals(fplayer.getFaction().getTag()) || faction.getTag().equals("§2Wilderness")) && config.getObject("jobs.farmeur.xp").containsKey(block.getType().toString())) {
            if (isRipe(block)) {
                double xpBlock = (double) config.getObject("jobs.farmeur.xp").getOrDefault(block.getType().toString(), 0.0);
                JobsImplements jobs = new JobsImplements();
                jobs.addXp(player.getName(), JobsEnum.FARMEUR, xpBlock);
            }
        }
    }

    /**
     *
     * Event Mineur
     *
     */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void miner(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        FLocation flocation = new FLocation(block.getLocation().getWorld().getName(), block.getLocation().getChunk().getX(), block.getLocation().getChunk().getZ());
        Faction faction = Board.getInstance().getFactionAt(flocation);

        if ((faction.getTag().equals(fplayer.getFaction().getTag()) || faction.getTag().equals("§2Wilderness")) && config.getObject("jobs.mineur.xp").containsKey(block.getType().toString())) {
            double xpBlock = (double) config.getObject("jobs.mineur.xp").getOrDefault(block.getType().toString(), 0.0);
            JobsImplements jobs = new JobsImplements();
            jobs.addXp(player.getName(), JobsEnum.MINEUR, xpBlock);
        }
    }

    /**
     *
     * Event Constructeur
     *
     */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void builder(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        FLocation flocation = new FLocation(block.getLocation().getWorld().getName(), block.getLocation().getChunk().getX(), block.getLocation().getChunk().getZ());
        Faction faction = Board.getInstance().getFactionAt(flocation);

        Material[] nonMinerableBlock = {
                Material.CROPS,
                Material.CARROT,
                Material.CACTUS,
                Material.POTATO,
                Material.SUGAR_CANE_BLOCK,
                Material.NETHER_WARTS,
                Material.BEDROCK
        };

        if ((faction.getTag().equals(fplayer.getFaction().getTag()) || faction.getTag().equals("§2Wilderness")) && !Arrays.asList(nonMinerableBlock).contains(block.getType())) {
            double xpBlock = (double) config.getObject("jobs.constructeur.xp").getOrDefault(block.getType().toString(), 0.1);
            JobsImplements jobs = new JobsImplements();
            jobs.addXp(player.getName(), JobsEnum.CONSTRUCTEUR, xpBlock);
        }
    }

    /**
     *
     * Event Chasseur
     *
     */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void hunter(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            Entity entity = event.getEntity();

            if (config.getObject("jobs.chasseur.xp").containsKey(entity.getType().toString())) {
                double xpBlock = (double) config.getObject("jobs.chasseur.xp").getOrDefault(entity.getType().toString(), 0.5);
                JobsImplements jobs = new JobsImplements();
                jobs.addXp(player.getName(), JobsEnum.CHASSEUR, xpBlock);
            }
        }
    }

    private boolean isRipe(Block block) {
        if (block.getType() == Material.CROPS) {
            Crops crops = (Crops) block.getState().getData();
            return crops.getState().equals(CropState.RIPE);
        } else if (block.getType() == Material.CARROT || block.getType() == Material.POTATO) {
            return block.getData() == 7;
        } else if(block.getType() == Material.NETHER_WARTS) {
            return block.getData() == 3;
        } else return block.getType() == Material.CACTUS || block.getType() == Material.SUGAR_CANE_BLOCK;
    }
}
