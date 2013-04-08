package fondos.actions;

import org.apache.commons.collections.Transformer;

import se.usuarios.AppUser;

import common.bi.ServiceRepository;

import fondos.vos.FondoVO;

/**
 * Utilidad para la transformación de value objects de fondos a componentes de
 * presentación
 */
public class FondoToPOTransformer implements Transformer {

	ServiceRepository services = null;

	public FondoToPOTransformer(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object obj) {
		return new FondoPO((FondoVO) obj, services);
	}

	public Object transform(Object obj, AppUser user) {
		return new FondoPO((FondoVO) obj, services, user);
	}

	public static FondoToPOTransformer getInstance(ServiceRepository services) {
		return new FondoToPOTransformer(services);
	}
}
