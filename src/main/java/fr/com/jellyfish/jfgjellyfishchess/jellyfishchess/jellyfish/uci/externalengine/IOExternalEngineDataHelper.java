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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExternalEngineConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;

/**
 *
 * @author thw
 */
public class IOExternalEngineDataHelper {
    
    /**
     * Singleton instance.
     */
    private static IOExternalEngineDataHelper instance;
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Singleton accesor.
     * @return IOExternalEngineDataHelper instance.
     */
    public static IOExternalEngineDataHelper getInstance() {
        if (instance == null) {
            instance = new IOExternalEngineDataHelper();
            return instance;
        } else {
            return instance;
        }
    }
    
    /**
     * Get best position move from engines feedback.
     *
     * @param output
     * @return
     */
    String trimEngineMove(final String output) {

        // Trim the output to extract move in a2a4 format.
        String engineOutput = output;

        engineOutput = engineOutput.replace(CommonConst.SEARCH_BESTMOVE, CommonConst.EMPTY_STR);
        // Stockfish 6 responds simply with "bestmove xXxX" instead of systematicaly
        // bestmove xXxX ponder xXxX OR (none) as Stockfish 5 does.
        if (engineOutput.contains(CommonConst.SEARCH_PONDER)) {
            engineOutput = engineOutput.replace(engineOutput.substring(
                    engineOutput.indexOf(CommonConst.SEARCH_PONDER)), CommonConst.EMPTY_STR);
        }
        engineOutput = engineOutput.replaceAll(CommonConst.SPACE_STR, CommonConst.EMPTY_STR);
        engineOutput = engineOutput.replaceAll(CommonConst.BACKSLASH_N, CommonConst.EMPTY_STR);

        return engineOutput;

    }

    /**
     * External engine has finished depth calculation : Best move has been
     * found.
     * 
     * @param output
     * @param executingStaticInfiniteSearch
     * @return boolean
     */
    boolean isBestMoveToExecute(final String output, final boolean executingStaticInfiniteSearch) {
        return output.contains(CommonConst.SEARCH_BESTMOVE) && !executingStaticInfiniteSearch;
    }

    /**
     * Engine has found best move for a consultation and not a best move to
     * execute.
     * 
     * @param output
     * @param executingStaticInfiniteSearch
     * @return boolean
     */
    boolean isBestMoveInfiniteSearch(final String output, final boolean executingStaticInfiniteSearch) {
        return output.contains(CommonConst.SEARCH_BESTMOVE) && executingStaticInfiniteSearch;
    }

    /**
     * Here filter message that we want to display.
     *
     * @param output
     * @return
     */
    boolean isGeneralComMessage(final String output) {
        return output.toLowerCase().contains(ExternalEngineConst.STOCKFISH)
                || output.toLowerCase().contains(UCIConst.IS_READY)
                || output.toLowerCase().contains(UCIConst.UCI_OK)
                || output.toLowerCase().contains(UCIConst.READY_OK);
    }
    //</editor-fold>
    
}
