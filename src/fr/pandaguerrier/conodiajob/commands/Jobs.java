package fr.pandaguerrier.conodiajob.commands;

import fr.pandaguerrier.conodiajob.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Jobs implements CommandExecutor {
    public boolean onCommand( CommandSender sender,  Command cmd,  String msg,  String[] args) {
        Player player = (Player)sender;
        Inventory inv = Bukkit.createInventory(null, 45, "§9➜ §bJobs");

        inv.setItem(0, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(1, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(7, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(8, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(9, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(17, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(27, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(35, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(36, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(37, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(44, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));
        inv.setItem(43, Utils.createGuiItem(Material.STAINED_GLASS_PANE, "", 3, ""));

        inv.setItem(20, Utils.createGuiItem(Material.DIAMOND_PICKAXE, "§9➜ §bMineur", 0,"§8§m---------------", " ", "§bCliquez pour accéder au menu du métier de mineur !", "§8§m---------------"));
        inv.setItem(21, Utils.createGuiItem(Material.DIAMOND_AXE, "§9➜ §bBucheron", 0,"§8§m---------------", " ", "§bCliquez pour accéder au menu du métier de bucheron !", "§8§m---------------"));
        inv.setItem(22, Utils.createGuiItem(Material.BRICK, "§9➜ §bConstructeur", 0,"§8§m---------------", " ", "§bCliquez pour accéder au menu du métier de constructeur !", "§8§m---------------"));
        inv.setItem(23, Utils.createGuiItem(Material.DIAMOND_SWORD, "§9➜ §bChasseur", 0,"§8§m---------------", " ", "§bCliquez pour accéder au menu du métier de chasseur !", "§8§m---------------"));
        inv.setItem(24, Utils.createGuiItem(Material.DIAMOND_HOE, "§9➜ §bFarmeur", 0,"§8§m---------------", " ", "§bCliquez pour accéder au menu du métier de farmeur !", "§8§m---------------"));
 // instancier une fois
        player.openInventory(inv);
        return true;
    }
}
