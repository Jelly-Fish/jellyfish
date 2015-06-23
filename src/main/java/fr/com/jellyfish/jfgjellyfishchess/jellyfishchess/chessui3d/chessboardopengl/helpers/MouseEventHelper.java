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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPiece;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import java.awt.Color;
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
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * @param squares
     */
    void selectedSquareEvent(final Map<ChessPositions, ChessSquare> squares) {

        if (Mouse.isButtonDown(0) && !Game3D.engine_moving
                && this.stopwatch.hasReachedMaxElapsedMS() && !Game3D.ui_checkmate) {

            /**
             * If wrong turn.
             */
            if (!this.uiHelper.driver.game.getColorToPLay().equals(Game3D.engine_oponent_color_str_value)) {
                this.notifyWrongTurn();
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

                    Game3D.ui_moving = true;

                    if (s.getValue().isOccupied()) {

                        if (s.getValue().getModel() != null
                                && ColorUtils.equals(s.getValue().getModel().getColor(), Game3D.engine_color)
                                && uiHelper.getBoard().getSelectedSquare() != null
                                && uiHelper.getBoard().getSelectedSquare().getModel() != null
                                && ColorUtils.equals(uiHelper.getBoard().getSelectedSquare().getModel().getColor(),
                                        Game3D.engine_oponent_color)) {

                            // Take move.
                            doMove(s.getKey(), uiHelper.getBoard().getSelectedSquare().CHESS_POSITION, s.getValue(), true);
                            break;
                        } else {

                            if ((s.getValue().getModel() != null
                                    && ColorUtils.equals(s.getValue().getModel().getColor(), Game3D.engine_color))
                                    && uiHelper.getBoard().getSelectedSquare() == null) {
                                break;
                            }
                            // Selecting chess square for move.
                            s.getValue().setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                            uiHelper.getBoard().setSelectedSquare(s.getValue());
                            uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.bip);
                        }
                    } else if (uiHelper.getBoard().getSelectedSquare() != null) {
                        // Move without take.
                        doMove(s.getKey(), uiHelper.getBoard().getSelectedSquare().CHESS_POSITION, s.getValue(), false);
                        break;
                    }
                } else {
                    s.getValue().setColliding(false);
                }
            }

            if (uiHelper.getBoard().getSelectedSquare() != null) {
                /**
                 * *************************************************************
                 * Set correct colors to selected and non-selected square.
                 */
                for (Map.Entry<ChessPositions, ChessSquare> s : squares.entrySet()) {
                    if (s.getKey().getStrPositionValue().equals(
                            uiHelper.getBoard().getSelectedSquare().CHESS_POSITION.getStrPositionValue())) {
                        final java.awt.Color c = new java.awt.Color(20, 220, 255);
                        s.getValue().setColor(ColorUtils.color(c));
                    } else {
                        s.getValue().setColor(s.getValue().getOriginColor());
                    }
                }
            }

            this.stopwatch = new StopWatch(MouseEventHelper.eventMaxInterval);
            // Free engine movement to impact UI via engine's response to this move.
            Game3D.ui_moving = false;
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    void doMove(final ChessPositions key, final ChessPositions posFrom, final ChessSquare value,
            final boolean takeMove) {

        /**
         * Systematically set to false to enable display list deletion in gl
         * main loop.
         */
        Game3D.undoingMoves = false;

        if (uiHelper.getBoard().getSelectedSquare() != null && !Game3D.engine_moving) {

            try {
                if (uiHelper.driver.game.executeMove(
                        uiHelper.getBoard().getSelectedSquare().CHESS_POSITION.getStrPositionValueToLowerCase(),
                        key.getStrPositionValueToLowerCase(), true, false, 'q')) {

                    /**
                     * Append move to queue for undoing.
                     */
                    Move m;
                    if (value.getModel() != null) {
                        m = new Move(posFrom, key, false, uiHelper.getBoard().getSelectedSquare().getModel(),
                                value.getModel());
                    } else {
                        m = new Move(posFrom, key, false, uiHelper.getBoard().getSelectedSquare().getModel());
                    }
                    uiHelper.driver.moveQueue.appendToEnd(m);

                    value.setColor(ColorUtils.color(new java.awt.Color(20, 220, 255)));
                    uiHelper.getBoard().updateSquare(key,
                            uiHelper.getBoard().getSelectedSquare().CHESS_POSITION,
                            Game3D.engine_oponent_color);

                    // Finally :
                    uiHelper.getBoard().setSelectedSquare(value);
                    uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.move);
                } else {
                    throw new InvalidMoveException(String.format("%s %s-%s is not a valid chess move.\n",
                            uiHelper.getBoard().getSelectedSquare().getModel().getType().toString(),
                            uiHelper.getBoard().getSelectedSquare().CHESS_POSITION.getStrPositionValue(),
                            key.getStrPositionValueToLowerCase()));
                }
            } catch (final PawnPromotionException ex) {
                Logger.getLogger(MouseEventHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (final InvalidMoveException ex) {
                this.uiHelper.driver.getWriter().appendText(ex.getMessage(), MessageTypeConst.ERROR, true);
                Logger.getLogger(MouseEventHelper.class.getName()).log(Level.WARNING, null, ex);
            }

        } else {
            this.notifyWrongTurn();
        }
    }

    /**
     * Notify Console for a 'wrong turn' error, oponent side must play first.
     */
    private void notifyWrongTurn() {
        this.uiHelper.driver.getWriter().appendText(
                String.format("It is %s's side to take a move...\n", Game3D.engine_color_str_value),
                MessageTypeConst.ERROR, true);
    }
    //</editor-fold>

}
