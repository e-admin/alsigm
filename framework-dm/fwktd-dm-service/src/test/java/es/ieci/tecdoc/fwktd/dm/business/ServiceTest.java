/*package es.ieci.tecdoc.fwktd.dm.business;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration( { "/beans/fwktd-dm-applicationContext.xml" })
public class ServiceTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void testService() {

		try {

			// Bean del core
			Object fwktd_dm_configurationResourceLoader = applicationContext.getBean("fwktd_dm_configurationResourceLoader");
			Assert.assertNotNull(fwktd_dm_configurationResourceLoader);

			// Bean de alfresco
			Object fwktd_dm_alfresco_serviceBuilder = applicationContext.getBean("fwktd_dm_alfresco_serviceBuilder");
			Assert.assertNotNull(fwktd_dm_alfresco_serviceBuilder);

			// Bean de base de datos
			Object fwktd_dm_bd_serviceBuilder = applicationContext.getBean("fwktd_dm_bd_serviceBuilder");
			Assert.assertNotNull(fwktd_dm_bd_serviceBuilder);

			// Bean de invesdoc
			Object fwktd_dm_invesdoc_serviceBuilder = applicationContext.getBean("fwktd_dm_invesdoc_serviceBuilder");
			Assert.assertNotNull(fwktd_dm_invesdoc_serviceBuilder);

		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

}*/
