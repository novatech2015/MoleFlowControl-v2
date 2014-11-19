/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleTestProcedures;

import moleSensors.ADXL345;

/**
 *
 * @author Mr. Mallory
 */
public class NineDofTest {
    
    private static ADXL345 m_accel;
    
    public static void init(){
        m_accel = new ADXL345();
    }
    
    public static void run(){
        updateAccel();
    }
    
    public static void updateAccel(){
        m_accel.read();
        System.out.println("X :" + m_accel.getX());
        System.out.println("Y :" + m_accel.getY());
        System.out.println("Z :" + m_accel.getZ());
    }
    
    public static void updateGyro(){
        
    }
    
    public static void updateCompass(){
        
    }
    
    public static void log(){
        
    }
}
