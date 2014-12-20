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

package jellyfish.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jellyfish.entities.Position;
import jellyfish.entities.chessmen.AbstractChessMan;
import jellyfish.utils.CommonUtils;

/**
 * Board class instances collection.
 * @author Thomas.H Warner 2014
 */
public class BoardSnapshot implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Snapshot of Board class coordinates of Position classes ChessMan classes.
     */
    private LinkedHashMap<String, AbstractChessMan> snapshot;
    
    /**
     * Fen string associated with snapshot.
     */
    private String fen;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private transient vars">
    /**
     * Move number of snapshop.
     */
    private transient int move;
    
    /**
     * File name ref.
     */
    private transient String fileRef;
    
    /**
     * Path to temp directory for .snapshot files.
     */
    private final static transient String SNAPSHOT_TEMP_PATH = "data/snapshots/temp/";
    
    /**
     * Path to backup / save snapshots directory.
     */
    private final static transient String SNAPSHOT_BACKUP_PATH = "data/snapshots/games/";
    
    /**
     * Back slash for building paths.
     */
    private final static transient String PATH_BACK_SLASH = "\\";
    
    /**
     * Snapshot file extention.
     */
    private final static transient String SNAPSHOT_FILE_EXTENTION = ".snapshot";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Serialization constructor.
     * @param snapshots
     * @param move
     */
    BoardSnapshot(final LinkedHashMap<String, Position> snapshot, final int move,
        final String fen) {
        
        this.snapshot = new LinkedHashMap<>();
        
        for (Map.Entry<String, Position> entry : snapshot.entrySet()) {
            AbstractChessMan chessMan = entry.getValue().getOnPositionChessMan();
            String position = entry.getValue().getCoordinates();
            this.snapshot.put(position, chessMan);
        }
        
        this.fen = fen;
        this.move = move;
        this.fileRef = SNAPSHOT_TEMP_PATH + String.valueOf(move) + SNAPSHOT_FILE_EXTENTION;
    }
    
    /**
     * Deserialization constructor.
     * @param move 
     */
    BoardSnapshot(final int move) {
        this.move = move;
        this.fileRef = SNAPSHOT_TEMP_PATH + String.valueOf(move) + SNAPSHOT_FILE_EXTENTION;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Package private Methods">
    /**
     * Serialize this.
     */
    void serialize() {
        
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(this.fileRef);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoardSnapshot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(BoardSnapshot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Deserialize.
     */
    void deserialize() {
        
        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;
        
        try {
            fileInputStreamm = new FileInputStream(this.fileRef);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoardSnapshot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof BoardSnapshot) {
                this.snapshot = ((BoardSnapshot)obj).getSnapshot();
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BoardSnapshot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Delete all snapshots > to index.
     * @param index 
     */
    static void deleteSnapshots(final int index) {
        
        File[] files = new File(SNAPSHOT_TEMP_PATH).listFiles();
        String snapshot = new String();
        Integer snapshotNumber = null;
        //some JVM's will return null for empty directories :
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Then call recursivly :
                    deleteSnapshots(file);
                } else { 
                    // it's a file, test for deletion :
                    try {
                        snapshot = CommonUtils.removeFileExtension(file);
                        snapshotNumber = Integer.valueOf(snapshot);
                    } catch (NumberFormatException ex) {
                        // TODO : really bad in this case...
                        Logger.getLogger(BoardSnapshot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if (snapshotNumber > index) {
                        file.delete();
                    }
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Save / backup shapshots to games directory.
     */
    public static void saveSnapshots() throws SecurityException {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-ms");
        Date date = new Date();
        
        // Move all snapshots to new file.
        // New file name = date/time stamp.
        // Move count = count of snapshot files in directory.
        File buDirectory = new File(SNAPSHOT_BACKUP_PATH + dateFormat.format(date));

        // if the directory does not exist, create it :
        if (!buDirectory.exists()) {
            try {
              buDirectory.mkdir();
            } catch (SecurityException se){
                throw se;
            }
        }
        
        // Move all .snapshots files to new directory.
        File tempDirectory = new File(SNAPSHOT_TEMP_PATH);
        if (tempDirectory.isDirectory()) {
            File[] snapshots = tempDirectory.listFiles();
            for (File snapshot : snapshots) {
                snapshot.renameTo(new File(buDirectory.getPath() + PATH_BACK_SLASH + snapshot.getName()));
            }
        }
    }
        
    /**
     * Delete all shapshots from snapshot directory.
     * @param snapshotsDir
     */
    public static void deleteSnapshots(final File snapshotsDir) {
        
        File[] files = snapshotsDir.listFiles();
        //some JVM's will return null for empty directories :
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Then call recursivly :
                    deleteSnapshots(file);
                } else {
                    file.delete();
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    LinkedHashMap<String, AbstractChessMan> getSnapshot() {
        return snapshot;
    }
    
    public static String getSNAPSHOT_PATH() {
        return SNAPSHOT_TEMP_PATH;
    }
    //</editor-fold>
    
}
