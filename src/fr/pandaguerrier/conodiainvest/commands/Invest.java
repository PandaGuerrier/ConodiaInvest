package fr.pandaguerrier.conodiainvest.commands;

import fr.pandaguerrier.conodiainvest.inventories.GuiInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invest implements CommandExecutor {
    public boolean onCommand( CommandSender sender,  Command cmd,  String msg,  String[] args) {
        Player player = (Player)sender;

        new GuiInfo(player).open();

        return true;
    }
}
