package game;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.awt.geom.AffineTransform;

public class Ship {
    private static final double AU_TO_M = 149597870700.0;

    
    private String shipName;
    private String solarSystemName;
    private String planetName;
    private String gateName;


    private Disposition disposition = Disposition.NEUTRAL;
    private boolean selected = false;

    // Armor
    private double armorCapacity;
    private double armorEmResistance;
    private double armorThermalResistance;
    private double armorKineticResistance;
    private double armorExplosiveResistance;
    private double armorCurrent;

    // Shields
    private double shieldCapacity;
    private double shieldEmResistance;
    private double shieldThermalResistance;
    private double shieldKineticResistance;
    private double shieldExplosiveResistance;
    private double shieldCurrent;

    // Hull
    private double hullCapacity;
    private double hullEmResistance;
    private double hullThermalResistance;
    private double hullKineticResistance;
    private double hullExplosiveResistance;
    private double hullCurrent;

    //Navigation
    private long positionX;
    private long positionY;
    private double velocityMax;
    private double velocityX;
    private double velocityY;
    private double inertiaModifier;
    private double warpAcceleration;
    private double warpVelocity;
    private ShipMovementState shipMovementState;
    private double orbitRadius;
    private long approachX;
    private long approachY;
    private long warpStartingX;
    private long warpStartingY;
    private double timeInWarp;

    //Structure
    private double cargoCapacity;
    private int droneBayCapacity;
    private double mass;
    private double shipDiameter;

    // Fittings
    private int highSlots;
    private int mediumSlots;
    private int lowSlots;
    private int rigSlots;
    private int rigCalibration;
    private int turretHardpoints;
    private int launcherHardpoints;
    private double powerGrid;
    private double cpu;

    // Capacitor
    private double capacitorCapacity;
    private double capacitorRechargeTime;
    private double capacitorCurrent;

    // Image
    private String imageFileName;
    private BufferedImage image;
    private double triangleRotationAngle = 0;

    public Ship(
            String shipName, 
            double armorCapacity,
            double armorEmResistance,
            double armorThermalResistance,
            double armorKineticResistance,
            double armorExplosiveResistance,
            double armorCurrent,
            double shieldCapacity,
            double shieldEmResistance,
            double shieldThermalResistance,
            double shieldKineticResistance,
            double shieldExplosiveResistance,
            double shieldCurrent,
            double hullCapacity,
            double hullEmResistance,
            double hullThermalResistance,
            double hullKineticResistance,
            double hullExplosiveResistance,
            double hullCurrent,
            long positionX,
            long positionY,
            double velocityX,
            double velocityY,
            double velocityMax,
            double inertiaModifier,
            ShipMovementState shipMovementState,
            double orbitRadius,
            long approachX,
            long approachY,
            double cargoCapacity,
            int droneBayCapacity,
            double mass,
            double shipDiameter,
            int highSlots,
            int mediumSlots,
            int lowSlots,
            int rigSlots,
            int rigCalibration,
            int turretHardpoints,
            int launcherHardpoints,
            double powerGrid,
            double cpu,
            double capacitorCapacity,
            double capacitorRechargeTime,
            double capacitorCurrent,
            String imageFileName,
            Double warpVelocity,
            Double warpAcceleration

            ) {
            
        this.shipName = shipName;
        this.armorCapacity = armorCapacity;
        this.armorEmResistance = armorEmResistance;
        this.armorThermalResistance = armorThermalResistance;
        this.armorKineticResistance = armorKineticResistance;
        this.armorExplosiveResistance = armorExplosiveResistance;
        this.armorCurrent = armorCurrent;
        this.shieldCapacity = shieldCapacity;
        this.shieldEmResistance = shieldEmResistance;
        this.shieldThermalResistance = shieldThermalResistance;
        this.shieldKineticResistance = shieldKineticResistance;
        this.shieldExplosiveResistance = shieldExplosiveResistance;
        this.shieldCurrent = shieldCurrent;
        this.hullCapacity = hullCapacity;
        this.hullEmResistance = hullEmResistance;
        this.hullThermalResistance = hullThermalResistance;
        this.hullKineticResistance = hullKineticResistance;
        this.hullExplosiveResistance = hullExplosiveResistance;
        this.hullCurrent = hullCurrent;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityMax = velocityMax;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.inertiaModifier = inertiaModifier;
        this.shipMovementState = shipMovementState;
        this.orbitRadius = orbitRadius;
        this.approachX = approachX;
        this.approachY = approachY;

        this.cargoCapacity = cargoCapacity;
        this.droneBayCapacity = droneBayCapacity;
        this.mass = mass;
        this.shipDiameter = shipDiameter;
        this.highSlots = highSlots;
        this.mediumSlots = mediumSlots;
        this.lowSlots = lowSlots;
        this.rigSlots = rigSlots;
        this.rigCalibration = rigCalibration;
        this.turretHardpoints = turretHardpoints;
        this.launcherHardpoints = launcherHardpoints;
        this.powerGrid = powerGrid;
        this.cpu = cpu;
        this.capacitorCapacity = capacitorCapacity;
        this.capacitorRechargeTime = capacitorRechargeTime;
        this.capacitorCurrent = capacitorCurrent;
        this.imageFileName = imageFileName;

        this.image = loadImage(imageFileName);
        this.warpVelocity = warpVelocity * AU_TO_M;
        this.warpAcceleration = warpAcceleration * AU_TO_M;
    }

