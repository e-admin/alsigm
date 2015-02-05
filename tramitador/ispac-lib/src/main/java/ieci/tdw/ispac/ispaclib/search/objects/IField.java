/*
 * Created on Jan 5, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;


public interface IField extends IItem
{
	/** Devuelve nombre de la columna
	 * @return nombre de la columna
	 */
	public String getColumName()throws ISPACException;

	/** Establece el nombre de la columna
	 * @param columname nombre de la columna
	 */
	public void setColumName(String columName) throws ISPACException;

	/**
	 * Devuelve el tipo de control
	 * @return tipo de control
	 */
	public IControlType getControlType();

	/**
	 * Devuelve el tipo de dato
	 * @return tipo de dato
	 */
	public String getDatatype() throws ISPACException;

	/**
	 * Establece tipo de dato
	 * @param datatype tipo de dato
	 */
	public void setDatatype(String datatype)throws ISPACException;

	/**
	 * Devuelve descripcion del campo
	 * @return Returns descripcion del campo
	 */
	public String getDescription() throws ISPACException;

	/**
	 * Establece descripcion del campo
	 * @param description descripcion del campo
	 */
	public void setDescription(String description) throws ISPACException;

	/**
	 * Devuelve el operador con el que se hara la query
	 * @return Returns operador
	 */
	public String getOperator()	throws ISPACException;

	/**
	 * Establece el operador con el q se hara la query
	 * @param operator operador con el q se hara la query
	 */
	public void setOperator(String operator) throws ISPACException;

	/**
	 * Devuelve el valor de campo
	 * @return valor del campo
	 */
	public Object getValue() throws ISPACException;

	/**
	 * Establece el valor del campo
	 * @param value valor del campo
	 */
	public void setValue(String value) throws ISPACException;

	/**
	 * Dice si el campo tiene valor o no
	 * @return boolean
	 */
	public boolean hasValue () throws ISPACException;
	
	/**
	 * 
	 * @return Devuelve el valor del campo binding
	 * @throws ISPACException
	 */
	public String getBinding ()throws ISPACException;
	
	/**
	 * 
	 * @param binding Establece el valor del campo binding, que se utilizará en aquellos casos donse sea necesario realizar una consulta
	 * con los valores que haya seleccionado el usuario
	 * @throws ISPACException
	 */
	public void setBinding(String binding)throws ISPACException;

}
