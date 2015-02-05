package docelectronicos.actions;

import gcontrol.view.UsuarioPO;
import gcontrol.vos.UsuarioVO;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

public class TransformerUsuarios implements Transformer {
	ServiceRepository rep = null;

	public TransformerUsuarios(ServiceRepository rep) {
		this.rep = rep;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections.Transformer#transform(java.lang.Object)
	 */
	public Object transform(Object arg0) {
		return new UsuarioPO((UsuarioVO) arg0, rep);
	}

}