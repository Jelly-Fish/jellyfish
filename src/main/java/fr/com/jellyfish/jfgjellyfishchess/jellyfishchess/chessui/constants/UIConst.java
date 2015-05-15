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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas Warner 2014
 */
public class UIConst {

    /**
     * New game white icon for controls panel button.
     */
    public static final String NEW_GAME_WHITE_BUTTON_ICON = "/icons/new_game_white.png";
    
    /**
     * New game black icon for controls panel button.
     */
    public static final String NEW_GAME_BLACK_BUTTON_ICON = "/icons/new_game_black.png";
    
    /**
     * Activated icon url for activate engine search support button.
     */
    public static final String SEARCH_SUPPORT_ACTIVATED_BUTTON_ICON = "/icons/activate_search.png";
    
    /**
     * Disactivated icon url for activate engine search support button.
     */
    public static final String SEARCH_SUPPORT_DISACTIVATED_BUTTON_ICON = "/icons/disactivate_search.png";
    
    /**
     * Frame icon path.
     */
    public static final String JELLYFISH_FRAME_ICON = "/icons/jellyfish.png";
    
    /**
     * 32x32 Frame icon url.
     */
    public static final String JELLYFISH_ICON_32 = "/icons/jellyfish32x32.png";
    
    /**
     * 16x16 Frame icon url.
     */
    public static final String JELLYFISH_ICON_16 = "/icons/jellyfish16x16.png";
    
    /**
     * Jellyfish home page uri.
     */
    public static final String JELLYFISH_HOME_PAGE = "http://jellyfishchess.org/";
    
    /**
     * Message appended to text area when loading previous game.
     */
    public static final String DISPLAY_LOADING_PREVIOUS_GAME = ">> Re-loading previous game : %s moves where played.";
    
    /**
     * Settings data path.
     */
    public static final String SETTINGS_SERIALIZATION_PATH = "data/settings.data";
    
    /**
     * Game data path.S
     */
    public static final String GAME_SERIALIZATION_PATH = "data/game.data";
    
    /**
     * game.data files backup directory.
     */
    public static final String GAME_DATA_BACKUP_DIR_PATH = "data/gamesdata/";
    
    /**
     * Jellyfish version.
     */
    public static final String JELLYFISH_V = ">> Jellyfish UI beta 0.5\n";
    
    /**
     * png file extention.
     */
    public static final String PNG_EXT = ".png";
    
    /**
     * .data file extention.
     */
    public static final String DATA_FILE_EXT = ".data";
    
    /**
     * White chessman Pawn.
     */
    public static final String WHITE_PAWN = "wp";
    
    /**
     * White chessman Rook.
     */
    public static final String WHITE_ROOK = "wr";
    
    /**
     * White chessman Knight.
     */
    public static final String WHITE_KNIGHT = "wknight";
    
    /**
     * White chessman Bishop.
     */
    public static final String WHITE_BISHOP = "wb";
    
    /**
     * White chessman Queen.
     */
    public static final String WHITE_QUEEN = "wq";
    
    /**
     * White chessman King.
     */
    public static final String WHITE_KING = "wk";
    
    /**
     * White chessman Pawn.
     */
    public static final String BLACK_PAWN = "bp";
    
    /**
     * White chessman Rook.
     */
    public static final String BLACK_ROOK = "br";
    
    /**
     * White chessman Knight.
     */
    public static final String BLACK_KNIGHT = "bknight";
    
    /**
     * White chessman Bishop.
     */
    public static final String BLACK_BISHOP = "bb";
    
    /**
     * White chessman Queen.
     */
    public static final String BLACK_QUEEN = "bq";
    
    /**
     * White chessman King.
     */
    public static final String BLACK_KING = "bk";
    
    /**
     * 
     */
    public static Map<Character, String> FEN_TO_CHESSMAN_REF = new HashMap<>(); 
    static
    {
        FEN_TO_CHESSMAN_REF.put('r', UIConst.BLACK_ROOK);
        FEN_TO_CHESSMAN_REF.put('n', UIConst.BLACK_KNIGHT);
        FEN_TO_CHESSMAN_REF.put('b', UIConst.BLACK_BISHOP);
        FEN_TO_CHESSMAN_REF.put('q', UIConst.BLACK_QUEEN);
        FEN_TO_CHESSMAN_REF.put('k', UIConst.BLACK_KING);
        FEN_TO_CHESSMAN_REF.put('p', UIConst.BLACK_PAWN);
        FEN_TO_CHESSMAN_REF.put('R', UIConst.WHITE_ROOK);
        FEN_TO_CHESSMAN_REF.put('N', UIConst.WHITE_KNIGHT);
        FEN_TO_CHESSMAN_REF.put('B', UIConst.WHITE_BISHOP);
        FEN_TO_CHESSMAN_REF.put('Q', UIConst.WHITE_QUEEN);
        FEN_TO_CHESSMAN_REF.put('K', UIConst.WHITE_KING);
        FEN_TO_CHESSMAN_REF.put('P', UIConst.WHITE_PAWN);
    }
    
