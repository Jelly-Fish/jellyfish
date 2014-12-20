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

package jellyfish.utils;

import jellyfish.constants.BoardConst;
import jellyfish.constants.FENConst;
import jellyfish.constants.UCIConst;
import jellyfish.entities.Board;

/**
 * FEN (Forsyth–Edwards Notation) notation utils.
 * @author Thomas.H Warner 2014
 */
public class FENUtils {

    /**
     * Build FEN string from jellyfish.entities.board's Position's CheesManclasses.
     * sub classes.
     * @param engineOponentColor
     * @return 
     */
    public static String buildFENString(final char engineOponentColor) {
        
        // String builder for building FEN String.
        StringBuilder fen = new StringBuilder();
        // Board class instance for Position calsses and their onPositionChessMan().
        Board board = Board.getInstance();
        // A FEN character from corresponding to a chess man.
        Object fenUnit = new Object();
        // Counter for empty squares, example: an empty line == /8/
        int emptySquares = 0;
        
        fen.append(UCIConst.POS + UCIConst.SPACE + UCIConst.FEN +
                UCIConst.SPACE);
        
        // For a maximum of 8 loops: a chess board has a maximum 8 lines.
        // Loop through, line by line (xy coordinates are inverted to make FEN
        // string building easier: top-left = y:1 x:1.).
        for (int i = 1; i < 9; ++i) { 
            for (int j = 1; j < 10; ++j) {
                if (j > 8) { // If j is out of chess board bounds.
                    // Check couter value again :
                    if (emptySquares > 0 && emptySquares < 9) {
                        Integer emptySquaresSum = new Integer(emptySquares);
                        fen.append(emptySquaresSum.toString());
                        emptySquares = 0; // Reset the counter.
                    }
                    
                    if (i < 8) { // Only appen slash if it's not last loop.
                        fen.append(FENConst.SLASH);
                    }
                } else {
                    
                    fenUnit = board.getCoordinates().get(
                        BoardConst.getPostionFromIntegers(new Integer[]{ i, j })
                        ).getOnPositionChessMan().getFenValue();
                    
                    // If the on position chessman is an instance of NullChessMan,
                    // then fenUnit will be an Integer need to build and calculate
                    // sum of empty spaces on a board line :
                    if (fenUnit instanceof Integer) {
                        emptySquares += (int)fenUnit;
                    } else if (fenUnit instanceof Character) {
                        
                        // If the fenUnit is an instance of a Character Object then,
                        // the posiion contains a ChessMan sub class other than 
                        // NullChessMan and it posesses a Charater property for FEN
                        // string notation building. 
                        // Ex: Pawn class has char fenValue == p OR P for Whites.                       
                        if (emptySquares > 0 && emptySquares < 9) {
                            Integer emptySquaresSum = new Integer(emptySquares);
                            fen.append(emptySquaresSum.toString());
                            emptySquares = 0;
                        } 
                        fen.append(fenUnit);
                    }
                }
            }
        }
        
        return fen.toString();
    }
    
}
