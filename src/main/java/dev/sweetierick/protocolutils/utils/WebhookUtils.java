package dev.sweetierick.protocolutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class WebhookUtils {
    /**
     * A helper function that sends the Server IP to a Discord Webhook or any HTTP endpoint
     * @param targetUrl The URL of the HTTP endpoint
     * @param JsonData The request data in JSON format
     * @return targetUrl
     */
    public static String forwardToWebhook(String targetUrl, String JsonData) {
        HttpURLConnection connection;
        try {
            /*
             * Sets all URL params
             * @method connection.setRequestMethod("POST") => Sets the method to post
             * @method connection.setRequestProperty("Content-Type", "application/json") => Discord application format json
             * @method connection.setDoOutput(true) => Makes it so the stream is outputted rather than listened
             */
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Writes the request and forwards it to the webhook endpoint
            try(OutputStream stream = connection.getOutputStream()) {
                byte[] input = JsonData.getBytes(StandardCharsets.UTF_8);
                stream.write(input, 0, input.length);
            } catch (IOException e) {
                Logger logger = Logger.getLogger("[ProtocolUtils]");
                logger.severe("ERROR: Configuration file for ProtocolUtils is incomplete: could not reach a webhook endpoint! \nPlease fill out the missing information in the file. Use this website to check for format errors: http://www.yamllint.com");
            }

            // Gets the response from the HTTP endpoint in JSON format
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Logger logger = Logger.getLogger("[ProtocolUtils]");
                logger.info(response.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetUrl;
    }
}
