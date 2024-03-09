package ru.nsu.ccfit.lisitsin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileOperations {
    private final static Logger LOGGER = LogManager.getLogger(FileOperations.class);

    public static void createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.createNewFile()) {
                throw new IOException();
            } else {
                LOGGER.info("Файл {} успешно создан.", fileName);
            }
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка при создании файла {}: {}.", fileName, e.getMessage());
        }
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            LOGGER.info("Файл {} успешно удален.", file);
        } else {
            LOGGER.error("Файл {} не может быть удален.", file);
        }
    }

    public static void writeToFile(String fileName, List<String> lines) {
        try {
            Path path = Paths.get(fileName);
            Files.write(path, lines, Charset.defaultCharset());
            LOGGER.info("Список строк успешно записан в файл {}.", fileName);
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка при записи в файл {}: {}.", fileName, e.getMessage());
        }
    }

    public static void clearFile(String fileName) {
        try {
            Path path = Paths.get(fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            LOGGER.info("Файл {} успешно очищен.", fileName);
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка при очистке файла {}: {}.", fileName, e.getMessage());
        }
    }

    public static String readFirstLine(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine();
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка при чтении файла {}: {}.", fileName, e.getMessage());
        }
        return null;
    }

    public static OutputStream getOutputStream(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Произошла ошибка при открытии файла {}: {}.", fileName, e.getMessage());
        }
        return null;
    }
}
