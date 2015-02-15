/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleTestProcedures;

import moleSensors.ADXL345;
import moleSensors.HMC5883;
import moleSensors.ITG3200;

/**
 * Test Functions for Assessing the Performance of the Sparkfun 9Dof Sensor Stick.
 * @author Mr. Mallory
 */
public class NineDofTest {
    
    private static ADXL345 m_accel;
    private static ITG3200 m_gyro;
    private static HMC5883 m_compass;
    
    /**
     * Initializes the sensors on the 9Dof Sensor Stick.
     */
    public static void init(){
        m_accel = new ADXL345();
        m_gyro = new ITG3200();
        m_compass = new HMC5883();
    }
    
    /**
     * Updates the registers on the 9Dof Sensor Stick.
     */
    public static void run(){
        update();
    }
    
    private static void update(){
        m_accel.read();
        System.out.println("X Accel :" + m_accel.getX());
        System.out.println("Y Accel :" + m_accel.getY());
        System.out.println("Z Accel :" + m_accel.getZ());
        
        m_gyro.read();
        System.out.println("X Gyro :" + m_gyro.getX());
        System.out.println("Y Gyro :" + m_gyro.getY());
        System.out.println("Z Gyro :" + m_gyro.getZ());
        
        m_compass.read();
        System.out.println("X Compass :" + m_compass.getX());
        System.out.println("Y Compass :" + m_compass.getY());
        System.out.println("Z Compass :" + m_compass.getZ());
    }
    
    /**
     * Logs the output of the sensor to a file.
     */
    public static void log(){
        
    }
}
