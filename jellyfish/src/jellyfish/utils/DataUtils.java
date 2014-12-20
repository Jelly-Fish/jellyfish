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

package jellyfish.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import jellyfish.dto.ChessGameDTO;

/**
 * @author Thomas.H Warner 2014
 */
public class DataUtils {
    
    //<editor-fold defaultstate="collapsed" desc="Private static final vars"> 
    /**
     * Path to temp directory for .data files.
     */
    private final static String DATA_TEMP_PATH = "data/";
    
    /**
     * Path to backup / save .data directory.
     */
    private final static String DATA_BACKUP_PATH = "data/gamesdata/";
    
    /**
     * Back slash for building paths.
     */
    private final static String PATH_BACK_SLASH = "\\";
    
    /**
     * String for slash.
     */
    private final static String SLASH = "/";
    
    /**
     * Under score const for building game.data backup file name.
     */
    private final static String UNDER_SCORE = "_";
    
    /**
     * yyyy-mm-dd_hh-mm-ss-ms Date format.
     */
    private final static String DATE_FORMAT_1 = "yyyy-MM-dd_HH-mm-ss-ms";
    
    /**
     * yyyy/MM/dd HH:mm:ss Date format.
     */
    private final static String DATE_FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
    
    /**
     * XML file extention.
     */
    private final static String XML_FILE_EXTENTION = ".xml";
    
    /**
     * String for 6 score.
     */
    private final static String SIX_SCORE = "-";
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Public static methods">
    /**
     * Delete all files from collection of paths in String format.
     * @param paths 
     * @throws java.io.IOException 
     */
    public static void deleteDataFiles(final Collection<String> paths) throws IOException {
        
        for (String path : paths) {
            
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            } else {
                throw new IOException("Failed to delete folowing data file :\n" + file.getPath());
            }
        }
    }
    
    /**
     * Back temp game.data file to correct directory and rename date-time-stamp.data
     * @param color
     * @param fen
     * @param gameMoves
     * @param moveCount
     * @param seconds
     * @throws java.io.FileNotFoundException
     */
    public static void saveGameData(final String color,
        final LinkedHashMap<Integer, String> fen, 
        final LinkedHashMap<Integer, String> gameMoves,
        final int moveCount, final int seconds) throws FileNotFoundException {
        
        final Date date = new Date();
        final DateFormat dateFormat1 = new SimpleDateFormat(DATE_FORMAT_1);
        final DateFormat dateFormat2 = new SimpleDateFormat(DATE_FORMAT_2);
        
        final ChessGameDTO chessGameBean = new ChessGameDTO();
        chessGameBean.setColor(color);
        chessGameBean.setFenMoves(fen);
        chessGameBean.setMoveCount(moveCount);
        chessGameBean.setMoves(gameMoves);
        chessGameBean.setDate(dateFormat2.format(date));
        chessGameBean.setSeconds(seconds);
        chessGameBean.setTimeStr(TimerUtils.convertTicksHhMmSs(seconds));

        final XMLEncoder xmlEncoder = new XMLEncoder(
            new BufferedOutputStream(
            new FileOutputStream(DATA_BACKUP_PATH + dateFormat1.format(date) + XML_FILE_EXTENTION)));
        xmlEncoder.writeObject(chessGameBean);
        xmlEncoder.close();

    }
    
    /**
     * Retreive fen DTO bean from .xml file path.
     * @param path
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public static ChessGameDTO getGameData(final String path) throws FileNotFoundException {

        final XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(path));
        final ChessGameDTO chessGameBean = (ChessGameDTO)xmlDecoder.readObject();
        xmlDecoder.close();

        return chessGameBean;

    }
    //</editor-fold> 
    
}
