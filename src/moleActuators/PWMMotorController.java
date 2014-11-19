/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleActuators;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import static moleResources.moleResources.gpio;

/**
 *
 * @author Mr. Mallory
 */
public class PWMMotorController {
    
    private int cycle = 500;//500 Hz Default
    private double power = 0;
    private int currentCycle = 0;
    private MotorController motorController;
    private long lastTime = 0;
    private boolean running = false;
    private GpioPinDigitalOutput signalPin;
    
    public PWMMotorController(Pin pinNumber){
        signalPin = gpio.provisionDigitalOutputPin(pinNumber);
        
        motorController = new MotorController();
        motorController.start();
        
    }
    
    public void setPower(double power){
        power = getSign(power) * Math.min(Math.abs(power),1);//Caps power at 1 / -1
        if(power > 0){
            cycle = 500;
        }else if(power < 0){
            cycle = 1000;
        }else{
            
        }
        this.power = power;
    }
    
    public void start(){
        currentCycle = cycle;
        lastTime = System.nanoTime();
        running = true;
    }
    
    public void stop(){
        running = false;
    }
    
    private int getSign(double value){
        int sign = 1;
        if(value > 0){
            sign = 1;
        }else if (value == 0){
            sign =  0;
        }else if (value < 0){
            sign = -1;
        }
        return sign;
    }
    
    public boolean isRunning(){
        return running;
    }

    
    private class MotorController extends Thread{
        
        @Override
        public void run(){
            while(true){
                if(running){
                    int elapsedTime = (int) (System.nanoTime() - lastTime)/1000;
                    lastTime = System.nanoTime();
                    if(currentCycle*100/cycle > power*100){
                        signalPin.low();
                    }else{
                        signalPin.high();
                    }
                    currentCycle -= elapsedTime;
                    if(currentCycle <= 0){
                        currentCycle += cycle;
                    }
                }
                            
            }
        }
    }
    
    
}
