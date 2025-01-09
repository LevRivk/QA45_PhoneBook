package utils;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static void main(String[] args) {
        getProperty("login.properties","email");
    }

    public static String getProperty(String fileName, String key) {
        Properties properties = new Properties();
        FileInputStream fileInputStream =
                null;
        try {
            fileInputStream = new FileInputStream("src/test/resources/properties/" + fileName);
            properties.load(fileInputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
// public static String getProperty(String fileName, String key) {
//        Properties properties = new Properties();
//        try (FileInputStream fileInputStream = new FileInputStream(
//                "src/test/resources/properties/" + fileName)) {
//            properties.load(fileInputStream);
//            return properties.getProperty(key)
//;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
        // это более правильное написание после 11 джавы

    }
}

