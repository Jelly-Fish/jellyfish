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
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.Writable;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.DisplayableTextZone;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.timer.GameTimer;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author Thomas.H Warner 2014
 */
public class UiDisplayWriterHelper implements DisplayableTextZone {

    // <editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Console instance.
     */
    private final Writable writable;

    /**
     * Message queue to append to while texte pane is being scrolled. All
     * messages will then be appended & queue cleared.
     */
    private final LinkedList<DelayedMessage> msgQueue = new LinkedList<>();

    /**
     * ui's display area for engine output or any other information.
     */
    private final JTextPane textPane;

    /**
     * Used for text style displaying.
     */
    private final StyledDocument styleDocument;

    /**
     * Best move output style.
     */
    private final Style bestMoveStyle;

    /**
     * Trivial message output style.
     */
    private final Style trivialStyle;

    /**
     * Trivial message output style.
     */
    private final Style lessTrivialStyle;

    /**
     * ui incoming input style.
     */
    private final Style guiInputStyle;

    /**
     * ui incoming input style.
     */
    private final Style guiInputStyle2;

    /**
     * King is in check situation.
     */
    private final Style checkStyle;

    /**
     * King is in checkmate situation.
     */
    private final Style checkmateStyle;

    /**
     * Error messagestyle.
     */
    private final Style errorStyle;

    /**
     * Style for time display in console.
     */
    private final Style timerStyle;

    /**
     * Display all (also display all trivial messages), default is false.
     */
    private boolean displayAll = false;

