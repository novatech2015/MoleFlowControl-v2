/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleflowcontrol;

import moleResources.moleInputManager;
import moleTestProcedures.NineDofTest;
import moleTestProcedures.PWMOutputTest;

/**
 *
 * @author Mr. Mallory
 */
public class MoleFlowControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //moleInputManager.init();
        //PWMOutputTest.init();
        NineDofTest.init();
        
        while(true){
            //moleInputManager.update();
            NineDofTest.run();
        }
    }
    
}