    private BufferedImage loadImage(String imageFileName) {
        BufferedImage i = null;
        // Load the image
        try {
            URL resourceUrl = getClass().getResource(imageFileName);

            i = ImageIO.read(resourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    // Getters and setters for the attributes

    public String getShipName() {
        return shipName;
    }

    public double getArmorCapacity() {
        return armorCapacity;
    }

    public void setArmorCapacity(double armorCapacity) {
        this.armorCapacity = armorCapacity;
    }

    public double getArmorEmResistance() {
        return armorEmResistance;
    }

    public void setArmorEmResistance(double armorEmResistance) {
        this.armorEmResistance = armorEmResistance;
    }

    public double getArmorThermalResistance() {
        return armorThermalResistance;
    }

    public void setArmorThermalResistance(double armorThermalResistance) {
        this.armorThermalResistance = armorThermalResistance;
    }

    public double getArmorKineticResistance() {
        return armorKineticResistance;
    }

    public void setArmorKineticResistance(double armorKineticResistance) {
        this.armorKineticResistance = armorKineticResistance;
    }

    public double getArmorExplosiveResistance() {
        return armorExplosiveResistance;
    }

    public void setArmorExplosiveResistance(double armorExplosiveResistance) {
        this.armorExplosiveResistance = armorExplosiveResistance;
    }

    public double getArmorCurrent() {
        return armorCurrent;
    }

    public void setArmorCurrent(double armorCurrent) {
        this.armorCurrent = armorCurrent;
    }

    public double getShieldCapacity() {
        return shieldCapacity;
    }

    public void setShieldCapacity(double shieldCapacity) {
        this.shieldCapacity = shieldCapacity;
    }

    public double getShieldEmResistance() {
        return shieldEmResistance;
    }

    public void setShieldEmResistance(double shieldEmResistance) {
        this.shieldEmResistance = shieldEmResistance;
    }

    public double getShieldThermalResistance() {
        return shieldThermalResistance;
    }

    public void setShieldThermalResistance(double shieldThermalResistance) {
        this.shieldThermalResistance = shieldThermalResistance;
    }

    public double getShieldKineticResistance() {
        return shieldKineticResistance;
    }

    public void setShieldKineticResistance(double shieldKineticResistance) {
        this.shieldKineticResistance = shieldKineticResistance;
    }

    public double getShieldExplosiveResistance() {
        return shieldExplosiveResistance;
    }

    public void setShieldExplosiveResistance(double shieldExplosiveResistance) {
        this.shieldExplosiveResistance = shieldExplosiveResistance;
    }

    public double getShieldCurrent() {
        return shieldCurrent;
    }

    public void setShieldCurrent(double shieldCurrent) {
        this.shieldCurrent = shieldCurrent;
    }

    public double getHullCapacity() {
        return hullCapacity;
    }

    public void setHullCapacity(double hullCapacity) {
        this.hullCapacity = hullCapacity;
    }

    public double getHullEmResistance() {
        return hullEmResistance;
    }

    public void setHullEmResistance(double hullEmResistance) {
        this.hullEmResistance = hullEmResistance;
    }

    public double getHullThermalResistance() {
        return hullThermalResistance;
    }

    public void setHullThermalResistance(double hullThermalResistance) {
        this.hullThermalResistance = hullThermalResistance;
    }

    public double getHullKineticResistance() {
        return hullKineticResistance;
    }

    public void setHullKineticResistance(double hullKineticResistance) {
        this.hullKineticResistance = hullKineticResistance;
    }

    public double getHullExplosiveResistance() {
        return hullExplosiveResistance;
    }

    public void setHullExplosiveResistance(double hullExplosiveResistance) {
        this.hullExplosiveResistance = hullExplosiveResistance;
    }

    public double getHullCurrent() {
        return hullCurrent;
    }

    public void setHullCurrent(double hullCurrent) {
        this.hullCurrent = hullCurrent;
    }

    public double getvelocityMax() {
        return velocityMax;
    }

    public void setvelocityMax(double velocityMax) {
        this.velocityMax = velocityMax;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public long getPositionX() {
        return positionX;
    }

    public void setPositionX(long positionX) {
        this.positionX = positionX;
    }

    public long getPositionY() {
        return positionY;
    }

    public void setPositionY(long positionY) {
        this.positionY = positionY;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public double getInertiaModifier() {
        return inertiaModifier;
    }

    public void setInertiaModifier(double inertiaModifier) {
        this.inertiaModifier = inertiaModifier;
    }

    public ShipMovementState getShipMovementState() {
        return shipMovementState;
    }

    public void setShipMovementState(ShipMovementState shipMovementState) {

        this.shipMovementState = shipMovementState;
    }

    public double getOrbitRadius() {
        return orbitRadius;
    }

    public void setOrbitRadius(double orbitRadius) {
        if (orbitRadius < 500) {
            orbitRadius = 500;
        }
        this.orbitRadius = orbitRadius;
    }

    public long getApproachX() {
        return approachX;
    }

    public void setApproachX(long approachX) {
        this.approachX = approachX;
    }

    public long getApproachY() {
        return approachY;
    }

    public void setApproachY(long approachY) {
        this.approachY = approachY;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public int getDroneBayCapacity() {
        return droneBayCapacity;
    }

    public void setDroneBayCapacity(int droneBayCapacity) {
        this.droneBayCapacity = droneBayCapacity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getshipDiameter() {
        return shipDiameter;
    }

    public void setshipDiameter(double shipDiameter) {
        this.shipDiameter = shipDiameter;
    }

    public int getHighSlots() {
        return highSlots;
    }

    public void setHighSlots(int highSlots) {
        this.highSlots = highSlots;
    }

    public int getMediumSlots() {
        return mediumSlots;
    }

    public void setMediumSlots(int mediumSlots) {
        this.mediumSlots = mediumSlots;
    }

    public int getLowSlots() {
        return lowSlots;
    }

    public void setLowSlots(int lowSlots) {
        this.lowSlots = lowSlots;
    }

    public int getRigSlots() {
        return rigSlots;
    }

    public void setRigSlots(int rigSlots) {
        this.rigSlots = rigSlots;
    }

    public int getRigCalibration() {
        return rigCalibration;
    }

    public void setRigCalibration(int rigCalibration) {
        this.rigCalibration = rigCalibration;
    }

    public int getTurretHardpoints() {
        return turretHardpoints;
    }

    public void setTurretHardpoints(int turretHardpoints) {
        this.turretHardpoints = turretHardpoints;
    }

    public int getLauncherHardpoints() {
        return launcherHardpoints;
    }

    public void setLauncherHardpoints(int launcherHardpoints) {
        this.launcherHardpoints = launcherHardpoints;
    }

    public double getPowerGrid() {
        return powerGrid;
    }

    public void setPowerGrid(double powerGrid) {
        this.powerGrid = powerGrid;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public double getCapacitorCapacity() {
        return capacitorCapacity;
    }

    public void setCapacitorCapacity(double capacitorCapacity) {
        this.capacitorCapacity = capacitorCapacity;
    }

    public double getCapacitorRechargeTime() {
        return capacitorRechargeTime;
    }

    public void setCapacitorRechargeTime(double capacitorRechargeTime) {
        this.capacitorRechargeTime = capacitorRechargeTime;
    }

    public double getCapacitorCurrent() {
        return capacitorCurrent;
    }

    public void setCapacitorCurrent(double capacitorCurrent) {
        this.capacitorCurrent = capacitorCurrent;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
        setImage();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage() {
        this.image = loadImage(imageFileName);
    }

    public Disposition getDisposition() {
        return disposition;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }

    public Double getWarpVelocity() {
        return warpVelocity;
    }

    public void setWarpVelocity(Double warpVelocity) {
        this.warpVelocity = warpVelocity;
    }

    public Double getWarpAcceleration() {
        return warpAcceleration;
    }

    public void setWarpAcceleration(Double warpAcceleration) {
        this.warpAcceleration = warpAcceleration;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Other methods, such as actions, movement, etc.

    public void warp(double deltaTime) {
        // Warp the ship
        if (this.shipMovementState != ShipMovementState.INWARP) {
            System.out.println("Warping");
            warpStartingX = positionX;
            warpStartingY = positionY;
            this.shipMovementState = ShipMovementState.INWARP;
            timeInWarp = 0;
        } 

        timeInWarp += deltaTime;
        

        // When the remaining distance is less than 1 km, stop warping
        
        double distance = Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2));
        if (distance < 50) {
            this.shipMovementState = ShipMovementState.STOP;
            this.positionX = approachX;
            this.positionY = approachY;
            return;
        }

        

        // Calculate the total time it will take to travel from the starting point to the approach point
        double totalTime = Math.sqrt(Math.pow(approachX - warpStartingX, 2) + Math.pow(approachY - warpStartingY, 2)) / warpVelocity;
        totalTime = Math.max(totalTime, 5);

        // Use a sigmoid function to calculate the new position as a function of timeInWarp
        double warpFactor = 5;
        double x = approachX - warpStartingX;
        double y = approachY - warpStartingY;
        double t = timeInWarp;
        double a = warpFunc(totalTime, warpFactor, t);
        
        double newX = warpStartingX + x * a;
        double newY = warpStartingY + y * a;
        this.positionX = (long) newX;
        this.positionY = (long) newY;

    }

    private double warpFunc(double t, double w, double x) {
        double a = 1 / (1 + Math.exp(-x * w * 2 / t + w));
        double b = 1 / (1 + Math.exp(w));
        double c = 1 / (1 + Math.exp(-w));
        return (a - b) / (c - b);
    }



    public void updateCurrentCapacitor(double t) {
        // Update the current capacitor

        // t is the time since the last update in seconds

        double p1 = Math.sqrt(capacitorCurrent / capacitorCapacity) - 1.0;
        double p2 = Math.exp(- 5 * t / capacitorRechargeTime);
        double p3 = 1 + p1 * p2;

        this.capacitorCurrent = capacitorCapacity * Math.pow(p3, 2);

    }

    public void takeDamage(double EmDamage, double ThermalDamage, double KineticDamage, double ExplosiveDamage) {
        // Update the current armor, shield, and hull. Take into account resistances.

        // The ship only takes armor damage if the sheild is down

        // The ship only takes hull damage if the armor is down

        double totalDamage = EmDamage + ThermalDamage + KineticDamage + ExplosiveDamage;

        // Exit function if totalDamage is 0
        if (totalDamage == 0) {
            return;
        }
        
        // Calculate the damage to the shield
        double potentialShieldDamage = EmDamage * (1 - shieldEmResistance) + ThermalDamage * (1 - shieldThermalResistance) + KineticDamage * (1 - shieldKineticResistance) + ExplosiveDamage * (1 - shieldExplosiveResistance);
        double actualShieldDamage = Math.min(potentialShieldDamage, shieldCurrent);
        // Calculate the total damage resisted by the shield
        double shieldResistedDamage = Math.min(totalDamage, shieldCurrent) - actualShieldDamage;
        
        // Calculate the amount of EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage the shield took
        double shieldEmDamage = actualShieldDamage * EmDamage / totalDamage;
        double shieldThermalDamage = actualShieldDamage * ThermalDamage / totalDamage;
        double shieldKineticDamage = actualShieldDamage * KineticDamage / totalDamage;
        double shieldExplosiveDamage = actualShieldDamage * ExplosiveDamage / totalDamage;

        // Calculate the amount of EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage the shield resisted
        double shieldEmResistedDamage = (actualShieldDamage + shieldResistedDamage) * shieldEmResistance * EmDamage / totalDamage;
        double shieldThermalResistedDamage = (actualShieldDamage + shieldResistedDamage) * shieldThermalResistance * ThermalDamage / totalDamage;
        double shieldKineticResistedDamage = (actualShieldDamage + shieldResistedDamage) * shieldKineticResistance * KineticDamage / totalDamage;
        double shieldExplosiveResistedDamage = (actualShieldDamage + shieldResistedDamage) * shieldExplosiveResistance * ExplosiveDamage / totalDamage;

        this.shieldCurrent = shieldCurrent - actualShieldDamage;
        
        // Reduce EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage by the amount the shield took and resisted
        EmDamage = EmDamage - shieldEmDamage - shieldEmResistedDamage;
        ThermalDamage = ThermalDamage - shieldThermalDamage - shieldThermalResistedDamage;
        KineticDamage = KineticDamage - shieldKineticDamage - shieldKineticResistedDamage;
        ExplosiveDamage = ExplosiveDamage - shieldExplosiveDamage - shieldExplosiveResistedDamage;

        totalDamage = EmDamage + ThermalDamage + KineticDamage + ExplosiveDamage;
        
        // Exit function if totalDamage is 0
        if (totalDamage == 0) {
            return;
        }

        // Calculate the damage to the armor
        double potentialArmorDamage = EmDamage * (1 - armorEmResistance) + ThermalDamage * (1 - armorThermalResistance) + KineticDamage * (1 - armorKineticResistance) + ExplosiveDamage * (1 - armorExplosiveResistance);
        double actualArmorDamage = Math.min(potentialArmorDamage, armorCurrent);
        System.out.println("actualArmorDamage: " + actualArmorDamage);
        // Calculate the total damage resisted by the armor
        double armorResistedDamage = Math.min(totalDamage, armorCurrent) - actualArmorDamage;
        System.out.println("armorResistedDamage: " + armorResistedDamage);

        // Calculate the amount of EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage the armor took
        double armorEmDamage = actualArmorDamage * EmDamage / totalDamage;
        double armorThermalDamage = actualArmorDamage * ThermalDamage / totalDamage;
        double armorKineticDamage = actualArmorDamage * KineticDamage / totalDamage;
        double armorExplosiveDamage = actualArmorDamage * ExplosiveDamage / totalDamage;

        // Calculate the amount of EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage the armor resisted
        double armorEmResistedDamage = (actualArmorDamage + armorResistedDamage) * armorEmResistance * EmDamage / totalDamage;
        double armorThermalResistedDamage = (actualArmorDamage + armorResistedDamage) * armorThermalResistance * ThermalDamage / totalDamage;
        double armorKineticResistedDamage = (actualArmorDamage + armorResistedDamage) * armorKineticResistance * KineticDamage / totalDamage;
        double armorExplosiveResistedDamage = (actualArmorDamage + armorResistedDamage) * armorExplosiveResistance * ExplosiveDamage / totalDamage;
        
        this.armorCurrent = armorCurrent - actualArmorDamage;

        // Reduce EmDamage, ThermalDamage, KineticDamage, and ExplosiveDamage by the amount the armor took and resisted
        EmDamage = EmDamage - armorEmDamage - armorEmResistedDamage;
        ThermalDamage = ThermalDamage - armorThermalDamage - armorThermalResistedDamage;
        KineticDamage = KineticDamage - armorKineticDamage - armorKineticResistedDamage;
        ExplosiveDamage = ExplosiveDamage - armorExplosiveDamage - armorExplosiveResistedDamage;

        totalDamage = EmDamage + ThermalDamage + KineticDamage + ExplosiveDamage;
        
        // Exit function if totalDamage is 0
        if (totalDamage == 0) {
            return;
        }

        // Calculate the damage to the hull
        double potentialHullDamage = EmDamage * (1 - hullEmResistance) + ThermalDamage * (1 - hullThermalResistance) + KineticDamage * (1 - hullKineticResistance) + ExplosiveDamage * (1 - hullExplosiveResistance);
        double actualHullDamage = Math.min(potentialHullDamage, hullCurrent);
        
        this.hullCurrent = hullCurrent - actualHullDamage;

    }
    
    public void accelerate(double t, double x, double y) {
        // Update the current velocity

        // t is the time since the last update in seconds

        // (x,y) is the direction of the acceleration
        double n = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        x = x / n;
        y = y / n;

        double a = inertiaModifier / mass * Math.pow(10,6);
        
        double vMaxX = velocityMax * x;
        double vNewX;
        if (vMaxX < 0) {
            vNewX = Math.max(velocityX - a * t, vMaxX);
        } else {
            vNewX = Math.min(velocityX + a * t, vMaxX);
        }
        this.velocityX = vNewX;
        
        double vMaxY = velocityMax * y;
        double vNewY;
        if (vMaxY < 0) {
            vNewY = Math.max(velocityY - a * t, vMaxY);
        } else {
            vNewY = Math.min(velocityY + a * t, vMaxY);
        }
        this.velocityY = vNewY;
        
        // Update the position
        this.positionX = (long) (positionX + velocityX * t);
        this.positionY = (long) (positionY + velocityY * t);
    }

    
    public void approach(long x, long y) {
        // Set the approach point
        if (shipMovementState == ShipMovementState.INWARP) {
            System.out.println("Can't approach while warping");
            return;
        }

        // (x,y) is the point to approach
        this.shipMovementState = ShipMovementState.APPROACH;
        this.approachX = x;
        this.approachY = y;
    }
    
    public void startWarp(long x, long y) {
        // Set the approach point

        // (x,y) is the point to approach
        this.shipMovementState = ShipMovementState.WARP;
        this.approachX = x;
        this.approachY = y;
    }

    public void orbit(long x, long y, long r) {
        // Set the orbit point and radius

        // (x,y) is the point to orbit
        // r is the radius of the orbit
        this.shipMovementState = ShipMovementState.ORBIT;
        this.approachX = x;
        this.approachY = y;
        this.orbitRadius = r;
    }

    public void stop() {
        // Stop the ship
        this.shipMovementState = ShipMovementState.STOP;
        this.approachX = this.positionX;
        this.approachY = this.positionY;
    }

    public void move(double t) {
        if (shipMovementState == ShipMovementState.APPROACH) {
            // Calculate distance from current location to approach location
            double distance = Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2));
            if (distance < 250) {
                // Stop the ship
                stop();
                // Decelerate the ship
                double x = - velocityX;
                double y = - velocityY;
                accelerate(t, x, y);
            } else {
                // Accelerate towards the approach point
                double x = approachX - positionX;
                double y = approachY - positionY;
                accelerate(t, x, y);
            }
        } else if (shipMovementState == ShipMovementState.STOP) {
            double v = Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
            if (v < 0.1 * velocityMax) {
                // Stop the ship
                this.velocityX = 0;
                this.velocityY = 0;
            } else {
                // Decelerate the ship
                double x = - velocityX;
                double y = - velocityY;
                accelerate(t, x, y);
            }
        } else if (shipMovementState == ShipMovementState.ORBIT) {
            // Calculate distance from current location to the orbit radius
            double distance = Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2));
            if ((distance - orbitRadius) > 250) {
                // Accelerate towards the orbit point
                double x = approachX - positionX;
                double y = approachY - positionY;
                accelerate(t, x, y);
            } else if ((distance - orbitRadius) < -250) {
                // Accelerate away from the orbit point
                double x = approachX - positionX;
                double y = approachY - positionY;
                accelerate(t, x, y);
            } else {
                
                // Calculate the unit vector (x,y) that points perpendicular to the vector from the ship to the orbit point
                double x = positionY - approachY;
                double y = approachX - positionX;

                accelerate(t, x, y);

            }
            
        } else if (shipMovementState == ShipMovementState.WARP) {

            

            // If the distance from the ship to the warp point is less than 150km, then approach the warp point
            if (Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2)) < 150000) {
                shipMovementState = ShipMovementState.APPROACH;
                // Accelerate towards the approach point
                double x = approachX - positionX;
                double y = approachY - positionY;
                accelerate(t, x, y);
            } else 


            // In order to warp, the ship must first be at least 75% of the max velocity
            // The angle of the velocity must also be within 20 degrees of the angle of the approach point
            if ((Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2)) >= 0.75 * velocityMax)
                && (Math.abs(Math.atan2(velocityY, velocityX) - Math.atan2(approachY - positionY, approachX - positionX)) < Math.PI / 9)) {
                // Warp the ship
                warp(t);
                
            } else {
                // Accelerate the ship towards the approach point
                double x = approachX - positionX;
                double y = approachY - positionY;
                accelerate(t, x, y);

            }

        } else if (shipMovementState == ShipMovementState.INWARP) {
            // Warp the ship
            warp(t);
        }

        

    }

