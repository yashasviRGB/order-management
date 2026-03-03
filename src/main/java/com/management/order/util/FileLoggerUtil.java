package com.management.order.util;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileLoggerUtil {

    private static final String FILE_PATH = "orders.log";

    public synchronized void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }
}