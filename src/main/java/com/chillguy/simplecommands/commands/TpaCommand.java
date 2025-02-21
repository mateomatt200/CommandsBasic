package com.chillguy.simplecommands.commands;

import com.chillguy.simplecommands.Main;
import com.chillguy.simplecommands.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {

    private final HashMap<UUID, UUID> teleportRequests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (!(sender instanceof Player)) {
            String noPlayer = config.getString("messages.no-console", "No console execute this.");
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }

        if (args.length != 1) {
            String usage = config.getString("messages.usage-tpa", "Usage: /tpa <player>");
            ChatUtils.sendMessage(usage, sender);
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            String noPlayer = config.getString("messages.no-player", "§c{player} is not online.")
                    .replace("{player}", args[0]);
            ChatUtils.sendMessage(noPlayer, sender);
            return true;
        }

        if (player.equals(target)) {
            String yourself = config.getString("messages.tpa-yourself", "Usage: /tpa <player>");
            ChatUtils.sendMessage(yourself, sender);
            return true;
        }

        teleportRequests.put(target.getUniqueId(), player.getUniqueId());


        String tpaSent = config.getString("messages.tpa-sent", "&cYou have sent a teleport request to {target}")
                .replace("{target}", target.getName());
        ChatUtils.sendMessage(tpaSent, sender);

        String tpaReceived = config.getString("messages.tpa-received", "&d{player} want to teleport to you. Use /tpaccept to accept or /tpdeny to reject")
                .replace("{player}", player.getName());
        ChatUtils.sendMessage(tpaReceived, target);

        // Expira la solicitud después de 60 segundos
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (teleportRequests.containsKey(target.getUniqueId()) && teleportRequests.get(target.getUniqueId()).equals(player.getUniqueId())) {
                teleportRequests.remove(target.getUniqueId());

                String tpaExpired = config.getString("messages.tpa-expired", "&dYour request for teleportation to {target} expired")
                        .replace("{target}", target.getName());
                ChatUtils.sendMessage(tpaExpired, player);

                String tpaExpiredTarget = config.getString("messages.tpa-expired-target", "The teleportation request of {target} expired")
                                .replace("{target}", player.getName());

                ChatUtils.sendMessage(tpaExpiredTarget, target);


            }
        }, 1200L);

        return true;
    }

    public HashMap<UUID, UUID> getTeleportRequests() {
        return teleportRequests;
    }
}