/**
 * *****************************************************************************
 * Copyright (c) 2015, Thomas.H Warner. All rights reserved.
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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;

/**
 *
 * @author thw
 */
public class ChessUtils {
    
    /**
     * 
     * @param posFrom
     * @param posTo
     * @param color
     * @return 
     */
    public static boolean isPawnPromotionMove(final ChessSquare posFrom, final ChessSquare posTo, 
            final String color) {
        
        if ((color.equals(UI3DConst.COLOR_W_STR_VALUE) && posFrom.getModel().getType().isWhitePawn()) &&
                posTo.CHESS_POSITION.getStrPositionValue().contains("8")) {
            return true;
        }
        
        if ((color.equals(UI3DConst.COLOR_B_STR_VALUE) && posFrom.getModel().getType().isBlackPawn()) &&
                posTo.CHESS_POSITION.getStrPositionValue().contains("1")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * @param checkmateOutput
     * @param check
     * @return true if stalemate situation.
     */
    public static boolean isKingStaleMate(final boolean checkmateOutput, final boolean check) {
        return checkmateOutput == true && check == false;
    }
    
    /**
     * @param depth
     * @param mouveCount
     * @param uciMessage
     * @return true if so.
     */
    public static boolean isOuputEngineCheckMateNotification(final int depth, final int mouveCount,
            final String uciMessage) {
        
        return (uciMessage.contains(UCIConst.BESTMOVE_NONE_PONDER_NONE) ||
                uciMessage.contains(UCIConst.BESTMOVE_NONE)) &&
                depth >= 1 && 
                mouveCount >= UCIConst.FOOLS_MATE;
    }
    
    /**
     * @param mouveCount
     * @param message
     * @return true if so.
     */
    public static boolean isOuputUiCheckMateNotification(final int mouveCount,
            final String message) {
        
        return (message.contains(UCIConst.PONDER_NONE) || 
                    (!message.contains(UCIConst.PONDER) && message.contains(UCIConst.BESTMOVE))) 
                    && mouveCount >= UCIConst.FOOLS_MATE;
    }
    
}
