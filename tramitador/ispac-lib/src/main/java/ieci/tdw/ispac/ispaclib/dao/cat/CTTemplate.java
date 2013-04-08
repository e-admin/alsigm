package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CTTemplate extends CompositeItem {
	
	private static final long serialVersionUID = 4595932643210047186L;
	
	protected static Logger logger = Logger.getLogger(CTTemplate.class);
	//
	public CTTemplate()
	{
		super("TEMPLATE:ID");
	}

	public CTTemplate( DbCnt cnt, int templateId)
	throws ISPACException
	{
		super("TEMPLATE:ID");
		getTemplate(cnt, templateId);
	}

	public CTTemplate( DbCnt cnt, TemplateDAO template)
	throws ISPACException
	{
		super("TEMPLATE:ID");
		addItem( template, "TEMPLATE:");
		TemplateDataDAO data = new TemplateDataDAO(cnt, template.getKeyInt());
		addItem( data, "DATA:", false);
	}

	public void newTemplate(DbCnt cnt, int idTpDoc, String name, String code, int idPcd, String expresion, String documentTemplate)
	throws ISPACException
	{
		
		if (idPcd == 0 && TemplateDAO.getTemplate( cnt, name, idTpDoc, expresion) != null)
		{
			throw new ISPACException("Ya existe una plantilla de nombre '" + name + "' asociada al tipo de documento actual con la misma expresión");
		}
		if (TemplateDAO.getTemplate( cnt, name, idTpDoc, idPcd) != null)
		{
			throw new ISPACException("Ya existe una plantilla de nombre '"+ name +"' asociada al tipo de documento y procedimiento actual");
		}

		clear();
		TemplateDAO template = new TemplateDAO(cnt);
		template.createNew(cnt);
		template.set("ID_TPDOC", idTpDoc);
		template.set("NOMBRE", name);
		template.set("COD_PLANT", code);
		template.set("EXPRESION", expresion);
		template.set("FECHA", new Date(System.currentTimeMillis()));
		template.store( cnt);
		addItem( template, "TEMPLATE:");
		// Datos de la plantilla
		setDefaultTemplate( cnt, template, documentTemplate);
		TemplateDataDAO data = new TemplateDataDAO(cnt, template.getKeyInt());
		addItem( data, "DATA:", false);
	}
	
	/**
	 * Para crear la plantilla cuando el documento asociado no es el que se pone por defecto en blanco
	 * sino que se carga el mismo al mismo tiempo que se crea la plantilla.
	 * @param cnt
	 * @param idTpDoc
	 * @param name
	 * @param code
	 * @param idPcd
	 * @param expresion
	 * @param fichero
	 * @throws ISPACException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void newTemplate(DbCnt cnt, int idTpDoc, String name, String code, int idPcd, String expresion, InputStream fichero)
	throws ISPACException, FileNotFoundException, IOException {
		newTemplate(cnt, idTpDoc, name, code, idPcd, expresion, fichero, null);
	}

	public void newTemplate(DbCnt cnt, int idTpDoc, String name, String code, int idPcd, String expresion, InputStream fichero, String mimeType)
	throws ISPACException, FileNotFoundException, IOException
	{		
		if (idPcd == 0 && TemplateDAO.getTemplate( cnt, name, idTpDoc, expresion) != null) {
			throw new ISPACException("Ya existe una plantilla de nombre '" + name + "' asociada al tipo de documento actual con la misma expresión");
		}
		
		if (TemplateDAO.getTemplate( cnt, name, idTpDoc, idPcd) != null) {
			throw new ISPACException("Ya existe una plantilla de nombre '"+ name +"' asociada al tipo de documento y procedimiento actual");
		}
		
		clear();
		TemplateDAO template = new TemplateDAO(cnt);
		template.createNew(cnt);
		template.set("ID_TPDOC", idTpDoc);
		template.set("NOMBRE", name);
		template.set("COD_PLANT", code);
		template.set("EXPRESION", expresion);
		template.set("FECHA", new Date(System.currentTimeMillis()));
		
		if (StringUtils.isNotBlank(mimeType)) {
			template.set("MIMETYPE", mimeType);		
		}
		
		template.store( cnt);
		
		addItem( template, "TEMPLATE:");
		
		// Datos de la plantilla
		setDefaultTemplate( cnt, template, fichero, mimeType);
		
		TemplateDataDAO data = new TemplateDataDAO(cnt, template.getKeyInt());		
		addItem( data, "DATA:", false);
	}

	public void getTemplate(DbCnt cnt, int templateId)
	throws ISPACException
	{
		clear();
		TemplateDAO template = new TemplateDAO(cnt, templateId);
		addItem( template, "TEMPLATE:");
		TemplateDataDAO data = new TemplateDataDAO(cnt, template.getKeyInt());
		addItem( data, "DATA:", false);
	}

	public void deleteTemplate(DbCnt cnt)
	throws ISPACException
	{
		TemplateDAO.delete(cnt, getInt("TEMPLATE:ID"));
	}

	public void setTemplate(DbCnt cnt, InputStream in,int length, String mimetype)
	throws ISPACException
	{
		int templateId = getInt("TEMPLATE:ID");
		
		TemplateDAO.setTemplate(cnt,templateId,in,length,mimetype);
		removeItem( "DATA:");
		TemplateDataDAO data = new TemplateDataDAO(cnt, templateId);
		addItem( data, "DATA:", false);
	}
	
	public void getTemplate(DbCnt cnt, OutputStream out)
	throws ISPACException
	{
		TemplateDAO.getTemplate( cnt,getInt("TEMPLATE:ID"),out);
	}

	public static void getTemplate(DbCnt cnt, int templateId, OutputStream out)
	throws ISPACException
	{
		TemplateDAO.getTemplate( cnt,templateId,out);
	}

	public int getTemplate()
	throws ISPACException
	{
		return getInt("TEMPLATE:ID");
	}

	public int getDocument()
	throws ISPACException
	{
		return getInt("TEMPLATE:ID_TPDOC");
	}

	public String getName()
	throws ISPACException
	{
		return getString("TEMPLATE:NOMBRE");
	}
	
	public String getExpresion()
	throws ISPACException
	{
		return getString("TEMPLATE:EXPRESION");
	}
	
	public Date getDate()
	throws ISPACException
	{
		return getDate("TEMPLATE:FECHA");
	}

	public int getSize()
	throws ISPACException
	{
		return getInt("DATA:NBYTES");
	}

	public String getMimetype()
	throws ISPACException
	{
		return getString("DATA:MIMETYPE");
	}

	protected void setDefaultTemplate(DbCnt cnt,TemplateDAO template, String defaultTemplate)
	throws ISPACException
	{
//Este codigo daba error al hacer 'new File(new URI(url.toString()));' en el catalogo montado para desarrollo en Madrid SIGEM (era linux)
//Error: java.lang.IllegalArgumentException: URI is not hierarchical 		
//		URL url = this.getClass().getClassLoader().getResource(defaultTemplate);
//		if (url == null)
//		{
//		    throw new ISPACException("No se ha podido encontrar la plantilla por defecto");
//		}
//		try
//        {
//            File file = new File(new URI(url.toString()));
//
//            InputStream in = new FileInputStream(file);
//            template.setTemplate(cnt,in,(int)file.length(),"application/msword");
//        } 
//		catch (Exception e)
//        {
//            throw new ISPACException(e);
//
//        } 
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(defaultTemplate);
		setDefaultTemplate(cnt, template, in);
	}
	
	
	/**
	 * Asigna a la plantilla nueva el documento que se acaba de cargar
	 * @param cnt
	 * @param template
	 * @param in
	 * @throws ISPACException
	 */
	protected void setDefaultTemplate(DbCnt cnt,TemplateDAO template, InputStream in)
			throws ISPACException {
		setDefaultTemplate(cnt, template, in, null);
	}
	
	protected void setDefaultTemplate(DbCnt cnt,TemplateDAO template, InputStream in, String mimeType)
			throws ISPACException {
		
		if (StringUtils.isBlank(mimeType)) {
			mimeType = "application/msword";
		}
		
		try{
		  int length = 0;		  
		  if (in.markSupported()) {
			  
			  while(in.read() != -1) {
				  length++;
			  }
			  in.close();
			  in.reset();
			  		  
			  template.setTemplate(cnt,in,length,mimeType);
			  
		  } else {			
			  FileTemporaryManager ftm = FileTemporaryManager.getInstance();
			  File tmpFile = ftm.newFile();
			  OutputStream out = new FileOutputStream(tmpFile);
			  FileUtils.copy(in, out);
			  out.flush();
			  out.close();
			  
			  template.setTemplate(cnt, new FileInputStream(tmpFile),
					  (int)tmpFile.length(), mimeType);			  		  
			  if (tmpFile != null) {
				  tmpFile.delete();
			  }
		  }		  		 
		  
		} catch (Exception e) {
			logger.error(e);
			throw new ISPACException(e);
		}
	}

}
