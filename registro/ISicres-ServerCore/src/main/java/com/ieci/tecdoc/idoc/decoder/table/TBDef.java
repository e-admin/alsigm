package com.ieci.tecdoc.idoc.decoder.table;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class TBDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int isstd = 0;
    private int numItems = 0;
    private TreeMap itemDefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public TBDef() {
        this(null);
    }

    public TBDef(String data) {
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

        buffer.append("TBDef \n\t\t\t isstd[");
        buffer.append(isstd);
        buffer.append("] numItems [");
        buffer.append(numItems);
        buffer.append("] \n\t\t\t itemdefs [");

        Integer pos = null;
        for (Iterator it = itemDefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(itemDefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t ]");

        return buffer.toString();
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "|");

        StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), ",");
        isstd = Integer.parseInt(tokenizer2.nextToken());

        if (isstd != 0) {
            numItems = Integer.parseInt(tokenizer2.nextToken());

            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                itemDefs.put(new Integer(i++), new TItemDef(tokenizer.nextToken()));
            }
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

