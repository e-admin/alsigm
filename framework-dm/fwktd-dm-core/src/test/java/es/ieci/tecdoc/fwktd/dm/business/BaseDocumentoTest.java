/*package es.ieci.tecdoc.fwktd.dm.business;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dm.business.config.Configuration;
import es.ieci.tecdoc.fwktd.dm.business.config.ConfigurationFactory;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;

@ContextConfiguration( { "/beans/fwktd-dm-core-applicationContext.xml" })
public abstract class BaseDocumentoTest extends AbstractJUnit4SpringContextTests {

	protected static final String CONFIGURATION_FACTORY_BEAN = "fwktd_dm_configurationFactoryBean";
	protected static final String GESTION_DOCUMENTAL_SERVICE_FACTORY_BEAN = "fwktd_dm_serviceFactory";

	protected Configuration getConfiguration() {
		ConfigurationFactory configurationFactory = (ConfigurationFactory) applicationContext
				.getBean(CONFIGURATION_FACTORY_BEAN);
		return configurationFactory.getConfiguration();
	}

	protected GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
		return (GestionDocumentalServiceFactory) applicationContext
			.getBean(GESTION_DOCUMENTAL_SERVICE_FACTORY_BEAN);
	}

}
*/
