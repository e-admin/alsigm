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
public class IspacAuditEventAvisoModificacionVO extends IspacAuditAbstractBasicUserEventVO {
	
	String idAviso;
	List<IspacAuditoriaValorModificado> valoresModificados;
	/**
	 * @return el idAviso
	 */
	public String getIdAviso() {
		return idAviso;
	}
	/**
	 * @param idAviso el idAviso a fijar
	 */
	public void setIdAviso(String idAviso) {
		this.idAviso = idAviso;
	}
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
