package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_SRPTAUX"
 *     
*/
public class ScrSrptaux implements Serializable {

    /** identifier field */
    private Integer fdrid;

    /** identifier field */
    private Date timestamp;

    /** identifier field */
    private String fld1;

    /** identifier field */
    private Date fld2;

    /** identifier field */
    private String fld3;

    /** identifier field */
    private Date fld4;

    /** identifier field */
    private Integer fld5;

    /** identifier field */
    private Integer fld6;

    /** identifier field */
    private Integer fld7;

    /** identifier field */
    private Integer fld8;

    /** identifier field */
    private String fld9;

    /** identifier field */
    private String fld10;

    /** identifier field */
    private String fld11;

    /** identifier field */
    private Integer fld12;

    /** identifier field */
    private String fld13;

    /** full constructor */
    public ScrSrptaux(Integer fdrid, Date timestamp, String fld1, Date fld2, String fld3, Date fld4, Integer fld5, Integer fld6, Integer fld7, Integer fld8, String fld9, String fld10, String fld11, Integer fld12, String fld13) {
        this.fdrid = fdrid;
        this.timestamp = timestamp;
        this.fld1 = fld1;
        this.fld2 = fld2;
        this.fld3 = fld3;
        this.fld4 = fld4;
        this.fld5 = fld5;
        this.fld6 = fld6;
        this.fld7 = fld7;
        this.fld8 = fld8;
        this.fld9 = fld9;
        this.fld10 = fld10;
        this.fld11 = fld11;
        this.fld12 = fld12;
        this.fld13 = fld13;
    }

    /** default constructor */
    public ScrSrptaux() {
    }

    /** 
     *                @hibernate.property
     *                 column="FDRID"
     *                 length="10"
     *             
     */
    public Integer getFdrid() {
        return this.fdrid;
    }

    public void setFdrid(Integer fdrid) {
        this.fdrid = fdrid;
    }

    /** 
     *                @hibernate.property
     *                 column="TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD1"
     *                 length="20"
     *             
     */
    public String getFld1() {
        return this.fld1;
    }

    public void setFld1(String fld1) {
        this.fld1 = fld1;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD2"
     *                 length="7"
     *             
     */
    public Date getFld2() {
        return this.fld2;
    }

    public void setFld2(Date fld2) {
        this.fld2 = fld2;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD3"
     *                 length="32"
     *             
     */
    public String getFld3() {
        return this.fld3;
    }

    public void setFld3(String fld3) {
        this.fld3 = fld3;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD4"
     *                 length="7"
     *             
     */
    public Date getFld4() {
        return this.fld4;
    }

    public void setFld4(Date fld4) {
        this.fld4 = fld4;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD5"
     *                 length="10"
     *             
     */
    public Integer getFld5() {
        return this.fld5;
    }

    public void setFld5(Integer fld5) {
        this.fld5 = fld5;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD6"
     *                 length="10"
     *             
     */
    public Integer getFld6() {
        return this.fld6;
    }

    public void setFld6(Integer fld6) {
        this.fld6 = fld6;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD7"
     *                 length="10"
     *             
     */
    public Integer getFld7() {
        return this.fld7;
    }

    public void setFld7(Integer fld7) {
        this.fld7 = fld7;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD8"
     *                 length="10"
     *             
     */
    public Integer getFld8() {
        return this.fld8;
    }

    public void setFld8(Integer fld8) {
        this.fld8 = fld8;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD9"
     *                 length="80"
     *             
     */
    public String getFld9() {
        return this.fld9;
    }

    public void setFld9(String fld9) {
        this.fld9 = fld9;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD10"
     *                 length="31"
     *             
     */
    public String getFld10() {
        return this.fld10;
    }

    public void setFld10(String fld10) {
        this.fld10 = fld10;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD11"
     *                 length="30"
     *             
     */
    public String getFld11() {
        return this.fld11;
    }

    public void setFld11(String fld11) {
        this.fld11 = fld11;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD12"
     *                 length="10"
     *             
     */
    public Integer getFld12() {
        return this.fld12;
    }

    public void setFld12(Integer fld12) {
        this.fld12 = fld12;
    }

    /** 
     *                @hibernate.property
     *                 column="FLD13"
     *                 length="240"
     *             
     */
    public String getFld13() {
        return this.fld13;
    }

    public void setFld13(String fld13) {
        this.fld13 = fld13;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fdrid", getFdrid())
            .append("timestamp", getTimestamp())
            .append("fld1", getFld1())
            .append("fld2", getFld2())
            .append("fld3", getFld3())
            .append("fld4", getFld4())
            .append("fld5", getFld5())
            .append("fld6", getFld6())
            .append("fld7", getFld7())
            .append("fld8", getFld8())
            .append("fld9", getFld9())
            .append("fld10", getFld10())
            .append("fld11", getFld11())
            .append("fld12", getFld12())
            .append("fld13", getFld13())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrSrptaux) ) return false;
        ScrSrptaux castOther = (ScrSrptaux) other;
        return new EqualsBuilder()
            .append(this.getFdrid(), castOther.getFdrid())
            .append(this.getTimestamp(), castOther.getTimestamp())
            .append(this.getFld1(), castOther.getFld1())
            .append(this.getFld2(), castOther.getFld2())
            .append(this.getFld3(), castOther.getFld3())
            .append(this.getFld4(), castOther.getFld4())
            .append(this.getFld5(), castOther.getFld5())
            .append(this.getFld6(), castOther.getFld6())
            .append(this.getFld7(), castOther.getFld7())
            .append(this.getFld8(), castOther.getFld8())
            .append(this.getFld9(), castOther.getFld9())
            .append(this.getFld10(), castOther.getFld10())
            .append(this.getFld11(), castOther.getFld11())
            .append(this.getFld12(), castOther.getFld12())
            .append(this.getFld13(), castOther.getFld13())
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
            .append(getFdrid())
            .append(getTimestamp())
            .append(getFld1())
            .append(getFld2())
            .append(getFld3())
            .append(getFld4())
            .append(getFld5())
            .append(getFld6())
            .append(getFld7())
            .append(getFld8())
            .append(getFld9())
            .append(getFld10())
            .append(getFld11())
            .append(getFld12())
            .append(getFld13())
            .toHashCode();
    }

}
