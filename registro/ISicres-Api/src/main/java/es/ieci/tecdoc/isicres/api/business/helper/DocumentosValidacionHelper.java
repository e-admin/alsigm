package es.ieci.tecdoc.isicres.api.business.helper;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que contiene metodos de validacion para los documentos/paginas
 *
 */
public class DocumentosValidacionHelper {

	//Maxima longitud de nombre para los documentos
	private static final int maxLengthNameDocument = 32;

	//Maxima longitud de nombre para las paginas
	private static final int maxLengthNamePage = 64;

	private static final Logger logger = Logger
			.getLogger(DocumentosValidacionHelper.class);


	/**
	 * Metodo que valida los documentos
	 *
	 * @param documentos - Listado de documentos {@link DocumentoRegistroVO}
	 */
	public static void validateDocuments(List documentos){
		if (null != documentos) {
			for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
				DocumentoRegistroVO documentoRegistro = (DocumentoRegistroVO) iterator
						.next();

				//validamos el nombre del documento
				if(documentoRegistro.getName().length()> maxLengthNameDocument){
					StringBuffer sb = new StringBuffer();
					sb.append("El nombre del documento [")
							.append(documentoRegistro.getName())
							.append("] no puede ser mayor de ")
							.append(maxLengthNameDocument)
							.append(" caracteres.");
					logger.error(sb.toString());
					throw new RegistroException(sb.toString());
				}

				//validamos las paginas del documento
				validatePaginas(documentoRegistro.getPaginas());
			}
		}
	}

	/**
	 * Metodo que valida las paginas
	 *
	 * @param paginas - Listado de paginas {@link PaginaDocumentoRegistroVO}
	 */
	public static void validatePaginas(List paginas){
		if(paginas!=null){
			for (Iterator iterator = paginas.iterator(); iterator.hasNext();) {
				PaginaDocumentoRegistroVO paginaRegistro = (PaginaDocumentoRegistroVO) iterator.next();

				//validamos el nombre de la pagina
				if (paginaRegistro.getName().length() > maxLengthNamePage) {
					StringBuffer sb = new StringBuffer();
					sb.append("El nombre de la pagina [")
							.append(paginaRegistro.getName())
							.append("] no puede ser mayor de ")
							.append(maxLengthNamePage)
							.append(" caracteres.");
					logger.error(sb.toString());
					throw new RegistroException(sb.toString());
				}
			}
		}
	}

}
