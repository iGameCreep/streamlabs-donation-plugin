package fr.gamecreep.streamlabsdonations.donations.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donation20 {
    public Donation20(Player player) {
        PlayerInventory inventory = player.getInventory();

        // Create a list to hold non-null items
        List<ItemStack> nonNullItems = new ArrayList<>();
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                nonNullItems.add(item);
            }
        }

        // Check if there are non-null items
        if (nonNullItems.isEmpty()) {
            player.sendMessage("Your inventory is empty.");
            return;
        }

        // Select a random non-null item
        Random random = new Random();
        ItemStack randomItem = nonNullItems.get(random.nextInt(nonNullItems.size()));

        if (!canDeleteArmor(inventory, randomItem)) inventory.removeItem(randomItem);
        player.sendMessage("Removed: " + randomItem.getType());
    }

    private boolean canDeleteArmor(PlayerInventory inv, ItemStack item) {
        String lowerCaseItemName = item.getType().toString().toLowerCase();

        if (lowerCaseItemName.contains("helmet")) inv.setHelmet(null);
        else if (lowerCaseItemName.contains("chestplate")) inv.setChestplate(null);
        else if (lowerCaseItemName.contains("leggings")) inv.setLeggings(null);
        else if (lowerCaseItemName.contains("boots")) inv.setBoots(null);
        else return false;

        return true;
    }
}
