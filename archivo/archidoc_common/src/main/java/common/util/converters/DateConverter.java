package common.util.converters;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

import common.util.DateUtils;

/**
 * Utilidad para convertir objetos a Date.
 */
public class DateConverter implements Converter
{

    /**
     * Constructor.
     */
    public DateConverter()
    {
    }

    
    /**
     * Convierte un objeto a Date.
     * @param type Clase del objeto.
     * @param value Valor del objeto.
     * @return Objeto de tipo Date.
     */
    public Object convert(Class type, Object value)
    {
        if(value == null)
            return null;
        
        if(value instanceof Date)
            return value;
        
        return DateUtils.getDate(value.toString());
    }

}
