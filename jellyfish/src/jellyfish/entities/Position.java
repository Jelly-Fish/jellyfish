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

package jellyfish.entities;

import java.io.Serializable;
import jellyfish.entities.chessmen.AbstractChessMan;
import jellyfish.entities.chessmen.NullChessMan;

/**
 * Describes a position on a chessboard.
 * @author Thomas.H Warner 2014.
 */
public class Position implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * A1, H5 ect...
     */
    private final String coordinates;
    
    /**
     * Chessman instance on this position.
     */
    private transient AbstractChessMan onPositionChessMan;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     * Default, set's a new NullChessMan. 
     * @param coordinates 
     */
    public Position(final String coordinates) {
        this.coordinates = coordinates;
        // By default set a NullChessMan on each position.
        this.onPositionChessMan = new NullChessMan();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Overriden methods">
    @Override
    public String toString() {
        return this.coordinates;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters Setters">
    public void setOnPositionChessMan(final AbstractChessMan onPositionChessMan) {
        this.onPositionChessMan = onPositionChessMan;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public AbstractChessMan getOnPositionChessMan() {
        return onPositionChessMan;
    }
    //</editor-fold>
    
}
