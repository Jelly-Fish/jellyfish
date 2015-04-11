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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.AbstractChessMan;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.King;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.NullChessMan;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Rook;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A chess board.
 * @author Thomas.H Warner 2014.
 */
public class Board {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Singleton instance.
     */
    private static Board instance;
        
    /**
     * Coordinates hashmap.
     */
    private static LinkedHashMap<String, Position> coordinates;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Private constructor">
    /**
     * Private constructor.
     */
    private Board() { }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Initialize board hashmap">
    /**
     * Initialize string value <> new Position(>string value>) hashmap.
     */
    public void init() {
        
        coordinates = new LinkedHashMap<>();
        
        coordinates.put(BoardConst.A1, new Position(BoardConst.A1));
        coordinates.put(BoardConst.A2, new Position(BoardConst.A2));
        coordinates.put(BoardConst.A3, new Position(BoardConst.A3));
        coordinates.put(BoardConst.A4, new Position(BoardConst.A4));
        coordinates.put(BoardConst.A5, new Position(BoardConst.A5));
        coordinates.put(BoardConst.A6, new Position(BoardConst.A6));
        coordinates.put(BoardConst.A7, new Position(BoardConst.A7));
        coordinates.put(BoardConst.A8, new Position(BoardConst.A8));
        coordinates.put(BoardConst.B1, new Position(BoardConst.B1));
        coordinates.put(BoardConst.B2, new Position(BoardConst.B2));
        coordinates.put(BoardConst.B3, new Position(BoardConst.B3));
        coordinates.put(BoardConst.B4, new Position(BoardConst.B4));
        coordinates.put(BoardConst.B5, new Position(BoardConst.B5));
        coordinates.put(BoardConst.B6, new Position(BoardConst.B6));
        coordinates.put(BoardConst.B7, new Position(BoardConst.B7));
        coordinates.put(BoardConst.B8, new Position(BoardConst.B8));
        coordinates.put(BoardConst.C1, new Position(BoardConst.C1));
        coordinates.put(BoardConst.C2, new Position(BoardConst.C2));
        coordinates.put(BoardConst.C3, new Position(BoardConst.C3));
        coordinates.put(BoardConst.C4, new Position(BoardConst.C4));
        coordinates.put(BoardConst.C5, new Position(BoardConst.C5));
        coordinates.put(BoardConst.C6, new Position(BoardConst.C6));
        coordinates.put(BoardConst.C7, new Position(BoardConst.C7));
        coordinates.put(BoardConst.C8, new Position(BoardConst.C8));
        coordinates.put(BoardConst.D1, new Position(BoardConst.D1));
        coordinates.put(BoardConst.D2, new Position(BoardConst.D2));
        coordinates.put(BoardConst.D3, new Position(BoardConst.D3));
        coordinates.put(BoardConst.D4, new Position(BoardConst.D4));
        coordinates.put(BoardConst.D5, new Position(BoardConst.D5));
        coordinates.put(BoardConst.D6, new Position(BoardConst.D6));
        coordinates.put(BoardConst.D7, new Position(BoardConst.D7));
        coordinates.put(BoardConst.D8, new Position(BoardConst.D8));
        coordinates.put(BoardConst.E1, new Position(BoardConst.E1));
        coordinates.put(BoardConst.E2, new Position(BoardConst.E2));
        coordinates.put(BoardConst.E3, new Position(BoardConst.E3));
        coordinates.put(BoardConst.E4, new Position(BoardConst.E4));
        coordinates.put(BoardConst.E5, new Position(BoardConst.E5));
        coordinates.put(BoardConst.E6, new Position(BoardConst.E6));
        coordinates.put(BoardConst.E7, new Position(BoardConst.E7));
        coordinates.put(BoardConst.E8, new Position(BoardConst.E8));
        coordinates.put(BoardConst.F1, new Position(BoardConst.F1));
        coordinates.put(BoardConst.F2, new Position(BoardConst.F2));
        coordinates.put(BoardConst.F3, new Position(BoardConst.F3));
        coordinates.put(BoardConst.F4, new Position(BoardConst.F4));
        coordinates.put(BoardConst.F5, new Position(BoardConst.F5));
        coordinates.put(BoardConst.F6, new Position(BoardConst.F6));
        coordinates.put(BoardConst.F7, new Position(BoardConst.F7));
        coordinates.put(BoardConst.F8, new Position(BoardConst.F8));
        coordinates.put(BoardConst.G1, new Position(BoardConst.G1));
        coordinates.put(BoardConst.G2, new Position(BoardConst.G2));
        coordinates.put(BoardConst.G3, new Position(BoardConst.G3));
        coordinates.put(BoardConst.G4, new Position(BoardConst.G4));
        coordinates.put(BoardConst.G5, new Position(BoardConst.G5));
        coordinates.put(BoardConst.G6, new Position(BoardConst.G6));
        coordinates.put(BoardConst.G7, new Position(BoardConst.G7));
        coordinates.put(BoardConst.G8, new Position(BoardConst.G8));
        coordinates.put(BoardConst.H1, new Position(BoardConst.H1));
        coordinates.put(BoardConst.H2, new Position(BoardConst.H2));
        coordinates.put(BoardConst.H3, new Position(BoardConst.H3));
        coordinates.put(BoardConst.H4, new Position(BoardConst.H4));
        coordinates.put(BoardConst.H5, new Position(BoardConst.H5));
        coordinates.put(BoardConst.H6, new Position(BoardConst.H6));
        coordinates.put(BoardConst.H7, new Position(BoardConst.H7));
        coordinates.put(BoardConst.H8, new Position(BoardConst.H8));
    }
     //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * ChessBoard class Singleton.
     * @return 
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
            instance.init();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * Get King on ChessBoard from color.
     * @param color
     * @return
     * @throws InvalidPositionException 
     */
    public AbstractChessMan getKing(final char color) throws InvalidPositionException {
        
        String searchColor = CommonConst.EMPTY_STR;
        if (Character.valueOf(color).equals(BoardConst.WHITE.toCharArray()[0])) {
            searchColor = BoardConst.WHITE;
        } else {
            searchColor = BoardConst.BLACK;
        }
        
        for (Position position : coordinates.values()) {
            if (position.getOnPositionChessMan() instanceof King && 
                    position.getOnPositionChessMan().getCOLOR().equals(searchColor)) {
                return position.getOnPositionChessMan();
            }
        }
        
        // If stack get's here means no King has been found, throw this exception :
        throw new InvalidPositionException(this.getClass().getName() + ExceptionConst.EX_INVALID_POS_ENDMSG);
    }
    
