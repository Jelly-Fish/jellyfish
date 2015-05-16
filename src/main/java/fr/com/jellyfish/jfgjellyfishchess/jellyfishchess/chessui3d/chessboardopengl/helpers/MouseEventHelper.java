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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.Location3DUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class MouseEventHelper {

    /**
     * 
     */
    private final OPENGLUIHelper uiHelper;
    
    /**
     * xyz coordinates onclick.
     */
    private int dx = 0, dy = 0, x = 0, y = 0;
    
    /**
     * Maximum elapsed time in ms between click events.
     */
    private static final double eventMaxInterval = 0.25;
    
    /**
     * Stop watch for prevent event redundancy.
     */
    private StopWatch stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
    
    /**
     * Constructor.
     * @param openglUI
     */
    public MouseEventHelper(final OPENGLUIHelper openglUI) {
        this.uiHelper = openglUI;
    }
    
    /**
     * @param squares 
     */
    public void selectedSquareEvent(final Map<ChessPositions, ChessSquare> squares) {
        
        if (Mouse.isButtonDown(0) && this.stopwatch.hasReachedMaxElapsedMS()) {
            
            this.dx = Mouse.getDX();
            this.dy = Mouse.getDY();
            this.x = Mouse.getX();
            this.y = Mouse.getY();
            final Vector3f v = Location3DUtils.getMousePositionIn3dCoordinates(x, y);

            for (Map.Entry<ChessPositions, ChessSquare> s : squares.entrySet()) {
                
                if (s.getValue().collidesWith(v)) {
                    
                    if (s.getValue().isOccupied()) {
                        
                        if (!ColorUtils.equals(s.getValue().getModel().getColor(), Game3D.engine_oponent_color) &&
                                uiHelper.getBoard().getSelectedSquare() != null &&
                                ColorUtils.equals(uiHelper.getBoard().getSelectedSquare().getModel().getColor(),
                                        Game3D.engine_oponent_color)) {
                            // Take move.
                            doMove(s.getKey(), s.getValue());
                            break;
                        } else {
                            
                            if ((s.getValue().getModel() != null && 
                                !ColorUtils.equals(s.getValue().getModel().getColor(), Game3D.engine_oponent_color)) &&
                                uiHelper.getBoard().getSelectedSquare() == null) {
                                break;
                            }
                            // Selecting chess square for move.
                            s.getValue().setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                            uiHelper.getBoard().setSelectedSquare(s.getValue());
                            uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.bip);
                        }
                    } else {
                        // Move without take.
                        doMove(s.getKey(), s.getValue());
                        break;
                    }
                } else {
                    s.getValue().setColliding(false);
                }
            }
            
            if (uiHelper.getBoard().getSelectedSquare() != null) {
                /*******************************************************************
                 * Set correct colors to selected and non-selected square.        */
                for (Map.Entry<ChessPositions, ChessSquare> s : squares.entrySet()) {
                    if (s.getKey().getStrPositionValue().equals(uiHelper.getBoard().getSelectedSquare().CHESS_POSITION.getStrPositionValue())) {
                        s.getValue().setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                    } else {
                        s.getValue().setColor(s.getValue().getOriginColor());
                    }
                }
            }
            
            this.stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
        }
    }
    
    /**
     * 
     * @param key
     * @param value 
     */
    private void doMove(final ChessPositions key, final ChessSquare value) {

        if (uiHelper.getBoard().getSelectedSquare() != null && 
                uiHelper.getDriver().game.getColorToPLay().toLowerCase().toCharArray()[0] ==
                                uiHelper.getDriver().game.getEngineOponentColor()) {
                            
            try {
                if (uiHelper.getDriver().game.executeMove(uiHelper.getBoard().getSelectedSquare().CHESS_POSITION.getStrPositionValueToLowerCase(),
                        key.getStrPositionValueToLowerCase(), true, false, 'q')) {

                    value.setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                    uiHelper.getBoard().updateSquare(key,
                            uiHelper.getBoard().getSelectedSquare().CHESS_POSITION,
                            Game3D.engine_oponent_color);
                    uiHelper.getBoard().setSelectedSquare(value);
                    // Finally :
                    uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.move);
                } else {
                    // TODO : notify invalid move.
                }
            } catch (final PawnPromotionException ex) {
                Logger.getLogger(MouseEventHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // TODO : Notify wrong turn;
        }
    }
    
}
