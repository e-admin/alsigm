package deposito.actions.hueco;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Utilidad para la transformación de value objects con la información de
 * unidades documentales ubicadas en el depósito físico a objetos para su
 * presentación
 */
public class UdocEnUIToPO implements Transformer {

	ServiceRepository services = null;

	UdocEnUIToPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public Object transform(Object obj) {
		UdocEnUIPO po = new UdocEnUIPO(services);
		POUtils.copyVOProperties(po, obj);
		return po;
	}

	public static UdocEnUIToPO getInstance(ServiceRepository services) {
		return new UdocEnUIToPO(services);
	}
}
