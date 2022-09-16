package fr.pandaguerrier.conodiajob.listeners;


import fr.pandaguerrier.conodiajob.inventories.GuiInfo;
import fr.pandaguerrier.conodiajob.utils.JobsEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
    @SuppressWarnings({ "unused" })
	@EventHandler
    public void onClick( InventoryClickEvent event) {
         Player player = (Player)event.getWhoClicked();
         ItemStack clicked = event.getCurrentItem();
         Inventory inventory = event.getInventory();

        if (inventory.getName().equals("§9➜ §bJobs")) {
            if(clicked == null || clicked.getType() == null || clicked.getType().equals(Material.STAINED_GLASS_PANE)) return;
            player.closeInventory();

            switch (clicked.getType()) {
                case DIAMOND_PICKAXE:
                    new GuiInfo(player, JobsEnum.MINEUR).open();
                    break;
                case DIAMOND_AXE:
                    new GuiInfo(player, JobsEnum.BUCHERON).open();
                    break;
                case DIAMOND_SWORD:
                    new GuiInfo(player, JobsEnum.CHASSEUR).open();
                    break;
                case DIAMOND_HOE:
                    new GuiInfo(player, JobsEnum.FARMEUR).open();
                    break;
                case BRICK:
                    new GuiInfo(player, JobsEnum.CONSTRUCTEUR).open();
                    break;
            }

        } else if(inventory.getName().contains("Mineur") || inventory.getName().contains("Bucheron") || inventory.getName().contains("Chasseur") || inventory.getName().contains("Constructeur") || inventory.getName().contains("Farmeur")) {
            if (clicked.getType() != null && clicked.getType() == Material.INK_SACK) {
                player.closeInventory();
                player.performCommand("jobs");
            }
        }
    }
}
