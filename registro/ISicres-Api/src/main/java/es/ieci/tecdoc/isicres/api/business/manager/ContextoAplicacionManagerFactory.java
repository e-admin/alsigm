package es.ieci.tecdoc.isicres.api.business.manager;

import es.ieci.tecdoc.isicres.api.IsicresSpringAppContext;

public class ContextoAplicacionManagerFactory {

	public static ContextoAplicacionManager getInstance(){
		ContextoAplicacionManager result= (ContextoAplicacionManager) IsicresSpringAppContext.getApplicationContext().getBean(getContextoAplicacionManagerBeanName());
		return result;
	}
	
	protected static String getContextoAplicacionManagerBeanName(){
		String result="contextoAplicacionManager";
		return result;
	}
}
