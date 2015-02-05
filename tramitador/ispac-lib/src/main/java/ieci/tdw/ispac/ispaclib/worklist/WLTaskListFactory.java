package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

public class WLTaskListFactory
{
    public WLTaskListBuilder getTaskListBuilder(String filenamepath)
    throws ISPACException
    {
        File file = new File(filenamepath);
        try
        {
            return getTaskListBuilder(new FileInputStream(file.getPath()));
        }
        catch(FileNotFoundException e)
        {
            throw new ISPACException("El formato de lista de trabajo "+file.getAbsoluteFile()+ "no existe",e);
        }
    }

    public WLTaskListBuilder getTaskListBuilder(InputStream resourcestream)
    throws ISPACException
    {
        try
        {
            URL rulesUrl = WLTaskListFactory.class.getClassLoader().getResource(
                    "ieci/tdw/ispac/ispaclib/worklist/TaskListRules.xml");
            Digester digester = DigesterLoader.createDigester(rulesUrl);
            return (WLTaskListBuilder)digester.parse(resourcestream);
        } catch (Exception e)
        {
            throw new ISPACException("getTaskListBuilder() -", e);
        }
    }

    public WLTaskListBuilder getClosedTaskListBuilder(String filenamepath)
    throws ISPACException
    {
        File file = new File(filenamepath);
        try
        {
            return getTaskListBuilder(new FileInputStream(file.getPath()));
        }
        catch(FileNotFoundException e)
        {
            throw new ISPACException("El formato de lista de trabajo "+file.getAbsoluteFile()+ "no existe",e);
        }
    }

    public WLTaskListBuilder getClosedTaskListBuilder(InputStream resourcestream)
    throws ISPACException
    {
        try
        {
            URL rulesUrl = WLTaskListFactory.class.getClassLoader().getResource(
                    "ieci/tdw/ispac/ispaclib/worklist/ClosedTaskListRules.xml");
            Digester digester = DigesterLoader.createDigester(rulesUrl);
            return (WLTaskListBuilder)digester.parse(resourcestream);
        } catch (Exception e)
        {
            throw new ISPACException("getClosedTaskListBuilder() -", e);
        }
    }
}

