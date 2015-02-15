/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleTestProcedures;

import moleSensors.HTU21D;

/**
 * Test Functions for Assessing the Performance of the HTU21D Hygrometer.
 * @author Mr. Mallory
 */
public class HTUSensorTest {

    private static HTU21D m_htu21d;
    
    /**
     * Initializes the HTU21D Hygrometer.
     */
    public static void init(){
        m_htu21d = new HTU21D();
    }
    
    /**
     * Updates the registers on the HTU21D Hygrometer.
     */
    public static void run(){
        update();
    }
    
    private static void update(){
        m_htu21d.read();
        System.out.println("HTU21D Humidity :" + m_htu21d.getHumidity());
        System.out.println("HTU21D Temperature :" + m_htu21d.getTemperature());
        
    }
    
    /**
     * Logs the output of the sensor to a file.
     */
    public static void log(){
        
    }
}
