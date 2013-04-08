package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MakeEntityApp
{

	public MakeEntityApp()
	{
	}

	public void generate(int entityId, String sOutputFileName) throws ISPACException
	{

		DbCnt cnt = null;

		try
		{
			EntityDAO entityDAO = null;
			cnt = new DbCnt();

			cnt.getConnection();

			entityDAO = EntityFactoryDAO.getInstance().newEntityDAO(cnt, entityId);

			String sXML = entityDAO.toXml();

			StringReader stringBuffer = new StringReader(sXML);

			TransformerFactory factory = TransformerFactory.newInstance();

			// Obtiene el documento xml
			//File fxml = new File( "c:/temp", "entity.xml");
			//FileInputStream inxml = new FileInputStream( fxml.getPath());
			//Source xml = new StreamSource( inxml);
			// Obtiene el documento xsl

			Source xml = new StreamSource(stringBuffer);

			ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			String sFileName = parameters.get(ISPACConfiguration.FILE_ENTITY_FORM);

			URL url = ISPACConfiguration.class.getClassLoader().getResource(sFileName);
			if (url == null)
				url = ClassLoader.getSystemResource(sFileName);
			if (url == null)
				throw new Exception("No encuentra el fichero " + sFileName);

			String sPath = url.getPath();
			String sFolder = new String(sPath.getBytes(), 0, sPath.lastIndexOf("/") + 1);

			File fxsl = new File(sFolder, sFileName);
			FileInputStream inxsl = new FileInputStream(fxsl.getPath());
			Source xsl = new StreamSource(inxsl);
			// Crea un transformer
			Transformer transformer = factory.newTransformer(xsl);

			String sOutputFolder = parameters.get(ISPACConfiguration.FOLDER_ENTITY_OUTPUT);
			File file = new File(sOutputFolder, sOutputFileName);
			FileOutputStream out = new FileOutputStream(file.getPath());
			// Realiza la transformacion
			transformer.transform(xml, new StreamResult(out));

			//inxml.close();
			inxsl.close();
			out.close();
		} 
		catch (Exception e)
		{
			throw new ISPACException(e);
		} 
		finally
		{
			if (cnt != null)
				cnt.closeConnection();
		}
	}

	public static void main(String[] args)
	{
		int entityId = -1;
		String sOutputFileName = null;

		try
		{
			/*
			if (args.length != 2)
			{
				entityId = 1;
				System.out.println("MakeEntityApp <entity> <jsp>");
				System.out.println("Genera el formulario jsp que gestiona una entidad.");
				System.out.println();
				System.out.println("Para que funcione correctamente es necesario que ");
				System.out.println("el fichero 'ispac.properties' esté localizado en el ");
				System.out.println("classpath. ");
				System.out.println();
				System.out.println("Este fichero contiene el nombre del fichero xsl que ");
				System.out.println("que se encarga de la transformación en el parámetro ");
				System.out.println("FILE_ENTITY_FORM.");
				System.out.println();
				System.out.println("El fichero xsl se tiene que localizar en el classpath. ");
				System.out.println();
				System.out.println("También contiene el nombre del directorio donde se ");
				System.out.println("almacena el fichero jsp resultante en el parámetro ");
				System.out.println("FOLDER_ENTITY_OUTPUT. ");
				sOutputFileName = "output.jsp";
			} else
			{
				entityId = Integer.parseInt(args[0]);
				sOutputFileName = args[1];
			}
			*/
			entityId = 1;
			sOutputFileName = "output.jsp";
			
			MakeEntityApp entityForm = new MakeEntityApp();

			entityForm.generate(entityId, sOutputFileName);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}