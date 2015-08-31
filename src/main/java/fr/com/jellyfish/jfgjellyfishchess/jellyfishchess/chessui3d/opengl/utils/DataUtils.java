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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils;

import com.thoughtworks.xstream.XStream;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas.H Warner 2014
 */
public class DataUtils {

    //<editor-fold defaultstate="collapsed" desc="public static final vars"> 
    /**
     * data directory for serializations.
     */
    public static final String DATA_BACKUP_PATH = "data/";

    /**
     * XML file extention.
     */
    public static final String XML_FILE_EXTENTION = ".xml";

    /**
     * XML file name for serializing move queues.
     */
    public static final String FILE_NAME = "moveq";
    
    /**
     * Games backup directory.
     */
    public static final String GAMES_PATH = "data/games/";
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Public static methods">
    /**
     * @param paths
     * @throws IOException 
     */
    public static void deleteDataFiles(final String ... paths) throws IOException {
        
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
     * @param moveQueue
     * @param filePath
     * @param description
     * @param gameBackup
     */
    public static void xmlSerializeMoveQueue(final MoveQueue moveQueue, final String filePath, 
            final String description, final boolean gameBackup) {
        
        final String emptyStr = "";
        final DateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        final Date today = Calendar.getInstance().getTime();        
        final String date = df.format(today);
        moveQueue.setDate(date);
        moveQueue.setDescription(description);
        
        final String path = (filePath == null || filePath.equals(emptyStr)) ? 
                (gameBackup ? DataUtils.GAMES_PATH + date + DataUtils.XML_FILE_EXTENTION : 
                    DataUtils.DATA_BACKUP_PATH + DataUtils.FILE_NAME + DataUtils.XML_FILE_EXTENTION) :
                filePath;
        
        moveQueue.clearAllObservers();

        final XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);

        try (FileOutputStream fop = new FileOutputStream(new File(path))) {
            
            xstream.toXML(moveQueue, fop);
            
            fop.flush();
            fop.close();
            
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, fnfex);
        } catch (final IOException ioex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ioex);
        }
    }
    
    /**
     * @param moveQueue 
     */
    public static void xmlSerializeMoveQueue(final MoveQueue moveQueue) {
        DataUtils.xmlSerializeMoveQueue(moveQueue, null, null, false);
    }

    /**
     * @param moveQueue
     * @param description
     * @param gameBackup
     */
    public static void xmlSerializeMoveQueue(final MoveQueue moveQueue, final String description,
            final boolean gameBackup) {
        DataUtils.xmlSerializeMoveQueue(moveQueue, null, description, gameBackup);
    }

    /**
     * @return MoveQueue
     */
    public static MoveQueue xmlDeserializeMoveQueue() {

        final XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        MoveQueue mq = null;
        try (FileInputStream fip = new FileInputStream(new File(DataUtils.DATA_BACKUP_PATH
                + DataUtils.FILE_NAME
                + DataUtils.XML_FILE_EXTENTION))) {
            
            mq = (MoveQueue)xstream.fromXML(fip);
            
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, fnfex);
        } catch (final IOException ioex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ioex);
        }
        
        return mq;
    }
    
    /**
     * @param path
     * @return MoveQueue
     */
    public static MoveQueue xmlDeserializeMoveQueue(final String path) {

        final XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        MoveQueue mq = null;
        try (FileInputStream fip = new FileInputStream(new File(path))) {
            
            mq = (MoveQueue)xstream.fromXML(fip);
            
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, fnfex);
        } catch (final IOException ioex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ioex);
        }
        
        return mq;
    }
    
    /**
     * @param path
     * @return List of filenames.
     */
    public static List<String> readFileNames(final String path) {
        
        final List<String> files = new ArrayList<>();
        
        final File dir = new File(DataUtils.GAMES_PATH);
        for (final File file : dir.listFiles()) {
            if (file.isDirectory()) {
                readFileNames(file.getAbsolutePath());
            } else {
                files.add(file.getName());
            }
        }
        
        return files;
    }
    //</editor-fold> 

}
