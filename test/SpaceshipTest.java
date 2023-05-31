import org.junit.Test;
import static org.junit.Assert.*;
import game.Ship;
import game.ShipMovementState;
public class SpaceshipTest {
    @Test
    public void testPlayerSpaceship() {
        // Create a PlayerSpaceship object
        Ship spaceship = new Ship(
            "My Ship", 
            100,
            0.6,
            0.35,
            0.25,
            0.1,
            100,
            100,
            0,
            0.2,
            0.4,
            0.5,
            100,
            100,
            0.6,
            0.6,
            0.6,
            0.6,
            100,
            0,
            0,
            0,
            0,
            365,
            10,
            ShipMovementState.STOP,
            100,
            0,
            100,
            100,
            100,
            1067000,
            35,
            100,
            100,
            100,
            100,
            250,
            60,
            0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            "",
            0.0,
            0.0
            );

        // Set some parameters of the PlayerSpaceship
        spaceship.setShipName("Updated Ship Name");
        
        // Output the results to the console
        System.out.println("Ship Name: " + spaceship.getShipName());
        
        // Add any necessary assertions
        assertEquals("Updated Ship Name", spaceship.getShipName());
        
        System.out.println("Current shield: " + spaceship.getShieldCurrent());
        System.out.println("Current armor: " + spaceship.getArmorCurrent());
        System.out.println("Current hull: " + spaceship.getHullCurrent());
        // Damage the ship
        spaceship.takeDamage(0,200,0,0);
        
        System.out.println("Current shield: " + spaceship.getShieldCurrent());
        System.out.println("Current armor: " + spaceship.getArmorCurrent());
        System.out.println("Current hull: " + spaceship.getHullCurrent());

        // Testing the velocity
        System.out.println("Current velocity: " + spaceship.getVelocityX());
        spaceship.accelerate(1, 1, 0);
        System.out.println("1 second velocity: " + spaceship.getVelocityX());
        spaceship.accelerate(10, 1, 0);
        System.out.println("10 second velocity: " + spaceship.getVelocityX());
        spaceship.accelerate(30, 1, 0);
        System.out.println("30 second velocity: " + spaceship.getVelocityX());
        spaceship.accelerate(60, 1, 0);
        System.out.println("60 second velocity: " + spaceship.getVelocityX());

        // Testing the capacitor
        System.out.println("Current capacitor: " + spaceship.getCapacitorCurrent());
        spaceship.updateCurrentCapacitor(1);
        System.out.println("1 second capacitor: " + spaceship.getCapacitorCurrent());
        spaceship.updateCurrentCapacitor(10);
        System.out.println("10 second capacitor: " + spaceship.getCapacitorCurrent());
        spaceship.updateCurrentCapacitor(30);
        System.out.println("30 second capacitor: " + spaceship.getCapacitorCurrent());
        spaceship.updateCurrentCapacitor(60);
        System.out.println("60 second capacitor: " + spaceship.getCapacitorCurrent());

    }

}
