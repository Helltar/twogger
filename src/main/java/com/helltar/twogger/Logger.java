package com.helltar.twogger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.helltar.twogger.Consts.DIR_LOG;
import static com.helltar.twogger.Consts.EXT_TXT;

public class Logger {

    public static void add(String msg) {
        add("twogger", msg);
    }

    public static void add(Exception e) {
        add(e.getMessage());
    }

    public static void add(String filename, String msg) {
        if (!new File(DIR_LOG).exists()) {
            new File(DIR_LOG).mkdir();
        }

        msg = new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " + msg;

        System.out.println(msg);
        Utils.appendStringToFile(DIR_LOG + filename + "_" + LocalDate.now() + EXT_TXT, msg);
    }
}
