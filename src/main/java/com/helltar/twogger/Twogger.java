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

    public boolean connect() throws IOException {
        if (twitchIRC.connect()) {
            if (sendToTelegram) {
                sendMessage("✅ → Logged\n📢 → " + twChannel + "\n😎 → " + twUsername);
            }

            return true;
        } else {
            sendMessage("❌ → Login error");
        }

        return false;
    }

    public void run() throws IOException {
        var ircData = twitchIRC.getUpdates();

        if (ircData != null) {
            sendMessage(
                    sendToTelegram
                            ? "<b>" + ircData.username() + "</b> 💬 " + Utils.escapeHtml(ircData.message())
                            : ircData.username() + ": " + ircData.message());
        }
    }

    private void sendMessage(String text) {
        if (sendToTelegram) {
            try {
                telegram.sendMessage(text);
            } catch (IOException e) {
                Logger.add(e);
            }
        } else {
            Logger.add("twitch_" + twChannel, text);
        }
    }
}
