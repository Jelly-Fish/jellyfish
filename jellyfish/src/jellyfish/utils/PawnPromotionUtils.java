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

import jellyfish.entities.chessmen.Knight;
import jellyfish.entities.chessmen.Bishop;
import jellyfish.entities.chessmen.Rook;
import jellyfish.entities.chessmen.AbstractChessMan;
import jellyfish.entities.chessmen.Queen;
import jellyfish.entities.chessmen.King;
import jellyfish.constants.BoardConst;
import jellyfish.constants.ExceptionConst;
import jellyfish.constants.FENConst;
import jellyfish.constants.UCIConst;
import jellyfish.entities.Position;
import jellyfish.exceptions.PawnPromotionException;
import jellyfish.interfaces.CheckObserver;

/**
 * @author Thomas.H Warner 2014
 */
public class PawnPromotionUtils {
    
    /**
     * Apply promotion to a Pawn class that has reached opponents home side.
     * @param promotionColor
     * @param promotionType
     * @param posTo
     * @param chessManKing
     * @param chessManOponentKing
     * @param checkObserver
     * @return ChessMan
     * @throws jellyfish.exceptions.PawnPromotionException 
     */
    public static AbstractChessMan applyPawnPromotion(final String promotionColor, final char promotionType,
        final Position posTo, final King chessManKing, final King chessManOponentKing,
        final CheckObserver checkObserver) throws PawnPromotionException {
        
        char fenValue = UCIConst.DUMMY;
        AbstractChessMan chessMan = null;
        
        // Determinate promotionColor, promotion class type then instanciate a new type of
        // ChessMan sub class to return.
        // Switch on all type offered by a Pawn promotion :
        switch (promotionType) {
            case UCIConst.QUEEN_PROMOTION:
                // Promote as Queen
                fenValue = promotionColor.equals(BoardConst.WHITE) ? FENConst.WHITE_QUEEN : FENConst.BLACK_QUEEN;
                chessMan = new Queen(promotionColor, 8.8f, true, posTo, false, fenValue);
                break;
            case UCIConst.BISHOP_PROMOTION:
                // Promote as Bishop
                fenValue = promotionColor.equals(BoardConst.WHITE) ? FENConst.WHITE_BISHOP : FENConst.BLACK_BISHOP;
                chessMan = new Bishop(promotionColor, 3.33f, true, posTo, false, fenValue);
                break;
            case UCIConst.KNIGHT_PROMOTION:
                // Promote as Knight
                fenValue = promotionColor.equals(BoardConst.WHITE) ? FENConst.WHITE_KNIGHT : FENConst.BLACK_KNIGHT;
                chessMan = new Knight(promotionColor, 3.2f, true, posTo, false, fenValue);
                break;
            case UCIConst.ROOK_PROMOTION:
                // Promote as Rook
                fenValue = promotionColor.equals(BoardConst.WHITE) ? FENConst.WHITE_ROOK : FENConst.BLACK_ROOK;
                chessMan = new Rook(promotionColor, 5.1f, true, posTo, false, fenValue);
                break;
            default:
                break;
        }
        
        if (chessMan != null) {
            chessMan.setChessManKing(chessManKing);
            chessMan.setChessManOponentKing(chessManOponentKing);
            chessMan.setCheckObserver(checkObserver);
            return chessMan;
        } else {
            throw new PawnPromotionException(ExceptionConst.EX_PAWNPROMOTION_MSG);
        }
        
    }
    
}