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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.gamehistory;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.Console3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueueDTO;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.DataUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.TimeUtils;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author thw
 */
public class GameHistoryList extends javax.swing.JList {
    
    /**
     * Singleton instance.
     */
    private static GameHistoryList instance = null;
    
    /**
     * Console Frame.
     */
    private javax.swing.JFrame console = null;
    
    /**
     * Default list model with all the list's elements.
     */
    private final DefaultListModel model = new DefaultListModel();
    
    /**
     * constructor.
     * @param listData 
     */
    private GameHistoryList(final MoveQueueDTO[] listData) {
        for (MoveQueueDTO mqdto : listData) {
            this.model.addElement(new GameHistoryLabel(mqdto));
        }
        this.setModel(this.model);
    }
    
    /**
     * Singleton accessor.
     * @param parent
     * @return GameList instance.
     */
    public static GameHistoryList getInstance(final javax.swing.JFrame parent) {
        
        if (GameHistoryList.instance != null) {
            return GameHistoryList.instance;
        }
        
        final List<String> files = DataUtils.readFileNames(DataUtils.GAMES_PATH);
        final MoveQueueDTO[] data = new MoveQueueDTO[files.size()];
        int i = 0;
        for (String file : files) {
            data[i] = new MoveQueueDTO(DataUtils.xmlDeserializeMoveQueue(DataUtils.GAMES_PATH + file));
            ++i;
        }
        
        GameHistoryList.instance = new GameHistoryList(data);
        GameHistoryList.instance.console = parent;
        GameHistoryList.instance.init(GameHistoryList.instance);
        
        return GameHistoryList.instance;
    }
    
    /**
     * Singleton accessor.
     * @param parent
     * @return GameList instance.
     */
    public static GameHistoryList getNewInstance(final javax.swing.JFrame parent) {
        
        final List<String> files = DataUtils.readFileNames(DataUtils.GAMES_PATH);
        final MoveQueueDTO[] data = new MoveQueueDTO[files.size()];
        int i = 0;
        for (String file : files) {
            data[i] = new MoveQueueDTO(DataUtils.xmlDeserializeMoveQueue(DataUtils.GAMES_PATH + file));
            ++i;
        }
        
        GameHistoryList.instance = new GameHistoryList(data);
        GameHistoryList.instance.console = parent;
        GameHistoryList.instance.init(GameHistoryList.instance);
        
        return GameHistoryList.instance;
    }
    
    /**
     * Initialize JList subclass.
     * @param inst GameHistoryList instance 
     */
    private void init(final GameHistoryList inst) {
        
        inst.setBackground(new Color(248,248,255));
        inst.setCellRenderer(new GameHistoryListRenderer());
        
        /**
         * Add event listener for x2 clicks.
         */
        inst.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent evt) {
                
                if (evt.getClickCount() == 2) {
                    // Then double-click == true
                    final MoveQueueDTO queueDto = ((GameHistoryLabel) inst.getSelectedValue()).getMoveQueueDto();
                    new GameHistoryLoadDialog(console, queueDto, ((Console3D) console));
                }
            }
        });
        
        /**
         * Add focus listener.
         */
        inst.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                inst.clearSelection();
            }
        });
        
        /**
         * Add selection listener.
         */
        inst.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        });
        
    }
    
}
