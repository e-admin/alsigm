package xml.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;

/**
 * Clase que almacena la información de un campo de búsqueda
 */
public class CampoBusqueda extends XMLObject
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Nombre del campo
	 */
	private String nombre = null;

	/**
	 *  Variable para indicar si se muestra el campo
	 */
	private String mostrar = null;
	
	/**
	 *  Tipo del campo
	 */
	private String tipo = null;
	
	/**
	 *  Variable para indicar si se muestran los calificadores para una fecha
	 */
	private String mostrarCalificadores = null;
    
    /**
     * Indica si se muestra un link en el campo
     */
    private String mostrarLink = null;
    
    /**
     * Map de restricciones
     */
    private LinkedHashMap restricciones = new LinkedHashMap();
    
    /**
     * Indica si el campo es configurable por el usuario
     */
    private String configurable = null;
    
    
    /**
     * Clave del properties que contiene el título
     */
    private String tituloKey = null;
    
    private String mostrarOperadores = null;
    
    private String activos=null;
    
    /**
     * S o N. Indica si el campo se muestra abreviado o no
     */
    private String abreviado=null;
    
    
    /**
     * Si el campo se muestra abreviado, el número de caracteres que deben mostrar.
     */
    private String numCaracteres=null;
    
    
    /**
     * Identificador del campo
     */
    private String valor = null;
    
    private int index = 0;
    

    
    
    
    /**
     * Constructor.
     */
    public CampoBusqueda()
    {
    	super();
    }
    
	/**
	 * @return el mostrarLink
	 */
	public String getMostrarLink() {
		return mostrarLink;
	}

	/**
	 * @param mostrarLink el mostrarLink a establecer
	 */
	public void setMostrarLink(String mostrarLink) {
		this.mostrarLink = mostrarLink;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el mostrar
	 */
	public String getMostrar() {
		return mostrar;
	}

	/**
	 * @param mostrar el mostrar a establecer
	 */
	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}
    
    public void addRestriccion(RestriccionCampoBusqueda restriccion) {
        this.restricciones.put(restriccion.getId(), restriccion);
    }

	/**
	 * @return el mostrarCalificadores
	 */
	public String getMostrarCalificadores() {
		return mostrarCalificadores;
	}

	/**
	 * @param mostrarCalificadores el mostrarCalificadores a establecer
	 */
	public void setMostrarCalificadores(String mostrarCalificadores) {
		this.mostrarCalificadores = mostrarCalificadores;
	}
	
	/**
	 * @return el restricciones
	 */
	public LinkedHashMap getRestricciones() {
		return restricciones;
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

	public String getConfigurable() {
		return configurable;
	}


	public void setConfigurable(String configurable) {
		this.configurable = configurable;
	}

	public String getTituloKey() {
		return tituloKey;
	}

	public void setTituloKey(String tituloKey) {
		this.tituloKey = tituloKey;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getMostrarOperadores() {
		return mostrarOperadores;
	}

	public void setMostrarOperadores(String mostrarOperadores) {
		this.mostrarOperadores = mostrarOperadores;
	}

	/**
	 * @return el restricciones
	 */
	public RestriccionCampoBusqueda getRestriccion(String key) {
		RestriccionCampoBusqueda restriccion = null;
		if (restricciones!=null)
			return (RestriccionCampoBusqueda) restricciones.get(key);
		return restriccion;
	}

	/**
	 * @param restricciones el restricciones a establecer
	 */
	public void setRestricciones(LinkedHashMap restricciones) {
		this.restricciones = restricciones;
	}

	/**
	 * @return the activos
	 */
	public String getActivos() {
		return activos;
	}

	/**
	 * @param activos the activos to set
	 */
	public void setActivos(String activos) {
		this.activos = activos;
	}

	public String getAbreviado() {
		return abreviado;
	}

	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}

	public String getNumCaracteres() {
		return numCaracteres;
	}

	public void setNumCaracteres(String numCaracteres) {
		this.numCaracteres = numCaracteres;
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

		xml.append(tabs + "<campo");
	    xml.append(nombre != null ? " nombre=\"" + nombre +"\"" : "");
	    xml.append(mostrar!=null ? " mostrar=\"" + mostrar +"\"" : "");
	    xml.append(tipo!=null ? " tipo=\"" + tipo +"\"" : "");
	    xml.append(mostrarCalificadores!=null ? " mostrarCalificadores=\"" + mostrarCalificadores +"\"" : "");
	    xml.append(mostrarLink!=null ? " mostrarLink=\"" + mostrarLink + "\"": "");
	    xml.append(configurable!=null ? " configurable=\"" + configurable + "\"": "");
	    xml.append(tituloKey!=null ? " tituloKey=\"" + tituloKey + "\"": "");
	    xml.append(mostrarOperadores!=null ? " mostrarOperadores=\"" + mostrarOperadores + "\"": "");
	    xml.append(activos!=null ? " activos=\"" + activos + "\"": "");
	    xml.append(abreviado!=null ? " abreviado=\"" + abreviado + "\"": "");
	    xml.append(abreviado!=null && numCaracteres != null ? " numCaracteres=\"" + numCaracteres + "\"": "");
	    
	    if(abreviado!=null){
	    }
	    
	    
	    if (!restricciones.isEmpty()){
	    	xml.append(">");
		    if (!restricciones.isEmpty()){
			    xml.append(Constants.NEWLINE);
			    Iterator it = restricciones.entrySet().iterator();
		    	while (it.hasNext()){
		    		Entry entry = (Entry) it.next();
		    		RestriccionCampoBusqueda restriccion = (RestriccionCampoBusqueda) entry.getValue();
		    		xml.append(restriccion.toXML(3));
		    	}
		    }
		    
		    xml.append(tabs + "</campo>");
	    	xml.append(Constants.NEWLINE);
	    } else {
	    	xml.append("/>");
	    	xml.append(Constants.NEWLINE);
	    }
		
		return xml.toString();
	}

}
