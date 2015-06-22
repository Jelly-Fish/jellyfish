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

/**
 *
 * @author thw
 */
public enum ChessPiece {

    P('P', "white pawn"),
    R('R', "white rook"),
    N('N', "white knight"),
    B('B', "white bishop"),
    Q('Q', "white queen"),
    K('K', "white king"),
    p('p', "black pawn"),
    r('r', "black rook"),
    n('n', "black knight"),
    b('b', "black bishop"),
    q('q', "black queen"),
    k('k', "black king");

    /**
     * FEN value of chess piece.
     */
    private final char fen;

    /**
     * Name of piece as String.
     */
    private final String name;

    /**
     * @param fen
     * @param name
     */
    private ChessPiece(final char fen, final String name) {
        this.fen = fen;
        this.name = name;
    }

    /**
     * @param c
     * @return ChessPiece
     */
    public static ChessPiece get(final char c) {
        for (ChessPiece cp : ChessPiece.values()) {
            if (cp.getFen() == c) {
                return cp;
            }
        }

        return null;
    }

    /**
     * @return ChessPiece
     */
    public static ChessPiece getWhiteKing() {
        for (ChessPiece cp : ChessPiece.values()) {
            if (cp.getFen() == 'K') {
                return cp;
            }
        }

        return null;
    }

    /**
     * @return ChessPiece
     */
    public static ChessPiece getBlackKing() {
        for (ChessPiece cp : ChessPiece.values()) {
            if (cp.getFen() == 'k') {
                return cp;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public char getFen() {
        return fen;
    }

    public String getName() {
        return name;
    }

    public boolean isKing() {
        return this.getFen() == 'k' || this.getFen() == 'K';
    }

}
