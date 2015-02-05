package uk.co.mmscomputing.device.twain;

/**
 * Define how to load native libraries.
 * 
 * @author michael.lossos@softwaregoodness.com
 * 
 */
public interface TwainINativeLoadStrategy {
    /**
     * Attempt to load the specified native library
     * 
     * @param cl
     *            the corresponding Java class or null if none
     * @param libname
     *            the library name without extension, i.e. jtwain.dll would be
     *            "jtwain"
     * 
     * @return true on success, false on failure
     */
    boolean load( Class cl, String libname );
}
