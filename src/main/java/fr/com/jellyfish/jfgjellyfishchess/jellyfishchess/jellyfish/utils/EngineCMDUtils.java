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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Thomas.H Warner 2014
 */
public class EngineCMDUtils {
    
    /**
     * Get UCI command : position startpos moves move1 move2...
     * @param moves
     * @return String UCI command
     */
    public static String buildMovesString(final LinkedHashMap<Integer, String> moves) {
        
        StringBuilder uciCmd = new StringBuilder();
        
        uciCmd.append(UCIConst.POS + UCIConst.SPACE + UCIConst.START_POS + 
            UCIConst.SPACE + UCIConst.MOVES + UCIConst.SPACE);       
        
        for (String move : moves.values()) {
            uciCmd.append(move);
            uciCmd.append(UCIConst.SPACE);
        }
        
        return uciCmd.toString();
    }
    
    /**
     * Get UCI command : position startpos moves move1 move2 ect depending on 
     * ChessGame class property gameIndex that = the current move number.
     * @param moves
     * @param moveIndex
     * @return String UCI command
     */
    public static String buildMovesString(final LinkedHashMap<Integer, String> moves, final int moveIndex) {
        
        StringBuilder uciCmd = new StringBuilder();
        
        uciCmd.append(UCIConst.POS + UCIConst.SPACE + UCIConst.START_POS + 
            UCIConst.SPACE + UCIConst.MOVES + UCIConst.SPACE);       
        
        for (Entry<Integer, String> entry : moves.entrySet()) {
            uciCmd.append(entry.getValue());
            uciCmd.append(UCIConst.SPACE);
            if (entry.getKey() >= moveIndex) {
                return uciCmd.toString();
            }
        }
        
        return uciCmd.toString();
    }
    
}
