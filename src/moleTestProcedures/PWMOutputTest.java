/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleTestProcedures;

import com.pi4j.io.gpio.RaspiPin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import moleActuators.PWMMotorController;
import moleResources.moleInputManager;
import static moleResources.moleResources.m_log;

/**
 *
 * @author mallory
 */
public class PWMOutputTest {
    
    private static PWMMotorController testPWM;
    private static double lastPower;
    private static double power;
    
    
    public static void init(){
        testPWM = new PWMMotorController(RaspiPin.GPIO_04);
    }
    
    public static void run(){
        power = (double) moleInputManager.getLastValue();
        if(power == -128){
            testPWM.stop();
            power = 0;
        }else if(!testPWM.isRunning()){
            testPWM.start();
        }
        power /= 128.0;
        if(testPWM.isRunning()){
            testPWM.setPower(power);
        }
        report();
        lastPower = power;
    }
    
    public static void report(){
        try {
            m_log.write(System.currentTimeMillis() + "\t PWM Signal Sent\t");
            if(power != lastPower){
                m_log.write(System.currentTimeMillis() + "\t New PWM Output : \t" + power);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(PWMOutputTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
