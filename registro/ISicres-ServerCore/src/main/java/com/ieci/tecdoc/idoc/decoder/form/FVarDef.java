package com.ieci.tecdoc.idoc.decoder.form;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class FVarDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String name = null;
    private int type = 0;
    private int dataType = 0;
    private int dataLen = 0;
    private int flags = 0;
    private String defVal = null;
    private String remarks = null;
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FVarDef() {
        this(null);
    }
    
    public FVarDef(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the data.
     */
    public String getSourceData() {
        return sourceData;
    }

    /**
     * @param data
     *            The data to set.
     */
    public void setSourceData(String data) {
        if (data != null) {
            this.sourceData = data;
            analize();
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(" FVarDef name[");
        buffer.append(name);
        buffer.append("] type [");
        buffer.append(type);
        buffer.append("] datatype [");
        buffer.append(dataType);
        buffer.append("] datalen [");
        buffer.append(dataLen);
        buffer.append("] flags [");
        buffer.append(flags);
        buffer.append("] defVal [");
        buffer.append(defVal);
        buffer.append("] remarks [");
        buffer.append(remarks);
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
        StringTokenizer tokenizer = new StringTokenizer(sourceData, ",");

        String nextToken = null;
        
        nextToken = tokenizer.nextToken();
        name = nextToken.substring(1, nextToken.length()-1);
        
        type = Integer.parseInt(tokenizer.nextToken());
        dataType = Integer.parseInt(tokenizer.nextToken());
        dataLen = Integer.parseInt(tokenizer.nextToken());
        flags = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
        defVal = nextToken.substring(1, nextToken.length()-1);
        
        nextToken = tokenizer.nextToken();
        remarks = nextToken.substring(1, nextToken.length()-1);
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

