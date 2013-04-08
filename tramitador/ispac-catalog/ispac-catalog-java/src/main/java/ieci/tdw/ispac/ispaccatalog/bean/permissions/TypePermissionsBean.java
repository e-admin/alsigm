/*
 * invesflow Java - ISPAC
 * Fuente: NoticeBean.java
 * Creado el 25-may-2005 por juanin
 *
 */
package ieci.tdw.ispac.ispaccatalog.bean.permissions;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Añade una propiedad nueva resultado de la traducción de una propiedad
 * numérica a su representación textual:
 * <p>
 * <code>PERMISO</code> en <code>PERMISO_TEXT</code>
 *
 */
public class TypePermissionsBean extends ItemBean
{
	private static HashMap TYPEPERMISSIONSMAP = new HashMap();

    static
    {
        TYPEPERMISSIONSMAP.put(null,"permission.type");
        TYPEPERMISSIONSMAP.put("0", "permission.type.0");
        TYPEPERMISSIONSMAP.put("1","permission.type.1");
        TYPEPERMISSIONSMAP.put("2","permission.type.2");
        TYPEPERMISSIONSMAP.put("3","permission.type.3");
//        TYPEPERMISSIONSMAP.put("2","Administrar casos");
//        TYPEPERMISSIONSMAP.put("3","Editar/Modificar");
    }
    
	public IItem processItem(IItem item)
	throws ISPACException
	{
	    String typePermissions=(String)TYPEPERMISSIONSMAP.get(item.getString("PERMISO"));
	    setProperty("PERMISO_TEXT",typePermissions);
		return item;
	}
    
    public void uidName(Map respsMap) throws ISPACException 
    {
       setProperty("RESPNAME", ((ItemBean)respsMap.get(getString("UID_USR"))).getProperty("RESPNAME"));
    	
    
    }
    public void setUidName(Map respsMap) throws ISPACException 
    {
    	setProperty("RESPNAME", respsMap.get(getString("UID_USR")));
    }
    
}
