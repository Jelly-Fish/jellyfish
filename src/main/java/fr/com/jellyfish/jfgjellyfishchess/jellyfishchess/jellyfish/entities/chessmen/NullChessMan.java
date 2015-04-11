/**
 * *****************************************************************************
 * Copyright (c) 2014, Thomas.H Warner. All rights reserved.
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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;

/**
 * A null/void chessman that represents, binded to a Position class, the value
 * of a free chess square (see also ChessSquare class). The boolean property
 * nullChessMan is always true for these objects.
 *
 * @author Thomas.H Warner 2014
 */
public class NullChessMan extends AbstractChessMan {

    /**
     * Constructor.
     *
     * @param COLOR
     * @param valuation
     * @param alive
     * @param boardPosition
     * @param nullChessMan
     * @param fenValue
     */
    public NullChessMan(final String COLOR, final float valuation, final boolean alive,
            final Position boardPosition, final boolean nullChessMan, final char fenValue) {
        super(COLOR, valuation, alive, boardPosition, nullChessMan, fenValue);
    }

    /**
     * No param contuctor.
     */
    public NullChessMan() {
        this.setNullChessMan(true);
        this.setFenValue(1);
    }

}
