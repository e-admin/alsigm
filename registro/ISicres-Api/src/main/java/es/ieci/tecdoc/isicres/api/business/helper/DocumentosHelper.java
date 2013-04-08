/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 * 
 *          Clase en la que se trabajara con los datos de los documentos
 * 
 */
public class DocumentosHelper {

	/**
	 * Método para convertir los documentos con el formato del API en documentos
	 * con el formato de Invesicres
	 * 
	 * @param documentos
	 * @return
	 */
	public static Map getDocumentosISicres(List documentos) {
		Map documents = new HashMap();

		if (null == documentos || documentos.isEmpty()) {
			return documents;
		}

		for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
			DocumentoRegistroVO documentoRegistro = (DocumentoRegistroVO) iterator
					.next();
			FlushFdrDocument document = new FlushFdrDocument();
			document.setDocumentName(documentoRegistro.getName());
			document.setPages(getPaginasISicres(documentoRegistro));

			// Registramos el objeto con una clave que no se pueda repetir
			documents.put(documentoRegistro.toString(), document);

		}

		return documents;
	}

	/**
	 * Método para convertir las paginas de un documento con el formato del API
	 * en las paginas de un documento en el formato de invesicres
	 * 
	 * @param documentoRegistro
	 * @return
	 */
	private static List getPaginasISicres(DocumentoRegistroVO documentoRegistro) {
		if (documentoRegistro.getPaginas() == null
				|| documentoRegistro.getPaginas().isEmpty()) {
			return null;
		}

		List listaPaginas = new ArrayList();
		for (Iterator iterator = documentoRegistro.getPaginas().iterator(); iterator
				.hasNext();) {
			PaginaDocumentoRegistroVO paginaRegistro = (PaginaDocumentoRegistroVO) iterator
					.next();
			FlushFdrPage page = new FlushFdrPage();
			page.setFatherId(paginaRegistro.getIdDocumentoRegistro());
			page.setPageName(paginaRegistro.getName());
			if (paginaRegistro.getNumeroPagina() != null) {
				page.setTreeId(paginaRegistro.getNumeroPagina().toString());
			}
			page.setFile(getFicheroISicres(paginaRegistro));

			listaPaginas.add(page);
		}

		return listaPaginas;
	}

	/**
	 * Método para convertir el fichero asociado a una pagina con el formato del
	 * API en el fichero asociado a una pagina en el formato de invesicres
	 * 
	 * @param paginaRegistro
	 * @return
	 */
	private static FlushFdrFile getFicheroISicres(
			PaginaDocumentoRegistroVO paginaRegistro) {
		if (paginaRegistro.getDocumentoFisico() == null) {
			return null;
		}

		DocumentoFisicoVO ficheroRegistro = paginaRegistro.getDocumentoFisico();

		FlushFdrFile file = new FlushFdrFile();
		file.setFileID(ficheroRegistro.getLocation());
		if (StringUtils.isNotBlank(ficheroRegistro.getName())) {
			file.setFileNameLog(ficheroRegistro.getName());
			if (StringUtils.isEmpty(file.getFileID())) {
				file.setFileNameFis(ficheroRegistro.getName());
			}
		} else {
			file.setFileNameLog(paginaRegistro.getName());
			if (StringUtils.isEmpty(file.getFileID())) {
				file.setFileNameFis(paginaRegistro.getName());
			}
		}
		file.setExtension(ficheroRegistro.getExtension());
		file.setBuffer(ficheroRegistro.getContent());

		return file;

	}

}
