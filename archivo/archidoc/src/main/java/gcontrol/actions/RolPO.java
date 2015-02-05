package gcontrol.actions;

import gcontrol.vos.RolVO;

import java.util.List;

import common.bi.GestionControlUsuariosBI;

public class RolPO extends RolVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	GestionControlUsuariosBI controlAccesoBI = null;
	List permisosRol = null;

	RolPO(GestionControlUsuariosBI controlAccesoBI) {
		super();
		this.controlAccesoBI = controlAccesoBI;
	}

	public List getPermisos() {
		if (permisosRol == null)
			permisosRol = controlAccesoBI.getPermisosRol(getId());
		return permisosRol;
	}
}
