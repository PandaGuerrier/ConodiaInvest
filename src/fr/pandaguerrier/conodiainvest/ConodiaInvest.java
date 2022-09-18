package fr.pandaguerrier.conodiainvest;

import fr.pandaguerrier.conodiainvest.utils.SaveData;
import fr.pandaguerrier.conodiainvest.utils.Utils;
import fr.pandaguerrier.conodiainvest.utils.registers.Commands;
import fr.pandaguerrier.conodiainvest.utils.registers.Events;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConodiaInvest extends JavaPlugin {
    public static ConodiaInvest getInstance;
    private Economy econ;
    private Permission perms;
    private Chat chat;
    // Player: joueur, String: time
    private final HashMap<Player, Object> hashInvest = new HashMap<>();

    private File customConfigFile;
    private FileConfiguration customConfig;

    public void onEnable() {
        getInstance = this;

        Commands.load();
        Events.load();

        Utils.investProgress();

        SaveData.save();
        createCustomConfig();
        // VAULT SETUP

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.setupChat();

        System.out.println("\n \n-------------------------\n \nLe ConodiaInvest est connecté !\n \n-------------------------\n \n");
    }

    public void onDisable() {
        System.out.println("\n \n-------------------------\n \nLe ConodiaInvest est déconnecté !\n \n-------------------------\n \n");
    }

    public HashMap<Player, Object> getHashInvest() {
        return hashInvest;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void saveCustomConfig() {
        try {
            this.customConfig.save(this.customConfigFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "data.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
           // new File(getDataFolder().getAbsolutePath() +  "/data.yml");

            saveResource("data.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Economy getEconomy() {
        return econ;
    }

    public Permission getPermissions() {
        return perms;
    }

    public Chat getChat() {
        return chat;
    }

}