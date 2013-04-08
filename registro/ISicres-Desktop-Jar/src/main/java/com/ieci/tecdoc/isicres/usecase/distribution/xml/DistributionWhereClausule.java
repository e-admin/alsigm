/*
 * Created on 14-jun-2006
 *
 */
package com.ieci.tecdoc.isicres.usecase.distribution.xml;

/**
 * @author 79426599
 *
*/
public class DistributionWhereClausule {

	private String fieldName = null;
	private String searchValue = null;
	private String operator = null;
	private String conversionOperator = null;
	private String clausule = null;
	
	public DistributionWhereClausule(String fieldName, String searchValue, String operator, String conversionOperator) {
		this.fieldName = fieldName;
		this.searchValue = searchValue;
		this.operator = operator;
		this.conversionOperator = conversionOperator;
		createClausule(fieldName, searchValue, operator, conversionOperator);
	}
	private void createClausule(String fieldName, String searchValue, String operator, String conversionOperator) {
		
		if (operator == "=" || operator == "<>" || operator == ">" || operator == "<" || operator == ">="
			 || operator == "<="){
			try{
				Integer aux = new Integer(searchValue);
				clausule = fieldName + operator + searchValue;
			}catch(NumberFormatException e){
				clausule = fieldName + operator + "'" + searchValue + "'";
			}
		}
		
	}
}

	