package com.ieci.tecdoc.idoc.decoder.validation.idocarchdet;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class FFldDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private StringTokenizer tokenizer = null;
    private int id = 0;
    private String name = null;
    private int type = 0;
    private int len = 0;
    private int nulls = 0;
    private String colname = null;
    private int isdoc = 0;
    private int ismult = 0;
    private String remarks = null;
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public FFldDef() {
        this(null);
    }

    public FFldDef(StringTokenizer tokenizer) {
        setTokenizer(tokenizer);
        analize();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("FFldDef id [");
        buffer.append(id);
        buffer.append("] name [");
        buffer.append(name);
        buffer.append("] type [");
        buffer.append(type);
        buffer.append("] len [");
        buffer.append(len);
        buffer.append("] nulls [");
        buffer.append(nulls);
        buffer.append("] colname [");
        buffer.append(colname);
        buffer.append("] isdoc [");
        buffer.append(isdoc);
        buffer.append("] ismult [");
        buffer.append(ismult);
        buffer.append("] remarks [");
        buffer.append(remarks);
         buffer.append("]");

        return buffer.toString();
    }

    
    /**
     * @return Returns the colname.
     */
    public String getColname() {
        return colname;
    }
    /**
     * @param colname The colname to set.
     */
    public void setColname(String colname) {
        this.colname = colname;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the isdoc.
     */
    public int getIsdoc() {
        return isdoc;
    }
    /**
     * @param isdoc The isdoc to set.
     */
    public void setIsdoc(int isdoc) {
        this.isdoc = isdoc;
    }
    /**
     * @return Returns the ismult.
     */
    public int getIsmult() {
        return ismult;
    }
    /**
     * @param ismult The ismult to set.
     */
    public void setIsmult(int ismult) {
        this.ismult = ismult;
    }
    /**
     * @return Returns the len.
     */
    public int getLen() {
        return len;
    }
    /**
     * @param len The len to set.
     */
    public void setLen(int len) {
        this.len = len;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the nulls.
     */
    public int getNulls() {
        return nulls;
    }
    /**
     * @param nulls The nulls to set.
     */
    public void setNulls(int nulls) {
        this.nulls = nulls;
    }
    /**
     * @return Returns the remarks.
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return Returns the tokenizer.
     */
    public StringTokenizer getTokenizer() {
        return tokenizer;
    }
    /**
     * @param tokenizer The tokenizer to set.
     */
    public void setTokenizer(StringTokenizer tokenizer) {
        this.tokenizer = tokenizer;
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
        
        id = Integer.parseInt(tokenizer2.nextToken());

        String nextToken = tokenizer2.nextToken();
        name = nextToken.substring(1, nextToken.length() - 1);

        type = Integer.parseInt(tokenizer2.nextToken());
        len = Integer.parseInt(tokenizer2.nextToken());
        nulls = Integer.parseInt(tokenizer2.nextToken());
        
        nextToken = tokenizer2.nextToken();
        colname = nextToken.substring(1, nextToken.length() - 1);

        isdoc = Integer.parseInt(tokenizer2.nextToken());
        ismult = Integer.parseInt(tokenizer2.nextToken());
        
        nextToken = tokenizer2.nextToken();
        remarks = nextToken.substring(1, nextToken.length() - 1);

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

