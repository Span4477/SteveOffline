package readers;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class Controls {
    private Properties properties;

    public Controls() {
        ObjectMapper objectMapper = new ObjectMapper();
        properties = new Properties();
        File configFile = new File("src/config/controls.json");

        try {
            JsonNode rootNode = objectMapper.readTree(configFile);
            

            // Retrieve the values and set them in the properties
            String panUp = rootNode.get("panUp").asText();
            String panDown = rootNode.get("panDown").asText();
            String panLeft = rootNode.get("panLeft").asText();
            String panRight = rootNode.get("panRight").asText();
            String zoomIn = rootNode.get("zoomIn").asText();
            String zoomOut = rootNode.get("zoomOut").asText();
            String systemMap = rootNode.get("systemMap").asText();


            // Set the properties
            properties.setProperty(String.valueOf(panUp), "panUp");
            properties.setProperty(String.valueOf(panDown), "panDown");
            properties.setProperty(String.valueOf(panLeft), "panLeft");
            properties.setProperty(String.valueOf(panRight), "panRight");
            properties.setProperty(String.valueOf(zoomIn), "zoomIn");
            properties.setProperty(String.valueOf(zoomOut), "zoomOut");
            properties.setProperty(String.valueOf(systemMap), "systemMap");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getControl(String value) {
        return properties.getProperty(value);
    }

}
