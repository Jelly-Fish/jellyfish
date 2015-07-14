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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.CheckObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.Movable;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.PawnPromotionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Class that describes a chessman.
 *
 * @author Thomas.H Warner 2014
 */
public abstract class AbstractChessMan implements Movable, Serializable {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * The chessman's King.
     */
    private King chessManKing;

    /**
     * Check Observer.
     */
    private transient CheckObserver checkObserver;

    /**
     * Chessman's oponent King.
     */
    private King chessManOponentKing;

    /**
     * If this is the null chess man.
     */
    private boolean nullChessMan;

    /**
     * If this is a virtual Pawn (en passant Panw move).
     */
    private boolean virtualPawn = false;

    /**
     * White or Black's...
     */
    private final String color;

    /**
     * Move count of chessman.
     */
    private int moveCount = 0;

    /**
     * Is still alive or not.
     */
    private boolean alive;

    /**
     * Position on the board : A1, H5 ect... as a String.
     */
    private transient Position boardPosition;

    /**
     * Value of chessman based on Hans Berliner's system.
     */
    private float valuation;

    /**
     * Corresponding FEN char.
     */
    private Object fenValue;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructor. All private variables are initialized via constructor : in
     * the case of a re-spawn (pawn that gets to other color's side can spawn as
     * desired at exact same location).
     *
     * @param COLOR
     * @param valuation
     * @param alive
     * @param boardPosition
     * @param nullChessMan
     * @param fenValue
     */
    public AbstractChessMan(final String COLOR, final float valuation, final boolean alive, final Position boardPosition,
            final boolean nullChessMan, final char fenValue) {
        this.color = COLOR;
        this.valuation = valuation;
        this.alive = alive;
        this.boardPosition = boardPosition;
        this.nullChessMan = nullChessMan;
        this.fenValue = fenValue;
    }

