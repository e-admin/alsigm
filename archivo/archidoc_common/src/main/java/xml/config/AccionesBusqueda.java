package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import xml.config.AccionBusqueda;

import common.Constants;
import common.util.StringUtils;

/**
 * Clase con la definición de las posibles acciones de búsqueda
 */
public class AccionesBusqueda {

    /**
     * Lista de posibles acciones de búsqueda
     */
    private Map mapAccionesBusqueda = new HashMap();
    
	/**
	 * Permite obtener una instancia de la clase
	 * @return ConfigConstants Instancia de la clase
	 */
	public AccionesBusqueda(){
		super();
	}
	
	/**
     * Permite añadir una nueva acción de búsqueda
     * @param accionBusqueda acción de búsqueda a añadir
     */
    public void addAccionBusqueda(AccionBusqueda accionBusqueda) {
		if (accionBusqueda != null){
			this.mapAccionesBusqueda.put(accionBusqueda.getId(),accionBusqueda);
		}
    }
    
	/**
	 * Permite obtener una acción de búsqueda específica
	 * @return Objeto {@link AccionBusqueda}}
	 */
	public AccionBusqueda getAccionBusqueda(String key) {
		AccionBusqueda accionBusqueda = null;
		if (mapAccionesBusqueda!=null)
			accionBusqueda = (AccionBusqueda) mapAccionesBusqueda.get(key);
		return accionBusqueda;
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

		xml.append(tabs + "<AccionesBusqueda>");
		xml.append(Constants.NEWLINE);
	    
	    /* Campos de entrada */
	    if ((mapAccionesBusqueda!=null)&&(!mapAccionesBusqueda.isEmpty())){
	    	
	    	AccionBusqueda accionBusqueda  = null;
	    	String key = null;
	    	Iterator it = mapAccionesBusqueda.keySet().iterator();
	    	while (it.hasNext()){
	    		key = (String) it.next();
	    		accionBusqueda = (AccionBusqueda) mapAccionesBusqueda.get(key);
	    		xml.append(accionBusqueda.toXML(1));
	    	}
	    }
	    
	    xml.append("</AccionesBusqueda>");
	    xml.append(Constants.NEWLINE);

		return xml.toString();
	}
	
	public String toString() {
		return this.toXML(0);
	}
	
}

