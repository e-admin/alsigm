package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

public class ReportResult implements Serializable {

    private String reportQuery = null;
    private String tableName = null;
    private String oldTableName = null;
    private String userOfic = null;
    private int idOfic = 0;
    private int typeBook = 0;
    private int size = 0;
    //indica la ordenacion
    private String orderBy = null;

    public ReportResult() {

    }

    public void setTableName(String tableName) {
        reportQuery = "SELECT * FROM " + tableName;
        this.tableName = tableName;
    }

    public String getTableName() {
    	return tableName;
    }

    /**
     * @return Returns the reportQuery.
     */
    public String getReportQuery() {
        return reportQuery;
    }

    /**
     * @param reportQuery
     *            The reportQuery to set.
     */
    public void setReportQuery(String reportQuery) {
        this.reportQuery = reportQuery;
    }

    /**
     * @return Returns the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     *            The size to set.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return Returns the oldTableName.
     */
    public String getOldTableName() {
        return oldTableName;
    }

    /**
     * @param oldTableName The oldTableName to set.
     */
    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    /**
     * @param userOfic The userOfic to set.
     */
    public void setUserOfic(String userOfic) {
        this.userOfic = userOfic;
    }
    /**
     * @return Returns the userOfic.
     */
    public String getUserOfic() {
        return userOfic;
    }

    /**
     * @param idOfic The idOfic to set.
     */
    public void setIdOfic(int idOfic) {
        this.idOfic = idOfic;
    }
    /**
     * @return Returns the idOfic.
     */
    public int getIdOfic() {
        return idOfic;
    }

    /**
     * @param typeBook The typeBook to set.
     */
    public void setTypeBook(int typeBook) {
        this.typeBook = typeBook;
    }
    /**
     * @return Returns the typeBook.
     */
    public int getTypeBook() {
        return typeBook;
    }
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ReportResult[");
        buffer.append("reportQuery = ").append(reportQuery);
        buffer.append(", orderBy = ").append(orderBy);
        buffer.append(", oldTableName = ").append(oldTableName);
        buffer.append(", size = ").append(size);
        buffer.append("]");
        return buffer.toString();
    }

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		if(this.orderBy == null){
			this.orderBy = "";
		}
		return this.orderBy;
	}

	public String getReportQueryWithOrderBy(){
		return this.reportQuery + getOrderBy();
	}
}