package es.ieci.tecdoc.fwktd.dir3.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.exception.ObtencionFicheroActualizacionDCOException;
import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerInicializacionDCO;
import es.ieci.tecdoc.fwktd.dir3.util.Base64Utils;
import es.ieci.tecdoc.fwktd.dir3.util.ZipUtils;
import es.map.directorio.manager.impl.SC01UNVolcadoDatosBasicos;
import es.map.directorio.manager.impl.SC11OFVolcadoDatosBasicos;
import es.map.directorio.manager.impl.wsexport.RespuestaWS;

public class ServicioObtenerInicializacionDCOWSClientImpl implements ServicioObtenerInicializacionDCO{

	/**
	 * Servicio para llamar al WS SC01UNVolcadoDatosBasicos - Inicializacion de Unidades Organicas
	 */
	protected SC01UNVolcadoDatosBasicos servicioVolcadoUnidades;

	/**
	 * Servicio para llamar al WS SC11OFVolcadoDatosBasicos - Inicializacion de Oficinas
	 */
	protected SC11OFVolcadoDatosBasicos servicioVolcadoOficinas;

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
	 * Indica si la información que se ha recuperar de oficinas/unid. solo son las activar para el SIR
	 */
	protected String indicadorSIR;


	private final String VOLCADO_OFICINAS_FILE_NAME = "datosBasicosOficina.xml";
	private final String VOLCADO_UORGANICAS_FILE_NAME = "datosBasicosUOrganica.xml";
	private final String VOLCADO_RELACIONES_UORGANICAS_OFICINAS_FILE_NAME = "relacionesUO-OFI.xml";

	private static final Logger logger = LoggerFactory.getLogger(ServicioObtenerInicializacionDCOWSClientImpl.class);


