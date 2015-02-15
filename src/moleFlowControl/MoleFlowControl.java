/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package moleFlowControl;

import moleResources.MoleInputManager;
import moleTestProcedures.NineDofTest;
import moleTestProcedures.PWMOutputTest;

/**
 * Master Class for Basic Logic Control.
 * @author Mr. Mallory
 */
public class MoleFlowControl {

    /**
     * Main method used for execution at runtime.
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
