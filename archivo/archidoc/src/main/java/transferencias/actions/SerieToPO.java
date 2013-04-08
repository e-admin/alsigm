package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de prevision en objetos para presentacion
 * 
 */
class SerieToPO implements Transformer {
	GestionCuadroClasificacionBI cclfBI = null;

	SerieToPO(GestionCuadroClasificacionBI cclfBI) {
		this.cclfBI = cclfBI;
	}

	public Object transform(Object vo) {
		SeriePO po = new SeriePO(cclfBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static SerieToPO getInstance(ServiceRepository services) {
		return new SerieToPO(services.lookupGestionCuadroClasificacionBI());
	}
}