    /**
     * Reset coordinates hashmap with content of deserialized hashmap.
     * @param deserializedCoordinates LinkedHashMap<String, ChessMan>
     * @param chessGame ChessGame
     */
    public void resetCoordinates(final LinkedHashMap<String, AbstractChessMan> deserializedCoordinates,
        final ChessGame chessGame) {

        for (Map.Entry<String, AbstractChessMan> entry : deserializedCoordinates.entrySet()) {
            // For each entry in deseialized ChessMan sub classes, affect to this.coordinates
            // depending on deserializedCoordinates LinkedHashMap key value.
            
            // If entry is NullChessMan, then build new NullChessManinstance.
            // Check for that instance's property isVirtualPawn boolean.
            if (entry.getValue() instanceof NullChessMan) {
                NullChessMan nullChessMan = new NullChessMan();
                if (entry.getValue().isVirtualPawn()) {
                    nullChessMan.setVirtualPawn(true);
                } else {
                    nullChessMan.setVirtualPawn(false);
                }
                // Add new instance to the corresponding entry of Board class
                // property 'coordinates' LinkedHashMap.
                nullChessMan.setBoardPosition(coordinates.get(entry.getKey()));
                coordinates.get(entry.getKey()).setOnPositionChessMan(nullChessMan);
            } else {
                // Is not instance of NullChessMan :
                // in any case affect corresponding entry of 'coordinates' LinkedHashMap.
                // ! also set Position reference for ChessMan's instance selected from 
                // param LinkedHasMap entry that has been replaced in 'coordinates'
                // LinkedHashMap corresponding entry.
                coordinates.get(entry.getKey()).setOnPositionChessMan(entry.getValue());
                coordinates.get(entry.getKey()).getOnPositionChessMan().setBoardPosition(coordinates.get(entry.getKey()));
                
                if (entry.getValue() instanceof Pawn) {
                    // If instance of Pawn class : all 'en passant' observers intances must be reset :
                    coordinates.get(entry.getKey()).setOnPositionChessMan(entry.getValue());
                    ((Pawn)coordinates.get(entry.getKey()).getOnPositionChessMan()).addPawnEnPassantObserver(chessGame);
                    ((Pawn)coordinates.get(entry.getKey()).getOnPositionChessMan()).addPawnEnPassantObserver(chessGame.getDriver());
                } else if (entry.getValue() instanceof Rook) {
                    // ... if instance of Rook class  : all castling observers must be reset.
                    ((Rook)coordinates.get(entry.getKey()).getOnPositionChessMan()).addCastlingObserver(chessGame);
                    ((Rook)coordinates.get(entry.getKey()).getOnPositionChessMan()).addCastlingObserver(chessGame.getDriver());
                }
            }
        }
        
        // Finally for all deserialized ChessMan instances that have been re-affected
        // to Board class property 'coordinates' LinkedHasMap Position classes :
        // reset ChessGame instance and ChessGame's GUIDriver instance ass CheckObservers.
        // ! All ChessMan sub classes are have instance reference or CheckObservers !
        for (Position position : coordinates.values()) {
            position.getOnPositionChessMan().setCheckObserver(chessGame);
            position.getOnPositionChessMan().setCheckObserver(chessGame.getDriver());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gettes & Setters">
    public LinkedHashMap<String, Position> getCoordinates() {
        return coordinates;
    }
    //</editor-fold>
    
}
