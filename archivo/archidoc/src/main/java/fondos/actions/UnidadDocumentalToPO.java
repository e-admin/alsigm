package fondos.actions;

import org.apache.commons.collections.Transformer;

import se.usuarios.AppUser;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionSolicitudesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class UnidadDocumentalToPO implements Transformer {
	GestionUnidadDocumentalBI udocBI = null;
	GestionSeriesBI serieBI = null;
	GestionControlUsuariosBI usuariosBI = null;
	GestionSolicitudesBI solicitudesBI = null;
	GestionSistemaBI sistemaBI = null;
	GestionDescripcionBI descripcionBI = null;
	GestionDocumentosElectronicosBI docsElecBI = null;
	SignaturaUdoc2PO udoc2PO = null;
	ServiceRepository services = null;
	private AppUser appUser = null;

	public UnidadDocumentalToPO(ServiceRepository services, AppUser appUser) {
		this.udocBI = services.lookupGestionUnidadDocumentalBI();
		this.serieBI = services.lookupGestionSeriesBI();
		this.usuariosBI = services.lookupGestionControlUsuariosBI();
		this.solicitudesBI = services.lookupGestionSolicitudesBI();
		this.sistemaBI = services.lookupGestionSistemaBI();
		this.descripcionBI = services.lookupGestionDescripcionBI();
		this.docsElecBI = services.lookupGestionDocumentosElectronicosBI();
		udoc2PO = new SignaturaUdoc2PO(
				services.lookupGestorEstructuraDepositoBI());
		this.services = services;
		this.setAppUser(appUser);
	}

	public UnidadDocumentalToPO(ServiceRepository services) {
		this.udocBI = services.lookupGestionUnidadDocumentalBI();
		this.serieBI = services.lookupGestionSeriesBI();
		this.usuariosBI = services.lookupGestionControlUsuariosBI();
		this.solicitudesBI = services.lookupGestionSolicitudesBI();
		this.sistemaBI = services.lookupGestionSistemaBI();
		this.descripcionBI = services.lookupGestionDescripcionBI();
		this.docsElecBI = services.lookupGestionDocumentosElectronicosBI();
		udoc2PO = new SignaturaUdoc2PO(
				services.lookupGestorEstructuraDepositoBI());
		this.services = services;
	}

	public static UnidadDocumentalToPO getInstance(ServiceRepository services) {
		return new UnidadDocumentalToPO(services);
	}

	public Object transform(Object vo) {
		UnidadDocumentalPO po = new UnidadDocumentalPO(udocBI, serieBI,
				usuariosBI, solicitudesBI, udoc2PO, sistemaBI, descripcionBI,
				docsElecBI, services, getAppUser());
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public Object transform(Object vo, AppUser user) {
		UnidadDocumentalPO po = new UnidadDocumentalPO(udocBI, serieBI,
				usuariosBI, solicitudesBI, udoc2PO, sistemaBI, descripcionBI,
				docsElecBI, services, getAppUser());
		POUtils.copyVOProperties(po, vo);
		po.setUser(user);
		return po;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public AppUser getAppUser() {
		return appUser;
	}

}