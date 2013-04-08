package es.ieci.tecdoc.isicres.api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class IsicresSpringApplicationContextProvider  implements ApplicationContextAware  {
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Wiring the ApplicationContext into a static method
		IsicresSpringAppContext.setApplicationContext(ctx);
        
    }
}
