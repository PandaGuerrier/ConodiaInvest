package fr.pandaguerrier.conodiajob.utils.registers;

import fr.pandaguerrier.conodiajob.ConodiaJobs;
import fr.pandaguerrier.conodiajob.listeners.*;

public class Events {
  private static final ConodiaJobs plugin =  ConodiaJobs.instance;
  public static void load() {
    plugin.getServer().getPluginManager().registerEvents(new InventoryClick(),  plugin);
    plugin.getServer().getPluginManager().registerEvents(new JobsListener(),  plugin);
  }
}
