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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.FENConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Bishop;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.King;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Knight;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Queen;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Rook;

/**
 * Chess men collection, initializes game position.
 * @author Thomas.H Warner 2014
 */
public class ChessMenCollection {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Singleton.
     */
    private static ChessMenCollection instance;
    
    /**
     * All chessmen of a starting game.
     */
    //<editor-fold defaultstate="collapsed" desc="Private ChessMan sub classes">
    ////////////
    // Blacks //
    ////////////
    private Rook rookH8;
    private Knight knightG8;
    private Bishop bishopF8;
    private King kingE8;
    private Queen queenD8;
    private Bishop bishopC8;
    private Knight knightB8;
    private Rook rookA8;
    private Pawn pawnH7;
    private Pawn pawnG7;
    private Pawn pawnF7;
    private Pawn pawnE7;
    private Pawn pawnD7;
    private Pawn pawnC7;
    private Pawn pawnB7;
    private Pawn pawnA7;
    ////////////
    // Whites //
    ////////////
    private Rook rookA1;
    private Knight knightB1;
    private Bishop bishopC1;
    private King kingE1;
    private Queen queenD1;
    private Bishop bishopF1;
    private Knight knightG1;
    private Rook rookH1;
    private Pawn pawnA2;
    private Pawn pawnB2;
    private Pawn pawnC2;
    private Pawn pawnD2;
    private Pawn pawnE2;
    private Pawn pawnF2;
    private Pawn pawnG2;
    private Pawn pawnH2;
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Privte constructor">
    /**
     * Private constructor.
     */
    private ChessMenCollection() { }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Initialize all chessmen.
     * Values are based on Hans Jack Berliner's scoring.
     */
    public void init() {
        
        //<editor-fold defaultstate="collapsed" desc="Instanciate all chessmen for game start">
        // Black pawns first.
        pawnH7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.H7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.H7).setOnPositionChessMan(pawnH7);
        
