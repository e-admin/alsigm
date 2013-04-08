/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

import java.util.Map;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventAvisoConsultaVO extends IspacAuditAbstractBasicUserEventVO {
	
	/**
	 * Mapa con los avisos consultados. 
	 * La key del Map es el identificador del aviso.
	 */
	private Map avisos;

	/**
	 * @return el avisos
	 */
	public Map getAvisos() {
		return avisos;
	}

	/**
	 * @param avisos el avisos a fijar
	 */
	public void setAvisos(Map avisos) {
		this.avisos = avisos;
	}
	
	
	
	
}
