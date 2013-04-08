/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/CTCatalogBuilder.java,v $
 * $Revision: 1.3 $
 * $Date: 2008/06/17 09:11:01 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.IApplicationDef;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;

import java.util.HashMap;

/**
 * CTCatalogBuilder
 *
 *
 * @version $Revision: 1.3 $ $Date: 2008/06/17 09:11:01 $
 */
public class CTCatalogBuilder
{
    private HashMap mentitydefmapById;
    private HashMap mentitydefmapByName;
    private HashMap mentityappdefmap;

    public CTCatalogBuilder()
    {
        mentitydefmapById = new HashMap();
        mentitydefmapByName = new HashMap();
        mentityappdefmap = new HashMap();
    }

    /**
     * Añade la definición de una aplicación.
     * Se llama en el CatalogRules.xml para cargar las aplicaciones de las entidades del catálogo.
     */
    public void addAppDef(IApplicationDef appdef) throws ISPACException
    {
    	mentityappdefmap.put(new Integer(appdef.getId()), appdef);
    }

    /**
     * Añade la definición de una entidad.
     * Se llama en el CatalogRules.xml para cargar las entidades del catálogo.
     */
    public void addEntityDef(IEntityDef entitydef) throws ISPACException
    {
        mentitydefmapById.put(new Integer(entitydef.getId()),entitydef);
        mentitydefmapByName.put(entitydef.getName(),entitydef);
    }

    /**
     * Obtiene la definición cacheada de una aplicación.
     */
    public IApplicationDef getAppDef(int id) throws ISPACException
    {
    	IApplicationDef entityappdef = (IApplicationDef) mentityappdefmap.get(new Integer(id));
        if (entityappdef == null)
            throw new ISPACException("CTCatalogBuilder::getAppDef() - No existe la aplicación de entidad con id = " + id);
        return entityappdef;
    }

    /**
     * Obtiene la definición cacheada de una entidad a partir del identificador.
     */
    public IEntityDef getEntityDef(int id) throws ISPACException
    {
    	IEntityDef entitydef = (IEntityDef) mentitydefmapById.get(new Integer(id));
        if (entitydef == null)
            throw new ISPACException("CTCatalogBuilder::getEntityDef() No existe la entidad de catálogo con id = " + id);
        return entitydef;
    }
    
    /**
     * Obtiene la definición cacheada de una entidad a partir del nombre de tabla.
     */
    public IEntityDef getEntityDef(String name) throws ISPACException
    {
    	IEntityDef entitydef = (IEntityDef) mentitydefmapByName.get(name);
        if (entitydef == null)
            throw new ISPACException("CTCatalogBuilder::getEntityDef() No existe la entidad de catálogo con nombre de tabla = " + name);
        return entitydef;
    }
    
}