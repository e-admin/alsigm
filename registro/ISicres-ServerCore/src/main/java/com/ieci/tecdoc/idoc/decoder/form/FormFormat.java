package com.ieci.tecdoc.idoc.decoder.form;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class FormFormat {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String ver = null;
    private FDlgDef dlgDef = null;
    private FVarDefs vardefs = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FormFormat() {
        this(null);
    }

    public FormFormat(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("FormFormat [ \n\t ver[");
        buffer.append(ver);
        buffer.append("] \n\t dlgDef [");
        buffer.append(dlgDef);
        buffer.append("\n\t ] \n\t varDefs [");
        buffer.append(vardefs);
        //        buffer.append("\n\t ");
        //        buffer.append("] \n\t tbdef [ \n\t\t");
        //        if (tbdef != null) {
        //            buffer.append(tbdef);
        //        }
        //        buffer.append("\n\t relFrmFmtId [");
        //        buffer.append(relFrmFmtId);
        //        buffer.append("] \n\t eventsDef [ \n\t\t");
        //        if (eventsDef != null) {
        //            buffer.append(eventsDef.toString());
        //        }
        buffer.append("\n\t ]");

        return buffer.toString();
    }

    /**
     * @return Returns the dlgDef.
     */
    public FDlgDef getDlgDef() {
        return dlgDef;
    }

    /**
     * @param dlgDef
     *            The dlgDef to set.
     */
    public void setDlgDef(FDlgDef dlgDef) {
        this.dlgDef = dlgDef;
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

        dlgDef = new FDlgDef(tokenizer);
        vardefs = new FVarDefs(tokenizer);

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

