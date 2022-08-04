package com.helltar.twogger;

import java.util.Scanner;

import static com.helltar.twogger.Consts.*;
import static com.helltar.twogger.Utils.getStringFromFile;

public class Main {

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        String twChannel;

        System.out.println(ANSI_GREEN + "Enter the name of the Twitch channel you want to monitor:" + ANSI_RESET);

        while (true) {
            twChannel = scanner.nextLine();

            if (twChannel.length() < 4 || twChannel.length() > 25) {
                System.out.println(ANSI_RED + "Name must be between 4 and 25 characters, try again:" + ANSI_RESET);
            } else {
                break;
            }
        }

        var twogger = new Twogger(
                twChannel.toLowerCase(),
                getStringFromFile(TWITCH_USERNAME_FILE),
                getStringFromFile(TWITCH_OAUTH_FILE),
                getStringFromFile(TG_CHANNEL_FILE),
                getStringFromFile(TG_TOKEN_FILE),
                args.length > 0 && args[0].equals("--telegram"));

        try {
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
        } catch (Exception e) {
            Logger.add(e);
        }
    }
}