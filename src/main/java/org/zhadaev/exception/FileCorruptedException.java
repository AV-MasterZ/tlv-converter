package org.zhadaev.exception;

public class FileCorruptedException extends Exception {

    public FileCorruptedException() {
        super("Source file is corrupted or empty");
    }

}
