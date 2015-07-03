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

/**
 * UCI Engine com constants.
 * @author Thomas.H Warner 2014
 */
public class UCIConst {
    
    /**
    *   tell engine to use the uci (universal chess interface),
    *	this will be sent once as a first command after program boot
    *	to tell the engine to switch to uci mode
    *   After receiving the uci command the engine must identify itself with the "id" command
    *	and send the "option" commands to tell the GUI which engine settings the engine supports if any
    *	After that the engine should send "uciok" to acknowledge the uci mode
    *	If no uciok is sent within a certain time period, the engine task will be killed by the GUI.
    */
    public static final String UCI = "uci";
    
    /**
     * Command for turning debug off.
     */
    public static final String DEBUG_OFF = "debug off";
    
    /**
     * Commmand for turning debug on.
     */
    public static final String DEBUG_ON = "debug on";
    
    /**
    * uciok engine response.
    */
    public static final String UCI_OK = "uciok";
    
    /**
    * readyok engine response.
    */
    public static final String READY_OK = "readyok";
    
    /**
     * Engine's ponder for checkmate situation.
     */
    public static final String NONE = "(none)";
    
    /**
     * Engine's ponder for checkmate situation.
     */
    public static final String BESTMOVE_NONE_PONDER_NONE = "bestmove (none) ponder (none)";
    
    /**
     * Best move none for checkmate situation.
     */
    public static final String PONDER_NONE = "ponder (none)";
    
    /**
     * Minimum game instance move count for a checkmate situation.
     * http://en.wikipedia.org/wiki/Fool%27s_mate
     */
    public static final int FOOLS_MATE = 4;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_MASTER = 15;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_EXPERT = 12;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_HARDER = 10;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_HARD = 7;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_MEDIUM = 5;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_EASY = 3;
    
    /**
     * Search depth for engine.
     */
    public static final int DEPTH_DUMMY = 1;
    
    /**
     *  Character to add between moves if capture / take.
     */
    public static final String CAPTURE = "x";
    
    /**
     * Moves cmd.
     */
    public static final String MOVES = "moves";
    
    /**
     * Start position for engine.
     */
    public static final String START_POS = "startpos";
    
    /**
     * Ping engine and check it's ready.
     */
    public static final String IS_READY = "isready";
    
    /**
     * Part of a fen string cmd.
     */
    public static final String FEN = "fen";
    
    /**
     * Part of a UCI fen cmd.
     */
    public static final String POS = "position";
    
    /**
     * UCI fen cmd prefix.
     */
    public static final String POS_FEN = "position fen ";
    
    /**
     * Begining of fen cmd.
     */
    public static final String POSITION_START = "position start";
    
    /**
     * A simple space to build FEN string.
     */
    public static final String SPACE = " ";
    
    /**
     * Depth cmd for engine.
     */
    public static final String GO_DEPTH = "go depth";
    
    /**
     * Promotion marker for a queen.
     */
    public static final char QUEEN_PROMOTION = 'q';
    
    /**
     * Promotion marker for a rook.
     */
    public static final char ROOK_PROMOTION = 'r';
    
    /**
     * Promotion marker for a knight.
     */
    public static final char KNIGHT_PROMOTION = 'n';
    
    /**
     * Promotion marker for a bishop.
     */
    public static final char BISHOP_PROMOTION = 'b';
    
    /**
     * Char for blacks.
     */
    public static final char BLACK_CHAR_LOWER = 'b';
    
    /**
     * Char for whites.
     */
    public static final char WHITE_CHAR_LOWER = 'w';
    
    /**
     * Quit command sent to engine.
     */
    public static final String ENGINE_QUIT = "quit";
    
    /**
     * A dummy char.
     */
    public static final char DUMMY = '£';
    
    /**
     * Infinite search command param.
     */
    public static final String INFINITE_SEARCH = "go infinite";
    
    /**
     * Stop engine search : for infinite or time set searching.
     */
    public static final String INFINITE_SEARCH_STOP = "stop";
    
    /**
     * Infinite search result display prefix.
     */
    public static String INFINITE_SEARCH_RESULT = ">> Search result:";
    
}
