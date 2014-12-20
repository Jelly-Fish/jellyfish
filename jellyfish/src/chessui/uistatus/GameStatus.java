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

package chessui.uistatus;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * The game status on system exit.
 * @author Thomas.H Waner 2014
 */
public class GameStatus implements Serializable {
   
    /**
    * Map for game moves (moves move1...).
    */
    private LinkedHashMap<Integer, String> gameMoves = new LinkedHashMap<>();
    
    /**
     * Move count.
     */
    private int moveCount;
    
    /**
    * Fen moves collection.
    */
    private LinkedHashMap<Integer, String> fenMoves = new LinkedHashMap<>();
    
    /**
     * Seconds played during the game.
     */
    private int seconds;

    /**
     * Constructor.
     */
    public GameStatus() { }
    
    public LinkedHashMap<Integer, String> getGameMoves() {
        return gameMoves;
    }
        
    public void setGameMoves(final LinkedHashMap<Integer, String> gameMoves) {
        this.gameMoves = gameMoves;
    }
    
    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(final int moveCount) {
        this.moveCount = moveCount;
    }

    public LinkedHashMap<Integer, String> getFenMoves() {
        return fenMoves;
    }

    public void setFenMoves(final LinkedHashMap<Integer, String> fenMoves) {
        this.fenMoves = fenMoves;
    }
    
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(final int seconds) {
        this.seconds = seconds;
    }

}
