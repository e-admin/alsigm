package com.ieci.tecdoc.idoc.decoder.form;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 27-may-2004 11:25:55
 * @version
 * @since
 */
public class FVarDefs {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private StringTokenizer tokenizer = null;
    private int numVars = 0;
    private TreeMap varDefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FVarDefs(StringTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        analize();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t FVarDefs numVars [");
        buffer.append(numVars);
        buffer.append("] varDefs [");

        Integer pos = null;
        for (Iterator it = varDefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(varDefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t ]");
        buffer.append("\n\t\t ]");

        return buffer.toString();
    }

    /**
     * @return Returns the numVars.
     */
    public int getNumVars() {
        return numVars;
    }

    /**
     * @param numVars
     *            The numVars to set.
     */
    public void setNumVars(int numVars) {
        this.numVars = numVars;
    }

    /**
     * @return Returns the tokenizer.
     */
    public StringTokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * @param tokenizer
     *            The tokenizer to set.
     */
    public void setTokenizer(StringTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * @return Returns the treeMap.
     */
    public TreeMap getVarDefs() {
        return varDefs;
    }

    /**
     * @param treeMap
     *            The treeMap to set.
     */
    public void setVarDefs(TreeMap varDefs) {
        this.varDefs = varDefs;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        FVarDef varDef = null;
        try{
            numVars = Integer.parseInt(tokenizer.nextToken());
        }catch (Exception e){
        	numVars = 0;
        }
        for (int i = 0; i < numVars; i++) {
            varDef = new FVarDef(tokenizer.nextToken());
            varDefs.put(new Integer(i), varDef);
        }

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

