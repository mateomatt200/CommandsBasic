package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Main.getInstance().getConfig();

        if (!(sender instanceof Player)) {
            String noPlayer = config.getString("messages.no-console", "No console execute this.");
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }


        Player player = (Player) sender;
        Player target;

        if (args.length == 0) {
            target = player;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {

                String noPlayer = config.getString("messages.no-player", "§c{player} is not online.")
                        .replace("{player}", args[0]);
                ChatUtils.sendMessage(noPlayer, player);
                return true;
            }
        }

        player.openInventory(target.getEnderChest());

        String openInv = config.getString("messages.open-ender", "§c{player} open enderchest.")
                .replace("{player}", args[0]);
        ChatUtils.sendMessage(openInv, player);

        return true;
    }
}