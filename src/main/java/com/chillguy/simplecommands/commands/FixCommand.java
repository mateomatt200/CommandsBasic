package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (!(sender instanceof Player)) {
            String noPlayer = config.getString("messages.no-console", "No console execute this.");
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            String noItemHand = config.getString("messages.no-item-hand", "§cNo item in hand.");
            ChatUtils.sendMessage(noItemHand, sender);
            return true;
        }

        if (item.getItemMeta() instanceof Damageable) {
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(0);
            item.setItemMeta(meta);
            String itemRepair = config.getString("messages.item-repair", "§cItem Repair");
            ChatUtils.sendMessage(itemRepair, sender);
        } else {
            String itemNoRepair = config.getString("messages.item-no-repair", "§cThis item no repair");
            ChatUtils.sendMessage(itemNoRepair, sender);
        }

        return true;
    }
}
