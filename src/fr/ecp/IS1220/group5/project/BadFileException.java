package fr.ecp.IS1220.group5.project;

import java.io.IOException;

/**
 * <b>The class that is thrown when a bad file exception appears.</b>
 */
public class BadFileException extends IOException {
    public BadFileException(String e) {
        super(e);
    }
}