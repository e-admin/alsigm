package gcontrol.actions;

import gcontrol.vos.ArchivoVO;
import gcontrol.vos.GrupoVO;

import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;

/**
 * Clase para presentacion de informacion de grupos de usuarios
 */
public class GrupoPO extends GrupoVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	transient GestionSistemaBI sistemaBI = null;

	ArchivoVO archivoCustodia = null;

	GrupoPO(ServiceRepository services) {
		this.services = services;
	}

	public ArchivoVO getArchivoCustodia() {
		if (this.archivoCustodia == null)
			this.archivoCustodia = getSistemaBI().getArchivo(getIdArchivo());
		return archivoCustodia;
	}

	private GestionSistemaBI getSistemaBI() {
		if (sistemaBI == null)
			sistemaBI = services.lookupGestionSistemaBI();
		return sistemaBI;
	}
}