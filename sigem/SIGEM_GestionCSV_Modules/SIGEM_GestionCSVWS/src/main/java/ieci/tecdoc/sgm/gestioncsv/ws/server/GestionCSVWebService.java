/**
 *
 */
package ieci.tecdoc.sgm.gestioncsv.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.gestioncsv.DocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.BooleanRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.ByteArrayB64;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.DocumentoCSVRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.InfoDocumentoCSVFormDTO;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.InfoDocumentoCSVRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.server.dto.StringB64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author IECISA
 *
 *         Implementación WS del servicio CSV
 *
 */
public class GestionCSVWebService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GestionCSVWebService.class);

	private static final java.lang.String SERVICE_NAME = ConstantesServicios.SERVICE_GESTION_CSV;

	/**
	 *
	 * @return El servicio de los mensajes Cortos
	 * @throws SOAPException
	 * @throws SigemException
	 */
	private ServicioGestionCSV getServicioGestionCSV() throws SOAPException, SigemException {
		if (logger.isDebugEnabled()) {
			logger.debug("getServicioGestionCSV() - start");
		}

		java.lang.String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext
				.getCurrentContext());

		if ((cImpl == null) || ("".equals(cImpl))) {
			ServicioGestionCSV returnServicioGestionCSV = LocalizadorServicios
					.getServicioGestionCSV();
			if (logger.isDebugEnabled()) {
				logger.debug("getServicioGestionCSV() - end");
			}
			return returnServicioGestionCSV;
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			ServicioGestionCSV returnServicioGestionCSV = LocalizadorServicios
					.getServicioGestionCSV(sbImpl.toString());
			if (logger.isDebugEnabled()) {
				logger.debug("getServicioGestionCSV() - end");
			}
			return returnServicioGestionCSV;
		}
	}

	/**
	 * Genera el CSV de un documento y almacena la información en el modelo de
	 * datos.
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param infoDocumentoForm
	 *            Información del documento
	 * @return Información del documento con el CSV
	 */
	public InfoDocumentoCSVRetorno generarCSV(Entidad entidad,
			InfoDocumentoCSVFormDTO infoDocumentoForm) {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCSV(Entidad, InfoDocumentoCSVFormDTO) - start. Entidad: ["
					+ entidad.getIdentificador() + "] infoDocumentoForm: ["
					+ infoDocumentoForm.toString() + "]");
		}

		InfoDocumentoCSV infoDocumentoCSV;
		InfoDocumentoCSVRetorno infoDocumentoCSVRetorno = new InfoDocumentoCSVRetorno();
		try {
			infoDocumentoCSV = getServicioGestionCSV().generarCSV(entidad,
					getInfoDocumentoCSVForm(infoDocumentoForm));
			infoDocumentoCSVRetorno = getInfoDocumentoCSVRetorno(infoDocumentoCSV);

		} catch (SigemException e) {
			logger.error("SigemException generarCSV(Entidad, InfoDocumentoCSVFormDTO)", e);

			InfoDocumentoCSVRetorno returnInfoDocumentoCSVRetorno = (InfoDocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(infoDocumentoCSVRetorno, e.getErrorCode());
			if (logger.isDebugEnabled()) {
				logger.debug("generarCSV(Entidad, InfoDocumentoCSVFormDTO) - end. Generado el CSV satisfactoriamente");
			}
			return returnInfoDocumentoCSVRetorno;
		} catch (Throwable e) {
			logger.error("generarCSV(Entidad, InfoDocumentoCSVFormDTO)", e);

			InfoDocumentoCSVRetorno returnInfoDocumentoCSVRetorno = (InfoDocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(infoDocumentoCSVRetorno);
			if (logger.isDebugEnabled()) {
				logger.debug("generarCSV(Entidad, InfoDocumentoCSVFormDTO) - end");
			}
			return (InfoDocumentoCSVRetorno) ServiciosUtils.completeReturnOK(returnInfoDocumentoCSVRetorno);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("generarCSV(Entidad, InfoDocumentoCSVFormDTO) - end");
		}
		return (InfoDocumentoCSVRetorno) ServiciosUtils.completeReturnOK(infoDocumentoCSVRetorno);
	}

	/**
	 * Obtiene la información de un documento a partir de su CSV.
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param csv
	 *            Código Seguro de Verificación asociado a dicho documento
	 * @return Información del documento con el CSV
	 */
	public InfoDocumentoCSVRetorno getInfoDocumentoByCSV(Entidad entidad, String csv) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoByCSV(Entidad, String) - start. Entidad: ["
					+ entidad.getIdentificador() + "] CSV: [" + csv + "]");
		}

		InfoDocumentoCSV infoDocumentoCSV;
		InfoDocumentoCSVRetorno infoDocumentoCSVRetorno = new InfoDocumentoCSVRetorno();
		try {
			infoDocumentoCSV = getServicioGestionCSV().getInfoDocumentoByCSV(entidad, csv);
			infoDocumentoCSVRetorno = getInfoDocumentoCSVRetorno(infoDocumentoCSV);
		} catch (SOAPException e) {
			logger.error("SOAPException: getInfoDocumentoByCSV(Entidad, String)", e);

			InfoDocumentoCSVRetorno returnInfoDocumentoCSVRetorno = (InfoDocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(infoDocumentoCSVRetorno);
			if (logger.isDebugEnabled()) {
				logger.debug("getInfoDocumentoByCSV(Entidad, String) - end");
			}
			return returnInfoDocumentoCSVRetorno;
		} catch (SigemException e) {
			logger.error("SigemException getInfoDocumentoByCSV(Entidad, String)", e);

			InfoDocumentoCSVRetorno returnInfoDocumentoCSVRetorno = (InfoDocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(infoDocumentoCSVRetorno, e.getErrorCode());
			if (logger.isDebugEnabled()) {
				logger.debug("getInfoDocumentoByCSV(Entidad, String) - end");
			}
			return returnInfoDocumentoCSVRetorno;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoByCSV(Entidad, String) - end");
		}
		return (InfoDocumentoCSVRetorno) ServiciosUtils.completeReturnOK(infoDocumentoCSVRetorno);
	}

	/**
	 * Obtiene la información de un documento, incluido su contenido, a partir
	 * de su CSV.
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param csv
	 *            Código Seguro de Verificación asociado a dicho documento
	 * @return Información del documento con el CSV y su contenido
	 */
	public DocumentoCSVRetorno getDocumentoByCSV(Entidad entidad, String csv) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoByCSV(Entidad, String) - Entidad: ["
					+ entidad.getIdentificador() + "] CSV: [" + csv + "]");
		}

		DocumentoCSV documentoCSV;
		DocumentoCSVRetorno documentoCSVRetorno = new DocumentoCSVRetorno();
		try {
			documentoCSV = getServicioGestionCSV().getDocumentoByCSV(entidad, csv);
			documentoCSVRetorno = getDocumentoCSVRetorno(documentoCSV);
		} catch (SOAPException e) {
			logger.error("getDocumentoByCSV(Entidad, String)", e);

			DocumentoCSVRetorno returnDocumentoCSVRetorno = (DocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(documentoCSVRetorno);
			if (logger.isDebugEnabled()) {
				logger.debug("getDocumentoByCSV(Entidad, String) - end");
			}
			return returnDocumentoCSVRetorno;
		} catch (SigemException e) {
			logger.error("getDocumentoByCSV(Entidad, String)", e);

			DocumentoCSVRetorno returnDocumentoCSVRetorno = (DocumentoCSVRetorno) ServiciosUtils
					.completeReturnError(documentoCSVRetorno, e.getErrorCode());
			if (logger.isDebugEnabled()) {
				logger.debug("getDocumentoByCSV(Entidad, String) - end");
			}
			return returnDocumentoCSVRetorno;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoByCSV(Entidad, String) - end");
		}
		return (DocumentoCSVRetorno) ServiciosUtils.completeReturnOK(documentoCSVRetorno);
	}

	/**
	 * Elimina la información de un documento
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 */
	public RetornoServicio deleteInfoDocumento(Entidad entidad, String id) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteInfoDocumento(Entidad entidad, String id) - start Entidad: ["
					+ entidad.getIdentificador() + "] ID: [" + id + "]");
		}

		try {
			getServicioGestionCSV().deleteInfoDocumento(entidad, id);
			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnOK();
			if (logger.isDebugEnabled()) {
				logger.debug("deleteInfoDocumento(Entidad, String) - end");
			}
			return returnRetornoServicio;
		} catch (SOAPException e) {
			logger.error("deleteInfoDocumento(Entidad, String)", e);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnError();
			if (logger.isDebugEnabled()) {
				logger.debug("deleteInfoDocumento(Entidad, String) - end");
			}
			return returnRetornoServicio;
		} catch (SigemException e) {
			logger.error("SigemException deleteInfoDocumento(Entidad, String)", e);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnError();
			if (logger.isDebugEnabled()) {
				logger.debug("deleteInfoDocumento(Entidad, String) - end");
			}
			return returnRetornoServicio;
		}
	}

	/**
	 * Comprueba si el contenido del documento se puede descargar de la
	 * aplicación externa
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @return true: Si existe el contenido del documento. false: Si no existe.
	 */
	public BooleanRetorno existeContenidoDocumento(Entidad entidad, String id) {
		if (logger.isDebugEnabled()) {
			logger.debug("existeContenidoDocumento(Entidad, String) - start Entidad: ["
					+ entidad.getIdentificador() + "] ID: [" + id + "]");
		}

		boolean existe = false;
		BooleanRetorno retorno = new BooleanRetorno();
		try {
			existe = getServicioGestionCSV().existeContenidoDocumento(entidad, id);
			retorno.setValor(existe);
		} catch (SOAPException e) {
			logger.error("existeContenidoDocumento(Entidad, String)", e);

			BooleanRetorno returnBooleanRetorno = (BooleanRetorno) ServiciosUtils
					.completeReturnError(retorno);
			if (logger.isDebugEnabled()) {
				logger.debug("existeContenidoDocumento(Entidad, String) - end");
			}
			return returnBooleanRetorno;
		} catch (SigemException e) {
			logger.error("existeContenidoDocumento(Entidad, String)", e);

			BooleanRetorno returnBooleanRetorno = (BooleanRetorno) ServiciosUtils
					.completeReturnError(retorno, e.getErrorCode());
			if (logger.isDebugEnabled()) {
				logger.debug("existeContenidoDocumento(Entidad, String) - end");
			}
			return returnBooleanRetorno;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existeContenidoDocumento(Entidad, String) - end");
		}
		return (BooleanRetorno) ServiciosUtils.completeReturnOK(retorno);
	}

	/**
	 * Obtiene el contenido de un documento a partir de su identificador
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @return Contenido del documento.
	 */
	public StringB64 getContenidoDocumento(Entidad entidad, String id) {
		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(Entidad, String) - start Entidad: ["
					+ entidad.getIdentificador() + "] ID: [" + id + "]");
		}

		byte[] contenido = null;
		StringB64 byteArray = new StringB64();
		try {
			contenido = getServicioGestionCSV().getContenidoDocumento(entidad, id);
			byteArray = this.getStringB64WS(contenido);

		} catch (SOAPException e) {
			logger.error("getContenidoDocumento(Entidad, String)", e);

			StringB64 returnStringB64 = (StringB64) ServiciosUtils.completeReturnError(byteArray);
			if (logger.isDebugEnabled()) {
				logger.debug("getContenidoDocumento(Entidad, String) - end");
			}
			return returnStringB64;
		} catch (SigemException e) {
			logger.error("SigemException getContenidoDocumento(Entidad, String)", e);

			StringB64 returnStringB64 = (StringB64) ServiciosUtils.completeReturnError(byteArray,
					e.getErrorCode());
			if (logger.isDebugEnabled()) {
				logger.debug("getContenidoDocumento(Entidad, String) - end");
			}
			return returnStringB64;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(Entidad, String) - end");
		}
		return (StringB64) ServiciosUtils.completeReturnOK(byteArray);
	}

	/**
	 * Escribe el contenido de un documento a partir del identificador en el
	 * OutputStrem
	 *
	 * @param entidad
	 *            Entidad sobre la que estamos trabajando
	 * @param id
	 *            Identificador del documento
	 * @param outputStream
	 *            OutputStream sobre el que escribiremos el contenido del
	 *            documento
	 *
	 */

	public RetornoServicio writeDocumento(Entidad entidad, String id, String stringB64Encoded) {
		if (logger.isDebugEnabled()) {
			logger.debug("writeDocumento(Entidad, String, String) - start Entidad: ["
					+ entidad.getIdentificador() + "] ID: [" + id + "]");
		}

		try {
			ByteArrayOutputStream outputStream = this.getOS(stringB64Encoded);
			getServicioGestionCSV().writeDocumento(entidad, id, outputStream);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnOK();
			if (logger.isDebugEnabled()) {
				logger.debug("writeDocumento(Entidad, String, String) - end");
			}
			return returnRetornoServicio;
		} catch (SOAPException e) {
			logger.error("writeDocumento(Entidad, String, String)", e);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnError();
			if (logger.isDebugEnabled()) {
				logger.debug("writeDocumento(Entidad, String, String) - end");
			}
			return returnRetornoServicio;
		} catch (SigemException e) {
			logger.error("writeDocumento(Entidad, String, String)", e);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnError();
			if (logger.isDebugEnabled()) {
				logger.debug("writeDocumento(Entidad, String, String) - end");
			}
			return returnRetornoServicio;
		} catch (IOException e) {
			logger.error("writeDocumento(Entidad, String, String)", e);

			RetornoServicio returnRetornoServicio = ServiciosUtils.createReturnError();
			if (logger.isDebugEnabled()) {
				logger.debug("writeDocumento(Entidad, String, String) - end");
			}
			return returnRetornoServicio;
		}

	}

	private ByteArrayOutputStream getOS(String stringB64) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOS(String) - start");
		}

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(stringB64);
		bo.write(decodedBytes);

		if (logger.isDebugEnabled()) {
			logger.debug("getOS(String) - end");
		}
		return bo;
	}

	private StringB64 getStringB64WS(byte[] poStr) {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64WS(byte[]) - start");
		}

		if (poStr == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64WS(byte[]) - end");
			}
			return null;
		}
		StringB64 oStr = new StringB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oStr.setStringB64(encoder.encode(poStr));

		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64WS(byte[]) - end");
		}
		return oStr;
	}

	private ByteArrayB64 getByteArrayB64WS(ByteArrayOutputStream poByteArray) {
		if (logger.isDebugEnabled()) {
			logger.debug("getByteArrayB64WS(ByteArrayOutputStream) - start");
		}

		if (poByteArray == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getByteArrayB64WS(ByteArrayOutputStream) - end");
			}
			return null;
		}
		ByteArrayB64 oByteArray = new ByteArrayB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oByteArray.setByteArrayB64(encoder.encode(poByteArray.toByteArray()));

		if (logger.isDebugEnabled()) {
			logger.debug("getByteArrayB64WS(ByteArrayOutputStream) - end");
		}
		return oByteArray;
	}

	private byte[] getStringB64Servicio(StringB64 poStr) {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64Servicio(StringB64) - start");
		}

		if (poStr == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64Servicio(StringB64) - end");
			}
			return null;
		}

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] oStr = decoder.decodeBuffer(poStr.getStringB64());

			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64Servicio(StringB64) - end");
			}
			return oStr;
		} catch (Exception e) {
			logger.error("getStringB64Servicio(StringB64)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64Servicio(StringB64) - end");
			}
			return null;
		}
	}

	private InfoDocumentoCSVRetorno getInfoDocumentoCSVRetorno(InfoDocumentoCSV doc) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVRetorno(InfoDocumentoCSV) - start");
		}

		InfoDocumentoCSVRetorno docRetorno = new InfoDocumentoCSVRetorno();
		if (doc != null) {
			docRetorno.setCsv(doc.getCsv());
			docRetorno.setFechaCSV(doc.getFechaCSV());
			docRetorno.setCodigoAplicacion(doc.getCodigoAplicacion());
			docRetorno.setDisponible(doc.isDisponible());
			docRetorno.setFechaCaducidad(doc.getFechaCaducidad());
			docRetorno.setFechaCreacion(doc.getFechaCreacion());
			docRetorno.setId(doc.getId());
			docRetorno.setNombre(doc.getNombre());
			docRetorno.setNombreAplicacion(doc.getNombreAplicacion());
			docRetorno.setTipoMime(doc.getTipoMime());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVRetorno(InfoDocumentoCSV) - end");
		}
		return docRetorno;
	}

	private DocumentoCSVRetorno getDocumentoCSVRetorno(DocumentoCSV doc) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSVRetorno(DocumentoCSV) - start");
		}

		DocumentoCSVRetorno docRetorno = new DocumentoCSVRetorno();

		if (doc != null) {
			// TODO:Comprobar que esta herencia esté bien
			docRetorno = (DocumentoCSVRetorno) getInfoDocumentoCSVRetorno((InfoDocumentoCSV) doc);
			docRetorno.setContenido(doc.getContenido());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSVRetorno(DocumentoCSV) - end");
		}
		return docRetorno;
	}

	private InfoDocumentoCSVForm getInfoDocumentoCSVForm(
			InfoDocumentoCSVFormDTO infoDocumentoCSVFormDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVForm(InfoDocumentoCSVFormDTO) - start");
		}

		InfoDocumentoCSVForm infoDocumentoCSVForm = new InfoDocumentoCSVForm();
		if (infoDocumentoCSVFormDTO != null) {
			infoDocumentoCSVForm.setCodigoAplicacion(infoDocumentoCSVFormDTO.getCodigoAplicacion());
			infoDocumentoCSVForm.setDisponible(infoDocumentoCSVFormDTO.isDisponible());
			infoDocumentoCSVForm.setFechaCaducidad(infoDocumentoCSVFormDTO.getFechaCaducidad());
			infoDocumentoCSVForm.setFechaCreacion(infoDocumentoCSVFormDTO.getFechaCreacion());
			infoDocumentoCSVForm.setNombre(infoDocumentoCSVFormDTO.getNombre());
			infoDocumentoCSVForm.setTipoMime(infoDocumentoCSVFormDTO.getTipoMime());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVForm(InfoDocumentoCSVFormDTO) - end");
		}
		return infoDocumentoCSVForm;
	}

}
