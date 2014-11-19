/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleResources;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import moleUtil.FileLogger;

/**
 *
 * @author mallory
 */
public class moleResources {
    
    public static GpioController gpio;
    public static FileLogger m_log;
    
    public static void init(){
        gpio = GpioFactory.getInstance();
        try {
            m_log = new FileLogger(new Date().toString());
        } catch (IOException ex) {
            Logger.getLogger(moleResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
