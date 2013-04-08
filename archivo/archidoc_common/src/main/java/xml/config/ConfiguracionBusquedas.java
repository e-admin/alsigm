package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena los parámetros de configuración de las búsquedas
 */
public class ConfiguracionBusquedas extends XMLObject
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Versión de la configuración
     */
    private String version = null;

    /**
     * Descripción de la configuración
     */
    private String descripcion = null;

    /**
     * Listas descriptoras usadas en busquedas
     */
    private Map listasDescriptoras = new HashMap();

    /**
     * Campos descriptivos usados en búsquedas
     */
    private Map camposDescriptivos = new HashMap();

    /**
     * Constructor.
     */
    public ConfiguracionBusquedas()
    {
    	super();
    }

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version el version a establecer
	 */
	public void setVersion(String version) {
		this.version = version;
	}

    /**
     * Permite añadir una lista descriptora
     *
     * @param nombre Nombre de la lista descriptora
     * @param valor Id de la lista descriptora
     */
    public void addListaDescriptora(ListaDescriptoraConfigBusqueda listaDescriptoraConfigBusqueda) {
        this.listasDescriptoras.put(listaDescriptoraConfigBusqueda.getNombre(), listaDescriptoraConfigBusqueda);
    }

    /**
     * Permite añadir una restricción de campo
     * @param nombre Nombre del campo
     * @param campo campo
     */
    public void addCampoDescriptivo(CampoDescriptivoConfigBusqueda campoDescriptivoConfigBusqueda) {
        this.camposDescriptivos.put(campoDescriptivoConfigBusqueda.getNombre(), campoDescriptivoConfigBusqueda);
    }

    /**
     * Permite obtener el id de una lista descriptora
     * @param nombre Nombre de la lista descriptora
     * @return Id de la lista descriptora
     */
    public ListaDescriptoraConfigBusqueda getListaDescriptora(String nombre){
    	return (ListaDescriptoraConfigBusqueda) listasDescriptoras.get(nombre);
    }
     /**
     * Permite obtener el id de un campo descriptivo
     * @param nombre Nombre del campo descriptivo
     * @return Id del campo descriptivo
     */
    public CampoDescriptivoConfigBusqueda getCampoDescriptivo(String nombre){
    	return (CampoDescriptivoConfigBusqueda) camposDescriptivos.get(nombre);
    }

	/**
	 * @return el camposDescriptivos
	 */
	public Map getCamposDescriptivos() {
		return camposDescriptivos;
	}

	/**
	 * @return el listasDescriptoras
	 */
	public Map getListasDescriptoras() {
		return listasDescriptoras;
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

		xml.append("<ConfigBusquedas");
	    xml.append(version != null ? " version=\"" + version + "\"": "");
	    xml.append(descripcion != null ? " descripcion=\"" + descripcion + "\"": "");
	    xml.append(">");
	    xml.append(Constants.NEWLINE);

	    /* Campos descriptivos */
	    if ((camposDescriptivos!=null)&&(!camposDescriptivos.isEmpty())){
	    	xml.append(tabs + "  <Campos_Descriptivos>");
	    	xml.append(Constants.NEWLINE);
	    	Iterator it = camposDescriptivos.entrySet().iterator();
	    	while (it.hasNext()){
	    		Entry entry = (Entry) it.next();
	    		CampoDescriptivoConfigBusqueda campo = (CampoDescriptivoConfigBusqueda) entry.getValue();
	    		xml.append(campo.toXML(2));
	    	}
	    	xml.append(tabs + "  </Campos_Descriptivos>");
	    	xml.append(Constants.NEWLINE);
	    }

	    /* Listas descriptoras */
	    if ((listasDescriptoras!=null)&&(!listasDescriptoras.isEmpty())){
	    	xml.append(tabs + "  <Listas_Descriptoras>");
	    	xml.append(Constants.NEWLINE);
	    	Iterator it = listasDescriptoras.entrySet().iterator();
	    	while (it.hasNext()){
	    		Entry entry = (Entry) it.next();
	    		ListaDescriptoraConfigBusqueda lista = (ListaDescriptoraConfigBusqueda) entry.getValue();
	    		xml.append(lista.toXML(2));
	    	}
	    	xml.append(tabs + "  </Listas_Descriptoras>");
	    	xml.append(Constants.NEWLINE);
	    }

	    xml.append("</ConfigBusquedas>");
	    xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
