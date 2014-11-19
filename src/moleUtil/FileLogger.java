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
 *
 * @author Mr. Mallory
 */
public class FileLogger {
    
    File m_file;
    FileWriter m_fileOut;
    public FileLogger(String name) throws IOException{
        m_file = new File(name);
        if(!m_file.exists()){
            m_file.createNewFile();
        }else{
            m_file.renameTo(new File(name + ".backup"));
        }
        
        m_fileOut = new FileWriter(m_file);
    }
    
    public void write(String value) throws IOException{
        m_fileOut.write(value);
        m_fileOut.flush();
    }
    
    public void close() throws IOException{
        m_fileOut.close();
    }
}
