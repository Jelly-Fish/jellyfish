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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Global chess & chess board constantes.
 * @author Thomas.H Warner 2014
 */
public class BoardConst {
    
    //<editor-fold defaultstate="collapsed" desc="Color constants">
    /**
     * White chessman.
     */
    public static final String WHITE = "white";
    
    /**
     * White chessman char value.
     */
    public static final Character WHITE_CHAR = 'w';
    
    /**
     * Black chessman.
     */
    public static final String BLACK = "black";
    
    /**
     * Black chessman char value.
     */
    public static final Character BLACK_CHAR = 'b';
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Chess positions constants">
    public static final String A1 = "a1";
    public static final String A2 = "a2";
    public static final String A3 = "a3";
    public static final String A4 = "a4";
    public static final String A5 = "a5";
    public static final String A6 = "a6";
    public static final String A7 = "a7";
    public static final String A8 = "a8";
    public static final String B1 = "b1";
    public static final String B2 = "b2";
    public static final String B3 = "b3";
    public static final String B4 = "b4";
    public static final String B5 = "b5";
    public static final String B6 = "b6";
    public static final String B7 = "b7";
    public static final String B8 = "b8";
    public static final String C1 = "c1";
    public static final String C2 = "c2";
    public static final String C3 = "c3";
    public static final String C4 = "c4";
    public static final String C5 = "c5";
    public static final String C6 = "c6";
    public static final String C7 = "c7";
    public static final String C8 = "c8";
    public static final String D1 = "d1";
    public static final String D2 = "d2";
    public static final String D3 = "d3";
    public static final String D4 = "d4";
    public static final String D5 = "d5";
    public static final String D6 = "d6";
    public static final String D7 = "d7";
    public static final String D8 = "d8";
    public static final String E1 = "e1";
    public static final String E2 = "e2";
    public static final String E3 = "e3";
    public static final String E4 = "e4";
    public static final String E5 = "e5";
    public static final String E6 = "e6";
    public static final String E7 = "e7";
    public static final String E8 = "e8";
    public static final String F1 = "f1";
    public static final String F2 = "f2";
    public static final String F3 = "f3";
    public static final String F4 = "f4";
    public static final String F5 = "f5";
    public static final String F6 = "f6";
    public static final String F7 = "f7";
    public static final String F8 = "f8";
    public static final String G1 = "g1";
    public static final String G2 = "g2";
    public static final String G3 = "g3";
    public static final String G4 = "g4";
    public static final String G5 = "g5";
    public static final String G6 = "g6";
    public static final String G7 = "g7";
    public static final String G8 = "g8";
    public static final String H1 = "h1";
    public static final String H2 = "h2";
    public static final String H3 = "h3";
    public static final String H4 = "h4";
    public static final String H5 = "h5";
    public static final String H6 = "h6";
    public static final String H7 = "h7";
    public static final String H8 = "h8";
    
