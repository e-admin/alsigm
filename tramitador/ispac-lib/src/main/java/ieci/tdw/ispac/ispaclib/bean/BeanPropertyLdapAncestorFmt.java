/*
 * Created on Sep 3, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;

public class BeanPropertyLdapAncestorFmt extends BeanPropertyFmt
{
	public BeanPropertyLdapAncestorFmt ()
	{
	}

	public Object format(Object value)
	{
		String result = (String) value;
		try
		{
			IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
			String root = directory.getContextRoot();
			if (result.equals(root))
				return root;
		}
		catch(ISPACException e)
		{

		}

		int posFirstEqual = result.indexOf('=');
		if (posFirstEqual != -1) {
			
			String firstEqualInDn = result.substring(posFirstEqual + 1, result.length());
			
			int posSecondEqual = firstEqualInDn.indexOf('=');
			if (posSecondEqual != -1) {
				
				String name = firstEqualInDn.substring(0, posSecondEqual);
				int posLastComa = name.lastIndexOf(',');
				if (posLastComa != -1) {
				
					result = result.substring(0, posFirstEqual + 1 + posLastComa);
				}
			}
		}

		return result;
	}
}
