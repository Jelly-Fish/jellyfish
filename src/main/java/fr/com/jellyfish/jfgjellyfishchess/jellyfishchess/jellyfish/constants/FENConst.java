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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * FEN notation constants.
 * @author Thomas.H Warner 2014
 */
public class FENConst {
    
    /**
     * Slash for fen building.
     */
    public static final char SLASH = '/';
    
    /**
     * Black pawn fen.
     */
    public static final char BLACK_PAWN = 'p';
    
    /**
     * White pawn fen.
     */
    public static final char WHITE_PAWN = 'P';
    
    /**
     * Black rook fen.
     */
    public static final char BLACK_ROOK = 'r';
    
    /**
     * Black knight fen.
     */
    public static final char BLACK_KNIGHT = 'n';
    
    /**
     * Black bishop fen.
     */
    public static final char BLACK_BISHOP = 'b';
    
    /**
     * Black queen fen.
     */
    public static final char BLACK_QUEEN = 'q';
    
    /**
     * Black king fen.
     */
    public static final char BLACK_KING = 'k';
    
    /**
     * White rook fen.
     */
    public static final char WHITE_ROOK = 'R';
    
    /**
     * White knight fen.
     */
    public static final char WHITE_KNIGHT = 'N';
    
    /**
     * White bishop fen.
     */
    public static final char WHITE_BISHOP = 'B';
    
    /**
     * White queen fen.
     */
    public static final char WHITE_QUEEN = 'Q';
    
    /**
     * White king fen.
     */
    public static final char WHITE_KING = 'K';
    
    /**
     * White next fen.
     */
    public static final char WHITE_CHAR = 'w';
    
    /**
     * Black next fen.
     */
    public static final char BLACK_CHAR = 'b';
    
    /**
     * Promotion char value for a new Queen.
     */
    public static final char QUEEN_PROMOTION = 'q';
    
    /**
     * Fen char dictionnary.
     */
    public static final List<Character> FEN_CHAR_LIST = new ArrayList<>();
    static
    {
        FEN_CHAR_LIST.add(FENConst.BLACK_ROOK);
        FEN_CHAR_LIST.add(FENConst.BLACK_KNIGHT);
        FEN_CHAR_LIST.add(FENConst.BLACK_BISHOP);
        FEN_CHAR_LIST.add(FENConst.BLACK_QUEEN);
        FEN_CHAR_LIST.add(FENConst.BLACK_KING);
        FEN_CHAR_LIST.add(FENConst.BLACK_PAWN);
        FEN_CHAR_LIST.add(FENConst.WHITE_ROOK);
        FEN_CHAR_LIST.add(FENConst.WHITE_KNIGHT);
        FEN_CHAR_LIST.add(FENConst.BLACK_BISHOP);
        FEN_CHAR_LIST.add(FENConst.WHITE_QUEEN);
        FEN_CHAR_LIST.add(FENConst.WHITE_KING);
        FEN_CHAR_LIST.add(FENConst.WHITE_PAWN);
    }
    
}
