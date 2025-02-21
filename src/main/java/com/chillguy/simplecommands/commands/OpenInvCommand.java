package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class OpenInvCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (!(sender instanceof Player)) {
            String noPlayer = config.getString("messages.no-console", "No console execute this.");
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }

        if (args.length != 1) {
            String usage = config.getString("messages.usage-openinv", "Usage: /openinv <player>.");
            ChatUtils.sendMessage(usage, sender);
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            String noPlayer = config.getString("messages.no-player", "§c{player} is not online.")
                    .replace("{player}", args[0]);
            ChatUtils.sendMessage(noPlayer, player);
            return true;
        }

        player.openInventory(target.getInventory());
        String openInv = config.getString("messages.open-inv", "§c{player} open inventory.")
                .replace("{player}", args[0]);
        ChatUtils.sendMessage(openInv, player);

        return true;
    }
}
