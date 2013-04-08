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
public class TDlgDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int l = 0;
    private int t = 0;
    private int r = 0;
    private int b = 0;
    private int style = 0;
    private String fontName = null;
    private int fontSize = 0;
    private int numCols = 0;
    private TreeMap coldefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public TDlgDef() {
        this(null);
    }

    public TDlgDef(String data) {
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

        buffer.append("\n\t\t DlgDef \n\t\t\t l [");
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
        buffer.append("] numcols [");
        buffer.append(numCols);
        buffer.append("] \n\t\t\t coldefs [");

        Integer pos = null;
        for (Iterator it = coldefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(coldefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t ]");
        
        return buffer.toString();
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
     * @return Returns the coldefs.
     */
    public TreeMap getColdefs() {
        return coldefs;
    }
    /**
     * @param coldefs The coldefs to set.
     */
    public void setColdefs(TreeMap coldefs) {
        this.coldefs = coldefs;
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
     * @return Returns the numCols.
     */
    public int getNumCols() {
        return numCols;
    }
    /**
     * @param numCols The numCols to set.
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
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
     * @return Returns the style.
     */
    public int getStyle() {
        return style;
    }
    /**
     * @param style The style to set.
     */
    public void setStyle(int style) {
        this.style = style;
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

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, ",");

        String nextToken = null;

        l = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());
        r = Integer.parseInt(tokenizer.nextToken());
        b = Integer.parseInt(tokenizer.nextToken());
		style = Integer.parseInt(tokenizer.nextToken());

        nextToken = tokenizer.nextToken();
        fontName = nextToken.substring(1, nextToken.length() - 1);

        fontSize = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
         numCols = Integer.parseInt(nextToken.substring(0, nextToken.lastIndexOf("|")));
        
        String end = sourceData.substring(sourceData.indexOf("|") + 1, sourceData.length());

        StringTokenizer tokenizer2 = new StringTokenizer(end, "|");

        int i = 0;
        while (tokenizer2.hasMoreTokens()) {
            coldefs.put(new Integer(i++), new TColDef(tokenizer2.nextToken()));
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

