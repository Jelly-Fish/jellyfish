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

package jellyfish.utils;

import jellyfish.constants.CommonConst;

/**
 * @author Thomas.H Warner 2014
 */
public class TimerUtils {
    
    /**
     * Converts integer secondes to String time format hh:mm:ss.
     * @param ticks
     * @return String time in format hh:mm:ss.
     */
    public static String convertTicksHhMmSs(final int ticks) {

        int h = (int)(ticks / 3600);
        int m = (int)((ticks % 3600) / 60);
        int s = (int)(ticks % 60);
        
        String hstr = (String) (h < 10 ? CommonConst.ZERO_STR + h : CommonConst.EMPTY_STR + h);
        String mstr = (String) (m < 10 ? CommonConst.ZERO_STR + m : CommonConst.EMPTY_STR + m);
        String sstr = (String) (s < 10 ? CommonConst.ZERO_STR + s : CommonConst.EMPTY_STR + s);
        
        return hstr + CommonConst.DOTS + mstr + CommonConst.DOTS + sstr;
    }
    
}