    public static final String A_LABEL = "a";
    public static final String B_LABEL = "b";
    public static final String C_LABEL = "c";
    public static final String D_LABEL = "d";
    public static final String E_LABEL = "e";
    public static final String F_LABEL = "f";
    public static final String G_LABEL = "g";
    public static final String H_LABEL = "h";
    public static final String ONE_LABEL = "1";
    public static final String TWO_LABEL = "2";
    public static final String THREE_LABEL = "3";
    public static final String FOUR_LABEL = "4";
    public static final String FIVE_LABEL = "5";
    public static final String SIX_LABEL = "6";
    public static final String SEVEN_LABEL = "7";
    public static final String EIGHT_LABEL = "8";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Chess positions hashmapped to integers">
    /**
     * Chess classic coordinates (A3, G7...) mapped to integer[] coordiantes :
     * from left top to left bottom. Example : A8 is first = [1, 1], H1 is 
     * last and = [8, 8]. This hashmap's purpose is to be able to calculte if
     * a move is valid for a specific chessman : Knight can only perform a
     * maximum of 4 moves (1up + 1up-right or 1up-left + 1left ect...) and integer 
     * values making possible move search easier.
     */
    public static final Map<String, Integer[]> coordinatesIntegerMap = new HashMap<>();
    static 
    {
        coordinatesIntegerMap.put(A8, new Integer[]{ 1, 1 });
        coordinatesIntegerMap.put(B8, new Integer[]{ 1, 2 });
        coordinatesIntegerMap.put(C8, new Integer[]{ 1, 3 });
        coordinatesIntegerMap.put(D8, new Integer[]{ 1, 4 });
        coordinatesIntegerMap.put(E8, new Integer[]{ 1, 5 });
        coordinatesIntegerMap.put(F8, new Integer[]{ 1, 6 });
        coordinatesIntegerMap.put(G8, new Integer[]{ 1, 7 });
        coordinatesIntegerMap.put(H8, new Integer[]{ 1, 8 });
        coordinatesIntegerMap.put(A7, new Integer[]{ 2, 1 });
        coordinatesIntegerMap.put(B7, new Integer[]{ 2, 2 });
        coordinatesIntegerMap.put(C7, new Integer[]{ 2, 3 });
        coordinatesIntegerMap.put(D7, new Integer[]{ 2, 4 });
        coordinatesIntegerMap.put(E7, new Integer[]{ 2, 5 });
        coordinatesIntegerMap.put(F7, new Integer[]{ 2, 6 });
        coordinatesIntegerMap.put(G7, new Integer[]{ 2, 7 });
        coordinatesIntegerMap.put(H7, new Integer[]{ 2, 8 });
        coordinatesIntegerMap.put(A6, new Integer[]{ 3, 1 }); 
        coordinatesIntegerMap.put(B6, new Integer[]{ 3, 2 });
        coordinatesIntegerMap.put(C6, new Integer[]{ 3, 3 });
        coordinatesIntegerMap.put(D6, new Integer[]{ 3, 4 });
        coordinatesIntegerMap.put(E6, new Integer[]{ 3, 5 });
        coordinatesIntegerMap.put(F6, new Integer[]{ 3, 6 });
        coordinatesIntegerMap.put(G6, new Integer[]{ 3, 7 });
        coordinatesIntegerMap.put(H6, new Integer[]{ 3, 8 });
        coordinatesIntegerMap.put(A5, new Integer[]{ 4, 1 }); 
        coordinatesIntegerMap.put(B5, new Integer[]{ 4, 2 });
        coordinatesIntegerMap.put(C5, new Integer[]{ 4, 3 });
        coordinatesIntegerMap.put(D5, new Integer[]{ 4, 4 });
        coordinatesIntegerMap.put(E5, new Integer[]{ 4, 5 });
        coordinatesIntegerMap.put(F5, new Integer[]{ 4, 6 });
        coordinatesIntegerMap.put(G5, new Integer[]{ 4, 7 });
        coordinatesIntegerMap.put(H5, new Integer[]{ 4, 8 });
        coordinatesIntegerMap.put(A4, new Integer[]{ 5, 1 }); 
        coordinatesIntegerMap.put(B4, new Integer[]{ 5, 2 });
        coordinatesIntegerMap.put(C4, new Integer[]{ 5, 3 });
        coordinatesIntegerMap.put(D4, new Integer[]{ 5, 4 });
        coordinatesIntegerMap.put(E4, new Integer[]{ 5, 5 });
        coordinatesIntegerMap.put(F4, new Integer[]{ 5, 6 });
        coordinatesIntegerMap.put(G4, new Integer[]{ 5, 7 });
        coordinatesIntegerMap.put(H4, new Integer[]{ 5, 8 });
        coordinatesIntegerMap.put(A3, new Integer[]{ 6, 1 }); 
        coordinatesIntegerMap.put(B3, new Integer[]{ 6, 2 });
        coordinatesIntegerMap.put(C3, new Integer[]{ 6, 3 });
        coordinatesIntegerMap.put(D3, new Integer[]{ 6, 4 });
        coordinatesIntegerMap.put(E3, new Integer[]{ 6, 5 });
        coordinatesIntegerMap.put(F3, new Integer[]{ 6, 6 });
        coordinatesIntegerMap.put(G3, new Integer[]{ 6, 7 });
        coordinatesIntegerMap.put(H3, new Integer[]{ 6, 8 });
        coordinatesIntegerMap.put(A2, new Integer[]{ 7, 1 }); 
        coordinatesIntegerMap.put(B2, new Integer[]{ 7, 2 });
        coordinatesIntegerMap.put(C2, new Integer[]{ 7, 3 });
        coordinatesIntegerMap.put(D2, new Integer[]{ 7, 4 });
        coordinatesIntegerMap.put(E2, new Integer[]{ 7, 5 });
        coordinatesIntegerMap.put(F2, new Integer[]{ 7, 6 });
        coordinatesIntegerMap.put(G2, new Integer[]{ 7, 7 });
        coordinatesIntegerMap.put(H2, new Integer[]{ 7, 8 });
        coordinatesIntegerMap.put(A1, new Integer[]{ 8, 1 }); 
        coordinatesIntegerMap.put(B1, new Integer[]{ 8, 2 });
        coordinatesIntegerMap.put(C1, new Integer[]{ 8, 3 });
        coordinatesIntegerMap.put(D1, new Integer[]{ 8, 4 });
        coordinatesIntegerMap.put(E1, new Integer[]{ 8, 5 });
        coordinatesIntegerMap.put(F1, new Integer[]{ 8, 6 });
        coordinatesIntegerMap.put(G1, new Integer[]{ 8, 7 });
        coordinatesIntegerMap.put(H1, new Integer[]{ 8, 8 });
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Chess positions string value collection">
    /**
     * Collection of all string board position.
     */
    public static final List<String> boardPositions = new ArrayList<>(); 
    static
    {
        boardPositions.add(H8);
        boardPositions.add(H7);
        boardPositions.add(H6);
        boardPositions.add(H5);
        boardPositions.add(H4);
        boardPositions.add(H3);
        boardPositions.add(H2);
        boardPositions.add(H1);
        boardPositions.add(G8);
        boardPositions.add(G7);
        boardPositions.add(G6);
        boardPositions.add(G5);
        boardPositions.add(G4);
        boardPositions.add(G3);
        boardPositions.add(G2);
        boardPositions.add(G1);
        boardPositions.add(F8);
        boardPositions.add(F7);
        boardPositions.add(F6);
        boardPositions.add(F5);
        boardPositions.add(F4);
        boardPositions.add(F3);
        boardPositions.add(F2);
        boardPositions.add(F1);
        boardPositions.add(E8);
        boardPositions.add(E7);
        boardPositions.add(E6);
        boardPositions.add(E5);
        boardPositions.add(E4);
        boardPositions.add(E3);
        boardPositions.add(E2);
        boardPositions.add(E1);
        boardPositions.add(D8);
        boardPositions.add(D7);
        boardPositions.add(D6);
        boardPositions.add(D5);
        boardPositions.add(D4);
        boardPositions.add(D3);
        boardPositions.add(D2);
        boardPositions.add(D1);
        boardPositions.add(C8);
        boardPositions.add(C7);
        boardPositions.add(C6);
        boardPositions.add(C5);
        boardPositions.add(C4);
        boardPositions.add(C3);
        boardPositions.add(C2);
        boardPositions.add(C1);
        boardPositions.add(B8);
        boardPositions.add(B7);
        boardPositions.add(B6);
        boardPositions.add(B5);
        boardPositions.add(B4);
        boardPositions.add(B3);
        boardPositions.add(B2);
        boardPositions.add(B1);
        boardPositions.add(A8);
        boardPositions.add(A7);
        boardPositions.add(A6);
        boardPositions.add(A5);
        boardPositions.add(A4);
        boardPositions.add(A3);
        boardPositions.add(A2);
        boardPositions.add(A1);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Get value from key on coordinatesIntegerMap Hashmap.
     * @param xy
     * @return 
     */
    public static String getPostionFromIntegers(final Integer[] xy) {
        
        for (Entry<String, Integer[]> entry : coordinatesIntegerMap.entrySet()) {
            if (entry.getValue()[0].equals(xy[0]) && entry.getValue()[1].equals(xy[1])) {
                return entry.getKey();
            }
        }
        
        return null;
    }
    //</editor-fold>
   
}
