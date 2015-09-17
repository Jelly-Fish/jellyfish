/**
 * *****************************************************************************
 * Copyright (c) 2015, Thomas.H Warner. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DCoordinateConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author thw
 */
public class KeyboardEventHelper {

    //<editor-fold defaultstate="collapsed" desc="variables">
    /**
     * UI helper instance reference.
     */
    private final OPENGLUIHelper uiHelper;

    /**
     * Maximum elapsed time in keyboard events.
     */
    private static final double eventMaxInterval = 0.45;

    /**
     * Stop watch for preventing event redundancy.
     */
    private StopWatch stopwatch = new StopWatch(KeyboardEventHelper.eventMaxInterval);
    
    /**
     * CTRL_Z is pressed.
     */
    private boolean ctrl_z_pressed = false;
    
    /**
     * h is pressed.
     */
    private boolean h_pressed = true;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param openglUI
     */
    KeyboardEventHelper(final OPENGLUIHelper openglUI) {
        this.uiHelper = openglUI;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Process keyboard input.
     */
    public void processKeyInput() {
        
        if (Game3D.getInstance().isReloadingPreviousGame()) {
            return;
        }

        boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        boolean space = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
        boolean ctrl_z = (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_Z)) | 
                (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_Z));
        boolean h = Keyboard.isKeyDown(Keyboard.KEY_H);
        boolean ctrl_minus = (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) |
                (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT));
        boolean ctrl_add = (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_ADD)) |
                (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_ADD));

        /**
         * Quit application if esc has been pressed and engine has finished moving.
         * this last condition is very important. In the case where games are 
         * reloaded, engine must have played in order to serialize it's move.
         */
        if (esc && !Game3D.getInstance().isEngineSearching()) {
            uiHelper.setRunning(false);
            return;
        }

        /**
         * Zooming in & out.
         */
        if (space) {
            if (keyDown) {
                uiHelper.zoom = uiHelper.zoom > UI3DCoordinateConst.MAX_ZOOM_OUT
                        ? uiHelper.zoom - (uiHelper.speed / 10.0f) : uiHelper.zoom;
            } else if (keyUp) {
                uiHelper.zoom = uiHelper.zoom < UI3DCoordinateConst.MAX_ZOOM_IN
                        ? uiHelper.zoom + (uiHelper.speed / 10.0f) : uiHelper.zoom;
            }
            return;
        }

        /**
         * Key arrow events for moving view.
         */
        if (keyDown) { uiHelper.g -= uiHelper.speed; } 
        else if (keyUp) { uiHelper.g += uiHelper.speed; }
        if (keyLeft) { uiHelper.r -= uiHelper.speed; } 
        else if (keyRight) { uiHelper.r += uiHelper.speed; }

        /**
         * Undo ctrl_z event.
         */
        if (this.uiHelper.driver.game.getMoveCount() > 0 &&
                ((ctrl_z && !ctrl_z_pressed) || KeyboardEventHelper.ConsoleEvents.force_ctrl_z)) {
            
            try {
                
                if (Game3D.getInstance().getEngineColorStringValue().equals(UI3DConst.COLOR_W_STR_VALUE) && 
                        this.uiHelper.driver.game.getMoveCount() == 1) {
                    return;
                }
                
                Game3D.getInstance().setUndoingMoves(true);
                uiHelper.driver.game.executeMoveBack();
                uiHelper.driver.game.executeMoveBack();
                ctrl_z_pressed = true;
                KeyboardEventHelper.ConsoleEvents.force_ctrl_z = false;
                Game3D.getInstance().setUiCheck(false);
                Game3D.getInstance().setUiCheckmate(false);
                this.uiHelper.getBoard().resetAllChessSquareBackgroundColors();
            } catch (final FenConvertionException fce) {
                Logger.getLogger(KeyboardEventHelper.class.getName()).log(Level.SEVERE, null, fce);
            } catch (final MoveIndexOutOfBoundsException mioobe) {
                Logger.getLogger(KeyboardEventHelper.class.getName()).log(Level.SEVERE, null, mioobe);
            }
        } else if (!ctrl_z && !KeyboardEventHelper.ConsoleEvents.force_ctrl_z) {
            ctrl_z_pressed = false;
        }
        
        /**
         * Hint result.
         */
        if (!Game3D.getInstance().isUiCheckmate() && !Game3D.getInstance().isEngineCheckmate() &&
                ((h && !h_pressed) || KeyboardEventHelper.ConsoleEvents.force_h)) {
            
            Game3D.getInstance().setDisplayHint(true);
            h_pressed = true;
            KeyboardEventHelper.ConsoleEvents.force_h = false;
            this.uiHelper.driver.stopHintSearch(Game3D.getInstance().isEnableHints());
            return;
        } else if (!h && !KeyboardEventHelper.ConsoleEvents.force_h) {
            h_pressed = false;
        }
        
        /**
         * Search depth modifications.
         */
        if (ctrl_add && !Game3D.getInstance().isEngineSearching() && 
                stopwatch.hasReachedMaxElapsedS()) {
            this.uiHelper.console.increaseDifficulty();
            stopwatch.start();
            return;
        }
        if (ctrl_minus && !Game3D.getInstance().isEngineSearching() && 
                stopwatch.hasReachedMaxElapsedS()) {   
            this.uiHelper.console.decreaseDifficulty();
            stopwatch.start();
        }
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inner public static class ConsoleEvents">
    /**
     * Events sent from console or other GUI that mimic keybord events.
     */
    public static class ConsoleEvents {
        
        //<editor-fold defaultstate="collapsed" desc="static variables">
        /**
         * Mimic this ctrl_z action from another class in an open gl context.
         * Manly used by a GUI.
         */
        public static boolean force_ctrl_z = false;
        
        /**
         * Mimic this ctrl_space from another class in an open gl context.
         * Manly used by a GUI.
         */
        public static boolean force_h = false;
        //</editor-fold>
        
    }
    //</editor-fold>
    
}