    /**
     * Styles mapped with message level ass Integer.
     */
    private final Map<Integer, Style> styleMap = new HashMap<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     *
     * @param textArea
     * @param console
     */
    public UiDisplayWriterHelper(final JTextPane textArea, final Writable console) {

        this.textPane = textArea;
        this.writable = console;
        styleDocument = textPane.getStyledDocument();

        // FIXME : all new java.awt.Color intances must go to UI3DConst class.
        bestMoveStyle = textPane.addStyle("bestmove", null);
        StyleConstants.setForeground(bestMoveStyle, new Color(0, 139, 139));
        styleMap.put(MessageTypeConst.BEST_MOVE, bestMoveStyle);
        trivialStyle = textPane.addStyle("trivial", null);
        StyleConstants.setForeground(trivialStyle, Color.GRAY);
        styleMap.put(MessageTypeConst.TRIVIAL, trivialStyle);
        lessTrivialStyle = textPane.addStyle("less trivial", null);
        StyleConstants.setForeground(lessTrivialStyle, Color.DARK_GRAY);
        styleMap.put(MessageTypeConst.NOT_SO_TRIVIAL, lessTrivialStyle);
        guiInputStyle = textPane.addStyle("guiInput1", null);
        StyleConstants.setForeground(guiInputStyle, new Color(0, 139, 139));
        styleMap.put(MessageTypeConst.INPUT_1, guiInputStyle);
        guiInputStyle2 = textPane.addStyle("guiInput2", null);
        StyleConstants.setForeground(guiInputStyle2, new Color(60, 60, 60));
        styleMap.put(MessageTypeConst.INPUT_2, guiInputStyle2);
        checkStyle = textPane.addStyle("king in check", null);
        StyleConstants.setForeground(checkStyle, new Color(204, 30, 0));
        styleMap.put(MessageTypeConst.CHECK, checkStyle);
        checkmateStyle = textPane.addStyle("king checkmate", null);
        StyleConstants.setForeground(checkmateStyle, new Color(204, 0, 0));
        styleMap.put(MessageTypeConst.CHECKMATE, checkmateStyle);
        errorStyle = textPane.addStyle("Error message", null);
        StyleConstants.setForeground(errorStyle, new Color(255, 77, 0));
        styleMap.put(MessageTypeConst.ERROR, errorStyle);
        timerStyle = textPane.addStyle("game time", null);
        StyleConstants.setForeground(timerStyle, new Color(0, 51, 102));
        styleMap.put(MessageTypeConst.TIMER, timerStyle);

        this.appendText("game time: 00:00:00\n", MessageTypeConst.TIMER, true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Append text/information too a GUI text area.
     *
     * @param msg
     * @param msgLevel
     * @param performDisplay
     */
    @Override
    public final void appendText(final String msg, final int msgLevel, final boolean performDisplay) {

        if (this.writable.isUserReadingOutput()) {
            this.msgQueue.addLast(new DelayedMessage(msg, msgLevel, performDisplay, false));
            return;
        }

        if (this.msgQueue.size() > 0 && !this.msgQueue.getFirst().isOverride()) {
            final DelayedMessage m = this.msgQueue.getFirst();
            this.msgQueue.removeFirst();
            this.appendText(m.getMsg(), m.getMsgLevel(), m.isDisplay());
        }

        if (performDisplay) {

            try {
                if (msgLevel > 0) {
                    this.styleDocument.insertString(styleDocument.getLength(), msg,
                            styleMap.get(msgLevel));
                } else if (msgLevel == 0 && this.displayAll) {
                    this.styleDocument.insertString(styleDocument.getLength(), msg,
                            styleMap.get(MessageTypeConst.TRIVIAL));
                }
            } catch (final BadLocationException ex) {
                Logger.getLogger(UiDisplayWriterHelper.class.getName()).log(Level.WARNING, null, ex);
            }

            resetCaretPosition();
        }
    }

    /**
     * Override/replace text is it exists and if it is onlast line of text pane.
     *
     * @param msg
     * @param msgLevel
     * @param performDisplay
     */
    @Override
    public void overrideText(final String msg, final int msgLevel, final boolean performDisplay) {

        if (this.writable.isUserReadingOutput()) {
            this.msgQueue.addLast(new DelayedMessage(msg, msgLevel, performDisplay, true));
            return;
        }

        if (this.msgQueue.size() > 0 && this.msgQueue.getFirst().isOverride()) {
            final DelayedMessage m = this.msgQueue.getFirst();
            this.msgQueue.removeFirst();
            this.overrideText(m.getMsg(), m.getMsgLevel(), m.isDisplay());
        }

        if (performDisplay) {

            try {
                if (msgLevel > 0) {

                    final Element root = this.styleDocument.getDefaultRootElement();
                    int line = Math.max(root.getElementCount(), 1);
                    line = Math.min(line, root.getElementCount());

                    // line - 3 = -line jump + -\n + -1 to set caret on last text appended.
                    if (root.getElement(line - 3).getStartOffset() > 0) {
                        // if offset is not out of bounds ?
                        this.textPane.setCaretPosition(root.getElement(line - 3).getStartOffset());
                    }

                    final int caretPosition = this.textPane.getCaretPosition();
                    final Element child = root.getElement(root.getElementIndex(caretPosition) + 1);
                    if (child == null) {
                        return;
                    }
                    final int start = child.getStartOffset();
                    final int end = child.getEndOffset();
                    final int length = end - start;
                    final String text = child.getDocument().getText(start, length);

                    if (text.contains(GameTimer.TIME_DISPLAY.replaceAll("%s\n", ""))) {
                        this.styleDocument.remove(start, length);
                    }

                    // In any case, insert string message.
                    this.styleDocument.insertString(styleDocument.getLength(), msg, styleMap.get(msgLevel));

                } else if (msgLevel == 0 && this.displayAll) {
                    this.styleDocument.insertString(styleDocument.getLength(), msg,
                            styleMap.get(MessageTypeConst.TRIVIAL));
                }
            } catch (final BadLocationException blex) {
                Logger.getLogger(UiDisplayWriterHelper.class.getName()).log(Level.SEVERE, null, blex);
            } catch (final ArrayIndexOutOfBoundsException aioobe) {
                Logger.getLogger(UiDisplayWriterHelper.class.getName()).log(Level.INFO, null, aioobe);
            }

            resetCaretPosition();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private methods">
    private void resetCaretPosition() {

        if (this.textPane != null) {
            this.textPane.setCaretPosition(this.textPane.getDocument().getLength());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public Writable getWritable() {
        return writable;
    }

    public boolean isDisplayAll() {
        return displayAll;
    }

    @Override
    public void setDisplayAll(boolean displayAll) {
        this.displayAll = displayAll;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private class">
    private class DelayedMessage {

        /**
         * Message content.
         */
        private String msg;

        /**
         * Display message ? Rarely used.
         */
        private boolean display;

        /**
         * Message level for text coloration.
         */
        private int msgLevel;

        /**
         * Text to be overriden ?
         */
        private boolean override;

        /**
         * Constructor.
         *
         * @param msg
         * @param display
         * @param msgLevel
         */
        public DelayedMessage(final String msg, final int msgLevel, final boolean display,
                final boolean override) {
            this.msg = msg;
            this.display = display;
            this.msgLevel = msgLevel;
            this.override = override;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isDisplay() {
            return display;
        }

        public int getMsgLevel() {
            return msgLevel;
        }

        public boolean isOverride() {
            return override;
        }

    }
    // </editor-fold>

}
