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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.EqualityException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.MoveQueueObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author thw
 */
public class MoveQueue {
    
    //<editor-fold defaultstate="collapsed" desc="vars"> 
    /**
     * Move collection.
     */
    private final LinkedHashMap<String, Move> moves;
    
    /**
     * Move counter.
     */
    private Integer counter = 0;
    
    /**
     * List of move observers.
     */
    private final transient List<MoveQueueObserver> observers;
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="constructors"> 
    public MoveQueue(final MoveQueueObserver ... observers) {
        this.observers = new ArrayList<>();
        this.moves = new LinkedHashMap<>();
        this.observers.addAll(Arrays.asList(observers));
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Clear all MoveQueueObserver from List.
     */
    public void clearAllObservers() {
        this.observers.removeAll(observers);
    }
    /**
     * @param move 
     */
    public void appendToEnd(final Move move) {
        
        ++counter;
        
        moves.put(String.valueOf(counter), move);
        notifyObserver();
    }
    
    /**
     * Clearallelements from queue and reset counter.
     */
    public void clearQueue() {
       moves.clear();
       counter = 0;
       notifyObserver();
    }
    
    /**
     * @param key 
     * @param move 
     * @return  
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException  
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.EqualityException  
     */
    public boolean removeFromQueue(final String key, final Move move) throws MoveIndexOutOfBoundsException,
            EqualityException {
        
        if (this.counter < 1) {
            throw new MoveIndexOutOfBoundsException(String.format(MoveIndexOutOfBoundsException.MESSAGE_1, 
                    String.valueOf(this.counter)));
        }
        
        if (!this.moves.get(key).equals(move)) {
            throw new EqualityException(String.format(EqualityException.MESSAGE_1,
                    move.getClass().getSimpleName()));
        }
        
        final Move removed = this.moves.remove(key);
        
        --counter;
        notifyObserver();
        return removed.equals(move);
    }
    
    /**
     * notify observer of all moves.
     */
    private void notifyObserver() {
        
        StringBuilder mvs = new StringBuilder("");
        for (Move m : this.moves.values()) {
            mvs.append(m.toString());
        }
        
        for (MoveQueueObserver obs : this.observers) {
            obs.notifyMove(mvs.toString());
        }
    }
    //</editor-fold> 
        
    //<editor-fold defaultstate="collapsed" desc="gets & sets"> 
    public List<MoveQueueObserver> getObservers() {
        return observers;
    }
    
    /**
     * @return 
     */
    public LinkedHashMap<String, Move> getMoves() {
        return moves;
    }
    
    public Integer getCounter() {
        return counter;
    }
    //</editor-fold>
    
}
