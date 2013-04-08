package fondos.actions;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;

import fondos.model.SolicitudSerie;
import fondos.vos.ElementoCuadroClasificacionVO;
import gcontrol.vos.UsuarioVO;

/**
 * Datos de presentación de una solicitud de autorización de acciones de gestión
 * sobre series documentales. Las acciones que no pueden ser ejecutadas
 * directamente por el gestor de una serie sino que requieren una autorización
 * previa por parte de un administrador funcional son el alta de una serie, el
 * paso a vigente y la realización de cambios en su identificación
 */
public class SolicitudSeriePO extends SolicitudSerie {

	UsuarioVO solicitante = null;
	ElementoCuadroClasificacionVO serieSolicitada = null;

	ServiceRepository services = null;
	GestionControlUsuariosBI usuariosService = null;
	GestionCuadroClasificacionBI cuadroClasificacionBI = null;
	GestionSeriesBI serieBI = null;

	public SolicitudSeriePO(ServiceRepository services) {
		this.services = services;
	}

	public UsuarioVO getUsuarioSolicitante() {
		String idSolicitante = getIdUsrSolicitante();
		if (idSolicitante != null && this.solicitante == null)
			solicitante = getUsuarioBI().getUsuario(idSolicitante);
		return solicitante;
	}

	public ElementoCuadroClasificacionVO getSerieSolicitada() {
		if (serieSolicitada == null) {
			if (isTipoSolicitudAlta())
				serieSolicitada = getCuadroClasificacionBI()
						.getElementoCuadroClasificacion(getIdSerie());
			else
				serieSolicitada = getSerieBI().getSerie(getIdSerie());
		}
		return serieSolicitada;
	}

	private GestionControlUsuariosBI getUsuarioBI() {
		if (usuariosService == null)
			usuariosService = services.lookupGestionControlUsuariosBI();
		return usuariosService;
	}

	private GestionSeriesBI getSerieBI() {
		if (serieBI == null)
			serieBI = services.lookupGestionSeriesBI();
		return serieBI;
	}

	private GestionCuadroClasificacionBI getCuadroClasificacionBI() {
		if (cuadroClasificacionBI == null)
			cuadroClasificacionBI = services
					.lookupGestionCuadroClasificacionBI();
		return cuadroClasificacionBI;
	}

}