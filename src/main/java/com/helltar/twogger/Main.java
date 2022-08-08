package com.helltar.twogger;

import org.apache.commons.cli.*;

import java.util.Scanner;

import static com.helltar.twogger.Consts.*;
import static com.helltar.twogger.Utils.getStringFromFile;

public class Main {

    private static boolean sendToTelegram;

    public static void main(String[] args) {

        parseArgs(args);

        var twogger =
                new Twogger(
                        twitchChannelInput().toLowerCase(),
                        getStringFromFile(TWITCH_USERNAME_FILE),
                        getStringFromFile(TWITCH_OAUTH_FILE),
                        getStringFromFile(TG_CHANNEL_FILE),
                        getStringFromFile(TG_TOKEN_FILE),
                        sendToTelegram);

        if (twogger.connect()) {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    twogger.run();
                } catch (Exception e) {
                    Logger.add(e);
                }
            }
        }
    }

    public static void parseArgs(String[] args) {
        var tg = new Option("tg", "telegram", false, "Send messages to Telegram Channel");
        var help = new Option("h", "help", false, "Print this message");

        var options = new Options().addOption(tg).addOption(help);

        try {
            var line = new DefaultParser().parse(options, args);

            if (line.hasOption(help)) {
                printHelp(options);
            }

            if (line.hasOption(tg)) {
                sendToTelegram = true;
            }
        } catch (ParseException e) {
            Logger.add(e);
            printHelp(options);
        }
    }

    public static String twitchChannelInput() {
        System.out.println(ANSI_GREEN + "Enter the name of the Twitch channel you want to monitor:" + ANSI_RESET);

        while (true) {
            var line = new Scanner(System.in).nextLine();

            if (line.length() < 2 || line.length() > 25) {
                System.out.println(ANSI_RED + "Name must be between 2 and 25 characters, try again:" + ANSI_RESET);
            } else {
                return line;
            }
        }
    }

    public static void printHelp(Options options) {
        new HelpFormatter().printHelp(PROJECT_NAME, options);
        System.exit(0);
    }
}