package com.ieci.tecdoc.idoc.decoder.validation.idocarchdet;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class FieldFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private int numflds = 0;
    private TreeMap flddefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FieldFormat() {
        this(null);
    }

    public FieldFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("FieldFormat ver[");
        buffer.append(ver);
        buffer.append("] numflds [");
        buffer.append(numflds);
        buffer.append("] flddef [");
        Integer key = null;
        for (Iterator it = flddefs.keySet().iterator(); it.hasNext();) {
            key = (Integer) it.next();
            buffer.append("\n\t");
            buffer.append(key);
            buffer.append(" ");
            buffer.append(flddefs.get(key));
        }
        buffer.append("\n]");

        return buffer.toString();
    }

    /**
     * @return Returns the flddefs.
     */
    public TreeMap getFlddefs() {
        return flddefs;
    }

    public FFldDef getFFldDef(int fldId) {
        return (FFldDef) flddefs.get(new Integer(fldId));
    }
    
    /**
     * @param flddefs
     *            The flddefs to set.
     */
    public void setFlddefs(TreeMap flddefs) {
        this.flddefs = flddefs;
    }

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
     * @return Returns the numflds.
     */
    public int getNumflds() {
        return numflds;
    }

    /**
     * @param numflds
     *            The numflds to set.
     */
    public void setNumflds(int numflds) {
        this.numflds = numflds;
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

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "|");

        String nextToken = tokenizer.nextToken();
        ver = nextToken.substring(1, nextToken.length() - 1);

        try {
            numflds = Integer.parseInt(tokenizer.nextToken());
        } catch (Exception e) {
            // Si existe algun error es que no existe numfields
        }

        for (int i = 0; i < numflds; i++) {
        	FFldDef ffldDef=new FFldDef(tokenizer);
            flddefs.put(new Integer(ffldDef.getId()),ffldDef );
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

