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
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.KingEvaluable;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.CastlingUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The king chessman.
 *
 * @author Thomas.H Warner 2014
 */
public class King extends AbstractChessMan implements KingEvaluable {

    //<editor-fold defaultstate="collapsed" desc="private variables">
    /**
     * If King is in check or not.
     */
    private boolean inCheck = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
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
    public King(final String COLOR, final float valuation, final boolean alive,
            final Position boardPosition, final boolean nullChessMan, final char fenValue) {

        super(COLOR, valuation, alive, boardPosition, nullChessMan, fenValue);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden methods">  
    @Override
    public boolean isValidMove(final Integer[] xyFrom, final Integer[] xyTo) throws InvalidMoveException {
        // The first king move is a special move because it makes castling 
        // impossible through the rest of the game.
        // Color has no importance : a black king moves like a white one.

        // Check the length of move. If move count is > 1 step and king is 
        // still is allowed to castle then check for castling move, else 
        // it's an invalid move.
        //<editor-fold defaultstate="collapsed" desc="Castling evaluation">
        if (this.getMoveCount() == 0 && CastlingUtils.isCastling(xyFrom, xyTo)) {

            // Castling is permissible if and only if all of the following 
            // conditions hold (Schiller 2001:19):
            //
            //    The king and the chosen rook are on the player's first rank.[3]
            //    Neither the king nor the chosen rook have previously moved.
            //    There are no pieces between the king and the chosen rook.
            //    The king is not currently in check.
            //    The king does not pass through a square that is attacked by an enemy piece.[4]
            //    The king does not end up in check. (True of any legal move.)
            //
            // Conditions 4 through 6 can be summarized with the more memorable 
            // phrase: "One cannot castle out of, through, or into check."
            // Then check for castling. If castling false return false now.
            String kingPosition = Board.getInstance().getCoordinates().get(
                    BoardConst.getPostionFromIntegers(xyFrom)).toString();
            String kingPositionToo = Board.getInstance().getCoordinates().get(
                    BoardConst.getPostionFromIntegers(xyTo)).toString();

            final LinkedHashMap<String, Position> coordinates = Board.getInstance().getCoordinates();
            if (this.getColor().equals(BoardConst.WHITE)) {
                if (xyFrom[1] > xyTo[1]) { // A1 castling.
                    Rook rookA1 = (Rook) coordinates.get(BoardConst.A1).getOnPositionChessMan();
                    if (kingPosition.equals(BoardConst.E1)
                            && kingPositionToo.equals(BoardConst.C1)
                            && rookA1.getMoveCount() == 0
                            && coordinates.get(BoardConst.B1).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.C1).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.D1).getOnPositionChessMan().isNullChessMan()) {

                        // See castling rules above.
                        // Check e1, d1 and c1.
                        if (isKingInCheckSituation(xyFrom) || isKingInCheckSituation(xyTo)
                                || isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(BoardConst.D1))) {
                            return false;
                        } else {
                            // Ok for white king castling with A1 rook.
                            rookA1.applyRookKingCastling(BoardConst.A1, BoardConst.D1);
                            return true;
                        }
                    }
                } else if (xyFrom[1] < xyTo[1]) { // H1 castling.
                    Rook rookH1 = (Rook) coordinates.get(BoardConst.H1).getOnPositionChessMan();
                    if (kingPosition.equals(BoardConst.E1)
                            && kingPositionToo.equals(BoardConst.G1)
                            && rookH1.getMoveCount() == 0
                            && coordinates.get(BoardConst.F1).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.G1).getOnPositionChessMan().isNullChessMan()) {

                        // See castling rules above.
                        // Check e1, f1 and g1.
                        if (isKingInCheckSituation(xyFrom) || isKingInCheckSituation(xyTo)
                                || isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(BoardConst.F1))) {
                            return false;
                        } else {
                            // Ok for white king castling with H1 rook.
                            rookH1.applyRookKingCastling(BoardConst.H1, BoardConst.F1);
                            return true;
                        }
                    }
                }
            } else if (this.getColor().equals(BoardConst.BLACK)) {
                if (xyFrom[1] > xyTo[1]) { // A8 castling.
                    Rook rookA8 = (Rook) coordinates.get(BoardConst.A8).getOnPositionChessMan();
                    if (kingPosition.equals(BoardConst.E8)
                            && kingPositionToo.equals(BoardConst.C8)
                            && rookA8.getMoveCount() == 0
                            && coordinates.get(BoardConst.B8).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.C8).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.D8).getOnPositionChessMan().isNullChessMan()) {

                        // See castling rules above.
                        // Check e8, d8 and g8.
                        if (isKingInCheckSituation(xyFrom) || isKingInCheckSituation(xyTo)
                                || isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(BoardConst.D8))) {
                            return false;
                        } else {
                            // Ok for white king castling with A8 rook.
                            rookA8.applyRookKingCastling(BoardConst.A8, BoardConst.D8);
                            return true;
                        }
                    }
                } else if (xyFrom[1] < xyTo[1]) { // H8 castling.
                    Rook rookH8 = (Rook) coordinates.get(BoardConst.H8).getOnPositionChessMan();
                    if (kingPosition.equals(BoardConst.E8)
                            && kingPositionToo.equals(BoardConst.G8)
                            && rookH8.getMoveCount() == 0
                            && coordinates.get(BoardConst.F8).getOnPositionChessMan().isNullChessMan()
                            && coordinates.get(BoardConst.G8).getOnPositionChessMan().isNullChessMan()) {

                        // See castling rules above.
                        // Check e8, f8 and g8.
                        if (isKingInCheckSituation(xyFrom) || isKingInCheckSituation(xyTo)
                                || isKingInCheckSituation(BoardConst.coordinatesIntegerMap.get(BoardConst.F8))) {
                            return false;
                        } else {
                            // Ok for white king castling with H8 rook.
                            rookH8.applyRookKingCastling(BoardConst.H8, BoardConst.F8);
                            return true;
                        }
                    }
                }
            }
        } // End castling evaluation.
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Duplicated code from Rook & Bishop class's: prepare validation">
        boolean rookTypeMove = false;
        boolean bishopTypeMove = false;

        char direction = '0'; // Rook type move. See Rook's class isValidMove method.
        int diagonal = 0; // 4 diagonals are possible.

        // First, if it is a rook type move then check for valid rook move. Else
        // it is a bishop type move. Can't be both...
        // If move is on a straight line then it's a rook move : 
        if (xyFrom[0].equals(xyTo[0]) || xyFrom[1].equals(xyTo[1])) {
            rookTypeMove = true;

            // Is it right, left, up or down move ?
            // 'u' == up, 'r' == right ect.
            if (xyFrom[0].equals(xyTo[0])) { // Left or right ?
                direction = xyFrom[1] < xyTo[1] ? 'r' : 'l';
            } else { // up or down ?
                direction = xyFrom[0] < xyTo[0] ? 'd' : 'u';
            }
        } else {
            bishopTypeMove = true;

            // 1: up.right - 2: down.right - 3: up.left - 4: down.left
            if (xyFrom[1] < xyTo[1]) { // Left or right ?
                // It's right.
                diagonal = xyFrom[0] > xyTo[0] ? 1 : 2; // Up or down ?
            } else if (xyFrom[1] > xyTo[1]) {
                // Move is left.
                diagonal = xyFrom[0] > xyTo[0] ? 3 : 4; // Up or down ?
            }
        }
        //</editor-fold>

        Integer[] singleBishopMove = null;
        Integer[] singleRookMove = null;

        if (rookTypeMove) {
            singleRookMove = getSingleRookStep(direction, xyFrom, xyTo);
        } else { // Bishop type move.
            singleBishopMove = getSingleBishopStep(diagonal, xyFrom, xyTo);
        }

        // If none of these move types are valid, move is invalidated.
        if ((bishopTypeMove && singleBishopMove[0] != -1)
                || (rookTypeMove && singleRookMove[0] != -1)) {

            if (!Board.getInstance().getCoordinates().get(
                    BoardConst.getPostionFromIntegers(xyTo)
            ).getOnPositionChessMan().isNullChessMan()) {
                // Here to position is a non null chess man :
                // Check for attack. Meaning position is occupied by a != color chessman.

                if (!Board.getInstance().getCoordinates().get(
                        BoardConst.getPostionFromIntegers(xyTo)
                ).getOnPositionChessMan().getColor().equals(this.getColor())) {
                    // It is a valid attack situation.
                    // Search if king is in check or checkmate situation. 
                    // Invalidate move if An openent chessman is attacking King.
                    return !isKingInCheckSituation(xyTo); // Move is validated :
                } else {
                    return false;
                }

            } else {
                // Search if king is in checked or checkmate situation. 
                // Invalidate move if An openent chessman is attacking King.
                return !isKingInCheckSituation(xyTo); // Move is validated :
            }

        } else { // Invalid move.
            return false;
        }

    }

    @Override
    public boolean isKingInCheckSituation(final Integer[] xyPos) {

        //<editor-fold defaultstate="collapsed" desc="Search for King in check situation">
        // Get all rook and bishop type positions aheand, then check for attacking
        // chessman that may block king move. Finally check for checkmate :
        // check all king's posible moves, if none are possible because of
        // checking oponents then == checkmate : throw exception.
        // Lists of FEN xy possible attack positions depending on type.
        List<Integer[]> possibleAttacksKnight = new ArrayList<>();
        List<Integer[]> possibleAttacksPawn = new ArrayList<>();
        List<List<Integer[]>> possibleAttacksBishopQueen = new ArrayList<>();
        List<List<Integer[]>> possibleAttacksRookQueen = new ArrayList<>();
        List<Integer[]> possibleAttacksKing = new ArrayList<>();

        // Knight positions for a possible attack.
        possibleAttacksKnight.addAll(CastlingUtils.getPossibleKnightAttacks(xyPos, this.getColor()));
        if (possibleAttacksKnight.size() > 0) {
            return true;
        }
        // Pawn positions for a possible attack.
        possibleAttacksPawn.addAll(CastlingUtils.getPossiblePawnAttacks(xyPos, this.getColor()));
        if (possibleAttacksPawn.size() > 0) {
            return true;
        }
        // King positions for a possible attack.
        possibleAttacksKing.addAll(getPossibleKingAttacks(xyPos));
        if (possibleAttacksKing.size() > 0) {
            return true;
        }
        // Here simulate a max lenght Rook type move in order to cover all posible 
        // attacks. Futur iterations will be breaked if check to King if found.
        // 'u' is up, 'l' left ect.
        // Rook positions also means Queen position.
        possibleAttacksRookQueen.add(CastlingUtils.getRookPositions(CommonConst.U_CHAR_LOWERCASE, xyPos));
        possibleAttacksRookQueen.add(CastlingUtils.getRookPositions(CommonConst.D_CHAR_LOWERCASE, xyPos));
        possibleAttacksRookQueen.add(CastlingUtils.getRookPositions(CommonConst.L_CHAR_LOWERCASE, xyPos));
        possibleAttacksRookQueen.add(CastlingUtils.getRookPositions(CommonConst.R_CHAR_LOWERCASE, xyPos));

        // Iterate through list of list of positions.
        // If chessman found and is of a certain type of ChessMan sub class,
        // then move is invalid.
        final LinkedHashMap<String, Position> coordinates = Board.getInstance().getCoordinates();
        for (List<Integer[]> xyList : possibleAttacksRookQueen) {
            for (Integer[] xy : xyList) {

                if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0) {
                    if (!coordinates.get(BoardConst.getPostionFromIntegers(xy)
                    ).getOnPositionChessMan().isNullChessMan()) {
                        // Here loop has encountered a chess man in loop on a line :
                        // Check for attack. If instance of Rook or Queen,
                        // king is in check status.

                        AbstractChessMan threat = coordinates.get(
                                BoardConst.getPostionFromIntegers(xy)).getOnPositionChessMan();

                        if ((threat instanceof Rook || threat instanceof Queen)
                                && !coordinates.get(BoardConst.getPostionFromIntegers(xy)
                                ).getOnPositionChessMan().getColor().equals(this.getColor())) {
                            // King is in a check situation 
                            // and move is invalid. Return true to check status codition.
                            return true;
                        } else {
                            // If for example loop encounters a Bishop, Pawn or Knight
                            // then King is coved by this ChessMan for futher Rook type 
                            // moves. Jump out of loop and iterate on list loop.
                            break;
                        }
                    }
                }
            }
        }

        // Here simulate a max length Bishop type move from xyPos position.
        // Bishop positions also means Queen position.
        // 1: up.right - 2: down.right - 3: up.left - 4: down.left
        possibleAttacksBishopQueen.add(CastlingUtils.getBihopDiagonals(1, xyPos));
        possibleAttacksBishopQueen.add(CastlingUtils.getBihopDiagonals(2, xyPos));
        possibleAttacksBishopQueen.add(CastlingUtils.getBihopDiagonals(3, xyPos));
        possibleAttacksBishopQueen.add(CastlingUtils.getBihopDiagonals(4, xyPos));

        // Iterate through list of position list. 
        // If chessman found and is of a certain type of ChessMan sub class,
        // then move is invalid.
        for (List<Integer[]> xyList : possibleAttacksBishopQueen) {
            for (Integer[] xy : xyList) {

                if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0) {
                    if (!coordinates.get(BoardConst.getPostionFromIntegers(xy)
                    ).getOnPositionChessMan().isNullChessMan()) {
                        // Here loop has encountered a chess man :
                        // Check for attack. If instance of Rook or Queen,
                        // king is in check status.

                        AbstractChessMan threat = Board.getInstance().getCoordinates().get(
                                BoardConst.getPostionFromIntegers(xy)).getOnPositionChessMan();

                        if ((threat instanceof Bishop || threat instanceof Queen)
                                && !coordinates.get(BoardConst.getPostionFromIntegers(xy)
                                ).getOnPositionChessMan().getColor().equals(this.getColor())) {
                            return true;
                        } else {
                            // If for example loop encounters a Rook or Knight, then
                            // King is coved by this ChessMan for futher Bishop type 
                            // moves. Jump out of loop and iterate on list loop.
                            break;
                        }
                    }
                }
            }
        }
        //</editor-fold>

        // All check situations have been searched, no threats found. 
        return false;
    }

    @Override
    public String toString() {
        if (this.isAlive()) {
            final String chessman = this.getClass().getName().replace(CommonConst.CHESSMEN_PACKAGE, CommonConst.EMPTY_STR);
            return chessman + CommonConst.UPPER_DASH + this.getColor()
                    + CommonConst.AT + this.getBoardPosition().toString() + CommonConst.BACKSLASH_N
                    + CommonConst.MOVE_COUNT + String.valueOf(this.getMoveCount())
                    + CommonConst.BACKSLASH_N + CommonConst.IN_CHECK_DISPLAY + String.valueOf(inCheck);
        } else {
            final String chessman = this.getClass().getName().replace(CommonConst.CHESSMEN_PACKAGE, CommonConst.EMPTY_STR);
            return chessman + CommonConst.UPPER_DASH + this.getColor()
                    + CommonConst.AT + this.getBoardPosition().toString() + CommonConst.SPACE_STR
                    + CommonConst.REST_IN_PEACE;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">   
    /**
     * Check if a king diagonal move is a one step move. Else return's -1
     *
     * @param diagonal
     * @param xyFrom
     * @param xyTo
     * @return boolean
     */
    private Integer[] getSingleBishopStep(final int diagonal, final Integer[] xyFrom, final Integer[] xyTo) {

        Integer[] position = null;

        // Get all possible numeric coordinates.
        // 1: up.right - 2: down.right - 3: up.left - 4: down.left
        switch (diagonal) {
            case 1:
                position = new Integer[]{xyFrom[0] - 1, xyFrom[1] + 1};
                break;
            case 2:
                position = new Integer[]{xyFrom[0] + 1, xyFrom[1] + 1};
                break;
            case 3:
                position = new Integer[]{xyFrom[0] - 1, xyFrom[1] - 1};
                break;
            case 4:
                position = new Integer[]{xyFrom[0] + 1, xyFrom[1] - 1};
                break;
            default:
                break;
        }

        // If position is null or != to xyTo coordinates then move is invalid.
        if (position == null || !(position[0].equals(xyTo[0]) && position[1].equals(xyTo[1]))) {
            position = new Integer[]{-1};
        }

        // In any case return array.
        return position;
    }

    /**
     * Check if rook like move is a single step. Else return's -1.
     *
     * @param direction
     * @param xyFrom
     * @param xyTo
     * @return boolean
     */
    private Integer[] getSingleRookStep(final char direction, final Integer[] xyFrom,
            final Integer[] xyTo) {

        Integer[] position = null;

        switch (direction) {
            case CommonConst.U_CHAR_LOWERCASE:
                position = new Integer[]{xyFrom[0] - 1, xyFrom[1]};
                break;
            case CommonConst.D_CHAR_LOWERCASE:
                position = new Integer[]{xyFrom[0] + 1, xyFrom[1]};
                break;
            case CommonConst.L_CHAR_LOWERCASE:
                position = new Integer[]{xyFrom[0], xyFrom[1] - 1};
                break;
            case CommonConst.R_CHAR_LOWERCASE:
                position = new Integer[]{xyFrom[0], xyFrom[1] + 1};
                break;
            default:
                break;
        }

        // If position is null or != to xyTo coordinates then move is invalid.
        if (position == null || !(position[0].equals(xyTo[0]) && position[1].equals(xyTo[1]))) {
            position = new Integer[]{-1};
        }

        // In any case return array.
        return position;
    }

    /**
     * Get possible attacks for King to King.
     *
     * @param xyPos
     * @return ArrayList of Integer[]
     */
    private ArrayList<Integer[]> getPossibleKingAttacks(final Integer[] xyPos) {

        ArrayList<Integer[]> positions = new ArrayList<>();
        ArrayList<Integer[]> realPositions = new ArrayList<>();

        // 8 posible position surronding position to analyze.
        // Start at top and go clockwise :
        positions.add(new Integer[]{xyPos[0] - 1, xyPos[1]});
        positions.add(new Integer[]{xyPos[0] - 1, xyPos[1] + 1});
        positions.add(new Integer[]{xyPos[0], xyPos[1] + 1});
        positions.add(new Integer[]{xyPos[0] + 1, xyPos[1] + 1});
        positions.add(new Integer[]{xyPos[0] + 1, xyPos[1]});
        positions.add(new Integer[]{xyPos[0] + 1, xyPos[1] - 1});
        positions.add(new Integer[]{xyPos[0], xyPos[1] - 1});
        positions.add(new Integer[]{xyPos[0] - 1, xyPos[1] - 1});

        // Trim out off board, null and non oponent positions.
        final LinkedHashMap<String, Position> coordinates = Board.getInstance().getCoordinates();
        for (Integer[] xy : positions) {
            // If safe/real position and != thisColor &&
            // !null ChessMan and instance of King then add to realPositions list.
            if (xy[0] < 9 && xy[0] > 0 && xy[1] < 9 && xy[1] > 0
                    && !coordinates.get(BoardConst.getPostionFromIntegers(xy)).getOnPositionChessMan().isNullChessMan()
                    && !coordinates.get(BoardConst.getPostionFromIntegers(xy)
                    ).getOnPositionChessMan().getColor().equals(this.getColor())
                    && coordinates.get(BoardConst.getPostionFromIntegers(xy)).getOnPositionChessMan() instanceof King) {
                // Oponent King is attacking and checking King on this move position.
                realPositions.add(xy);
            }
        }

        // Return trimed list of positions:
        return realPositions;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">   
    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }
    //</editor-fold>

}
