/**
 *
 */
package ieci.tecdoc.sgm.gestioncsv;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestioncsv.CSVException;
import ieci.tecdoc.sgm.core.services.gestioncsv.DocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSV;
import ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;

import java.io.OutputStream;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;

/**
 * @author IECISA
 *
 *         Implementación local del servicio para la generación y gestión de
 *         CSV (Código Seguro de Verificación) de SIGEM.
 *
 */
public class ServicioGestionCSVAdapter implements ServicioGestionCSV {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ServicioGestionCSVAdapter.class);

	/**
	 * Servicio de gestión de documentos del módulo fwktd-csv
	 */
	private ServicioDocumentos servicioDocumentos;

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#generarCSV(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      ieci.tecdoc.sgm.core.services.gestioncsv.InfoDocumentoCSVForm)
	 */
	public InfoDocumentoCSV generarCSV(Entidad entidad, InfoDocumentoCSVForm infoDocumentoForm)
			throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarCSV(Entidad, InfoDocumentoCSVForm) - start");
		}

		/*
		 * Es necesario introducir el identificador de la entidad en el objeto
		 * MultiEntityContextHolder para hacer utilizar el DataSource asociado a
		 * dicha entidad
		 */
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm infoDocumentoFWKTDForm = new es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm();
		infoDocumentoFWKTDForm = getInfoDocFWKTDForm(infoDocumentoForm);

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV infoDocFWKTDCSV;
		try {
			infoDocFWKTDCSV = servicioDocumentos.generarCSV(infoDocumentoFWKTDForm);
		} catch (Exception e) {
			logger.error("Error al generarCSV" + " Entidad: " + entidad.getIdentificador(), e);
			throw new CSVException("", e);
		}

		InfoDocumentoCSV infoDocCSV = getInfoDocFWKTDCSV(infoDocFWKTDCSV);

		if (logger.isDebugEnabled()) {
			logger.debug("generarCSV(Entidad, InfoDocumentoCSVForm) - end");
		}
		return infoDocCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getInfoDocumentoByCSV(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.lang.String)
	 */
	public InfoDocumentoCSV getInfoDocumentoByCSV(Entidad entidad, String csv) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoByCSV(Entidad, String) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV infoDocFWKTDCSV;
		try {
			infoDocFWKTDCSV = servicioDocumentos.getInfoDocumentoByCSV(csv);
		} catch (Exception e) {
			logger.error("Error al obtener la información del documento por el csv: " + csv
					+ " Entidad: " + entidad.getIdentificador(), e);
			throw new CSVException("", e);
		}

		InfoDocumentoCSV infoDocCSV = getInfoDocFWKTDCSV(infoDocFWKTDCSV);

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocumentoByCSV(Entidad, String) - end");
		}
		return infoDocCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getDocumentoByCSV(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.lang.String)
	 */
	public DocumentoCSV getDocumentoByCSV(Entidad entidad, String csv) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoByCSV(Entidad, String) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV documentoFWKTD;
		try {
			documentoFWKTD = servicioDocumentos.getDocumentoByCSV(csv);
		} catch (Exception e) {
			logger.error("Error al obtener un documento por el csv: " + csv + " Entidad: "
					+ entidad.getIdentificador(), e);
			throw new CSVException("Error al obtener un documento por el csv", e);
		}

		DocumentoCSV documentoCSV = getDocumentoCSV(documentoFWKTD);

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoByCSV(Entidad, String) - end");
		}
		return documentoCSV;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#deleteInfoDocumento(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.lang.String)
	 */
	public void deleteInfoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteInfoDocumento(Entidad, String) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());
		try {
			servicioDocumentos.deleteInfoDocumento(id);
		} catch (Exception e) {
			logger.error(
					"Error al intentar eliminar el documento: " + id + " Entidad: "
							+ entidad.getIdentificador(), e);
			throw new CSVException("Error al intentar eliminar el documento", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteInfoDocumento(Entidad, String) - end");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#existeContenidoDocumento(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.lang.String)
	 */
	public boolean existeContenidoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("existeContenidoDocumento(Entidad, String) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		boolean returnboolean = false;

		try {
			returnboolean = servicioDocumentos.existeContenidoDocumento(id);
		} catch (Exception e) {
			logger.error("Error al comprobar si existe contenido del documento: " + id
					+ " Entidad: " + entidad.getIdentificador(), e);
			throw new CSVException("Error al comprobar si existe contenido del documento", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existeContenidoDocumento(Entidad, String) - end");
		}
		return returnboolean;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#getContenidoDocumento(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.lang.String)
	 */
	public byte[] getContenidoDocumento(Entidad entidad, String id) throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(Entidad, String) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		byte[] returnbyteArray;
		try {
			returnbyteArray = servicioDocumentos.getContenidoDocumento(id);
		} catch (Exception e) {
			logger.error("Error al obtener el contenido del documento: " + id + " Entidad: "
					+ entidad.getIdentificador(), e);
			throw new CSVException("Error al obtener el contenido del documento", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(Entidad, String) - end");
		}
		return returnbyteArray;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws CSVException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV#writeDocumento(ieci.tecdoc.sgm.core.services.entidades.Entidad,
	 *      java.io.OutputStream)
	 */
	public void writeDocumento(Entidad entidad, String id, OutputStream outputStream)
			throws CSVException {
		if (logger.isDebugEnabled()) {
			logger.debug("writeDocumento(Entidad, String, OutputStream) - start");
		}

		MultiEntityContextHolder.setEntity(entidad.getIdentificador());
		try {
			servicioDocumentos.writeDocumento(id, outputStream);
		} catch (Exception e) {
			logger.error(
					"Error al escribir el documento: " + id + " Entidad: "
							+ entidad.getIdentificador(), e);
			throw new CSVException("Error al escribir el documento", e);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("writeDocumento(Entidad, String, OutputStream) - end");
		}
	}

	/**
	 * @return el servicioDocumentos
	 */
	public ServicioDocumentos getServicioDocumentos() {
		return servicioDocumentos;
	}

	/**
	 * @param servicioDocumentos
	 *            el servicioDocumentos a fijar
	 */
	public void setServicioDocumentos(ServicioDocumentos servicioDocumentos) {
		this.servicioDocumentos = servicioDocumentos;
	}

	/**
	 * @param documentoFWKTD
	 */
	private DocumentoCSV getDocumentoCSV(
			es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV documentoFWKTD) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSV(es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV) - start");
		}

		DocumentoCSV documentoCSV = new DocumentoCSV();

		documentoCSV.setCodigoAplicacion(documentoFWKTD.getCodigoAplicacion());
		documentoCSV.setContenido(documentoFWKTD.getContenido());
		documentoCSV.setCsv(documentoFWKTD.getCsv());
		documentoCSV.setDisponible(documentoFWKTD.isDisponible());
		documentoCSV.setFechaCaducidad(documentoFWKTD.getFechaCaducidad());
		documentoCSV.setFechaCreacion(documentoFWKTD.getFechaCreacion());
		documentoCSV.setFechaCSV(documentoFWKTD.getFechaCSV());
		documentoCSV.setId(documentoFWKTD.getId());
		documentoCSV.setNombre(documentoFWKTD.getNombre());
		documentoCSV.setNombreAplicacion(documentoFWKTD.getNombreAplicacion());
		documentoCSV.setTipoMime(documentoFWKTD.getTipoMime());

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentoCSV(es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV) - end");
		}
		return documentoCSV;
	}

	/**
	 * @param infoDocFWKTDCSV
	 * @return
	 */
	private InfoDocumentoCSV getInfoDocFWKTDCSV(
			es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV infoDocFWKTDCSV) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocFWKTDCSV(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV) - start");
		}

		InfoDocumentoCSV infoDocCSV = new InfoDocumentoCSV();

		infoDocCSV.setCodigoAplicacion(infoDocFWKTDCSV.getCodigoAplicacion());
		infoDocCSV.setCsv(infoDocFWKTDCSV.getCsv());
		infoDocCSV.setDisponible(infoDocFWKTDCSV.isDisponible());
		infoDocCSV.setFechaCaducidad(infoDocFWKTDCSV.getFechaCaducidad());
		infoDocCSV.setFechaCreacion(infoDocFWKTDCSV.getFechaCreacion());
		infoDocCSV.setFechaCSV(infoDocFWKTDCSV.getFechaCSV());
		infoDocCSV.setId(infoDocFWKTDCSV.getId());
		infoDocCSV.setNombre(infoDocFWKTDCSV.getNombre());
		infoDocCSV.setNombreAplicacion(infoDocFWKTDCSV.getNombreAplicacion());
		infoDocCSV.setTipoMime(infoDocFWKTDCSV.getTipoMime());

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocFWKTDCSV(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV) - end");
		}
		return infoDocCSV;
	}

	/**
	 * @param infoDocumentoForm
	 * @param infoDocumentoFWKTDForm
	 */
	private es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm getInfoDocFWKTDForm(
			InfoDocumentoCSVForm infoDocumentoForm) {
		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocFWKTDForm(InfoDocumentoCSVForm) - start");
		}

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm infoDocumentoFWKTDForm = new es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm();
		infoDocumentoFWKTDForm.setCodigoAplicacion(infoDocumentoForm.getCodigoAplicacion());
		infoDocumentoFWKTDForm.setDisponible(infoDocumentoForm.isDisponible());
		infoDocumentoFWKTDForm.setFechaCaducidad(infoDocumentoForm.getFechaCaducidad());
		infoDocumentoFWKTDForm.setFechaCreacion(infoDocumentoForm.getFechaCreacion());
		infoDocumentoFWKTDForm.setNombre(infoDocumentoForm.getNombre());
		infoDocumentoFWKTDForm.setTipoMime(infoDocumentoForm.getTipoMime());

		if (logger.isDebugEnabled()) {
			logger.debug("getInfoDocFWKTDForm(InfoDocumentoCSVForm) - end");
		}
		return infoDocumentoFWKTDForm;
	}

}
