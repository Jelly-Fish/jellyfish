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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author thw
 */
class KeyboardEventHelper {

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
     * Stop watch for prevent event redundancy.
     */
    private StopWatch stopwatch = new StopWatch(KeyboardEventHelper.eventMaxInterval);
    
    /**
     * CTRL_Z is pressed down.
     */
    private boolean ctrl_z_pressed = false;
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
    void getKeyInput() {

        boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        boolean space = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
        boolean ctrl_z = (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_Z)) | 
                (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_Z));

        if (esc) {
            uiHelper.running = false;
        }

        if (space) {

            if (keyDown) {
                uiHelper.zoom = uiHelper.zoom > UI3DConst.MAX_ZOOM_OUT
                        ? uiHelper.zoom - (uiHelper.speed / 10.0f) : uiHelper.zoom;
            } else if (keyUp) {
                uiHelper.zoom = uiHelper.zoom < UI3DConst.MAX_ZOOM_IN
                        ? uiHelper.zoom + (uiHelper.speed / 10.0f) : uiHelper.zoom;
            }

            return;
        }

        if (keyDown) {
            uiHelper.g -= uiHelper.speed;
        } else if (keyUp) {
            uiHelper.g += uiHelper.speed;
        }

        if (keyLeft) {
            uiHelper.r -= uiHelper.speed;
        } else if (keyRight) {
            uiHelper.r += uiHelper.speed;
        }

        if (ctrl_z && !ctrl_z_pressed) {
            try {
                uiHelper.driver.removeAllLabels();
                uiHelper.driver.game.executeMoveBack();
                uiHelper.driver.game.executeMoveBack();
                ctrl_z_pressed = true;
            } catch (final FenConvertionException fce) {
                Logger.getLogger(KeyboardEventHelper.class.getName()).log(Level.SEVERE, null, fce);
            } catch (final MoveIndexOutOfBoundsException mioobe) {
                Logger.getLogger(KeyboardEventHelper.class.getName()).log(Level.SEVERE, null, mioobe);
            } catch (final Exception e) {
                Logger.getLogger(KeyboardEventHelper.class.getName()).log(Level.SEVERE, null, e);
            }
        } else if (!ctrl_z) {
            ctrl_z_pressed = false;
        }
    }
    //</editor-fold>

}
