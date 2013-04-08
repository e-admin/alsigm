package gcontrol.view;

import gcontrol.vos.UsuarioVO;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

/**
 * Transformer para convertir value objects de usuario en objetos de
 * presentación
 */
public class TransformerUsuariosVOToUsuariosPO implements Transformer {
	ServiceRepository services = null;

	public TransformerUsuariosVOToUsuariosPO(ServiceRepository services) {
		this.services = services;
	}

	public Object transform(Object arg0) {
		return new UsuarioPO((UsuarioVO) arg0, services);
	}
}
