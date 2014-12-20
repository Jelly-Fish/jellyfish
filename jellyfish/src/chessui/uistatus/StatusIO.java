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

package chessui.uistatus;

import chessui.constants.UIConst;
import chessui.ui.MainUiDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas.H Waner 2014
 */
public class StatusIO {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars"> 
    /**
     * GUIDriver instance.
     */
    private MainUiDriver driver;

    /**
     * User settings insance.
     */
    private UserSettings userSettings;
    
    /**
     * GameStatus class instance.
     */
    private GameStatus gameStatus;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Constructor"> 
    /**
     * Constructor.
     */
    public StatusIO() {
        init();
    }
    //</editor-fold> 
     
    //<editor-fold defaultstate="collapsed" desc="Public methods"> 
    /**
     * Serialize data.
     */
    public void serializeStatus() {
        if (this.userSettings.isLoadPreviousGame()) {
            saveGameStatus();
        }
        saveUserSettings();
    }
    
    /**
     * Deserialize game status from back-up'ed game.data file.
     * @param path
     * @throws java.lang.Exception
     */
    public void reloadGameStatus(final String path) throws Exception {
        
        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;
        
        try {
            fileInputStreamm = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof GameStatus) {
                gameStatus = (GameStatus)obj;
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Private methods"> 
    /**
     * Load and build, deserialize UserSettings class.
     */
    private void loadUserSetting() throws Exception {
        
        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;
        
        try {
            fileInputStreamm = new FileInputStream(UIConst.SETTINGS_SERIALIZATION_PATH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof UserSettings) {
                userSettings = (UserSettings)obj;
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
        
    }
        
    /**
     * Deserialize game status.
     */
    private void loadGameStatus() throws Exception {
        
        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;
        
        try {
            fileInputStreamm = new FileInputStream(UIConst.GAME_SERIALIZATION_PATH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof GameStatus) {
                gameStatus = (GameStatus)obj;
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }
    
    /**
     * Save, serialize UserSettings class.
     */
    private void saveUserSettings() {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(UIConst.SETTINGS_SERIALIZATION_PATH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userSettings);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Serialize game moves.
     */
    private void saveGameStatus() {
        
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(UIConst.GAME_SERIALIZATION_PATH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameStatus);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(StatusIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Init deserialization.
     */
    private void init() {
        
        // If anything goes wrong simply reset to default 'first use' status.
        // Serializable class below have default set properties.
        try {
            loadUserSetting();
            loadGameStatus();
        } catch (Exception ex) {
            userSettings = new UserSettings();
            gameStatus = new GameStatus();
        }
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters"> 
    public UserSettings getUserSettings() {
        return userSettings;
    }
    
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    
    public void setDriver(final MainUiDriver driver) {
        this.driver = driver;
    }
    //</editor-fold>
    
}