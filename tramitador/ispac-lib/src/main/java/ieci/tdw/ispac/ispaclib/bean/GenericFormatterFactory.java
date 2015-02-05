/*
 * Created on 11-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.sql.Types;
import java.util.Iterator;

/**
 *
 * Permite crear un formateador genérico a partir de las propiedades de un IItem
 *
 */
public class GenericFormatterFactory extends BeanFormatterFactory
{

    /**
     * @throws ISPACException
     */
    public GenericFormatterFactory() throws ISPACException
    {
        super();
    }


	/**
	 * Crea un formateador que incluirá todas las propiedades del IItem.
	 *
	 * @param item  IItem para el que se desea obtener el formateador
	 * @return el objeto BeanFormatter
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatter(IItem item)
			throws ISPACException
	{
	    if (item==null)
	        return new BeanFormatter();

	    return getBeanFormatter(item.getProperties());
	}


	/**
	 * Crea un formateador que incluirá todas las propiedades del IItem.
	 *
	 * @param prop  Propiedades para el formateador
	 * @return el objeto BeanFormatter
	 * @throws ISPACException
	 */
	public BeanFormatter getBeanFormatter(Properties prop)
			throws ISPACException
	{
	    BeanFormatter beanformatter=new BeanFormatter();

	    if (prop==null)
	        return beanformatter;

	    Iterator it=prop.iterator();
	    while (it.hasNext())
        {
            Property property = (Property) it.next();
            beanformatter.add(createBeanFormatter(property));
        }
	    return beanformatter;
	}

	private BeanPropertyFmt createBeanFormatter(Property property)
	{
	    BeanPropertyFmt beanfmt=newBeanFormatter(property);
	    if (beanfmt==null)
	    	return null;

	    beanfmt.setProperty(property.getName());
	    beanfmt.setTitle(property.getName());
	    beanfmt.setFieldType("DATA");
	    beanfmt.setReadOnly(false);

	    return beanfmt;
	}
	private BeanPropertyFmt newBeanFormatter(Property property)
	{
		switch(property.getType())
		{
		case Types.BIT:
		case Types.TINYINT:
		case Types.SMALLINT:
		    return new BeanPropertySimpleFmt();

		case Types.INTEGER:
		    return new BeanPropertySimpleFmt();

		case Types.BIGINT:
		    return new BeanPropertySimpleFmt();

		case Types.REAL:
		    return new BeanPropertySimpleFmt();
		case Types.FLOAT:
		case Types.DOUBLE:
		    return new BeanPropertySimpleFmt();

		case Types.NUMERIC:
		case Types.DECIMAL:
		    return new BeanPropertySimpleFmt();

		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		    return new BeanPropertySimpleFmt();

		case Types.TIMESTAMP:
		{
		    BeanPropertyDateFmt beanfmt=new BeanPropertyDateFmt();
		    beanfmt.setFormat(TypeConverter.TIMESTAMPFORMAT);
		    return beanfmt;
		}
		case Types.DATE:
		{
		    BeanPropertyDateFmt beanfmt=new BeanPropertyDateFmt();
		    beanfmt.setFormat(TypeConverter.DATEFORMAT);
		    return beanfmt;
		}
		case Types.TIME:
		{
		    BeanPropertyDateFmt beanfmt=new BeanPropertyDateFmt();
		    beanfmt.setFormat(TypeConverter.TIMEFORMAT);
		    return beanfmt;
		}

		default:
			return new BeanPropertySimpleFmt();
		}
	}
}
