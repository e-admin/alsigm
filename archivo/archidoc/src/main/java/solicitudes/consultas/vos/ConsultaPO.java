package solicitudes.consultas.vos;

import gcontrol.vos.ArchivoVO;

import java.util.Locale;

import salas.model.GestionSalasConsultaBI;
import salas.vos.UsuarioSalasConsultaVO;

import common.CodigoTransferenciaUtils;
import common.bi.GestionConsultasBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

public class ConsultaPO extends ConsultaVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	GestionSistemaBI sistemaBI = null;
	GestionConsultasBI consultasBI = null;
	GestionSalasConsultaBI salasBI = null;

	ArchivoVO archivo = null;
	String telefonosolicitante = null;
	String faxsolicitante = null;
	String emailsolicitante = null;

	public ConsultaPO(Locale locale, GestionSistemaBI sistemaBI,
			GestionConsultasBI consultasBI, GestionSalasConsultaBI salasBI) {
		super();
		this.sistemaBI = sistemaBI;
		this.consultasBI = consultasBI;
		this.salasBI = salasBI;
	}

	public ConsultaPO(Locale locale, ConsultaVO consultaVO,
			ServiceRepository services) {
		super();
		POUtils.copyVOProperties(this, consultaVO);

		this.telefonosolicitante = super.getTelefonosolicitante();
		this.faxsolicitante = super.getTelefonosolicitante();
		this.emailsolicitante = super.getTelefonosolicitante();

		this.services = services;
		this.sistemaBI = services.lookupGestionSistemaBI();
		this.consultasBI = services.lookupGestionConsultasBI();
	}

	public String getCodigoTransferencia() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(this);
	}

	public String getCodigoarchivo() {
		if (archivo == null)
			archivo = sistemaBI.getArchivo(getIdarchivo());
		return (archivo != null ? archivo.getCodigo() : null);
	}

	public String getNombrearchivo() {
		if (archivo == null)
			archivo = sistemaBI.getArchivo(getIdarchivo());
		return (archivo != null ? archivo.getNombre() : null);
	}

	public String getNombreUsuarioSala() {
		UsuarioSalasConsultaVO usuarioSala = null;
		if (StringUtils.isNotEmpty(idusrcsala))
			usuarioSala = salasBI.getUsuarioById(idusrcsala, false);
		return (usuarioSala != null ? usuarioSala.getNombre() : null);
	}

	public boolean getTieneUnidadesDocumentales() {
		int numDetalles = consultasBI.getNumDetalles(this.getId());

		if (numDetalles > 0)
			return true;
		return false;
	}
}