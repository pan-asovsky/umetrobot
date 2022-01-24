package space.panasovsky.telegram.bot.util;

import space.panasovsky.telegram.bot.Sender;


public class Timer implements Sender {

    public String start() {
        return "started";
    }

    public String pause() {
        return "paused";
    }

    public String stop() {
        return "stopped";
    }

}