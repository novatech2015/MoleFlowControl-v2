/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleSensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

/**
 *
 * @author Mr. Mallory
 */
public class BMP180 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_pressureSensor;//Declares the I2CDevice
    private short m_pressure = 0;
    private short m_temperature = 0; 
    
    public BMP180(){
        
    }
    
    public void read(){
        
    }
    
    public double getPressure(){
        return m_pressure;
    }
}
