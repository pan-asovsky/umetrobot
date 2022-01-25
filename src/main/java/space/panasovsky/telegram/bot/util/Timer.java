package space.panasovsky.telegram.bot.util;

import space.panasovsky.telegram.bot.Sender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Timer implements Sender {

    private final DateFormat dateFormat =  new SimpleDateFormat("HH:mm.ss");
    private static final long[] timeCasts = new long[1];
    private final long correction = 10_800_000;

    /**
     По какой-то причине при выводе добавляется плюс три часа к результату.<br>
     Поэтому до выяснения причины добавлена переменная {@link #correction}.
     */

    public String timeHandler(String method) {

        final List<String> timeCasts = new ArrayList<>();
        String handlerAnswer = "";
//        switch (method) {
//            case "start" -> {
//                handlerAnswer = start();
//                timeCasts.add(handlerAnswer);
//            }
//            case "pause" -> {
//                handlerAnswer = pause();
//                timeCasts.add(handlerAnswer);
//            }
//            case "stop" -> {
//                handlerAnswer = stop();
//                timeCasts.clear();
//            }
//        }
        if (!timeCasts.isEmpty()) {
            if (method.equals("start")) {
                handlerAnswer = start();
                timeCasts.add(handlerAnswer);
            } else if (method.equals("pause")) {
                handlerAnswer = pause();
                timeCasts.add(handlerAnswer);
            } else if (method.equals("stop")) {
                handlerAnswer = stop();
                timeCasts.clear();
            }
        } else {
            handlerAnswer = "Starting new timer";
            if (method.equals("start")) {
                handlerAnswer = start();
                timeCasts.add(handlerAnswer);
            } else if (method.equals("pause")) {
                handlerAnswer = pause();
                timeCasts.add(handlerAnswer);
            } else if (method.equals("stop")) {
                handlerAnswer = stop();
                timeCasts.clear();
            }
        }

        return handlerAnswer;
    }

    private String start() {

        final long startTime = System.currentTimeMillis();
        timeCasts[0] = startTime;
        return "Started at " + dateFormat.format(startTime);
    }

    private String pause() {

        final long currentTime = System.currentTimeMillis();
        final long tempTime = currentTime - timeCasts[0] - correction;
        return "Paused at " + dateFormat.format(currentTime) + ". " +
                dateFormat.format(tempTime) + " have passed.";
    }

    private String stop() {

        final long currentStopTime = System.currentTimeMillis();
        final long stopTime = currentStopTime - timeCasts[0] - correction;
        return "Stopped. Total operation time " + dateFormat.format(stopTime);
    }

}