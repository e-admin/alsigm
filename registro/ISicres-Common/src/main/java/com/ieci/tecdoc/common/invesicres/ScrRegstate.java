package com.ieci.tecdoc.common.invesicres;

import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="SCR_REGSTATE"
 *     
*/
public class ScrRegstate implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int state;

    /** nullable persistent field */
    private Date closedate;

    /** nullable persistent field */
    private String closeuser;

    /** persistent field */
    private int numerationType;

    /** persistent field */
    private int imageAuth;

    /** persistent field */
    private Idocarchhdr idocarchhdr;

    /** full constructor */
    public ScrRegstate(Integer id, int state, Date closedate, String closeuser, int numerationType, int imageAuth, Idocarchhdr idocarchhdr) {
        this.id = id;
        this.state = state;
        this.closedate = closedate;
        this.closeuser = closeuser;
        this.numerationType = numerationType;
        this.imageAuth = imageAuth;
        this.idocarchhdr = idocarchhdr;
    }

    /** default constructor */
    public ScrRegstate() {
    }

    /** minimal constructor */
    public ScrRegstate(Integer id, int state, int numerationType, int imageAuth, Idocarchhdr idocarchhdr) {
        this.id = id;
        this.state = state;
        this.numerationType = numerationType;
        this.imageAuth = imageAuth;
        this.idocarchhdr = idocarchhdr;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="ID"
     *         
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     *            @hibernate.property
     *             column="STATE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /** 
     *            @hibernate.property
     *             column="CLOSEDATE"
     *             length="7"
     *         
     */
    public Date getClosedate() {
        return this.closedate;
    }

    public void setClosedate(Date closedate) {
        this.closedate = closedate;
    }

    /** 
     *            @hibernate.property
     *             column="CLOSEUSER"
     *             length="32"
     *         
     */
    public String getCloseuser() {
        return this.closeuser;
    }

    public void setCloseuser(String closeuser) {
        this.closeuser = closeuser;
    }

    /** 
     *            @hibernate.property
     *             column="NUMERATION_TYPE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getNumerationType() {
        return this.numerationType;
    }

    public void setNumerationType(int numerationType) {
        this.numerationType = numerationType;
    }

    /** 
     *            @hibernate.property
     *             column="IMAGE_AUTH"
     *             length="10"
     *             not-null="true"
     *         
     */
    public int getImageAuth() {
        return this.imageAuth;
    }

    public void setImageAuth(int imageAuth) {
        this.imageAuth = imageAuth;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="IDARCHREG"         
     *         
     */
    public Idocarchhdr getIdocarchhdr() {
        return this.idocarchhdr;
    }

    public void setIdocarchhdr(Idocarchhdr idocarchhdr) {
        this.idocarchhdr = idocarchhdr;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScrRegstate) ) return false;
        ScrRegstate castOther = (ScrRegstate) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
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
            .toHashCode();
    }

}
