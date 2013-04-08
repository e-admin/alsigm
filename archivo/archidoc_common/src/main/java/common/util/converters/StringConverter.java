package common.util.converters;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

import common.util.DateUtils;


/**
 * Utilidad para convertir objetos a String.
 */
public class StringConverter implements Converter
{

    /**
     * Constructor.
     */
    public StringConverter()
    {
        
    }
    
    /**
     * Convierte un objeto a String.
     * @param type Clase del objeto.
     * @param value Valor del objeto.
     * @return Objeto de tipo String.
     */
    public Object convert(Class type, Object value)
    {
        if(value == null)
            return (String)null;
        else if (value instanceof Date)
        	  return DateUtils.formatDate((Date)value);
        else
            return value.toString();
    }

}
