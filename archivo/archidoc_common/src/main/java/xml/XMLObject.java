package xml;

import java.io.Serializable;

import common.Constants;
import common.Globals;
import common.util.StringUtils;


/**
 * Clase abstracta de la que deberán extender todos los objetos que
 * tengan relación con un fichero XML de configuración.
 */
public abstract class XMLObject implements Serializable, Cloneable 
{

	private static final long serialVersionUID = 1L;

	public static final String TRUE = Constants.TRUE_STRING;
	public static final String FALSE = Constants.FALSE_STRING;
	
	/**
	 * Obtiene una representación del objeto.
	 * @return Representación del objeto.
	 */
	public String toString()
	{
		return toXML(0);
	}


	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public abstract String toXML(int indent);

	public String getCabeceraXML(String tabs){
		if(StringUtils.isEmpty(tabs)){
			tabs = Constants.BLANK;
		}
		
		return tabs + Globals.CABECERA_XML_FILES; 
	}
	
}
