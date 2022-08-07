package com.helltar.twogger;

public class Consts {

    private static final String DIR_CONFIG = "config/";

    public static final String
            DIR_LOG = "log/",
            EXT_TXT = ".txt",

            TG_TOKEN_FILE = DIR_CONFIG + "telegram_token" + EXT_TXT,
            TG_CHANNEL_FILE = DIR_CONFIG + "telegram_channel" + EXT_TXT,
            TWITCH_OAUTH_FILE = DIR_CONFIG + "twitch_token" + EXT_TXT,
            TWITCH_USERNAME_FILE = DIR_CONFIG + "twitch_username" + EXT_TXT,

            ANSI_RESET = "\u001B[0m",
            ANSI_RED = "\u001B[31m",
            ANSI_GREEN = "\u001B[32m",
            ANSI_GREEN_BOLD = "\033[1;32m";
}
