package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileTemplateManager extends FileManager {

	
	protected static FileTemplateManager mInstance = null;

	public static synchronized FileManager getInstance()
	throws ISPACException {
		
		if (mInstance == null) 
		{
			mInstance = new FileTemplateManager();
		}

		return mInstance;
	}
	public FileTemplateManager() throws ISPACException {
     super(ISPACConfiguration.TEMPLATE_PATH);
	}

	

	/**
	 * Obtiene el path a una plantilla.
	 * 
	 * @param templateId identificador de la plantilla.
	 * @return nombre del fichero.
	 * @exception ISPACException.
	 */
	public String getTemplatePath(DbCnt cnt, int templateId) 
	throws ISPACException {

		try 
		{
			CTTemplate ctTemplate = new CTTemplate(cnt, templateId);
			return getTemplatePath(cnt, ctTemplate);
		} 
		catch (Exception e) 
		{
			throw new ISPACException(e);
		}
	}
	
	public String getTemplatePath(DbCnt cnt,CTTemplate ctTemplate) 
	throws ISPACException {

		String sName = "";
		
		try 
		{
		    int templateId = ctTemplate.getTemplate();
			Date dateTemplate = ctTemplate.getDate();
			// Obtiene la plantilla
			String mimetype = ctTemplate.getMimetype();
			
			String ext = MimetypeMapping.getExtension( mimetype);
			// Nombre de la plantilla
			sName = Integer.toString(templateId) + "." + ext;
			
			//Control de plantillas por multientidad
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null){
				String organizationId = info.getOrganizationId();
				sName = organizationId + "_" + sName; 
			}
			
			File file = new File(mFileDirContext.getDocBase(), sName);
			// La plantilla ya existe
			if (file.exists()) 
			{
				FileResourceAttributes attributes = (
				FileResourceAttributes) mFileDirContext.getAttributes(sName);
				Date dateFile = attributes.getLastModifiedDate();
				// La feche de la plantilla es posterior a la
				// fecha del fichero temporal.
				if (dateTemplate.after(dateFile)) 
				{
					bind(cnt, sName, templateId);
				}
			} 
			else 
			{
				bind(cnt, sName, templateId);
			}
		} 
		catch (Exception e) 
		{
			throw new ISPACException(e);
		}

		return mFileDirContext.getDocBase() + "/" + sName;
	}
	
	

	/**
	 * Descarga la plantilla en el directorio temporal de plantillas
	 */
	protected void bind(DbCnt cnt, String sName, int Id)
	throws ISPACException {
		
		FileOutputStream out = null;

		try 
		{
			String sFileName = mFileDirContext.getDocBase() + "/" + sName;
			out = new FileOutputStream( sFileName);
			CTTemplate.getTemplate(cnt, Id, out);
		} 
		catch (IOException e) 
		{
			throw new ISPACException(e);
		} 
		finally 
		{
			try 
			{
				if (out != null)
				{
					out.close();
				}
			} 
			catch (IOException e) 
			{}
		}
	}
}