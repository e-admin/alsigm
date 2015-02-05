package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.File;

public class FileManager {


	// Filesystem Directory Context implementation.
	protected FileDirContext mFileDirContext = null;
	private static String defaultPath=ISPACConfiguration.TEMPLATE_PATH;

	
	
	public FileManager() throws ISPACException
	{
		 this(defaultPath);
	}

	
	public FileManager(String path) 
	throws ISPACException {

		try 
		{
			String sFilePath;
			ISPACConfiguration parameters = ISPACConfiguration.getInstance();
			sFilePath = parameters.get(path);
			if (sFilePath.endsWith("/"))
			{
				sFilePath = sFilePath.substring(sFilePath.length() - 1);
			}

			File file = new File(sFilePath);
			if (!file.exists())
			{
				file.mkdir();
			}

			mFileDirContext = new FileDirContext();
			mFileDirContext.setDocBase(sFilePath);
		} 
		catch (IllegalArgumentException e) 
		{
			throw new ISPACException(e);
		}
	}

	/**
	 * Obtiene el directorio raiz.
	 */
	public String getFileMgrPath() 
	{
		return mFileDirContext.getDocBase();
	}

	
	
	/**
	 * Elimina todos los ficheros
	 *  
	 */

	public void clean() 
	throws ISPACException {
		
		try 
		{
			File base = mFileDirContext.getBase();

			if (base == null)
			{
				return;
			}

			String[] names = base.list();

			for (int i = 0; i < names.length; i++) 
			{
				mFileDirContext.unbind(names[i]);
			}
		} 
		catch (Exception e) 
		{
			throw new ISPACException(e);
		}
	}

	
}