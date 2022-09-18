package fr.pandaguerrier.conodiainvest.utils.registers;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.listeners.*;

public class Events {
  private static final ConodiaInvest plugin =  ConodiaInvest.getInstance;
  public static void load() {
    plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new InvestStopListener(), plugin);
  }
}
