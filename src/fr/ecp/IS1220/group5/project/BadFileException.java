package fr.ecp.IS1220.group5.project;

import java.io.IOException;

/**
 * Created by dennis101251 on 2016/11/16.
 */
public class BadFileException extends IOException {
    public BadFileException(String e) {
        super(e);
    }
}