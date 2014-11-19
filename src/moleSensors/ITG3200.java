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
public class ITG3200 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_gyro;//Declares the I2CDevice
    private double x_axis = 0.0, y_axis = 0.0, z_axis = 0.0;//Initializes variables
    private final double kLSBPerDegreePerSecond = 14.375;//Scalar value for ITG3200 Output
    
    public ITG3200(){
	try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
	
            m_gyro = bus.getDevice(0xD0);//Gets device on the bus 
            //Try 0x68 if not successful
            System.out.println("ITG3200 Connected");

            m_gyro.write(0x3E, (byte) 0x80);//Configures the POWER_CTL to start measuring
            m_gyro.write(0x15, (byte) 0x27);//Configures the SMPLRT_DIV to operate at 25 Hertz
            m_gyro.write(0x16, (byte) 0x1C);//Configures the DATA_FORMAT to operate in FULL_RES 
                                            //with a range of +/- 2000 degrees per second
                                            //and a low pass filter at 20Hz 
                                            //and an Internal Sample Rate at 1kHz
            } catch (IOException e) {
		System.out.println(e);
            }
    }
    
    public void read(){
        try {
            byte[] buffer = new byte[6];//Declares a buffer which will hold the ITG3200 data
            m_gyro.read(0x1D, buffer, 0, 6);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent MSB and Odd Numbers represent LSB
            x_axis = (((((short)buffer[0]) << 8) & 0xff00) | buffer[1]&0xff) / kLSBPerDegreePerSecond;
            y_axis = (((((short)buffer[2]) << 8) & 0xff00) | buffer[3]&0xff) / kLSBPerDegreePerSecond;
            z_axis = (((((short)buffer[4]) << 8) & 0xff00) | buffer[5]&0xff) / kLSBPerDegreePerSecond;
        } catch (IOException e){
		System.out.println(e);
	}
    }
    
    //Returns the last value from the x Axis
    public double getX(){
	return x_axis;
    }
    
    //Returns the last value from the y Axis
    public double getY(){
    	return y_axis;
    }
    
    //Returns the last value from the z Axis
    public double getZ(){
    	return z_axis;
    }
}
