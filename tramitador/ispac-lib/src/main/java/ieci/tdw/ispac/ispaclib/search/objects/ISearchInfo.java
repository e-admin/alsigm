/*
 * Created on Jan 12, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;


public interface ISearchInfo extends IItem
{
	/**
	 * Devuelve el numero de entidades que intervienen en la busqueda
	 * @return numero de entidades 
	 */	
	public int getNumEntities ();
	
	/**
	 * Devuelve la entidad de indice 'i'
	 * @param i indice dentro del conjunto de entidades. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IEntity getEntity (int i)
	throws ISPACException;	
	
	/**
	 * Devuelve la entidad con un cierto nombre. El nombre corrresponde con el nombre
	 * de la tabla
	 * @param name nombre de la entidad
	 * @return entidad
	 * @throws ISPACException
	 */
	public IEntity getEntity (String name);
	
	/**
	 * Establece el valor de un campo para una determinada entidad
	 * @param entity nombre de la entidad
	 * @param name nombre del campo
	 * @param value valor de campo
	 * @throws ISPACException
	 */
	public void setFieldValueForEntity (String entity, String name, String value)
	throws ISPACException;
	
	/**
	 * Establece el operador de un campo para una determinada entidad.
	 * Esta operador sera usado en la query de busqueda 
	 * @param entity nombre de la entidad
	 * @param name nombre del campo
	 * @param operator operador
	 * @throws ISPACException
	 */
	public void setFieldOperatorForEntity (String entity, String name, String operator)
	throws ISPACException;
	
	/**
	 * Devuelve el dominio de una busqueda
	 * @return dominio de la busqueda
	 * @throws ISPACException
	 */
	public int getDomain ()
	throws ISPACException;
	
	/**
	 * Devuelve el displaywidth
	 * @return displaywidth
	 * @throws ISPACException
	 */
	public String getDisplayWidth ()
	throws ISPACException;
	
	/**
	 * Establece el displaywidth
	 * @param displaywidth 
	 * @throws ISPACException
	 */
	public void setDisplayWidth (int displaywidth)
	throws ISPACException;
	
	/**
	 * Devuelve el numero de propertyfmt de la búsqueda
	 * @return numero de propertyfmt
	 */
	public int getNumPropertyFmts ();
	
	/**
	 * Devuelve el propertyfmt de indice 'i'
	 * @param i indice dentro del conjunto de propertyfmt. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IPropertyFmt getPropertyFmt (int i)
	throws ISPACException;
	
}
