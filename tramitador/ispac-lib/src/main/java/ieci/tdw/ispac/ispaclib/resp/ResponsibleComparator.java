/*
 * Created on Sep 7, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.Comparator;


public class ResponsibleComparator implements Comparator
{
	public int compare (Object obj1, Object obj2)
	{
		ItemBean itembean1 = (ItemBean) obj1;
		ItemBean itembean2 = (ItemBean) obj2;
		Responsible item1 = (Responsible) itembean1.getItem();
		Responsible item2 = (Responsible) itembean2.getItem();
		String dn1 = item1.getName();
		String dn2 = item2.getName();		
/*		String dn1 = null;
		String dn2 = null;
		try
		{
			dn1 = (String) itembean1.getItem().get("NAME");
			dn2 = (String) itembean2.getItem().get("NAME");
		}
		catch (ISPACException e)
		{
			return 0;
		}
		*/
			
		return dn1.compareToIgnoreCase(dn2);
	}
}


