/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author thw
 */
public class CommonUtils {
    
    /**
     * Get file name with extention removed.
     * @param file
     * @return null is file name not extracted or param null.
     */
    public static String removeFileExtension(final File file) {
    
        // if param is null return null.
        if (file == null) return null;
        final String fileName = file.getName();
        // Handle null case specially.
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(CommonConst.DOT);
        // If no extention was found return null.
        if (pos == -1) return fileName;

        // Otherwise return the string, up to the dot.
        return fileName.substring(0, pos);
    }
    
    /**
     * Copy file from one place to another.UNUSED 
     * @param source
     * @param destination
     * @throws IOException 
     */
    public static void copyFile(final String source, final String destination) throws IOException {
        
        final File src = new File(source);
        final File dest = new File(destination);
        
        if (!dest.exists()) {
            dest.createNewFile();
        }
        
        InputStream in = null;
        OutputStream out = null;
        
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    
}
