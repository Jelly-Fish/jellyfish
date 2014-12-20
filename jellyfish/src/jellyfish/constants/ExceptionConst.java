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

package jellyfish.constants;

/**
 * @author Thomas Warner 2014
 */
public class ExceptionConst {
    
    /**
     * Is not a valid move exception msg.
     */
    public static final String EX_ISNOT_A_VALID_MOVE = " is not a valid move for ";
    
    /**
    * Checkmate exception end message.
    */
    public static final String EX_KING_CHECKMATE_ENDMSG = " King is checkmate!";
            
    /**
    * End of InvalidPositionException messsage.
    */
    public static final String EX_INVALID_POS_ENDMSG = " - Invalid position"; 
    
    /**
     * PawnPromotionException message.
     */
    public static final String EX_PAWNPROMOTION_MSG = "Pawn promotion has failed : return ChessMan class instance is null.";
    
    /**
     * Fen convertion exception.
     */
    public static final String EX_FEN_CONVERTION_MSG = "Fen convertion failed at line number %s. Map size equals %s.";

    /**
     * Index move out of bounds exception message.
     */
    public static final String EX_MOVE_OUT_OF_BOUNDS = "The game move counter = %s, move index %s is out of bounds.";
    
    /**
     * IOExternalEngine class cannot find it's ChessGame observer instance or it is null.
     */
    public static final String EX_IOEXTERNALENGINE_NULL_GAME_INSTANCE = "IOExternalEngine class cannot find it's ChessGame observer instance or it is null";
    
    /**
     * ChessGameBuildException message; when ChessGame return value is null.
     */
    public static final String EX_GAME_BUILD_ERROR = "Error building new chess game";
    
    /**
     * ChessGameBuildException message; when game type is null.
     */
    public static final String EX_GAME_TYPE_ERROR = "No game type [String] has been sent in params.";
    
    /**
     * InvalidChessPositionException message; when Position class is out of board's
     * possible chess position.
     */
    public static final String EX_INVALID_CHESS_POS = "Invalid position : %s does not exist.";
    
}
