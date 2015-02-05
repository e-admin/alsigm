/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/CTCatalogFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:16:58 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

/**
 * CTCatalogFactory
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:16:58 $
 */
public class CTCatalogFactory
{
    public CTCatalogBuilder getCatalogBuilder(String filenamepath)
    throws ISPACException
    {
        File file = new File(filenamepath);
        try
        {
            return getCatalogBuilder(new FileInputStream(file.getPath()));
        }
        catch(FileNotFoundException e)
        {
            throw new ISPACException("La descripción de entidades y aplicaciones "+file.getAbsoluteFile()+ " no existe",e);
        }
    }

    public CTCatalogBuilder getCatalogBuilder(InputStream resourcestream)
    throws ISPACException
    {
        try
        {
            URL rulesUrl = CTCatalogFactory.class.getClassLoader().getResource(
                    "ieci/tdw/ispac/ispaclib/catalog/CatalogRules.xml");
            Digester digester = DigesterLoader.createDigester(rulesUrl);
            return (CTCatalogBuilder)digester.parse(resourcestream);
        } catch (Exception e)
        {
            throw new ISPACException("getCatalogBuilder() -", e);
        }
    }
}
