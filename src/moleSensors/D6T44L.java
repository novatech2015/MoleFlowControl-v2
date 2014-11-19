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
public class D6T44L {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_thermopile;//Declares the I2CDevice
    private int[] thermalArray = new int[16];
    
    public D6T44L(){
        
    }
    
    public void read(){
        
    }
    
    public int[] getArray(){
        return thermalArray;
    }
}
