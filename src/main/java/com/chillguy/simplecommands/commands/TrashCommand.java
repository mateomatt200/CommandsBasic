package com.chillguy.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player player) {
            Inventory inventory = Bukkit.createInventory(null, 54, "Trash");
            player.openInventory(inventory);
        }

        return false;
    }
}
