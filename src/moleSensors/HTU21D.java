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
 * I2C Hygrometer.
 * @author Mr. Mallory
 */
public class HTU21D {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_humiditySensor;//Declares the I2CDevice
    private double m_humidity = 0.0;
    private double m_temperature = 0.0;
    
    /**
     * Constructor.
     */
    public HTU21D(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
            
            m_humiditySensor = bus.getDevice(0x40);//Gets device on the bus 
            //Try 0x40 if not successful
            System.out.println("ADXL345 Connected");
	} catch (IOException e) {
            System.out.println(e);
	}
    }
    
    /**
     * Updates the registers on the I2C Hygrometer.
     */   
    public void read(){
        try {
            byte[] buffer = new byte[2];//Declares a buffer which will hold the ADXL345 data
            m_humiditySensor.read(0xF3, buffer, 0, 2);//Reads data into the buffer
            Thread.sleep(60);
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            m_temperature = -46.85 + (((((long)buffer[0] << 8) | buffer[1]) & 0xFFFC) / 65536.0 * 175.72);
            
            m_humiditySensor.read(0xF5, buffer, 0, 2);//Reads data into the buffer
            Thread.sleep(55);
            
            m_humidity = -6 + (((((long)Math.abs(buffer[2]) << 8) | (long)Math.abs(buffer[3])) & 0x0000FFFC) / 65536.0 * 125);
	} catch (IOException e){
            System.out.println(e);
	} catch (InterruptedException ex) {
            Logger.getLogger(HTU21D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the most current value from the hygrometer's humidity register since the read() function was called.
     * @return The most current value from the hygrometer's humidity register.
     */
    public double getHumidity(){
        return m_humidity;
    }
    
    /**
     * Returns the most current value from the hygrometer's temperature register since the read() function was called.
     * @return The most current value from the hygrometer's temperature register.
     */
    public double getTemperature(){
        return m_temperature;
    }
}
