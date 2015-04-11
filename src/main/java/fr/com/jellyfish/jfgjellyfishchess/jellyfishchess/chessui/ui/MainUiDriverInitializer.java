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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.ImageIconPool;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;

/**
 * @author Thomas.H Warner 2014
 */
public class MainUiDriverInitializer {

    //<editor-fold defaultstate="collapsed" desc="Private vars"> 
    /**
     * UiDriver class reference.
     */
    private final MainUiDriver driver;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Constructor"> 
    /**
     * Constructor.
     * @param driver
     */
    public MainUiDriverInitializer(final MainUiDriver driver) { 
        this.driver = driver;
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Methods"> 
    /**
     * Launch initialization.
     * @param playingWhites 
     */
    public void init(final boolean playingWhites) {
        // Perform mapping depending on black or white played by UI side:
        if (playingWhites) { initMapEngineBlack();
        } else { initMapEngineWhite(); }
        initChessMenLayout();
        initBoardBackground();
        initChessSquareLabellingLayout(
            this.driver.getStatusIO().getUserSettings().isBoardCoordinatesVisible());
    }

    /**
     * Init engine for engine playing blacks.
     */
    private void initMapEngineBlack() {       
        //<editor-fold defaultstate="collapsed" desc="initialize map">
        this.driver.setSquareHashMap(new LinkedHashMap<String, ChessSquare>());
        
        this.driver.getSquareHashMap().put(BoardConst.A1, this.driver.getUi().getbA1());
        this.driver.getSquareHashMap().put(BoardConst.A2, this.driver.getUi().getbA2());
        this.driver.getSquareHashMap().put(BoardConst.A3, this.driver.getUi().getbA3());
        this.driver.getSquareHashMap().put(BoardConst.A4, this.driver.getUi().getbA4());
        this.driver.getSquareHashMap().put(BoardConst.A5, this.driver.getUi().getbA5());
        this.driver.getSquareHashMap().put(BoardConst.A6, this.driver.getUi().getbA6());
        this.driver.getSquareHashMap().put(BoardConst.A7, this.driver.getUi().getbA7());
        this.driver.getSquareHashMap().put(BoardConst.A8, this.driver.getUi().getbA8());
        this.driver.getSquareHashMap().put(BoardConst.B1, this.driver.getUi().getbB1());
        this.driver.getSquareHashMap().put(BoardConst.B2, this.driver.getUi().getbB2());
        this.driver.getSquareHashMap().put(BoardConst.B3, this.driver.getUi().getbB3());
        this.driver.getSquareHashMap().put(BoardConst.B4, this.driver.getUi().getbB4());
        this.driver.getSquareHashMap().put(BoardConst.B5, this.driver.getUi().getbB5());
        this.driver.getSquareHashMap().put(BoardConst.B6, this.driver.getUi().getbB6());
        this.driver.getSquareHashMap().put(BoardConst.B7, this.driver.getUi().getbB7());
        this.driver.getSquareHashMap().put(BoardConst.B8, this.driver.getUi().getbB8());
        this.driver.getSquareHashMap().put(BoardConst.C1, this.driver.getUi().getbC1());
        this.driver.getSquareHashMap().put(BoardConst.C2, this.driver.getUi().getbC2());
        this.driver.getSquareHashMap().put(BoardConst.C3, this.driver.getUi().getbC3());
        this.driver.getSquareHashMap().put(BoardConst.C4, this.driver.getUi().getbC4());
        this.driver.getSquareHashMap().put(BoardConst.C5, this.driver.getUi().getbC5());
        this.driver.getSquareHashMap().put(BoardConst.C6, this.driver.getUi().getbC6());
        this.driver.getSquareHashMap().put(BoardConst.C7, this.driver.getUi().getbC7());
        this.driver.getSquareHashMap().put(BoardConst.C8, this.driver.getUi().getbC8());
        this.driver.getSquareHashMap().put(BoardConst.D1, this.driver.getUi().getbD1());
        this.driver.getSquareHashMap().put(BoardConst.D2, this.driver.getUi().getbD2());
        this.driver.getSquareHashMap().put(BoardConst.D3, this.driver.getUi().getbD3());
        this.driver.getSquareHashMap().put(BoardConst.D4, this.driver.getUi().getbD4());
        this.driver.getSquareHashMap().put(BoardConst.D5, this.driver.getUi().getbD5());
        this.driver.getSquareHashMap().put(BoardConst.D6, this.driver.getUi().getbD6());
        this.driver.getSquareHashMap().put(BoardConst.D7, this.driver.getUi().getbD7());
        this.driver.getSquareHashMap().put(BoardConst.D8, this.driver.getUi().getbD8());
        this.driver.getSquareHashMap().put(BoardConst.E1, this.driver.getUi().getbE1());
        this.driver.getSquareHashMap().put(BoardConst.E2, this.driver.getUi().getbE2());
        this.driver.getSquareHashMap().put(BoardConst.E3, this.driver.getUi().getbE3());
        this.driver.getSquareHashMap().put(BoardConst.E4, this.driver.getUi().getbE4());
        this.driver.getSquareHashMap().put(BoardConst.E5, this.driver.getUi().getbE5());
        this.driver.getSquareHashMap().put(BoardConst.E6, this.driver.getUi().getbE6());
        this.driver.getSquareHashMap().put(BoardConst.E7, this.driver.getUi().getbE7());
        this.driver.getSquareHashMap().put(BoardConst.E8, this.driver.getUi().getbE8());
        this.driver.getSquareHashMap().put(BoardConst.F1, this.driver.getUi().getbF1());
        this.driver.getSquareHashMap().put(BoardConst.F2, this.driver.getUi().getbF2());
        this.driver.getSquareHashMap().put(BoardConst.F3, this.driver.getUi().getbF3());
        this.driver.getSquareHashMap().put(BoardConst.F4, this.driver.getUi().getbF4());
        this.driver.getSquareHashMap().put(BoardConst.F5, this.driver.getUi().getbF5());
        this.driver.getSquareHashMap().put(BoardConst.F6, this.driver.getUi().getbF6());
        this.driver.getSquareHashMap().put(BoardConst.F7, this.driver.getUi().getbF7());
        this.driver.getSquareHashMap().put(BoardConst.F8, this.driver.getUi().getbF8());
        this.driver.getSquareHashMap().put(BoardConst.G1, this.driver.getUi().getbG1());
        this.driver.getSquareHashMap().put(BoardConst.G2, this.driver.getUi().getbG2());
        this.driver.getSquareHashMap().put(BoardConst.G3, this.driver.getUi().getbG3());
        this.driver.getSquareHashMap().put(BoardConst.G4, this.driver.getUi().getbG4());
        this.driver.getSquareHashMap().put(BoardConst.G5, this.driver.getUi().getbG5());
        this.driver.getSquareHashMap().put(BoardConst.G6, this.driver.getUi().getbG6());
        this.driver.getSquareHashMap().put(BoardConst.G7, this.driver.getUi().getbG7());
        this.driver.getSquareHashMap().put(BoardConst.G8, this.driver.getUi().getbG8());
        this.driver.getSquareHashMap().put(BoardConst.H1, this.driver.getUi().getbH1());
        this.driver.getSquareHashMap().put(BoardConst.H2, this.driver.getUi().getbH2());
        this.driver.getSquareHashMap().put(BoardConst.H3, this.driver.getUi().getbH3());
        this.driver.getSquareHashMap().put(BoardConst.H4, this.driver.getUi().getbH4());
        this.driver.getSquareHashMap().put(BoardConst.H5, this.driver.getUi().getbH5());
        this.driver.getSquareHashMap().put(BoardConst.H6, this.driver.getUi().getbH6());
        this.driver.getSquareHashMap().put(BoardConst.H7, this.driver.getUi().getbH7());
        this.driver.getSquareHashMap().put(BoardConst.H8, this.driver.getUi().getbH8());
        //</editor-fold>    
    }

    /**
     * Init engine for engine playing whites.
     */
    private void initMapEngineWhite() {
        //<editor-fold defaultstate="collapsed" desc="initialize map">
        this.driver.setSquareHashMap(new LinkedHashMap<String, ChessSquare>());
        
        this.driver.getSquareHashMap().put(BoardConst.H8, this.driver.getUi().getbA1());
        this.driver.getSquareHashMap().put(BoardConst.H7, this.driver.getUi().getbA2());
        this.driver.getSquareHashMap().put(BoardConst.H6, this.driver.getUi().getbA3());
        this.driver.getSquareHashMap().put(BoardConst.H5, this.driver.getUi().getbA4());
        this.driver.getSquareHashMap().put(BoardConst.H4, this.driver.getUi().getbA5());
        this.driver.getSquareHashMap().put(BoardConst.H3, this.driver.getUi().getbA6());
        this.driver.getSquareHashMap().put(BoardConst.H2, this.driver.getUi().getbA7());
        this.driver.getSquareHashMap().put(BoardConst.H1, this.driver.getUi().getbA8());
        this.driver.getSquareHashMap().put(BoardConst.G8, this.driver.getUi().getbB1());
        this.driver.getSquareHashMap().put(BoardConst.G7, this.driver.getUi().getbB2());
        this.driver.getSquareHashMap().put(BoardConst.G6, this.driver.getUi().getbB3());
        this.driver.getSquareHashMap().put(BoardConst.G5, this.driver.getUi().getbB4());
        this.driver.getSquareHashMap().put(BoardConst.G4, this.driver.getUi().getbB5());
        this.driver.getSquareHashMap().put(BoardConst.G3, this.driver.getUi().getbB6());
        this.driver.getSquareHashMap().put(BoardConst.G2, this.driver.getUi().getbB7());
        this.driver.getSquareHashMap().put(BoardConst.G1, this.driver.getUi().getbB8());
        this.driver.getSquareHashMap().put(BoardConst.F8, this.driver.getUi().getbC1());
        this.driver.getSquareHashMap().put(BoardConst.F7, this.driver.getUi().getbC2());
        this.driver.getSquareHashMap().put(BoardConst.F6, this.driver.getUi().getbC3());
        this.driver.getSquareHashMap().put(BoardConst.F5, this.driver.getUi().getbC4());
        this.driver.getSquareHashMap().put(BoardConst.F4, this.driver.getUi().getbC5());
        this.driver.getSquareHashMap().put(BoardConst.F3, this.driver.getUi().getbC6());
        this.driver.getSquareHashMap().put(BoardConst.F2, this.driver.getUi().getbC7());
        this.driver.getSquareHashMap().put(BoardConst.F1, this.driver.getUi().getbC8());
        this.driver.getSquareHashMap().put(BoardConst.E8, this.driver.getUi().getbD1());
        this.driver.getSquareHashMap().put(BoardConst.E7, this.driver.getUi().getbD2());
        this.driver.getSquareHashMap().put(BoardConst.E6, this.driver.getUi().getbD3());
        this.driver.getSquareHashMap().put(BoardConst.E5, this.driver.getUi().getbD4());
        this.driver.getSquareHashMap().put(BoardConst.E4, this.driver.getUi().getbD5());
        this.driver.getSquareHashMap().put(BoardConst.E3, this.driver.getUi().getbD6());
        this.driver.getSquareHashMap().put(BoardConst.E2, this.driver.getUi().getbD7());
        this.driver.getSquareHashMap().put(BoardConst.E1, this.driver.getUi().getbD8());
        this.driver.getSquareHashMap().put(BoardConst.D8, this.driver.getUi().getbE1());
        this.driver.getSquareHashMap().put(BoardConst.D7, this.driver.getUi().getbE2());
        this.driver.getSquareHashMap().put(BoardConst.D6, this.driver.getUi().getbE3());
        this.driver.getSquareHashMap().put(BoardConst.D5, this.driver.getUi().getbE4());
        this.driver.getSquareHashMap().put(BoardConst.D4, this.driver.getUi().getbE5());
        this.driver.getSquareHashMap().put(BoardConst.D3, this.driver.getUi().getbE6());
        this.driver.getSquareHashMap().put(BoardConst.D2, this.driver.getUi().getbE7());
        this.driver.getSquareHashMap().put(BoardConst.D1, this.driver.getUi().getbE8());
        this.driver.getSquareHashMap().put(BoardConst.C8, this.driver.getUi().getbF1());
        this.driver.getSquareHashMap().put(BoardConst.C7, this.driver.getUi().getbF2());
        this.driver.getSquareHashMap().put(BoardConst.C6, this.driver.getUi().getbF3());
        this.driver.getSquareHashMap().put(BoardConst.C5, this.driver.getUi().getbF4());
        this.driver.getSquareHashMap().put(BoardConst.C4, this.driver.getUi().getbF5());
        this.driver.getSquareHashMap().put(BoardConst.C3, this.driver.getUi().getbF6());
        this.driver.getSquareHashMap().put(BoardConst.C2, this.driver.getUi().getbF7());
        this.driver.getSquareHashMap().put(BoardConst.C1, this.driver.getUi().getbF8());
        this.driver.getSquareHashMap().put(BoardConst.B8, this.driver.getUi().getbG1());
        this.driver.getSquareHashMap().put(BoardConst.B7, this.driver.getUi().getbG2());
        this.driver.getSquareHashMap().put(BoardConst.B6, this.driver.getUi().getbG3());
        this.driver.getSquareHashMap().put(BoardConst.B5, this.driver.getUi().getbG4());
        this.driver.getSquareHashMap().put(BoardConst.B4, this.driver.getUi().getbG5());
        this.driver.getSquareHashMap().put(BoardConst.B3, this.driver.getUi().getbG6());
        this.driver.getSquareHashMap().put(BoardConst.B2, this.driver.getUi().getbG7());
        this.driver.getSquareHashMap().put(BoardConst.B1, this.driver.getUi().getbG8());
        this.driver.getSquareHashMap().put(BoardConst.A8, this.driver.getUi().getbH1());
        this.driver.getSquareHashMap().put(BoardConst.A7, this.driver.getUi().getbH2());
        this.driver.getSquareHashMap().put(BoardConst.A6, this.driver.getUi().getbH3());
        this.driver.getSquareHashMap().put(BoardConst.A5, this.driver.getUi().getbH4());
        this.driver.getSquareHashMap().put(BoardConst.A4, this.driver.getUi().getbH5());
        this.driver.getSquareHashMap().put(BoardConst.A3, this.driver.getUi().getbH6());
        this.driver.getSquareHashMap().put(BoardConst.A2, this.driver.getUi().getbH7());
        this.driver.getSquareHashMap().put(BoardConst.A1, this.driver.getUi().getbH8());
        //</editor-fold>
    }

    /**
     * Init layout for driver.
     */
    private void initChessMenLayout() {       
        //<editor-fold defaultstate="collapsed" desc="initialize chessmen layout">
        String[] whitepawns = new String[]{ BoardConst.A2, BoardConst.B2, BoardConst.C2, 
            BoardConst.D2, BoardConst.E2, BoardConst.F2, BoardConst.G2, BoardConst.H2 };
        String[] blackpawns = new String[]{ BoardConst.A7, BoardConst.B7, 
            BoardConst.C7, BoardConst.D7, BoardConst.E7, BoardConst.F7, BoardConst.G7, 
            BoardConst.H7 };

        for (String str : whitepawns) { // White pawn's.
            ChessSquare square = this.driver.getSquareHashMap().get(str);
            ImageIcon icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                    this.driver.getStatusIO().getUserSettings().getChessmenStyle() +
                    UIConst.SLASH + UIConst.WHITE_PAWN + UIConst.PNG_EXT);
            if (icon != null) {
                icon.setDescription(UIConst.WHITE_PAWN);
                square.setIcon(icon);
                ImageIconPool.getPool().put(icon, BoardConst.WHITE);
            }
        }

        for (String str : blackpawns) { // Black pawn's.
            ChessSquare square = this.driver.getSquareHashMap().get(str);
            ImageIcon icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                    this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                    UIConst.SLASH + UIConst.BLACK_PAWN + UIConst.PNG_EXT);
            if (icon != null) {
                icon.setDescription(UIConst.BLACK_PAWN);
                square.setIcon(icon);
                ImageIconPool.getPool().put(icon, BoardConst.BLACK);
            }
        }

