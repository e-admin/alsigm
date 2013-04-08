/*
 * Created on 14-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * El interfaz IItemCollection representa un conjunto de IItems y
 * ofrece m&eacute;todos para recorrerla, convertirla a una colecci&oacute;n est&aacute;ndar de
 * J2EE o a una representaci&oacute;n como texto XML.
 */
public interface IItemCollection
{

	/**
	 * Posiciona la colecci&oacute;n en el siguiente IItem
	 * @return true si hay alg&uacute;n IItem que devolver.
	 * @throws ISPACException
	 */
	public boolean next() throws ISPACException;

	/**
	 * Devuelve el IItem actual de la colecci&oacute;n.
	 *
	 * @return el IItem actual
	 * @throws ISPACException
	 */
	public IItem value() throws ISPACException;

	/**
	 * Posiciona al principio la colecci&oacute;n para volver a recorrerla
	 */
	public void reset();

	/**
	 * Devuelve un iterador para recorrer todos los IItems de la colecci&oacute;n.
	 * @return el iterador
	 */
	public Iterator iterator();


	/**
	 * Crea y devuelve un mapa con todos los valores de las propiedades.
	 * @return el mapa de nombres de propiedades a sus valores correspondientes
	 * @throws ISPACException
	 */
	public String toXml() throws ISPACException;

	/**
	 * Crea y devuelve una lista con todos los IItems de la colecci&oacute;n.
	 * @return la lista de  IItems
	 * @throws ISPACException
	 */
	public List toList() throws ISPACException;

	/**
	 * Crea y devuelve un mapa de IItems cuyas claves son los valores de la propiedad
	 * clave del IItem
	 * @return el mapa de IItems
	 * @throws ISPACException
	 */
	public Map toMap() throws ISPACException;

	/**
	 * Crea y devuelve un mapa de IItems cuyas claves son los valores de la propiedad
	 * hashPropertyKey
	 * @param hashPropertyKey la propiedad cuyo valor ser&aacute; la clave de cada IItem
	 * @return el mapa de IItems
	 * @throws ISPACException
	 */
	public Map toMap(String hashPropertyKey) throws ISPACException;
	
	/**
	 * Crea y devuelve un mapa de IItems cuyas claves de tipo String son los valores de la propiedad
	 * hashPropertyKey 
	 * @param hashPropertyKey la propiedad cuyo valor ser&aacute; la clave de cada IItem
	 * @return el mapa de IItems donde el campo clave es el hashPropertyKey ahormado a String
	 * @throws ISPACException
	 */
	public Map toMapStringKey(String hashPropertyKey) throws ISPACException;
	
	/**
	 * Crea y devuelve un mapa de IItems cuyas claves de tipo String son los valores de la propiedad
	 * clave del IItem
	 * @return el mapa de IItems donde el campo clave es el identificador del IItem ahormado a String
	 * @throws ISPACException
	 */
	public Map toMapStringKey() throws ISPACException;
	
}