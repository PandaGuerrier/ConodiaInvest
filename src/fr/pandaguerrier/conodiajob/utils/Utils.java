package fr.pandaguerrier.conodiajob.utils;

import fr.pandaguerrier.conodiajob.ConodiaJobs;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

/**
 ┎----------------------*------------------------┒
 ╎                                               ╎
 ╎                                               ╎
 ╎                 UTILS Class                   ╎
 ╎                                               ╎
 ╎                                               ╎
 ┖----------------------*------------------------┚
 */

public class Utils {
    public static String progress(Player player, String job, double current) {
        HashMap obj = (HashMap) ConodiaJobs.instance.getHashJobs().get(player.getName());

        DecimalFormat df = new DecimalFormat("#.#");
        int max = ((int) obj.get("lvl" + job) + 1) * 2500;

        double percentage =  current / max;
        int progress = (int) Math.round((20 * percentage));
        int emptyProgress = 20 - progress;
        // Idée des signes a utiliser au cas où ça me plaît pas :D --> ┃┃┃┃┃┃┃┃▊▊ ▋ ▍▍ ▎▎ ▉▉ ▏▏ ●●
        String progressText = StringUtils.repeat("▍", progress);
        String emptyProgressText = StringUtils.repeat("▍", emptyProgress);

        return "§b" + progressText + "§8" + emptyProgressText + " §9(" + df.format(percentage * 100) + "%)";
    }

    public static ItemStack createGuiItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (short)data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static void createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + ConodiaJobs.instance.getDataFolder().getAbsolutePath() + "/database.db")) {
            if (conn != null) {
                Statement db = conn.createStatement();

                db.execute("CREATE TABLE IF NOT EXISTS jobs (pseudo VARCHAR(255), lvlMineur INTEGER, xpMineur REAL, lvlBucheron INTEGER, xpBucheron REAL, lvlConstructeur INTEGER, xpConstructeur, lvlChasseur INTEGER, xpChasseur REAL, lvlFarmeur INTEGER, xpFarmeur REAL);");

                db.close();
            }
        } catch (SQLException e) {
            new File(ConodiaJobs.instance.getDataFolder().getAbsolutePath() + "/database.db");
            System.out.println(e.getMessage());
        }
    }

    public static void pushDatabase() {
        for (String key :  ConodiaJobs.instance.getHashJobs().keySet()) {
            Connection conn = ConodiaJobs.instance.getJobsDatabase().getConnection();
            HashMap obj = (HashMap) ConodiaJobs.instance.getHashJobs().get(key);
            String sql = "UPDATE jobs SET " +
                    "lvlMineur = " + obj.get("lvlMineur") + ", " +
                    "xpMineur = " + obj.get("xpMineur") + ", " +
                    "lvlBucheron = " + obj.get("lvlBucheron") + ", " +
                    "xpBucheron = " + obj.get("xpBucheron") + ", " +
                    "lvlConstructeur = " + obj.get("lvlConstructeur") + ", " +
                    "xpConstructeur = " + obj.get("xpConstructeur") + ", " +
                    "lvlChasseur = " + obj.get("lvlChasseur") + ", " +
                    "xpChasseur = " + obj.get("xpChasseur") + ", " +
                    "lvlFarmeur = " + obj.get("lvlFarmeur") + ", " +
                    "xpFarmeur = " + obj.get("xpFarmeur") + " " +
                    "WHERE pseudo = '" + key + "'";
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(key + " " + ConodiaJobs.instance.getHashJobs().get(key));
        }
        System.out.println("[ConodiaJobs] - Enregistrement des données sur la bdd faites !");

    }
}
