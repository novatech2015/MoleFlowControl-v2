/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleUtil;

/**
 *
 * @author Mr. Mallory
 */
public class TrapezoidalIntegrator {
    
    public double m_accumulation = 0;
    public double m_accumulationLimit = Integer.MAX_VALUE;
    public double m_lastValue = 0;
    public double m_sampleTime = 0.0666;//15 Hz update rate
    
    public TrapezoidalIntegrator(){
        
    }
    
    public void resetAccumulation(){
        m_accumulation = 0;
        m_lastValue = 0;
    }
    
    public double updateAccumulation(double value, double sampleTime){
        m_sampleTime = sampleTime;
        m_accumulation = m_accumulation + ((m_lastValue + value)*sampleTime)/2;
        if (m_accumulation > m_accumulationLimit){
            m_accumulation = m_accumulationLimit; 
        }else if(m_accumulation < -m_accumulationLimit){
            m_accumulation = -m_accumulationLimit;
        }
        return m_accumulation;
    }
    
    public double updateAccumulation(double value){
        m_accumulation = m_accumulation + ((m_lastValue + value)*m_sampleTime)/2;
        if (m_accumulation > m_accumulationLimit){
            m_accumulation = m_accumulationLimit; 
        }else if(m_accumulation < -m_accumulationLimit){
            m_accumulation = -m_accumulationLimit;
        }
        return m_accumulation;
    }
}
