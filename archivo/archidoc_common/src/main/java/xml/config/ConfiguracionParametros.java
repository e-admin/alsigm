package xml.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import xml.XMLObject;

public class ConfiguracionParametros extends XMLObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9104444588354981947L;
	private Map listaParametros = null;
	
	
	/**
	 * Constructor.
	 */
	public ConfiguracionParametros()
	{
		super();
	}    
    
    /**
	 * Muestra el Objeto en Formato XML
	 */
    public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_Parametros>");
		if(listaParametros!=null){
			for(Iterator it=listaParametros.entrySet().iterator();it.hasNext();){
				xml.append(Constants.NEWLINE);				
				xml.append(tabs + "  " + "<parametro>");
				Map.Entry par=(Map.Entry)it.next();
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "       <id>");
				xml.append((String)par.getKey());
				xml.append("</id>");
				xml.append(Constants.NEWLINE);				
				xml.append(tabs + "       <valor>");
				xml.append((String)par.getValue());
				xml.append("</valor>");
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "  " + "</parametro>");
			}
		}
		xml.append(Constants.NEWLINE);
		xml.append(tabs + "</Configuracion_Parametros>");
		xml.append(Constants.NEWLINE);
		return xml.toString();
	}

	/**
	 * Añade el parámetro especificado.
	 * @param parametro
	 */
	public void addParametro(Parametro parametro) {
		if(listaParametros == null)listaParametros = new HashMap();
		
		listaParametros.put(parametro.getId(), parametro.getValor());
	}
	
	/**
	 * Obtiene el Valor de Parámetro Especificado.
	 * @param id Identificador del Parámetro
	 * @return Valor del Parámetro.
	 */
	public String getValor(String id) {
		return (String)listaParametros.get(id);
	}
}
