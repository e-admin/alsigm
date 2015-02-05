package com.ieci.tecdoc.idoc.decoder.query;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QDlgDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private String title = null;
    private int l = 0;
    private int t = 0;
    private int r = 0;
    private int b = 0;
    private String fontName = null;
    private int fontSize = 0;
    private int numCtrls = 0;
    private TreeMap ctrldefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QDlgDef() {
        this(null);
    }

    public QDlgDef(String data) {
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
     * @return Returns the b.
     */
    public int getB() {
        return b;
    }
    /**
     * @param b The b to set.
     */
    public void setB(int b) {
        this.b = b;
    }
    /**
     * @return Returns the ctrldefs.
     */
    public TreeMap getCtrldefs() {
        return ctrldefs;
    }
    /**
     * @param ctrldefs The ctrldefs to set.
     */
    public void setCtrldefs(TreeMap ctrldefs) {
        this.ctrldefs = ctrldefs;
    }
    /**
     * @return Returns the fontName.
     */
    public String getFontName() {
        return fontName;
    }
    /**
     * @param fontName The fontName to set.
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    /**
     * @return Returns the fontSize.
     */
    public int getFontSize() {
        return fontSize;
    }
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    /**
     * @return Returns the l.
     */
    public int getL() {
        return l;
    }
    /**
     * @param l The l to set.
     */
    public void setL(int l) {
        this.l = l;
    }
    /**
     * @return Returns the numCtrls.
     */
    public int getNumCtrls() {
        return numCtrls;
    }
    /**
     * @param numCtrls The numCtrls to set.
     */
    public void setNumCtrls(int numCtrls) {
        this.numCtrls = numCtrls;
    }
    /**
     * @return Returns the r.
     */
    public int getR() {
        return r;
    }
    /**
     * @param r The r to set.
     */
    public void setR(int r) {
        this.r = r;
    }
    /**
     * @return Returns the t.
     */
    public int getT() {
        return t;
    }
    /**
     * @param t The t to set.
     */
    public void setT(int t) {
        this.t = t;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t DlgDef \n\t\t\t title[");
        buffer.append(title);
        buffer.append("] l [");
        buffer.append(l);
        buffer.append("] t [");
        buffer.append(t);
        buffer.append("] r [");
        buffer.append(r);
        buffer.append("] b [");
        buffer.append(b);
        buffer.append("] fontName [");
        buffer.append(fontName);
        buffer.append("] fontSize [");
        buffer.append(fontSize);
        buffer.append("] numCtrls [");
        buffer.append(numCtrls);
        buffer.append("] \n\t\t\t ctrldefs [");

        Integer pos = null;
        for (Iterator it = ctrldefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(ctrldefs.get(pos).toString());
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
        StringTokenizer tokenizer = new StringTokenizer(sourceData, ",");

        String nextToken = null;

        nextToken = tokenizer.nextToken();
        title = nextToken.substring(1, nextToken.length() - 1);

        l = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());
        r = Integer.parseInt(tokenizer.nextToken());
        b = Integer.parseInt(tokenizer.nextToken());

        nextToken = tokenizer.nextToken();
        fontName = nextToken.substring(1, nextToken.length() - 1);

        fontSize = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
         numCtrls = Integer.parseInt(nextToken.substring(0, nextToken.lastIndexOf("|")));
        
        String end = sourceData.substring(sourceData.indexOf("|") + 1, sourceData.length());

        StringTokenizer tokenizer2 = new StringTokenizer(end, "|");

        int i = 0;
        while (tokenizer2.hasMoreTokens()) {
            ctrldefs.put(new Integer(i++), new QCtrlDef(tokenizer2.nextToken()));
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

