package readers;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import game.ShipMovementState;

import com.fasterxml.jackson.databind.JsonNode;

public class ShipProperties {
    private Properties properties;

    public ShipProperties() {
        ObjectMapper objectMapper = new ObjectMapper();
        properties = new Properties();
        File configFile = new File("src/config/ship_config.json");

        try {
            JsonNode rootNode = objectMapper.readTree(configFile);
            JsonNode playerStartingShipNode = rootNode.get("playerStartingShip");
            
            setProperties(playerStartingShipNode, "playerStartingShip");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties(JsonNode node, String fieldName) {
        properties.setProperty(fieldName + ".shipName", node.get("shipName").asText());
        properties.setProperty(fieldName + ".armorCapacity", node.get("armorCapacity").asText());
        properties.setProperty(fieldName + ".armorEmResistance", node.get("armorEmResistance").asText());
        properties.setProperty(fieldName + ".armorThermalResistance", node.get("armorThermalResistance").asText());
        properties.setProperty(fieldName + ".armorKineticResistance", node.get("armorKineticResistance").asText());
        properties.setProperty(fieldName + ".armorExplosiveResistance", node.get("armorExplosiveResistance").asText());
        properties.setProperty(fieldName + ".armorCurrent", node.get("armorCurrent").asText());
        properties.setProperty(fieldName + ".shieldCapacity", node.get("shieldCapacity").asText());
        properties.setProperty(fieldName + ".shieldEmResistance", node.get("shieldEmResistance").asText());
        properties.setProperty(fieldName + ".shieldThermalResistance", node.get("shieldThermalResistance").asText());
        properties.setProperty(fieldName + ".shieldKineticResistance", node.get("shieldKineticResistance").asText());
        properties.setProperty(fieldName + ".shieldExplosiveResistance", node.get("shieldExplosiveResistance").asText());
        properties.setProperty(fieldName + ".shieldCurrent", node.get("shieldCurrent").asText());
        properties.setProperty(fieldName + ".hullCapacity", node.get("hullCapacity").asText());
        properties.setProperty(fieldName + ".hullEmResistance", node.get("hullEmResistance").asText());
        properties.setProperty(fieldName + ".hullThermalResistance", node.get("hullThermalResistance").asText());
        properties.setProperty(fieldName + ".hullKineticResistance", node.get("hullKineticResistance").asText());
        properties.setProperty(fieldName + ".hullExplosiveResistance", node.get("hullExplosiveResistance").asText());
        properties.setProperty(fieldName + ".hullCurrent", node.get("hullCurrent").asText());
        properties.setProperty(fieldName + ".positionX", node.get("positionX").asText());
        properties.setProperty(fieldName + ".positionY", node.get("positionY").asText());
        properties.setProperty(fieldName + ".velocityMax", node.get("velocityMax").asText());
        properties.setProperty(fieldName + ".velocityX", node.get("velocityX").asText());
        properties.setProperty(fieldName + ".velocityY", node.get("velocityY").asText());
        properties.setProperty(fieldName + ".inertiaModifier", node.get("inertiaModifier").asText());
        properties.setProperty(fieldName + ".timeToWarp", node.get("timeToWarp").asText());
        properties.setProperty(fieldName + ".shipMovementState", node.get("shipMovementState").asText());
        properties.setProperty(fieldName + ".orbitRadius", node.get("orbitRadius").asText());
        properties.setProperty(fieldName + ".approachX", node.get("approachX").asText()); 
        properties.setProperty(fieldName + ".approachY", node.get("approachY").asText()); 
        properties.setProperty(fieldName + ".cargoCapacity", node.get("cargoCapacity").asText());
        properties.setProperty(fieldName + ".droneBayCapacity", node.get("droneBayCapacity").asText());
        properties.setProperty(fieldName + ".mass", node.get("mass").asText());
        properties.setProperty(fieldName + ".shipDiameter", node.get("shipDiameter").asText());
        properties.setProperty(fieldName + ".highSlots", node.get("highSlots").asText());
        properties.setProperty(fieldName + ".mediumSlots", node.get("mediumSlots").asText());
        properties.setProperty(fieldName + ".lowSlots", node.get("lowSlots").asText());
        properties.setProperty(fieldName + ".rigSlots", node.get("rigSlots").asText());
        properties.setProperty(fieldName + ".rigCalibration", node.get("rigCalibration").asText());
        properties.setProperty(fieldName + ".turretHardpoints", node.get("turretHardpoints").asText());
        properties.setProperty(fieldName + ".launcherHardpoints", node.get("launcherHardpoints").asText());
        properties.setProperty(fieldName + ".powerGrid", node.get("powerGrid").asText());
        properties.setProperty(fieldName + ".cpu", node.get("cpu").asText());
        properties.setProperty(fieldName + ".capacitorCapacity", node.get("capacitorCapacity").asText());
        properties.setProperty(fieldName + ".capacitorRechargeTime", node.get("capacitorRechargeTime").asText());
        properties.setProperty(fieldName + ".capacitorCurrent", node.get("capacitorCurrent").asText());
        properties.setProperty(fieldName + ".imageFileName", node.get("imageFileName").asText());
        properties.setProperty(fieldName + ".warpVelocity", node.get("warpVelocity").asText());
        properties.setProperty(fieldName + ".warpAcceleration", node.get("warpAcceleration").asText());

    }

    public String shipName(String shipType) {
        return properties.getProperty(shipType + ".shipName");
    }
    public double armorCapacity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorCapacity"));
    }
    public double armorEmResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorEmResistance"));
    }
    public double armorThermalResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorThermalResistance"));
    }
    public double armorKineticResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorKineticResistance"));
    }
    public double armorExplosiveResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorExplosiveResistance"));
    }
    public double armorCurrent(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".armorCurrent"));
    }
    public double shieldCapacity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldCapacity"));
    }
    public double shieldEmResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldEmResistance"));
    }
    public double shieldThermalResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldThermalResistance"));
    }
    public double shieldKineticResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldKineticResistance"));
    }
    public double shieldExplosiveResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldExplosiveResistance"));
    }
    public double shieldCurrent(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shieldCurrent"));
    }
    public double hullCapacity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullCapacity"));
    }
    public double hullEmResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullEmResistance"));
    }
    public double hullThermalResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullThermalResistance"));
    }
    public double hullKineticResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullKineticResistance"));
    }
    public double hullExplosiveResistance(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullExplosiveResistance"));
    }
    public double hullCurrent(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".hullCurrent"));
    }
    public long positionX(String shipType) {
        return Long.parseLong(properties.getProperty(shipType + ".positionX"));
    }
    public long positionY(String shipType) {
        return Long.parseLong(properties.getProperty(shipType + ".positionY"));
    }
    public double velocityMax(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".velocityMax"));
    }
    public double velocityX(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".velocityX"));
    }
    public double velocityY(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".velocityY"));
    }
    public double inertiaModifier(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".inertiaModifier"));
    }
    public double timeToWarp(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".timeToWarp"));
    }
    public ShipMovementState shipMovementState(String shipType) {
        String s = properties.getProperty(shipType + ".shipMovementState");
        if (s == "APPROACH") {
            return ShipMovementState.APPROACH;
        } else if (s == "ORBIT") {
            return ShipMovementState.ORBIT;
        } else if (s == "WARP") {
            return ShipMovementState.WARP;
        }
        return ShipMovementState.STOP;
    }
    public double orbitRadius(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".orbitRadius"));
    }
    public long approachX(String shipType) {
        return Long.parseLong(properties.getProperty(shipType + ".approachX"));
    }
    public long approachY(String shipType) {
        return Long.parseLong(properties.getProperty(shipType + ".approachY"));
    }
    public double cargoCapacity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".cargoCapacity"));
    }
    public int droneBayCapacity(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".droneBayCapacity"));
    }
    public double mass(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".mass"));
    }
    public double shipDiameter(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".shipDiameter"));
    }
    public int highSlots(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".highSlots"));
    }
    public int mediumSlots(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".mediumSlots"));
    }
    public int lowSlots(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".lowSlots"));
    }
    public int rigSlots(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".rigSlots"));
    }
    public int rigCalibration(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".rigCalibration"));
    }
    public int turretHardpoints(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".turretHardpoints"));
    }
    public int launcherHardpoints(String shipType) {
        return Integer.parseInt(properties.getProperty(shipType + ".launcherHardpoints"));
    }
    public double powerGrid(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".powerGrid"));
    }
    public double cpu(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".cpu"));
    }
    public double capacitorCapacity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".capacitorCapacity"));
    }
    public double capacitorRechargeTime(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".capacitorRechargeTime"));
    }
    public double capacitorCurrent(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".capacitorCurrent"));
    }
    public String imageFileName(String shipType) {
        return properties.getProperty(shipType + ".imageFileName");
    }
    public double warpVelocity(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".warpVelocity"));
    }
    public double warpAcceleration(String shipType) {
        return Double.parseDouble(properties.getProperty(shipType + ".warpAcceleration"));
    }
}
