package com.ieci.tecdoc.isicres.context;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ISicresServiceNonSpringImpl {

	protected static ISicresServiceNonSpringImpl _instance = null;


	public synchronized static ISicresServiceNonSpringImpl getInstance() {
		if (_instance == null) {
			_instance = new ISicresServiceNonSpringImpl();
		}

		return _instance;
	}

	/**
	 * constructor implementacion por defecto del a traves del modulo api
	 */
	public ISicresServiceNonSpringImpl() {
		super();
		new ClassPathXmlApplicationContext(
				"/beans/isicres-server-applicationContext.xml");
	}

	/**
	 * constructor para implementaciones personalizadas
	 *
	 * @param contextPath
	 */
	public ISicresServiceNonSpringImpl(String contextPath) {
		super();
		new ClassPathXmlApplicationContext(
				"/beans/isicres-server-applicationContext.xml",
				contextPath);
	}

}
