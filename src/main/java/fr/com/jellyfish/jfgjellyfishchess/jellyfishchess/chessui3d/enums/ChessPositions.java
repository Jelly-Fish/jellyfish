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
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public enum ChessPositions {

    A1("A1", new Integer[]{1, 1}, -4.0f, 3.0f),
    A2("A2", new Integer[]{1, 2}, -4.0f, 2.0f),
    A3("A3", new Integer[]{1, 3}, -4.0f, 1.0f),
    A4("A4", new Integer[]{1, 4}, -4.0f, 0.0f),
    A5("A5", new Integer[]{1, 5}, -4.0f, -1.0f),
    A6("A6", new Integer[]{1, 6}, -4.0f, -2.0f),
    A7("A7", new Integer[]{1, 7}, -4.0f, -3.0f),
    A8("A8", new Integer[]{1, 8}, -4.0f, -4.0f),
    B1("B1", new Integer[]{2, 1}, -3.0f, 3.0f),
    B2("B2", new Integer[]{2, 2}, -3.0f, 2.0f),
    B3("B3", new Integer[]{2, 3}, -3.0f, 1.0f),
    B4("B4", new Integer[]{2, 4}, -3.0f, 0.0f),
    B5("B5", new Integer[]{2, 5}, -3.0f, -1.0f),
    B6("B6", new Integer[]{2, 6}, -3.0f, -2.0f),
    B7("B7", new Integer[]{2, 7}, -3.0f, -3.0f),
    B8("B8", new Integer[]{2, 8}, -3.0f, -4.0f),
    C1("C1", new Integer[]{3, 1}, -2.0f, 3.0f),
    C2("C2", new Integer[]{3, 2}, -2.0f, 2.0f),
    C3("C3", new Integer[]{3, 3}, -2.0f, 1.0f),
    C4("C4", new Integer[]{3, 4}, -2.0f, 0.0f),
    C5("C5", new Integer[]{3, 5}, -2.0f, -1.0f),
    C6("C6", new Integer[]{3, 6}, -2.0f, -2.0f),
    C7("C7", new Integer[]{3, 7}, -2.0f, -3.0f),
    C8("C8", new Integer[]{3, 8}, -2.0f, -4.0f),
    D1("D1", new Integer[]{4, 1}, -1.0f, 3.0f),
    D2("D2", new Integer[]{4, 2}, -1.0f, 2.0f),
    D3("D3", new Integer[]{4, 3}, -1.0f, 1.0f),
    D4("D4", new Integer[]{4, 4}, -1.0f, 0.0f),
    D5("D5", new Integer[]{4, 5}, -1.0f, -1.0f),
    D6("D6", new Integer[]{4, 6}, -1.0f, -2.0f),
    D7("D7", new Integer[]{4, 7}, -1.0f, -3.0f),
    D8("D8", new Integer[]{4, 8}, -1.0f, -4.0f),
    E1("E1", new Integer[]{5, 1}, 0.0f, 3.0f),
    E2("E2", new Integer[]{5, 2}, 0.0f, 2.0f),
    E3("E3", new Integer[]{5, 3}, 0.0f, 1.0f),
    E4("E4", new Integer[]{5, 4}, 0.0f, 0.0f),
    E5("E5", new Integer[]{5, 5}, 0.0f, -1.0f),
    E6("E6", new Integer[]{5, 6}, 0.0f, -2.0f),
    E7("E7", new Integer[]{5, 7}, 0.0f, -3.0f),
    E8("E8", new Integer[]{5, 8}, 0.0f, -4.0f),
    F1("F1", new Integer[]{6, 1}, 1.0f, 3.0f),
    F2("F2", new Integer[]{6, 2}, 1.0f, 2.0f),
    F3("F3", new Integer[]{6, 3}, 1.0f, 1.0f),
    F4("F4", new Integer[]{6, 4}, 1.0f, 0.0f),
    F5("F5", new Integer[]{6, 5}, 1.0f, -1.0f),
    F6("F6", new Integer[]{6, 6}, 1.0f, -2.0f),
    F7("F7", new Integer[]{6, 7}, 1.0f, -3.0f),
    F8("F8", new Integer[]{6, 8}, 1.0f, -4.0f),
    G1("G1", new Integer[]{7, 1}, 2.0f, 3.0f),
    G2("G2", new Integer[]{7, 2}, 2.0f, 2.0f),
    G3("G3", new Integer[]{7, 3}, 2.0f, 1.0f),
    G4("G4", new Integer[]{7, 4}, 2.0f, 0.0f),
    G5("G5", new Integer[]{7, 5}, 2.0f, -1.0f),
    G6("G6", new Integer[]{7, 6}, 2.0f, -2.0f),
    G7("G7", new Integer[]{7, 7}, 2.0f, -3.0f),
    G8("G8", new Integer[]{7, 8}, 2.0f, -4.0f),
    H1("H1", new Integer[]{8, 1}, 3.0f, 3.0f),
    H2("H2", new Integer[]{8, 2}, 3.0f, 2.0f),
    H3("H3", new Integer[]{8, 3}, 3.0f, 1.0f),
    H4("H4", new Integer[]{8, 4}, 3.0f, 0.0f),
    H5("H5", new Integer[]{8, 5}, 3.0f, -1.0f),
    H6("H6", new Integer[]{8, 6}, 3.0f, -2.0f),
    H7("H7", new Integer[]{8, 7}, 3.0f, -3.0f),
    H8("H8", new Integer[]{8, 8}, 3.0f, -4.0f);

    /**
     * String representation of value.
     */
    private final String strPositionValue;

    /**
     * Integer array representation of value.
     */
    private final Integer[] integerPositionValues;
    
    /**
     * 3D start position of quads. As squares are planes and always have fixed y
     * coordinates, setting a yM is useless.
     */
    private final float xM, zM;

    /**
     * @param strPositionValue
     * @param integerPositionValues
     * @param xM x 3d position
     * @param zM z 3d position
     */
    ChessPositions(final String strPositionValue, final Integer[] integerPositionValues, 
            final float xM, final float zM) {
        this.strPositionValue = strPositionValue;
        this.integerPositionValues = integerPositionValues;
        this.xM = xM;
        this.zM = zM;
    }
    
    /**
     * 
     * @param i
     * @param j
     * @return
     * @throws ErroneousChessPositionException 
     */
    public static ChessPositions get(final int i, final int j) throws ErroneousChessPositionException {
        
        final String logMessage = "%s returning valid chess position %d-%d";
        Integer[] ij;
        for (ChessPositions p : ChessPositions.values()) {
            ij = p.getIntegerPositionValues(); 
            if (ij[0] == i && ij[1] == j) {
                Logger.getLogger(ChessPositions.class.getName()).log(Level.INFO, 
                    String.format(logMessage, ChessPositions.class.getSimpleName(), j, i));
                return p;
            }
        }
        
        throw new ErroneousChessPositionException(
                String.format(ErroneousChessPositionException.MESSAGE_1, j , i));
    }
    
    /**
     * 
     * @param value
     * @return
     * @throws ErroneousChessPositionException 
     */
    public static ChessPositions get(String value) throws ErroneousChessPositionException {
        
        final String logMessage = "Returning valid chess position %s";
        value = value.toUpperCase();
        for (ChessPositions p : ChessPositions.values()) {
            if (p.getStrPositionValue().equals(value)) {
                Logger.getLogger(ChessPositions.class.getName()).log(Level.INFO, 
                    String.format(logMessage, ChessPositions.class.getSimpleName(), value));
                return p;
            }
        }
        
        throw new ErroneousChessPositionException(
                String.format(ErroneousChessPositionException.MESSAGE_2, value));
    }
    
    /**
     * 
     * @return 
     */
    public String getStrPositionValueToLowerCase() {
        return strPositionValue.toLowerCase();
    }
    
    public float xM() {
        return xM;
    }
    
    public float zM() {
        return zM;
    }
    
    public String getStrPositionValue() {
        return strPositionValue;
    }
    
    private Integer[] getIntegerPositionValues() {
        return integerPositionValues;
    }

}
