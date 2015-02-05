package com.ieci.tecdoc.idoc.decoder.query;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QSelectDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int type = 0;
    private String where1 = null;
    private String where2 = null;
    private String orderBy = null;
    private int macroId = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QSelectDef() {
        this(null);
    }
    
    public QSelectDef(String data) {
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


    /**
     * @return Returns the macroId.
     */
    public int getMacroId() {
        return macroId;
    }
    /**
     * @param macroId The macroId to set.
     */
    public void setMacroId(int macroId) {
        this.macroId = macroId;
    }
    /**
     * @return Returns the orderBy.
     */
    public String getOrderBy() {
        return orderBy;
    }
    /**
     * @param orderBy The orderBy to set.
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    /**
     * @return Returns the type.
     */
    public int getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * @return Returns the where1.
     */
    public String getWhere1() {
        return where1;
    }
    /**
     * @param where1 The where1 to set.
     */
    public void setWhere1(String where1) {
        this.where1 = where1;
    }
    /**
     * @return Returns the where2.
     */
    public String getWhere2() {
        return where2;
    }
    /**
     * @param where2 The where2 to set.
     */
    public void setWhere2(String where2) {
        this.where2 = where2;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SelectDef type[");
        buffer.append(type);
        buffer.append("] where1 [");
        buffer.append(where1);
        buffer.append("] where2 [");
        buffer.append(where2);
        buffer.append("] orderBy [");
        buffer.append(orderBy);
        buffer.append("] macroId [");
        buffer.append(macroId);
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
        int numTokens = tokenizer.countTokens();
        
        type = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
        where1 = nextToken.substring(1, nextToken.length()-1);
        
        nextToken = tokenizer.nextToken();
        where2 = nextToken.substring(1, nextToken.length()-1);
        
        nextToken = tokenizer.nextToken();
        if (numTokens > 5) {
            for (int i = 5; i <= numTokens-1; i++) {
                nextToken = nextToken + "," + tokenizer.nextToken();
            }
            orderBy = nextToken.substring(1, nextToken.length()-1);
        } else {
            orderBy = nextToken.substring(1, nextToken.length()-1);
        }
        
        macroId = Integer.parseInt(tokenizer.nextToken());
    }
   
    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