        pawnG7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.G7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.G7).setOnPositionChessMan(pawnG7);
        
        pawnF7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.F7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.F7).setOnPositionChessMan(pawnF7);
        
        pawnE7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.E7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.E7).setOnPositionChessMan(pawnE7);
        
        pawnD7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.D7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.D7).setOnPositionChessMan(pawnD7);
        
        pawnC7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.C7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.C7).setOnPositionChessMan(pawnC7);
        
        pawnB7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.B7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.B7).setOnPositionChessMan(pawnB7);
        
        pawnA7 = new Pawn(BoardConst.BLACK, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.A7), false, FENConst.BLACK_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.A7).setOnPositionChessMan(pawnA7);
        
        // The white pawns.
        pawnA2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.A2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.A2).setOnPositionChessMan(pawnA2);
        
        pawnB2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.B2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.B2).setOnPositionChessMan(pawnB2);
        
        pawnC2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.C2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.C2).setOnPositionChessMan(pawnC2);
        
        pawnD2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.D2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.D2).setOnPositionChessMan(pawnD2);
        
        pawnE2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.E2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.E2).setOnPositionChessMan(pawnE2);
        
        pawnF2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.F2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.F2).setOnPositionChessMan(pawnF2);
        
        pawnG2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.G2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.G2).setOnPositionChessMan(pawnG2);
        
        pawnH2 = new Pawn(BoardConst.WHITE, 1.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.H2), false, FENConst.WHITE_PAWN);
        Board.getInstance().getCoordinates().get(BoardConst.H2).setOnPositionChessMan(pawnH2);
        
        // Knights.
        knightG8 = new Knight(BoardConst.BLACK, 3.2f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.G8), false, FENConst.BLACK_KNIGHT);
        Board.getInstance().getCoordinates().get(BoardConst.G8).setOnPositionChessMan(knightG8);
        
        knightB8 = new Knight(BoardConst.BLACK, 3.2f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.B8), false, FENConst.BLACK_KNIGHT);
        Board.getInstance().getCoordinates().get(BoardConst.B8).setOnPositionChessMan(knightB8);
        
        knightB1 = new Knight(BoardConst.WHITE, 3.2f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.B1), false, FENConst.WHITE_KNIGHT);
        Board.getInstance().getCoordinates().get(BoardConst.B1).setOnPositionChessMan(knightB1);
        
        knightG1 = new Knight(BoardConst.WHITE, 3.2f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.G1), false, FENConst.WHITE_KNIGHT);
        Board.getInstance().getCoordinates().get(BoardConst.G1).setOnPositionChessMan(knightG1);
        
        // Rooks.
        rookH8 = new Rook(BoardConst.BLACK, 5.1f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.H8), false, FENConst.BLACK_ROOK);
        Board.getInstance().getCoordinates().get(BoardConst.H8).setOnPositionChessMan(rookH8);
        
        rookA8 = new Rook(BoardConst.BLACK, 5.1f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.A8), false, FENConst.BLACK_ROOK);
        Board.getInstance().getCoordinates().get(BoardConst.A8).setOnPositionChessMan(rookA8);
        
        rookA1 = new Rook(BoardConst.WHITE, 5.1f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.A1), false, FENConst.WHITE_ROOK);
        Board.getInstance().getCoordinates().get(BoardConst.A1).setOnPositionChessMan(rookA1);
        
        rookH1 = new Rook(BoardConst.WHITE, 5.1f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.H1), false, FENConst.WHITE_ROOK);
        Board.getInstance().getCoordinates().get(BoardConst.H1).setOnPositionChessMan(rookH1);
        
        // Bishops.
        bishopF8 = new Bishop(BoardConst.BLACK, 3.33f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.F8), false, FENConst.BLACK_BISHOP);
        Board.getInstance().getCoordinates().get(BoardConst.F8).setOnPositionChessMan(bishopF8); 
        
        bishopC8 = new Bishop(BoardConst.BLACK, 3.33f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.C8), false, FENConst.BLACK_BISHOP);
        Board.getInstance().getCoordinates().get(BoardConst.C8).setOnPositionChessMan(bishopC8);
        
        bishopC1 = new Bishop(BoardConst.WHITE, 3.33f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.C1), false, FENConst.WHITE_BISHOP);
        Board.getInstance().getCoordinates().get(BoardConst.C1).setOnPositionChessMan(bishopC1); 
        
        bishopF1 = new Bishop(BoardConst.WHITE, 3.33f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.F1), false, FENConst.WHITE_BISHOP);
        Board.getInstance().getCoordinates().get(BoardConst.F1).setOnPositionChessMan(bishopF1); 
        
        // Queens.
        queenD1 = new Queen(BoardConst.WHITE, 8.8f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.D1), false, FENConst.WHITE_QUEEN);
        Board.getInstance().getCoordinates().get(BoardConst.D1).setOnPositionChessMan(queenD1); 
        
        queenD8 = new Queen(BoardConst.BLACK, 8.8f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.D8), false, FENConst.BLACK_QUEEN);
        Board.getInstance().getCoordinates().get(BoardConst.D8).setOnPositionChessMan(queenD8);
        
        // Kings.
        kingE1 = new King(BoardConst.WHITE, 0.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.E1), false, FENConst.WHITE_KING);
        Board.getInstance().getCoordinates().get(BoardConst.E1).setOnPositionChessMan(kingE1);
        
        kingE8 = new King(BoardConst.BLACK, 0.0f, true, 
            Board.getInstance().getCoordinates().get(BoardConst.E8), false, FENConst.BLACK_KING);
        Board.getInstance().getCoordinates().get(BoardConst.E8).setOnPositionChessMan(kingE8);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Set King to all Chessmen">
        // Set kings for all white chessman :
        pawnA2.setChessManKing(kingE1);
        pawnB2.setChessManKing(kingE1);
        pawnC2.setChessManKing(kingE1);
        pawnD2.setChessManKing(kingE1);
        pawnE2.setChessManKing(kingE1);
        pawnF2.setChessManKing(kingE1);
        pawnG2.setChessManKing(kingE1);
        pawnH2.setChessManKing(kingE1);
        rookA1.setChessManKing(kingE1);
        rookH1.setChessManKing(kingE1);
        knightB1.setChessManKing(kingE1);
        knightG1.setChessManKing(kingE1);
        bishopC1.setChessManKing(kingE1);
        bishopF1.setChessManKing(kingE1);
        queenD1.setChessManKing(kingE1);
        kingE1.setChessManKing(kingE1);
        // And for all black chessman :
        pawnA7.setChessManKing(kingE8);
        pawnB7.setChessManKing(kingE8);
        pawnC7.setChessManKing(kingE8);
        pawnD7.setChessManKing(kingE8);
        pawnE7.setChessManKing(kingE8);
        pawnF7.setChessManKing(kingE8);
        pawnG7.setChessManKing(kingE8);
        pawnH7.setChessManKing(kingE8);
        rookA8.setChessManKing(kingE8);
        rookH8.setChessManKing(kingE8);
        knightB8.setChessManKing(kingE8);
        knightG8.setChessManKing(kingE8);
        bishopC8.setChessManKing(kingE8);
        bishopF8.setChessManKing(kingE8);
        queenD8.setChessManKing(kingE8);
        kingE8.setChessManKing(kingE8);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Set oponent King to all Chessmen">
        // Set kings for all white chessman :
        pawnA2.setChessManOponentKing(kingE8);
        pawnB2.setChessManOponentKing(kingE8);
        pawnC2.setChessManOponentKing(kingE8);
        pawnD2.setChessManOponentKing(kingE8);
        pawnE2.setChessManOponentKing(kingE8);
        pawnF2.setChessManOponentKing(kingE8);
        pawnG2.setChessManOponentKing(kingE8);
        pawnH2.setChessManOponentKing(kingE8);
        rookA1.setChessManOponentKing(kingE8);
        rookH1.setChessManOponentKing(kingE8);
        knightB1.setChessManOponentKing(kingE8);
        knightG1.setChessManOponentKing(kingE8);
        bishopC1.setChessManOponentKing(kingE8);
        bishopF1.setChessManOponentKing(kingE8);
        queenD1.setChessManOponentKing(kingE8);
        kingE1.setChessManOponentKing(kingE8);
        // And for all black chessman :
        pawnA7.setChessManOponentKing(kingE1);
        pawnB7.setChessManOponentKing(kingE1);
        pawnC7.setChessManOponentKing(kingE1);
        pawnD7.setChessManOponentKing(kingE1);
        pawnE7.setChessManOponentKing(kingE1);
        pawnF7.setChessManOponentKing(kingE1);
        pawnG7.setChessManOponentKing(kingE1);
        pawnH7.setChessManOponentKing(kingE1);
        rookA8.setChessManOponentKing(kingE1);
        rookH8.setChessManOponentKing(kingE1);
        knightB8.setChessManOponentKing(kingE1);
        knightG8.setChessManOponentKing(kingE1);
        bishopC8.setChessManOponentKing(kingE1);
        bishopF8.setChessManOponentKing(kingE1);
        queenD8.setChessManOponentKing(kingE1);
        kingE8.setChessManOponentKing(kingE1);
        //</editor-fold>
        
    }

    /**
     * Singleton.
     * @return ChessMenCollection instance.
     */
    public static ChessMenCollection getInstance() {
        if (instance == null) {
            instance = new ChessMenCollection();
            instance.init();
            return instance;
        } else {
            return instance;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Chessmen Getters">
    public Rook getRookH8() {
        return rookH8;
    }

    public Knight getKnightG8() {
        return knightG8;
    }

    public Bishop getBishopF8() {
        return bishopF8;
    }

    public King getKingE8() {
        return kingE8;
    }

    public Queen getQueenD8() {
        return queenD8;
    }

    public Bishop getBishopC8() {
        return bishopC8;
    }

    public Knight getKnightB8() {
        return knightB8;
    }

    public Rook getRookA8() {
        return rookA8;
    }

    public Pawn getPawnH7() {
        return pawnH7;
    }

    public Pawn getPawnG7() {
        return pawnG7;
    }

    public Pawn getPawnF7() {
        return pawnF7;
    }

    public Pawn getPawnE7() {
        return pawnE7;
    }

    public Pawn getPawnD7() {
        return pawnD7;
    }

    public Pawn getPawnC7() {
        return pawnC7;
    }

    public Pawn getPawnB7() {
        return pawnB7;
    }

    public Pawn getPawnA7() {
        return pawnA7;
    }

    public Rook getRookA1() {
        return rookA1;
    }

    public Knight getKnightB1() {
        return knightB1;
    }

    public Bishop getBishopC1() {
        return bishopC1;
    }

    public King getKingE1() {
        return kingE1;
    }

    public Queen getQueenD1() {
        return queenD1;
    }

    public Bishop getBishopF1() {
        return bishopF1;
    }

    public Knight getKnightG1() {
        return knightG1;
    }

    public Rook getRookH1() {
        return rookH1;
    }

    public Pawn getPawnA2() {
        return pawnA2;
    }

    public Pawn getPawnB2() {
        return pawnB2;
    }

    public Pawn getPawnC2() {
        return pawnC2;
    }

    public Pawn getPawnD2() {
        return pawnD2;
    }

    public Pawn getPawnE2() {
        return pawnE2;
    }

    public Pawn getPawnF2() {
        return pawnF2;
    }

    public Pawn getPawnG2() {
        return pawnG2;
    }

    public Pawn getPawnH2() {
        return pawnH2;
    }
    //</editor-fold>
    
}
