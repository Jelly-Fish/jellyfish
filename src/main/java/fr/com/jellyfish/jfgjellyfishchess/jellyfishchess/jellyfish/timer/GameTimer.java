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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.timer;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.TimerObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.TimerUtils;
import javax.swing.Timer;

/**
 * Timer for classic chess game timing.
 * @author Thomas.H Warner 2014
 */
public class GameTimer {

    //<editor-fold defaultstate="collapsed" desc="Private vars">  
    /**
     * Singleton instance.
     */
    private static GameTimer instance;
    
    /**
     * Display message constante for console or label type displaying.
     */
    public static final String TIME_DISPLAY = "game time: %s\n";

    /**
     * if timer if running.
     */
    private static boolean ticking;
    
    /**
     * Collection of observers of this timer.
     */
    private static TimerObserver timeObserver;
    
    /**
     * Ticks for a game.
     */
    private static int ticks;
    
    /**
     * Timer delay.
     */
    private static final int TIMER_DELAY = 1000;
    
    /**
     * Timer.
     */
    private static javax.swing.Timer timer;
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="Constructor">  
    /**
     * Private singleton constructor.
     */
    private GameTimer() { }
    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="Methods">  
    /**
     * Tick tack...
     */
    static void tick() {
        ++ticks;
        notifyObserver();
    }
    
    /**
     * Add timer observers.
     * @param observer 
     */
    public void setTimerObserver(final TimerObserver observer) {
        timeObserver = observer;
    }
    
    /**
     * Notify observers sending time value as a hh:mm:ss String.
     */
    private static void notifyObserver() {
        if (timeObserver != null) {
            timeObserver.tick(TimerUtils.convertTicksHhMmSs(ticks));
        }
    }
    
    /**
     * Singleton accessor.
     * @return 
     */
    public static GameTimer getInstance() {
        if (instance == null) {
            instance = new GameTimer();
            init();
            return instance;
        } else {
            return instance;
        }
    }
    
    /**
     * Private initialize.
     */
    private static void init() { 
        timer = new Timer(TIMER_DELAY, new GameTimerListener(instance));
        timer.setInitialDelay(0);
    }
    
    /**
     * Initialize overload 2.
     * @param ticks
     * @param start 
     */
    public void init(final int ticks, final boolean start) {
        GameTimer.ticks = ticks;
        timer.start();
    }
    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">  
    public boolean isTicking() {
        return ticking;
    }
    
    public int getTicks() {
        return ticks;
    }

    public Timer getSwingTimer() {
        return timer;
    }
    //</editor-fold>  
    
}
