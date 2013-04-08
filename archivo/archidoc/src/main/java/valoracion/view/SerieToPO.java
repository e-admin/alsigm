package valoracion.view;

import org.apache.commons.collections.Transformer;

import se.usuarios.AppUser;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de series en objetos para presentacion
 */
public class SerieToPO implements Transformer {

	ServiceRepository services = null;

	SerieToPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object vo) {
		SeriePO po = new SeriePO(services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public Object transform(Object vo, AppUser user) {
		SeriePO po = new SeriePO(services);
		POUtils.copyVOProperties(po, vo);
		po.setUser(user);
		return po;
	}

	public static SerieToPO getInstance(ServiceRepository services) {
		return new SerieToPO(services);
	}
}