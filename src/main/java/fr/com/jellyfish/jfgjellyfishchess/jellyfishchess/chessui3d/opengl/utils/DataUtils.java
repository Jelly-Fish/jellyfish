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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils;

import com.thoughtworks.xstream.XStream;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * @author Thomas.H Warner 2014
 */
public class DataUtils {

    //<editor-fold defaultstate="collapsed" desc="Private static final vars"> 
    /**
     * data directory for serializations.
     */
    private static final String DATA_BACKUP_PATH = "data/";

    /**
     * XML file extention.
     */
    private static final String XML_FILE_EXTENTION = ".xml";

    /**
     * XML file name for serializing move queues.
     */
    private static final String FILE_NAME = "moveq";
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Public static methods">
    /**
     * @param moveQueue
     */
    public static void xmlSerializeMoveQueue(final MoveQueue moveQueue) {

        moveQueue.clearAllObservers();
        
        final XStream xstream = new XStream();
        final String xml = xstream.toXML(moveQueue);
        try {
            FileUtils.writeStringToFile(
                    new File(DATA_BACKUP_PATH
                            + FILE_NAME
                            + XML_FILE_EXTENTION), xml);
        } catch (final IOException ioex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ioex);
        }

    }

    /**
     * @param path
     * @return MoveQueue
     */
    public static MoveQueue xmlDeserializeMoveQueue(final String path) {

        return null;

    }
    //</editor-fold> 

}
