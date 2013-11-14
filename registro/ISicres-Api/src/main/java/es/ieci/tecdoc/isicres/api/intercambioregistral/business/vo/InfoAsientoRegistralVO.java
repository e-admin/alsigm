package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.List;

import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Clase que representa un asiento registral con información extendida de los interesados
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InfoAsientoRegistralVO extends AsientoRegistralVO {

	private static final long serialVersionUID = 1331989888965965890L;
	/**
	 * Lista de interesados del asiento registral.
	 */
	private List<InteresadoExReg> interesadosExReg = null;

	public List<InteresadoExReg> getInteresadosExReg() {
		return interesadosExReg;
	}

	public void setInteresadosExReg(List<InteresadoExReg> interesadosExReg) {
		this.interesadosExReg = interesadosExReg;
	}



}
