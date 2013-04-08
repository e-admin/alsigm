package com.ieci.tecdoc.idoc.decoder.table;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class TItemDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int style = 0;
    private int iconSrc = 0;
    private int iconId = 0;
    private String tooltip = null;
    private int actionType = 0;
    private int actionId = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public TItemDef() {
        this(null);
    }
    
    public TItemDef(String data) {
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

        buffer.append("ItemDef style[");
        buffer.append(style);
        buffer.append("] iconSrc [");
        buffer.append(iconSrc);
        buffer.append("] iconId [");
        buffer.append(iconId);
        buffer.append("] tooltip [");
        buffer.append(tooltip);
        buffer.append("] actionType [");
        buffer.append(actionType);
        buffer.append("] actionId [");
        buffer.append(actionId);
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
        
        style = Integer.parseInt(tokenizer.nextToken());
        iconSrc = Integer.parseInt(tokenizer.nextToken());
        iconId = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
        tooltip = nextToken.substring(1, nextToken.length()-1);
        
        actionType = Integer.parseInt(tokenizer.nextToken());
        actionId = Integer.parseInt(tokenizer.nextToken());
    }
   
    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

