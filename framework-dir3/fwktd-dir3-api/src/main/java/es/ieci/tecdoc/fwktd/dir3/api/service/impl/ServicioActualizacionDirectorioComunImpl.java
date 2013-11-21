package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.helper.XmlDcoToObject;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosRelacionUnidOrgOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosUnidadOrganicaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.EstadoActualizacionDCOManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionesUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;
import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioActualizacionDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerActualizacionesDCO;

/**
 * Implementacion por defecto del servicio de actualización del DCO
 *
 */
public class ServicioActualizacionDirectorioComunImpl implements
		ServicioActualizacionDirectorioComun {

	/**
	 * Servicio para obtener los ficheros de actualización del DCO
	 */
	protected ServicioObtenerActualizacionesDCO servicioObtenerActualizacionesDCO;
	/**
	 * Manager para la gestión de oficinas
	 */
	protected DatosBasicosOficinaManager datosBasicosOficinaManager;
	/**
	 * Manager para la gestión de unidades orgánicas
	 */
	protected DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager;
	/**
	 * Manager para la gestión de unidades orgánicas
	 */
	protected EstadoActualizacionDCOManager estadoActualizacionDCOManager;

	/**
	 * Manager para la gestión de las relaciones entre las oficinas y las unid.
	 * orgánicas
	 */
	protected DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager;

	protected GenerateScriptSQLManager generateScriptSQLOficinaManager;
	protected GenerateScriptSQLManager generateScriptSQLUnidadOrganicaManager;
	protected GenerateScriptSQLManager generateScriptSQLRelacionesOficinaUnidOrgManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ServicioActualizacionDirectorioComunImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.fwktd.dir3.core.service.ServicioActualizacionDirectorioComun
	 * #actualizarDirectorioComun()
	 */
	public void actualizarDirectorioComun() {
		if (logger.isDebugEnabled()) {
			logger.debug("Comienza la actualización del sistema");
		}

		// Obtenemos la fecha de la ultima actualizacion
		EstadoActualizacionDcoVO estadoActualizacion = estadoActualizacionDCOManager
				.getLastSuccessUpdate();
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Los datos de la última actualización son: ").append(
					estadoActualizacion);
			logger.debug(sb.toString());
		}

		// Obtenemos los ficheros con los datos

		// oficinas
		String fileActualizarOficinas = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarOficinasDCO(
						estadoActualizacion.getFechaActualizacion());

		// unidades orgánicas
		String fileActualizarUnidades = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarUnidadesDCO(
						estadoActualizacion.getFechaActualizacion());

		// relaciones entre oficinas y unid. orgánicas
		String fileActualizarRelacionesOfiUnid = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarRelacionOficinasUnidadesDCO(
						estadoActualizacion.getFechaActualizacion());

		// Transformamos los datos de los ficheros a VOs
		OficinasVO oficinasDCO = XmlDcoToObject.getInstance()
				.getOficinasFromXmlFile(fileActualizarOficinas);
		OrganismosVO organismosDCO = XmlDcoToObject.getInstance()
				.getOrganismosFromXmlFile(fileActualizarUnidades);
		RelacionesUnidOrgOficinaVO relacionesDCO = XmlDcoToObject.getInstance()
				.getRelacionesUnidOrgOficinaFromXmlFile(
						fileActualizarRelacionesOfiUnid);

		// Actualizamos los datos

		// oficinas
		getDatosBasicosOficinaManager().updateDatosBasicosOficinas(oficinasDCO);
		// unidades orgánicas
		getDatosBasicosUnidadOrganicaManager()
				.updateDatosBasicosUnidadesOrganicas(organismosDCO);
		// relaciones
		getDatosBasicosRelacionUnidOrgOficinaManager()
				.updateDatosBasicosRelacionesUnidOrgOficinaVO(relacionesDCO);
		if (logger.isDebugEnabled()) {
			logger.debug("Actualizados los datos de oficinas, organismos y sus relaciones");
		}

		// Actualizamos la fecha de ultima actualizacion
		estadoActualizacion.setFechaActualizacion(Calendar.getInstance()
				.getTime());
		getEstadoActualizacionDCOManager().update(estadoActualizacion);

		if (logger.isDebugEnabled()) {
			logger.debug("Finaliza la actualización del sistema");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.fwktd.dir3.core.service.ServicioActualizacionDirectorioComun
	 * #generateScriptsActualizacionDirectorioComun()
	 */
	public void generateScriptsActualizacionDirectorioComun() {
		if (logger.isDebugEnabled()) {
			logger.debug("Comienza la generación de los script de actualización del sistema");
		}

		// Obtenemos la fecha de la ultima actualizacion
		Date lastDateUpdate = estadoActualizacionDCOManager
				.getLastSuccessUpdate().getFechaActualizacion();

		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("La fecha de la ultima actualización es: ").append(
					lastDateUpdate.toString());
			logger.debug(sb.toString());
		}

		// Obtenemos los ficheros con los datos
		String fileInicializarOficinas = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarOficinasDCO(lastDateUpdate);
		String fileInicializarUnidades = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarUnidadesDCO(lastDateUpdate);
		String fileInicializarRelaciones = getServicioObtenerActualizacionesDCO()
				.getFicheroActualizarRelacionOficinasUnidadesDCO(lastDateUpdate);

		// Generamos los script
		getGenerateScriptSQLOficinaManager().generateScriptActualizacion(
				fileInicializarOficinas);
		getGenerateScriptSQLUnidadOrganicaManager()
				.generateScriptActualizacion(fileInicializarUnidades);
		getGenerateScriptSQLRelacionesOficinaUnidOrgManager()
				.generateScriptActualizacion(fileInicializarRelaciones);

		if (logger.isDebugEnabled()) {
			logger.debug("Finaliza la generación de los script de actualización del sistema");
		}
	}

	public ServicioObtenerActualizacionesDCO getServicioObtenerActualizacionesDCO() {
		return servicioObtenerActualizacionesDCO;
	}

	public void setServicioObtenerActualizacionesDCO(
			ServicioObtenerActualizacionesDCO servicioObtenerActualizacionesDCO) {
		this.servicioObtenerActualizacionesDCO = servicioObtenerActualizacionesDCO;
	}

	public DatosBasicosOficinaManager getDatosBasicosOficinaManager() {
		return datosBasicosOficinaManager;
	}

	public void setDatosBasicosOficinaManager(
			DatosBasicosOficinaManager datosBasicosOficinaManager) {
		this.datosBasicosOficinaManager = datosBasicosOficinaManager;
	}

	public DatosBasicosUnidadOrganicaManager getDatosBasicosUnidadOrganicaManager() {
		return datosBasicosUnidadOrganicaManager;
	}

	public void setDatosBasicosUnidadOrganicaManager(
			DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager) {
		this.datosBasicosUnidadOrganicaManager = datosBasicosUnidadOrganicaManager;
	}

	public EstadoActualizacionDCOManager getEstadoActualizacionDCOManager() {
		return estadoActualizacionDCOManager;
	}

	public void setEstadoActualizacionDCOManager(
			EstadoActualizacionDCOManager estadoActualizacionDCOManager) {
		this.estadoActualizacionDCOManager = estadoActualizacionDCOManager;
	}

	public GenerateScriptSQLManager getGenerateScriptSQLOficinaManager() {
		return generateScriptSQLOficinaManager;
	}

	public void setGenerateScriptSQLOficinaManager(
			GenerateScriptSQLManager generateScriptSQLOficinaManager) {
		this.generateScriptSQLOficinaManager = generateScriptSQLOficinaManager;
	}

	public GenerateScriptSQLManager getGenerateScriptSQLUnidadOrganicaManager() {
		return generateScriptSQLUnidadOrganicaManager;
	}

	public void setGenerateScriptSQLUnidadOrganicaManager(
			GenerateScriptSQLManager generateScriptSQLUnidadOrganicaManager) {
		this.generateScriptSQLUnidadOrganicaManager = generateScriptSQLUnidadOrganicaManager;
	}

	public DatosBasicosRelacionUnidOrgOficinaManager getDatosBasicosRelacionUnidOrgOficinaManager() {
		return datosBasicosRelacionUnidOrgOficinaManager;
	}

	public void setDatosBasicosRelacionUnidOrgOficinaManager(
			DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager) {
		this.datosBasicosRelacionUnidOrgOficinaManager = datosBasicosRelacionUnidOrgOficinaManager;
	}

	public GenerateScriptSQLManager getGenerateScriptSQLRelacionesOficinaUnidOrgManager() {
		return generateScriptSQLRelacionesOficinaUnidOrgManager;
	}

	public void setGenerateScriptSQLRelacionesOficinaUnidOrgManager(
			GenerateScriptSQLManager generateScriptSQLRelacionesOficinaUnidOrgManager) {
		this.generateScriptSQLRelacionesOficinaUnidOrgManager = generateScriptSQLRelacionesOficinaUnidOrgManager;
	}

}
