package com.ieci.tecdoc.idoc.decoder.query;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QueryFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private QDlgDef dlgDef = null;
    private String varDefs = null;
    private String vldDefs = null;
    private QSelectDef selectDef = null;
    private QInfo info = null;
    private int resultMode = 0;
    private int relFmtId = 0;
    private int webMacroId = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QueryFormat() {
        this(null);
    }

    public QueryFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("QueryFormat \n\t ver[");
        buffer.append(ver);
        buffer.append("] \n\t dlgDef [");
        buffer.append(dlgDef);
        buffer.append("\n\t ]");
        buffer.append("\n\t varDefs [");
        buffer.append(varDefs);
        buffer.append("] \n\t vldDefs [");
        buffer.append(vldDefs);
        buffer.append("] \n\t selectDef [ \n\t\t");
        buffer.append(selectDef);
        buffer.append("\n\t ]");
        buffer.append("\n\t info [ \n\t\t");
        if (info != null) {
            buffer.append(info.toString());
        }
        buffer.append("\n\t ] \n\t resultMode [");
        buffer.append(resultMode);
        buffer.append("] \n\t relFmtId [");
        buffer.append(relFmtId);
        buffer.append("] \n\t webMacroId [");
        buffer.append(webMacroId);
        buffer.append("]");
        
        
        

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
    public QDlgDef getDlgDef() {
        return dlgDef;
    }
    /**
     * @param dlgDef The dlgDef to set.
     */
    public void setDlgDef(QDlgDef dlgDef) {
        this.dlgDef = dlgDef;
    }
    /**
     * @return Returns the info.
     */
    public QInfo getInfo() {
        return info;
    }
    /**
     * @param info The info to set.
     */
    public void setInfo(QInfo info) {
        this.info = info;
    }
    /**
     * @return Returns the relFmtId.
     */
    public int getRelFmtId() {
        return relFmtId;
    }
    /**
     * @param relFmtId The relFmtId to set.
     */
    public void setRelFmtId(int relFmtId) {
        this.relFmtId = relFmtId;
    }
    /**
     * @return Returns the resultMode.
     */
    public int getResultMode() {
        return resultMode;
    }
    /**
     * @param resultMode The resultMode to set.
     */
    public void setResultMode(int resultMode) {
        this.resultMode = resultMode;
    }
    /**
     * @return Returns the selectDef.
     */
    public QSelectDef getSelectDef() {
        return selectDef;
    }
    /**
     * @param selectDef The selectDef to set.
     */
    public void setSelectDef(QSelectDef selectDef) {
        this.selectDef = selectDef;
    }
    /**
     * @return Returns the varDefs.
     */
    public String getVarDefs() {
        return varDefs;
    }
    /**
     * @param varDefs The varDefs to set.
     */
    public void setVarDefs(String varDefs) {
        this.varDefs = varDefs;
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
     * @return Returns the vldDefs.
     */
    public String getVldDefs() {
        return vldDefs;
    }
    /**
     * @param vldDefs The vldDefs to set.
     */
    public void setVldDefs(String vldDefs) {
        this.vldDefs = vldDefs;
    }
    /**
     * @return Returns the webMacroId.
     */
    public int getWebMacroId() {
        return webMacroId;
    }
    /**
     * @param webMacroId The webMacroId to set.
     */
    public void setWebMacroId(int webMacroId) {
        this.webMacroId = webMacroId;
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

        dlgDef = new QDlgDef(buffer.toString());

        varDefs = tokenizer.nextToken();
        vldDefs = tokenizer.nextToken();
        selectDef = new QSelectDef(tokenizer.nextToken());

        nextToken = tokenizer.nextToken();
        nextToken = nextToken.substring(1, nextToken.length() - 1);

        if (nextToken.length() > 0) {
            size = Integer.parseInt(nextToken);
            buffer = new StringBuffer();
            buffer.append(size);
            for (int i = 0; i < size; i++) {
                buffer.append("|");
                buffer.append(tokenizer.nextToken());
                if (i != size - 1) {
                    buffer.append("|");
                }
            }
            info = new QInfo(buffer.toString());
        }
        
        nextToken = tokenizer.nextToken();
        StringTokenizer tokenizer2 = new StringTokenizer(nextToken, ",");
        resultMode = Integer.parseInt(tokenizer2.nextToken());
        relFmtId = Integer.parseInt(tokenizer2.nextToken());
        try{
        	webMacroId = Integer.parseInt(tokenizer.nextToken());
        }catch (Exception e){
        	webMacroId = 0;
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

