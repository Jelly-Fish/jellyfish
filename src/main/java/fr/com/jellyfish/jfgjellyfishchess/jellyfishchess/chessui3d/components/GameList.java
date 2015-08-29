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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueueDTO;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.DataUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author thw
 */
public class GameList extends JList {
    
    /**
     * Singleton instance.
     */
    private static GameList instance = null;
    
    /**
     * Console Frame.
     */
    private javax.swing.JFrame console = null;
    
    /**
     * constructor.
     * @param listData 
     */
    private GameList(final Object[] listData) {
        super(listData);
    }
    
    /**
     * Singleton accessor.
     * @param parent
     * @return GameList instance.
     */
    public static GameList getInstance(final javax.swing.JFrame parent) {
        
        if (GameList.instance != null) {
            return GameList.instance;
        }
        
        final List<String> files = DataUtils.readFileNames(DataUtils.GAMES_PATH);
        final Object[] data = new Object[files.size()];
        int i = 0;
        for (String file : files) {
            data[i] = new MoveQueueDTO(DataUtils.xmlDeserializeMoveQueue(DataUtils.GAMES_PATH + file));
            ++i;
        }
        
        GameList.instance = new GameList(data);
        GameList.instance.console = parent;
        GameList.instance.init(GameList.instance);
        
        return GameList.instance;
    }
    
    /**
     * Singleton accessor.
     * @param parent
     * @return GameList instance.
     */
    public static GameList getNewInstance(final javax.swing.JFrame parent) {
        
        final List<String> files = DataUtils.readFileNames(DataUtils.GAMES_PATH);
        final Object[] data = new Object[files.size()];
        int i = 0;
        for (String file : files) {
            data[i] = new MoveQueueDTO(DataUtils.xmlDeserializeMoveQueue(DataUtils.GAMES_PATH + file));
            ++i;
        }
        
        GameList.instance = new GameList(data);
        GameList.instance.console = parent;
        GameList.instance.init(GameList.instance);
        
        return GameList.instance;
    }
    
    /**
     * Initialize JList subclass.
     * @param instance 
     */
    private void init(final GameList instance) {
        
        instance.setBackground(new Color(248,248,255));
        instance.setForeground(new Color(51,51,51));
        instance.setFont(new java.awt.Font("Meiryo", Font.PLAIN, 14));
        instance.setSelectionBackground(Color.GRAY);
        instance.setSelectionForeground(Color.ORANGE);
        
        /**
         * Add event listener for x2 clicks.
         */
        instance.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent evt) {
                
                if (evt.getClickCount() == 2) {
                    // Then double-click == true
                    final MoveQueueDTO queueDto = (MoveQueueDTO) instance.getSelectedValue();

                    // Prompt to load saved move queue ? :
                    Object[] options = new Object[]{"Reload game", "Cancel"};
                    int result = JOptionPane.showOptionDialog(console,
                            "Are you sur you want to reload the selected game ?\n" 
                            + (queueDto.getQueue().getFen() == null ? "No Fen data..." : queueDto.getQueue().getFen())
                            + "\n\nIf so, the current game will be lost...",
                            "Reload game from games history",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (result == 0) {
                        ((Console3D) console).reloadSavedGame(queueDto);
                    }                  
                }
            }
        });
    }
    
}
