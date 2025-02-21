package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class GodCommand implements CommandExecutor {

    private final Set<Player> godModePlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (args.length > 1) {
            sender.sendMessage("Usage: /god [jugador]");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                String noPlayer = config.getString("messages.no-console", "No console execute this.");
                ChatUtils.sendMessage(noPlayer, sender);
                return true;
            }
            Player player = (Player) sender;
            toggleGodMode(player);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String noPlayer = config.getString("messages.no-player", "§c{player} is not online.")
                    .replace("{player}", args[0]);
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }

        toggleGodMode(target);
        sender.sendMessage("god mode " + (godModePlayers.contains(target) ? "activate" : "desactive") + " for " + target.getName());
        return true;
    }

    private void toggleGodMode(Player player) {
        if (godModePlayers.contains(player)) {
            godModePlayers.remove(player);
            player.setInvulnerable(false);
            player.sendMessage("God Mode On.");
        } else {
            godModePlayers.add(player);
            player.setInvulnerable(true);
            player.sendMessage("God Mode Off.");
        }
    }
}