    /**
     * Slash.
     */
    public static final String SLASH = "/";
    
    /**
     * Black token.
     */
    public static final Character BLACK_CHAR = 'b';
    
    /**
     * White token.
     */
    public static final Character WHITE_CHAR = 'w'; 
    
    /**
     * Path to ressources, chessmen.
     */
    public static final String CHESSMEN = "/chessmen/";
    
    /**
     * Path to res boards background png's.
     */
    public static final String BOARD_RES = "/boards/";
    
    /**
     * Classic 2 chessmen style.
     */
    public static final String CLASSIC_TWO = "classictwo";
    
    /**
    * Classic 1 chessmen style.
    */
    public static final String CLASSIC_ONE = "classicone";
    
    /**
    * Classic 2 chessmen style.
    */
    public static final String TWO_D_MODERN_ONE = "twod";
    
    /**
     * Wooden chess board background image (.png).
     */
    public static final String WOOD = "wood.png";
    
    /**
     * Digital chess board background image 1 (.png).
     */
    public static final String DIGITAL_LIGHT1 = "digital_light1.png";
    
    /**
     * Digital chess board background image 2 (.png).
     */
    public static final String DIGITAL_LIGHT2 = "digital_light2.png";
    
    /**
     * Digital chess board background image 3 (.png).
     */
    public static final String DIGITAL_LIGHT3 = "digital_light3.png";
        
    /**
     * Digital chess board background image 4 (.png).
     */
    public static final String DIGITAL_LIGHT4 = "digital_light4.png";
    
    /**
     * Ebony & ivory chess board background image (.png).
     */
    public static final String EBONY_IVORY = "ebony_ivory.png";
    
    /**
     * Color const.
     */
    public static final Color WHITE_COLOR = new Color(255,255,255);
    
    /**
     * Color const.
     */
    public static final Color GRAY_COLOR = new Color(153,153,153);
    
    /**
     * Selected color const.
     */
    public static final Color SELECTED_COLOR = Color.CYAN;
    
    /**
     * 
     */
    public static final Color ENGINE_PREVIOUS_MOVE = Color.BLUE;
    
    /**
     * Chess square border color for hints.
     */
    public static final Color HINT_COLOR = Color.ORANGE;
    
    /**
     * Border width of a selected ChessSquare.
     */
    public static final int BORDER_WIDTH = 1;
    
    /**
     * Mouse over button background color.
     */
    public static final Color CONTROL_HOVER_BACKGROUND = new Color(160,170,170);
    
    /**
     * Control button background color.
     */
    public static final Color CONTROL_MOUSE_EXITED_COLOR = new Color(100,120,120);
    
    /**
     * Control button border color when mouse over.
     */
    public static final Color CONTROL_HOVER_BORDER_COLOR = new Color(50,55,55);
    
    /**
     * Control button border color.
     */
    public static final Color CONTROL_BORDER_COLOR = new Color(30,30,30);
    
    /**
     * Check Color for king.
     */
    public static final Color CHECK_COLOR = Color.RED;
    
    /**
     * King's out of check color.
     */
    public static final Color KING_OUT_OF_CHECK = new Color(30,144,255);
    
    /**
     * Color border of last move position.
     */
    public static final Color LAST_MOVE_COLOR = Color.MAGENTA;
    
    /**
     * Empty string.
     */
    public static final String STR_EMPTY = "";
    
    /**
     * String value of 1 space.
     */
    public static final String STR_SPACE = " ";
    
    /**
     * Back slash n.
     */
    public static final String BACK_SLASH_N = "\n";
    
    /**
     * String value white = "white"
     */
    public static final String WHITE_STR = "white";
    
    /**
     * String value black = "black"
     */
    public static final String BLACK_STR = "black";
    
}
