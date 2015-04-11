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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.FENConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas.H Warner 2014
 */
public class FENConverter {
    
    /**
     * Chess labels for each column.
     */
    private static final Character[] columnLabels = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    
    /**
     * Convert a FEN string to Map of <string:position (a1, g6 ect), char fen value>. 
     * @param fen
     * @return HashMap<String, Character>
     * @throws FenConvertionException 
     */
    public static Map<String, Character> givePositionsFromFen(String fen) 
        throws FenConvertionException {
        
        int lineNumber = 8;
        int row = 0;
        
        fen = fen.replace(UCIConst.POS + CommonConst.SPACE_STR + UCIConst.FEN + 
            CommonConst.SPACE_STR, CommonConst.EMPTY_STR);
        fen = fen.replaceAll(CommonConst.SPACE_STR, CommonConst.EMPTY_STR);
        String[] fenArray = fen.split(String.valueOf(FENConst.SLASH));
        
        
        Map<String, Character> positions = new HashMap<>();
        
        for (int i = 0; i < fenArray.length; ++i) { // For each line.
            
            char[] lineArray = fenArray[i].toCharArray();
            for (int j = 0; j < lineArray.length; ++j) {
                
                if (Character.isDigit(lineArray[j])) {
                    
                    String nullPositionsStr = String.valueOf(lineArray[j]);
                    int nullPositions = Integer.valueOf(nullPositionsStr);
                    for (int k = 0; k < nullPositions; ++k) {
                        positions.put(String.valueOf(columnLabels[row]) + 
                            String.valueOf(lineNumber), null);
                        ++row;
                    }
                    
                } else if (Character.isAlphabetic(lineArray[j])) {
                    positions.put(String.valueOf(columnLabels[row]) + 
                        String.valueOf(lineNumber), lineArray[j]);
                        ++row;
                }
            } // End line iteration.
            --lineNumber;
            row = 0;
        } // End chess board iteration.

        if (lineNumber == 0) {
            return positions;
        } else {
            throw new FenConvertionException(String.format(ExceptionConst.EX_FEN_CONVERTION_MSG,
                String.valueOf(lineNumber), String.valueOf(positions.size())));
        }
    }
    
}
