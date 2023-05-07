package com.helltar.twogger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.helltar.twogger.Consts.*;

public class Logger {

    public static void add(String msg) {
        add(PROJECT_NAME, "", msg);
    }

    public static void add(Exception e) {
        add(e.getMessage());
    }

    public static void add(String filename, String username, String msg) {
        var logDir = new File(DIR_LOG);

        if (!logDir.exists())
            if (!logDir.mkdir())
                return;

        var time = new SimpleDateFormat("HH:mm:ss ").format(new Date());

        System.out.println(time + ANSI_GREEN_BOLD + username + ANSI_RESET + ": " + msg);
        Utils.appendStringToFile(DIR_LOG + filename + "_" + LocalDate.now() + EXT_TXT, time + username + ": " + msg);
    }
}
