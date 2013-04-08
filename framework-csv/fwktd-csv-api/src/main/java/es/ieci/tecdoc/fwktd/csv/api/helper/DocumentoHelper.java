package es.ieci.tecdoc.fwktd.csv.api.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.csv.api.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

/**
 * Clase de utilidad para transformar objetos con información de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DocumentoHelper.class);

	public static InfoDocumentoCSV getInfoDocumentoCSV(DocumentoVO documentoVO) {

		InfoDocumentoCSV infoDocumentoCSV = null;

		if (documentoVO != null) {
			infoDocumentoCSV = new InfoDocumentoCSV();
			infoDocumentoCSV.setId(documentoVO.getId());
			infoDocumentoCSV.setNombre(documentoVO.getNombre());
			infoDocumentoCSV.setTipoMime(documentoVO.getTipoMIME());
			infoDocumentoCSV.setFechaCreacion(documentoVO.getFechaCreacion());
			infoDocumentoCSV.setFechaCaducidad(documentoVO.getFechaCaducidad());
			infoDocumentoCSV.setCsv(documentoVO.getCsv());
			infoDocumentoCSV.setFechaCSV(documentoVO.getFechaCSV());
			infoDocumentoCSV.setDisponible(documentoVO.isDisponible());

			AplicacionVO aplicacion = documentoVO.getAplicacion();
			if (aplicacion != null) {
				infoDocumentoCSV.setCodigoAplicacion(aplicacion.getCodigo());
				infoDocumentoCSV.setNombreAplicacion(aplicacion.getNombre());
			}

			infoDocumentoCSV.addDescripciones(getDescripcionesMap(documentoVO.getDescripcion()));
		}

		return infoDocumentoCSV;
	}

	public static DocumentoCSV getDocumentoCSV(DocumentoVO documentoVO) {

		DocumentoCSV documentoCSV = null;

		if (documentoVO != null) {
			documentoCSV = new DocumentoCSV();
			documentoCSV.setId(documentoVO.getId());
			documentoCSV.setNombre(documentoVO.getNombre());
			documentoCSV.setTipoMime(documentoVO.getTipoMIME());
			documentoCSV.setFechaCreacion(documentoVO.getFechaCreacion());
			documentoCSV.setFechaCaducidad(documentoVO.getFechaCaducidad());
			documentoCSV.setCsv(documentoVO.getCsv());
			documentoCSV.setFechaCSV(documentoVO.getFechaCSV());
			documentoCSV.setDisponible(documentoVO.isDisponible());

			AplicacionVO aplicacion = documentoVO.getAplicacion();
			if (aplicacion != null) {
				documentoCSV.setCodigoAplicacion(aplicacion.getCodigo());
				documentoCSV.setNombreAplicacion(aplicacion.getNombre());
			}

			documentoCSV.addDescripciones(getDescripcionesMap(documentoVO.getDescripcion()));
		}

		return documentoCSV;
	}

	public static DocumentoVO getDocumentoVO(InfoDocumentoCSV infoDocumentoCSV, AplicacionManager aplicacionManager, boolean copyId) {

		DocumentoVO documentoVO = null;

		if (infoDocumentoCSV != null) {
			documentoVO = new DocumentoVO();

			if (copyId) {
				documentoVO.setId(infoDocumentoCSV.getId());
			}

			documentoVO.setNombre(infoDocumentoCSV.getNombre());
			documentoVO.setDescripcion(getDescripcion(infoDocumentoCSV));
			documentoVO.setTipoMIME(infoDocumentoCSV.getTipoMime());
			documentoVO.setFechaCreacion(infoDocumentoCSV.getFechaCreacion());
			documentoVO.setFechaCaducidad(infoDocumentoCSV.getFechaCaducidad());
			documentoVO.setCsv(infoDocumentoCSV.getCsv());
			documentoVO.setFechaCSV(infoDocumentoCSV.getFechaCSV());
			documentoVO.setDisponible(infoDocumentoCSV.isDisponible());

			// Añadir la información de la aplicación
			AplicacionVO aplicacion = aplicacionManager.getAplicacionByCodigo(infoDocumentoCSV.getCodigoAplicacion());
			if (aplicacion != null) {
				documentoVO.setAplicacion(aplicacion);
			} else {
				throw new CSVException(
						"error.csv.application.codeNotFound",
						new String[] { infoDocumentoCSV.getCodigoAplicacion() },
						"No existe ninguna aplicación con el código: "
								+ infoDocumentoCSV.getCodigoAplicacion());
			}
		}

		return documentoVO;
	}

	public static Map<String, String> getDescripcionesMap(String xmlDescripciones) {

		Map<String, String> descripcionesMap = new HashMap<String, String>();

		if (StringUtils.isNotBlank(xmlDescripciones)) {

			try {
				Document doc = DocumentHelper.parseText(xmlDescripciones);

				@SuppressWarnings("unchecked")
				List<Node> labelNodes = doc.selectNodes("/labels/label");
				if (labelNodes != null) {
					for (Node labelNode : labelNodes) {
						String locale = labelNode.valueOf("@locale");
						if (StringUtils.isBlank(locale)) {
							locale = "default";
						}

						descripcionesMap.put(locale, labelNode.getText());
						if (descripcionesMap.get("default") == null) {
							descripcionesMap.put("default", labelNode.getText());
						}
					}
				}

			} catch (DocumentException e) {
				logger.error("Error al parsear el xml con las descripciones: " + xmlDescripciones, e);
			}
		}

		return descripcionesMap;
	}

	public static String getDescripcion(InfoDocumentoCSVForm form) {

        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");

        // Nodo: labels
        Element rootNode = doc.addElement("labels");

        Map<String, String> descripciones = form.getDescripciones();
        if (MapUtils.isNotEmpty(descripciones)) {
        	for (String locale : descripciones.keySet()) {

        		String descripcion = descripciones.get(locale);
        		if (StringUtils.isNotBlank(descripcion)) {

        	        // Nodo: label
        	        Element labelElement = rootNode.addElement("label");
        	        labelElement.addAttribute("locale", locale);
        	        labelElement.addCDATA(descripcion);
        		}
        	}
        }

        return doc.asXML();
	}
}
