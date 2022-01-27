package dev.sweetierick.serveriplogger;

import dev.sweetierick.serveriplogger.utils.WebhookUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandHook;
import redempt.redlib.commandmanager.CommandParser;

import java.util.logging.Logger;

public final class ProtocolUtils extends JavaPlugin implements Listener {
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        // ? Configuration of config.yml
        config.addDefault("forward-server-ip", true);
        config.addDefault("webhookUrl", "");
        config.addDefault("JsonQuery", "");
        config.addDefault("checkerUrl", "http://checkip.amazonaws.com");
        config.addDefault("version", 1.1);
        config.options().copyDefaults(true);
        saveConfig();


        // Initialise logger
        Logger logger = Logger.getLogger("[ProtocolUtils]");
         // ? Enables the CommandParser for plugin commands
        new CommandParser(this.getResource("checkip.rdcml")).parse().register("ip", this);

        logger.info("ProtocolUtils lib has been enabled successfully!");
        logger.info("Forwarding IP info to Webhook...");

        String webhookUrl = config.getString("webhookUrl");
        String JsonEmbed = config.getString("JsonQuery");
        String checkerUrl = config.getString("checkerUrl");

        if (JsonEmbed != null | webhookUrl != null && config.getBoolean("forward-server-ip")) {
            WebhookUtils.forwardToWebhook(webhookUrl, JsonEmbed);
        } else {
            throw new RuntimeException("ERROR: Configuration file for ProtocolUtils is incomplete! Please fill out the missing information in the file. If it is badly formatted, please use this website to check for format errors: http://www.yamllint.com");
        }

        // Enables server side Event registration
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Logger logger = Logger.getLogger("[ProtocolUtils]");
        logger.info("Shutting down ProtocolUtils, goodbye!");
    }

    // ! Please don't read too much into this
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Just a small thing for fun
        event.setMessage(event.getMessage().replaceAll("sus", "ඞ"));
    }

    @CommandHook("checkip")
    public void checkIpCommand(CommandSender sender, Player player) {
        String targetUrl = config.getString("checkerUrl");
        // Gets the external IP and sends it to the player
        dev.sweetierick.serveriplogger.utils.ProtocolUtils.getExternalIP(targetUrl, player);
    }
}
