package org.example.observer;

import java.util.logging.Logger;

public class LoggingLibraryObserver implements LibraryObserver {

    private static final Logger LOGGER =
            Logger.getLogger(LoggingLibraryObserver.class.getName());

    @Override
    public void onLibraryEvent(String message) {
        LOGGER.info("Library notification: " + message);
    }
}