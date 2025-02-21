package com.chillguy.simplecommands.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class ChatUtils {

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");


    public static void sendMessage(String message, CommandSender player){
        message = ChatColor.translateAlternateColorCodes('&', message);

        player.sendMessage(translateHexColorCodes(message));
    }


    public static String translateHexColorCodes(String message) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1);
            String replacement = COLOR_CHAR + "x"
                    + COLOR_CHAR + hex.charAt(0) + COLOR_CHAR + hex.charAt(1)
                    + COLOR_CHAR + hex.charAt(2) + COLOR_CHAR + hex.charAt(3)
                    + COLOR_CHAR + hex.charAt(4) + COLOR_CHAR + hex.charAt(5);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
