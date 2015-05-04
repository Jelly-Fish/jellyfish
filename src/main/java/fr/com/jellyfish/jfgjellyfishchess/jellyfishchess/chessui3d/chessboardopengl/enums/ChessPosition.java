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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.enums;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.exceptions.ErroneousChessPositionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public enum ChessPosition {

    A1("A1", new Integer[]{1, 1}),
    A2("A2", new Integer[]{1, 2}),
    A3("A3", new Integer[]{1, 3}),
    A4("A4", new Integer[]{1, 4}),
    A5("A5", new Integer[]{1, 5}),
    A6("A6", new Integer[]{1, 6}),
    A7("A7", new Integer[]{1, 7}),
    A8("A8", new Integer[]{1, 8}),
    B1("B1", new Integer[]{2, 1}),
    B2("B2", new Integer[]{2, 2}),
    B3("B3", new Integer[]{2, 3}),
    B4("B4", new Integer[]{2, 4}),
    B5("B5", new Integer[]{2, 5}),
    B6("B6", new Integer[]{2, 6}),
    B7("B7", new Integer[]{2, 7}),
    B8("B8", new Integer[]{2, 8}),
    C1("C1", new Integer[]{3, 1}),
    C2("C2", new Integer[]{3, 2}),
    C3("C3", new Integer[]{3, 3}),
    C4("C4", new Integer[]{3, 4}),
    C5("C5", new Integer[]{3, 5}),
    C6("C6", new Integer[]{3, 6}),
    C7("C7", new Integer[]{3, 7}),
    C8("C8", new Integer[]{3, 8}),
    D1("D1", new Integer[]{4, 1}),
    D2("D2", new Integer[]{4, 2}),
    D3("D3", new Integer[]{4, 3}),
    D4("D4", new Integer[]{4, 4}),
    D5("D5", new Integer[]{4, 5}),
    D6("D6", new Integer[]{4, 6}),
    D7("D7", new Integer[]{4, 7}),
    D8("D8", new Integer[]{4, 8}),
    E1("E1", new Integer[]{5, 1}),
    E2("E2", new Integer[]{5, 2}),
    E3("E3", new Integer[]{5, 3}),
    E4("E4", new Integer[]{5, 4}),
    E5("E5", new Integer[]{5, 5}),
    E6("E6", new Integer[]{5, 6}),
    E7("E7", new Integer[]{5, 7}),
    E8("E8", new Integer[]{5, 8}),
    F1("F1", new Integer[]{6, 1}),
    F2("F2", new Integer[]{6, 2}),
    F3("F3", new Integer[]{6, 3}),
    F4("F4", new Integer[]{6, 4}),
    F5("F5", new Integer[]{6, 5}),
    F6("F6", new Integer[]{6, 6}),
    F7("F7", new Integer[]{6, 7}),
    F8("F8", new Integer[]{6, 8}),
    G1("G1", new Integer[]{7, 1}),
    G2("G2", new Integer[]{7, 2}),
    G3("G3", new Integer[]{7, 3}),
    G4("G4", new Integer[]{7, 4}),
    G5("G5", new Integer[]{7, 5}),
    G6("G6", new Integer[]{7, 6}),
    G7("G7", new Integer[]{7, 7}),
    G8("G8", new Integer[]{7, 8}),
    H1("H1", new Integer[]{8, 1}),
    H2("H2", new Integer[]{8, 2}),
    H3("H3", new Integer[]{8, 3}),
    H4("H4", new Integer[]{8, 4}),
    H5("H5", new Integer[]{8, 5}),
    H6("H6", new Integer[]{8, 6}),
    H7("H7", new Integer[]{8, 7}),
    H8("H8", new Integer[]{8, 8});

    /**
     * String representation of value.
     */
    private final String strPositionValue;

    /**
     * Integer array representation of value.
     */
    private Integer[] integerPositionValues;

    ChessPosition(final String strPositionValue, final Integer[] integerPositionValues) {
        this.strPositionValue = strPositionValue;
        this.integerPositionValues = integerPositionValues;
    }
    
    public static ChessPosition get(final int i, final int j) throws ErroneousChessPositionException {
        
        final String logMessage = "%s returning valid chess position %d-%d";
        Integer[] ij;
        for (ChessPosition p : ChessPosition.values()) {
            ij = p.getIntegerPositionValues(); 
            if (ij[0] == i && ij[1] == j) {
                Logger.getLogger(ChessPosition.class.getName()).log(Level.INFO, 
                String.format(logMessage, ChessPosition.class.getSimpleName(), j, i));
                return p;
            }
        }
        
        throw new ErroneousChessPositionException(
                String.format(ErroneousChessPositionException.MESSAGE, j , i));
    }
    
    private String getStrPositionValue() {
        return strPositionValue;
    }
    
    private Integer[] getIntegerPositionValues() {
        return integerPositionValues;
    }

}
