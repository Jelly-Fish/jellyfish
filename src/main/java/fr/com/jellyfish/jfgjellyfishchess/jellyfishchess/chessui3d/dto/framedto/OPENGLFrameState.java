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
public class OPENGLFrameState implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * Singleton instance.
     */
    private static OPENGLFrameState instance = null;
    
    /**
     * Console's x location.
     */
    private Integer locationX = 20;

    /**
     * Console's y location.
     */
    private Integer locationY = 20;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="private constructor">
    /**
     *
     */
    private OPENGLFrameState() {
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public methods">
    /**
     * Singleton accessor.
     *
     * @return Console3DState's only instance.
     */
    public static OPENGLFrameState getInstance() {

        if (OPENGLFrameState.instance == null) {
            OPENGLFrameState.instance = new OPENGLFrameState();
        }

        return OPENGLFrameState.instance;
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
            fileOutputStream = new FileOutputStream("data/srlz/openglfrmste");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(OPENGLFrameState.class.getName()).log(Level.SEVERE, null, fnfex);
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
            Logger.getLogger(OPENGLFrameState.class.getName()).log(Level.SEVERE, null, ioex);
        }

    }

    /**
     *
     */
    public void deserialize() {

        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStreamm = new FileInputStream("data/srlz/openglfrmste");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(OPENGLFrameState.class.getName()).log(Level.SEVERE, null, fnfex);
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof OPENGLFrameState) {
                OPENGLFrameState.instance = ((OPENGLFrameState) obj);
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (final IOException ioex) {
            Logger.getLogger(OPENGLFrameState.class.getName()).log(Level.SEVERE, null, ioex);
        } catch (final ClassNotFoundException cnfex) {
            Logger.getLogger(OPENGLFrameState.class.getName()).log(Level.SEVERE, null, cnfex);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public void setLocationX(final Integer locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(final Integer locationY) {
        this.locationY = locationY;
    }
    
    public Integer getLocationX() {
        return locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }
    //</editor-fold>
    
}
