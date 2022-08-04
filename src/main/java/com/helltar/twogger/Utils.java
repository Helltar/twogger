package com.helltar.twogger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String getStringFromFile(String filename) {
        var line = "";

        try (var reader = new BufferedReader(new FileReader(filename))) {
            line = reader.readLine();
        } catch (IOException e) {
            Logger.add(e);
        }

        return line;
    }

    public static void appendStringToFile(String filename, String str) {
        try (var writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.append(str).append("\n");
        } catch (IOException e) {
            Logger.add(e);
        }
    }

    public static void sendPost(String[] data, String url, int sleepSec) {
        try {
            Jsoup.connect(url)
                    .data(data)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(0)
                    .execute();

            if (sleepSec > 0) {
                TimeUnit.SECONDS.sleep(sleepSec);
            }
        } catch (IOException | InterruptedException e) {
            Logger.add(e);
        }
    }

    public static String escapeHtml(String text) {
        return text
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#039;");
    }
}
