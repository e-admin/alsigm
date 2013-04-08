package ieci.tdw.ispac.ispacmgr.comparators;

import java.util.Comparator;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

/**
 * Compara los nombres de los grupos
 * @author Ildefonso Noreña
 *
 */
public class GroupComparator implements Comparator
{
	public int compare (Object obj1, Object obj2)
	{
		ItemBean itembean1 = (ItemBean) obj1;
		ItemBean itembean2 = (ItemBean) obj2;
		String name1 = null;
		String name2 = null;
		try
		{
			name1 = (String) itembean1.getItem().get("NAME");
			name2 = (String) itembean2.getItem().get("NAME");
		}
		catch (ISPACException e)
		{
			return 0;
		}
		return name1.compareToIgnoreCase(name2);
	}
}