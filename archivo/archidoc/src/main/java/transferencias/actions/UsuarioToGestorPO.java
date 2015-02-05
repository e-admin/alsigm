package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.view.POUtils;

/**
 * Transformacion de value object de usuario a objeto de presentacion de gestor
 * de transferencia
 */
public class UsuarioToGestorPO implements Transformer {

	public Object transform(Object vo) {
		GestorPO po = new GestorPO();
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static UsuarioToGestorPO getInstance() {
		return new UsuarioToGestorPO();
	}
}
