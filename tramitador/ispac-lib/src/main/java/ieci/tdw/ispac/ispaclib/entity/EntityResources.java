package ieci.tdw.ispac.ispaclib.entity;

import java.util.HashMap;
import java.util.Map;

import ieci.tdw.ispac.api.ISPACEntities;

public class EntityResources {

	/**
	 * Identificador de la entidad.
	 */
	private int entityId = ISPACEntities.ENTITY_NULLREGKEYID;
	
	/**
	 * Mapa de recursos por idioma.
	 */
	private Map resourcesMap = new HashMap();
	
	
	/**
	 * Constructor.
	 */
	public EntityResources() {}

	/**
	 * Obtiene el identificador de la entidad.
	 * @return Identificador de la entidad.
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * Establece el identificador de la entidad.
	 * @param entityId Identificador de la entidad.
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * Añade un recurso.
	 * @param lang Idioma del recurso.
	 * @param key Nombre del recurso.
	 * @param value Texto del recurso en el idioma establecido.
	 */
	public void addResource(String lang, String key, String value) {
		
		// Obtener el mapa de recursos 
		Map resources = (Map) resourcesMap.get(key);
		if (resources == null) {
			resources = new HashMap();
			resourcesMap.put(key, resources);
		}
		
		resources.put(lang, value);
	}
	
	/**
	 * Obtiene el texto de un recurso en el idioma determinado.
	 * @param lang Idioma del recurso.
	 * @param key Nombre del recurso.
	 * @return Texto del recurso en el idioma determinado.
	 */
	public String getResource(String lang, String key) {
		String resource = null;
		Map resources = (Map) resourcesMap.get(key);
		if (resources != null) {
			resource = (String) resources.get(lang);
		}
		return resource;
	}

	/**
	 * Obtiene el texto de un recurso en todos los idiomas.
	 * @param key Nombre del recurso.
	 * @return Mapa con el texto del recurso en todos los idiomas.
	 */
	public Map getResources(String key) {
		return (Map) resourcesMap.get(key);
	}
}
