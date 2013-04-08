package com.ieci.tecdoc.common.invesicres;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="CHAINED_ROWS"
 *     
*/
public class ChainedRow implements Serializable {

    /** identifier field */
    private String ownerName;

    /** identifier field */
    private String tableName;

    /** identifier field */
    private String clusterName;

    /** identifier field */
    private String partitionName;

    /** identifier field */
    private String subpartitionName;

    /** identifier field */
    private Object headRowid;

    /** identifier field */
    private Date analyzeTimestamp;

    /** full constructor */
    public ChainedRow(String ownerName, String tableName, String clusterName, String partitionName, String subpartitionName, Object headRowid, Date analyzeTimestamp) {
        this.ownerName = ownerName;
        this.tableName = tableName;
        this.clusterName = clusterName;
        this.partitionName = partitionName;
        this.subpartitionName = subpartitionName;
        this.headRowid = headRowid;
        this.analyzeTimestamp = analyzeTimestamp;
    }

    /** default constructor */
    public ChainedRow() {
    }

    /** 
     *                @hibernate.property
     *                 column="OWNER_NAME"
     *                 length="30"
     *             
     */
    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /** 
     *                @hibernate.property
     *                 column="TABLE_NAME"
     *                 length="30"
     *             
     */
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /** 
     *                @hibernate.property
     *                 column="CLUSTER_NAME"
     *                 length="30"
     *             
     */
    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    /** 
     *                @hibernate.property
     *                 column="PARTITION_NAME"
     *                 length="30"
     *             
     */
    public String getPartitionName() {
        return this.partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    /** 
     *                @hibernate.property
     *                 column="SUBPARTITION_NAME"
     *                 length="30"
     *             
     */
    public String getSubpartitionName() {
        return this.subpartitionName;
    }

    public void setSubpartitionName(String subpartitionName) {
        this.subpartitionName = subpartitionName;
    }

    /** 
     *                @hibernate.property
     *                 column="HEAD_ROWID"
     *                 length="10"
     *             
     */
    public Object getHeadRowid() {
        return this.headRowid;
    }

    public void setHeadRowid(Object headRowid) {
        this.headRowid = headRowid;
    }

    /** 
     *                @hibernate.property
     *                 column="ANALYZE_TIMESTAMP"
     *                 length="7"
     *             
     */
    public Date getAnalyzeTimestamp() {
        return this.analyzeTimestamp;
    }

    public void setAnalyzeTimestamp(Date analyzeTimestamp) {
        this.analyzeTimestamp = analyzeTimestamp;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("ownerName", getOwnerName())
            .append("tableName", getTableName())
            .append("clusterName", getClusterName())
            .append("partitionName", getPartitionName())
            .append("subpartitionName", getSubpartitionName())
            .append("headRowid", getHeadRowid())
            .append("analyzeTimestamp", getAnalyzeTimestamp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ChainedRow) ) return false;
        ChainedRow castOther = (ChainedRow) other;
        return new EqualsBuilder()
            .append(this.getOwnerName(), castOther.getOwnerName())
            .append(this.getTableName(), castOther.getTableName())
            .append(this.getClusterName(), castOther.getClusterName())
            .append(this.getPartitionName(), castOther.getPartitionName())
            .append(this.getSubpartitionName(), castOther.getSubpartitionName())
            .append(this.getHeadRowid(), castOther.getHeadRowid())
            .append(this.getAnalyzeTimestamp(), castOther.getAnalyzeTimestamp())
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
            .append(getOwnerName())
            .append(getTableName())
            .append(getClusterName())
            .append(getPartitionName())
            .append(getSubpartitionName())
            .append(getHeadRowid())
            .append(getAnalyzeTimestamp())
            .toHashCode();
    }

}
