package fr.pandaguerrier.conodiajob.utils.registers;

import fr.pandaguerrier.conodiajob.ConodiaJobs;
import fr.pandaguerrier.conodiajob.commands.Jobs;

public class Commands {
  public static void load() {
    ConodiaJobs.instance.getCommand("jobs").setExecutor(new Jobs());
  }
}
