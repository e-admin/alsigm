package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena los mapeos de campos descriptivos de la fracción de serie a las unidades documentales en las que se va a dividir
 */
public class ConfiguracionMapeoFSUDoc extends XMLObject
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
     * Campos descriptivos usados en búsquedas
     */
    private Map camposDescriptivos = new HashMap();

    /**
     * Constructor.
     */
    public ConfiguracionMapeoFSUDoc()
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
     * Permite añadir una restricción de campo
     * @param nombre Nombre del campo
     * @param campo campo
     */
    public void addCampoDescriptivo(CampoDescriptivoConfigMapFSUDoc campoDescriptivoMapFSUDoc) {
        this.camposDescriptivos.put(campoDescriptivoMapFSUDoc.getValorOrigen(), campoDescriptivoMapFSUDoc);
    }

     /**
     * Permite obtener el id de un campo descriptivo
     * @param nombre Nombre del campo descriptivo
     * @return Id del campo descriptivo
     */
    public CampoDescriptivoConfigMapFSUDoc getCampoDescriptivo(String valorOrigen){
    	return (CampoDescriptivoConfigMapFSUDoc) camposDescriptivos.get(valorOrigen);
    }

	/**
	 * @return el camposDescriptivos
	 */
	public Map getCamposDescriptivos() {
		return camposDescriptivos;
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

		xml.append("<Map_fs_udoc");
	    xml.append(version != null ? " version=\"" + version + "\"": "");
	    xml.append(descripcion != null ? " descripcion=\"" + descripcion + "\"": "");
	    xml.append(">");
	    xml.append(Constants.NEWLINE);

	    /* Campos descriptivos mapeados */
	    if ((camposDescriptivos!=null)&&(!camposDescriptivos.isEmpty())){
	    	xml.append(tabs + "  <Campos_Mapeo>");
	    	xml.append(Constants.NEWLINE);
	    	Iterator it = camposDescriptivos.entrySet().iterator();
	    	while (it.hasNext()){
	    		Entry entry = (Entry) it.next();
	    		CampoDescriptivoConfigMapFSUDoc campo = (CampoDescriptivoConfigMapFSUDoc) entry.getValue();
	    		xml.append(campo.toXML(2));
	    	}
	    	xml.append(tabs + "  </Campos_Mapeo>");
	    	xml.append(Constants.NEWLINE);
	    }


	    xml.append("</Map_fs_udoc>");
	    xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
