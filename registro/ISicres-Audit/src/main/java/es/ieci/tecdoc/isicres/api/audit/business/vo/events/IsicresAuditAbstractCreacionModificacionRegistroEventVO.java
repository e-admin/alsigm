package es.ieci.tecdoc.isicres.api.audit.business.vo.events;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.isicres.api.audit.business.vo.IsicresAuditoriaValorModificadoVO;

public class IsicresAuditAbstractCreacionModificacionRegistroEventVO extends
	IsicresAuditAbstractBasicRegistroUserEventVO {

	private String infoRegistroXML;
	private List <IsicresAuditoriaValorModificadoVO> valores = new ArrayList<IsicresAuditoriaValorModificadoVO>();

	public List<IsicresAuditoriaValorModificadoVO> getValores() {
		return valores;
	}

	public void setValores(List<IsicresAuditoriaValorModificadoVO> valores) {
		this.valores = valores;
	}

	public String getInfoRegistroXML() {
		return infoRegistroXML;
	}

	public void setInfoRegistroXML(String infoRegistroXML) {
		this.infoRegistroXML = infoRegistroXML;
	}
}
