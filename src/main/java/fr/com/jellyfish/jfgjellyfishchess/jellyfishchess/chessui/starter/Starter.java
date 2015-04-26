/*******************************************************************************
 * Copyright (c) 2014, Thomas.H Warner.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors 
 * may be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 *******************************************************************************/

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.starter;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui.MainUi;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui.MainUiDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.uistatus.StatusIO;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Thomas.H Warner 2014
 */
public class Starter {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Logger.getLogger(Starter.class.getName()).log(Level.INFO, null, "UI tendency: " + args[0]);

        if (args[0].equals("3d")) {
            start3DUI();
        } else if (args[0].equals("2d")) {
            start2DUI();
        } else {
            start2DUI();
        }
    }
    
    /**
     * Start Swing type 2d GUI.
     */
    private static void start2DUI() {
        
        // <editor-fold defaultstate="collapsed" desc="UI Manager">    
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        //</editor-fold>
        
        // Build serializer StatusIO class. This will deserialize user settings.
        StatusIO statusIO = new StatusIO(); 
        
        MainUi ui = new MainUi(statusIO.getUserSettings().isConsoleVisible());
        MainUiDriver driver = new MainUiDriver(ui, statusIO, true, GameTypeConst.CHESS_GAME);
        ui.setUiDriver(driver);
        
        // Center frame :
        ui.setLocationRelativeTo(null);
        ui.setVisible(true);
        
        // Finnaly deserialize previous game.
        driver.loadGame(false);
    }
    
    /**
     * Start OpenGL type 3d GUI.
     */
    private static void start3DUI() {
        
    }
    
}
