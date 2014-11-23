/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleSensors;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import java.util.logging.Level;
import java.util.logging.Logger;
import static moleResources.moleResources.gpio;

/**
 *
 * @author mallory
 */
public class Ultrasonic {
    
    private GpioPinDigitalInput pingChannel;
    private GpioPinDigitalOutput echoChannel;
    
    public Ultrasonic(Pin pingChannel, Pin echoChannel){
        this.pingChannel = gpio.provisionDigitalInputPin(pingChannel);
        this.echoChannel = gpio.provisionDigitalOutputPin(echoChannel);
    }
    
    public double getRange(){
        try {
            echoChannel.high();
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(Ultrasonic.class.getName()).log(Level.SEVERE, null, ex);
        }
        echoChannel.low();
        double startTime = System.currentTimeMillis();
        double stopTime = 0;
        do {
            stopTime = System.currentTimeMillis();
            if(System.currentTimeMillis() - startTime >= 40){
                break;
            }
        } while (!pingChannel.isHigh());
        
        double result;
        
        if ((stopTime - startTime) <= 38) {
            result = (stopTime - startTime) * 165.7;
        }else{
            System.out.println("Timed out");
            result = -1;
        }
        
        return result;
    }
}

