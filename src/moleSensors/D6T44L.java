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
 * I2C Thermopile.
 * @author Mr. Mallory
 */
public class D6T44L {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_thermopile;//Declares the I2CDevice
    private int[] thermalArray = new int[16];
    private int referenceTemp;
    private int errorCheckCode;
    
    /**
     * Constructor.
     */
    public D6T44L(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_0);//Connects the bus
            System.out.println("Bus Connected");
            
            m_thermopile = bus.getDevice(0x0A);//Gets device on the bus 
            System.out.println("D6T44L Connected");

        } catch (IOException e) {
            System.out.println(e);
	}
    }
    
    /**
     * Updates the registers on the I2C Thermopile.
     */
    public void read(){
        try {
            byte[] buffer = new byte[6];//Declares a buffer which will hold the ADXL345 data
            m_thermopile.read(0x15, buffer, 0, 35);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            referenceTemp = 256*buffer[1] + buffer[0];
            thermalArray[0] = 256*buffer[3] + buffer[2];
            thermalArray[1] = 256*buffer[5] + buffer[4];
            thermalArray[2] = 256*buffer[7] + buffer[6];
            thermalArray[3] = 256*buffer[9] + buffer[8];
            thermalArray[4] = 256*buffer[11] + buffer[10];
            thermalArray[5] = 256*buffer[13] + buffer[12];
            thermalArray[6] = 256*buffer[15] + buffer[14];
            thermalArray[7] = 256*buffer[17] + buffer[16];
            thermalArray[8] = 256*buffer[19] + buffer[18];
            thermalArray[9] = 256*buffer[21] + buffer[20];
            thermalArray[10] = 256*buffer[23] + buffer[22];
            thermalArray[11] = 256*buffer[25] + buffer[24];
            thermalArray[12] = 256*buffer[27] + buffer[26];
            thermalArray[13] = 256*buffer[29] + buffer[28];
            thermalArray[14] = 256*buffer[31] + buffer[30];
            thermalArray[15] = 256*buffer[33] + buffer[32];
            errorCheckCode = buffer[34];
	} catch (IOException e){
            System.out.println(e);
	}
    }
    
    /**
     * Returns the most current array from the thermopile's registers since the read() function was called.
     * @return The most current value from the barometer's pressure register.
     */
    public int[] getArray(){
        return thermalArray;
    }
    
    /**
     * Returns the most recent error code from thermopile's error register since the read() function was called.
     * @return The most recent error code from thermopile's error register.
     */
    public int getErrorCode(){
        return errorCheckCode;
    }
    
    /**
     * Returns the most current reference temperature since the read() function was called.
     * @return The most current reference temperature since the read() function was called.
     */
    public int getReferenceTemp(){
        return referenceTemp;
    }
}
