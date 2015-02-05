package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de prevision en objetos para presentacion
 * 
 */
class PrevisionToPO implements Transformer {
	GestionPrevisionesBI previsionBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	GestionControlUsuariosBI controlAccesoBI = null;
	GestionFondosBI fondoBI = null;
	GestionSistemaBI sistemaBI = null;
	ServiceRepository services = null;

	PrevisionToPO(GestionPrevisionesBI previsionBI,
			GestionRelacionesEntregaBI relacionBI,
			GestionControlUsuariosBI controlAccesoBI, GestionFondosBI fondoBI,
			GestionSistemaBI sistemaBI, ServiceRepository services) {
		this.previsionBI = previsionBI;
		this.relacionBI = relacionBI;
		this.controlAccesoBI = controlAccesoBI;
		this.fondoBI = fondoBI;
		this.sistemaBI = sistemaBI;
		this.services = services;
	}

	public Object transform(Object vo) {
		PrevisionPO po = new PrevisionPO(previsionBI, relacionBI,
				controlAccesoBI, fondoBI, sistemaBI, services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static PrevisionToPO getInstance(ServiceRepository services) {
		return new PrevisionToPO(services.lookupGestionPrevisionesBI(),
				services.lookupGestionRelacionesBI(),
				services.lookupGestionControlUsuariosBI(),
				services.lookupGestionFondosBI(),
				services.lookupGestionSistemaBI(), services);
	}
}