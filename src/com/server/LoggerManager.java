package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * for logging server info
 */
public class LoggerManager {

    private static final LogManager logManager = LogManager.getLogManager();

    static {
        try {
            InputStream inputStream = ClassLoader.class.getResourceAsStream("/logger.properties");
            logManager.readConfiguration(inputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Logger GetLogger(String str) {
        return Logger.getLogger(str);
    }
}
