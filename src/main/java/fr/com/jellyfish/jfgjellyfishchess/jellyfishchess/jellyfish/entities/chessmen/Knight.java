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
 ******************************************************************************
 */

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a knight chessman in a chess game.
 *
 * @author Thomas.H Warner 2014
 */
public class Knight extends AbstractChessMan {

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     *
     * @param COLOR
     * @param valuation
     * @param alive
     * @param boardPosition
     * @param nullChessMan
     * @param fenValue
     */
    public Knight(final String COLOR, final float valuation, final boolean alive,
            final Position boardPosition, final boolean nullChessMan,
            final char fenValue) {
        super(COLOR, valuation, alive, boardPosition, nullChessMan, fenValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden methods">    
    @Override
    public boolean isValidMove(final Integer[] xyFrom, final Integer[] xyTo) {
        // Check Position class's between from & too.
        // A knight can move anyware in i's reach and move above other chessmen.
        // ! Color has no importance here, a knignt moves the same way if it's
        // black or white.

        // Calculate all possible position and compare with new numeric position.
        List<Integer[]> possiblePositions = knightPossibleMoves(xyFrom);

        // Compare...
        for (Integer[] position : possiblePositions) {
            if (position[0].equals(xyTo[0]) && position[1].equals(xyTo[1])) {
                // Position match's, now check for color if attacking, also
                // for null chessman position.
                if (!Board.getInstance().getCoordinates().get(BoardConst.getPostionFromIntegers(xyTo)
                ).getOnPositionChessMan().getCOLOR().equals(this.getCOLOR())
                        || Board.getInstance().getCoordinates().get(
                                BoardConst.getPostionFromIntegers(xyTo)).getOnPositionChessMan().isNullChessMan()) {
                    return true;
                }
            }
        }

        // Finally, if we have reached this point, move is invalid.
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     *
     * @param xyFrom
     * @return List<Integer[]> 8 knights possible numeric coordinate moves.
     */
    private List<Integer[]> knightPossibleMoves(final Integer[] xyFrom) {
        List<Integer[]> results = new ArrayList<>();
        int newY, newX;

        // Possible moves clockwise... 8 ticks :)
        // Move 1.
        newX = xyFrom[0] - 2;
        newY = xyFrom[1] - 1;
        results.add(new Integer[]{newX, newY});

        // Move 2.
        newX = xyFrom[0] - 2;
        newY = xyFrom[1] + 1;
        results.add(new Integer[]{newX, newY});

        // Move 3.
        newX = xyFrom[0] - 1;
        newY = xyFrom[1] + 2;
        results.add(new Integer[]{newX, newY});

        // Move 4.
        newX = xyFrom[0] + 1;
        newY = xyFrom[1] + 2;
        results.add(new Integer[]{newX, newY});

        // Move 5.
        newX = xyFrom[0] + 2;
        newY = xyFrom[1] + 1;
        results.add(new Integer[]{newX, newY});

        // Move 6.
        newX = xyFrom[0] + 2;
        newY = xyFrom[1] - 1;
        results.add(new Integer[]{newX, newY});

        // Move 7.
        newX = xyFrom[0] + 1;
        newY = xyFrom[1] - 2;
        results.add(new Integer[]{newX, newY});

        // Move 8
        newX = xyFrom[0] - 1;
        newY = xyFrom[1] - 2;
        results.add(new Integer[]{newX, newY});

        return results;
    }
    //</editor-fold>

}
