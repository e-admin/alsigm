/*
 * Created on 21-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

/**
 * @author juanin
 *
 */
public class WLWorklistFactory
{
    public WLProcessListBuilder getProcessListBuilder(String filenamepath)
    throws ISPACException
    {
        File file = new File(filenamepath);
        try
        {
            return getProcessListBuilder(new FileInputStream(file.getPath()));
        }
        catch(FileNotFoundException e)
        {
            throw new ISPACException("El formato de lista de trabajo "+file.getAbsoluteFile()+ "no existe",e);
        }
    }

    public WLProcessListBuilder getProcessListBuilder(InputStream resourcestream)
    throws ISPACException
    {
        try
        {
            URL rulesUrl = WLWorklistFactory.class.getClassLoader().getResource(
                    "ieci/tdw/ispac/ispaclib/worklist/WorklistRules.xml");
            Digester digester = DigesterLoader.createDigester(rulesUrl);
            return (WLProcessListBuilder)digester.parse(resourcestream);
        } catch (Exception e)
        {
            throw new ISPACException("getProcessListBuilder() -", e);
        }
    }
}

