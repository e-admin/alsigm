package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author LMVICENTE
 * @creationDate 13-may-2004 9:25:15
 * @version
 * @since 
 */
public class AxSfQueryField implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String fldId = null;
    private String operator = null;
    private Object value = null;
    private Integer bookId = null;
    private String nexo = null; 
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public AxSfQueryField() {

    }

    /**
     * @param fldId
     * @param operator
     * @param value
     * @param operatorCtrlId
     * @param fldIdCtrlId
     */
    public AxSfQueryField(String fldId, String operator, String value) {
        super();
        this.fldId = fldId;
        this.operator = operator;
        this.value = value;
    }

    
    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toXML() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<AxSfQueryField fldId='");
        buffer.append(fldId);
        buffer.append("' operator='");
        buffer.append(operator);
          buffer.append("'>");
        buffer.append(value);
        buffer.append("</AxSfQueryField>");

        return buffer.toString();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("AxSfQueryField fldId [");
        buffer.append(fldId);
        buffer.append("] operator [");
        buffer.append(operator);
        buffer.append("] value [");
        buffer.append(value);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the fldId.
     */
    public String getFldId() {
        return fldId;
    }

    /**
     * @param fldId
     *            The fldId to set.
     */
    public void setFldId(String fldId) {
        this.fldId = fldId;
    }

    

    /**
     * @return Returns the operator.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            The operator to set.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    

    /**
     * @return Returns the value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

	/**
	 * @return Returns the bookId.
	 */
	public Integer getBookId() {
		return bookId;
	}
	/**
	 * @param bookId The bookId to set.
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getNexo() {
		return nexo;
	}

	public void setNexo(String nexo) {
		this.nexo = nexo;
	}
	
}

