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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Rook;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidInfiniteSearchResult;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.CastlingObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.CheckObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.DisplayableTextZone;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.ExternalEngineObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.PawnEnPassantObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.TimerObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import java.util.Map;

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
    
    /**
     * Game writter instance.
     */
    protected DisplayableTextZone writer;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Public methods"> 
    /**
     * Comminicate move back to driver extended class.
     * @param positions
     * @param fen
     * @param moveCount
     * @param plyDepth 
     */
    public abstract void applyMoveBack(final Map<String, Character> positions, final String fen,
            final int moveCount, final int plyDepth);
    
    public void clearAllSquareBorders() { }
    
    public boolean isPerforming() {
        return false;
    }
        
    /**
     * Add this to all necessary observer patterns.
     */
    public final void initDriverObservation() {

        // Add this class to Rook class's CastlingObservers.
        Rook rookA1 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.A1).getOnPositionChessMan();
        rookA1.addCastlingObserver(this);
        Rook rookA8 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.A8).getOnPositionChessMan();
        rookA8.addCastlingObserver(this);
        Rook rookH1 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.H1).getOnPositionChessMan();
        rookH1.addCastlingObserver(this);
        Rook rookH8 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.H8).getOnPositionChessMan();
        rookH8.addCastlingObserver(this);

        // Add this as observer on all Pawn classes for "En passant" moving.
        // Black Pawns :
        Pawn pawnH7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.H7).getOnPositionChessMan();
        pawnH7.addPawnEnPassantObserver(this);
        Pawn pawnG7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.G7).getOnPositionChessMan();
        pawnG7.addPawnEnPassantObserver(this);
        Pawn pawnF7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.F7).getOnPositionChessMan();
        pawnF7.addPawnEnPassantObserver(this);
        Pawn pawnE7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.E7).getOnPositionChessMan();
        pawnE7.addPawnEnPassantObserver(this);
        Pawn pawnD7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.D7).getOnPositionChessMan();
        pawnD7.addPawnEnPassantObserver(this);
        Pawn pawnC7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.C7).getOnPositionChessMan();
        pawnC7.addPawnEnPassantObserver(this);
        Pawn pawnB7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.B7).getOnPositionChessMan();
        pawnB7.addPawnEnPassantObserver(this);
        Pawn pawnA7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.A7).getOnPositionChessMan();
        pawnA7.addPawnEnPassantObserver(this);
        // White Pawns :
        Pawn pawnA2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.A2).getOnPositionChessMan();
        pawnA2.addPawnEnPassantObserver(this);
        Pawn pawnB2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.B2).getOnPositionChessMan();
        pawnB2.addPawnEnPassantObserver(this);
        Pawn pawnC2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.C2).getOnPositionChessMan();
        pawnC2.addPawnEnPassantObserver(this);
        Pawn pawnD2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.D2).getOnPositionChessMan();
        pawnD2.addPawnEnPassantObserver(this);
        Pawn pawnE2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.E2).getOnPositionChessMan();
        pawnE2.addPawnEnPassantObserver(this);
        Pawn pawnF2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.F2).getOnPositionChessMan();
        pawnF2.addPawnEnPassantObserver(this);
        Pawn pawnG2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.G2).getOnPositionChessMan();
        pawnG2.addPawnEnPassantObserver(this);
        Pawn pawnH2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.H2).getOnPositionChessMan();
        pawnH2.addPawnEnPassantObserver(this);

        // Add this as check observer to all chessmen :
        for (String position : BoardConst.boardPositions) {
            Board.getInstance().getCoordinates().get(position).getOnPositionChessMan().setCheckObserver(this);
        }
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
    public void applyPawnEnPassant(final String takenPawnPosition) { }

    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) { }

    @Override
    public void tick(final String displayTime, final int ticks) { }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters"> 
    public DisplayableTextZone getWriter() {
        return writer;
    }
        
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
