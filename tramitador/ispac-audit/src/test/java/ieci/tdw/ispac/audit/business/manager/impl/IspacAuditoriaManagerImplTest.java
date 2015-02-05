/**
 * 
 */
package ieci.tdw.ispac.audit.business.manager.impl;

import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAccesoAplicacionVO;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
@ContextConfiguration({ "/beans/Ispac-Audit-applicationContext.xml",
	"/beans/transaction-beans.xml",
	"/beans/datasource-beans.xml" })
public class IspacAuditoriaManagerImplTest extends AbstractDbUnitTransactionalJUnit4SpringContextTests {
	
	@Autowired
	protected IspacAuditoriaManagerImpl ispacAuditoriaManager;

	@Test
	public void testAuditAcceso() {
		
		IspacAuditEventAccesoAplicacionVO auditEventAccesoAplicacion = new IspacAuditEventAccesoAplicacionVO();
		auditEventAccesoAplicacion.setAppDescription("App Description");
		auditEventAccesoAplicacion.setAppId(new Long(1));
		auditEventAccesoAplicacion.setFecha(new Date());
		auditEventAccesoAplicacion.setIdUser("1");
		auditEventAccesoAplicacion.setUser("User 1");
		auditEventAccesoAplicacion.setUserHostName("OX35888879DC01");
		auditEventAccesoAplicacion.setUserIp("192.168.1.1");
		
		ispacAuditoriaManager.audit(auditEventAccesoAplicacion);
		

	}

	/**
	 * @return el ispacAuditoriaManager
	 */
	public IspacAuditoriaManagerImpl getIspacAuditoriaManager() {
		return ispacAuditoriaManager;
	}

	/**
	 * @param ispacAuditoriaManager el ispacAuditoriaManager a fijar
	 */
	public void setIspacAuditoriaManager(IspacAuditoriaManagerImpl ispacAuditoriaManager) {
		this.ispacAuditoriaManager = ispacAuditoriaManager;
	}
	
	
}
