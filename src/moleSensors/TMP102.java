/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleSensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr. Mallory
 */
public class TMP102 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_tempSensor;//Declares the I2CDevice
    private double m_temp;
    
    public TMP102(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
            
            m_tempSensor = bus.getDevice(0x48);//Gets device on the bus
            System.out.println("Temperature Sensor Connected");
        } catch (IOException ex) {
            Logger.getLogger(TMP102.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void read(){
        try {
            m_temp = m_tempSensor.read(0);
        } catch (IOException ex) {
            Logger.getLogger(TMP102.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getTemp(){
        return m_temp;
    }
}