    public void draw(Graphics2D g, CoordinateConvert convert, double deltaTime) {
        // convert game coordinates to screen coordinates
        Coordinate screenCoordinate = convert.gameToWindow(positionX, positionY);
        int x = (int) screenCoordinate.getX();
        int y = (int) screenCoordinate.getY();

        // If the ships movement state is approach, draw a line from the ship to the approach point
        if (shipMovementState == ShipMovementState.APPROACH 
            && disposition == Disposition.PLAYER) {

            drawApproachLine(g, convert, x, y);
        }
        
        // If the ships movement state is warp, draw a line from the ship to the approach point
        if ((shipMovementState == ShipMovementState.WARP 
                || shipMovementState == ShipMovementState.INWARP) 
                && disposition == Disposition.PLAYER) {
            
            drawWarpLine(g, convert, x, y);
        }


        // Draw a solid green triangle centered at the ship's location
        if (disposition == Disposition.PLAYER) {
            g.setColor(new java.awt.Color(0, 255, 0));
        } else if (disposition == Disposition.ENEMY) {
            g.setColor(new java.awt.Color(255, 0, 0));
        } else if (disposition == Disposition.NEUTRAL) {
            // grey
            g.setColor(new java.awt.Color(128, 128, 128));
        }
        g.fillPolygon(new int[] {x, x - 5, x + 5}, new int[] {y - 5, y + 5, y + 5}, 3);


        // Draw a circle around the ship if it is selected
        if (selected) {
            drawSelected(g, convert, x, y, deltaTime);
        }

        
        
    }

