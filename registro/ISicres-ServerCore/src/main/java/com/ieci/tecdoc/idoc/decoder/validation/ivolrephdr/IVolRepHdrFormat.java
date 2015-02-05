package com.ieci.tecdoc.idoc.decoder.validation.ivolrephdr;

import java.util.StringTokenizer;

import com.ieci.tecdoc.common.isicres.FtpFileResult;



/*
 * @author LMVICENTE @creationDate 21-jul-2004 17:00:28
 * 
 * @version @since
 */
public class IVolRepHdrFormat {

    private String ver = null;

    private String loc = null;

    private int repType = 0;

    private int os = 0;

    private int dirFmt = 0;

    private int local = 0;

    private int flags = 0;

    private String source = null;

    public IVolRepHdrFormat(String source) {
        this.source = source;
        analize();
    }

    /**
     * @return Returns the dirFmt.
     */
    public int getDirFmt() {
        return dirFmt;
    }

    /**
     * @param dirFmt
     *            The dirFmt to set.
     */
    public void setDirFmt(int dirFmt) {
        this.dirFmt = dirFmt;
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
     * @return Returns the local.
     */
    public int getLocal() {
        return local;
    }

    /**
     * @param local
     *            The local to set.
     */
    public void setLocal(int local) {
        this.local = local;
    }

    /**
     * @return Returns the os.
     */
    public int getOs() {
        return os;
    }

    /**
     * @param os
     *            The os to set.
     */
    public void setOs(int os) {
        this.os = os;
    }

    /**
     * @return Returns the repType.
     */
    public int getRepType() {
        return repType;
    }

    /**
     * @param repType
     *            The repType to set.
     */
    public void setRepType(int repType) {
        this.repType = repType;
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

    public void loadFtp(FtpFileResult ftpFileResult) {
        StringTokenizer tokenizer = new StringTokenizer(loc, ",");

        ftpFileResult.setHost(tokenizer.nextToken());
        ftpFileResult.setUser(tokenizer.nextToken());
        ftpFileResult.setPassword(tokenizer.nextToken());
        ftpFileResult.setWorkingDirectory(tokenizer.nextToken());
    }

    public String getFileSystemSeparator() {
        if ((os == 1) || (os == 2))
            return "\\";
        else if (os == 5)
            return "/";
        else
            return "/";
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
        buffer.append("IVolRepHdrFormat[");
        buffer.append("ver = ").append(ver);
        buffer.append(", loc = ").append(loc);
        buffer.append(", repType = ").append(repType);
        buffer.append(", os = ").append(os);
        buffer.append(", dirFmt = ").append(dirFmt);
        buffer.append(", local = ").append(local);
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

        repType = Integer.parseInt(tokenizer.nextToken());
        os = Integer.parseInt(tokenizer.nextToken());
        dirFmt = Integer.parseInt(tokenizer.nextToken());
        local = Integer.parseInt(tokenizer.nextToken());
        flags = Integer.parseInt(tokenizer.nextToken());
    }

}