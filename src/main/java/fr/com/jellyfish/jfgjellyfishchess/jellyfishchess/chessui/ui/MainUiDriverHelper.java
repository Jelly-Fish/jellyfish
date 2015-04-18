/**
 * *****************************************************************************
 * Copyright (c) 2014, Thomas.H Warner. All rights reserved.
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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.MessageConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.ImageIconPool;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.SoundPlayer;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidChessPositionException;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Static methods for UI driver extended class.
 *
 * @author Thomas.H Warner 2014
 */
public class MainUiDriverHelper {

    //<editor-fold defaultstate="collapsed" desc="Private vars">   
    /**
     * Driver ref.
     */
    private final MainUiDriver driver;

    /**
     * UI ref.
     */
    private final MainUi ui;
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Constructor">   
    /**
     * Constructor.
     *
     * @param driver
     */
    public MainUiDriverHelper(final MainUiDriver driver) {
        this.driver = driver;
        this.ui = driver.getUi();
    }
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Methods"> 
    /**
     * Reset chess square coordiante labelling visibility.
     *
     * @param display
     */
    void resetChessSquareLabelling(final boolean display) {

        for (ChessSquare square : this.driver.getSquareHashMap().values()) {
            // square cannot be null. By default ChessSquare class constructor 
            // sets bool labeled to false.
            if (square.isLabeled()) {
                // If ChessSquare extends JButton isLabeled equal true, label will
                // be at index 0 of component's components array :
                square.getComponent(0).setVisible(display);
                square.setLabelVisible(display);
            }
        }
    }

    /**
     * Repaint all chess squares.
     *
     * @param squares
     */
    void repaintChessSquares(ChessSquare ... squares) {
        for (ChessSquare square : squares) {
            square.repaint();
        }
        ui.getBoardContainer().updateUI();
        ui.repaint();
        ui.focus();
    }

    /**
     * Repaint all the chess board's squares.
     */
    void repaintAllChessSquares() {
        for (ChessSquare square : this.driver.getSquareHashMap().values()) {
            square.repaint();
        }
        ui.getBoardContainer().updateUI();
        ui.repaint();
        ui.focus();
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path
     * @return ImageIcon
     */
    ImageIcon createImageIcon(final String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }

    /**
     * Move A chess board icon to update GUI.
     *
     * @param icon
     * @param posFrom
     * @param posTo
     */
    void moveChessSquareIcon(final ImageIcon icon, final ChessSquare posFrom, final ChessSquare posTo) {

        posFrom.setIcon(null);
        posTo.setIcon(icon);

        // If in process of reloading a game, no need to execute sound and
        // perform repinting.
        if (!this.driver.isCurrentlyReloadingPreviousGame()) {

            posFrom.repaint();
            posTo.repaint();
            // Play move sound :
            this.driver.getSoundPlayer().playSound(SoundPlayer.MOVE);
        }
    }
    
    /**
     * Update Gui for Pawn class promotion. See moveChessSquareIcon(...), analog
     * method but for general purposes. Here icon must changed to be synchronised 
     * with the game.
     * @param posFrom
     * @param posTo
     * @param icon 
     */
    void applyGUIPawnPromotion(final String posFrom, final String posTo, final ImageIcon icon) {
        
        driver.getSquareHashMap().get(posFrom).setIcon(null);
        driver.getSquareHashMap().get(posTo).setIcon(icon);
        
        // If in process of reloading a game, no need to execute sound and
        // perform repinting.
        if (!driver.isCurrentlyReloadingPreviousGame()) {
            ui.repaint();
            driver.getSoundPlayer().playSound(SoundPlayer.MOVE);
        }
    }

    /**
     * Get key from value in the ChessSquare (JButton sub class) hashmap.
     *
     * @param square
     * @return square position (A2, B6 ect).
     * @throws InvalidChessPositionException
     */
    public String getPositionFromSquare(final ChessSquare square) throws InvalidChessPositionException {

        String result = "";

        if (this.driver.getSquareHashMap().containsValue(square)) {
            for (Map.Entry<String, ChessSquare> entry : this.driver.getSquareHashMap().entrySet()) {
                if (entry.getValue().equals(square)) {
                    result = entry.getKey();
                    return result;
                }
            }
        } else {
            throw new InvalidChessPositionException(String.format(ExceptionConst.EX_INVALID_CHESS_POS, result));
        }
        return null;
    }

    /**
     * Update ply depth display.
     */
    public void updatePlyDepthDisplay() {

        final String depth = this.driver.getGame().getDepth().toString();

        // Append last FEN string to text area :
        this.driver.getWriter().appendText(String.format(MessageConst.DISPLAY_PLY_DEPTH, depth + UIConst.BACK_SLASH_N),
                MessageTypeConst.CHECK, !this.driver.isCurrentlyReloadingPreviousGame());
    }

    /**
     * Change chessmen icons depending on type sent from GUI.
     *
     * @param type
     */
    public void swapChessMenIcons(final String type) {

        // Empty icon pool :
        ImageIconPool.getPool().clear();
        ImageIcon icon = null;

        for (ChessSquare square : this.driver.getSquareHashMap().values()) {

            if (square.getIcon() != null) {

                // Get icon and it's description :
                icon = square.getIcon();
                String description = icon.getDescription();

                // Re-build Icon :
                icon = createImageIcon(UIConst.CHESSMEN + type
                        + UIConst.SLASH + description + UIConst.PNG_EXT);
                icon.setDescription(description);
                square.setIcon(icon);

                // Determine if icon is black or white and reload to ImageIcon pool.
                if (description.substring(0, 1).equals(UIConst.BLACK_CHAR.toString())) {
                    ImageIconPool.getPool().put(icon, BoardConst.BLACK);
                } else { // It's white.
                    ImageIconPool.getPool().put(icon, BoardConst.WHITE);
                }

            }
        } // end for loop.

        // Repaint all the chess board.
        for (ChessSquare square : this.driver.getSquareHashMap().values()) {
            repaintChessSquares(square);
        }
    }

    /**
     * Update ui with user settings.
     */
    void updateUiSettings() {
        this.driver.getUi().getDisplayCoordinatesCheckBoxMenuItem().setSelected(
                this.driver.getStatusIO().getUserSettings().isBoardCoordinatesVisible());
    }
    //</editor-fold>  

}
