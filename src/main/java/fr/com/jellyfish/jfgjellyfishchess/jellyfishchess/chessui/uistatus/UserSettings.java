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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.uistatus;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import java.awt.Color;
import java.io.Serializable;

/**
 * User settings.
 * @author Thomas.H Waner 2014
 */
public class UserSettings implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars"> 
    /**
    * Game ply depth.
    */
    private int depth = 10;
    
    /**
     * Chessmen style previously used.
     */
    private String chessmenStyle = UIConst.CLASSIC_TWO;
    
    /**
     * Chess board style previously used.
     */
    private String chessBoardStyle = UIConst.DIGITAL_LIGHT4; 
    
    /**
     * Color GUI is playing with and engine is playing against.
     */
    private String color = BoardConst.WHITE;
    
    /**
     * ui display setting.
     */
    private boolean displayAll = false;
    
    /**
     * ui playing whites.
     */
    private boolean white = true;
    
    /**
     * Reload previous game or not.
     */
    private boolean loadPreviousGame = true;
    
    /**
     * Backup game.data files on window close.
     */
    private boolean backupGameData = false;
    
    /**
     * Launch infinite search on game positions after engine has moved and GUI
     * side is thinking/analyzing game moves.
     */
    private boolean activateInfiniteSearchAfterEngineMove = false;
    
    /**
     * Show or hide menu bar.
     */
    private boolean menuBarVisible = true;
    
    /**
     * Consoles state, display visible or !.
     */
    private boolean consoleVisible = true;
    
    /**
     * Display or not chess board coordinates (a1 b1 ect).
     */
    private boolean boardCoordinatesVisible = false;
    
    /**
     * Main panel container background color.
     */
    private Color gloabalContainerBackgroundColor = new Color(80,92,92);
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Constructor"> 
    public UserSettings() { }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public Color getGloabalContainerBackgroundColor() {
        return gloabalContainerBackgroundColor;
    }

    public void setGloabalContainerBackgroundColor(final Color gloabalContainerBackgroundColor) {
        this.gloabalContainerBackgroundColor = gloabalContainerBackgroundColor;
    }
    
    public boolean isBoardCoordinatesVisible() {
        return boardCoordinatesVisible;
    }

    public void setBoardCoordinatesVisible(final boolean boardCoordinatesVisible) {
        this.boardCoordinatesVisible = boardCoordinatesVisible;
    }
    
    public boolean isConsoleVisible() {
        return consoleVisible;
    }

    public void setConsoleVisible(final boolean consoleVisible) {
        this.consoleVisible = consoleVisible;
    }
    
    public boolean isMenuBarVisible() {
        return menuBarVisible;
    }

    public void setMenuBarVisible (final boolean showMenuBar) {
        this.menuBarVisible = showMenuBar;
    }

    public boolean isActivateInfiniteSearchAfterEngineMove() {
        return activateInfiniteSearchAfterEngineMove;
    }

    public void setActivateInfiniteSearchAfterEngineMove(final boolean activateInfiniteSearchAfterEngineMove) {
        this.activateInfiniteSearchAfterEngineMove = activateInfiniteSearchAfterEngineMove;
    }
    
    public boolean isBackupGameData() {
        return backupGameData;
    }

    public void setBackUpGameData(final boolean backupGameData) {
        this.backupGameData = backupGameData;
    }
    
    public boolean isLoadPreviousGame() {
        return loadPreviousGame;
    }

    public void setLoadPreviousGame(final boolean loadPreviousGame) {
        this.loadPreviousGame = loadPreviousGame;
    }
    
    public boolean isWhite() {
        return white;
    }

    public void setWhite(final boolean white) {
        this.white = white;
    }
    
    public int getDepth() {
        return depth;
    }

    public boolean isDisplayAll() {
        return displayAll;
    }
    
    public void setDepth(final int depth) {
        this.depth = depth;
    }
    
    public void setDisplayAll(final boolean displayAll) {
        this.displayAll = displayAll;
    }
    
    public String getChessmenStyle() {
        return chessmenStyle;
    }

    public void setChessmenStyle(final String chessmenStyle) {
        this.chessmenStyle = chessmenStyle;
    }

    public String getChessBoardStyle() {
        return chessBoardStyle;
    }

    public void setChessBoardStyle(final String chessBoardStyle) {
        this.chessBoardStyle = chessBoardStyle;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }
    //</editor-fold> 

}
