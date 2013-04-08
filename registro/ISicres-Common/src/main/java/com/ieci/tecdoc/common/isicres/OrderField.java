/**
 * @author MABENITO
 * 
 */

package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

import com.ieci.tecdoc.common.conf.FieldConf;

public class OrderField implements Serializable {
	
	private FieldConf fieldConf;
	private String sense;
	
	
	
	
	public OrderField() {
		super();
	}
	/**
	 * @return the fieldConf
	 */
	public FieldConf getFieldConf() {
		return fieldConf;
	}
	/**
	 * @param fieldConf the fieldConf to set
	 */
	public void setFieldConf(FieldConf fieldConf) {
		this.fieldConf = fieldConf;
	}
	/**
	 * @return the sense
	 */
	public String getSense() {
		return sense;
	}
	/**
	 * @param sense the sense to set
	 */
	public void setSense(String sense) {
		this.sense = sense;
	}
	
}
