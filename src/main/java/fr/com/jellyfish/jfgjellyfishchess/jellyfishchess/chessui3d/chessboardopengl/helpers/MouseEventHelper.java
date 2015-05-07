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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.Location3DUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.time.StopWatch;
import java.util.Collection;
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
    private final OPENGLUIHelper openglUI;
    
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
        this.openglUI = openglUI;
    }
    
    /**
     * @param squares 
     */
    public void selectedSquareEvent(final Collection<ChessSquare> squares) {
        
        if (Mouse.isButtonDown(0) && this.stopwatch.hasReachedMaxElapsedMS()) {
            
            this.dx = Mouse.getDX();
            this.dy = Mouse.getDY();
            this.x = Mouse.getX();
            this.y = Mouse.getY();
            final Vector3f v = Location3DUtils.getMousePositionIn3dCoordinates(x, y);

            boolean found = false;
            for (ChessSquare s : squares) {
                if (!found && s.collidesWith(v)) {
                    s.setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                    Logger.getLogger(MouseEventHelper.class.getName()).log(Level.INFO,
                            "selected position: {0}", s.CHESS_POSITION.getStrPositionValue());
                    found = true;
                    
                    openglUI.soundManager.playEffect(UI3DConst.StaticSoundVars.bip);
                    
                } else {
                    s.setColliding(false);
                    s.setColor(s.getOriginColor());
                }
            }
            
            this.stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
        }
    }
    
}
