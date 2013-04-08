package es.ieci.tecdoc.fwktd.time;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FactoryTimeService implements ApplicationContextAware {


	private Properties properties;
	private ApplicationContext applicationContext;
	private final String PROPERTY_BEAN_TIME_SERVICE = "fwktd-time.service.bean";

	public FactoryTimeService(Properties properties){
		this.properties = properties;
	}

	public TimeService getTimeService(){
		return (TimeService)applicationContext.getBean((String)properties.get(PROPERTY_BEAN_TIME_SERVICE));
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

}
