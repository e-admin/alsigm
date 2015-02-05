package ieci.tdw.ispac.ispaclib.gendoc.stamp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.io.File;

import org.apache.struts.util.MessageResources;

/**
 * Conector de sellado de documentos.
 * 
 */
public interface StampConnector {

	/**
	 * Sella un documento.
	 * 
	 * @param ctx
	 *            Contexto de cliente.
	 * @param document
	 *            Información del documento.
	 * @param messageResources
	 *            Mensajes de recursos.
	 * @param response
	 *            Respuesta al cliente.
	 * @return Fichero con el documento sellado.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public File stampDocument(ClientContext ctx, IItem document,
			MessageResources messageResources) throws ISPACException;

}
