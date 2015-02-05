package transferencias.electronicas.udoc;

import transferencias.electronicas.ficha.CamposFicha;
import transferencias.electronicas.serie.Productor;

public class InformacionUnidadDocumentalElectronica {
	private IdentificacionUnidadDocumental identificacionUnidadDocumental;
	private CamposFicha camposFicha;
	/**
	 * @return el identificacionUnidadDocumental
	 */
	public IdentificacionUnidadDocumental getIdentificacionUnidadDocumental() {
		return identificacionUnidadDocumental;
	}
	/**
	 * @param identificacionUnidadDocumental el identificacionUnidadDocumental a fijar
	 */
	public void setIdentificacionUnidadDocumental(
			IdentificacionUnidadDocumental identificacionUnidadDocumental) {
		this.identificacionUnidadDocumental = identificacionUnidadDocumental;
	}
	public void setCamposFicha(CamposFicha camposFicha) {
		this.camposFicha = camposFicha;
	}
	public CamposFicha getCamposFicha() {
		return camposFicha;
	}
	public void setCodigoProcedimiento(String codigoProcedimiento) {
		if(identificacionUnidadDocumental != null){
			identificacionUnidadDocumental.setCodigoProcedimiento(codigoProcedimiento);
		}
	}
	public Productor getProductor() {
		if(identificacionUnidadDocumental != null){
			return identificacionUnidadDocumental.getProductor();
		}
		return null;
	}

	
	
	
}
