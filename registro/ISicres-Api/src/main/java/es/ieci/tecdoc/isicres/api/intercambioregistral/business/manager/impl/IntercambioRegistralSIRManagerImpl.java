package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;


public class IntercambioRegistralSIRManagerImpl implements
		IntercambioRegistralSIRManager {

	private static Logger logger = Logger.getLogger(IntercambioRegistralSIRManagerImpl.class);

	protected ServicioIntercambioRegistral servicioIntercambioRegistral;

	public AsientoRegistralVO getAsientoRegistral(String idIntercambioRegistral) {
		return getServicioIntercambioRegistral().getAsientoRegistral(idIntercambioRegistral);
	}

	public byte[] getContenidoAnexo(String idAnexo) {
		return getServicioIntercambioRegistral().getContenidoAnexo(idAnexo);
	}


	public void validarAsientoRegistral(String id, String numeroRegistro, Date fechaRegistro){
		getServicioIntercambioRegistral().validarAsientoRegistral(id, numeroRegistro, fechaRegistro);
	}


	public AsientoRegistralVO enviarAsientoRegistral(
			AsientoRegistralFormVO asiento) {
		return getServicioIntercambioRegistral().enviarAsientoRegistral(asiento);
	}

	public List<AsientoRegistralVO> findAsientosRegistrales(
			CriteriosVO criterios) {
		return getServicioIntercambioRegistral().findAsientosRegistrales(criterios);
	}

	public EstadoAsientoRegistraVO getEstadoAsientoRegistral(
			String identificadorIntercambio) {
		EstadoAsientoRegistraVO result = getServicioIntercambioRegistral().getEstadoAsientoRegistral(identificadorIntercambio);
		return result;
	}

	public void rechazarAsientoRegistral(String identificadorIntercambio,
			TipoRechazoEnum tipoRechazoEnum, String motivo) {
		InfoRechazoVO infoRechazo = new InfoRechazoVO();
		infoRechazo.setTipoRechazo(tipoRechazoEnum);
		infoRechazo.setDescripcion(motivo);
		getServicioIntercambioRegistral().rechazarAsientoRegistral(identificadorIntercambio,infoRechazo);
	}

	/**
	 * {@inheritDoc}
	 */
	public void reenviarAsientoRegistral(String identificadorIntercambio, String usuario, String contacto, String descripcionReenvio, UnidadTramitacionIntercambioRegistralVO nuevoDestino) {

		InfoReenvioVO infoReenvio = new InfoReenvioVO();
		infoReenvio.setCodigoEntidadRegistralDestino(nuevoDestino.getCodeEntity());
		infoReenvio.setDescripcionEntidadRegistralDestino(nuevoDestino.getNameEntity());
		infoReenvio.setCodigoUnidadTramitacionDestino(nuevoDestino.getCodeTramunit());
		infoReenvio.setDescripcionUnidadTramitacionDestino(nuevoDestino.getNameTramunit());

		//TODO ¿Código de aplicación? es opcional no se envia
		//infoReenvio.setAplicacion("app1");
		infoReenvio.setContacto(contacto);
		infoReenvio.setUsuario(usuario);
		infoReenvio.setDescripcion(descripcionReenvio);
		getServicioIntercambioRegistral().reenviarAsientoRegistral(identificadorIntercambio, infoReenvio);

	}

	public ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return servicioIntercambioRegistral;
	}

	public void setServicioIntercambioRegistral(
			ServicioIntercambioRegistral servicioIntercambioRegistral) {
		this.servicioIntercambioRegistral = servicioIntercambioRegistral;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager#getHistoricoCompletoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String id) {
		
		return servicioIntercambioRegistral.getHistoricoCompletoAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager#getHistoricoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoAsientoRegistral(String id) {
		
		return servicioIntercambioRegistral.getHistoricoAsientoRegistral(id);
	}
}
