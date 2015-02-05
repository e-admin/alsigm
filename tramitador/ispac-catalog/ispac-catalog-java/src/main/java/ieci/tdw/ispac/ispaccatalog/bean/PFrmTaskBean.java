/*
 * Created on 10-oct-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaccatalog.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.LinkedHashMap;

/**
 * @author alberto
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PFrmTaskBean extends ItemBean {

    private LinkedHashMap appMap;

	/**
	 * Apartir de un mapa con las reglas existentes obtiene el texto identificativo para un Id de aplicacion concreto
	 *
	 * @param Map Contiene todas las aplicaciones existentes para realizar el cruze
	 * @throws ISPACException
	 */
    public PFrmTaskBean(LinkedHashMap appMap)
    	throws ISPACException
    {
        this.appMap = appMap;
    }

    /**
	 * Procesa el IItem que recive, Apartir de un mapa con las aplicaciones existentes obtiene el texto identificativo
	 * para un Id de aplicacion concreto
	 *
	 * @param IItem Item a procesar
	 * @return IItem Devuelve el Item procesado
	 * @throws ISPACException
	 */
	public IItem processItem(IItem item)
		throws ISPACException
	{
	    Object id = item.get("PFRMTASK:FRM_EDIT");
	    if (id!=null) setProperty("NOMBRE_APP", ((IItem)appMap.get(id)).get("NOMBRE"));
	    else setProperty("NOMBRE_APP", "Por defecto");

		return item;
	}
}
