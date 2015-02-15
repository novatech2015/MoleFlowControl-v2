/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleTestProcedures;

import com.pi4j.io.gpio.RaspiPin;
import moleSensors.PN532;

/**
 * Test Functions for Assessing the Performance of the PN532 RFID Transceiver.
 * @author Mr. Mallory
 */
public class NFCDataWriteTest {
    
    private static PN532 m_pn532;
    
    /**
     * Initializes the PN532 RFID Transceiver.
     */
    public static void init(){
        m_pn532 = new PN532(RaspiPin.GPIO_22);
    }
    
    /**
     * Writes a canned message to an attached MiFare RFID Sticker.
     */
    public static void write(){
        m_pn532.write("XXXX");
    }
    
    /**
     * Reads a message from an attached MiFare RFID Sticker.
     */
    public static void read(){
        System.out.println("RFID Sticker Message :" + m_pn532.read());
    }
    
}
