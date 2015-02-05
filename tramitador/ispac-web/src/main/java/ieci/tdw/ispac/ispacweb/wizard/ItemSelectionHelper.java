package ieci.tdw.ispac.ispacweb.wizard;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Utilidad para gestionar selecciones de objetos.
 * 
 */
public class ItemSelectionHelper {
	
	public static final String ISPAC_ATTR_ENTITY_CURRENTITEMID = 
		"ISPAC_ATTR_ENTITY_CURRENTITEMID";

	public static final String ISPAC_ATTR_ENTITY = "ISPAC_ATTR_ENTITY";

	private HttpSession session = null;

	/**
	 * Constructor.
	 * @param session Sesión HTTP.
	 */
	public ItemSelectionHelper(HttpSession session) {
		this.session = session;
	}

	/**
	 * Elimina la información de sesión.
	 */
	public void clear() {
		clear(this.session);
	}

	/**
	 * Elimina la información de sesión.
	 * @param session Información de la sesión HTTP.
	 */
	public static void clear(HttpSession session) {
		Enumeration attNames = session.getAttributeNames();
		while (attNames.hasMoreElements()) {
			String attName = (String) attNames.nextElement();
			if (attName.startsWith(ISPAC_ATTR_ENTITY)
					|| attName.startsWith(ISPAC_ATTR_ENTITY_CURRENTITEMID)) {
				session.removeAttribute(attName);
			}
		}
	}

	/**
	 * Elimina la información de sesión de una entidad.
	 * @param entity Identificador de la entidad.
	 */
	public void clearItemIdList(int entity) {
		clearItemIdList(entity, null);
	}

	/**
	 * Elimina la información de sesión de una entidad.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del objeto.
	 */
	public void clearItemIdList(int entity, String itemId) {
		String nomlist = buildAttributeName(entity, itemId);
		session.removeAttribute(nomlist);
	}

	/**
	 * Obtiene el identificador del objeto actual de la entidad.
	 * @param entity Identificador de la entidad.
	 * @return Identificador del objeto.
	 * @throws ISPACException si ocurre algún error.
	 */
    public String getCurrentEntityItemId(int entity) throws ISPACException {
		String attrCurrentEntityItemId = ISPAC_ATTR_ENTITY_CURRENTITEMID
				+ entity;

		Object itemid = session.getAttribute(attrCurrentEntityItemId);

		return (itemid != null ? itemid.toString() : null);
	}

	/**
	 * Establece el identificador del objeto actual de la entidad.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del objeto.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void setCurrentEntityItemId(int entity, String itemId)
			throws ISPACException {
		String attrCurrentEntityItemId = ISPAC_ATTR_ENTITY_CURRENTITEMID
				+ entity;
		session.setAttribute(attrCurrentEntityItemId, itemId);
	}

	/**
	 * Obtiene el objeto a partir de su identificador.
	 * @param entity Identificador de la entidad.
	 * @param id Identificador del objeto
	 * @return Objeto.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ItemBean findItemBean(int entity, int id) throws ISPACException {
		ItemBean itemBean = null;
		List itemBeans = getItemBeanList(entity);
		if (itemBeans != null)  {
			for (int i = 0; (itemBean == null) && (i < itemBeans.size()); i++) {
				ItemBean aux = (ItemBean) itemBeans.get(i);
				if (new Integer(id).equals(aux.getProperty("ID"))) {
					itemBean = aux;
				}
			}
		}
		return itemBean;
	}

	/**
	 * Obtiene la lista de objetos seleccionados de una entidad.
	 * 
	 * @param entity
	 *            Identificador de la entidad.
	 * @return Lista de ItemBeans.
	 */
	public List getItemBeanList(int entity) {
		return getItemBeanList(entity, null);
	}

	/**
	 * Obtiene la lista de objetos seleccionados de una entidad.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del objeto.
	 * @return Lista de ItemBeans.
	 */
	public List getItemBeanList(int entity, String itemId) {
		String nomlist = buildAttributeName(entity, itemId);

		List itemBeanList = (List) session.getAttribute(nomlist);
		if (itemBeanList == null) { 
			itemBeanList = new ArrayList();
			session.setAttribute(nomlist, itemBeanList);
		}

		return itemBeanList;
	}

