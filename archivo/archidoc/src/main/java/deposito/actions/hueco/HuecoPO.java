package deposito.actions.hueco;

import gcontrol.vos.ArchivoVO;

import org.apache.log4j.Logger;

import transferencias.TransferenciasConstants;
import transferencias.vos.RelacionEntregaVO;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoVO;
import deposito.vos.UInsDepositoVO;

public class HuecoPO extends HuecoVO {

	Logger logger = Logger.getLogger(HuecoPO.class);

	ServiceRepository services = null;

	GestorEstructuraDepositoBI _depositoService = null;
	GestionRelacionesEntregaBI _relacionService = null;
	GestionSistemaBI _sistemaService = null;

	UInsDepositoVO uinsDepositoVO = null;
	String nombreFormato = null;
	RelacionEntregaVO relacionEntregaVO = null;

	public HuecoPO(HuecoVO huecoVO, ServiceRepository services) {
		POUtils.copyVOProperties(this, huecoVO);
		this.services = services;

		_depositoService = services.lookupGestorEstructuraDepositoBI();
		_relacionService = services.lookupGestionRelacionesBI();
		_sistemaService = services.lookupGestionSistemaBI();

	}

	public UInsDepositoVO getUnidInstalacion() {
		if (uinsDepositoVO == null && getIduinstalacion() != null) {
			uinsDepositoVO = _depositoService
					.getUinsEnDeposito(getIduinstalacion());
		}
		return uinsDepositoVO;
	}

	public String getNombreformato() {
		if (getIdformato() != null && nombreFormato == null) {
			FormatoHuecoVO formatoHueco = _depositoService
					.getFormatoHueco(getIdformato());
			nombreFormato = formatoHueco.getNombre();
		}
		return nombreFormato;
	}

	public RelacionEntregaVO getRelacion() {
		if (getIdRelEntrega() != null && relacionEntregaVO == null) {
			relacionEntregaVO = _relacionService
					.getRelacionXIdRelacion(getIdRelEntrega());
		}

		return relacionEntregaVO;
	}

	public String getCodigoTransferencia() {

		if (getRelacion() != null) {
			ArchivoVO archivo = _sistemaService.getArchivo(getRelacion()
					.getIdarchivoreceptor());
			String codigoArchivo = (archivo != null ? archivo.getCodigo()
					: null);
			RelacionEntregaVO relacionEntregaVO = getRelacion();
			return CodigoTransferenciaUtils.getCodigoTransferencia(
					relacionEntregaVO.getAno(), codigoArchivo, new Integer(
							relacionEntregaVO.getOrden()),
					TransferenciasConstants.FORMAT_ORDEN);
		} else {
			return Constants.STRING_EMPTY;
		}

	}
}
