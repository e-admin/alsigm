/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/app/ExtendedEntityApp.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:17:00 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

/**
 * ExtendedEntityApp
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:17:00 $
 */
public class ExtendedEntityApp extends SimpleEntityApp
{
	/**
     * @param context
     */
    public ExtendedEntityApp(ClientContext context)
    {
        super(context);
    }

	public String getValue( String key)
	throws ISPACException	{
		String rKey = null;
		try{
			 rKey = mitem.getString( key);
		}catch(ISPACException e){
			rKey = (String)valuesExtra.get(key);
		}
		return rKey;
	}
	
	public void setValue( String key, String value)
	throws ISPACException {

		Property property = null;
		try
		{
			property = mitem.getProperty(key);

			if (property != null)
			{
				Object object = TypeConverter.parse( property.getType(), value);
				mitem.set( key, object);
			}else{
				this.valuesExtra.put( key, value );
			}
		}
		catch(ISPACException ie)
		{
			throw new ISPACException( "La propiedad " + property.getName() + " no admite el valor " + value,ie);
		}

	}
}
