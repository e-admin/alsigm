package gcontrol.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transformer de value objects grupo a objetos de presentacion
 */
public class GrupoToPO implements Transformer {

	ServiceRepository services = null;

	GrupoToPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object vo) {
		GrupoPO po = new GrupoPO(services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static GrupoToPO getInstance(ServiceRepository services) {
		return new GrupoToPO(services);
	}
}
