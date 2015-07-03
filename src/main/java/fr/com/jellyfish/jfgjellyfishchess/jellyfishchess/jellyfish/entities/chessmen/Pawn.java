/**
 * *****************************************************************************
 * Copyright (c) 2014, Thomas.H Warner. All rights reserved.
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
 ******************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.PawnEnPassantObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a chess pawn's behavior in a chess game.
 *
 * @author Thomas.H Warner 2014
 */
public class Pawn extends AbstractChessMan {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * List of en passant observers.
     */
    private transient List<PawnEnPassantObserver> enPassantObservers = new ArrayList<>();

    /**
     * Promotion type for Pawn.
     */
    private char promotionType;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     *
     * @param COLOR
     * @param valuation
     * @param alive
     * @param boardPosition
     * @param nullChessMan
     * @param fenValue
     */
    public Pawn(final String COLOR, final float valuation, final boolean alive,
            final Position boardPosition, final boolean nullChessMan, final char fenValue) {
        super(COLOR, valuation, alive, boardPosition, nullChessMan, fenValue);
        // Pawn promotion type is Queen class by default.
        promotionType = UCIConst.QUEEN_PROMOTION;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden methods">
    @Override
    public boolean isValidMove(final Integer[] xyFrom, final Integer[] xyTo) {

        Position posTo = Board.getInstance().getCoordinates().get(BoardConst.getPostionFromIntegers(xyTo));
        Position posToTake = null;

        //<editor-fold defaultstate="collapsed" desc="En passant move">
        if (xyFrom[0] == 4 && this.getColor().equals(BoardConst.WHITE) && !xyFrom[1].equals(xyTo[1])) {
            if (posTo.getOnPositionChessMan().isNullChessMan()
                    && posTo.getOnPositionChessMan().isVirtualPawn()
                    && xyFrom[0] - xyTo[0] == 1 && (xyFrom[1] - xyTo[1] == 1 || xyTo[1] - xyFrom[1] == 1)) {
                // Pawn is taking a virtual Pawn and executng a valid "en passant" move.
                // Set a new NullChessMan on the xyTo[0] - 1 position.
                // Inform en passant observers (usually GUI) that update is
                // necessary. First get the virtual pawn position (1 step behind).
                posToTake = Board.getInstance().getCoordinates().get(
                        BoardConst.getPostionFromIntegers(new Integer[]{xyTo[0] + 1, xyTo[1]}));
                applyPawnEnPassantTake(posTo, posToTake);
                // Finally validate move.
                return true;
            }
        } else if (xyFrom[0] == 5 && this.getColor().equals(BoardConst.BLACK) && !xyFrom[1].equals(xyTo[1])) {
            if (posTo.getOnPositionChessMan().isNullChessMan()
                    && posTo.getOnPositionChessMan().isVirtualPawn()
                    && xyTo[0] - xyFrom[0] == 1 && (xyFrom[1] - xyTo[1] == 1 || xyTo[1] - xyFrom[1] == 1)) {

                posToTake = Board.getInstance().getCoordinates().get(
                        BoardConst.getPostionFromIntegers(new Integer[]{xyTo[0] - 1, xyTo[1]}));
                applyPawnEnPassantTake(posTo, posToTake);
                return true;
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Pawn classic moves">
        // The first pawn move is a special move. It can move ahead by two steps,
        // ex: a2a4. In this situation the Pawn must intaciate a new NullChessMan
        // on the postion 1 step backwards. This new NullChessMan must be set with
        // isVirtualPawn == true. When checking for Pawn "En passant" move the 
        // search will look for a virtualPawn true & NullChessMan true ChessMan sub
        // class.
        if (this.getMoveCount() == 0) {
            // Then it is pawn's first move.
            if (this.getColor().equals(BoardConst.WHITE)) {
                if (xyFrom[1].equals(xyTo[1]) && (xyFrom[0] - xyTo[0] == 2
                        || xyFrom[0] - xyTo[0] == 1)
                        && Board.getInstance().getCoordinates().get(
                                BoardConst.getPostionFromIntegers(
                                        new Integer[]{xyFrom[0] - 1, xyFrom[1]})
                        ).getOnPositionChessMan().isNullChessMan()) {

                    // Position is a NullChessMan. Simply set virtualPawn if and 
                    // only if the move is a double step.
                    if (xyFrom[0] - xyTo[0] == 2) {
                        Board.getInstance().getCoordinates().get(BoardConst.getPostionFromIntegers(
                                new Integer[]{xyFrom[0] - 1, xyFrom[1]})).getOnPositionChessMan().setVirtualPawn(true);
                    }
                    // If move ahead 1 or 2 positions & first ahead position is free
                    // then move is validated.
                    return true;
                }
            } else if (this.getColor().equals(BoardConst.BLACK)) {
                if (xyFrom[1].equals(xyTo[1]) && (xyTo[0] - xyFrom[0] == 2
                        || xyTo[0] - xyFrom[0] == 1)
                        && Board.getInstance().getCoordinates().get(
                                BoardConst.getPostionFromIntegers(
                                        new Integer[]{xyFrom[0] + 1, xyFrom[1]})
                        ).getOnPositionChessMan().isNullChessMan()) {

                    // Position is a NullChessMan. Simply set virtualPawn if and 
                    // only if the move is a double step.
                    if (xyTo[0] - xyFrom[0] == 2) {
                        Board.getInstance().getCoordinates().get(BoardConst.getPostionFromIntegers(
                                new Integer[]{xyFrom[0] + 1, xyFrom[1]})).getOnPositionChessMan().setVirtualPawn(true);
                    }
                    // Same as above but blacks move down in the coordinates.
                    return true;
                }
            }

            // At first move a pawn can also attack on a diagonal.
            if (isDiagonalPawnAttack(posTo, xyFrom, xyTo)) {
                return true;
            }

        } else if (this.getMoveCount() > 0) {

            if (this.getColor().equals(BoardConst.WHITE)) {
                if (xyFrom[1].equals(xyTo[1]) && xyFrom[0] - xyTo[0] == 1
                        && posTo.getOnPositionChessMan().isNullChessMan()) {
                    return true;
                } else if (isDiagonalPawnAttack(posTo, xyFrom, xyTo)) {
                    return true;
                }
            } else if (this.getColor().equals(BoardConst.BLACK)) {
                if (xyFrom[1].equals(xyTo[1]) && xyTo[0] - xyFrom[0] == 1
                        && posTo.getOnPositionChessMan().isNullChessMan()) {
                    return true;
                } else if (isDiagonalPawnAttack(posTo, xyFrom, xyTo)) {
                    return true;
                }
            } else {
                // All posibilities have been checked, move is invalidated.
                return false;
            }

        }
        //</editor-fold>

        // If none of the conditions are true, move is not validated :
        // return false.
        return false;
    }

    @Override
    public String toString() {
        if (this.isAlive()) {
            final String chessman = this.getClass().getName().replace(CommonConst.CHESSMEN_PACKAGE, CommonConst.EMPTY_STR);
            return chessman + CommonConst.UPPER_DASH + this.getColor()
                    + CommonConst.AT + this.getBoardPosition().toString() + CommonConst.BACKSLASH_N
                    + CommonConst.MOVE_COUNT + String.valueOf(this.getMoveCount());
        } else {
            final String chessman = this.getClass().getName().replace(CommonConst.CHESSMEN_PACKAGE, CommonConst.EMPTY_STR);
            return chessman + CommonConst.UPPER_DASH + this.getColor()
                    + CommonConst.AT + this.getBoardPosition().toString() + CommonConst.SPACE_STR
                    + CommonConst.REST_IN_PEACE;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Return string representation of chessman depending on char UCI value.
     *
     * @param promotionType
     * @return
     */
    public String displayPromotionType(char promotionType) {

        switch (promotionType) {
            case UCIConst.QUEEN_PROMOTION:
                return CommonConst.STR_QUEEN;
            case UCIConst.BISHOP_PROMOTION:
                return CommonConst.STR_BISHOP;
            case UCIConst.KNIGHT_PROMOTION:
                return CommonConst.STR_KNIGHT;
            case UCIConst.ROOK_PROMOTION:
                return CommonConst.STR_ROOK;
            default:
                return CommonConst.EMPTY_STR;
        }
    }

    /**
     * Add a new observer.
     *
     * @param enPassantObserver
     */
    public void addPawnEnPassantObserver(final PawnEnPassantObserver enPassantObserver) {
        // In the case of ChessMan subclasses deserialization, check that transient
        // property ArrayList<> enPassantObservers collection is null. If so, then
        // = new ArrayList.
        if (this.enPassantObservers == null) {
            this.enPassantObservers = new ArrayList<>();
        }
        this.enPassantObservers.add(enPassantObserver);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * Apply en passant take to observers.
     *
     * @param pawnToTake
     */
    private void applyPawnEnPassantTake(final Position posTo, final Position pawnToTake) {

        // Move Pawn, King move's itself on isValidMove() return true.
        pawnToTake.setOnPositionChessMan(new NullChessMan());
        posTo.getOnPositionChessMan().setVirtualPawn(false);

        // Finally call observers (GUI & ChessGame).
        for (PawnEnPassantObserver observer : enPassantObservers) {
            observer.applyPawnEnPassant(pawnToTake.toString());
        }

    }

    /**
     * Check that a diagonal attack pawn move is a valid move. Method works
     * idependently from color.
     *
     * @param posTo
     * @param xyFrom
     * @param xyTo
     * @return
     */
    private boolean isDiagonalPawnAttack(final Position posTo, final Integer[] xyFrom, final Integer[] xyTo) {

        // Color must be !=, position must be !null chessman & coordinates
        // must be correct for a diagonal one step pawn attack.
        return !xyFrom[1].equals(xyTo[1])
                && !posTo.getOnPositionChessMan().isNullChessMan()
                && ((xyFrom[0] - xyTo[0] == 1 && (xyFrom[1] - xyTo[1] == 1 || xyTo[1] - xyFrom[1] == 1)
                && this.getColor().equals(BoardConst.WHITE)
                && posTo.getOnPositionChessMan().getColor().equals(BoardConst.BLACK))
                || (xyTo[0] - xyFrom[0] == 1 && (xyFrom[1] - xyTo[1] == 1 || xyTo[1] - xyFrom[1] == 1)
                && this.getColor().equals(BoardConst.BLACK)
                && posTo.getOnPositionChessMan().getColor().equals(BoardConst.WHITE))); // Check for forward left - right attacks.
    }
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public char getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(final char promotionType) {
        this.promotionType = promotionType;
    }
    //</editor-fold>

}
