package fr.pandaguerrier.conodiajob.jobs;

import fr.pandaguerrier.conodiajob.Config;
import fr.pandaguerrier.conodiajob.utils.JobsEnum;
import fr.pandaguerrier.conodiajob.ConodiaJobs;
import fr.pandaguerrier.conodiajob.database.Database;
import fr.pandaguerrier.conodiajob.utils.Utils;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class JobsImplements implements Jobs {
    private final Database database = new Database();
    private final Config config = ConodiaJobs.instance.getJobsConfig();

    @Override
    public void addXp(String player, JobsEnum job, double xp) {
        Bukkit.getScheduler().runTask(ConodiaJobs.instance, () -> {
            if (database.isConnected()) {
                if (!isExist(player)) {
                    createPlayer(player);
                } else {
                    if (ConodiaJobs.instance.getHashJobs().get(player) == null) {
                        JSONObject obj = new JSONObject();

                        obj.put("lvlMineur", getLevel(player, JobsEnum.MINEUR));
                        obj.put("xpMineur", getXp(player,  JobsEnum.MINEUR));
                        obj.put("lvlBucheron", getLevel(player, JobsEnum.BUCHERON));
                        obj.put("xpBucheron", getXp(player, JobsEnum.BUCHERON));
                        obj.put("lvlConstructeur", getLevel(player, JobsEnum.CONSTRUCTEUR));
                        obj.put("xpConstructeur", getXp(player, JobsEnum.CONSTRUCTEUR));
                        obj.put("lvlChasseur", getLevel(player, JobsEnum.CHASSEUR));
                        obj.put("xpChasseur", getXp(player, JobsEnum.CHASSEUR));
                        obj.put("lvlFarmeur", getLevel(player, JobsEnum.FARMEUR));
                        obj.put("xpFarmeur", getXp(player, JobsEnum.FARMEUR));

                        ConodiaJobs.instance.getHashJobs().put(player, obj);
                    } else {
                        HashMap obj = (HashMap) ConodiaJobs.instance.getHashJobs().get(player);

                        if ((double) obj.get("xp" + job.getJobName()) + xp >= ((int) obj.get("lvl" + job.getJobName()) + 1) * 2500) {
                            if (!((int) obj.get("lvl" + job.getJobName()) >= 10)) {
                                addLevel(player, job, (int) obj.get("lvl" + job.getJobName()) + 1);
                            }
                        } else {
                            obj.put("xp" + job.getJobName(), (double) obj.get("xp" + job.getJobName()) + xp);

                            PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText("§bAvancement §9" + job.getJobName() + "§b: " + Utils.progress(Bukkit.getPlayer(player), job.getJobName(), (double) obj.get("xp" + job.getJobName()))), (byte) 2);
                            ((CraftPlayer) Bukkit.getPlayer(player)).getHandle().playerConnection.sendPacket(packet);
                        }
                    }
                }
            } else {
                System.out.println("[Jobs] Error: Database pas connectée !");
            }
        });
    }

    @Override
    public void addLevel(String player, JobsEnum job, int level) {
        Bukkit.getScheduler().runTaskAsynchronously(ConodiaJobs.instance, () -> {
            JSONArray recJobs = (JSONArray) config.getObject("jobs." + job.getJobName().toLowerCase() + ".recompenses").get(String.valueOf(level));
            HashMap obj = (HashMap) ConodiaJobs.instance.getHashJobs().get(player);

            System.out.println("Jobs evolué en level: " + level);

            for (Object o : recJobs) {
                Bukkit.getScheduler().runTask(ConodiaJobs.instance, () -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), o.toString().replace("%player%", player).replace("&", "§")));
            }

            obj.put("lvl" + job.getJobName(), (int) obj.get("lvl" + job.getJobName()) + 1);
            obj.put("xp" + job.getJobName(), 0.0);
            Bukkit.getServer().getPlayer(player).sendMessage("§9➜ §bVous avez reçu une récompense pour avoir atteint le niveau §9" + level + "§b du métier : §9" + job.getJobName());
        });
    }

    @Override
    public double getXp(String player, JobsEnum job) {
        double xp;
        try {
            PreparedStatement ps = ConodiaJobs.instance.getJobsDatabase().getConnection().prepareStatement("SELECT xp" + job.getJobName() + " FROM jobs WHERE pseudo = ?");
            ps.setString(1, player);
            xp = ps.executeQuery().getDouble("xp" + job.getJobName());
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return xp;
    }

    @Override
    public int getLevel(String player, JobsEnum job) {
        int level;
        try {
            PreparedStatement ps = ConodiaJobs.instance.getJobsDatabase().getConnection().prepareStatement("SELECT lvl" + job.getJobName() + " FROM jobs WHERE pseudo = ?");
            ps.setString(1, player);
            level = ps.executeQuery().getInt("lvl" + job.getJobName());
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return level;
    }

    @Override
    public boolean isExist(String player) {
        try {
            PreparedStatement ps = ConodiaJobs.instance.getJobsDatabase().getConnection().prepareStatement("SELECT * FROM jobs WHERE pseudo = ?");
            ps.setString(1, player);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createPlayer(String player) {
        try {
            PreparedStatement ps = ConodiaJobs.instance.getJobsDatabase().getConnection().prepareStatement("INSERT INTO jobs (pseudo, lvlMineur, lvlChasseur, lvlBucheron, lvlFarmeur, lvlConstructeur) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, player);
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
