package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.dom4j.io.XMLWriter;

public class IOUtils {
    /**
     * Closes an input stream.
     * 
     * @param in
     *            InputStream to be closed.
     */
    public static void close(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ioE) {
            // Ignored.
        }
    }
    
    public static void close(XMLWriter in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ioE) {
            // Ignored.
        }
    }

    /**
     * Closes an output stream.
     * 
     * @param in
     *            OutputStream to be closed.
     */
    public static void close(OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException ioE) {
            // Ignored.
        }
    }

    /**
     * Closes a reader.
     * 
     * @param reader
     *            Reader to be closed.
     */
    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioE) {
            // Ignored.
        }
    }

    /**
     * Closes a writer.
     * 
     * @param writer
     *            Writer to be closed.
     */
    public static void close(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ioE) {
            // Ignored.
        }
    }

    /**
     * Returns true if the directory passed as parameter is empty, false
     * otherwise.
     * 
     * @param path
     *            Directory to be checked.
     * @param ignoreEmpty
     *            If true, all empty subdirectories will be ignored.
     */
    public static boolean isEmpty(File path, boolean ignoreEmpty) {
        boolean empty = true;

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; ((i < files.length) && empty); i++) {
                if (files[i].isDirectory()) {
                    empty = isEmpty(files[i], ignoreEmpty);
                } else {
                    empty = false;
                }
            }
        } else {
            empty = false;
        }

        return empty;
    }

    /**
     * Deletes recursively a directory.
     * 
     * @param path
     *            Directory to be deleted.
     * @return true if the directory was deleted, false otherwise.
     */
    public static boolean deleteRecursively(File path) {
        boolean deleted = true;

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; ((i < files.length) && deleted); i++) {
                if (files[i].isDirectory()) {
                    deleted = deleteRecursively(files[i]);
                } else {
                    deleted = files[i].delete();
                }
            }
            deleted = deleted && path.delete();
        } else {
            deleted = false;
        }

        return deleted;
    }

    
    public static void copy(InputStream in, OutputStream out) throws IOException {
        try {
            int c;
            byte[] buffer = new byte[1024];
            while ((c = in.read(buffer)) != -1) {
                out.write(buffer, 0, c);
                out.flush();
            }
        } finally {
            close(in);
            close(out);
        }
    }
}