package es.ieci.tecdoc.fwktd.dir3.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.exception.ObtencionFicheroActualizacionDCOException;
import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerActualizacionesDCO;
import es.ieci.tecdoc.fwktd.dir3.util.Base64Utils;
import es.ieci.tecdoc.fwktd.dir3.util.ZipUtils;
import es.map.directorio.manager.impl.SC02UNIncrementalDatosBasicos;
import es.map.directorio.manager.impl.SC12OFIncrementalDatosBasicos;
import es.map.directorio.manager.impl.wsexport.RespuestaWS;

/**
 * Implementación del servicio de obtención del fichero de actualización
 * mediante los WS del DCO.
 *
 */
public class ServicioObtenerActualizacionesDCOWSClientImpl implements
		ServicioObtenerActualizacionesDCO {

	/**
	 * Servicio para llamar al WS SC02UNIncrementalDatosBasicos - Actualización
	 * de Unidades Organicas
	 */
	protected SC02UNIncrementalDatosBasicos servicioIncrementalUnidades;
	/**
	 * Servicio para llamar al WS SC12OFIncrementalDatosBasicos - Actualización
	 * de Oficinas
	 */
	protected SC12OFIncrementalDatosBasicos servicioIncrementalOficinas;

	/**
	 * Directorio para dejar los ficheros
	 */
	protected String tempFilesDir;
	/**
	 * Login del servicio del DCO proporcionado por el ministerio
	 */
	protected String login;
	/**
	 * Password del servicio del DCO proporcionada por el ministerio
	 */
	protected String pass;
	/**
	 * Formato en el que obtener las actualizaciones
	 */
	protected String fileFormat;
	/**
	 * Tipo de consulta para las oficinas
	 */
	protected String oficinasQueryType;
	/**
	 * Tipo de consulta para las unidades organicas
	 */
	protected String unidadesQueryType;
	/**
	 * Tipo de consulta para las unidades organicas
	 */
	protected String relacionOficinasUnidOrgQueryType;

	/**
	 * Indica si la información que se ha recuperar de oficinas/unid. solo son
	 * las activar para el SIR
	 */
	protected String indicadorSIR;

	private final String INCREMENTAL_OFICINAS_FILE_NAME = "datosBasicosOficinaIncremental.xml";
	private final String INCREMENTAL_UORGANICAS_FILE_NAME = "datosBasicosUOrganicaIncremental.xml";
	private final String RELACIONES_UORGANICAS_OFICINAS_FILE_NAME = "relacionesUO-OFIIncremental.xml";

	private static final Logger logger = LoggerFactory
			.getLogger(ServicioObtenerActualizacionesDCOWSClientImpl.class);

	/**
	 * Obtiene el fichero para actualizar las oficinas. Retorna el path al
	 * fichero xml
	 *
	 * @param fechaUltimaActualizacion
	 *            - Fecha de última actualización
	 * @return nombre del fichero de actualización de Oficinas
	 *
	 */
	public String getFicheroActualizarOficinasDCO(Date fechaUltimaActualizacion) {
		String finalFileName = null;
		RespuestaWS respuesta = null;

		if (null == fechaUltimaActualizacion) {
			logger.error("ServicioActualizacionDCOWSClientImpl::getFicheroActualizarOficinasDCO - Error la fecha de última actualización es nula");
			throw new ObtencionFicheroActualizacionDCOException(
					"No hay fecha de última actualización");
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			File tempZipFile = File.createTempFile("dec", "zip", new File(
					getTempFilesDir()));

			respuesta = getServicioIncrementalOficinas().exportar(getLogin(),
					getPass(), getFileFormat(), getOficinasQueryType(),
					dateFormat.format(fechaUltimaActualizacion),
					dateFormat.format(Calendar.getInstance().getTime()), "",
					"", "", "", "", getIndicadorSIR(), "", "", "", "", "", "",
					"", "", "", "");

			// comprobamos si la respuesta del WS del DCO es correcta
			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {

				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(),
						tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(
						tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while (itr.hasNext()) {
					fileName = itr.next();
					if (fileName.endsWith(INCREMENTAL_OFICINAS_FILE_NAME)) {
						finalFileName = fileName;
					}
				}
			}
		} catch (ZipException zipEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarOficinasDCO - Error al descomprimir el fichero retornado por el DCO.",
					zipEx);

		} catch (IOException ioEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarOficinasDCO - Error al crear los ficheros temporales.",
					ioEx);

		} catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ")
					.append(INCREMENTAL_OFICINAS_FILE_NAME);
			// Generamos warning con el nombre del fichero donde se ha producido
			// el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {
				// si la respuesta del WS nos ha remitido algun error
				sbMsgError
						.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				// por si no se ha encontrado el fichero
				sbMsgError
						.append("El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(INCREMENTAL_OFICINAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(
					sbMsgError.toString());
		}
		return finalFileName;
	}

	/**
	 * Retorna el path al fichero xml
	 *
	 * @param fechaUltimaActualizacion
	 *            - Fecha de última actualización
	 * @return nombre del fichero de actualización de Unidades
	 */
	public String getFicheroActualizarUnidadesDCO(Date fechaUltimaActualizacion) {
		String finalFileName = null;
		RespuestaWS respuesta = null;

		if (null == fechaUltimaActualizacion) {
			logger.error("ServicioActualizacionDCOWSClientImpl::getFicheroActualizarUnidadesDCO - Error la fecha de última actualización es nula");
			throw new ObtencionFicheroActualizacionDCOException(
					"No hay fecha de última actualización");
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			File tempZipFile = File.createTempFile("dec", "zip", new File(
					getTempFilesDir()));

			respuesta = getServicioIncrementalUnidades().exportar(getLogin(),
					getPass(), getFileFormat(), getUnidadesQueryType(),
					dateFormat.format(fechaUltimaActualizacion),
					dateFormat.format(Calendar.getInstance().getTime()), "",
					"", "", "", "", "", "", "", "");

			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {

				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(),
						tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(
						tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while (itr.hasNext()) {
					fileName = itr.next();
					if (fileName.endsWith(INCREMENTAL_UORGANICAS_FILE_NAME)) {
						finalFileName = fileName;
					}
				}
			}
		} catch (ZipException zipEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.",
					zipEx);

		} catch (IOException ioEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarUnidadesDCO - Error al crear los ficheros temporales.",
					ioEx);

		} catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ")
					.append(INCREMENTAL_UORGANICAS_FILE_NAME);
			// Generamos warning con el nombre del fichero donde se ha producido
			// el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {
				// si la respuesta del WS nos informa de algún error
				sbMsgError
						.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				// si no se ha encontrado el fichero necesario
				sbMsgError
						.append("El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(INCREMENTAL_UORGANICAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(
					sbMsgError.toString());
		}

		return finalFileName;
	}

	public String getFicheroActualizarRelacionOficinasUnidadesDCO(
			Date fechaUltimaActualizacion) {
		String finalFileName = null;
		RespuestaWS respuesta = null;

		if (null == fechaUltimaActualizacion) {
			logger.error("ServicioActualizacionDCOWSClientImpl::getFicheroActualizarRelacionOficinasUnidadesDCO - Error la fecha de última actualización es nula");
			throw new ObtencionFicheroActualizacionDCOException(
					"No hay fecha de última actualización");
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			File tempZipFile = File.createTempFile("dec", "zip", new File(
					getTempFilesDir()));

			respuesta = getServicioIncrementalOficinas().exportar(getLogin(),
					getPass(), getFileFormat(),
					getRelacionOficinasUnidOrgQueryType(),
					dateFormat.format(fechaUltimaActualizacion),
					dateFormat.format(Calendar.getInstance().getTime()), "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

			// comprobamos si la respuesta del WS del DCO es correcta
			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {

				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(),
						tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(
						tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while (itr.hasNext()) {
					fileName = itr.next();
					if (fileName
							.endsWith(RELACIONES_UORGANICAS_OFICINAS_FILE_NAME)) {
						finalFileName = fileName;
					}
				}
			}
		} catch (ZipException zipEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarRelacionOficinasUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.",
					zipEx);

		} catch (IOException ioEx) {
			logger.error(
					"ServicioActualizacionDCOWSClientImpl::getFicheroActualizarRelacionOficinasUnidadesDCO - Error al crear los ficheros temporales.",
					ioEx);

		} catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ")
					.append(RELACIONES_UORGANICAS_OFICINAS_FILE_NAME);
			// Generamos warning con el nombre del fichero donde se ha producido
			// el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {
				// si la respuesta del WS nos ha remitido algun error
				sbMsgError
						.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				// por si no se ha encontrado el fichero
				sbMsgError
						.append("El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(RELACIONES_UORGANICAS_OFICINAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(
					sbMsgError.toString());
		}
		return finalFileName;
	}

	public SC02UNIncrementalDatosBasicos getServicioIncrementalUnidades() {
		return servicioIncrementalUnidades;
	}

	public void setServicioIncrementalUnidades(
			SC02UNIncrementalDatosBasicos servicioIncrementalUnidades) {
		this.servicioIncrementalUnidades = servicioIncrementalUnidades;
	}

	public SC12OFIncrementalDatosBasicos getServicioIncrementalOficinas() {
		return servicioIncrementalOficinas;
	}

	public void setServicioIncrementalOficinas(
			SC12OFIncrementalDatosBasicos servicioIncrementalOficinas) {
		this.servicioIncrementalOficinas = servicioIncrementalOficinas;
	}

	public String getTempFilesDir() {
		return tempFilesDir;
	}

	public void setTempFilesDir(String tempFilesDir) {
		this.tempFilesDir = tempFilesDir;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getOficinasQueryType() {
		return oficinasQueryType;
	}

	public void setOficinasQueryType(String oficinasQueryType) {
		this.oficinasQueryType = oficinasQueryType;
	}

	public String getUnidadesQueryType() {
		return unidadesQueryType;
	}

	public void setUnidadesQueryType(String unidadesQueryType) {
		this.unidadesQueryType = unidadesQueryType;
	}

	public String getIndicadorSIR() {
		return indicadorSIR;
	}

	public void setIndicadorSIR(String indicadorSIR) {
		this.indicadorSIR = indicadorSIR;
	}

	public String getRelacionOficinasUnidOrgQueryType() {
		return relacionOficinasUnidOrgQueryType;
	}

	public void setRelacionOficinasUnidOrgQueryType(
			String relacionOficinasUnidOrgQueryType) {
		this.relacionOficinasUnidOrgQueryType = relacionOficinasUnidOrgQueryType;
	}

}
