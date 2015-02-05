/*
 * Created on Sep 9, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.bean;


public class BeanPropertyLdapUserFmt extends BeanPropertyFmt
{
	private final int MAXLENGTH = 25;

	public BeanPropertyLdapUserFmt ()
	{
	}

	public Object format(Object value)
	{
		String result = (String) value;
		
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

 		if (result.length() > MAXLENGTH)
			result = result.substring(0, MAXLENGTH) + "...";

		return result;
	}
}
