package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.helper.XmlDcoToObject;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosUnidadOrganicaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.GenerateScriptSQLManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;
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

	protected GenerateScriptSQLManager generateScriptSQLOficinaManager;
	protected GenerateScriptSQLManager generateScriptSQLUnidadOrganicaManagerImpl;

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

		OficinasVO oficinasDCO = XmlDcoToObject.getInstance().getOficinasFromXmlFile(fileInicializarOficinas);
		getDatosBasicosOficinaManager().saveDatosBasicosOficinas(oficinasDCO);
		if(logger.isDebugEnabled()){
			logger.debug("Oficinas inicializadas");
		}

		OrganismosVO organismosDCO = XmlDcoToObject.getInstance().getOrganismosFromXmlFile(fileInicializarUnidades);
		getDatosBasicosUnidadOrganicaManager().saveDatosBasicosUnidadesOrganicas(organismosDCO);
		if(logger.isDebugEnabled()){
			logger.debug("Organismos inicializados");
			logger.debug("Finalizada la inicialización del sistema");
		}
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

		getGenerateScriptSQLOficinaManager().generateScriptInicializacion(fileInicializarOficinas);
		getGenerateScriptSQLUnidadOrganicaManagerImpl().generateScriptInicializacion(fileInicializarUnidades);

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

	public GenerateScriptSQLManager getGenerateScriptSQLUnidadOrganicaManagerImpl() {
		return generateScriptSQLUnidadOrganicaManagerImpl;
	}

	public void setGenerateScriptSQLUnidadOrganicaManagerImpl(
			GenerateScriptSQLManager generateScriptSQLUnidadOrganicaManagerImpl) {
		this.generateScriptSQLUnidadOrganicaManagerImpl = generateScriptSQLUnidadOrganicaManagerImpl;
	}
}

