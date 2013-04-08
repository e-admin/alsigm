/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaccatalog/src/ieci/tdw/ispac/ispaccatalog/bean/PcdVersionBean.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/02/25 16:52:37 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaccatalog.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

/**
 * PcdVersionBean
 *
 *
 * @version $Revision: 1.2 $ $Date: 2008/02/25 16:52:37 $
 */
public class PcdVersionBean extends ItemBean
{

public IItem processItem(IItem item)
	throws ISPACException
{
    int state=item.getInt("ESTADO");
    String statetxt="";
    switch(state)
    {
    	case IProcedure.PCD_STATE_DRAFT:
    	    statetxt="DRAFT";
    		break;
    	case IProcedure.PCD_STATE_CURRENT:
    	    statetxt="CURRENT";
    		break;
    	case IProcedure.PCD_STATE_OLD:
    	    statetxt="OLD";
    		break;
    }
    setProperty("ESTADO_TXT",statetxt);

	return item;
}

}
