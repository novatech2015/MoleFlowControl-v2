/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleSensors;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import static moleResources.moleResources.gpio;

/**
 *
 * @author Mr. Mallory
 */
public class MCP3008 {
    
    private GpioPinDigitalOutput clockPin;
    private GpioPinDigitalInput inputPin;
    private GpioPinDigitalOutput outputPin;
    private GpioPinDigitalOutput slaveSelectPin;
    
    public MCP3008(Pin clock, Pin input, Pin output, Pin slaveSelect){
        clockPin = gpio.provisionDigitalOutputPin(clock);
        inputPin = gpio.provisionDigitalInputPin(input);
        outputPin = gpio.provisionDigitalOutputPin(output);
        slaveSelectPin = gpio.provisionDigitalOutputPin(slaveSelect);
    }
    
    public int readChannel(int channel){
        int output = 0;
        int i;
        int[] inputarray = new int[5];
        
        if(channel < 0 || channel > 7){
            return -1;
        }
        slaveSelectPin.high();
        clockPin.low();
        slaveSelectPin.low();
        
        mcp3008_select_chip(inputarray, channel);
        for(i=0; i<5; i++){
            clocked_write(inputarray[i]);
        }
        
        for(i = 12; i>0; i--){
            if(clocked_read() == 1){
                output += power_of_2(i);
            }
        }
        slaveSelectPin.high();
        output /= 4;
        return output;
    }
    
    private void clocked_write(int value) throws Error{
        if(value == 1){
            outputPin.high();
        }else if(value == 0){
            outputPin.low();
        }else{
            throw(new Error("Hey Stupid. Clocked Write"));
        }
        clockPin.high();
        clockPin.low();
    }
    
    private int clocked_read(){
        clockPin.high();
        clockPin.low();
        return inputPin.isHigh()?1:0;
    }
    
    private void mcp3008_select_chip(int bin[], int channel) throws Error{
        bin[0] = 1; bin[1] = 1;
        int i = 2;
        int[] o = new int[3];
        for(i=2; i >= 0; i--){
            o[i] = (channel % 2);
            channel /= 2;
        }
        int c;
        for (c = i-1; c>=0; c--){
            o[c] = 0;
        }
        c = 2;
        for( i = 0; i<3; i++){
            bin[c] = o[i];
            c++;
        }
    }
    
    private int power_of_2(int exp){
        int output = 1;
        int i;
        for (i = 1; i<=exp; i++) {
            output *= 2;
        }
        return output;
    }
}
