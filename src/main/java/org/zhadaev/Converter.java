package org.zhadaev;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zhadaev.exception.FileCorruptedException;
import org.zhadaev.model.Order;
import org.zhadaev.service.FileService;
import org.zhadaev.service.ReadService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Converter {

    private static ReadService readService = new ReadService();
    private static FileService fileService = new FileService();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not all arguments entered");
            return;
        }
        String pathToReadableFile = args[0];
        String pathToWritableFile = args[1];

        File file;
        try {
            file = fileService.findFile(pathToReadableFile);
        } catch (FileNotFoundException e) {
            System.out.println("Source document not found");
            return;
        }

        Order order;
        try {
            FileInputStream fis = new FileInputStream(file);
            order = readService.readData(fis);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the source document");
            return;
        } catch (FileCorruptedException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            objectMapper.writeValue(fileService.getWritableFile(pathToWritableFile), order);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the receiver file");
        }
    }

}
