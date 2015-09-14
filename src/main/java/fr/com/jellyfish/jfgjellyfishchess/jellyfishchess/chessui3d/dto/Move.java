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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.Model;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPiece;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ObjPaths;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FenValueException;
import java.util.Objects;

/**
 *
 * @author thw
 */
public class Move {

    //<editor-fold defaultstate="collapsed" desc="vars"> 
    /**
     * Chess piece as model on this square. Null if chess square is empty.
     */
    private final Model model;

    /**
     * Chess piece as model taken by this move.
     */
    private final Model takenModel;

    /**
     * Position from data.
     */
    private final ChessPositions posFrom;

    /**
     * Position to data.
     */
    private final ChessPositions posTo;

    /**
     * Is engine move ? else = false then ui move.
     */
    private final boolean engineMove;

    /**
     * Is castling move initiated by King ?
     */
    private final boolean castlingMove;
    
    /**
     * move number from 1 to N.
     */
    private final int moveCount;

    /**
     * static id for incrementation.
     */
    private static int ID = 0;

    /**
     * incremented id.
     */
    private final long id;

    /**
     *
     */
    private PawnPromotion pawnPromotion = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor"> 
    /**
     * public constructor.
     * 
     * @param moveCount
     * @param posFrom
     * @param posTo
     * @param engineMove
     * @param castlingMove
     * @param model
     * @param takenModel 
     */
    public Move(final int moveCount, final ChessPositions posFrom, final ChessPositions posTo, 
            final boolean engineMove, final boolean castlingMove, final Model model, 
            final Model takenModel) {
        this.posFrom = posFrom;
        this.posTo = posTo;
        this.engineMove = engineMove;
        this.model = model;
        this.castlingMove = castlingMove;
        this.takenModel = takenModel;
        this.moveCount = moveCount;
        this.id = ++Move.ID;
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="methods">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.model);
        hash = 17 * hash + Objects.hashCode(this.takenModel);
        hash = 17 * hash + Objects.hashCode(this.posFrom);
        hash = 17 * hash + Objects.hashCode(this.posTo);
        hash = 17 * hash + (this.engineMove ? 1 : 0);
        hash = 17 * hash + (this.castlingMove ? 1 : 0);
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 17 * hash + Objects.hashCode(this.pawnPromotion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Move other = (Move) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.takenModel, other.takenModel)) {
            return false;
        }
        if (this.posFrom != other.posFrom) {
            return false;
        }
        if (this.posTo != other.posTo) {
            return false;
        }
        if (this.engineMove != other.engineMove) {
            return false;
        }
        if (this.castlingMove != other.castlingMove) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.pawnPromotion, other.pawnPromotion)) {
            return false;
        }
        return true;
    }

    /**
     * Set PawnPromotion property if move is of pawn promotion type.
     *
     * @param type
     * @param color
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FenValueException
     */
    public void addPawnPromotionData(final char type, final String color) throws FenValueException {

        final String strT = String.valueOf(type).toLowerCase();

        if (strT.length() > 1) {
            throw new FenValueException(String.format(FenValueException.MESSAGE_1, strT));
        }

        final char t = strT.toCharArray()[0];
        this.pawnPromotion = new PawnPromotion(t, ObjPaths.get(t, color), ChessPiece.get(type),
            ObjPaths.get('p', color));
    }

    /**
     * Is move a pawn promotion move ?
     *
     * @return
     */
    public boolean isPawnPromotion() {
        return this.pawnPromotion != null;
    }

    /**
     * Is move a take move.
     *
     * @return
     */
    public boolean isTakeMove() {
        return this.takenModel != null;
    }

    @Override
    public String toString() {

        final StringBuilder s = new StringBuilder();
        
        if (this.isCastlingMove()) {
            return s.toString();
        }
            
        s.append(String.format("%d. ", this.moveCount));
        s.append(String.valueOf(this.model.getType().getFen()));
        s.append(this.posFrom.getStrPositionValueToLowerCase());
        s.append("-");
        s.append(
                this.isTakeMove()
                        ? "x"
                        + String.valueOf(this.getTakenModel().getType().getFen())
                        + this.posTo.getStrPositionValueToLowerCase()
                        : this.getPosTo().getStrPositionValueToLowerCase());
        s.append("  ");

        return s.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="gets & sets"> 
    public char getPawnPromotionType() {
        return pawnPromotion.getPromotionType();
    }

    public String getPawnPromotionObjPath() {
        return pawnPromotion.getModelObjPath();
    }

    public ChessPiece getPawnPromotionPieceType() {
        return pawnPromotion.getPieceType();
    }
    
    public String getPawnModelObjPath() {
        return pawnPromotion.getPawnModelObjPath();
    }

    public boolean isEngineMove() {
        return engineMove;
    }

    public ChessPositions getPosFrom() {
        return posFrom;
    }

    public ChessPositions getPosTo() {
        return posTo;
    }

    public Model getModel() {
        return model;
    }

    public boolean isCastlingMove() {
        return castlingMove;
    }

    public Model getTakenModel() {
        return takenModel;
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="inner private class for pawn promotion">
    /**
     * Pawn promotion dto, equals null if the move does not imply a pawn
     * promotion.
     */
    private class PawnPromotion {

        /**
         * q = queen, r = rook ect.
         */
        private final char promotionType;

        /**
         * .obj file corresponding to the new chess piece Model to build.
         */
        private final String modelObjPath;

        /**
         * Chess piece type.
         */
        private final ChessPiece pieceType;
        
        /**
         * Pawn promoted from mode.
         */
        private final String pawnModelObjPath;

        /**
         * Constructor.
         *
         * @param promotionType
         * @param modelObjPath
         */
        public PawnPromotion(final char promotionType, final String modelObjPath, final ChessPiece pieceType,
                final String pawnModelObjPath) {
            this.promotionType = promotionType;
            this.modelObjPath = modelObjPath;
            this.pieceType = pieceType;
            this.pawnModelObjPath = pawnModelObjPath;
        }

        public char getPromotionType() {
            return promotionType;
        }

        public String getModelObjPath() {
            return modelObjPath;
        }

        public ChessPiece getPieceType() {
            return pieceType;
        }
        
        public String getPawnModelObjPath() {
            return pawnModelObjPath;
        }
        
    }
    //</editor-fold>

}
