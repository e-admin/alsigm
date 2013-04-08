/*
 * Created on 16-feb-2005
 *
 */
package transferencias.decorators;

import javax.servlet.ServletRequest;

import transferencias.TransferenciasConstants;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;

import common.Messages;

/**
 * @author ABELRL
 * 
 */
public class ViewRelacionEntregaDecorator extends TransferenciasBaseDecorator {

	public String getEstado() {
		String ret = null;
		ServletRequest request = getPageContext().getRequest();
		int estado;
		estado = ((RelacionEntregaVO) getCurrentRowObject()).getEstado();
		if (estado == EstadoREntrega.ABIERTA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "." + EstadoREntrega.ABIERTA.getIdentificador(),
					request.getLocale());
		} else if (estado == EstadoREntrega.ENVIADA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "." + EstadoREntrega.ENVIADA.getIdentificador(),
					request.getLocale());
		} else if (estado == EstadoREntrega.RECIBIDA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "." + EstadoREntrega.RECIBIDA.getIdentificador(),
					request.getLocale());
		} else if (estado == EstadoREntrega.SIGNATURIZADA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "."
							+ EstadoREntrega.SIGNATURIZADA.getIdentificador(),
					request.getLocale());
		} else if (estado == EstadoREntrega.CON_ERRORES_COTEJO
				.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "."
							+ EstadoREntrega.CON_ERRORES_COTEJO
									.getIdentificador(), request.getLocale());
		} else if (estado == EstadoREntrega.CORREGIDA_ERRORES
				.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "."
							+ EstadoREntrega.CORREGIDA_ERRORES
									.getIdentificador(), request.getLocale());
		} else if (estado == EstadoREntrega.VALIDADA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "." + EstadoREntrega.VALIDADA.getIdentificador(),
					request.getLocale());
		} else if (estado == EstadoREntrega.COTEJADA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
							+ "." + EstadoREntrega.COTEJADA.getIdentificador(),
					request.getLocale());
		}

		return ret;
	}

	public String getTipotransferencia() {
		// String ret=null;
		int tipotransferencia = ((RelacionEntregaVO) getCurrentRowObject())
				.getTipotransferencia();
		return getTipotransferencia(tipotransferencia);
	}

}
