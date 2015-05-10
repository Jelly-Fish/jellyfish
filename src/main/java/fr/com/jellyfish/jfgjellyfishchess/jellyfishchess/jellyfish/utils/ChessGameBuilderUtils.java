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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.ChessGameBuildException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;

/**
 * @author Thomas.H Warner 2014
 */
public class ChessGameBuilderUtils {

    /**
     * Build chess game by class reference.
     * @param driver
     * @param gameType
     * @param engineColor
     * @param engineOponentColor
     * @param depth
     * @param loadingPreviousGame
     * @param seconds
     * @return new ChessGame or ChessGame sub class. 
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.ChessGameBuildException 
     */
    public static ChessGame buildGame(final AbstractChessGameDriver driver, final String gameType, 
            final char engineColor, final char engineOponentColor, final int depth, 
            final boolean loadingPreviousGame, final int seconds) throws ChessGameBuildException {
    
        if (gameType == null) {
            throw new ChessGameBuildException(ExceptionConst.EX_GAME_BUILD_ERROR);
        }
        
        ChessGame game = null;
        
        switch (gameType) {
            case GameTypeConst.CHESS_GAME :
                return new ChessGame(driver, engineColor, engineOponentColor, depth, 
                    loadingPreviousGame, seconds);
            // case ...
            //public static ChessGame buildGame(final MainUiDriver driver, final String gameType, 
            //final char engineColor, final char engineOponentColor, final int depth, 
            //final boolean loadingPreviousGame, final Object engineOponentTimerRef,
            //final Object engineTimerRef) throws ChessGameBuildException {
        }
        
        return game;
    }
    
}
