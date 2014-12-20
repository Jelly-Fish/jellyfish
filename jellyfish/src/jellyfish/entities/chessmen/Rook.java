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

package jellyfish.entities.chessmen;

import jellyfish.entities.Board;
import jellyfish.entities.Position;
import java.util.List;
import jellyfish.constants.BoardConst;
import jellyfish.interfaces.CastlingObserver;
import java.util.ArrayList;
import jellyfish.constants.CommonConst;

/**
 * Describes a rook chessman in a chess game. 
 * @author Thomas.H Warner 2014
 */
public class Rook extends AbstractChessMan {
      
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
    * List of observers.
    */
    private transient ArrayList<CastlingObserver> castlingObserver = new ArrayList<>();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * @param COLOR
     * @param valuation
     * @param alive
     * @param boardPosition
     * @param nullChessMan 
     * @param fenValue 
     */
    public Rook(final String COLOR, final float valuation, final boolean alive, 
            final Position boardPosition, final boolean nullChessMan,
            final char fenValue) {
        super(COLOR, valuation, alive, boardPosition, nullChessMan, fenValue);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Overriden methods">
    /**
     * Evaluate if the move is valid for a rook's characteristics.
     * @param xyFrom
     * @param xyTo
     * @return 
     */
    @Override
    public boolean isValidMove(final Integer[] xyFrom, final Integer[] xyTo) {
        // Check Position class's between from & too for null chessmen.
        // ! Color has no importance here, a rook moves the same way if it's
        // black or white.
       
        boolean upDown = false;
        boolean leftRight = false;
        char direction;
        List<Integer[]> positions;
        
        // First is it right, left, up or down move ?
        // 'u' == up, 'r' == right ect.
        if (xyFrom[0] == xyTo[0]) { // Left or right ?
            direction = xyFrom[1] < xyTo[1] ? CommonConst.R_CHAR_LOWERCASE : CommonConst.L_CHAR_LOWERCASE;
            leftRight = true;
        } else { // up or down ?
            direction = xyFrom[0] < xyTo[0] ? CommonConst.D_CHAR_LOWERCASE : CommonConst.U_CHAR_LOWERCASE;
            upDown = true;
        }
        
        positions = getRookPositions(direction, xyFrom, xyTo);
        
        // Check for valid rook move : straight lines whatever the direction is.
        if (upDown && !(xyFrom[1] == xyTo[1])) {
            return false;
        } else if (leftRight && !(xyFrom[0] == xyTo[0])) {
            return false;
        }
        
        // Continue to check validity of move.
        int counter = 0; // Use counter to compare with positions.size().
        for (int i = 0; i < positions.size(); ++i) {
            
            if (!Board.getInstance().getCoordinates().get(
                    BoardConst.getPostionFromIntegers(positions.get(i))
                    ).getOnPositionChessMan().isNullChessMan()) { 
                // Here loop has encountered a chess man :
                // Check for attack. Meaning if it is last loop then check that
                // position ahead is occupied by a != color chessman.
                
                if (i + 1 == positions.size() &&
                        !Board.getInstance().getCoordinates().get(
                        BoardConst.getPostionFromIntegers(positions.get(i))
                        ).getOnPositionChessMan().isNullChessMan() &&
                        !Board.getInstance().getCoordinates().get(
                        BoardConst.getPostionFromIntegers(positions.get(i))
                        ).getOnPositionChessMan().getCOLOR().equals(this.getCOLOR())) {
                    // It is a valid attack situation.
                    // We are on last position check, meaning all previous
                    // positions have been checked. Move is validated :
                    return true;
                } else {
                    return false;
                }
                
            } else { // Position is free.
                // Add 1 to counter.
                ++counter;
            }
        }
        
        // Finally compare counter and array list size. If == then the move can
        // be validated.
        return counter == positions.size();
        // Move has been checked step by step and is valid.
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Apply castling to rook and throw a CastlingException towards GUI.
     * @param posFrom
     * @param posTo
     */
    public void applyRookKingCastling(final String posFrom, final String posTo) {
        
        // Move Rook, King move's itself on isValidMove() return true.
        Board.getInstance().getCoordinates().get(posTo).setOnPositionChessMan(this);
        Board.getInstance().getCoordinates().get(posFrom).setOnPositionChessMan(new NullChessMan());
        
        // Finally call observers (GUI & ChessGame).
        for (CastlingObserver observer : castlingObserver) {
            observer.applyCastling(posFrom, posTo);
        }
        
    }
    
    /**
    * Add a new observer.
    * @param castlingObserver
    */
    public void addCastlingObserver(final CastlingObserver castlingObserver) {
        // In the case of ChessMan subclasses deserialization, check that transient
        // property ArrayList<> castlingObserver collection is null. If so, then
        // = new ArrayList.
        if (this.castlingObserver == null) {
            this.castlingObserver = new ArrayList<>();
        }
        this.castlingObserver.add(castlingObserver);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setters">
    public ArrayList<CastlingObserver> getCastlingObserver() {
        return this.castlingObserver;
    }
    //</editor-fold>
    
}
