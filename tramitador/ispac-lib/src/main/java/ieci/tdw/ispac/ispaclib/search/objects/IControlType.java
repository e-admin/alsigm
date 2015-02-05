/*
 * Created on Jan 5, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;


public interface IControlType extends IItem 
{
	/**
	 * Devuelve el tipo de control
	 * @return
	 */
	public String getControlType ()	throws ISPACException;	
	
	/**
	 * Establece el tipo de control
	 * @param type tipo de control
	 */	
	public void setTypeControl (String type) throws ISPACException;
}
