package com.helltar.twogger;

import java.io.IOException;

public class Twogger {

    private final String twChannel, twUsername;
    private final boolean sendToTelegram;

    private final TwitchIRC twitchIRC;
    private final Telegram telegram;

    public Twogger(String twChannel, String twUsername, String twToken,
                   String tgChannel, String tgToken,
                   boolean sendToTelegram) {

        this.twChannel = twChannel;
        this.twUsername = twUsername;
        this.sendToTelegram = sendToTelegram;

        twitchIRC = new TwitchIRC(twChannel, twUsername, twToken);
        telegram = new Telegram(tgChannel, tgToken);
    }

    public boolean connect() {
        try {
            if (twitchIRC.connect()) {
                sendMessage(
                        "✅ : Logged\n" +
                                "📢 : " + twChannel + "\n" +
                                "😎 : " + twUsername);
                return true;
            } else {
                sendMessage("❌ : Login error");
            }
        } catch (IOException e) {
            Logger.add(e);
        }

        return false;
    }

    public void run() throws IOException {
        var ircData = twitchIRC.getUpdates();

        if (ircData != null) {
            sendMessage(ircData.username(), ircData.message());
        }
    }

    private void sendMessage(String username, String text) {
        if (sendToTelegram) {
            try {
                telegram.sendMessage((username.isEmpty() ? "" : "<b>" + username + "</b> 💬 ") + Utils.escapeHtml(text));
            } catch (IOException e) {
                Logger.add(e);
            }
        } else {
            Logger.add("twitch_" + twChannel, username, text);
        }
    }

    private void sendMessage(String text) {
        sendMessage("", text);
    }
}