    /**
     * No param constructor.
     */
    public AbstractChessMan() {
        this.color = "null";
        this.valuation = 0.0f;
        this.alive = false;
        this.boardPosition = null;
        this.nullChessMan = true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Add one to the move count...
     */
    public void incrementMoveCount() {
        this.moveCount++;
    }

    /**
     * Get all possible positions for a rook move depending on direction.
     *
     * @param direction
     * @param xyFrom
     * @param xyTo
     * @return positions
     */
    public List<Integer[]> getRookPositions(char direction, final Integer[] xyFrom, final Integer[] xyTo) {

        List<Integer[]> positions = new ArrayList<>();

        // Collect all possible numeric coordinates depending on direction.
        int from, too; // Numeric coordinates to iterate on.
        // u = up, l = left ect.
        switch (direction) {
            case CommonConst.U_CHAR_LOWERCASE:
                from = xyFrom[0];
                too = xyTo[0];
                for (int i = 1; from > too; ++i) {
                    positions.add(new Integer[]{xyFrom[0] - i, xyFrom[1]});
                    --from;
                }
                break;
            case CommonConst.D_CHAR_LOWERCASE:
                from = xyFrom[0];
                too = xyTo[0];
                for (int i = 1; from < too; ++i) {
                    positions.add(new Integer[]{xyFrom[0] + i, xyFrom[1]});
                    ++from;
                }
                break;
            case CommonConst.L_CHAR_LOWERCASE:
                from = xyFrom[1];
                too = xyTo[1];
                for (int i = 1; from > too; ++i) {
                    positions.add(new Integer[]{xyFrom[0], xyFrom[1] - i});
                    --from;
                }
                break;
            case CommonConst.R_CHAR_LOWERCASE:
                from = xyFrom[1];
                too = xyTo[1];
                for (int i = 1; from < too; ++i) {
                    positions.add(new Integer[]{xyFrom[0], xyFrom[1] + i});
                    ++from;
                }
                break;
            default:
                break;
        }

        return positions;
    }

    /**
     * Get possible possitions for a bishop move depending on which diagonal it
     * has chosen to take.
     *
     * @param diagonal
     * @param xyFrom
     * @param xyTo
     * @return List<Integer[]> positions
     */
    public List<Integer[]> getBihopDiagonals(final int diagonal,
            final Integer[] xyFrom, final Integer[] xyTo) {

        List<Integer[]> positions = new ArrayList<>();

        // Collect all possible numeric coordinates.
        // 1: up.right - 2: down.right - 3: up.left - 4: down.left
        int maxPos = 8;
        int minPos = 1;
        int x, y;
        switch (diagonal) {
            case 1:
                // Loop for < 8 (maxPos): a bishop can only take a miximum of 7
                // steps in any case. Add moves to positions array list of 
                // Integer[]. A bishop can hit two different sides on a move (a
                // rook can't), in order to prevent NullPointer's by adding invalid
                // integer coordinates such as 0 or 9 check on each loop that coordinates
                // are not above 8 or lower than 1. Also check for xyTo Integer 
                // position equality : if == they no need to loop foward; return
                // the array list. The break statement is logically never reached
                // , it is left anyway to respect switch syntax.
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] - i;
                    x = xyFrom[1] + i;
                    positions.add(new Integer[]{y, x});
                    // If position ahead is out of range (> 8 OR < 1) then return.
                    if (y - 1 < minPos || x + 1 > maxPos || (y == xyTo[0] && x == xyTo[1])) {
                        return positions;
                    }
                }
                break;
            case 2:
                // Same as above but for down.right move.
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] + i;
                    x = xyFrom[1] + i;
                    positions.add(new Integer[]{y, x});
                    if (y + 1 > maxPos || x + 1 > maxPos || (y == xyTo[0] && x == xyTo[1])) {
                        return positions;
                    }
                }
                break;
            case 3:
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] - i;
                    x = xyFrom[1] - i;
                    positions.add(new Integer[]{y, x});
                    if (y - 1 < minPos || x - 1 < minPos || (y == xyTo[0] && x == xyTo[1])) {
                        return positions;
                    }
                }
                break;
            case 4:
                for (int i = 1; i < maxPos; ++i) {
                    y = xyFrom[0] + i;
                    x = xyFrom[1] - i;
                    positions.add(new Integer[]{y, x});
                    if (y + 1 > maxPos || x - 1 < minPos || (y == xyTo[0] && x == xyTo[1])) {
                        return positions;
                    }
                }
                break;
            default:
                break;
        }

        return positions;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * Notify check observer that king is in check situation.
     *
     * @param king
     * @param posFrom
     * @param inCheck
     */
    private void notifyCheckSituationToObserver(final Position king, final boolean inCheck) {
        checkObserver.applyCheckSituation(king, inCheck);
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Overriden methods">
    @Override
    public String toString() {
        if (this.isAlive()) {

            final String chessman = this.getClass().getName().replace(CommonConst.CHESSMEN_PACKAGE, CommonConst.EMPTY_STR);

            return chessman + CommonConst.UPPER_DASH + this.getColor()
                    + CommonConst.AT + this.getBoardPosition().toString()
                    + CommonConst.BACKSLASH_N
                    + CommonConst.MOVE_COUNT + String.valueOf(this.getMoveCount());
        } else {
            return CommonConst.EMPTY_STR;
        }
    }

    /**
     * Move a chess man from Position a to b. This method call's isValidMove to
     * assure the move is valid and respects chess moving rules.
     *
     * @param posFrom
     * @param posTo
     * @return boolean.
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException
     */
    @Override
    public boolean move(final Position posFrom, final Position posTo, final boolean guiMove)
            throws InvalidMoveException, PawnPromotionException {

        if (this.isAlive() && isValidMove(BoardConst.coordinatesIntegerMap.get(posFrom.toString()),
                BoardConst.coordinatesIntegerMap.get(posTo.toString()))) {

            // Save the posTo chessman for eventuel rollback. If the move is a take
            // and king is still in check situation then move must be reset or
            // "rollbacked" to previous state.
            AbstractChessMan rollback = posTo.getOnPositionChessMan();
            // Validate and execute move.
            this.setBoardPosition(posTo);
            posTo.setOnPositionChessMan(this);
            posFrom.setOnPositionChessMan(new NullChessMan());

            //<editor-fold defaultstate="collapsed" desc="King search check situation">
            // On each move of every chessman kings must be evaluated for check
            // situation or checkmate. 
            if (posTo.getOnPositionChessMan().getChessManKing().isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(
                    posTo.getOnPositionChessMan().getChessManKing().getBoardPosition().toString()))) {
                
                // Move must be blocked : King is still in checked status.

                // Rollback previous move :
                this.setBoardPosition(posFrom);
                posFrom.setOnPositionChessMan(this);
                // If taken chessman, reset. If position was a null chessman also reset.
                // In the case of previous and executed pawn promotion, the 
                // rollback type must be reset.
                posTo.setOnPositionChessMan(rollback);

                // Set king in check boolean :
                posFrom.getOnPositionChessMan().getChessManOponentKing().setInCheck(true);
                // Move is an Invalid move, it must beblocked and a Invalid
                // move exception must be thrown :
                throw new InvalidMoveException(posFrom.toString() + posTo.toString()
                        + CommonConst.SPACE_STR + ExceptionConst.EX_ISNOT_A_VALID_MOVE + CommonConst.SPACE_STR
                        + posFrom.getOnPositionChessMan().toString());
            } else {
                posTo.getOnPositionChessMan().getChessManOponentKing().setInCheck(false);
            }
            //</editor-fold>  

            //<editor-fold defaultstate="collapsed" desc="Oponent King search check situation">
            // The chessman's King is out of check situation.
            // Search oponent King for check situation and notify observers if
            // necessary.
            if (posTo.getOnPositionChessMan().getChessManOponentKing().isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(
                    posTo.getOnPositionChessMan().getChessManOponentKing().getBoardPosition().toString()))) {

                // Oponent King is in check, checkmate evaluation is necessary,
                // else oponentKingInCheck boolean stay's false.
                posTo.getOnPositionChessMan().getChessManOponentKing().setInCheck(true);
                // Oponent is in check : notify.
                notifyCheckSituationToObserver(posTo.getOnPositionChessMan().getChessManOponentKing().getBoardPosition(), true);

            } else {
                posTo.getOnPositionChessMan().getChessManOponentKing().setInCheck(false);
                notifyCheckSituationToObserver(posTo.getOnPositionChessMan().getChessManOponentKing().getBoardPosition(), false);
            }
            //</editor-fold>        

            //<editor-fold defaultstate="collapsed" desc="Pawn promotion">
            // Pawn promotion search is done after King class search for 
            // check or checkmate. If evaluateKingChecked(...) method returns true
            // the Pawn promotion search is not reached and move is invalidated.
            if (posTo.getOnPositionChessMan() instanceof Pawn) {

                Integer xyTo[] = BoardConst.coordinatesIntegerMap.get(posTo.toString());

                // Check position and color.
                if ((this.getColor().equals(BoardConst.WHITE) && xyTo[0] == 1)
                        || (this.getColor().equals(BoardConst.BLACK) && xyTo[0] == 8)) {
                    // Pawn is a candidate for promotion : set new chessman on
                    // the "to position" depending on Pawn class's promotionType
                    // property (sent as param 2 to applyPawnPromotion(...).

                    posTo.setOnPositionChessMan(PawnPromotionUtils.applyPawnPromotion(
                            this.getColor(), ((Pawn) this).getPromotionType(), posTo,
                            this.getChessManKing(), this.getChessManOponentKing(), this.getCheckObserver()));
                }
            }
            //</editor-fold>      

            // If king is not checked and move is a valid chess move,
            // and no rollback has taken place, set a new NullChessMan 
            // on the "from" position.
            // Finally :
            this.incrementMoveCount(); // Increment move counter.
            return true;

        } else {
            throw new InvalidMoveException(posFrom.toString() + posTo.toString()
                    + CommonConst.SPACE_STR + ExceptionConst.EX_ISNOT_A_VALID_MOVE + CommonConst.SPACE_STR
                    + posFrom.getOnPositionChessMan().toString());
        }
    }

    /**
     * The move() method call's this method to assure the move is valid
     * depending on type of ChessMan class's characteristics.
     *
     * @param xyFrom
     * @param xyTo
     * @return boolean.
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException
     */
    @Override
    public boolean isValidMove(final Integer[] xyFrom, final Integer[] xyTo)
            throws InvalidMoveException {
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters Setters">  
    public CheckObserver getCheckObserver() {
        return checkObserver;
    }

    public void setCheckObserver(final CheckObserver checkObserver) {
        this.checkObserver = checkObserver;
    }

    public King getChessManOponentKing() {
        return chessManOponentKing;
    }

    public void setChessManOponentKing(final King chessManOponentKing) {
        this.chessManOponentKing = chessManOponentKing;
    }

    public boolean isVirtualPawn() {
        return virtualPawn;
    }

    public void setVirtualPawn(final boolean virtualPawn) {
        this.virtualPawn = virtualPawn;
    }

    public void setFenValue(final Object fenValue) {
        this.fenValue = fenValue;
    }

    public Object getFenValue() {
        return fenValue;
    }

    public void setNullChessMan(final boolean nullChessMan) {
        this.nullChessMan = nullChessMan;
    }

    public boolean isNullChessMan() {
        return nullChessMan;
    }

    public float getValuation() {
        return valuation;
    }

    public void setValuation(final float valuation) {
        this.valuation = valuation;
    }

    public String getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(final int moveCount) {
        this.moveCount = moveCount;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(final boolean alive) {
        this.alive = alive;
    }

    public Position getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(final Position boardPosition) {
        this.boardPosition = boardPosition;
    }

    public King getChessManKing() {
        return chessManKing;
    }

    public void setChessManKing(final King chessMansKing) {
        this.chessManKing = chessMansKing;
    }
    //</editor-fold>

}
