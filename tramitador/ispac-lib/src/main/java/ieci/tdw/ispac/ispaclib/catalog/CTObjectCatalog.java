package ieci.tdw.ispac.ispaclib.catalog;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.IApplicationDef;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;

import java.io.InputStream;

/**
 *
 * @version $Revision: 1.3 $ $Date: 2008/06/17 09:11:26 $
 */
public class CTObjectCatalog
{
    static private CTObjectCatalog s_instance=null;

    private CTCatalogBuilder  catbuilder;

    private CTObjectCatalog()  throws ISPACException
    {
        catbuilder=loadCatalog();
    }

    public static synchronized CTObjectCatalog getInstance()  throws ISPACException
    {
        if (s_instance!=null)
            return s_instance;

        s_instance=new CTObjectCatalog();

        return s_instance;
    }

    private CTCatalogBuilder loadCatalog()
    throws ISPACException
    {
        CTCatalogFactory catalogfactory=new CTCatalogFactory();

//        String catalogdef=ISPACConfiguration.getInstance().get(ISPACConfiguration.CATALOG_ENTITYDEF);
//        InputStream is=null;
//        if (catalogdef != null)
//            is=ISPACConfiguration.getInstance().getFileInputStream(catalogdef);
//        else
//            is=CTCatalogFactory.class.getClassLoader().getResourceAsStream(
//        		"ieci/tdw/ispac/ispaclib/catalog/catalogdef.xml");
        InputStream is = CTCatalogFactory.class.getClassLoader()
				.getResourceAsStream("ieci/tdw/ispac/ispaclib/catalog/catalogdef.xml");

        return catalogfactory.getCatalogBuilder(is);
    }

    public IApplicationDef getAppDef(int id) throws ISPACException
    {
        return catbuilder.getAppDef(id);
    }

    public IEntityDef getEntityDef(int id) throws ISPACException
    {
        return catbuilder.getEntityDef(id);
    }
    
    public IEntityDef getEntityDef(String name) throws ISPACException
    {
        return catbuilder.getEntityDef(name);
    }

    public IApplicationDef getDefaultAppDef(int entityid) throws ISPACException
    {
    	IEntityDef entitydef = getEntityDef(entityid);
        return getAppDef(entitydef.getAppId());
    }
    
}