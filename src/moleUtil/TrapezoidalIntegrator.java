/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleUtil;

/**
 * Trapezoidal Integrator.
 * @author Mr. Mallory
 */
public class TrapezoidalIntegrator {
    
    private double m_accumulation = 0;
    private double m_accumulationLimit = Integer.MAX_VALUE;
    private double m_lastValue = 0;
    private double m_sampleTime = 0.0666;//15 Hz update rate
    
    /**
     * Constructor.
     */
    public TrapezoidalIntegrator(){
        
    }
    
    /**
     * Resets the current accumulation.
     */
    public void resetAccumulation(){
        m_accumulation = 0;
        m_lastValue = 0;
    }
    
    /**
     * Updates and returns the current accumulation.
     * @param value The y term in the integrator.
     * @param sampleTime The dx term in the integrator.
     * @return The current accumulation.
     */
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
    
    /**
     * Updates and returns the current accumulation with either the default time of 0.06666 seconds or the most recently changed sample time.
     * @param value The y term in the integrator.
     * @return The current accumulation.
     */
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
