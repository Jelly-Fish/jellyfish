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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci;

/**
 * UCI message for UI communication.
 * @author Thomas.H Warner 2014
 */
public class UCIMessage {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * default pawn promotion value.
     */
    private final static char DEFAULT_PROM_VAL = '%';
    
    /**
     * String message.
     */
    private String message;
    
    /**
     * String move result.
     */
    private boolean result;
    
    /**
     * Best move returned by external or internal engine.
     */
    private String bestMove;
    
    /**
     * Message level : see MessageLvlConst class.
     */
    private int messageLvl;
    
    /**
     * The position a piece is moving from.
     */
    private String positionFrom;
    
    /**
     * The position a piece is moving to.
     */
    private String positionTo;
    
    /**
     * Promotion fen value if output is a pawn promotion move.
     */
    private char promotionFenValue = UCIMessage.DEFAULT_PROM_VAL;
    
    /**
     * Is the uci ok to process ?
     */
    private boolean validUCI = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructor 1.
     * @param message
     * @param bestMove 
     */
    public UCIMessage(final String message, final String bestMove) {
        this.message = message;
        this.bestMove = bestMove;
        this.buildMoveData(bestMove);
    }
    
    /**
     * Constructor 2.
     * @param message
     * @param result
     * @param bestMove 
     */
    public UCIMessage(final String message, final boolean result, final String bestMove) {
        this.message = message;
        this.bestMove = bestMove;
        this.result = result;
        this.buildMoveData(bestMove);
    }
    
    /**
     * Constructor 3.
     * @param message
     * @param bestMove
     * @param messageLvl 
     */
    public UCIMessage(final String message, final String bestMove, final int messageLvl) {
        this.message = message;
        this.bestMove = bestMove;
        this.messageLvl = messageLvl;
        this.buildMoveData(bestMove);
    }
    
    /**
     * Constructor 4.
     * @param message
     */
    public UCIMessage(final String message) {
        this.message = message;
        this.buildMoveData(bestMove);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Build position from and to values from engine bestmove output.
     * @param bestMove 
     */
    private void buildMoveData(final String bestMove) {
        
        if (bestMove.length() == 4 || bestMove.length() == 5) {

            this.validUCI = true;
            
            this.positionFrom = (String.valueOf(bestMove.toCharArray()[0])
                    + String.valueOf(bestMove.toCharArray()[1]));
            this.positionTo = (bestMove.toCharArray()[2]) 
                    + String.valueOf(bestMove.toCharArray()[3]);
            
            if (bestMove.length() == 5) {
                // Get promotion type. Ex : a7a8q 'q' meaning Queen.
                this.promotionFenValue = bestMove.toCharArray()[4];
            }
        }
    }
    
    /**
     * @return is a pawn promotion value ?
     */
    public boolean isPawnPromotion() {
        return this.promotionFenValue != UCIMessage.DEFAULT_PROM_VAL;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getPositionFrom() {
        return positionFrom;
    }

    public String getPositionTo() {
        return positionTo;
    }

    public char getPromotionFenValue() {
        return promotionFenValue;
    }
    
    public boolean isResult() {
        return result;
    }

    public int getMessageLvl() {
        return messageLvl;
    }

    public String getMessage() {
        return message;
    }

    public String getBestMove() {
        return bestMove;
    }
    
    public boolean isValidUCI() {
        return validUCI;
    }
    //</editor-fold>

}
