package fondos.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transformer para obtener a partir de value objects de solicitudes de
 * autorización objetos para su presentación
 * 
 */
public class SolicitudSerieToPO implements Transformer {

	ServiceRepository services = null;

	SolicitudSerieToPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object obj) {
		SolicitudSeriePO po = new SolicitudSeriePO(services);
		POUtils.copyVOProperties(po, obj);
		return po;
	}

	public static SolicitudSerieToPO getInstance(ServiceRepository services) {
		return new SolicitudSerieToPO(services);
	}
}