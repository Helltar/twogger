package com.helltar.twogger;

import java.io.*;
import java.net.Socket;

public class TwitchIRC {

    private final String channel, username, token;

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public TwitchIRC(String channel, String username, String token) {
        this.channel = "#" + channel;
        this.username = username;
        this.token = token;
    }

    public boolean connect() throws IOException {
        if (socket != null) {
            if (socket.isConnected()) {
                socket.close();
            }
        }

        socket = new Socket("irc.chat.twitch.tv", 6667);
        Logger.add(socket.toString());

        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.write("PASS " + token + "\r\n");
        writer.write("NICK " + username + "\r\n");
        writer.flush();

        var line = reader.readLine();

        if (line != null) {
            Logger.add(line);

            if (line.startsWith(":tmi.twitch.tv 001")) {
                writer.write("JOIN " + channel + "\r\n"); // todo: check if join
                writer.flush();
                Logger.add("JOIN " + channel);
                return true;
            }
        }

        return false;
    }

    public record IRCData(String username, String message) { }

    public IRCData getUpdates() throws IOException {
        var line = reader.readLine();

        if (line.contains("PRIVMSG")) {
            var username = line.replaceAll("(.*)@(.*).tmi.twitch.tv(.*)", "$2");
            var message = line.replaceAll("(.*)PRIVMSG #(.*) :(.*)", "$3");
            return new IRCData(username, message);
        } else {
            if (line.startsWith("PING :tmi.twitch.tv")) {
                writer.write("PONG :tmi.twitch.tv\r\n");
                writer.flush();
                Logger.add("PONG tmi.twitch.tv");
            }
        }

        return null;
    }
}
