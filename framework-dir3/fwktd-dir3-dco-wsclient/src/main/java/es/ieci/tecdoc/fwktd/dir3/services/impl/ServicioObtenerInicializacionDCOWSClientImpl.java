package es.ieci.tecdoc.fwktd.dir3.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.exception.ObtencionFicheroInicializacionDCOException;
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


	private final String VOLCADO_OFICINAS_FILE_NAME = "datosBasicosOficina.xml";
	private final String VOLCADO_UORGANICAS_FILE_NAME = "datosBasicosUOrganica.xml";
	private static final Logger logger = LoggerFactory.getLogger(ServicioObtenerInicializacionDCOWSClientImpl.class);


	public String getFicheroInicializarOficinasDCO() {

		String finalFileName = null;

		try{

			File tempZipFile = File.createTempFile("dec", "zip", new File(getTempFilesDir()));
			RespuestaWS respuesta = getServicioVolcadoOficinas().exportar(getLogin(), getPass(), getFileFormat(), getOficinasQueryType(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
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
		}catch (ZipException zipEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.", zipEx);


		}catch (IOException ioEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al crear los ficheros temporales.", ioEx);

		}catch (Exception e) {
			logger.error("Error inesperado", e);
		}

		if(finalFileName==null)
		{
			throw new ObtencionFicheroInicializacionDCOException("El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: "+VOLCADO_OFICINAS_FILE_NAME);
		}
		return finalFileName;
	}

	public String getFicheroInicializarUnidadesDCO() {
		String finalFileName = null;
		try{
			File tempZipFile = File.createTempFile("dec", "zip", new File(getTempFilesDir()));
			RespuestaWS respuesta = getServicioVolcadoUnidades().exportar(getLogin(), getPass(), getFileFormat(), getUnidadesQueryType(), "", "", "", "", "", "", "", "", "");
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
		}catch (ZipException zipEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al descomprimir el fichero retornado por el DCO.", zipEx);

		}catch (IOException ioEx) {
			logger.error("ServicioInicializacionDCOWSClientImpl::getFicheroInicializarUnidadesDCO - Error al crear los ficheros temporales.", ioEx);

		}catch (Exception e) {
			logger.error("Error inesperado", e);
		}
		if(finalFileName==null)
		{
			throw new ObtencionFicheroInicializacionDCOException("El proceso ha finalizado sin errores pero no se encuentra el fichero esperado con nombre: "+VOLCADO_UORGANICAS_FILE_NAME);
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



}
