package fr.pandaguerrier.conodiainvest.utils.registers;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.commands.InvestCommand;

public class Commands {
  public static void load() {
    ConodiaInvest.getInstance.getCommand("invest").setExecutor(new InvestCommand());
  }
}
