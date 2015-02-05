/**
 * @author MABENITO
 * 
 */

package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

public class QuerySearchAvanced implements Serializable {

	private FieldSearchAvanced[] fieldSearchAvanced;
	private int[] idOperator;
	private String[] valueWhere;
	private String[] nexo;
	private boolean[] hasInvalidValue;
	private String order;
	
	
	public QuerySearchAvanced() {
		super();
	}


	/**
	 * @return the fieldSearchAvanced
	 */
	public FieldSearchAvanced[] getFieldSearchAvanced() {
		return fieldSearchAvanced;
	}


	/**
	 * @param fieldSearchAvanced the fieldSearchAvanced to set
	 */
	public void setFieldSearchAvanced(FieldSearchAvanced[] fieldSearchAvanced) {
		this.fieldSearchAvanced = fieldSearchAvanced;
	}

	/**
	 * @return the idOperator
	 */
	public int[] getIdOperator() {
		return idOperator;
	}


	/**
	 * @param idOperator the idOperator to set
	 */
	public void setIdOperator(int[] idOperator) {
		this.idOperator = idOperator;
	}


	/**
	 * @return the valueWhere
	 */
	public String[] getValueWhere() {
		return valueWhere;
	}


	/**
	 * @param valueWhere the valueWhere to set
	 */
	public void setValueWhere(String[] valueWhere) {
		this.valueWhere = valueWhere;
	}


	/**
	 * @return the nexo
	 */
	public String[] getNexo() {
		return nexo;
	}


	/**
	 * @param nexo the nexo to set
	 */
	public void setNexo(String[] nexo) {
		this.nexo = nexo;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public boolean[] getHasInvalidValue() {
		return hasInvalidValue;
	}


	public void setHasInvalidValue(boolean[] hasInvalidValue) {
		this.hasInvalidValue = hasInvalidValue;
	}
	
}
