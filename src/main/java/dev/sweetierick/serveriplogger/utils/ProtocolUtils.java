package dev.sweetierick.serveriplogger.utils;

import org.bukkit.entity.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ProtocolUtils {
    /**
     * Gets the external IP of the machine trough a valid IP checker
     * @param checkerUrl The URL of a checker API
     * @param player The player to forward the message, if any
     */
    public static String getExternalIP(String checkerUrl, Player player) {
        String IP = null;
        try {
            URL url = new URL(checkerUrl);
            // Opens a new buffer reader to check for the server ext IP address
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.openStream()
            ));
            IP = reader.readLine();
            // Sends the message to the player (if not null)
            if (player != null) {player.sendMessage("The server ip is: " + IP);}
            return IP;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
