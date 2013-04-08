package com.ieci.tecdoc.isicres.context;

import es.ieci.tecdoc.isicres.terceros.business.manager.InteresadoManager;
import es.ieci.tecdoc.isicres.terceros.util.InteresadosDecorator;

public class ISicresBeansProvider {

	protected static ISicresBeansProvider instance=null;

	private static final String INTERESADOS_DECORATOR = "interesadosDecorator";

	private static final String INTERESADOR_MANAGER_BEAN = "interesadoManager";

	public static ISicresBeansProvider getInstance(){
		if (instance==null){
			instance = new ISicresBeansProvider();
		}

		return instance;
	}

	public InteresadoManager getInteresadoManager() {
		InteresadoManager result = (InteresadoManager) getGenericBean(INTERESADOR_MANAGER_BEAN);
		return result;
	}

	public InteresadosDecorator getInteresadosDecorator() {
		InteresadosDecorator result = (InteresadosDecorator) getGenericBean(INTERESADOS_DECORATOR);
		return result;
	}

	public Object getGenericBean(String bean) {

		Object result = ISicresSpringContextServiceFactory.getInstance(
				ISicresSpringContextServiceFactory.NON_SPRING_INTEGRATION)
				.getBean(bean);

		return result;
	}
}
