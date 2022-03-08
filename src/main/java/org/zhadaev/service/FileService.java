package org.zhadaev.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class FileService {

    public File findFile(String pathToReadableFile) throws FileNotFoundException {
        File file = new File(pathToReadableFile);
        if (!file.exists()) {
            pathToReadableFile = getAppPath().concat(pathToReadableFile);
            file = new File(pathToReadableFile);
        }
        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException();
        }
    }

    private String getAppPath() {
        File file = null;
        try {
            file = new File(org.zhadaev.Converter.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            System.out.println("An unexpected error has occurred :(");
            System.exit(1);
        }
        String path = file.getPath();
        String fileName = file.getName();
        return path.substring(0, path.length() - fileName.length());
    }

    public File getWritableFile(String pathToWritableFile) {
        File writableFile = new File(pathToWritableFile);
        if (!writableFile.isAbsolute()) {
            writableFile = new File(getAppPath().concat(pathToWritableFile));
        }
        return writableFile;
    }

}
