package com.ieci.tecdoc.idoc.decoder.validation.idocarchdet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class ValidationFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private int fdrvldmacroid = 0;
    private int numreqflds = 0;
    private int numfldvlds = 0;
    private List reqfldid = new ArrayList();
    private TreeMap fldvlddefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public ValidationFormat() {
        this(null);
    }

    public ValidationFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("ValidationFormat ver[");
        buffer.append(ver);
        buffer.append("] fdrvldmacroid [");
        buffer.append(fdrvldmacroid);
        buffer.append("] numreqflds [");
        buffer.append(numreqflds);
        buffer.append("] reqfldid [");
        for (Iterator it = reqfldid.iterator(); it.hasNext();) {
            buffer.append(it.next());
        }
        buffer.append("] numfldvlds [");
        buffer.append(numfldvlds);
        buffer.append("] fldvlddefs [");
        Integer key = null;
        for (Iterator it = fldvlddefs.keySet().iterator(); it.hasNext();) {
            key = (Integer) it.next();
            buffer.append("\n\t");
            buffer.append(key);
            buffer.append(" ");
            buffer.append(fldvlddefs.get(key));
        }
        buffer.append("\n]");

        return buffer.toString();
    }

    /**
     * @return Returns the fdrvldmacroid.
     */
    public int getFdrvldmacroid() {
        return fdrvldmacroid;
    }
    /**
     * @param fdrvldmacroid The fdrvldmacroid to set.
     */
    public void setFdrvldmacroid(int fdrvldmacroid) {
        this.fdrvldmacroid = fdrvldmacroid;
    }
    /**
     * @return Returns the fldvlddefs.
     */
    public TreeMap getFldvlddefs() {
        return fldvlddefs;
    }
    /**
     * @param fldvlddefs The fldvlddefs to set.
     */
    public void setFldvlddefs(TreeMap fldvlddefs) {
        this.fldvlddefs = fldvlddefs;
    }
    /**
     * @return Returns the numfldvlds.
     */
    public int getNumfldvlds() {
        return numfldvlds;
    }
    /**
     * @param numfldvlds The numfldvlds to set.
     */
    public void setNumfldvlds(int numfldvlds) {
        this.numfldvlds = numfldvlds;
    }
    /**
     * @return Returns the numreqflds.
     */
    public int getNumreqflds() {
        return numreqflds;
    }
    /**
     * @param numreqflds The numreqflds to set.
     */
    public void setNumreqflds(int numreqflds) {
        this.numreqflds = numreqflds;
    }
    /**
     * @return Returns the reqfldid.
     */
    public List getReqfldid() {
        return reqfldid;
    }
    /**
     * @param reqfldid The reqfldid to set.
     */
    public void setReqfldid(List reqfldid) {
        this.reqfldid = reqfldid;
    }
    /**
     * @return Returns the ver.
     */
    public String getVer() {
        return ver;
    }
    /**
     * @param ver The ver to set.
     */
    public void setVer(String ver) {
        this.ver = ver;
    }
    /**
     * @return Returns the sourceData.
     */
    public String getSourceData() {
        return sourceData;
    }

    /**
     * @param sourceData
     *            The sourceData to set.
     */
    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
        analize();
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

        fdrvldmacroid = Integer.parseInt(tokenizer.nextToken());

        numreqflds = Integer.parseInt(tokenizer.nextToken());

        if (numreqflds > 0) {
            nextToken = tokenizer.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(nextToken, ",");
            while (tokenizer2.hasMoreTokens()) {
                reqfldid.add(new Integer(tokenizer2.nextToken()));
            }
        }

        numfldvlds = Integer.parseInt(tokenizer.nextToken());
        for (int i = 0; i < numfldvlds; i++) {
            fldvlddefs.put(new Integer(i), new VFldVldDef(tokenizer));
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

