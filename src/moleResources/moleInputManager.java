/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moleResources;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mallory
 */
public class moleInputManager {
    
    private static byte lastValue;
    private static Socket m_socket;
    private static InputStream m_in;
    
    public static void init(){
        try {
            m_socket = new Socket("192.168.1.200", 8720);
            m_in = m_socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(moleInputManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void update(){
        byte[] buffer = new byte[1];
        try {
            m_in.read(buffer, m_in.available()-1, 1);
        } catch (IOException ex) {
            Logger.getLogger(moleInputManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastValue = buffer[0];
    }
    
    public static byte getLastValue(){
        return lastValue;
    }
}
