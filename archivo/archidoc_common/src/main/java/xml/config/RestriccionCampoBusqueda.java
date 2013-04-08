package xml.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;

/**
 * Clase que almacena la información de una restricción de un campo de búsqueda
 */
public class RestriccionCampoBusqueda extends XMLObject
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String RESTRICCION_IDCAMPO_TODOS_VALORES = "0";
	public static final short RESTRICCION_TIPO_ESPECIAL_TITULO = (short)-1;
	public static final short RESTRICCION_TIPO_ESPECIAL_CODIGO = (short)-2;
	public static final short RESTRICCION_TIPO_ESPECIAL_NUMERO_EXPEDIENTE = (short)-3;
	public static final short RESTRICCION_TIPO_ESPECIAL_CODIGO_REFERENCIA = (short)-4;
	public static final short RESTRICCION_TIPO_ESPECIAL_TABLA = (short)-5;
	public static final short RESTRICCION_TIPO_ESPECIAL_RANGOS = (short)-6;

	/**
	 *  Id del campo
	 */
	private String id = null;

	/**
	 *  Tipo del campo
	 */
	private String tipo = null;

    /**
     * Ids de las listas descriptoras necesarias para buscar el campo
     */
    private Map listasDescriptoras = new HashMap();

    /**
     * Constructor.
     */
    public RestriccionCampoBusqueda()
    {
    	super();
    }

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo el tipo a establecer
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param listasDescriptoras el listasDescriptoras a establecer
	 */
	public void setListasDescriptoras(Map listasDescriptoras) {
		this.listasDescriptoras = listasDescriptoras;
	}

    /**
     * Permite obtener las listas descriptoras
     * @return listas descriptoras
     */
    public List getListasDescriptoras() {

    	Iterator it = listasDescriptoras.entrySet().iterator();
    	List ltDescriptoras = new ArrayList();
    	while (it.hasNext()){
    		Entry entry = (Entry) it.next();
    		ltDescriptoras.add(entry.getValue());
    	}
        return ltDescriptoras;
    }

    /**
     * Permite añadir una listaDescriptora al campo
     * @param listaDescriptora listaDescriptora a añadir
     */
    public void addListaDescriptora(String listaDescriptora) {
        this.listasDescriptoras.put(listaDescriptora, listaDescriptora);
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

		xml.append(tabs + "<restriccion");
	    xml.append(id != null ? " id=\"" + id +"\"" : "");
	    xml.append(tipo != null ? " tipo=\"" + tipo +"\"" : "");

	    if (!listasDescriptoras.isEmpty()){
	    	xml.append(">");
	    	if (!listasDescriptoras.isEmpty()){
			    xml.append(Constants.NEWLINE);
			    xml.append(tabs + "  <listasDescriptoras>");
			    xml.append(Constants.NEWLINE);
			    Iterator it = listasDescriptoras.entrySet().iterator();
		    	while (it.hasNext()){
		    		Entry entry = (Entry) it.next();
		    		String listaDescriptora = (String) entry.getValue();
		    		xml.append(tabs+"    <lista>"+ listaDescriptora + "</lista>"+Constants.NEWLINE);
		    	}
		    	xml.append(tabs + "  </listasDescriptoras>");
		    }
	    	xml.append(Constants.NEWLINE);
		    xml.append(tabs + "</restriccion>");
	    } else {
	    	xml.append("/>");
	    }

		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
