package transferencias.decorators;

import javax.servlet.ServletRequest;

import org.displaytag.decorator.TableDecorator;

import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.vos.PrevisionVO;

import common.Messages;

public class TransferenciasBaseDecorator extends TableDecorator {
	public String getTipoprevision() {
		String ret = null;
		ServletRequest request = getPageContext().getRequest();
		boolean detallada = ((PrevisionVO) getCurrentRowObject()).isDetallada();
		if (detallada) {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_PREVISION_DETALLADA,
							request.getLocale());
		} else {
			ret = Messages
					.getString(
							TransferenciasConstants.LABEL_TRANSFERENCIAS_PREVISION_NO_DETALLADA,
							request.getLocale());
		}

		return ret;
	}

	protected String getTipotransferencia(int tipotransferencia) {
		ServletRequest request = getPageContext().getRequest();
		String ret = null;
		if (tipotransferencia == TipoTransferencia.ORDINARIA.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.LABEL_TRANSFERENCIAS_TIPO_TRANS
							+ "1", request.getLocale());
		} else if (tipotransferencia == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.LABEL_TRANSFERENCIAS_TIPO_TRANS
							+ "3", request.getLocale());
		} else if (tipotransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
				.getIdentificador()) {
			ret = Messages.getString(
					TransferenciasConstants.LABEL_TRANSFERENCIAS_TIPO_TRANS
							+ "2", request.getLocale());
		}
		return ret;
	}

}
