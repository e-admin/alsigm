package docelectronicos.actions;

import gcontrol.view.UsuarioPO;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

import docelectronicos.EstadoClasificador;
import docelectronicos.EstadoTareaCaptura;
import docelectronicos.MarcasClasificador;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocTCapturaVO;

public class DocClasificadorPO extends DocClasificadorVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	ServiceRepository serviceRepository = null;

	GestionControlUsuariosBI usuarioService = null;
	GestionDocumentosElectronicosBI documentosService = null;
	UsuarioPO usuarioPO = null;
	ServiceClient client = null;
	DocTCapturaVO tarea = null;

	public DocClasificadorPO(ServiceRepository serviceRepository,
			ServiceClient client, DocClasificadorVO capturaVO,
			DocTCapturaVO tarea) {
		POUtils.copyVOProperties(this, capturaVO);
		this.serviceRepository = serviceRepository;
		this.client = client;
		this.tarea = tarea;
	}

	public GestionControlUsuariosBI getUsuarioService() {
		if (usuarioService == null)
			usuarioService = serviceRepository.lookupGestionControlUsuariosBI();
		return usuarioService;
	}

	public GestionDocumentosElectronicosBI getDocumentosService() {
		if (documentosService == null)
			documentosService = serviceRepository
					.lookupGestionDocumentosElectronicosBI();
		return documentosService;
	}

	public boolean isEliminable() {
		if (tarea != null) {
			if (tarea.getEstado() == EstadoTareaCaptura.PENDIENTE.getValue()
					|| tarea.getEstado() == EstadoTareaCaptura.CON_ERRORES
							.getValue()) {
				if (getMarcas() != MarcasClasificador.FIJO) {
					if (client.hasAnyPermission(new String[] {
							AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
							AppPermissions.ADMINISTRACION_TAREAS_CAPTURA })) {
						return true;
					} else {
						if (getEstado() == EstadoClasificador.SIN_VALIDAR
								|| getEstado() == EstadoClasificador.NO_VALIDO)
							return true;
						return false;
					}
				}
				return false;

			} else
				return false;
		} else {
			if (client.hasAnyPermission(new String[] {
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
					AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION })) {
				return true;
			} else
				return false;
		}
	}

	public boolean isEditable() {
		return isEliminable();
	}

	public boolean isValidable() {
		if (tarea != null) {
			if (tarea.getEstado() == EstadoTareaCaptura.FINALIZADA.getValue()) {
				if (getEstado() == EstadoClasificador.NO_VALIDO
						|| getEstado() == EstadoClasificador.SIN_VALIDAR
						|| getEstado() == EstadoClasificador.VALIDADO) {
					return true;
				} else
					return false;
			}

		}
		return false;
	}

}
