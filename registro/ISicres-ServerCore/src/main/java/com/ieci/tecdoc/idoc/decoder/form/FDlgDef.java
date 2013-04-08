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
public class FDlgDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private StringTokenizer tokenizer = null;
    private String title = null;
    private int l = 0;
    private int t = 0;
    private int r = 0;
    private int b = 0;
    private int numPages = 0;
    private TreeMap pagedefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FDlgDef() {
        this(null);
    }

    public FDlgDef(StringTokenizer  tokenizer) {
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

        buffer.append("\n\t\t DlgDef title [");
        buffer.append(title);
        buffer.append("] l [");
        buffer.append(l);
        buffer.append("] t [");
        buffer.append(t);
        buffer.append("] r [");
        buffer.append(r);
        buffer.append("] b [");
        buffer.append(b);
        buffer.append("] numpages [");
        buffer.append(numPages);
        buffer.append("] pagedefs [");

        Integer pos = null;
        for (Iterator it = pagedefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(pagedefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t ]");
        buffer.append("\n\t\t ]");

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
     * @return Returns the numPages.
     */
    public int getNumPages() {
        return numPages;
    }
    /**
     * @param numPages The numPages to set.
     */
    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
    /**
     * @return Returns the pagedefs.
     */
    public TreeMap getPagedefs() {
        return pagedefs;
    }
    /**
     * @param pagedefs The pagedefs to set.
     */
    public void setPagedefs(TreeMap pagedefs) {
        this.pagedefs = pagedefs;
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
    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), ",");
        
        String nextToken = tokenizer2.nextToken();
        title = nextToken.substring(1, nextToken.length() - 1);
        
        l = Integer.parseInt(tokenizer2.nextToken());
        t = Integer.parseInt(tokenizer2.nextToken());
        r = Integer.parseInt(tokenizer2.nextToken());
        b = Integer.parseInt(tokenizer2.nextToken());
        numPages = Integer.parseInt(tokenizer2.nextToken());

        FPageDef pageDef = null;
        for (int i = 0; i < numPages; i++) {
            pageDef = new FPageDef(tokenizer);
            pagedefs.put(new Integer(i), pageDef);
        }
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

