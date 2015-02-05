/*
 * Created on Jan 12, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;


public interface IEntity extends IItem
{
	/**
	 * @return Returns the description.
	 */
	public String getDescription()
	throws ISPACException;	
	
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description)
	throws ISPACException;
	
	/**
	 * @return Returns the bindField.
	 */
	public String getBindField()
	throws ISPACException;
	
	/**
	 * @param numExpField The numExpField to set.
	 */
	public void setBindField(String bindField)
	throws ISPACException;
	
	/**
	 * @return Returns the table.
	 */
	public String getTable()
	throws ISPACException;
	
	/**
	 * @param table The table to set.
	 */
	public void setTable(String table)
	throws ISPACException;
	
	/**
	 * Devuelve el numero de campos de la entidad
	 * @return numero de campos 
	 */
	public int getNumFields ();
	
	/**
	 * Devuelve el numero de propertyfmt de la entidad
	 * @return numero de propertyfmt
	 */
	/*
	public int getNumPropertyFmts ();
	*/
	
	/**
	 * Devuelve el campo de indice 'i'
	 * @param i indice dentro del conjunto de campos. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IField getField (int i)
	throws ISPACException;
	
	/**
	 * Devuelve el propertyfmt de indice 'i'
	 * @param i indice dentro del conjunto de propertyfmt. Comienza en 0
	 * @return la entidad i-esima
	 */
	/*
	public IPropertyFmt getPropertyFmt (int i)
	throws ISPACException;
	*/
	
	/**
	 * Establece un valor a una de los campos de la entidad
	 * @param name nombre del campo
	 * @param value valor del campo
	 */
	public void setFieldValue (String name, String value)
	throws ISPACException;
	
	/**
	 * Establece el operador q se utilizara en la query para un determinado campo
	 * @param name nombre del campo
	 * @param value operador
	 */
	public void setFieldOperator (String name, String operator)
	throws ISPACException;	
}
