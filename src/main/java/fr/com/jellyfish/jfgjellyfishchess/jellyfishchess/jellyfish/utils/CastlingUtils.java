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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.King;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Knight;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas.H Waner 2014
 */
public class CastlingUtils {
    
    //<editor-fold defaultstate="collapsed" desc="Public static methods">
    /**
     * Get all possible positions for a rook move depending on direction.
     * This ChessMan class method is redefined here in King class. 
     * See ChessMan class.
     * @param direction
     * @param xyFrom
     * @return positions
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException
     */
    public static List<Integer[]> getRookPositions(char direction, final Integer[] xyFrom) throws CastlingUtilsException {
        
        if (!CastlingUtils.checkInputNonNull((Object[]) xyFrom)) {
            throw new CastlingUtilsException();
        }
        
        List<Integer[]> positions = new ArrayList<>();
        
        // Collect all possible numeric coordinates depending on direction.
        final int min = 0;
        final int max = 9;
        int from; // Numeric coordinates to iterate on.
        switch (direction) {
            case CommonConst.U_CHAR_LOWERCASE:
                from = xyFrom[0];
                for (int i = 1; from > min; ++i) {
                    positions.add(new Integer[]{ xyFrom[0] - i, xyFrom[1] });
                    --from;
                }
                break;
            case CommonConst.D_CHAR_LOWERCASE:
                from = xyFrom[0];
                for (int i = 1; from < max; ++i) {
                    positions.add(new Integer[]{ xyFrom[0] + i, xyFrom[1] });
                    ++from;
                }
                break;
            case CommonConst.L_CHAR_LOWERCASE:
                from = xyFrom[1];
                for (int i = 1; from > min; ++i) {
                    positions.add(new Integer[]{ xyFrom[0], xyFrom[1] - i });
                    --from;
                }
                break;
            case CommonConst.R_CHAR_LOWERCASE:
                from = xyFrom[1];
                for (int i = 1; from < max; ++i) {
                    positions.add(new Integer[]{ xyFrom[0], xyFrom[1] + i });
                    ++from;
                }
                break;
            default:
                throw new CastlingUtilsException();
        }
        
        return positions;
    }
    
