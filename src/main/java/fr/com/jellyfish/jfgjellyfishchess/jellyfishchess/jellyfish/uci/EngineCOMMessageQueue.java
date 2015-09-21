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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.ExternalEngineObserver;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author thw
 */
public class EngineCOMMessageQueue {
    
    /**
     * Singleton instance.
     */
    private static EngineCOMMessageQueue instance;
    
    /**
     * UCI message queue.
     */
    private final LinkedList<EngineCOMMessage> messageQueue;

    /**
     * private constructor.
     */
    private EngineCOMMessageQueue() {
        messageQueue = new LinkedList<>();
    }
    
    /**
     * Append to end of list.  
     * @param message
     */
    public void appendEngineCOMMessageAsLast(final EngineCOMMessage message) {
        this.messageQueue.addLast(message);
    }
    
    /**
     * Send all messages to observers.
     * @param observers 
     */
    public void sendAllCOMMessages(final List<ExternalEngineObserver> observers) {

        if (observers.isEmpty() || this.messageQueue.isEmpty()) { return; }
        for (ExternalEngineObserver obs : observers) {
            // All observers must be ready.
            if (!obs.isObserverReady()) { return; }
        }
        
        final EngineCOMMessage m = this.messageQueue.getFirst();
        for (ExternalEngineObserver obs : observers) {
            if (!this.messageQueue.isEmpty()) {
                obs.engineResponse(m.getMessage(), m.getMsgLevel());
            } else {
                return;
            }
        }
        this.messageQueue.remove(m);
        this.sendAllCOMMessages(observers);
    }
    
    /**
     * Empty queue.
     */
    public void clearQueue() {
        this.messageQueue.clear();
    }
    
    /**
     * Singleton accesor.
     * @return UCIMessageQueue instance.
     */
    public static EngineCOMMessageQueue getInstance() {
        if (instance == null) {
            instance = new EngineCOMMessageQueue();
            return instance;
        } else {
            return instance;
        }
    }
    
}
