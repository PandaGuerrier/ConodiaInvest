package fr.pandaguerrier.conodiainvest.utils.registers;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.commands.Invest;

public class Commands {
  public static void load() {
    ConodiaInvest.getInstance.getCommand("invest").setExecutor(new Invest());
  }
}