    private void drawSelected(Graphics2D g, CoordinateConvert convert, int x, int y, double deltaTime) {
        // Change line color to electric blue and thickness to 1
        g.setColor(new java.awt.Color(0, 255, 255));
        g.setStroke(new java.awt.BasicStroke(1));
        g.drawOval(x - 15, y - 15, 30, 30);

        // Calculate the position and size of the orbiting triangles
        int triangleSize = 5;  // Adjust the size of the triangles as needed
        double triangleDistance = 15 + triangleSize + 5;  // Adjust the distance from the ship as needed
        double secondsPerRevolution = 20000;
        triangleRotationAngle += (2 * Math.PI) / (deltaTime * secondsPerRevolution);
        // Draw the orbiting triangles
        for (int i = 0; i < 3; i++) {
            double angle = triangleRotationAngle + (i * (2 * Math.PI / 3));
            int triangleX = (int) (x + triangleDistance * Math.cos(angle));
            int triangleY = (int) (y + triangleDistance * Math.sin(angle));
            
            AffineTransform at = AffineTransform.getRotateInstance(angle, x,y);
            at.translate(triangleX, triangleY);
            at.rotate(angle - Math.PI / 2, 0, 0);
            
            // Draw the triangle with the middle of the triangle at 0,0
            Polygon triangle = new Polygon();
            triangle.addPoint(0, -triangleSize);
            triangle.addPoint(triangleSize, triangleSize);
            triangle.addPoint(-triangleSize, triangleSize);
            
            g.fill(at.createTransformedShape(triangle));

            
            
        }

    }

