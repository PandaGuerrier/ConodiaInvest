package fr.pandaguerrier.conodiajob.inventories;

import fr.pandaguerrier.conodiajob.ConodiaJobs;
import fr.pandaguerrier.conodiajob.utils.JobsEnum;
import fr.pandaguerrier.conodiajob.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiInfo {
    private final Player player;
    private final JobsEnum job;
    public GuiInfo(Player player, JobsEnum job) {
        this.player = player;
        this.job = job;
    }

    public void open() {
        
        Inventory inv = Bukkit.createInventory(null, 45, "§9➜ §b" + this.job.getJobName());

        inv.setItem(4, Utils.createGuiItem(this.job.getJobMaterial(), "§9➜ §b" + this.job.getJobName(), 0, ""));

        inv.setItem(0, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(1, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(7, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(8, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(9, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(17, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(27, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(36, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(37, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(35, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(43, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(44, Utils.createGuiItem(Material.INK_SACK, "§cRetour", 1, ""));
        
        if(ConodiaJobs.instance.getHashJobs().get(this.player.getName()) == null) {
            inv.setItem(22, Utils.createGuiItem(Material.REDSTONE_BLOCK, "§cERREUR", 0, "§cOh non !", " ", "§cVa casser 2 / 3 blocks et reviens ici !"));
        } else {
            HashMap obj = (HashMap) ConodiaJobs.instance.getHashJobs().get(this.player.getName());

            String lore = (String) ConodiaJobs.instance.getLoreConfig().getArray("jobs." + this.job.getJobName().toLowerCase() + ".lore").get((int) obj.get("lvl" + this.job.getJobName()));

            inv.setItem(21, Utils.createGuiItem(Material.PAPER, "§bInformation sur le level §9actuel", 0, "§8§m---------------", " ", "§9➜ §bLevel: §9" + obj.get("lvl" + this.job.getJobName()), "§9➜ §bXp: " + obj.get("xp" + this.job.getJobName()), "§8§m---------------"));
            inv.setItem(22, Utils.createGuiItem(Material.ARROW, "§bLevel suivant", 0, "§8§m---------------", " ", "§9➜ §bXp requise: §9" + ((int) obj.get("lvl" + this.job.getJobName()) + 1) * 2500, "§9➜ §bProgression:                                             ", "  ", Utils.progress(player, this.job.getJobName(), (double) obj.get("xp" + this.job.getJobName())), " ", "§8§m---------------"));
            inv.setItem(23, Utils.createGuiItem(Material.GOLD_ORE, "§bRécompenses suivantes", 0, "§8§m---------------", lore.replace("!!", "\n§9➜ §b").replace("&", "§").replace("/!", "\n") ,"§8§m---------------"));
        }
        
        this.player.openInventory(inv);
    }
}