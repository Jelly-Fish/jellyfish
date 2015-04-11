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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.dto;

import java.util.LinkedHashMap;

/**
 * @author Thomas.H Warner 2014
 */
public class ChessGameDTO {
    
    /**
     * Color played with.
    */
    private String color;
    
    /**
     * Number of moves played in the game.
     */
    private int moveCount;
    
    /**
     * Date file was saved.
     */
    private String date;
    
    /**
     * Move collection : d2d4 ect.
     */
    private LinkedHashMap<Integer, String> moves;
    
    /**
     * Move collection : d2d4 ect.
     */
    private LinkedHashMap<Integer, String> fenMoves;
    
    /**
     * Time value in seconds for this game. 
     */
    private int seconds;
    
    /**
     * String vale for time played during game, hh:mm:ss
     */
    private String timeStr;
    
    /**
     * Constructor.
     */
    public ChessGameDTO() { }
    
    @Override
    public String toString() {
      return String.format("[ChessGameDTO: date='%s']", this.date);
    }
    
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinkedHashMap<Integer, String> getMoves() {
        return moves;
    }

    public void setMoves(final LinkedHashMap<Integer, String> moves) {
        this.moves = moves;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
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
    
    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    
}
