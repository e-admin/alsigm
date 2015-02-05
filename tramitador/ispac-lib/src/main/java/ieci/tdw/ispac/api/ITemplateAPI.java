package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ITemplateAPI
{
	/**
	 * Crea una nueva plantilla.
	 * @param idTpDoc identificador del tipo de documento
	 * @param name nombre de la plantilla
	 * @param code código de la plantilla
	 * @param expresion expresion de la plantilla
	 * @param idPcd identificador de procedimiento
	 * @param fichero para asociar a la plantilla
	 * @return el objeto plantilla
	 * @throws ISPACException
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	public CTTemplate newTemplate (int idTpDoc, String name, String code, int idPcd, String expresion, InputStream fichero)
	throws ISPACException, FileNotFoundException, IOException;

	/**
	 * Crea una nueva plantilla.
	 * 
	 * @param idTpDoc
	 *            identificador del tipo de documento
	 * @param name
	 *            nombre de la plantilla
	 * @param code
	 *            código de la plantilla
	 * @param expresion
	 *            expresion de la plantilla
	 * @param idPcd
	 *            identificador de procedimiento
	 * @param fichero
	 *            para asociar a la plantilla
	 * @param mimeType
	 *            Tipo MIME
	 * @return el objeto plantilla
	 * @throws ISPACException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public CTTemplate newTemplate(int idTpDoc, String name, String code,
			int idPcd, String expresion, InputStream fichero, String mimeType)
			throws ISPACException, FileNotFoundException, IOException;

	/**
	 * Obtiene las plantillas del catálogo.
	 * @return Lista de plantillas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getTemplates() throws ISPACException;

	/**
	 * Obtiene las plantillas del catálogo.
	 * @param templateName Nombre de la plantilla.
	 * @return Lista de plantillas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getTemplates(String templateName) throws ISPACException;
	
	/**
	 * Obtiene una plantilla.
	 * @param id identificador de la plantilla
	 * @return la plantilla
	 * @throws ISPACException
	 */
	public CTTemplate getTemplate(int id)
	throws ISPACException;

	/**
	 * Obtiene una plantilla
	 * @param code Código de la plantilla.
	 * @return Plantilla
	 * @throws ISPACException si ocurre algún error.
	 */
	public TemplateDAO getTemplateByCode(String code) throws ISPACException;
	
	/**
	 * Elimina una plantilla
	 * @param template la plantilla
	 * @throws ISPACException
	 */
	public void deleteTemplate(CTTemplate template)
	throws ISPACException;

	/**
	 * Asigna el contenido a una plantilla.
	 * @param template plantilla
	 * @param in fuente de los datos del archivo a anexar
	 * @param mimetype mimetype del archivo
	 * @throws ISPACException
	 */
	public void setTemplate(CTTemplate template, InputStream in,int length, String mimetype)
	throws ISPACException;

	/**
	 * Obtiene el contenido de una plantilla
	 * @param template la plantilla
	 * @param out destino donde se vuelca el archivo
	 * @throws ISPACException
	 */
	public void getTemplate(CTTemplate template, OutputStream out)
	throws ISPACException;
	
	/**
	 * Crea una plantilla nueva asociandole un documento
	 * @param idTpDoc
	 * @param name
	 * @param code
	 * @param expresion
	 * @param procId
	 * @param fichero
	 * @return
	 * @throws ISPACException
	 */
	public CTTemplate createTemplateProc (int idTpDoc, String name, String code, String expresion, int procId, InputStream fichero)
	throws ISPACException, FileNotFoundException, IOException;

	/**
	 * Crea una plantilla nueva asociandole un documento
	 * 
	 * @param idTpDoc
	 * @param name
	 * @param code
	 * @param expresion
	 * @param procId
	 * @param fichero
	 * @param mimeType
	 * @return
	 * @throws ISPACException
	 */
	public CTTemplate createTemplateProc(int idTpDoc, String name, String code,
			String expresion, int procId, InputStream fichero, String mimeType)
			throws ISPACException, FileNotFoundException, IOException;

	/**
	 * Elimina la plantilla genérica.
	 * @param templateId Identificador de la plantilla.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteTemplate(int templateId) throws ISPACException;

	/**
	 * Elimina una plantilla especifica del procedimiento pasado por parametro
	 *  
	 * @param procId
	 * @param id_p_plantdoc
	 * @return
	 * @throws ISPACException
	 */
	public void deleteTemplateProc(int procId, int id_p_plantdoc)
	throws ISPACException;

	/**
	 * Elimina una plantilla especifica de la lista de plantillas de los procedimientos
	 * 
	 * @param id_p_plantdoc
	 * @return
	 * @throws ISPACException
	 */
	public void deleteTemplateProc(int id_p_plantdoc)
	throws ISPACException;
	
	/**
	 * Comprueba si el id de plantilla pertenece a una plantilla específica
	 * 
	 * @return True si es una plantilla específica de un procedimiento
	 * 
	 */
	public boolean isProcedureTemplate(int id_p_plantdoc)
	 throws ISPACException ;
	
	/**
	 * Cuenta los procedimientos a los que esta añadida una plantilla específica
	 * @param id_p_plantdoc
	 * @return El numero de procedimiento en los que se encuentra una plantilla específica
	 * @throws ISPACException
	 */
	public int countTemplatesProcedure(int id_p_plantdoc)
	throws ISPACException ;
	
	/**
	 * Comprueba si el tipo MIME de una plantilla está soportado por el gestor
	 * de plantillas
	 * 
	 * @param mimeType
	 *            Tipo MIME de la plantilla.
	 * @return true si el tipo MIME está soportado, false en caso contrario.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public boolean isMimeTypeSupported(String mimeType) throws ISPACException;
	
}
