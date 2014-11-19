package moleSensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
/**
 *
 * @author Mr. Mallory
 */

public class ADXL345 {
	
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_accel;//Declares the I2CDevice
    private double x_axis = 0.0, y_axis = 0.0, z_axis = 0.0;//Initializes variables
    private final double kGsPerLSB = 0.004;//Scalar value for ADXL345 Output

    public ADXL345(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_0);//Connects the bus
            System.out.println("Bus Connected");
            
            m_accel = bus.getDevice(0xA6);//Gets device on the bus 
            //Try 0x53 if not successful
            //Try 0x3A if not successful
            //Try 0x1D if not successful
            System.out.println("ADXL345 Connected");

            m_accel.write(0x2C, (byte) 0x08);//Configures the BW_Rate to operate at 25 Hertz
            m_accel.write(0x31, (byte) 0x0B);//Configures the DATA_FORMAT to operate in FULL_RES with a range of +/- 16g
            m_accel.write(0x2D, (byte) 0x08);//Configures the POWER_CTL to start measuring
	} catch (IOException e) {
            System.out.println(e);
	}
    }

    public void read(){
        try {
            byte[] buffer = new byte[6];//Declares a buffer which will hold the ADXL345 data
            m_accel.read(0x32, buffer, 0, 6);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            x_axis = (((((short)buffer[1]) << 8) & 0xff00) | buffer[0]&0xff) * kGsPerLSB;
            y_axis = (((((short)buffer[3]) << 8) & 0xff00) | buffer[2]&0xff) * kGsPerLSB;
            z_axis = (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff) * kGsPerLSB;
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
