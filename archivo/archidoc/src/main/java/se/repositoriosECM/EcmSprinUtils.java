package se.repositoriosECM;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ieci.tecdoc.fwktd.dm.business.config.Configuration;
import es.ieci.tecdoc.fwktd.dm.business.config.ConfigurationFactory;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;

public class EcmSprinUtils {

	public static ApplicationContext appContext;

	protected static final String CONFIGURATION_FACTORY_BEAN = "fwktd_dm_configurationFactoryBean";
	protected static final String GESTION_DOCUMENTAL_SERVICE_FACTORY_BEAN = "fwktd_dm_serviceFactory";

	public static String[] SPRING_CONTEXT_FILES = new String[] { "classpath:/beans/fwktd-dm-applicationContext.xml", };

	private static Object getSpringBean(String beanName) {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(
					SPRING_CONTEXT_FILES);
		}

		return appContext.getBean(beanName);
	}

	public static Configuration getConfiguration() {
		ConfigurationFactory configurationFactory = (ConfigurationFactory) getSpringBean(CONFIGURATION_FACTORY_BEAN);
		return configurationFactory.getConfiguration();
	}

	public static GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
		return (GestionDocumentalServiceFactory) getSpringBean(GESTION_DOCUMENTAL_SERVICE_FACTORY_BEAN);
	}

}
