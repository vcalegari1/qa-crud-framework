package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads test configuration from config.properties, with the option to
 * override via a JVM system property, for example:
 * mvn test -Dbase.uri=https://random-api.com
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String override = System.getProperty(key);
        return override != null ? override : properties.getProperty(key);
    }

    public static String baseUri() {
        return get("base.uri");
    }
}