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
public class HMC5883 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_compass;//Declares the I2CDevice
    private short x_axis = 0, y_axis = 0, z_axis = 0;//Initializes variables
    private final short kGaussPerLSB = 1;//Scalar value for HMC5883L Output
    
    public HMC5883(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");

            m_compass = bus.getDevice(0x3C);//Gets device on the bus 
            //Try 0x1E if not successful
            System.out.println("HMC5883L Connected");

            m_compass.write(0x00, (byte) 0x70);//Configures the BW_Rate to operate at 15 Hertz
            m_compass.write(0x01, (byte) 0x20);//Configures the DATA_FORMAT to operate with a range of +/- 1.3 Gauss
            m_compass.write(0x02, (byte) 0x00);//Configures the POWER_CTL to start measuring continuously
        } catch (IOException e) {
                System.out.println(e);
        }
    }
    
    public void read(){
        try {
            byte[] buffer = new byte[6];//Declares a buffer which will hold the HMC5883L data
            m_compass.read(0x03, buffer, 0, 6);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent MSB and Odd Numbers represent LSB
            x_axis = (short) ((((((short)buffer[0]) << 8) & 0xff00) | buffer[1]&0xff) * kGaussPerLSB);
            y_axis = (short) ((((((short)buffer[2]) << 8) & 0xff00) | buffer[3]&0xff) * kGaussPerLSB);
            z_axis = (short) ((((((short)buffer[4]) << 8) & 0xff00) | buffer[5]&0xff) * kGaussPerLSB);
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
    public short getX(){
        return x_axis;
    }
    
    public short getY(){
        return y_axis;
    }
        
    public short getZ(){
        return z_axis;
    }
}
