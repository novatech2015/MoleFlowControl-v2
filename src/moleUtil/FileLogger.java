/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File Logger.
 * @author Mr. Mallory
 */
public class FileLogger {
    
    private File m_file;
    private FileWriter m_fileOut;
    
    /**
     * Constructor.
     * @param name Name of File
     * @throws IOException 
     */
    public FileLogger(String name) throws IOException{
        m_file = new File(name);
        if(!m_file.exists()){
            m_file.createNewFile();
        }else{
            m_file.renameTo(new File(name + ".backup"));
        }
        
        m_fileOut = new FileWriter(m_file);
    }
    
    /**
     * Writes string to file.
     * @param value The string to write to the file.
     * @throws IOException 
     */
    public void write(String value) throws IOException{
        m_fileOut.write(value);
        m_fileOut.flush();
    }
    
    /**
     * Closes the file connection.
     * @throws IOException 
     */
    public void close() throws IOException{
        m_fileOut.close();
    }
}
