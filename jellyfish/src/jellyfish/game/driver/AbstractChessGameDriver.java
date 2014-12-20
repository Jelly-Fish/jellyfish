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

package jellyfish.game.driver;

import java.util.Map;
import jellyfish.entities.Position;
import jellyfish.exceptions.InvalidInfiniteSearchResult;
import jellyfish.interfaces.CastlingObserver;
import jellyfish.interfaces.CheckObserver;
import jellyfish.interfaces.ExternalEngineObserver;
import jellyfish.interfaces.PawnEnPassantObserver;
import jellyfish.interfaces.TimerObserver;
import jellyfish.uci.UCIMessage;

/**
 * @author Thomas.H Warner 2014
 */
public abstract class AbstractChessGameDriver implements ExternalEngineObserver, CastlingObserver, 
        PawnEnPassantObserver, CheckObserver, TimerObserver {

    //<editor-fold defaultstate="collapsed" desc="Private vars"> 
    /**
     * If GUI is playing white or not.
     */
    private boolean uiPlayingWhites;

    /**
     * Color engine is playing with.
     */
    private String engineColor;

    /**
     * Engines oponent color.
     */
    private String engineOponentColor;
    
    /**
     * Fen value of last selected chessman.
     */
    private String fenLastSelectedChessMan;
    
    /**
     * If engine is searching for bestmove, GUI cannot play.
     */
    private boolean engineSearching = false;
    
    /**
     * Game class type.
     */
    private String gameType;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Public methods"> 
    public void applyMoveBack(final Map<String, Character> positions, final String fen,
            final int moveCount, final int plyDepth) { }
    
    public void clearAllSquareBorders() { }
    
    public boolean isPerforming() {
        return false;
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Overriden interface methods"> 
    @Override
    public void engineResponse(final String response, final int msgLevel) { }

    @Override
    public void engineMoved(final UCIMessage message) { }

    @Override
    public void engineInfiniteSearchResponse(final UCIMessage message) throws InvalidInfiniteSearchResult { }

    @Override
    public void applyCastling(final String posFrom, final String posTo) { }

    @Override
    public void applyPawnEnPassant(final String virtualPawnPosition) { }

    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) { }

    @Override
    public void tick(final String displayTime) { }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters"> 
    public boolean isUiPlayingWhites() {
        return uiPlayingWhites;
    }

    public void setUiPlayingWhites(final boolean uiPlayingWhites) {
        this.uiPlayingWhites = uiPlayingWhites;
    }
    
    public boolean isEngineSearching() {
        return engineSearching;
    }
    
    public void setEngineSearching(final boolean engineSearching) {
        this.engineSearching = engineSearching;
    }
    
    public void setFenLastSelectedChessMan(final String fenLastSelectedChessMan) {
        this.fenLastSelectedChessMan = fenLastSelectedChessMan;
    }
    
    public String getGameType() {
        return gameType;
    }
    
    public String getEngineColor() {
        return engineColor;
    }

    public String getEngineOponentColor() {
        return engineOponentColor;
    }

    public void setEngineColor(final String engineColor) {
        this.engineColor = engineColor;
    }

    public void setEngineOponentColor(final String engineOponentColor) {
        this.engineOponentColor = engineOponentColor;
    }

    public void setGameType(final String gameType) {
        this.gameType = gameType;
    }
    
    public String getFenLastSelectedChessMan() {
        return fenLastSelectedChessMan;
    }
    //</editor-fold> 
    
}
