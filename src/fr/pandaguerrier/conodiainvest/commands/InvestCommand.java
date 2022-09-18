package fr.pandaguerrier.conodiainvest.commands;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InvestCommand implements CommandExecutor {
    public boolean onCommand( CommandSender sender,  Command cmd,  String msg,  String[] args) {
        Player player = (Player)sender;

        Inventory inv = Bukkit.createInventory(null, 45, "§9➜ §bInvest");

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
        inv.setItem(44, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));

        if(ConodiaInvest.getInstance.getHashInvest().get(player) == null) {
            inv.setItem(20, Utils.createGuiItem(Material.GOLD_NUGGET, "§e10k", 0, "§8§m-------------------", " ", "§bCoût: §aFREE", "§bTemps: §930min", " ", "§8§m-------------------"));
            inv.setItem(21, Utils.createGuiItem(Material.GOLD_NUGGET, "§e100k", 0, "§8§m-------------------", " ", "§bCoût: §950k", "§bTemps: §91h30min", " ", "§8§m-------------------"));
            inv.setItem(13, Utils.createGuiItem(Material.GOLD_INGOT, "§e300k", 0, "§8§m-------------------", " ", "§bCoût: §9200k", "§bTemps: §92h15min", " ", "§8§m-------------------"));
            inv.setItem(31, Utils.createGuiItem(Material.GOLD_INGOT, "§e500k", 0, "§8§m-------------------", " ", "§bCoût: §9350k", "§bTemps: §93h", " ", "§8§m-------------------"));
            inv.setItem(23, Utils.createGuiItem(Material.GOLD_BLOCK, "§e1M", 0, "§8§m-------------------", " ", "§bCoût: §9650k", "§bTemps: §95h", " ", "§8§m-------------------"));
            inv.setItem(24, Utils.createGuiItem(Material.EMERALD_BLOCK, "§e3M", 0, "§8§m-------------------", " ", "§bCoût: §91.6M", "§bTemps: §911h", " ", "§8§m-------------------"));
        } else {
            HashMap playerInvest = (HashMap) ConodiaInvest.getInstance.getHashInvest().get(player);
            inv.setItem(21, Utils.createGuiItem(Material.REDSTONE, "§cInvestissement en cours", 0, "§8§m-------------------", " ", "§bTemps restant: §9" + intToDate((int) playerInvest.get("time") - (int) playerInvest.get("currentTime")), " ", "§8§m-------------------"));
            inv.setItem(23, Utils.createGuiItem(Material.BARRIER, "§cCliquez ici pour annuler (Vous recevrez uniquement 75% de votre argent investie).", 0, " "));
        }

        player.openInventory(inv);

        return true;
    }

    private String intToDate(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
