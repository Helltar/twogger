package com.helltar.twogger;

import java.io.IOException;

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

        Utils.sendPost(data, "https://api.telegram.org/" + token + "/sendMessage", 3);
    }
}
