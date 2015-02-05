package com.ieci.tecdoc.idoc.decoder.table;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class TableFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private TDlgDef dlgDef = null;
    private TBDef tbdef = null;
    private int relFrmFmtId = 0;
    private TEventsDef eventsDef = null;

        /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public TableFormat() {
        this(null);
    }

    public TableFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("TableFormat \n\t ver[");
        buffer.append(ver);
        buffer.append("] \n\t dlgDef [");
        buffer.append(dlgDef);
        buffer.append("\n\t ");
        buffer.append("] \n\t tbdef [ \n\t\t");
        if (tbdef != null) {
            buffer.append(tbdef);
        }
        buffer.append("\n\t relFrmFmtId [");
        buffer.append(relFrmFmtId);
        buffer.append("] \n\t eventsDef [ \n\t\t");
        if (eventsDef != null) {
            buffer.append(eventsDef.toString());
        }
        buffer.append("\n\t\t]");

        return buffer.toString();
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
     * @return Returns the dlgDef.
     */
    public TDlgDef getDlgDef() {
        return dlgDef;
    }
    /**
     * @param dlgDef The dlgDef to set.
     */
    public void setDlgDef(TDlgDef dlgDef) {
        this.dlgDef = dlgDef;
    }
    /**
     * @return Returns the eventsDef.
     */
    public TEventsDef getEventsDef() {
        return eventsDef;
    }
    /**
     * @param eventsDef The eventsDef to set.
     */
    public void setEventsDef(TEventsDef eventsDef) {
        this.eventsDef = eventsDef;
    }
    /**
     * @return Returns the relFrmFmtId.
     */
    public int getRelFrmFmtId() {
        return relFrmFmtId;
    }
    /**
     * @param relFrmFmtId The relFrmFmtId to set.
     */
    public void setRelFrmFmtId(int relFrmFmtId) {
        this.relFrmFmtId = relFrmFmtId;
    }
    /**
     * @return Returns the tbdef.
     */
    public TBDef getTbdef() {
        return tbdef;
    }
    /**
     * @param tbdef The tbdef to set.
     */
    public void setTbdef(TBDef tbdef) {
        this.tbdef = tbdef;
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

        nextToken = tokenizer.nextToken();

        int size = Integer.parseInt(nextToken.substring(nextToken.lastIndexOf(',') + 1, nextToken.length()));

        StringBuffer buffer = new StringBuffer();
        buffer.append(nextToken);

        for (int i = 0; i < size; i++) {
            buffer.append("|");
            buffer.append(tokenizer.nextToken());
            if (i != size - 1) {
                buffer.append("|");
            }
        }

        dlgDef = new TDlgDef(buffer.toString());

        nextToken = tokenizer.nextToken();
        String aux1 = nextToken;
        if(aux1.indexOf(",") == -1){
        	nextToken = tokenizer.nextToken();
        }
        StringTokenizer tokenizer2 = new StringTokenizer(nextToken, ",");
        int aux = Integer.parseInt(tokenizer2.nextToken());

        if (aux != 0) {
            size = Integer.parseInt(tokenizer2.nextToken());
            buffer = new StringBuffer();
            buffer.append(size);
            for (int i = 0; i < size; i++) {
                buffer.append("|");
                buffer.append(tokenizer.nextToken());
                if (i != size - 1) {
                    buffer.append("|");
                }
            }
            tbdef = new TBDef(buffer.toString());
        }

        relFrmFmtId = Integer.parseInt(tokenizer.nextToken());

        size = Integer.parseInt(tokenizer.nextToken());
        buffer = new StringBuffer();
        buffer.append(size);
        for (int i = 0; i < size; i++) {
            buffer.append("|");
            buffer.append(tokenizer.nextToken());
            if (i != size - 1) {
                buffer.append("|");
            }
        }
        
        eventsDef = new TEventsDef(buffer.toString());
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

