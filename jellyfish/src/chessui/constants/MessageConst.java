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

package chessui.constants;

/**
 * @author Thomas.H Warner 2014
 */
public class MessageConst {
    
    /**
     * Console display prefix.
     */
    public static final String DISPLAY_PREFIX = ">> ";
    
    /**
     * Options for new game dialog.
     */
    public static final Object[] OK_CANCEL_OPTIONS = { "OK", "Cancel" };
    
    /**
     * Options for new game dialog.
     */
    public static final Object[] BLITZ_WHITE_BLACK_OPTIONS = { "Play white", "Play black" };
    
    /**
     * Fen display.
     */
    public static final String DISPLAY_FEN = ">> Position fen %s";
    
    /**
     * Ply depth display.
     */
    public final static String DISPLAY_PLY_DEPTH = ">> Engine search depth: %s";
    
    /**
     * Move count display.
     */
    public static final String DISPLAY_MOVE_COUNT = ">> move n°%s";
    
    /**
     * Empty String.
     */
    public static final String STR_EMPTY = "";
    
    /**
     * String space.
     */
    public static final String STR_SPACE = " ";
    
    /**
     * String for \n
     */
    public static final String BACK_SLASH_N = "\n";
    
    /**
     * Data bu failed dilog title.
     */
    public static final String DATA_BU_FAILED_DIALOG_TITLE = "Failed to back-up game data";
    
    /**
     * When jellyfish ver is obsolete and must be updated via jellyfishchess.org.
     */
    public static final String OBSOLETE_VER_JELLYFISH = 
        "Your version of jellyfish is not up to date.\n\nPlease visit http:\\\\jellyfishchess.org for latest download releases of this software."; 
    
    /**
     * Associated title with message above : When jellyfish ver is obsolete and must be updated via jellyfishchess.org.
     */
    public static final String OBSOLETE_VER_JELLYFISH_TITLE = "Obsolete jellyfish version";
    
    /**
     * IO exception when trying to load jellyfish home page via default browser.
     */
    public static final String IO_EXCEPTION_JELLYFISH_HOME_PAGE = 
        "Something may be wrong with your network.\nPlease check your internet connexion and/or that you have a default web browser installed on your operating system.\n\nPlease visit http:\\\\jellyfishchess.org for latest download releases of this software.";
    
    /**
     * Associated title with message above : IO exception when trying to load jellyfish home page via default browser.
     */
    public static final String IO_EXCEPTION_JELLYFISH_HOME_PAGE_TITLE = "Network error";
    
    /**
     * Append to InvalidMoveException if fired.
     */
    public static final String IS_NOT_VALID_MOVE = " is not a valid move.";
    
    /**
     * Message for promotion type setting.
     */
    public static final String PROMOTION_TYPE_SETTINGS_MSG = "To change promotion type go to menu/Game/Pawn promotion settings.";
    
    /**
     * Message to display if GUI is trying to play when it is engines turn, this occurs
     * when GUI forces engine to move for him; an engine move is not triggered after that
     * event.
     */
    public static final String DISPLAY_ERR_ENGINE_TO_PLAY = ">> It is %s's turn to move!";
        
    /**
     * Dialog text for new game confirmation.
     */
    public static final String NEW_GAME_CONFIRMATION_DIALOG_TEXT = "Cancel current game and start a new game\nplaying %ss?";
    
    /**
     * Dialog text for confirming deletion of .data files.
     */
    public static final String DELETE_DATA_FILES_TEXT = "The following game files will deleted:\n\n %s\n";
    
    /**
     * Dialog title for confirming deletion of .data files.
     */
    public static final String DELETE_DATA_FILES_TITLE = "Delete game data files";
    
    /**
     * Dialog title for new game confirmation.
     */
    public static final String NEW_GAME_CONFIRMATION_DIALOG_TITLE = "New game confirmation";
    
    /**
     * Dialog title for error on data files deletion.
     */
    public static final String DELETE_DATA_FILES_ERR_TITLE = "Failed to delete files";
    
    /**
     * Dialog title for error on data files deletion.
     */
    public static final String LOAD_DATA_FILES_TITLE = "Load game from data file";
    
    /**
     * Dialog title for error on data files deletion.
     */
    public static final String LOAD_DATA_FILES_TEXT = "Load the following game data ?\n%s\n";
    
    /**
     * Dialog title for error on data files deletion.
     */
    public static final String LOAD_DATA_FILES_ERR_TEXT = "Unable to load game from XML data file.\nPlease try again or contact jellyfish staff.";
    
    /**
     * Dialog title for error on data files deletion.
     */
    public static final String LOAD_DATA_FILES_ERR_TITLE = "Error loading game file";
    
}

