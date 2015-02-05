package gcontrol.actions;

import org.apache.commons.collections.Transformer;

import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

class RolToPO implements Transformer {
	GestionControlUsuariosBI controlAccesoBI = null;

	RolToPO(GestionControlUsuariosBI controlAccesoBI) {
		this.controlAccesoBI = controlAccesoBI;
	}

	public Object transform(Object vo) {
		RolPO po = new RolPO(controlAccesoBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	static RolToPO getInstance(ServiceRepository services) {
		return new RolToPO(services.lookupGestionControlUsuariosBI());
	}
}
