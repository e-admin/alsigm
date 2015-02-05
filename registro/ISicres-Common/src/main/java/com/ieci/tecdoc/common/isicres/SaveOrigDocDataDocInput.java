package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 12-jul-2004 10:33:11
 * @version
 * @since
 */
public class SaveOrigDocDataDocInput implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private Integer procId = null;
    private String procName = null;
    private transient StringTokenizer tokenizer = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public SaveOrigDocDataDocInput(StringTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        analize();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the procId.
     */
    public Integer getProcId() {
        return procId;
    }

    /**
     * @param procId
     *            The procId to set.
     */
    public void setProcId(Integer procId) {
        this.procId = procId;
    }

    /**
     * @return Returns the procName.
     */
    public String getProcName() {
        return procName;
    }

    /**
     * @param procName
     *            The procName to set.
     */
    public void setProcName(String procName) {
        this.procName = procName;
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
        buffer.append("SaveOrigDocDataDocInput[");
        buffer.append("procId = ").append(procId);
        buffer.append(", procName = ").append(procName);
        buffer.append("]");
        return buffer.toString();
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        String value = null;
        try {
            value =  tokenizer.nextToken();
            procId = new Integer(value);
            procName = tokenizer.nextToken();
        } catch (NumberFormatException n) {
            procName = value;
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

