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
public class PN532 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_transceiver;//Declares the I2CDevice
    
    public PN532(){
        
    }
    
    public void write(String value){
        
    }
    
    public String read(){
        String data = null;
        
        return data;
    }
}
