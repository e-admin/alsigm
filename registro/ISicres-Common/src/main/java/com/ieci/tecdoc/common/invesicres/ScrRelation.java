package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_RELATIONS"
 *     
*/
public class ScrRelation implements Serializable {

    /** identifier field */
    private int typebook;

    /** identifier field */
    private int typerel;

    /** identifier field */
    private int relyear;

    /** identifier field */
    private int relmonth;

    /** identifier field */
    private int relday;

    /** identifier field */
    private Date reldate;

    /** identifier field */
    private int nrel;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg;

    /** persistent field */
    private com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic;

    /** full constructor */
    public ScrRelation(int typebook, int typerel, int relyear, int relmonth, int relday, Date reldate, int nrel, com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg, com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.typebook = typebook;
        this.typerel = typerel;
        this.relyear = relyear;
        this.relmonth = relmonth;
        this.relday = relday;
        this.reldate = reldate;
        this.nrel = nrel;
        this.scrOrg = scrOrg;
        this.scrOfic = scrOfic;
    }

    /** default constructor */
    public ScrRelation() {
    }

    /** 
     *                @hibernate.property
     *                 column="TYPEBOOK"
     *                 length="10"
     *             
     */
    public int getTypebook() {
        return this.typebook;
    }

    public void setTypebook(int typebook) {
        this.typebook = typebook;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPEREL"
     *                 length="10"
     *             
     */
    public int getTyperel() {
        return this.typerel;
    }

    public void setTyperel(int typerel) {
        this.typerel = typerel;
    }

    /** 
     *                @hibernate.property
     *                 column="RELYEAR"
     *                 length="10"
     *             
     */
    public int getRelyear() {
        return this.relyear;
    }

    public void setRelyear(int relyear) {
        this.relyear = relyear;
    }

    /** 
     *                @hibernate.property
     *                 column="RELMONTH"
     *                 length="10"
     *             
     */
    public int getRelmonth() {
        return this.relmonth;
    }

    public void setRelmonth(int relmonth) {
        this.relmonth = relmonth;
    }

    /** 
     *                @hibernate.property
     *                 column="RELDAY"
     *                 length="10"
     *             
     */
    public int getRelday() {
        return this.relday;
    }

    public void setRelday(int relday) {
        this.relday = relday;
    }

    /** 
     *                @hibernate.property
     *                 column="RELDATE"
     *                 length="7"
     *             
     */
    public Date getReldate() {
        return this.reldate;
    }

    public void setReldate(Date reldate) {
        this.reldate = reldate;
    }

    /** 
     *                @hibernate.property
     *                 column="NREL"
     *                 length="10"
     *             
     */
    public int getNrel() {
        return this.nrel;
    }

    public void setNrel(int nrel) {
        this.nrel = nrel;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDUNIT"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOrg getScrOrg() {
        return this.scrOrg;
    }

    public void setScrOrg(com.ieci.tecdoc.common.invesicres.ScrOrg scrOrg) {
        this.scrOrg = scrOrg;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDOFIC"         
     *         
     */
    public com.ieci.tecdoc.common.invesicres.ScrOfic getScrOfic() {
        return this.scrOfic;
    }

    public void setScrOfic(com.ieci.tecdoc.common.invesicres.ScrOfic scrOfic) {
        this.scrOfic = scrOfic;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typebook", getTypebook())
            .append("typerel", getTyperel())
            .append("relyear", getRelyear())
            .append("relmonth", getRelmonth())
            .append("relday", getRelday())
            .append("reldate", getReldate())
            .append("nrel", getNrel())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRelation) ) return false;
        ScrRelation castOther = (ScrRelation) other;
        return new EqualsBuilder()
            .append(this.getTypebook(), castOther.getTypebook())
            .append(this.getTyperel(), castOther.getTyperel())
            .append(this.getRelyear(), castOther.getRelyear())
            .append(this.getRelmonth(), castOther.getRelmonth())
            .append(this.getRelday(), castOther.getRelday())
            .append(this.getReldate(), castOther.getReldate())
            .append(this.getNrel(), castOther.getNrel())
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
            .append(getTypebook())
            .append(getTyperel())
            .append(getRelyear())
            .append(getRelmonth())
            .append(getRelday())
            .append(getReldate())
            .append(getNrel())
            .toHashCode();
    }

}
