package solicitudes.prestamos.vos;

import java.util.Locale;

import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class RevisionDocumentacionPO extends RevisionDocumentacionVO {

	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	GestionControlUsuariosBI controlAccesoBI = null;
	GestionRelacionesEntregaBI relacionBI = null;

	String nombreGestor = null;

	public RevisionDocumentacionPO(Locale locale,
			GestionControlUsuariosBI controlAccesoBI,
			GestionRelacionesEntregaBI relacionBI) {
		super();
		this.controlAccesoBI = controlAccesoBI;
		this.relacionBI = relacionBI;
	}

	public RevisionDocumentacionPO(Locale locale,
			RevisionDocumentacionVO documentacionUDocsVO,
			ServiceRepository services) {
		super();
		POUtils.copyVOProperties(this, documentacionUDocsVO);

		this.services = services;
		this.controlAccesoBI = services.lookupGestionControlUsuariosBI();
		this.relacionBI = services.lookupGestionRelacionesBI();
	}

	public String getNombreGestor() {
		if (nombreGestor == null)
			nombreGestor = controlAccesoBI.getUsuario(getIdUsrGestor())
					.getNombreCompleto();
		return nombreGestor;
	}

	public boolean isMostrarAgregar() {
		if (this.getIdAlta() != null) {
			if (!relacionBI.existeRelacion(this.getIdAlta()))
				return true;
		} else
			return true;
		return false;
	}

	public boolean isMostrarEliminar() {
		if (this.getIdAlta() != null) {
			if (relacionBI.existeRelacion(this.getIdAlta())) {
				RelacionEntregaVO relacionVO = relacionBI
						.getRelacionXIdRelacion(this.getIdAlta());
				if (relacionVO.getEstado() != EstadoREntrega.VALIDADA
						.getIdentificador())
					return true;
			}
		}
		return false;
	}

	public boolean isMostrarVer() {
		if (!isMostrarAgregar() && !isMostrarEliminar())
			return true;
		return false;
	}

	public boolean isMostrarEditar() {
		if (!isMostrarAgregar() && isMostrarEliminar())
			return true;
		return false;
	}
}