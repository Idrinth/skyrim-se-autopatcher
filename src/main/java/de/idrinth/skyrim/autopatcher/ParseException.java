package de.idrinth.skyrim.autopatcher;

import java.io.File;

class ParseException extends Exception {
    public ParseException(File file, String message)
    {
        super("Failed to parse " + file.getName() + ": " + message);
    }
}
