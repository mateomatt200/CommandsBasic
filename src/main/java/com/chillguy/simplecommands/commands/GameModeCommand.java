package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (args.length < 1) {
            String usage = config.getString("messages.usage-gamemode", "Usage: /gamemode <modo> [jugador]");
            ChatUtils.sendMessage(usage, sender);
            return true;
        }

        GameMode gameMode;
        try {
            gameMode = GameMode.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            String usage = config.getString("messages.mode-game-invalid", "Usage: SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR");
            ChatUtils.sendMessage(usage, sender);
            return true;
        }

        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                String noPlayer = config.getString("messages.no-player", "§c{player} is not online.")
                        .replace("{player}", args[0]);
                ChatUtils.sendMessage(noPlayer, sender);
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                String noPlayer = config.getString("messages.no-console", "No console execute this.");
                ChatUtils.sendMessage(noPlayer, sender);
                return true;
            }
            target = (Player) sender;
        }

        target.setGameMode(gameMode);

        String ChangeMode = config.getString("messages.gamemode-change", "Your game mode has been changed to {gamemode}").replace("{gamemode}", gameMode.toString());
        ChatUtils.sendMessage(ChangeMode, sender);
        if (!target.equals(sender)) {

            String changeMode = config.getString("messages.gamemode-change-target", "&cYou have changed the game mode of {player} to {gamemode}").replace("{player}", target.getName()).replace("{gamemode}", gameMode.toString());
            ChatUtils.sendMessage(changeMode, sender);

        }
        return true;
    }
}

