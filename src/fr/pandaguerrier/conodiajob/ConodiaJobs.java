package fr.pandaguerrier.conodiajob;

import fr.pandaguerrier.conodiajob.database.Database;
import fr.pandaguerrier.conodiajob.utils.Utils;
import fr.pandaguerrier.conodiajob.utils.registers.Commands;
import fr.pandaguerrier.conodiajob.utils.registers.Events;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class ConodiaJobs extends JavaPlugin {
    public static ConodiaJobs instance;
    private Config config;
    private Config loreConfig;
    private Database database;

    private final HashMap<String, Object> hashJobs = new HashMap<>();

    public void onEnable() {
        instance = this;
        config = new Config(new File(getDataFolder().getAbsolutePath() + "/config.json"));
        loreConfig = new Config(new File(getDataFolder().getAbsolutePath() + "/lore.json"));

        database = new Database();
        database.connect();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Utils.pushDatabase();
            }
        }, 0L, 20 * 600);

        Utils.createNewDatabase();
        Commands.load();
        Events.load();
        System.out.println("\n \n-------------------------\n \nLe ConodiaJobs est connecté !\n \n-------------------------\n \n");
    }

    public void onDisable() {
        Utils.pushDatabase();
        System.out.println("\n \n-------------------------\n \nLe ConodiaJobs est déconnecté !\n \n-------------------------\n \n");
    }

    public Config getJobsConfig() {
        return config;
    }

    public Config getLoreConfig() {
        return loreConfig;
    }

    public Database getJobsDatabase() {
        return database;
    }

    public HashMap<String, Object> getHashJobs() {
        return hashJobs;
    }
}