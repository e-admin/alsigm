/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import ieci.tecdoc.core.types.IeciTdType;


/**
 * @author Antonio María
 *
 */
public class Field {

    private String name;
    private String typeToken;
    private String length;
    private String colName;
    private int id;
    
    private int type;
    
    boolean doc;
    boolean mult;
    String description;
    boolean obligatory;
    
    boolean isNew;
    boolean isUpdate;
    
    public boolean getIsText()
    {
        boolean isText = false;
        if (type == IeciTdType.SHORT_TEXT || type == IeciTdType.LONG_TEXT )
            isText = true;
        return isText;
    }
    public boolean getIsShortText()
    {
        return (type == IeciTdType.SHORT_TEXT);
    }
    public boolean getIsLongText(){
        return (type == IeciTdType.LONG_TEXT);
    }
    
    
    
    public boolean getIsUpdate()
    {
        return isUpdate;
    }
    public void setIsUpdate(boolean update)
    {
        this.isUpdate = update;
    }
    public boolean getObligatory()
    {
        return obligatory;
    }
    public void setObligatory(boolean obligatory)
    {
        this.obligatory = obligatory;
    }
    public boolean getIsNew()
    {
        return isNew;
    }
    public void setIsNew(boolean isNew){
        this.isNew = isNew;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the doc.
     */
    public boolean getDoc() {
        return doc;
    }
    /**
     * @param doc The doc to set.
     */
    public void setDoc(boolean doc) {
        this.doc = doc;
    }
    /**
     * @return Returns the mult.
     */
    public boolean getMult() {
        return mult;
    }
    /**
     * @param mult The mult to set.
     */
    public void setMult(boolean mult) {
        this.mult = mult;
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
    /**
     * @return Returns the colName.
     */
    public String getColName() {
        return colName;
    }
    /**
     * @param colName The colName to set.
     */
    public void setColName(String colName) {
        this.colName = colName;
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
     * @return Returns the length.
     */
    public String getLength() {
        return length;
    }
    /**
     * @param length The length to set.
     */
    public void setLength(String length) {
        this.length = length;
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
     * @return Returns the type.
     */
    public String getTypeToken() {
        return typeToken;
    }
    /**
     * @param type The type to set.
     */
    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }
}
