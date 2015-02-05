package com.ieci.tecdoc.idoc.decoder.query;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QCtrlDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int id = 0;
    private int l = 0;
    private int t = 0;
    private int r = 0;
    private int b = 0;
    private int style = 0;
    private int classId = 0;
    private String text = null;
    private String fontName = null;
    private int fontSize = 0;
    private int fontEnh = 0;
    private int fontColor = 0;
    private int role = 0;
    private int relCtrlId = 0;
    private int fldId = 0;
    private int oprs = 0;
    private String name = null;
    private String info = null;
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QCtrlDef() {
        this(null);
    }
    
    public QCtrlDef(String data) {
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
     * @return Returns the classId.
     */
    public int getClassId() {
        return classId;
    }
    /**
     * @param classId The classId to set.
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }
    /**
     * @return Returns the fldId.
     */
    public int getFldId() {
        return fldId;
    }
    /**
     * @param fldId The fldId to set.
     */
    public void setFldId(int fldId) {
        this.fldId = fldId;
    }
    /**
     * @return Returns the fontColor.
     */
    public int getFontColor() {
        return fontColor;
    }
    /**
     * @param fontColor The fontColor to set.
     */
    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }
    /**
     * @return Returns the fontEnh.
     */
    public int getFontEnh() {
        return fontEnh;
    }
    /**
     * @param fontEnh The fontEnh to set.
     */
    public void setFontEnh(int fontEnh) {
        this.fontEnh = fontEnh;
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
     * @return Returns the oprs.
     */
    public int getOprs() {
        return oprs;
    }
    /**
     * @param oprs The oprs to set.
     */
    public void setOprs(int oprs) {
        this.oprs = oprs;
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
     * @return Returns the relCtrlId.
     */
    public int getRelCtrlId() {
        return relCtrlId;
    }
    /**
     * @param relCtrlId The relCtrlId to set.
     */
    public void setRelCtrlId(int relCtrlId) {
        this.relCtrlId = relCtrlId;
    }
    /**
     * @return Returns the role.
     */
    public int getRole() {
        return role;
    }
    /**
     * @param role The role to set.
     */
    public void setRole(int role) {
        this.role = role;
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
    /**
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }
    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(" CtrlDef id[");
        buffer.append(id);
        buffer.append("] l [");
        buffer.append(l);
        buffer.append("] t [");
        buffer.append(t);
        buffer.append("] r [");
        buffer.append(r);
        buffer.append("] b [");
        buffer.append(b);
        buffer.append("] style [");
        buffer.append(style);
        buffer.append("] classId [");
        buffer.append(classId);
        buffer.append("] text [");
        buffer.append(text);
        buffer.append("] fontName [");
        buffer.append(fontName);
        buffer.append("] fontSize [");
        buffer.append(fontSize);
        buffer.append("] fontEnh [");
        buffer.append(fontEnh);
        buffer.append("] fontColor [");
        buffer.append(fontColor);
        buffer.append("] role [");
        buffer.append(role);
        buffer.append("] relCtrlId [");
        buffer.append(relCtrlId);
        buffer.append("] fldId [");
        buffer.append(fldId);
        buffer.append("] oprs [");
        buffer.append(oprs);
        buffer.append("] name [");
        buffer.append(name);
        buffer.append("] info [");
        buffer.append(info);
        buffer.append("]");

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
        
        id = Integer.parseInt(tokenizer.nextToken());
        l = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());
        r = Integer.parseInt(tokenizer.nextToken());
        b = Integer.parseInt(tokenizer.nextToken());
        style = Integer.parseInt(tokenizer.nextToken());
        classId = Integer.parseInt(tokenizer.nextToken());
        
        text = "";
        boolean include = false;
        do {
            nextToken = tokenizer.nextToken();
            if (nextToken.startsWith("\"") && nextToken.endsWith("\"")) {
                text = text + nextToken.substring(1, nextToken.length() - 1);
            } else if (nextToken.startsWith("\"")) {
                text = text + nextToken.substring(1, nextToken.length());
                include = true;
            } else if (nextToken.endsWith("\"")) {
                text = text + nextToken.substring(0, nextToken.length()-1);
            } else {
                text = text + nextToken;
                include = true;
            }
            
            if (include) {
                text = text + ",";
                include = false;
            }
        } while (!nextToken.endsWith("\""));
        
//        nextToken = tokenizer.nextToken();
//        text = nextToken.substring(1, nextToken.length()-1);
        
        nextToken = tokenizer.nextToken();
        fontName = nextToken.substring(1, nextToken.length()-1);
        
        fontSize = Integer.parseInt(tokenizer.nextToken());
        fontEnh = Integer.parseInt(tokenizer.nextToken());
        fontColor = Integer.parseInt(tokenizer.nextToken());
        role = Integer.parseInt(tokenizer.nextToken());
        relCtrlId = Integer.parseInt(tokenizer.nextToken());
        fldId = Integer.parseInt(tokenizer.nextToken());
        oprs = Integer.parseInt(tokenizer.nextToken());
        
        nextToken = tokenizer.nextToken();
        name = nextToken.substring(1, nextToken.length()-1);
        
        nextToken = tokenizer.nextToken();
        info = nextToken.substring(1, nextToken.length()-1);
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

