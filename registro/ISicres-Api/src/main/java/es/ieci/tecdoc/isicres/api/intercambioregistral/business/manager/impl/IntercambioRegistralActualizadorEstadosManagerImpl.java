package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.Date;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralActualizadorEstadosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSalidaManager;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public class IntercambioRegistralActualizadorEstadosManagerImpl implements
		IntercambioRegistralActualizadorEstadosManager {

	private static Logger logger = Logger.getLogger(IntercambioRegistralActualizadorEstadosManagerImpl.class);

	/**
	 * Manager para leer o actualizar los intercambios registrales de salida
	 */
	protected IntercambioRegistralSalidaManager intercambioRegistralSalidaManager;

	protected IntercambioRegistralSIRManager intercambioRegistralSIRManager;

	public void actualizarEstadoEnviados() {
		if (logger.isDebugEnabled()) {
			logger
					.debug("Se van a actualizar los estados de los intercambios registrales ENVIADOS");
		}
		// ¿Configurar un escheduler por entidad? ¿Leer desde aquí todas las
		// entidades y ejecutar por cada una?

		List<IntercambioRegistralSalidaVO> intercambiosPendientes = getIntercambioRegistralSalidaManager()
				.getIntercambiosRegistralesSalida(
						EstadoIntercambioRegistralSalidaEnumVO.ENVIADO
								.getValue());

		if (intercambiosPendientes != null) {
			for (IntercambioRegistralSalidaVO intercambioRegistralSalidaVO : intercambiosPendientes) {
				
				// Consultamos el estado en el SIR
				if (intercambioRegistralSalidaVO.getIdIntercambioInterno() != null) {
					try {
						//obtenemos el estado del modulo intermedio
						EstadoAsientoRegistraVO estado = getIntercambioRegistralSIRManager()
								.getEstadoAsientoRegistral(
										intercambioRegistralSalidaVO
												.getIdIntercambioInterno()
												.toString());
						
						
						EstadoAsientoRegistralEnum estadoEnum = null;
						if (estado != null){
							estadoEnum = estado.getEstado();
						}
						
						
						
						//actuamos en caso de que el estado sea distinto de enviado a enviado y ack y enviado error
						// para sicres estos estados equivalen a nuestro estado Enviado en global
						if (estadoEnum != null
								&& estadoEnum != EstadoAsientoRegistralEnum.ENVIADO
								&& estadoEnum != EstadoAsientoRegistralEnum.ENVIADO_Y_ACK
								&& estadoEnum != EstadoAsientoRegistralEnum.ENVIADO_Y_ERROR
								&& estadoEnum != EstadoAsientoRegistralEnum.DEVUELTO) {

							EstadoIntercambioRegistralSalidaVO nuevoEstadoSalida = null ; 
							if (estado != null) {
								nuevoEstadoSalida = new EstadoIntercambioRegistralSalidaVO();
								estadoEnum = estado.getEstado();
								String contactoUsuario = estado.getContactoUsuario();
								//de momento no se usa
								Map<String, String> datosAdicionales = estado.getDatosAdicionales();
								Date fechaEstado = estado.getFechaEstado();
								String nombreUsuario = estado.getNombreUsuario();
								String observaciones = estado.getObservaciones();
								
								nuevoEstadoSalida.setEstado(EstadoIntercambioRegistralSalidaEnumVO.getEnum(estadoEnum.getValue()));
								nuevoEstadoSalida.setComentarios(observaciones);
								nuevoEstadoSalida.setUserName(nombreUsuario +"-"+contactoUsuario);
								nuevoEstadoSalida.setFechaEstado(fechaEstado);
								nuevoEstadoSalida.setIdExReg(intercambioRegistralSalidaVO.getId());
								
							}
							
							if (nuevoEstadoSalida.getEstado()!=null){
								getIntercambioRegistralSalidaManager()
										.updateEstado(intercambioRegistralSalidaVO,
												nuevoEstadoSalida);

								if (logger.isDebugEnabled()) {
									logger
											.debug("Actualizado el estado del intercambio registral "
													+ intercambioRegistralSalidaVO
															.getIdIntercambioInterno()
													+ " al estado "
													+ estado.getEstado().getName());
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
						logger
								.error("Error al obtener el estado del intercambio registral con id:"
										+ intercambioRegistralSalidaVO
												.getIdIntercambioInterno());
						logger
								.error("Continuamos intentando actualizar el siguiente");
					}
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger
						.debug("No hay intercambios registrales en estado ENVIADO para actualizar");
			}
		}
	}

	public IntercambioRegistralSalidaManager getIntercambioRegistralSalidaManager() {
		return intercambioRegistralSalidaManager;
	}

	public void setIntercambioRegistralSalidaManager(
			IntercambioRegistralSalidaManager intercambioRegistralSalidaManager) {
		this.intercambioRegistralSalidaManager = intercambioRegistralSalidaManager;
	}

	public IntercambioRegistralSIRManager getIntercambioRegistralSIRManager() {
		return intercambioRegistralSIRManager;
	}

	public void setIntercambioRegistralSIRManager(
			IntercambioRegistralSIRManager intercambioRegistralSIRManager) {
		this.intercambioRegistralSIRManager = intercambioRegistralSIRManager;
	}

}
