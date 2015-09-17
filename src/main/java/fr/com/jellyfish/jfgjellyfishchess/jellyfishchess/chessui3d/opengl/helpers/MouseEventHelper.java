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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.Location3DUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import java.util.Map;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class MouseEventHelper {

    //<editor-fold defaultstate="collapsed" desc="variables">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param uiHelper
     * @param color
     */
    MouseEventHelper(final OPENGLUIHelper uiHelper, final String color) {
        this.uiHelper = uiHelper;
        ChessMoveHelper.getInstance().serOPENGLUIHelper(uiHelper);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * @param squares Map<ChessPositions, ChessSquare>
     */
    void selectedSquareEvent(final Map<ChessPositions, ChessSquare> squares) {

        if (Game3D.getInstance().isReloadingPreviousGame()) {
            return;
        }
        
        if (Mouse.isButtonDown(0)
                && !Game3D.getInstance().isEngineMoving()
                && !Game3D.getInstance().isEngineSearching()
                && this.stopwatch.hasReachedMaxElapsedS()
                && !Game3D.getInstance().isUiCheckmate()
                && !Game3D.getInstance().isEngineCheckmate()) {

            /**
             * If wrong turn.
             */
            if (!this.uiHelper.driver.game.getColorToPLay().equals(
                    Game3D.getInstance().getEngineOponentColorStringValue())) {
                ChessMoveHelper.getInstance().notifyWrongTurn();
                this.stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
                return;
            }

            this.dx = Mouse.getDX();
            this.dy = Mouse.getDY();
            this.x = Mouse.getX();
            this.y = Mouse.getY();
            final Vector3f v = Location3DUtils.getMousePositionIn3dCoordinates(x, y);

            for (Map.Entry<ChessPositions, ChessSquare> s : squares.entrySet()) {

                if (s.getValue().collidesWith(v)) {

                    Game3D.getInstance().setUiMoving(true);

                    if (s.getValue().isOccupied()) {

                        if (s.getValue().getModel() != null
                                && ColorUtils.floatArrayEqual(
                                        s.getValue().getModel().getColor(), 
                                        Game3D.getInstance().getEngineColor())
                                && uiHelper.getBoard().getSelectedSquare() != null
                                && uiHelper.getBoard().getSelectedSquare().getModel() != null
                                && ColorUtils.floatArrayEqual(
                                        uiHelper.getBoard().getSelectedSquare().getModel().getColor(),
                                        Game3D.getInstance().getEngineOponentColor())) {

                            // Take move.
                            ChessMoveHelper.getInstance().doMove(s.getKey(), 
                                    uiHelper.getBoard().getSelectedSquare().CHESS_POSITION, 
                                    s.getValue());
                            break;
                        } else {

                            if ((s.getValue().getModel() != null
                                    && ColorUtils.floatArrayEqual(s.getValue().getModel().getColor(), 
                                            Game3D.getInstance().getEngineColor()))
                                    && uiHelper.getBoard().getSelectedSquare() == null) {
                                break;
                            }
                            // Selecting chess square for move.
                            s.getValue().setColor(UI3DConst.UI_MOVE_SQUARE_COLOR);
                            uiHelper.getBoard().setSelectedSquare(s.getValue());
                            uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.bip);
                        }
                    } else if (uiHelper.getBoard().getSelectedSquare() != null) {
                        // Move without take.
                        ChessMoveHelper.getInstance().doMove(s.getKey(), 
                                uiHelper.getBoard().getSelectedSquare().CHESS_POSITION, 
                                s.getValue());
                        break;
                    }
                } else {
                    s.getValue().setColliding(false);
                }
            }

            // Set correct colors to selected, non-selected & in-check squares
            // and display square information to console.
            if (uiHelper.getBoard().getSelectedSquare() != null) {
                this.uiHelper.getBoard().resetSquareColors();
                this.uiHelper.driver.getWriter().appendText(
                    this.uiHelper.getBoard().getSelectedSquare().toString(), 
                    MessageTypeConst.ERROR, true);
            }

            this.stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
            // Free engine movement to impact UI via engine's response to this move.
            Game3D.getInstance().setUiMoving(false);
        }
    }
    //</editor-fold>

}
