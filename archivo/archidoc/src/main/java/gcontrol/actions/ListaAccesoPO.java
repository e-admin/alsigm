package gcontrol.actions;

import gcontrol.view.TransformerUsuariosVOToUsuariosPO;
import gcontrol.vos.ListaAccesoVO;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class ListaAccesoPO extends ListaAccesoVO {

	ListaAccesoVO listaAccesoVO = null;
	ServiceRepository services = null;
	transient GestionControlUsuariosBI usuariosService = null;

	public ListaAccesoPO(ServiceRepository servicesRepository,
			ListaAccesoVO listaAccesoVO) {
		this.listaAccesoVO = listaAccesoVO;
		POUtils.copyVOProperties(this, listaAccesoVO);
		this.services = servicesRepository;
		getUsuarioBI();
	}

	public List getUsuariosEnLista() {
		List usuarios = getUsuarioBI().getUsuariosEnListaAcceso(getId());
		CollectionUtils.transform(usuarios,
				new TransformerUsuariosVOToUsuariosPO(services));
		return usuarios;
	}

	public List getGruposEnLista() {
		return usuariosService.getGruposEnListaAcceso(getId());
	}

	public List getOrganosEnLista() {
		return usuariosService.getOrganosEnListaAcceso(getId());
	}

	private GestionControlUsuariosBI getUsuarioBI() {
		if (usuariosService == null)
			usuariosService = services.lookupGestionControlUsuariosBI();
		return usuariosService;
	}
}
