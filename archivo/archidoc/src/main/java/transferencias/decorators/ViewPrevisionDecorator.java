package transferencias.decorators;

import javax.servlet.ServletRequest;

import transferencias.TransferenciasConstants;
import transferencias.model.EstadoPrevision;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;

import common.Constants;
import common.Messages;

public class ViewPrevisionDecorator extends TransferenciasBaseDecorator {

	public String getEstado() {

		String ret = null;
		ServletRequest request = getPageContext().getRequest();
		int estado;
		estado = ((PrevisionVO) getCurrentRowObject()).getEstado();
		if (estado == EstadoPrevision.ABIERTA.getIdentificador()) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
									+ "1", request.getLocale());
		} else if (estado == EstadoPrevision.ENVIADA.getIdentificador()) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
									+ "2", request.getLocale());
		} else if (estado == EstadoPrevision.ACEPTADA.getIdentificador()) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
									+ "3", request.getLocale());
		} else if (estado == EstadoPrevision.RECHAZADA.getIdentificador()) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
									+ "4", request.getLocale());
		} else if (estado == EstadoPrevision.CERRADA.getIdentificador()) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_ESTADO_PREVISION
									+ "5", request.getLocale());
		}
		return ret;
	}

	public String getTipotransferencia() {
		// String ret=null;
		int tipotransferencia = ((PrevisionVO) getCurrentRowObject())
				.getTipotransferencia();
		return getTipotransferencia(tipotransferencia);
	}

	public String getCerrada() {
		String ret = null;
		boolean cerrada = ((DetallePrevisionVO) getCurrentRowObject())
				.isCerrada();
		ServletRequest request = getPageContext().getRequest();
		if (cerrada) {
			ret = Messages
					.getString(Constants.ETIQUETA_SI, request.getLocale());
		} else {
			ret = Messages
					.getString(Constants.ETIQUETA_NO, request.getLocale());
		}

		return ret;

	}
}
