package com.ieci.tecdoc.idoc.decoder.form;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class FPageDef {

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
    private StringTokenizer tokenizer = null;
    private int type = 0;
    private int pictId = 0;
    private String info = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FPageDef() {
        this(null);
    }

    public FPageDef(StringTokenizer tokenizer) {
        setTokenizer(tokenizer);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the data.
     */
    public StringTokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * @param data
     *            The data to set.
     */
    public void setTokenizer(StringTokenizer tokenizer) {
        if (tokenizer != null) {
            this.tokenizer = tokenizer;
            analize();
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("PageDef title[");
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
        buffer.append("] type [");
        buffer.append(type);
        buffer.append("] pictid [");
        buffer.append(pictId);
        buffer.append("] info [");
        buffer.append(info);
        buffer.append("] fontSize [");
        buffer.append(fontSize);
        buffer.append("] numCtrls [");
        buffer.append(numCtrls);
        buffer.append("] ctrldefs [");

        Integer pos = null;
        for (Iterator it = ctrldefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t\t");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(ctrldefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t\t ]");
        
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
     * @return Returns the info.
     */
    public String getInfo() {
        return info;
    }
    /**
     * @param info The info to set.
     */
    public void setInfo(String info) {
        this.info = info;
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
     * @return Returns the pictId.
     */
    public int getPictId() {
        return pictId;
    }
    /**
     * @param pictId The pictId to set.
     */
    public void setPictId(int pictId) {
        this.pictId = pictId;
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
     * @return Returns the sourceData.
     */
    public String getSourceData() {
        return sourceData;
    }
    /**
     * @param sourceData The sourceData to set.
     */
    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
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
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), ",");

        String nextToken = null;

        nextToken = tokenizer2.nextToken();
        title = nextToken.substring(1, nextToken.length() - 1);

        l = Integer.parseInt(tokenizer2.nextToken());
        t = Integer.parseInt(tokenizer2.nextToken());
        r = Integer.parseInt(tokenizer2.nextToken());
        b = Integer.parseInt(tokenizer2.nextToken());

        nextToken = tokenizer2.nextToken();
        fontName = nextToken.substring(1, nextToken.length() - 1);

        fontSize = Integer.parseInt(tokenizer2.nextToken());
        
        numCtrls = Integer.parseInt(tokenizer2.nextToken());
        
        FCtrlDef ctrlDef = null;
        for (int i=0; i<numCtrls; i++) {
            nextToken = tokenizer.nextToken();
            ctrlDef = new FCtrlDef(nextToken);
            ctrldefs.put(new Integer(i), ctrlDef);
        }
        
        tokenizer2 = new StringTokenizer(tokenizer.nextToken(), ",");
        
        type = Integer.parseInt(tokenizer2.nextToken());
        pictId = Integer.parseInt(tokenizer2.nextToken());
        
        nextToken = tokenizer2.nextToken();
        info = nextToken.substring(1, nextToken.length() - 1);

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

