package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGPDOCS"
 *     
*/
public class ScrRegpdoc implements Serializable {

    /** identifier field */
    private int id;

    /** identifier field */
    private int idArch;

    /** identifier field */
    private int idFdr;

    /** identifier field */
    private String nameDoc;

    /** identifier field */
    private int presented;

    /** full constructor */
    public ScrRegpdoc(int id, int idArch, int idFdr, String nameDoc, int presented) {
        this.id = id;
        this.idArch = idArch;
        this.idFdr = idFdr;
        this.nameDoc = nameDoc;
        this.presented = presented;
    }

    /** default constructor */
    public ScrRegpdoc() {
    }

    /** 
     *                @hibernate.property
     *                 column="ID"
     *                 length="10"
     *             
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_ARCH"
     *                 length="10"
     *             
     */
    public int getIdArch() {
        return this.idArch;
    }

    public void setIdArch(int idArch) {
        this.idArch = idArch;
    }

    /** 
     *                @hibernate.property
     *                 column="ID_FDR"
     *                 length="10"
     *             
     */
    public int getIdFdr() {
        return this.idFdr;
    }

    public void setIdFdr(int idFdr) {
        this.idFdr = idFdr;
    }

    /** 
     *                @hibernate.property
     *                 column="NAME_DOC"
     *                 length="250"
     *             
     */
    public String getNameDoc() {
        return this.nameDoc;
    }

    public void setNameDoc(String nameDoc) {
        this.nameDoc = nameDoc;
    }

    /** 
     *                @hibernate.property
     *                 column="PRESENTED"
     *                 length="10"
     *             
     */
    public int getPresented() {
        return this.presented;
    }

    public void setPresented(int presented) {
        this.presented = presented;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("idArch", getIdArch())
            .append("idFdr", getIdFdr())
            .append("nameDoc", getNameDoc())
            .append("presented", getPresented())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegpdoc) ) return false;
        ScrRegpdoc castOther = (ScrRegpdoc) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getIdArch(), castOther.getIdArch())
            .append(this.getIdFdr(), castOther.getIdFdr())
            .append(this.getNameDoc(), castOther.getNameDoc())
            .append(this.getPresented(), castOther.getPresented())
            .isEquals();
    }

    
         
                                       
//************************************
// Incluido pos ISicres-Common Oracle 9i


public String toXML() {
       String className = getClass().getName();
       className = className.substring(className.lastIndexOf(".") + 1, className.length()).toUpperCase();
       StringBuffer buffer = new StringBuffer();
       buffer.append("<");
       buffer.append(className);
       buffer.append(">");
       try {
           java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
           java.lang.reflect.Field field = null;
           String name = null;
           int size = fields.length;
           for (int i = 0; i < size; i++) {
               field = fields[i];
               name = field.getName();
               buffer.append("<");
               buffer.append(name.toUpperCase());
               buffer.append(">");
               if (field.get(this) != null) {
                   buffer.append(field.get(this));
               }
               buffer.append("</");
               buffer.append(name.toUpperCase());
               buffer.append(">");
           }
       } catch (Exception e) {
           e.printStackTrace(System.err);
       }
       buffer.append("</");
       buffer.append(className);
       buffer.append(">");
       return buffer.toString();
}
                               
//************************************  
                                                                                                                                                                   
public int hashCode() {
      
        return new HashCodeBuilder()
            .append(getId())
            .append(getIdArch())
            .append(getIdFdr())
            .append(getNameDoc())
            .append(getPresented())
            .toHashCode();
    }

}
