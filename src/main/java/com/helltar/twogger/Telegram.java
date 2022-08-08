package com.helltar.twogger;

import java.io.IOException;

import static com.helltar.twogger.Consts.TG_CHANNEL_FILE;

public class Telegram {

    private final String chatId, token;

    public Telegram(String chatId, String token) {
        this.chatId = chatId.startsWith("@") ? chatId : "@" + chatId;
        this.token = token.startsWith("bot") ? token : "bot" + token;
    }

    public void sendMessage(String text) throws IOException {
        String[] data = {
                "chat_id", chatId,
                "parse_mode", "HTML",
                "text", text
        };

        switch (Utils.sendPost(data, "https://api.telegram.org/" + token + "/sendMessage", 3)) {
            case 401, 421 -> Logger.add("Telegram: The token is invalid");
            case 400 -> Logger.add("Telegram: Bad Request, maybe invalid channel name -> " + TG_CHANNEL_FILE);
        }
    }
}