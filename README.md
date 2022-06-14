# ProtocolUtils
A little utility plugin to send HTTPS requests as JSON, pretty much a Discord to Minecraft automation library.
It's main purpose is to aid in sending messages to a Discord Webhook when an avent happens on the server; however, this is way more expandable
and usable for any kind of automation that you might want for your personal dashboard or agent server, and someone used this for [Huginn](https://github.com/huginn/huginn) at some point

## Installation for Development
ProtocolUtils is a library, but also acts as a standalone plugin to use in order to check for the server IP or debugging port forward operations.
To use it as a library, just add it as a dependency in your `plugin.yml` file:
```yaml
depend: [ProtocolUtils]
```
To get the plugin Jar, download it from (idk yet), or build it yourself™️

### New build instructions coming soon!

## How to use the plugin
There are 2 main functions:
- `forwardToWebhook`, which parses a JSON object and sends a POST request to an endpoint
- `getExternalIP`, gets the external IP of your machine or your internet endpoint (the router, most of the times)

### Examples
Let's send a JSON Discord embed when a player says hi!
```java
import dev.sweetierick.serveriplogger.utils.WebhookUtils;
...
String MY_JSON_DATA = "{content:{'A player said hi!'}}"
String MY_WEBHOOK_URL = "https://discord.com/webhooks/blAbLaBl0:t0k3n:UrL"

@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().contains("hi")) {
            WebhookUtils.forwardToWebhook(MY_WEBHOOK_URL, MY_JSON_DATA);
        }
    }
```