        // whites.
        ChessSquare square = this.driver.getSquareHashMap().get(BoardConst.A1);
        ImageIcon icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_ROOK + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_ROOK);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.B1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_KNIGHT + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_KNIGHT);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.C1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_BISHOP + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_BISHOP);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.D1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_QUEEN + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_QUEEN);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.E1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_KING + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_KING);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.F1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_BISHOP + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_BISHOP);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.G1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_KNIGHT + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_KNIGHT);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);

        square = this.driver.getSquareHashMap().get(BoardConst.H1);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.WHITE_ROOK + UIConst.PNG_EXT);
        icon.setDescription(UIConst.WHITE_ROOK);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.WHITE);
        
        // Blacks.
        square = this.driver.getSquareHashMap().get(BoardConst.A8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.BLACK_ROOK + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_ROOK);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.B8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.BLACK_KNIGHT + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_KNIGHT);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.C8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.BLACK_BISHOP + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_BISHOP);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.D8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.BLACK_QUEEN + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_QUEEN);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.E8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
                this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
                UIConst.SLASH + UIConst.BLACK_KING + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_KING);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.F8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
            this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
            UIConst.SLASH + UIConst.BLACK_BISHOP + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_BISHOP);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.G8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
            this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
            UIConst.SLASH + UIConst.BLACK_KNIGHT + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_KNIGHT);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);

        square = this.driver.getSquareHashMap().get(BoardConst.H8);
        icon = this.driver.getHelper().createImageIcon(UIConst.CHESSMEN + 
            this.driver.getStatusIO().getUserSettings().getChessmenStyle() + 
            UIConst.SLASH + UIConst.BLACK_ROOK + UIConst.PNG_EXT);
        icon.setDescription(UIConst.BLACK_ROOK);
        square.setIcon(icon);
        ImageIconPool.getPool().put(icon, BoardConst.BLACK);
        //</editor-fold>       
    }

    /**
     * Init boardbackground.
     */
    private void initBoardBackground() {
        this.driver.getUi().getBoardContainer().setChessBoardBackgroundImage(
            this.driver.getHelper().createImageIcon(UIConst.BOARD_RES + 
            this.driver.getStatusIO().getUserSettings().getChessBoardStyle()));
    }
    
    /**
     * Initialize label in ChessSquare instances (JButtonsub class) for UI
     * playing white layout.
     * @param display 
     */
    void initChessSquareLabellingLayout(final boolean display) {

        for (ChessSquare square : this.driver.getSquareHashMap().values()) {
            if (square.isLabeled()) {
                square.remove(0);
                square.setLabelVisible(false);
                square.setLabeled(false);
            }
        }
        
        ChessSquare csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A1);
        csq.addPositionLabel(BoardConst.ONE_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A2);
        csq.addPositionLabel(BoardConst.TWO_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A3);
        csq.addPositionLabel(BoardConst.THREE_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A4);
        csq.addPositionLabel(BoardConst.FOUR_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A5);
        csq.addPositionLabel(BoardConst.FIVE_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A6);
        csq.addPositionLabel(BoardConst.SIX_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A7);
        csq.addPositionLabel(BoardConst.SEVEN_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.A8);
        csq.addPositionLabel(BoardConst.A_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.B8);
        csq.addPositionLabel(BoardConst.B_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.C8);
        csq.addPositionLabel(BoardConst.C_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.D8);
        csq.addPositionLabel(BoardConst.D_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.E8);
        csq.addPositionLabel(BoardConst.E_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.F8);
        csq.addPositionLabel(BoardConst.F_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.G8);
        csq.addPositionLabel(BoardConst.G_LABEL);
        csq = (ChessSquare)this.driver.getSquareHashMap().get(BoardConst.H8);
        csq.addPositionLabel(BoardConst.H_LABEL);
        
        /**
         * Remove labels if unset in user settings.
         */
        if (!display) {
            for (ChessSquare square : this.driver.getSquareHashMap().values()) {
                if (square.isLabeled()) {
                    square.getComponent(0).setVisible(false);
                    square.setLabelVisible(false);
                }
            }
            this.driver.getHelper().repaintAllChessSquares();
        }
    }
    //</editor-fold>

}
