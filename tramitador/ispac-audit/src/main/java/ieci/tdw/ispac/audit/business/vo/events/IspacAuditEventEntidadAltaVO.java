/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventEntidadAltaVO extends IspacAuditAbstractBasicEntidadEventVO{
	
	String content;

	/**
	 * @return el content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content el content a fijar
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
