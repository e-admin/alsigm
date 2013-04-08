package gcontrol.view;

import gcontrol.vos.ArchivoVO;
import gcontrol.vos.NivelArchivoVO;

import org.apache.log4j.Logger;

import transferencias.TipoSignaturacionConstants;
import transferencias.model.TipoSignaturacion;

import common.bi.GestionArchivosBI;
import common.bi.GestionNivelesArchivoBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

public class ArchivoPO extends ArchivoVO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ArchivoPO.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	GestionNivelesArchivoBI nivelesService = null;
	GestionArchivosBI archivosService = null;

	String nombreNivel = null;
	String nombreReceptor = null;
	String nombreSignaturacion = null;

	public ArchivoPO(ArchivoVO archivoVO, ServiceRepository services) {
		POUtils.copyVOProperties(this, archivoVO);
		this.services = services;
		nivelesService = services.lookupGestionNivelesArchivoBI();
		archivosService = services.lookupGestionArchivosBI();
	}

	public String getNombreNivel() {
		if (nombreNivel == null) {
			try {
				NivelArchivoVO nivelArchivoVO = nivelesService
						.getNivelArchivoXId(getIdnivel());
				nombreNivel = nivelArchivoVO.getNombre();
			} catch (Exception e) {
				logger.error(e);
				return null;
			}
		}
		return nombreNivel;
	}

	public String getNombreReceptor() {

		if (StringUtils.isBlank(getIdreceptordefecto())) {
			return null;
		}

		if (nombreReceptor == null) {
			try {
				ArchivoVO archivoVO = archivosService
						.getArchivoXId(getIdreceptordefecto());
				nombreReceptor = archivoVO.getNombre();
			} catch (Exception e) {
				logger.error(e);
				return null;
			}
		}
		return nombreReceptor;
	}

	/**
	 * Muestra el nombre del tipo de signaturación utilizado en cada archivo (al
	 * mostrar la lista de archivos).
	 * 
	 * @return
	 */
	public String getNombreSignaturacion() {
		if (nombreSignaturacion == null) {
			try {
				int idTipoSignaturacion = getTiposignaturacion();
				TipoSignaturacionConstants tipo = TipoSignaturacion
						.getTipoSignaturacion(idTipoSignaturacion);
				if (tipo != null)
					nombreSignaturacion = tipo.getNombre();
			} catch (Exception e) {
				logger.error(e);
				return null;
			}
		}
		return nombreSignaturacion;
	}
}