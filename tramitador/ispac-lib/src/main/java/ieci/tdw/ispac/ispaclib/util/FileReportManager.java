package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;

public class FileReportManager extends FileManager {

	/**
	 * Constructor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public FileReportManager() throws ISPACException {
		super(ISPACConfiguration.REPORT_PATH);
	}
	
	
	public File getJasperFile(IItem ctReport) throws ISPACException {

		try {
			
			// Identificador del informe en el catálogo
			int reportId = ctReport.getKeyInt();

			// Nombre de la plantilla compilada
			String fileName = String.valueOf(reportId) + ".jasper";

			// Control de plantillas por multientidad
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if ((info != null) && StringUtils.isNotBlank(info.getOrganizationId())){
				fileName = info.getOrganizationId() + "_" + fileName;
			}

			return new File(mFileDirContext.getDocBase(), fileName);
			
		} catch (Exception e) {
			throw new ISPACException("Error al obtener el fichero jasper", e);
		}
	}
	

	public File createTemplateFile(IItem ctReport) throws ISPACException {

		try {
			File templateFile = getTemplateFile(ctReport);
			
			FileOutputStream tempXml = new FileOutputStream(templateFile);
			tempXml.write(ctReport.getString("XML").getBytes());
			tempXml.flush();
			tempXml.close();
			
			return templateFile;
			
		} catch (Exception e) {
			throw new ISPACException("Error al crear la plantilla", e);
		}
	}

	public File createTemplateFile(String xml) throws ISPACException {

		try {
			File templateFile = mFileDirContext.newFile(".jrxml");
			
			FileOutputStream tempXml = new FileOutputStream(templateFile);
			tempXml.write(xml.getBytes());
			tempXml.flush();
			tempXml.close();
			
			return templateFile;
			
		} catch (Exception e) {
			throw new ISPACException("Error al crear la plantilla", e);
		}
	}

	public File getTemplateFile(IItem ctReport) throws ISPACException {

		try {
			
			// Identificador del informe en el catálogo
			int reportId = ctReport.getKeyInt();

			// Nombre de la plantilla compilada
			String fileName = String.valueOf(reportId) + ".jrxml";
			
			// Control de plantillas por multientidad
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if ((info != null) && StringUtils.isNotBlank(info.getOrganizationId())){
				fileName = info.getOrganizationId() + "_" + fileName;
			}

			return new File(mFileDirContext.getDocBase(), fileName);
			
		} catch (Exception e) {
			throw new ISPACException("Error al componer el fichero de la plantilla", e);
		}
	}
	
}