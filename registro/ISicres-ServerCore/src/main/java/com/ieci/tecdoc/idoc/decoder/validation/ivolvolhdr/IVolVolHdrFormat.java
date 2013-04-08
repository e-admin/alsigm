package com.ieci.tecdoc.idoc.decoder.validation.ivolvolhdr;

import java.util.StringTokenizer;

/*
 * @author LMVICENTE @creationDate 21-jul-2004 17:01:56
 * 
 * @version @since
 */
public class IVolVolHdrFormat {

    private String ver = null;

    private String loc = null;

    private String maxSize = null;

    private int flags = 0;

    private String source = null;

    public IVolVolHdrFormat(String source) {
        this.source = source;
        analize();
    }

    /**
     * @return Returns the flags.
     */
    public int getFlags() {
        return flags;
    }

    /**
     * @param flags
     *            The flags to set.
     */
    public void setFlags(int flags) {
        this.flags = flags;
    }

    /**
     * @return Returns the loc.
     */
    public String getLoc() {
        return loc;
    }

    /**
     * @param loc
     *            The loc to set.
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * @return Returns the maxSize.
     */
    public String getMaxSize() {
        return maxSize;
    }

    /**
     * @param maxSize
     *            The maxSize to set.
     */
    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * @return Returns the source.
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     *            The source to set.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return Returns the ver.
     */
    public String getVer() {
        return ver;
    }

    /**
     * @param ver
     *            The ver to set.
     */
    public void setVer(String ver) {
        this.ver = ver;
    }

    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     *  
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("IVolVolHdrFormat[");
        buffer.append("ver = ").append(ver);
        buffer.append(", loc = ").append(loc);
        buffer.append(", maxSize = ").append(maxSize);
        buffer.append(", flags = ").append(flags);
        buffer.append(", source = ").append(source);
        buffer.append("]");
        return buffer.toString();
    }

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(source, "|");

        String nextToken = tokenizer.nextToken();
        ver = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        loc = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        maxSize = nextToken.substring(1, nextToken.length() - 1);

        nextToken = tokenizer.nextToken();
        flags = Integer.parseInt(nextToken);
    }

}