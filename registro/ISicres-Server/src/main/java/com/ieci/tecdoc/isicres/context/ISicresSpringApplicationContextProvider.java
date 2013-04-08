package com.ieci.tecdoc.isicres.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ISicresSpringApplicationContextProvider implements ApplicationContextAware {

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ISicresSpringAppContext.setApplicationContext(applicationContext);
	}

}
