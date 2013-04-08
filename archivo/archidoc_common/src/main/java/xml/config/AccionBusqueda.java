package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;

/**
 * Clase que almacena la información de una acción de una búsqueda.
 */
public class AccionBusqueda extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la acción
	 */
	private String id = null;

	/**
	 * Permisos necesarios para que se muestre la acción en la lista de acciones.
	 */
	private String permisos;

	/**
     * Map de tipos permitidos
     */
    private Map mapTiposPermitidos = new HashMap();


    /**
     * Constructor.
     */
    public AccionBusqueda()
    {
    	super();
    }

    /**
     * Devuelve el identificador de la acción
	 * @return identificador de la acción
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador de la acción
	 * @param id identificador de la acción
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Permite añadir un tipo permitido a la acción de búsqueda
     * @param tipoAccionBusqueda tipo a añadir
     */
    public void addTipoPermitido(TipoAccionBusqueda tipoAccionBusqueda) {
		if (tipoAccionBusqueda != null){
			this.mapTiposPermitidos.put(tipoAccionBusqueda.getId(),tipoAccionBusqueda);
		}
    }

	/**
	 * Map con los tipos permitidos para la acción actual
	 * @return Map con los tipos permitidos para la acción actual
	 */
	public Map getMapTiposPermitidos() {
		return mapTiposPermitidos;
	}

	/**
	 * Devuelve un tipo permitido para una acción
	 * @return tipo permitido para una acción {@link TipoAccionBusqueda}
	 */
	public TipoAccionBusqueda getTipoPermitido(String id) {
		TipoAccionBusqueda tipoAccionBusqueda = null;
		if ((mapTiposPermitidos!=null)&&(StringUtils.isNotEmpty(id))){
			tipoAccionBusqueda = (TipoAccionBusqueda) mapTiposPermitidos.get(id);
		}
		return tipoAccionBusqueda;
	}

	/**
	 * Establece los tipos permitidos para la acción actual
	 * @param mapTiposPermitidos tipos permitidos para la acción actual
	 */
	public void setMapTiposPermitidos(Map mapTiposPermitidos) {
		this.mapTiposPermitidos = mapTiposPermitidos;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat("  ", indent);

		xml.append(tabs + "<Accion");

		if ((id!=null) && (!"".equals(id))){
			xml.append(" id=\""+id+"\"");
		}

		xml.append(">").append(Constants.NEWLINE);

	    /* Campos de entrada */
	    if ((mapTiposPermitidos!=null)&&(!mapTiposPermitidos.isEmpty())){
	    	xml.append(tabs + "  <Tipos_permitidos>");
	    	xml.append(Constants.NEWLINE);

	    	String key = null;
	    	TipoAccionBusqueda tipoAccionBusqueda = null;
	    	Iterator it = mapTiposPermitidos.keySet().iterator();
	    	while (it.hasNext()){
	    		key = (String) it.next();
	    		tipoAccionBusqueda = (TipoAccionBusqueda) mapTiposPermitidos.get(key);
	    		xml.append(tipoAccionBusqueda.toXML(indent+2));
	    	}
	    	xml.append(tabs + "  </Tipos_permitidos>");
	    	xml.append(Constants.NEWLINE);
	    }

	    xml.append(tabs + "</Accion>");
	    xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}

	public String getPermisos() {
		return permisos;
	}

}
