package es.ieci.tecdoc.isicres.api.intercambioregistral.business.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {


    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Wiring the ApplicationContext into a static method
        AppContext.setApplicationContext(ctx);
        
    }
	
}
