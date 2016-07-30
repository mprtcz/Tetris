package com.mprtcz.tetris.logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Azet on 2016-05-05.
 */
public class TetrisGameLogger {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private static Handler handler = null;

    public static void initializeLogger() {
        try {
            handler = new FileHandler("GameRunning.log", false);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not create file");
            e.printStackTrace();
        }
        Logger newLogger = Logger.getLogger("");
        handler.setFormatter(new MyFormatter());
        newLogger.addHandler(handler);
        newLogger.setLevel(Level.CONFIG);
    }
}

class MyFormatter extends Formatter {
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        builder.append("[").append(record.getLevel()).append("]");
        builder.append("[").append(getClassNameOnly(record.getSourceClassName())).append(": ");
        builder.append(record.getSourceMethodName()).append("]");
        if(!formatMessage(record).equals("")) {
            builder.append("\n");
            builder.append(formatMessage(record));
        }
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }

    private static String getClassNameOnly(String string){
        String[] parts = string.split("\\.");
        return parts[parts.length-1];
    }
}
