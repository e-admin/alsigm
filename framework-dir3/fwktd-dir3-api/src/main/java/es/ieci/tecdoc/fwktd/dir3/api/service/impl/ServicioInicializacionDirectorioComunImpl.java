package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.helper.XmlDcoToObject;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosRelacionUnidOrgOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosUnidadOrganicaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.EstadoActualizacionDCOManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionesUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioInicializacionDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerInicializacionDCO;

/**
 * Implementación por defecto del servicio de inicialización del DCO.
 *
 */
public class ServicioInicializacionDirectorioComunImpl implements ServicioInicializacionDirectorioComun{

	/**
	 * Servicio para obtener el volcado de datos del DCO
	 */
	protected ServicioObtenerInicializacionDCO servicioObtenerInicializacionDCO;
	protected DatosBasicosOficinaManager datosBasicosOficinaManager;
	protected DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager;
	protected DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager;
	protected EstadoActualizacionDCOManager estadoActualizacionDCOManager;


	protected GenerateScriptSQLManager generateScriptSQLOficinaManager;
	protected GenerateScriptSQLManager generateScriptSQLUnidadOrganicaManager;
	protected GenerateScriptSQLManager generateScriptSQLRelacionesOficinaUnidOrgManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ServicioInicializacionDirectorioComunImpl.class);


	/**
	 * {@inheritDoc}
	 */
	public void inicializarDirectorioComun() {
		if(logger.isDebugEnabled()){
			logger.debug("Comienza la inicialización del sistema");
		}
		String fileInicializarOficinas = getServicioObtenerInicializacionDCO().getFicheroInicializarOficinasDCO();
		String fileInicializarUnidades = getServicioObtenerInicializacionDCO().getFicheroInicializarUnidadesDCO();
		String fileInicializarRelacionesUnidOrgOficina = getServicioObtenerInicializacionDCO().getFicheroInicializarRelacionesRelacionesOficinaUnidOrgDCO();

		OficinasVO oficinasDCO = XmlDcoToObject.getInstance().getOficinasFromXmlFile(fileInicializarOficinas);
		getDatosBasicosOficinaManager().saveDatosBasicosOficinas(oficinasDCO);
		if(logger.isDebugEnabled()){
			logger.debug("Oficinas inicializadas");
		}

		OrganismosVO organismosDCO = XmlDcoToObject.getInstance().getOrganismosFromXmlFile(fileInicializarUnidades);
		getDatosBasicosUnidadOrganicaManager().saveDatosBasicosUnidadesOrganicas(organismosDCO);
		if(logger.isDebugEnabled()){
			logger.debug("Organismos inicializados");
		}

		RelacionesUnidOrgOficinaVO relacionesUnidOrgOficinaDCO = XmlDcoToObject
				.getInstance().getRelacionesUnidOrgOficinaFromXmlFile(
						fileInicializarRelacionesUnidOrgOficina);
		getDatosBasicosRelacionUnidOrgOficinaManager().saveDatosBasicosRelacionesUnidOrgOficinaVO(relacionesUnidOrgOficinaDCO);
		if(logger.isDebugEnabled()){
			logger.debug("Relaciones entre las oficinas y las unid. orgánicas inicializados");
		}

		// añadimos la información del estado de actualizacion e inicializacion
		// con la fecha de la inicialización
		EstadoActualizacionDcoVO estadoActualizacion = getDataEstadoActualizacionDCO();
		getEstadoActualizacionDCOManager().save(estadoActualizacion);
		if(logger.isDebugEnabled()){
			logger.debug("EstadoActualizacion inicializados");
			logger.debug("Finalizada la inicialización del sistema");
		}

	}

	/**
	 * Método que genera la información del estado de actualizacion/inicialización del DCO
	 *
	 * @return {@link EstadoActualizacionDcoVO}
	 */
	private EstadoActualizacionDcoVO getDataEstadoActualizacionDCO() {

		EstadoActualizacionDcoVO estadoActualizacion = new EstadoActualizacionDcoVO();
		//Este valor se setea al igual que en los script de BBDD, pero deberá ser realizado con incrementer
		//TODO adaptar esto al uso de incrementer
		estadoActualizacion.setId("0");
		estadoActualizacion.setFechaActualizacion(Calendar.getInstance().getTime());
		estadoActualizacion.setEstado("OK");
		return estadoActualizacion;
	}

	/**
	 * {@inheritDoc}
	 */
	public void generateScriptsInicializacionDirectorioComun() {
		if(logger.isDebugEnabled()){
			logger.debug("Comienza la generación de los script de inicialización del sistema");
		}
		String fileInicializarOficinas = getServicioObtenerInicializacionDCO().getFicheroInicializarOficinasDCO();
		String fileInicializarUnidades = getServicioObtenerInicializacionDCO().getFicheroInicializarUnidadesDCO();
		String fileInicializarRelacionesUnidOrgOficina = getServicioObtenerInicializacionDCO().getFicheroInicializarRelacionesRelacionesOficinaUnidOrgDCO();

		getGenerateScriptSQLOficinaManager().generateScriptInicializacion(fileInicializarOficinas);
		getGenerateScriptSQLUnidadOrganicaManager().generateScriptInicializacion(fileInicializarUnidades);
		getGenerateScriptSQLRelacionesOficinaUnidOrgManager().generateScriptInicializacion(fileInicializarRelacionesUnidOrgOficina);

		if(logger.isDebugEnabled()){
			logger.debug("Finaliza la generación de los script de inicialización del sistema");
		}
	}


	private String composeScriptFileName(String scriptsFilesDir2, String init2,
			String oficinas2) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServicioObtenerInicializacionDCO getServicioObtenerInicializacionDCO() {
		return servicioObtenerInicializacionDCO;
	}

	public void setServicioObtenerInicializacionDCO(
			ServicioObtenerInicializacionDCO servicioObtenerInicializacionDCO) {
		this.servicioObtenerInicializacionDCO = servicioObtenerInicializacionDCO;
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

	public EstadoActualizacionDCOManager getEstadoActualizacionDCOManager() {
		return estadoActualizacionDCOManager;
	}

	public void setEstadoActualizacionDCOManager(
			EstadoActualizacionDCOManager estadoActualizacionDCOManager) {
		this.estadoActualizacionDCOManager = estadoActualizacionDCOManager;
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