    private void drawWarpLine(Graphics2D g, CoordinateConvert convert, int x, int y) {
        Coordinate approachScreenCoordinate = convert.gameToWindow((long) approachX, (long) approachY);
        int aX = (int) approachScreenCoordinate.getX();
        int aY = (int) approachScreenCoordinate.getY();
        // Change line color to electric red and thickness to 1
        g.setColor(new java.awt.Color(255, 0, 255));
        g.setStroke(new java.awt.BasicStroke(1));
        g.drawLine(x, y, aX, aY);
        // At the endpoint of the line, write the distance in km
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        g.drawString(String.format("%.0f", Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2)) / 1000) + " km", aX, aY);

    }

    private void drawApproachLine(Graphics2D g, CoordinateConvert convert, int x, int y) {

        Coordinate approachScreenCoordinate = convert.gameToWindow((long) approachX, (long) approachY);
        int aX = (int) approachScreenCoordinate.getX();
        int aY = (int) approachScreenCoordinate.getY();
        // Change line color to electric blue and thickness to 1
        g.setColor(new java.awt.Color(0, 255, 255));
        g.setStroke(new java.awt.BasicStroke(1));
        g.drawLine(x, y, aX, aY);
        // At the endpoint of the line, write the distance in km
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        g.drawString(String.format("%.0f", Math.sqrt(Math.pow(approachX - positionX, 2) + Math.pow(approachY - positionY, 2)) / 1000) + " km", aX, aY);
    }

    private void drawWarpStart() {

    }

    public void update(double t) {
        // Update the ship

        // t is the time since the last update in seconds

        // Update the current capacitor
        updateCurrentCapacitor(t);

        // Update the ship's movement
        move(t);


    }
}
