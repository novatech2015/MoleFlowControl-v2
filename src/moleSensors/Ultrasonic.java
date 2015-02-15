/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleSensors;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import static moleResources.MoleResources.gpio;

/**
 * Ultrasonic Ping))) Sensor.
 * @author Mr. Mallory
 */
public class Ultrasonic {
    
    private GpioPinDigitalInput pingChannel;
    private GpioPinDigitalOutput echoChannel;
    private Pin pin;
    private double inches;
    /**
     * Constructor.
     * @param pingChannel Ping Input Channel.
     */
    public Ultrasonic(Pin pingChannel){
        pin = pingChannel;
        this.pingChannel = gpio.provisionDigitalInputPin(pin);
        this.echoChannel = gpio.provisionDigitalOutputPin(pin);
    }
    
    /**
     * Updates the reading on the Ultrasonic Ping Sensor.
     */    
    public void read(){
        try {
            echoChannel.low();
            Thread.sleep(2);
            echoChannel.high();
            Thread.sleep(5);
            echoChannel.low();
        } catch (InterruptedException ex) {
            Logger.getLogger(Ultrasonic.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.pingChannel = gpio.provisionDigitalInputPin(pin);
        double startTime = System.currentTimeMillis();
        double stopTime = 0;
        do {
            stopTime = System.currentTimeMillis();
            if(System.currentTimeMillis() - startTime >= 40){
                break;
            }
        } while (!pingChannel.isHigh());
        
        if ((stopTime - startTime) <= 40) {
            inches = (stopTime - startTime)/343*2.54/1000;
        }else{
            System.out.println("Timed out");
            inches = -1;
        }
    }
    
    /**
     * Returns the most current value from the ultrasonic sensor in inches since the read() function was called.
     * @return The most current value from the ultrasonic sensor in inches.
     */
    public double getInches(){
        return inches;
    }
}

