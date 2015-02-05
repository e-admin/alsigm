package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author LMVICENTE
 * @creationDate 21-jul-2004 15:58:48
 * @version @since
 */
public class FtpFileResult extends FileResult implements Serializable {

    /***************************************************************************
     * Attributes
     **************************************************************************/

    private String host = null;

    private String user = null;

    private String password = null;

    private String workingDirectory = null;

    private String relativeDirectory = null;

    private String path = null;

    /***************************************************************************
     * Constructors
     **************************************************************************/

    public FtpFileResult() {
        super();
    }

    /***************************************************************************
     * Public methods
     **************************************************************************/

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            The host to set.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return Returns the relativeDirectory.
     */
    public String getRelativeDirectory() {
        return relativeDirectory;
    }

    /**
     * @param relativeDirectory
     *            The relativeDirectory to set.
     */
    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory = relativeDirectory;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return Returns the workingDirectory.
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @param workingDirectory
     *            The workingDirectory to set.
     */
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
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
        buffer.append("FtpFileResult[");
        buffer.append("host = ").append(host);
        buffer.append(", user = ").append(user);
        buffer.append(", password = ").append(password);
        buffer.append(", workingDirectory = ").append(workingDirectory);
        buffer.append(", logicalfilename = ").append(logicalFileName);
        buffer.append(", physicalfilename = ").append(physicalFileName);
        buffer.append(", relativedirectory = ").append(relativeDirectory);
        buffer.append(", path = ").append(path);
        buffer.append("]");
        return buffer.toString();
    }
}