	public String getFicheroInicializarOficinasDCO() {

		String finalFileName = null;
		RespuestaWS respuesta = null;

		try{

			File tempZipFile = File.createTempFile("dec", "zip", new File(getTempFilesDir()));
			respuesta = getServicioVolcadoOficinas().exportar(getLogin(), getPass(), getFileFormat(), getOficinasQueryType(), "", "", "", "", "", getIndicadorSIR(), "", "", "", "", "", "", "", "", "", "");

			//comprobamos si la respuesta del WS del DCO es correcta
			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(), "01"))) {
				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(), tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while(itr.hasNext())
				{
					fileName = itr.next();
					if(fileName.endsWith(VOLCADO_OFICINAS_FILE_NAME)){
						finalFileName = fileName;
					}
				}
			}
		}catch (ZipException zipEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.", zipEx);


		}catch (IOException ioEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al crear los ficheros temporales.", ioEx);

		}catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ").append(VOLCADO_OFICINAS_FILE_NAME);
			//Generamos warning con el nombre del fichero donde se ha producido el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(), "01"))) {
				//si la respuesta del WS nos indica un error
				sbMsgError.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				//si no se encuentra el fichero necesario
				sbMsgError.append(
						"El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(VOLCADO_OFICINAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(sbMsgError.toString());
		}

		return finalFileName;
	}

	public String getFicheroInicializarRelacionesRelacionesOficinaUnidOrgDCO() {
		String finalFileName = null;
		RespuestaWS respuesta = null;

		try{

			File tempZipFile = File.createTempFile("dec", "zip", new File(getTempFilesDir()));
			respuesta = getServicioVolcadoOficinas().exportar(getLogin(), getPass(), getFileFormat(), getRelacionOficinasUnidOrgQueryType(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

			//comprobamos si la respuesta del WS del DCO es correcta
			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(), "01"))) {
				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(), tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while(itr.hasNext())
				{
					fileName = itr.next();
					if(fileName.endsWith(VOLCADO_RELACIONES_UORGANICAS_OFICINAS_FILE_NAME)){
						finalFileName = fileName;
					}
				}
			}
		}catch (ZipException zipEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarRelacionesRelacionesOficinaUnidOrgDCO - Error al descomprimir el fichero retornado por el DCO.", zipEx);


		}catch (IOException ioEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarRelacionesRelacionesOficinaUnidOrgDCO - Error al crear los ficheros temporales.", ioEx);

		}catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ").append(VOLCADO_RELACIONES_UORGANICAS_OFICINAS_FILE_NAME);
			//Generamos warning con el nombre del fichero donde se ha producido el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(), "01"))) {
				//si la respuesta del WS nos indica un error
				sbMsgError.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				//si no se encuentra el fichero necesario
				sbMsgError.append(
						"El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(VOLCADO_RELACIONES_UORGANICAS_OFICINAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(sbMsgError.toString());
		}

		return finalFileName;
	}


	public String getFicheroInicializarUnidadesDCO() {
		String finalFileName = null;
		RespuestaWS respuesta = null;
		try{
			File tempZipFile = File.createTempFile("dec", "zip", new File(getTempFilesDir()));
			respuesta = getServicioVolcadoUnidades().exportar(getLogin(), getPass(), getFileFormat(), getUnidadesQueryType(), "", "", "", "", "", "", "", "", "");

			//comprobamos si la respuesta del WS del DCO es correcta
			if ((respuesta != null)
					&& (StringUtils.equalsIgnoreCase(respuesta.getCodigo(), "01"))) {
				Base64Utils.getInstance().decodeToFile(respuesta.getFichero(), tempZipFile.getAbsolutePath());
				List<String> filesUnzipped = ZipUtils.getInstance().unzipFile(tempZipFile.getAbsolutePath(), getTempFilesDir());

				Iterator<String> itr = filesUnzipped.listIterator();
				String fileName;
				while(itr.hasNext())
				{
					fileName = itr.next();
					if(fileName.endsWith(VOLCADO_UORGANICAS_FILE_NAME)){
						finalFileName = fileName;
					}
				}
			}
		}catch (ZipException zipEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.", zipEx);

		}catch (IOException ioEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al crear los ficheros temporales.", ioEx);

		}catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if (finalFileName == null) {
			StringBuffer sb = new StringBuffer();
			sb.append("No se encuentra el fichero esperado con nombre: ").append(VOLCADO_UORGANICAS_FILE_NAME);
			//Generamos warning con el nombre del fichero donde se ha producido el error
			logger.warn(sb.toString());

			StringBuffer sbMsgError = new StringBuffer();
			if (respuesta != null
					&& !(StringUtils.equalsIgnoreCase(respuesta.getCodigo(),
							"01"))) {
				//si la respuesta del WS se produce algún error
				sbMsgError.append("El proceso ha finalizado con errores, cod. error: ")
						.append(respuesta.getCodigo()).append(" - ")
						.append(respuesta.getDescripcion());
			} else {
				//sino se ha encontrado el fichero necesario
				sbMsgError.append(
						"El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: ")
						.append(VOLCADO_UORGANICAS_FILE_NAME);
			}

			throw new ObtencionFicheroActualizacionDCOException(sbMsgError.toString());
		}

		return finalFileName;
	}

	public SC01UNVolcadoDatosBasicos getServicioVolcadoUnidades() {
		return servicioVolcadoUnidades;
	}

	public void setServicioVolcadoUnidades(
			SC01UNVolcadoDatosBasicos servicioVolcadoUnidades) {
		this.servicioVolcadoUnidades = servicioVolcadoUnidades;
	}

	public String getTempFilesDir() {
		return tempFilesDir;
	}

	public void setTempFilesDir(String tempFilesDir) {
		this.tempFilesDir = tempFilesDir;
	}

	public SC11OFVolcadoDatosBasicos getServicioVolcadoOficinas() {
		return servicioVolcadoOficinas;
	}

	public void setServicioVolcadoOficinas(
			SC11OFVolcadoDatosBasicos servicioVolcadoOficinas) {
		this.servicioVolcadoOficinas = servicioVolcadoOficinas;
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
