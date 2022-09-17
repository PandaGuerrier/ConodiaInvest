package fr.pandaguerrier.conodiainvest.utils.registers;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.listeners.*;

public class Events {
  private static final ConodiaInvest plugin =  ConodiaInvest.getInstance;
  public static void load() {
    plugin.getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new PlayerMove(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new Player(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new InvestStopListener(), plugin);
  }
}
