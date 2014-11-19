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

/**
 *
 * @author Mr. Mallory
 */
public class HTU21D {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_humiditySensor;//Declares the I2CDevice
    private double m_humidity = 0.0;
    private double m_temperature = 0.0;
    
    public HTU21D(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
            
            m_humiditySensor = bus.getDevice(0x80);//Gets device on the bus 
            //Try 0x40 if not successful
            System.out.println("ADXL345 Connected");

            byte userRegister = (byte) (m_humiditySensor.read(0xE7) & 0x7e);//Turn off the resolution bits
            byte resolution        = 0x00;//12 bit Resolution
            userRegister |= resolution;
            
            m_humiditySensor.write(0xE6, userRegister);//Sets the resolution to 12 bits
	} catch (IOException e) {
            System.out.println(e);
	}
    }
    
    public void read(){
        try {
            byte[] buffer = new byte[4];//Declares a buffer which will hold the ADXL345 data
            m_humiditySensor.read(0xF5, buffer, 0, 4);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            m_humidity  = (((((int) buffer[0] << 8) | (int) buffer[1]) & 0xFFFC) / 65536 * 125) - 6;
            m_temperature = (((((int) buffer[0] << 8) | (int) buffer[1]) & 0xFFFC) / 65536 * 175.72) - 46.85;
	} catch (IOException e){
            System.out.println(e);
	}
    }
    
    public double getHumidity(){
        return m_humidity;
    }
    
    public double getTemperature(){
        return m_temperature;
    }
}
