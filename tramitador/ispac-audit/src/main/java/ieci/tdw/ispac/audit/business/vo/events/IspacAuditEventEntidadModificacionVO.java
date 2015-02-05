/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

import ieci.tdw.ispac.audit.business.vo.IspacAuditoriaValorModificado;

import java.util.List;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventEntidadModificacionVO extends IspacAuditAbstractBasicEntidadEventVO{
	

	List<IspacAuditoriaValorModificado> valoresModificados;
	
	/**
	 * @return el valoresModificados
	 */
	public List<IspacAuditoriaValorModificado> getValoresModificados() {
		return valoresModificados;
	}
	/**
	 * @param valoresModificados el valoresModificados a fijar
	 */
	public void setValoresModificados(List<IspacAuditoriaValorModificado> valoresModificados) {
		this.valoresModificados = valoresModificados;
	}
		
}
