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
public class IspacAuditEventRegDistConsultaVO extends IspacAuditAbstractBasicUserEventVO {
	
	/**
	 * Mapa con los registros consultados. 
	 * La key del Map es el identificador del registro.
	 */
	private Map registros;

	/**
	 * @return el registros
	 */
	public Map getRegistros() {
		return registros;
	}

	/**
	 * @param registros el registros a fijar
	 */
	public void setRegistros(Map registros) {
		this.registros = registros;
	}

		
	
	
}
