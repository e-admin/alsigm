package solicitudes.view;

import java.util.Iterator;
import java.util.List;

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.BusquedaDetalleVO;
import transferencias.actions.UsuarioToGestorPO;
import util.CollectionUtils;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionConsultasBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionPrestamosBI;

import descripcion.vos.RangoVO;
import fondos.model.ElementoCuadroClasificacion;

/**
 * 
 * Clase para mostrar en la vista detalles de préstamos/consultas resultado de
 * la búsqueda para poder devolverlas (unidades documentales en
 * préstamos/consultas)
 * 
 */
public class BusquedaDetallePO extends BusquedaDetalleVO {

	private GestionCuadroClasificacionBI cclfBI = null;
	private GestionPrestamosBI prestamosBI = null;
	private GestionConsultasBI consultasBI = null;

	private String nombreRangos = null;
	private boolean yaConsultado = false;

	public BusquedaDetallePO(GestionCuadroClasificacionBI cclfBI,
			GestionPrestamosBI prestamosBI, GestionConsultasBI consultasBI) {
		this.cclfBI = cclfBI;
		this.prestamosBI = prestamosBI;
		this.consultasBI = consultasBI;
	}

	/**
	 * Permite obtener los rangos
	 * 
	 * @return Cadena con los rangos
	 */
	public String getNombreRangos() {
		String ret = Constants.STRING_EMPTY;

		if (yaConsultado) {
			return nombreRangos;
		} else {
			// Obtener el nombre de los rangos sólo para fracciones de serie
			if (getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				if (nombreRangos == null) {
					// LLamar y obtener la lista de rangos
					if (this.rangos == null || this.rangos.size() == 0) {
						String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getRangoInicial();
						String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion().getRangoFinal();

						if ((idCampoRangoInicial != null)
								&& (idCampoRangoFinal != null))
							this.rangos = cclfBI.getRangosElementoCF(
									getIdudoc(), idCampoRangoInicial,
									idCampoRangoFinal);
					}

					if ((this.rangos != null) && (!this.rangos.isEmpty())) {
						Iterator it = this.rangos.iterator();
						while (it.hasNext()) {
							RangoVO rango = (RangoVO) it.next();
							ret += rango.getRangoInicial()
									+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
									+ rango.getRangoFinal();
							if (it.hasNext())
								ret += Constants.DELIMITADOR_RANGOS;
						}
					}

					nombreRangos = ret;
				}
			}

			yaConsultado = true;
		}
		return ret;
	}

	/**
	 * Obtener los posibles usuarios gestores de revision posibles
	 * 
	 * @return Lista de usuarios gestores
	 */
	public List getUsuariosGestoresRevDocPosibles() {
		// Obtenemos la lista de posibles gestores de revisión de documentación
		List usuariosGestoresRevDoc = prestamosBI
				.getUsuariosGestoresRevDocPosibles(getCodigoArchivo());
		CollectionUtils.transform(usuariosGestoresRevDoc,
				UsuarioToGestorPO.getInstance());

		return usuariosGestoresRevDoc;
	}

	/**
	 * Devuelve el solicitante para saber que unidad documental es la que
	 * estamos buscando.
	 * 
	 * @return
	 */
	public String getSolicitante() {
		if (getTiposolicitud() > 0
				&& getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO_INT) {
			PrestamoVO prestamo = prestamosBI.getPrestamo(getIdSolicitud());
			return prestamo.getNusrsolicitante();
		} else if (getTiposolicitud() > 0
				&& getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT) {
			ConsultaVO consulta = consultasBI.getConsulta(getIdSolicitud());
			return consulta.getNusrconsultor();
		}
		return null;
	}
}