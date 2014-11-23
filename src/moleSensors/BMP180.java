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
 *
 * @author Mr. Mallory
 */
public class BMP180 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_pressureSensor;//Declares the I2CDevice
    private long m_pressure = 0;
    private long m_temperature = 0; 
    private short AC1, AC2, AC3, B1, B2, MB, MC, MD;
    private int AC4, AC5, AC6;
    private long X1, X2, X3, B3, B4, B5, B6, B7;
    
    public BMP180(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_0);//Connects the bus
            System.out.println("Bus Connected");
            
            m_pressureSensor = bus.getDevice(0xEF);//Gets device on the bus 
            //Try 0xEE if not successful
            //Try 0x77 if not successful
            System.out.println("BMP180 Connected");
            
            m_pressureSensor.write(0xE0, (byte) 0xB6);//Reset
            
            byte[] buffer = new byte[22];//Declares a buffer which will hold the BMP180 data
            m_pressureSensor.read(0xAA, buffer, 0, 22);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            AC1 = (short) (((((short)buffer[1]) << 8) & 0xff00) | buffer[0]&0xff);//408
            AC2 = (short) (((((short)buffer[3]) << 8) & 0xff00) | buffer[2]&0xff);//-72
            AC3 = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//-14383
            AC4 = (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//32741
            AC5 = (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//32757
            AC6 = (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//23153
            B1 = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//6190
            B2 = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//4
            MB = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//-32768
            MC = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//-8711
            MD = (short) (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff);//2868
            
            
            m_pressureSensor.write(0xF4, (byte) 0x2E);//Power on Calibrate
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void read(){
        readTemperature();
        readPressure();
    }
    
    public double getPressure(){
        return m_pressure;
    }
    
    public double getTemperature(){
        return m_temperature;
    }
    
    private void readTemperature(){
        try {
            m_pressureSensor.write(0xF4, (byte) 0x2E);//Configure Data Register
            Thread.sleep(5);
            byte[] buffer = new byte[2];//Declares a buffer which will hold the BMP180 data
            m_pressureSensor.read(0xF6, buffer, 0, 2);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            long uncompensatedTemperature = buffer[0]<<8 + buffer[1];
            X1 = (uncompensatedTemperature - AC6) * AC5 / 32768;
            X2 = MC * 2048 / (X1 + MD);
            B5 = X1 + X2;
            m_temperature = (B5 + 8) / 16;
	} catch (IOException e){
            System.out.println(e);
	} catch (InterruptedException ex) {
            Logger.getLogger(BMP180.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readPressure(){
        try {
            m_pressureSensor.write(0xF4, (byte) 0x34);//Configure Data Register
            Thread.sleep(5);
            byte[] buffer = new byte[3];//Declares a buffer which will hold the BMP180 data
            m_pressureSensor.read(0xF6, buffer, 0, 3);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            long uncompensatedPressure = (buffer[0]<<16 + buffer[1]<<8 + buffer[2]) >> (8-0);
            B6 = B5 - 4000;
            X1 = (B2 * (B6 * B6 / 4096))/2048;
            X2 = AC2 * B6 / 2048;
            X3 = X1 + X2;
            B3 = (((AC1*4 + X3) << 0) + 2) / 4;
            X1 = AC3 * B6 / 8192;
            X2 = (B1 * (B6 * B6 / 4096)) / 262144;
            X3 = ((X1 + X2) + 2 ) / 4;
            B4 = (AC4 * (X3 + 32768)) / 32768;
            B7 = (uncompensatedPressure - B3) * (50000 >> 0);
            if (B7 < 0x80000000){
                m_pressure = ((B7 * 2) / B4);
            }else{
                m_pressure = (B7 / B4) * 2;
            }
            X1 = (m_pressure / 256) * (m_pressure / 256);
            X1 = (X1 * 3038) /  262144;
            X2 = (-7357 * m_pressure) / 262144;
            m_pressure = m_pressure + (X1 + X2 + 3791) / 16;
        } catch (IOException e){
            System.out.println(e);
	} catch (InterruptedException ex) {
            Logger.getLogger(BMP180.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
