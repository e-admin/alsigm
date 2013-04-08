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
import ieci.tdw.ispac.ispaclib.events.DescriptionsPEvents;

import java.util.LinkedHashMap;

/**
 * @author alberto
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PEventBean extends ItemBean {

	private LinkedHashMap rulesMap;

	/**
	 * Apartir de un mapa con las reglas existentes obtiene el texto
	 * identificativo para un Id de regla concreto
	 * 
	 * @param Map
	 *            Contiene todas las reglas existentes para realizar el cruze
	 * @throws ISPACException
	 */
	public PEventBean(LinkedHashMap rulesMap) throws ISPACException {
		this.rulesMap = rulesMap;
	}

	/**
	 * Procesa el IItem que recive, Apartir de un mapa con las reglas existentes
	 * obtiene el texto identificativo para un Id de regla concreto y le asigna
	 * el nombre del evento correspondiente a el identificador del evento
	 * 
	 * @param IItem
	 *            Item a procesar
	 * @return IItem Devuelve el Item procesado
	 * @throws ISPACException
	 */
	public IItem processItem(IItem item)
		throws ISPACException
	{
		// Nombre de la regla
		IItem rule = null;
		int ruleId = item.getInt("ID_REGLA");
		if (ruleId > 0) {
			rule = (IItem) rulesMap.get(item.get("ID_REGLA"));
		}
		setProperty("NOMBRE_REGLA", (rule != null ? rule.get("NOMBRE") : "SQL"));
	    rulesMap = null;
	    
	    // Nombre del evento
	    setProperty("EVENTO_TEXT", DescriptionsPEvents.getDescripcionEvents(item.getInt("EVENTO")));
	    
		return item;
	}
}
