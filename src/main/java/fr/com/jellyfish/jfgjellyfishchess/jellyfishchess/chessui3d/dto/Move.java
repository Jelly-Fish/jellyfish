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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.Model;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;

/**
 *
 * @author thw
 */
public class Move {
    
    /**
     * Chess piece as model on this square. Null if chess square is empty.
     */
    private final Model model;
    
    /**
     * Chess piece as model taken by this move.
     */
    private final Model takenModel;
    
    /**
     * Position from data.
     */
    private final ChessPositions posFrom;
    
    /**
     * Position to data.
     */
    private final ChessPositions posTo;
    
    /**
     * Is engine move ? else = false then ui move.
     */
    private final boolean engineMove;
    
    /**
     * Is castling move initiated by King ? 
     */
    private final boolean castlingMove;
    
    /**
     * 
     */
    private static int ID = 0;
    
    /**
     * 
     */
    private final int id;
    
    /**
     * constructor.
     * @param posFrom ChessPositions
     * @param posTo ChessPositions
     * @param engineMove
     * @param model
     */
    public Move(final ChessPositions posFrom, final ChessPositions posTo, final boolean engineMove,
            final Model model) {
        this.posFrom = posFrom;
        this.posTo = posTo;
        this.engineMove = engineMove;
        this.model = model;
        this.castlingMove = false;
        this.takenModel = null;
        this.id = ++Move.ID;
    }
    
    /**
     * constructor.
     * @param posFrom ChessPositions
     * @param posTo ChessPositions
     * @param engineMove
     * @param model
     * @param castlingMove
     */
    public Move(final ChessPositions posFrom, final ChessPositions posTo, final boolean engineMove,
            final Model model, final boolean castlingMove) {
        this.posFrom = posFrom;
        this.posTo = posTo;
        this.engineMove = engineMove;
        this.model = model;
        this.castlingMove = castlingMove;
        this.takenModel = null;
        this.id = ++Move.ID;
    }
    
    /**
     * constructor.
     * @param posFrom
     * @param posTo
     * @param engineMove
     * @param model
     * @param takenModel 
     */
    public Move(final ChessPositions posFrom, final ChessPositions posTo, final boolean engineMove,
            final Model model, final Model takenModel) {
        this.posFrom = posFrom;
        this.posTo = posTo;
        this.engineMove = engineMove;
        this.model = model;
        this.castlingMove = false;
        this.takenModel = takenModel;
        this.id = ++Move.ID;
    }
    
    public boolean isTakeMove() {
        return this.takenModel != null;
    }
    
    public boolean isEngineMove() {
        return engineMove;
    }
    
    public ChessPositions getPosFrom() {
        return posFrom;
    }

    public ChessPositions getPosTo() {
        return posTo;
    }
    
    public Model getModel() {
        return model;
    }
    
    public boolean isCastlingMove() {
        return castlingMove;
    }
    
    public Model getTakenModel() {
        return takenModel;
    }
    
}
