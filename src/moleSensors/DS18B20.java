/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleSensors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr. Mallory
 */
public class DS18B20 {
    
    private File temperatureSensor = null;
    private FileInputStream temperatureSensorInput = null;
    private static int sensorNumber = 0;
    private double temp = 0;
    
    public DS18B20(){
        File masterDirectory = new File("/sys/bus/w1/devices/");
        String[] files = masterDirectory.list();
        String[] sensorFiles = new String[files.length];
        int sensorFileSum = 0;
        for(int i = 0; i < files.length; i++){
            if(files[i].startsWith("[28-]")){
                sensorFiles[sensorFileSum] = files[i];
                sensorFileSum++;
            }else if(sensorFileSum == 0){
                System.out.println("Error : Temperature Sensor Offline.\n"
                        + "Abort Temperature Sensing Operation");
            }
        }
        temperatureSensor = new File(sensorFiles[sensorNumber]);
        try {
            temperatureSensorInput = new FileInputStream(temperatureSensor);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DS18B20.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DS18B20 Error : " + ex);
        }
        sensorNumber++;
    }
    
    public double getTemp(){
        return temp;
    }
    
    public void read(){
        try {
            byte[] buffer = new byte[temperatureSensorInput.available()];
            temperatureSensorInput.read(buffer);
            String readout = new String(buffer);
            String tempReading = readout.split("[t=]")[1];
            temp = Integer.parseInt(tempReading)/1000;
        } catch (IOException ex) {
            Logger.getLogger(DS18B20.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DS18B20 Error : " + ex);
        }
    }
}
