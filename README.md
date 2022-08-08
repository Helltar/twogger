twogger
-------

- [twogger-0.1.zip](https://github.com/Helltar/twogger/releases/download/v0.1/twogger-0.1.zip) 447 KB

![twogger-1.0-SNAPSHOT](https://helltar.com/projects/twogger/img/twogger-1.0-SNAPSHOT.gif)

- get Twitch token: [Twitch Chat OAuth Password Generator](https://twitchapps.com/tmi/)
- save token in: **twitch_token.txt**
- write your Twitch username in: **twitch_username.txt**

Done.

```
java -jar twogger-x.x.x.jar
```

To send messages to Telegram:

- create bot and get token: [BotFather](https://t.me/BotFather)
- save token in: **telegram_token.txt**
- add bot to your channel (bot must have permission to post messages)
- write name of your telegram channel in: **telegram_channel.txt**
- run twogger with **-tg** or **--telegram** argument

```
java -jar twogger-x.x.x.jar -tg
```
