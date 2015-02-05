/*
 * Created on 15-mar-2005
 *
 */
package ieci.tdw.ispac.ispacmgr.bean;

import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

/**
 * @author juanin
 *
 * WLStageBean extiende las propiedades de  una fase de la lista de trabajo
 * añadiendole los nombres de fase y procedimiento
 */
public class WLStageBean extends ItemBean
{
    public final static String NOMBREPCD 	= "NOMBREPCD";
    public final static String NOMBRE 		= "NOMBRE";

    public void processProperties(IProcedure procedure,Map stageMap)
    	throws ISPACException
    {
        if (procedure!=null)
            setProperty(WLStageBean.NOMBREPCD,procedure.getString("NOMBRE"));

        IItem stage=(IItem)stageMap.get(new Integer(mitem.getInt("ID_FASE")));
        if (stage!=null)
            setProperty(WLStageBean.NOMBRE,stage.getString("NOMBRE"));
    }
}