	/**
	 * Obtiene la lista de identificadores de los objetos de una entidad.
	 * @param entity Identificador de la entidad.
	 * @return Lista de identificadores.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getItemBeanIdList(int entity) throws ISPACException {
		return getItemBeanIdList(entity, null);
	}

	/**
	 * Obtiene la lista de identificadores de los objetos de una entidad.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del objeto.
	 * @return Lista de identificadores.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getItemBeanIdList(int entity, String itemId) 
			throws ISPACException {
        List ids = new ArrayList();

        List beans = getItemBeanList(entity, itemId);
        for (int i = 0; i < beans.size(); i++) {
        	Integer id = new Integer((String)((ItemBean) beans.get(i)).getProperty("AUX_ID"));
        	if (id != null) {
        		ids.add(id);
        	}
        }

        return ids;
	}

	/**
	 * Añade un ItemBean a la lista.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del item.
	 * @param name Nombre del objeto seleccionado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void addItemBean(int entity, String itemId, String name) 
			throws ISPACException {
		addItemBean(entity, itemId, name, null);
	}

	/**
	 * Añade un ItemBean a la lista.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del item.
	 * @param name Nombre del objeto seleccionado.
	 * @param auxId Identificador auxiliar del objeto seleccionado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void addItemBean(int entity, String itemId, String name, String auxId) 
			throws ISPACException {
		List itemBeanList = getItemBeanList(entity, itemId);
		if (!containsItemBean(itemBeanList, name)) {
			// Evitar ids repetidos
			int id = itemBeanList.size() + 1;
			while (indexOf(itemBeanList, id) != -1) {
				id++;
			}
			itemBeanList.add(createItemBean(id, name, auxId));
		}
	}

	/**
	 * Indica si la lista contiene el objeto.
	 * @param itemBeanList Lista de objetos.
	 * @param name Nombre del objeto.
	 * @return true si la lista contiene el objeto.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean containsItemBean(List itemBeanList, String name) 
			throws ISPACException {
		
		boolean contained = false;

		for (int i = 0; !contained && (i < itemBeanList.size()); i++) {
			ItemBean aux = (ItemBean) itemBeanList.get(i);
			
			if (StringUtils.equalsNullEmpty(name, 
					(String)aux.getProperty("NOMBRE"))) {
				contained = true;
			}
		}
		
		return contained;
	}


	/**
	 * Elimina un ItemBean de la lista.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del item.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void removeItemBean(int entity, int itemId) throws ISPACException {
		Iterator it = getItemBeanList(entity).iterator();
		while (it.hasNext()) {
			ItemBean itemBean = (ItemBean) it.next();
			if (itemBean.getProperty("ID").equals(new Integer(itemId))) {
				it.remove();
			}
		}
	}

	/**
	 * Cambia el orden de un ItemBean en la lista.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del item.
	 * @param modOrder Modo de movimiento: INC o DEC.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void moveItemBean(int entity, int itemId, int prevItemId, String modOrder) 
			throws ISPACException {
		List itemBeanList = getItemBeanList(entity);
		
		// Obtener la posición del itemBean en la lista
		int index = indexOf(itemBeanList, itemId);
		// Obtener la posición del elemento previo que se ha movido
		int prevIndex = -1;
		if (prevItemId > 0) {
			prevIndex = indexOf(itemBeanList, prevItemId);
		}
		if (index >= 0) {
			if ("INC".equalsIgnoreCase(modOrder) 
			   && (index > 0)
			   && (index - 1 != prevIndex)) {
				ItemBean itemBean = (ItemBean) itemBeanList.remove(index);
				itemBeanList.add(index - 1, itemBean);
			}
			else if ("DEC".equalsIgnoreCase(modOrder)
					&& (index < itemBeanList.size() - 1)
					&& (index + 1 != prevIndex)) {
				ItemBean itemBean = (ItemBean) itemBeanList.remove(index);
				itemBeanList.add(index + 1, itemBean);
			}
		}
	}
	

	/**
	 * Crea un ItemBean.
	 * @param id Identificador del item.
	 * @param name Nombre del item.
	 * @param auxId Identificador auxiliar del item.
	 * @throws ISPACException si ocurre algún error.
	 */
	private ItemBean createItemBean(int id, String name, String auxId) 
			throws ISPACException {
		ItemBean bean = new ItemBean();
		bean.setProperty("ID", new Integer(id));
		bean.setProperty("NOMBRE", name);
		bean.setProperty("AUX_ID", auxId);
		bean.setProperty("ADD_ID", new Integer(id) + "-" + auxId);
		return bean;
	}
	
	/**
	 * Compone el nombre del atributo de sesión que almacena la información de 
	 * la entidad.
	 * @param entity Identificador de la entidad.
	 * @param itemId Identificador del objeto.
	 * @return Nombre del atributo de sesión.
	 */
	private String buildAttributeName(int entity, String itemId) {
		String attrCurrentEntityItemId = ISPAC_ATTR_ENTITY_CURRENTITEMID
				+ entity;

        if (itemId != null) {
            session.setAttribute(attrCurrentEntityItemId, itemId);
        }

		Object objid = session.getAttribute(attrCurrentEntityItemId);

		if (objid == null) {
			return ISPAC_ATTR_ENTITY + entity + "#";
		}

		return ISPAC_ATTR_ENTITY + entity + "#" + objid.toString();
	}

	/**
	 * Obtiene la posición del ItemBean en la lista.
	 * @param itemBeanList Lista de ItemBean.
	 * @param itemId Identificador del ItemBean.
	 * @return Posición del ItemBean en la lista.
	 * @throws ISPACException si ocurre algún error.
	 */
	private int indexOf(List itemBeanList, int itemId) throws ISPACException {
		for (int i = 0; i < itemBeanList.size(); i++) {
			ItemBean itemBean = (ItemBean) itemBeanList.get(i);
			if (itemBean.getProperty("ID").equals(new Integer(itemId))) {
				return i;
			}
		}
		return -1;
	}

}