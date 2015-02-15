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
 * I2C Photometer.
 * @author Mr. Mallory
 */
public class TSL2561 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_lumSensor;//Declares the I2CDevice
    private double m_lux;
    private int m_channel0, m_channel1;
    
    /**
     * Constructor.
     */
    public TSL2561(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
            
            m_lumSensor = bus.getDevice(0x39);//Gets device on the bus
            System.out.println("Temperature Sensor Connected");
            m_lumSensor.write(0x01 | 0x80, (byte) 0x0A);//Integration of 402 ms with a 1 : 1 scale factor with 1x gain.
            m_lumSensor.write(0x00 | 0x80, (byte) 0x03);//Power Up.
            
        } catch (IOException ex) {
            Logger.getLogger(TMP102.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Updates the registers on the I2C Photometer.
     */    
    public void read(){
        try {
            byte[] buffer = new byte[4];//Declares a buffer which will hold the ITG3200 data
            m_lumSensor.read(0x0C | 0x80, buffer, 0, 4);//Reads data into the buffer
            m_channel0 = ((buffer[1] << 8) & 0xff00) | (buffer[0] & 0xff);
            m_channel1 = ((buffer[3] << 8) & 0xff00) | (buffer[2] & 0xff);
        } catch (IOException ex) {
            Logger.getLogger(TMP102.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the most current value from the photometer's channel 0 register since the read() function was called.
     * @return The most current value from the photometer's channel 0 register.
     */
    public double getChannel0(){
        return m_channel0;
    }
    
    /**
     * Returns the most current value from the photometer's channel 1 register since the read() function was called.
     * @return The most current value from the photometer's channel 1 register.
     */
    public double getChannel1(){
        return m_channel1;
    }
    
    /**
     * Returns the most current lux value calculated from the photometer since the read() function was called.
     * @return The most current lux value calculated from the photometer.
     */
    public double getLux(){

        double ratio, d0, d1;
        // Determine if either sensor saturated (0xFFFF)
        // If so, abandon ship (calculation will not be accurate)
        if ((m_channel0 == 0xFFFF) || (m_channel1 == 0xFFFF)){
            m_lux = 0.0;
            return(-9001.0);
        }
        // Convert from unsigned integer to floating point
        d0 = m_channel0; d1 = m_channel1;
        // We will need the ratio for subsequent calculations
        ratio = d1 / d0;
        // Normalize for integration time
        d0 *= (402.0/402.0);
        d1 *= (402.0/402.0);
        // Determine _lux per datasheet equations:
        if (ratio < 0.5){
            m_lux = 0.0304 * d0 - 0.062 * d0 * Math.pow(ratio,1.4);
            return(m_lux);
        }
        if (ratio < 0.61){
            m_lux = 0.0224 * d0 - 0.031 * d1;
            return(m_lux);
        }
        if (ratio < 0.80){
            m_lux = 0.0128 * d0 - 0.0153 * d1;
            return(m_lux);
        }
        if (ratio < 1.30){
            m_lux = 0.00146 * d0 - 0.00112 * d1;
            return(m_lux);
        } 
        return m_lux;
    }
}
