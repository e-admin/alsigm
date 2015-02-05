/*
 * Created on Sep 9, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.bean;


/**
 *
 * BeanPropertyLdapReduceSizeFmt
 *
 * @deprecated
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:16:58 $
 */
public class BeanPropertyLdapReduceSizeFmt extends BeanPropertyFmt
{

	public BeanPropertyLdapReduceSizeFmt ()
	{
	}

	public Object format(Object value)
	{
		int size = Integer.parseInt(getWidth());
		String result = (String) value;

 		if (result.length() > size)
			result = result.substring(0, size) + "...";

		return result;
	}
}
