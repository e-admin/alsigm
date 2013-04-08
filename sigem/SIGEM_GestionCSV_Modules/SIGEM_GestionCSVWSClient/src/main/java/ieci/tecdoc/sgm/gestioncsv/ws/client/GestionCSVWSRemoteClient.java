/**
 *
 */
package ieci.tecdoc.sgm.gestioncsv.ws.client;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.gestioncsv.CSVException;
import ieci.tecdoc.sgm.core.services.gestioncsv.DocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.BooleanRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.DocumentoCSVRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.GestionCSVWebService_PortType;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.InfoDocumentoCSVFormDTO;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.InfoDocumentoCSVRetorno;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.RetornoServicio;
import ieci.tecdoc.sgm.gestioncsv.ws.client.axis.StringB64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class GestionCSVWSRemoteClient implements ServicioGestionCSV {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GestionCSVWSRemoteClient.class);

	private GestionCSVWebService_PortType service;

	/**
	 * @return el service
	 */
	public GestionCSVWebService_PortType getService() {
		if (logger.isDebugEnabled()) {
			logger.debug("getService() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getService() - end");
		}
		return service;
	}

	/**
	 * @param service
	 *            el service a fijar
	 */
	public void setService(GestionCSVWebService_PortType service) {
		if (logger.isDebugEnabled()) {
			logger.debug("setService(GestionCSVWebService_PortType) - start");
		}

		this.service = service;

		if (logger.isDebugEnabled()) {
			logger.debug("setService(GestionCSVWebService_PortType) - end");
		}
	}

	private InfoDocumentoCSVFormDTO getInfoDocumentoCSVFormDTO(InfoDocumentoCSVForm infoDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVFormDTO(InfoDocumentoCSVForm) - start");
		}

		InfoDocumentoCSVFormDTO infoDocDTO = new InfoDocumentoCSVFormDTO();

		if (infoDoc != null) {
			infoDocDTO.setCodigoAplicacion(infoDoc.getCodigoAplicacion());
			infoDocDTO.setDisponible(infoDoc.isDisponible());
			if (infoDoc.getFechaCaducidad() != null) {
				Calendar fechaCaducidad = new GregorianCalendar();
				fechaCaducidad.setTime(infoDoc.getFechaCaducidad());

				infoDocDTO.setFechaCaducidad(fechaCaducidad);
			}
			if (infoDoc.getFechaCreacion() != null) {
				Calendar fechaCreacion = new GregorianCalendar();
				fechaCreacion.setTime(infoDoc.getFechaCreacion());
				infoDocDTO.setFechaCreacion(fechaCreacion);

			}
			infoDocDTO.setNombre(infoDoc.getNombre());

			infoDocDTO.setTipoMime(infoDoc.getTipoMime());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSVFormDTO(InfoDocumentoCSVForm) - end");
		}
		return infoDocDTO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#generarCSV(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm)
	 */
	public InfoDocumentoCSV generarCSV(Entidad entidad, InfoDocumentoCSVForm infoDocumentoForm)
			throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCSV(Entidad, InfoDocumentoCSVForm) - start");
		}

		InfoDocumentoCSV infoDocumentoCSV;

		try {
			InfoDocumentoCSVRetorno infoDocumentoCSVRetorno = getService().generarCSV(
					getEntidadWS(entidad), getInfoDocumentoCSVFormDTO(infoDocumentoForm));
			if (ServiciosUtils.isReturnOK((IRetornoServicio) infoDocumentoCSVRetorno)) {
				infoDocumentoCSV = getInfoDocumentoCSV(infoDocumentoCSVRetorno);

				if (logger.isDebugEnabled()) {
					logger.debug("generarCSV(Entidad, InfoDocumentoCSVForm) - end");
				}
				return infoDocumentoCSV;
			} else {
				throw getCSVException((IRetornoServicio) infoDocumentoCSVRetorno);
			}

		} catch (RemoteException e) {
			logger.error("generarCSV(Entidad, InfoDocumentoCSVForm)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getInfoDocumentoByCSV(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(Entidad entidad, String csv) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoByCSV(Entidad, String) - start");
		}

		InfoDocumentoCSV infoDocumentoCSV;

		try {
			InfoDocumentoCSVRetorno infoDocumentoCSVRetorno = getService().getInfoDocumentoByCSV(
					getEntidadWS(entidad), csv);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) infoDocumentoCSVRetorno)) {
				infoDocumentoCSV = getInfoDocumentoCSV(infoDocumentoCSVRetorno);

				if (logger.isDebugEnabled()) {
					logger.debug("getInfoDocumentoByCSV(Entidad, String) - end");
				}
				return infoDocumentoCSV;
			} else {
				throw getCSVException((IRetornoServicio) infoDocumentoCSVRetorno);
			}

		} catch (RemoteException e) {
			logger.error("getInfoDocumentoByCSV(Entidad, String)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getDocumentoByCSV(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String)
	 */
	public DocumentoCSV getDocumentoByCSV(Entidad entidad, String csv) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoByCSV(Entidad, String) - start");
		}

		DocumentoCSV documentoCSV;

		try {
			DocumentoCSVRetorno documentoCSVRetorno = getService().getDocumentoByCSV(
					getEntidadWS(entidad), csv);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) documentoCSVRetorno)) {
				documentoCSV = getDocumentoCSV(documentoCSVRetorno);

				if (logger.isDebugEnabled()) {
					logger.debug("getDocumentoByCSV(Entidad, String) - end");
				}
				return documentoCSV;
			} else {
				throw getCSVException((IRetornoServicio) documentoCSVRetorno);
			}
		} catch (RemoteException e) {
			logger.error("getDocumentoByCSV(Entidad, String)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#deleteInfoDocumento(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String)
	 */
	public void deleteInfoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteInfoDocumento(Entidad, String) - start");
		}

		try {
			ieci.tecdoc.sgm.gestioncsv.ws.client.axis.RetornoServicio retornoServicio = getService()
					.deleteInfoDocumento(getEntidadWS(entidad), id);

			if (!ServiciosUtils.isReturnOK((IRetornoServicio) retornoServicio)) {
				throw getCSVException((IRetornoServicio) retornoServicio);
			}

		} catch (RemoteException e) {
			logger.error("deleteInfoDocumento(Entidad, String)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteInfoDocumento(Entidad, String) - end");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#existeContenidoDocumento(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String)
	 */
	public boolean existeContenidoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("existeContenidoDocumento(Entidad, String) - start");
		}

		try {
			BooleanRetorno booleanRetorno = getService().existeContenidoDocumento(
					getEntidadWS(entidad), id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) booleanRetorno)) {

				boolean returnboolean = booleanRetorno.isValor();
				if (logger.isDebugEnabled()) {
					logger.debug("existeContenidoDocumento(Entidad, String) - end");
				}
				return returnboolean;
			} else {
				throw getCSVException((IRetornoServicio) booleanRetorno);
			}
		} catch (RemoteException e) {
			logger.error("existeContenidoDocumento(Entidad, String)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getContenidoDocumento(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String)
	 */
	public byte[] getContenidoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(Entidad, String) - start");
		}

		try {
			StringB64 stringB64 = getService().getContenidoDocumento(getEntidadWS(entidad), id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) stringB64)) {

				byte[] contenido = getStringB64Servicio(stringB64);

				if (logger.isDebugEnabled()) {
					logger.debug("getContenidoDocumento(Entidad, String) - end");
				}
				return contenido;

			} else {
				throw getCSVException((IRetornoServicio) stringB64);
			}

		} catch (RemoteException e) {
			logger.error("getContenidoDocumento(Entidad, String)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#writeDocumento(ieci.tecdoc.sgm.core.services.dto.Entidad,
	 *      java.lang.String, java.io.OutputStream)
	 */
	public void writeDocumento(Entidad entidad, String id, OutputStream outputStream)
			throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("writeDocumento(Entidad, String, OutputStream) - start");
		}

		String outputStreamEncoded = getString(outputStream);

		try {
			RetornoServicio retornoServicio = getService().writeDocumento(getEntidadWS(entidad),
					id, outputStreamEncoded);
			if (!ServiciosUtils.isReturnOK((IRetornoServicio) retornoServicio)) {
				throw getCSVException((IRetornoServicio) retornoServicio);
			} else {
				outputStream.write(outputStreamEncoded.getBytes());
			}
		} catch (RemoteException e) {
			logger.error("writeDocumento(Entidad, String, OutputStream)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		} catch (IOException e) {
			logger.error("writeDocumento(Entidad, String, OutputStream)", e);

			throw new CSVException(CSVException.EXC_GENERIC_EXCEPCION, e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("writeDocumento(Entidad, String, OutputStream) - end");
		}
	}

	private String getString(OutputStream output) {
		if (logger.isDebugEnabled()) {
			logger.debug("getString(OutputStream) - start");
		}

		String string = output.toString();
		BASE64Encoder encoder = new BASE64Encoder();
		String returnString = encoder.encode(string.getBytes());
		if (logger.isDebugEnabled()) {
			logger.debug("getString(OutputStream) - end");
		}
		return returnString;

	}

	private ByteArrayOutputStream getByteArrayB64Servicio(StringB64 poByteArrayB64) {
		if (logger.isDebugEnabled()) {
			logger.debug("getByteArrayB64Servicio(StringB64) - start");
		}

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(decoder.decodeBuffer(poByteArrayB64.getStringB64()));

			if (logger.isDebugEnabled()) {
				logger.debug("getByteArrayB64Servicio(StringB64) - end");
			}
			return baos;
		} catch (Exception e) {
			logger.error("getByteArrayB64Servicio(StringB64)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("getByteArrayB64Servicio(StringB64) - end");
			}
			return null;
		}
	}

	private byte[] getStringB64Servicio(StringB64 poStringB64) {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64Servicio(StringB64) - start");
		}

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] returnbyteArray = decoder.decodeBuffer(poStringB64.getStringB64());
			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64Servicio(StringB64) - end");
			}
			return returnbyteArray;
		} catch (Exception e) {
			logger.error("getStringB64Servicio(StringB64)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("getStringB64Servicio(StringB64) - end");
			}
			return null;
		}
	}

	private StringB64 getStringB64WS(byte[] poStringB64) {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64WS(byte[]) - start");
		}

		StringB64 stringB64 = null;
		if (poStringB64 != null) {
			BASE64Encoder encoder = new BASE64Encoder();
			stringB64 = new StringB64();
			stringB64.setStringB64(encoder.encode(poStringB64));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getStringB64WS(byte[]) - end");
		}
		return stringB64;
	}

	private CSVException getCSVException(IRetornoServicio oReturn) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCSVException(IRetornoServicio) - start");
		}

		CSVException returnCSVException = new CSVException(Long.valueOf(oReturn.getErrorCode())
				.longValue());
		if (logger.isDebugEnabled()) {
			logger.debug("getCSVException(IRetornoServicio) - end");
		}
		return returnCSVException;
	}

	private InfoDocumentoCSV getInfoDocumentoCSV(InfoDocumentoCSVRetorno documentoCSVRetorno) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSV(InfoDocumentoCSVRetorno) - start");
		}

		InfoDocumentoCSV doc = new InfoDocumentoCSV();
		if (documentoCSVRetorno != null) {
			doc.setCsv(documentoCSVRetorno.getCsv());
			doc.setFechaCSV(documentoCSVRetorno.getFechaCSV().getTime());
			doc.setCodigoAplicacion(documentoCSVRetorno.getCodigoAplicacion());
			doc.setDisponible(documentoCSVRetorno.isDisponible());
			if (documentoCSVRetorno.getFechaCaducidad() != null) {
				doc.setFechaCaducidad(documentoCSVRetorno.getFechaCaducidad().getTime());
			}
			doc.setFechaCreacion(documentoCSVRetorno.getFechaCreacion().getTime());
			doc.setId(documentoCSVRetorno.getId());
			doc.setNombre(documentoCSVRetorno.getNombre());
			doc.setNombreAplicacion(documentoCSVRetorno.getNombreAplicacion());
			doc.setTipoMime(documentoCSVRetorno.getTipoMime());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoCSV(InfoDocumentoCSVRetorno) - end");
		}
		return doc;
	}

	private DocumentoCSV getDocumentoCSV(DocumentoCSVRetorno doc) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSV(DocumentoCSVRetorno) - start");
		}

		DocumentoCSV documentoCSV = new DocumentoCSV();

		if (doc != null) {
			// TODO:Comprobar que esta herencia esté bien
			documentoCSV = (DocumentoCSV) getInfoDocumentoCSV((InfoDocumentoCSVRetorno) doc);
			documentoCSV.setContenido(doc.getContenido());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSV(DocumentoCSVRetorno) - end");
		}
		return documentoCSV;
	}

	private ieci.tecdoc.sgm.gestioncsv.ws.client.axis.Entidad getEntidadWS(Entidad poEntidad) {
		if (logger.isDebugEnabled()) {
			logger.debug("getEntidadWS(Entidad) - start");
		}

		if (poEntidad == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getEntidadWS(Entidad) - end");
			}
			return null;
		}
		ieci.tecdoc.sgm.gestioncsv.ws.client.axis.Entidad oEntidad = new ieci.tecdoc.sgm.gestioncsv.ws.client.axis.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());

		if (logger.isDebugEnabled()) {
			logger.debug("getEntidadWS(Entidad) - end");
		}
		return oEntidad;
	}

}
