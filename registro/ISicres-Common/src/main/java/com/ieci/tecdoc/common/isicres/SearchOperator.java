/**
 * @author MABENITO
 * 
 */

package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

public class SearchOperator implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/	
	private Integer idOperator;
	private String literal;
	private String operatorSimbol;
	

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /**
     *  
     */	
	public SearchOperator() {
		super();
	}
		
    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/	
	/**
	 * @return the idOperator
	 */
	public Integer getIdOperator() {
		return idOperator;
	}

	/**
	 * @param idOperator the idOperator to set
	 */
	public void setIdOperator(Integer idOperator) {
		this.idOperator = idOperator;
	}

	/**
	 * @return the literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * @param literal the literal to set
	 */
	public void setLiteral(String literal) {
		this.literal = literal;
	}

	/**
	 * @return the operatorSimbol
	 */
	public String getOperatorSimbol() {
		return operatorSimbol;
	}

	/**
	 * @param operatorSimbol the operatorSimbol to set
	 */
	public void setOperatorSimbol(String operatorSimbol) {
		this.operatorSimbol = operatorSimbol;
	}
	

	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SearchOperator[");
        buffer.append("id = ").append(idOperator);
        buffer.append(", literal = ").append(literal);
        buffer.append(", operatorSimbol = " ).append(operatorSimbol);
        buffer.append("]");
        return buffer.toString();
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

		
	
	
}
