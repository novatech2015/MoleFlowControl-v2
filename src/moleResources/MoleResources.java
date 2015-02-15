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
 * Static Object Container.
 * @author Mr. Mallory
 */
public class MoleResources {
    
    /**
     * Default GPIO Controller.
     */
    public static GpioController gpio;
    /**
     * Default FileLogger.
     */
    public static FileLogger m_log;
    
    /**
     * Must be run before other members of the class is ran, Initializes the GPIO and FileLogger.
     */
    public static void init(){
        gpio = GpioFactory.getInstance();
        try {
            m_log = new FileLogger(new Date().toString());
        } catch (IOException ex) {
            Logger.getLogger(MoleResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