    /**
     * Get possible possitions for a bishop move depending on which diagonal it
     * has chosen to take.
     * @param diagonal
     * @param xyFrom
     * @return List<Integer[]> positions
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException
     */
    public static List<Integer[]> getBihopDiagonals(final int diagonal,
            final Integer[] xyFrom) throws CastlingUtilsException {
        
        if (!CastlingUtils.checkInputNonNull((Object[]) xyFrom)) {
            throw new CastlingUtilsException();
        }
        
        List<Integer[]> positions = new ArrayList<>();
        
        // Collect all possible numeric coordinates.
        // 1: up.right - 2: down.right - 3: up.left - 4: down.left
        int maxPos = 8;
        int minPos = 1;
        int x, y;
        switch (diagonal) {
            case 1:
                // Loop for < 8 (maxPos): a bishop can only take a miximum of 7
                // steps in any case. Add moves to positions array list of 
                // Integer[]. A bishop can hit two different sides on a move (a
                // rook can't), in order to prevent NullPointer's by adding invalid
                // int coordinates such as 0 or 9 check on each loop that coordinates
                // are not above 8 or lower than 1. Also check for xyTo Integer 
                // position equality : if == they no need to loop foward; return
                // the array list. The break statement is logically never reached
                // , I left it anyway to respect switch syntax.
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] - i;
                    x = xyFrom[1] + i;
                    positions.add(new Integer[]{ y, x });
                    if (y < minPos || x > maxPos) {
                        return positions;
                    }
                }
                break;
            case 2:
                // Same as above but for down.right move.
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] + i;
                    x = xyFrom[1] + i;
                    positions.add(new Integer[]{ y, x });
                    if (y > maxPos || x > maxPos) {
                        return positions;
                    }
                }
                break;
            case 3:
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] - i;
                    x = xyFrom[1] - i;
                    positions.add(new Integer[]{ y, x });
                    if (y < minPos || x < minPos) {
                        return positions;
                    }
                }
                break;
            case 4:
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] + i;
                    x = xyFrom[1] - i;
                    positions.add(new Integer[]{ y, x });
                    if (y > maxPos || x < minPos) {
                        return positions;
                    }
                }
                break;
            default:
                throw new CastlingUtilsException();
        }
        
        return positions; 
    }
    
    /**
     * Get all possible knight attacks from a xy chess position.
     * @param xyPos
     * @param color
     * @return ArrayList of Integer objects.
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException
     */
    public static ArrayList<Integer[]> getPossibleKnightAttacks(final Integer[] xyPos, final String color) throws CastlingUtilsException {
        
        if (!CastlingUtils.checkInputNonNull(xyPos, color)) {
            throw new CastlingUtilsException();
        }
        
        // All possible positions to check for knight attacks on a King move.
        ArrayList<Integer[]> positions = new ArrayList<>();
        // Array list of position to return after all > 8 or < 1 xy coordinates
        // have been removed (/!\must avoid a null pointer).
        ArrayList<Integer[]> realPositions = new ArrayList<>();
        
        // There are 8 positions in this situation.
        // Start from top left (d6 attacks e4) and go clockwise.
        positions.add(new Integer[]{ xyPos[0] - 2, xyPos[1] - 1 });
        positions.add(new Integer[]{ xyPos[0] - 2, xyPos[1] + 1 });
        positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] + 2 });
        positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] + 2 });
        positions.add(new Integer[]{ xyPos[0] + 2, xyPos[1] + 1 });
        positions.add(new Integer[]{ xyPos[0] + 2, xyPos[1] - 1 });
        positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] - 2 });
        positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] - 2 });
        
        // Trim out off board positions, non oponent chessmen and empty positions:
        for (Integer[] xy : positions) {
            // If safe/real position then add to realPositions list.
            if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0 &&
                Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan() instanceof Knight &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().isNullChessMan() &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().getColor().equals(color)) {
                
                realPositions.add(xy);
            }
        }
        
        // Return trimed positions.
        return realPositions;
    }

    /**
     * Get all possible knight attacks from a xy chess position.
     * @param xyPos
     * @param color
     * @return 
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException 
     */
    public static ArrayList<Integer[]> getPossiblePawnAttacks(final Integer[] xyPos, final String color) throws CastlingUtilsException {
        
        if (!CastlingUtils.checkInputNonNull(xyPos, color)) {
            throw new CastlingUtilsException();
        }
        
        // All possible positions to check for Pawn attacks on a King move.
        ArrayList<Integer[]> positions = new ArrayList<>();
        // Array list of position to return after all > 8 or < 1 xy coordinates
        // have been removed (/!\must avoid a null pointer).
        ArrayList<Integer[]> realPositions = new ArrayList<>();
        
        // 4 possibliltes in this situation. Start top left, go clockwise.
        // Add to positions List depending on color equality :
        // Blacks, pawns move ahead, whites, pawns move/attack moving back on
        // coordinates. If check is not done, black pawns will check king moving 
        // like white pawns.
        if (color.equals(BoardConst.WHITE)) {
            positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] - 1 });
            positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] + 1 });
        } else if (color.equals(BoardConst.BLACK)) {
            positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] + 1 });
            positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] - 1 });
        }
        
        // Trim out off board, null and non oponent positions,
        for (Integer[] xy : positions) {
            // If safe/real position then add to realPositions list.
            if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0 &&
                Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan() instanceof Pawn &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().isNullChessMan() &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().getColor().equals(color)) {
                
                realPositions.add(xy);
            }
        }
        
        // Return trimed positions.
        return realPositions;
    }
    
    /**
     * Is it a possible castling attempt ?
     * @param xyFrom
     * @param xyTo
     * @return boolean
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException
     */
    public static boolean isCastling(final Integer[] xyFrom, final Integer[] xyTo) throws CastlingUtilsException {
        
        if (!CastlingUtils.checkInputNonNull(xyFrom, xyTo)) {
            throw new CastlingUtilsException();
        }
        return ((xyFrom[1] - xyTo[1] > 1) || (xyTo[1] - xyFrom[1] > 1)) && xyFrom[0] == xyTo[0];
    }
    
    /**
     * Get possible attacks for King to King.
     * @param xyPos
     * @param color
     * @return ArrayList of Integer[]
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.CastlingUtilsException
     */
    public static ArrayList<Integer[]> getPossibleKingAttacks(final Integer[] xyPos, final String color) throws CastlingUtilsException {
    
        if (!CastlingUtils.checkInputNonNull(xyPos, color)) {
            throw new CastlingUtilsException();
        }
        
        ArrayList<Integer[]> positions = new  ArrayList<>();
        ArrayList<Integer[]> realPositions = new  ArrayList<>();

        // 8 posible position surronding position to analyze.
        // Start at top and go clockwise :
        positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] });
        positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] + 1 });
        positions.add(new Integer[]{ xyPos[0], xyPos[1] + 1 });
        positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] + 1 });
        positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] });
        positions.add(new Integer[]{ xyPos[0] + 1, xyPos[1] - 1 });
        positions.add(new Integer[]{ xyPos[0], xyPos[1] - 1 });
        positions.add(new Integer[]{ xyPos[0] - 1, xyPos[1] - 1 });
         
        // Trim out off board, null and non oponent positions.
        for (Integer[] xy : positions) {
            // If safe/real position and != thisColor &&
            // !null ChessMan and instance of King then add to realPositions list.
            if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0 &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().isNullChessMan() &&
                !Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan().getColor().equals(color) &&
                Board.getInstance().getCoordinates().get(
                BoardConst.getPostionFromIntegers(xy)
                ).getOnPositionChessMan() instanceof King) {
                // Oponent King is attacking and checking King on this move position.
                realPositions.add(xy);
            }
        }
        
        // Return trimed list of positions:
        return realPositions;
    }
    
    /**
     * @param obj
     * @return is any entry or input equal to null.
     */
    private static boolean checkInputNonNull(Object ... obj) {
        for (Object o : obj) {
            if (o == null)
                return false;
        }
        
        return true;
    }
    //</editor-fold>
    
}
