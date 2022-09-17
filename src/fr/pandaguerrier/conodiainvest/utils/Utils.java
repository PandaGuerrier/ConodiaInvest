package fr.pandaguerrier.conodiainvest.utils;

import fr.pandaguerrier.conodiainvest.ConodiaInvest;
import fr.pandaguerrier.conodiainvest.events.InvestStop;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Utils {

    private static final DecimalFormat df = new DecimalFormat("#.#");

    public static boolean playerZone(Player player) {
        Location coord2 = new Location(player.getWorld(), 26, 195, 33);
        Location coord1 = new Location(player.getWorld(), 12, 84, 17);

        if ((player.getLocation().getBlockX() > coord1.getBlockX()) && (player.getLocation().getBlockX() < coord2.getBlockX())) {
            if ((player.getLocation().getBlockY() > coord1.getBlockY()) && (player.getLocation().getBlockY() < coord2.getBlockY())) {
                if ((player.getLocation().getBlockZ() > coord1.getBlockZ()) && (player.getLocation().getBlockZ() < coord2.getBlockZ())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void investProgress() {
        ConodiaInvest.getInstance.getServer().getScheduler().scheduleSyncRepeatingTask(ConodiaInvest.getInstance, new Runnable() {
            @Override
            public void run() {
                for (Player player : ConodiaInvest.getInstance.getHashInvest().keySet()) {
                    if (playerZone(player)) {
                        HashMap hash = (HashMap) ConodiaInvest.getInstance.getHashInvest().get(player);
                        int currentTime = (int) hash.get("currentTime");
                        int time = (int) hash.get("time");

                        if (currentTime < time) {
                            currentTime++;
                            hash.put("currentTime", currentTime);
                            ConodiaInvest.getInstance.getHashInvest().put(player, hash);
                        } else {
                            InvestStop event = new InvestStop(player, (int) hash.get("money"));
                            Bukkit.getPluginManager().callEvent(event);
                        }

                        Progress progress = new Progress(currentTime, time);
                        sendTitle(player, "§9" + df.format(progress.getPercentage() * 100) + "%", progress.progressBar);

                    }
                }
            }
        }, 0L, 20L);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 20, 5);


        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    public static ItemStack createGuiItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
