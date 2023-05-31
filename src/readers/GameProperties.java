package readers;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GameProperties {
    private Properties properties;

    public GameProperties() {
        ObjectMapper objectMapper = new ObjectMapper();
        properties = new Properties();
        File configFile = new File("src/config/game_config.json");

        try {
            JsonNode rootNode = objectMapper.readTree(configFile);
            JsonNode windowNode = rootNode.get("window");
            JsonNode fontNode = rootNode.get("font");
            JsonNode zoomNode = rootNode.get("zoom");

            // Retrieve the values and set them in the properties
            int windowWidth = windowNode.get("width").asInt();
            int windowHeight = windowNode.get("height").asInt();
            String fontName = fontNode.get("name").asText();
            int fontSize = fontNode.get("size").asInt();
            long maxZoom = zoomNode.get("max").asLong();
            long minZoom = zoomNode.get("min").asLong();
            long defaultZoom = zoomNode.get("default").asLong();

            // Set the properties
            properties.setProperty("window.width", String.valueOf(windowWidth));
            properties.setProperty("window.height", String.valueOf(windowHeight));
            properties.setProperty("font.name", fontName);
            properties.setProperty("font.size", String.valueOf(fontSize));
            properties.setProperty("zoom.max", String.valueOf(maxZoom));
            properties.setProperty("zoom.min", String.valueOf(minZoom));
            properties.setProperty("zoom.default", String.valueOf(defaultZoom));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWindowWidth() {
        return Integer.parseInt(properties.getProperty("window.width"));
    }
    public int getWindowHeight() {
        return Integer.parseInt(properties.getProperty("window.height"));
    }
    public String getFontName() {
        return properties.getProperty("font.name");
    }
    public int getFontSize() {
        return Integer.parseInt(properties.getProperty("font.size"));
    }
    public long getMaxZoom() {
        return Long.parseLong(properties.getProperty("zoom.max"));
    }
    public long getMinZoom() {
        return Long.parseLong(properties.getProperty("zoom.min"));
    }
    public long getDefaultZoom() {
        return Long.parseLong(properties.getProperty("zoom.default"));
    }
}
