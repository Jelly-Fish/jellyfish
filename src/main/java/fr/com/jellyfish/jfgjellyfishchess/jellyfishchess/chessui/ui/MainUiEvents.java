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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.MessageConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.ImageIconPool;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.AbstractChessMan;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidChessPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
// Java utils, swing & awt libs imports :
import java.awt.Component;
import java.awt.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
// Jellyfish imports :


/**
 * @author Thomas.H Warner 2014
 */
public class MainUiEvents {
    
    /**
     * Gui driver instane.
     */
    private final MainUiDriver driver;

    /**
     * Constructor.
     * @param driver 
     */
    public MainUiEvents(final MainUiDriver driver) {
        this.driver = driver;
    }
    
    //<editor-fold defaultstate="collapsed" desc="init GUI events method">
    /**
     * Add listeners & processes on all components.
     */
    public void initGUIComponentEvents() {
        
        driver.getUi().getGlobalContainer().addMouseListener(new java.awt.event.MouseAdapter() {
            //<editor-fold defaultstate="collapsed" desc="mouse events Overrides">
            @Override
            public void mouseEntered (java.awt.event.MouseEvent evt) { 
                if (!driver.isPerforming()) {
                    driver.getUi().getGlobalContainer().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
            }
            
            @Override
            public void mouseDragged (java.awt.event.MouseEvent evt) { }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {            
                
                if (!driver.isPerforming() && SwingUtilities.isLeftMouseButton(evt)) {
                    //<editor-fold defaultstate="collapsed" desc="Event code: board moving">
                    int boardX = driver.getUi().getBoardContainer().getX();
                    int boardY = driver.getUi().getBoardContainer().getY();
                    int controlX = driver.getUi().getControlPanel().getX();
                    int controlY = driver.getUi().getControlPanel().getY();
                    int eventX = evt.getX();
                    int eventY = evt.getY();

                    if (eventX > boardX && eventY > boardY && eventX < (boardX + (55 * 8))) {
                        // Move down on click.
                        driver.getUi().getBoardContainer().setLocation(boardX, boardY + 20);
                        driver.getUi().getControlPanel().setLocation(controlX, controlY + 20);      
                    } else if (eventX > boardX && eventY < boardY && eventX < (boardX + (55 * 8))) {
                        // Move up.
                        if (boardY > 10) {
                            driver.getUi().getBoardContainer().setLocation(boardX, boardY - 20);
                            driver.getUi().getControlPanel().setLocation(controlX, controlY - 20);
                        }
                    } else if (eventX < boardX && eventY > boardY && eventY < (boardY + (55 * 8))) {
                        // move left.
                        if (boardX > 10) {
                            driver.getUi().getBoardContainer().setLocation(boardX - 20, boardY);
                            driver.getUi().getControlPanel().setLocation(controlX - 20, controlY);
                        }
                    } else if (eventX > (boardX + (55 * 8)) && eventY > boardY && eventY < (boardY + (55 * 8))) {
                        // Move right.
                        driver.getUi().getBoardContainer().setLocation(boardX + 20, boardY);
                        driver.getUi().getControlPanel().setLocation(controlX + 20, controlY);
                    } else if (eventX < boardX && eventY < boardY) {
                        // Up & left.
                        if (boardX > 10 && boardY > 10) {
                            driver.getUi().getBoardContainer().setLocation(boardX - 20, boardY - 20);
                            driver.getUi().getControlPanel().setLocation(controlX - 20, controlY - 20);
                        }
                    } else if (eventX > boardX && eventY > boardY) {
                        // Down & right.
                        driver.getUi().getBoardContainer().setLocation(boardX + 20, boardY + 20);
                        driver.getUi().getControlPanel().setLocation(controlX + 20, controlY + 20);
                    } else if (eventX > boardX && eventY < boardY) {
                        // Up & right.
                        driver.getUi().getBoardContainer().setLocation(boardX + 20, boardY - 20);
                        driver.getUi().getControlPanel().setLocation(controlX + 20, controlY - 20);
                    } else if (eventX < boardX && eventY > boardY) {
                        // Down & left.
                        driver.getUi().getBoardContainer().setLocation(boardX - 20, boardY + 20);
                        driver.getUi().getControlPanel().setLocation(controlX - 20, controlY + 20);
                    }
                }
                //</editor-fold>                
                }
            //</editor-fold>
        });
        
        for (Component component : driver.getUi().getBoardContainer().getComponents()) {
            
            // Add mouse listeners on all chess board's squares :
            component.addMouseListener(new java.awt.event.MouseAdapter() {
                //<editor-fold defaultstate="collapsed" desc="mouse event Overrides">
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                    if (!driver.isPerforming() && 
                            SwingUtilities.isRightMouseButton(evt)) {
                        
                        try {
                            // If the Square has an Icon and the Icon is GUI color equal.
                            if (driver.getSquareHashMap().get(driver.getHelper().getPositionFromSquare(
                                    (ChessSquare)evt.getComponent())).getIcon() != null) {
                                
                                // Chessman info display.
                                String pos = driver.getHelper().getPositionFromSquare((ChessSquare)evt.getComponent());
                                    
                                AbstractChessMan chessMan = Board.getInstance().getCoordinates().get(
                                    pos).getOnPositionChessMan();
                                
                                if (chessMan instanceof Pawn) {
                                    // Different display for engine or gui pawns :
                                    if (chessMan.getCOLOR().equals(driver.getEngineOponentColor())) {
                                        driver.getWriter().appendText(MessageConst.DISPLAY_PREFIX + chessMan.toString() + 
                                            CommonConst.BACKSLASH_N + 
                                            MessageConst.DISPLAY_PREFIX + MessageConst.PROMOTION_TYPE_SETTINGS_MSG + 
                                            CommonConst.BACKSLASH_N + CommonConst.PROMOTION_DISPLAY + 
                                            ((Pawn) chessMan).displayPromotionType(((Pawn) chessMan).getPromotionType())
                                            + CommonConst.BACKSLASH_N, MessageTypeConst.CHECK, true);
                                    } else {
                                        driver.getWriter().appendText(chessMan.toString() + CommonConst.BACKSLASH_N, 
                                        MessageTypeConst.CHECK, true);
                                    }
                                } else {
                                    // Select chessman is not a pawn; different display.
                                    driver.getWriter().appendText(chessMan.toString() + CommonConst.BACKSLASH_N, 
                                        MessageTypeConst.CHECK, true);
                                }
                                
                            }
                        } catch (InvalidChessPositionException | NullPointerException ex) {
                            Logger.getLogger(MainUiEvents.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    
                    if (!driver.isPerforming()) {
                        driver.getUi().getBoardContainer().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
                
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) { 
                    
                    // If engine is searching for bestmove GUI may not send a move to
                    // engine. Stay put, engineMoved(...) callback will set bool
                    // engineSeraching too false. If it is engines turn to play then
                    // move cannot be taken.
                    if (!driver.getGame().getColorToPLay().equals(driver.getEngineColor()) && 
                            !driver.isPerforming() &&
                            SwingUtilities.isLeftMouseButton(evt)) {
                        
                        //<editor-fold defaultstate="collapsed" desc="Event code">
                        try {

                            if (driver.getLastSelectedChessSquare() == null) {
                                // Either first move or a move has been validated and
                                // last selected chess square has been reset to null.
                                // In both cases clean up all borders (selected square border
                                // = aColor ect).
                                driver.clearAllSquareBorders();

                                if (driver.getSquareHashMap().get(driver.getHelper().getPositionFromSquare(
                                        (ChessSquare)evt.getComponent())).getIcon() != null &&
                                        !ImageIconPool.getPool().get(driver.getSquareHashMap().get(driver.getHelper().getPositionFromSquare(
                                        (ChessSquare)evt.getComponent())).getIcon()).equals(driver.getEngineColor())) {

                                    // If the square has an icon (not an empty square) and
                                    // the color selected is not the Engine's "oponent" color
                                    // then square selected becomes last selected square :
                                    driver.setLastSelectedChessSquare((ChessSquare)evt.getComponent());
                                    ChessSquare square = (ChessSquare)evt.getComponent();  
                                    square.addEventEffect(UIConst.SELECTED_COLOR, UIConst.BORDER_WIDTH);
                                }
                            } else {
                                // Last selected color is not null, meaning a chessman has
                                // been previously selected.
                                if (driver.getSquareHashMap().get(driver.getHelper().getPositionFromSquare(
                                        (ChessSquare)evt.getComponent())).getIcon() != null &&
                                        !ImageIconPool.getPool().get(driver.getSquareHashMap().get(driver.getHelper().getPositionFromSquare(
                                        (ChessSquare)evt.getComponent())).getIcon()).equals(driver.getEngineColor())) { 
                                    // The position selected is a not null position and 
                                    // the color is not == to an engine color chessman.
                                    // Player is making up his mind on which chessman to move
                                    // therefor borders must be cleaned up.

                                    // !This code is redundant (see just above:p) and could of course be factorized...
                                    // I decided to leave code as so because the stack in the mouseReleased()
                                    // method event is crucial for understanding the procedural logic of the
                                    // design.

                                    // Player is fiddling around :
                                    driver.clearAllSquareBorders();

                                    driver.setLastSelectedChessSquare((ChessSquare)evt.getComponent());
                                    ChessSquare square = (ChessSquare)evt.getComponent();
                                    square.addEventEffect(UIConst.SELECTED_COLOR, UIConst.BORDER_WIDTH);
                                } else { 
                                    // If stack gets to this point, the player has choosen a position
                                    // for a move. Move must be validated by jellyfish lib
                                    // before changes on UI are applied.
                                    // Stop engine infinite search support if it is launched :
                                    
                                    // /!\ If infinite search has been lauched then stop it /!\
                                    if (driver.getUi().getBrainButton().isThinking()) {
                                        // Stop infinite engine search on game move string for gui side
                                        // and display results :
                                        // In any case fire clear button method :
                                        driver.getUi().brainButtonStopThinking();
                                    } 
                                    
                                    String posFrom = new String();
                                    String posTo = new String();
                                            
                                    posFrom = driver.getHelper().getPositionFromSquare(driver.getLastSelectedChessSquare());
                                    posTo = driver.getHelper().getPositionFromSquare((ChessSquare)evt.getComponent());
                                    
                                    // Set fen value of selected chessman for move.
                                    driver.setFenLastSelectedChessMan(String.valueOf(Board.getInstance().getCoordinates(
                                        ).get(posFrom).getOnPositionChessMan().getFenValue()));

                                    try {

                                        //<editor-fold defaultstate="collapsed" desc="Pawn promotion">
                                        // Here check for Pawn promotion. Send params to executeMove(...).
                                        // In if (executeMove(...), if return is true, use applyGUIPawnPromotion(...).
                                        boolean promotion = false;
                                        char promotionType = UCIConst.DUMMY;
                                        if (Board.getInstance().getCoordinates().get(posFrom).getOnPositionChessMan() 
                                                instanceof Pawn) {
                                            if ((BoardConst.coordinatesIntegerMap.get(posTo)[0] == 1 && 
                                                    Board.getInstance().getCoordinates().get(posFrom).getOnPositionChessMan(
                                                    ).getCOLOR().equals(BoardConst.WHITE)) ||
                                                    (BoardConst.coordinatesIntegerMap.get(posTo)[0] == 8 && 
                                                    Board.getInstance().getCoordinates().get(posFrom).getOnPositionChessMan(
                                                    ).getCOLOR().equals(BoardConst.BLACK))) {
                                                Pawn pawn = (Pawn)Board.getInstance().getCoordinates().get(posFrom).getOnPositionChessMan();
                                                promotionType = pawn.getPromotionType();
                                                promotion = true;
                                                // Promotion will be validated and GUI updated if the move is validated :
                                                // see next step...
                                            }
                                        }
                                        //</editor-fold>

                                        // /!\ Now call jellyfish for move validation :
                                        // Param n°3 will always be true because call can only come from GUI.
                                        // Param n°4 & 5 : send pawn promotion details to ChessGame class for 
                                        // updating.
                                        if (driver.getGame().executeMove(posFrom, posTo, true, promotion, promotionType)) {

                                            // Move is validated, now update GUI. Move icon :
                                            driver.getHelper().moveChessSquareIcon(driver.getSquareHashMap().get(posFrom).getIcon(),
                                                driver.getSquareHashMap().get(posFrom), driver.getSquareHashMap().get(posTo));

                                            // Set moved colored border.
                                            driver.clearAllSquareBorders();
                                            driver.getSquareHashMap().get(posFrom).removeEventEffects();
                                            driver.getSquareHashMap().get(posTo).addEventEffect(UIConst.LAST_MOVE_COLOR, UIConst.BORDER_WIDTH);

                                            //<editor-fold defaultstate="collapsed" desc="Pawn promotion">
                                            if (promotion) {
                                                // If move is valid, it could be a pawn promotion situation.
                                                // In this case GUI must update and apply pawn promotion to
                                                // player. Ex pawn becomes queen, then icon must be updated.
                                                // !Add to pool to prevent imageIconPool.get = null pointer!
                                                ImageIcon promotedIcon = driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                                                    driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                                                    UIConst.SLASH + (driver.getEngineOponentColor().toLowerCase()).toCharArray()[0] + 
                                                    String.valueOf(promotionType) + UIConst.PNG_EXT);
                                                ImageIconPool.getPool().put(promotedIcon, driver.getEngineOponentColor());
                                                driver.getHelper().applyGUIPawnPromotion(posFrom, posTo, promotedIcon);
                                            }
                                            //</editor-fold>
                                            
                                            // Notify UI observers :
                                            driver.notifyMoveToUiObserver();
                                            
                                            // Finnaly the last selected chess square must be reset to null.
                                            driver.setLastSelectedChessSquare(null);
                                        }
                                    } catch (PawnPromotionException ex) {
                                        Logger.getLogger(MainUiEvents.class.getName()).log(Level.WARNING, null, ex);
                                    }
                                } // End else 
                            } // End else
                        } catch (InvalidChessPositionException ex) {
                            Logger.getLogger(MainUiEvents.class.getName()).log(Level.WARNING, null, ex);
                        }
                        //</editor-fold>
                        
                    } else if ((!driver.isEngineSearching() || 
                        driver.getGame().getColorToPLay().equals(driver.getEngineColor()))  &&
                        SwingUtilities.isLeftMouseButton(evt)) {
                        
                        // GUI is trying to take move when it is engines turn to play.
                        // Notify error in text area :
                        driver.getWriter().appendText(String.format(MessageConst.DISPLAY_ERR_ENGINE_TO_PLAY, driver.getEngineColor()) + 
                            CommonConst.BACKSLASH_N, MessageTypeConst.ERROR, true);
                    }
                }
                //</editor-fold>
            });            
        }
        
    }
    //</editor-fold>
    
}
