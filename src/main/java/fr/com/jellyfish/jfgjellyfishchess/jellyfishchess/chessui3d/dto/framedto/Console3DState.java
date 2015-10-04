/**
 * *****************************************************************************
 * Copyright (c) 2015, Thomas.H Warner. All rights reserved.
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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.framedto;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public class Console3DState implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * Singleton instance.
     */
    private static Console3DState instance = null;
    
    /**
     * Console's x location.
     */
    private Integer locationX = 20;
    
    /**
     * Console's y location.
     */
    private Integer locationY = 20;

    /**
     * Console's width.
     */
    private Integer width = 440;
    
    /**
     * Console's height.
     */
    private Integer height = 661;
    
    /**
     * JFrame.state enum value (maximized, iconified ect.)
     */
    private Integer frameState = javax.swing.JFrame.NORMAL;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="private constructor">
    /**
     *
     */
    private Console3DState() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public methods">
    /**
     * Singleton accessor.
     *
     * @return Console3DState's only instance.
     */
    public static Console3DState getInstance() {

        if (Console3DState.instance == null) {
            Console3DState.instance = new Console3DState();
        }

        return Console3DState.instance;
    }
    
    /**
     * Apply previous serialized state to frame.
     * @param frame 
     */
    public void applyState(final javax.swing.JFrame frame) {
        frame.setLocation(this.locationX, this.locationY);
        frame.setSize(this.width, this.height);
        frame.setState(this.frameState);
    }
    
    /**
     * Prepare state for serialization.
     * @param frame 
     */
    public void backupState(final javax.swing.JFrame frame) {
        this.frameState = frame.getState();
        this.width = frame.getWidth();
        this.height = frame.getHeight();
        this.locationX = frame.getX();
        this.locationY = frame.getY();
    }
    
    /**
     * @return location of the frame.
     */
    public Point getLocation() {
        return new Point(this.locationX, this.locationY);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public serialization methods">
    /**
     * Save, serialize user's setup.
     */
    public void serialize() {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream("data/srlz/cons3dste");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(Console3DState.class.getName()).log(Level.SEVERE, null, fnfex);
        }

        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (final IOException ioex) {
            Logger.getLogger(Console3DState.class.getName()).log(Level.SEVERE, null, ioex);
        }

    }

    /**
     *
     */
    public void deserialize() {

        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStreamm = new FileInputStream("data/srlz/cons3dste");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(Console3DState.class.getName()).log(Level.SEVERE, null, fnfex);
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof Console3DState) {
                Console3DState.instance = ((Console3DState) obj);
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (final IOException ioex) {
            Logger.getLogger(Console3DState.class.getName()).log(Level.SEVERE, null, ioex);
        } catch (final ClassNotFoundException cnfex) {
            Logger.getLogger(Console3DState.class.getName()).log(Level.SEVERE, null, cnfex);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public void setFrameState(final Integer frameState) {
        this.frameState = frameState;
    }
    
    public void setLocationX(final Integer locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(final Integer locationY) {
        this.locationY = locationY;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }

    public void setHeight(final Integer height) {
        this.height = height;
    }
        
    public Integer getLocationX() {
        return locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getFrameState() {
        return frameState;
    }
    //</editor-fold>
    
}
