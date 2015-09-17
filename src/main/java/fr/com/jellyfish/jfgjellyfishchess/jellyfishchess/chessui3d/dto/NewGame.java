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
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.ProgressObserver;

/**
 *
 * @author thw
 */
public class NewGame {

    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * UI side color.
     */
    private final String uiColor;

    /**
     * Sleep time used after reloading data.
     */
    private final long sleepMS;

    /**
     * Is restart finished ?
     */
    private boolean restarted = false;
    
    /**
     * 
     */
    private final boolean reloadingSavedGame;
    
    /**
     * Are hints enabled.
     */
    private boolean hintsEnabled;
    
    /**
     * Move queue used for reloading saved games.
     */
    private MoveQueue queue = null;
    
    /**
     * 
     */
    private ProgressObserver progressObserver;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param uiColor
     * @param sleepMS
     * @param hintsEnabled
     * @param reloadingSavedGame
     * @param progressObserver
     */
    public NewGame(final String uiColor, final long sleepMS, final boolean hintsEnabled,
            final boolean reloadingSavedGame, final ProgressObserver progressObserver) {
        this.uiColor = uiColor;
        this.sleepMS = sleepMS;
        this.hintsEnabled = hintsEnabled;
        this.reloadingSavedGame = reloadingSavedGame;
        this.progressObserver = progressObserver;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Get ui side color (black or white). If MoveQueue != null then
     * return ui side color defined in the queue.
     * @return ui side color string value.
     */
    public String fetchUiColor() {
        return this.queue == null ? this.uiColor : this.queue.getUiColor();
    }
    
    /**
     * Get engine side color (black or white). If MoveQueue != null then
     * return engine side color defined in the queue.
     * @return engine side color string value.
     */
    public String fetchEngineColor() {
        return this.queue == null ? 
                this.uiColor.equals(UI3DConst.COLOR_W_STR_VALUE) ? 
                    UI3DConst.COLOR_B_STR_VALUE : UI3DConst.COLOR_W_STR_VALUE :
                this.queue.getEngineColor();
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public ProgressObserver getProgressObserver() {
        return progressObserver;
    }

    public boolean isReloadingSavedGame() {
        return reloadingSavedGame;
    }
    
    public boolean isRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    public boolean isHintsEnabled() {
        return hintsEnabled;
    }

    public long getSleepMS() {
        return sleepMS;
    }
    
    public MoveQueue getQueue() {
        return queue;
    }

    public void setQueue(final MoveQueue queue) {
        this.queue = queue;
    }
    //</editor-fold> 

}
