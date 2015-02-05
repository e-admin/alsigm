package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 12-jul-2004 10:26:26
 * @version
 * @since
 */
public class SaveOrigDocDatasInput implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private int docsNumber = 0;
    private Map docs = new TreeMap();
    private String source = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public SaveOrigDocDatasInput(String source) {
        this.source = source;
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the docs.
     */
    public Map getDocs() {
        return docs;
    }

    /**
     * @param docs
     *            The docs to set.
     */
    public void setDocs(Map docs) {
        this.docs = docs;
    }

    /**
     * @return Returns the docsNumber.
     */
    public int getDocsNumber() {
        return docsNumber;
    }

    /**
     * @param docsNumber
     *            The docsNumber to set.
     */
    public void setDocsNumber(int docsNumber) {
        this.docsNumber = docsNumber;
    }

    /**
     * @return Returns the source.
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     *            The source to set.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * toString methode: creates a String representation of the object
     * 
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin
     *  
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SaveOrigDocDatasInput[");
        buffer.append("docsNumber = ").append(docsNumber);
        buffer.append(", docs = ").append(docs);
        buffer.append(", source = ").append(source);
        buffer.append("]");
        
        return buffer.toString();
    }

    public void analize() {
        StringTokenizer tokenizer = new StringTokenizer(source, "#");

        docsNumber = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < docsNumber; i++) {
            docs.put(new Integer(i), new SaveOrigDocDataDocInput(tokenizer));
        }
    }
    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/
    
}

