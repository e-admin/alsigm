package com.ieci.tecdoc.common.adapter;

import com.ieci.tecdoc.common.utils.adapter.RuleContext;

/**
 * Interfaz de eventos.
 * 
 * Todas las clases que implementen la gestión de eventos deben seguir esta
 * interfaz
 * 
 */
public interface EventsManager {

	/**
	 * Método en el cual se inicializan las variables necesarias por el evento
	 * 
	 * @param ruleCtx
	 * @throws Exception
	 */
	public void init(RuleContext ruleCtx) throws Exception;

	/**
	 * Método en el que se realizan las operaciones que ha de hacer el evento
	 * 
	 * @param ruleCtx
	 * @return
	 * @throws Exception
	 */
	public Object execute(RuleContext ruleCtx) throws Exception;

	/**
	 * Método para comprobar la validad del estado para la ejecucion del evento.
	 * O bien la correcta ejecucion de este
	 * 
	 * @param ruleCtx
	 * @return
	 * @throws Exception
	 */
	public boolean validate(RuleContext ruleCtx) throws Exception;

	/**
	 * Método en el que definiremos las operaciones necesarias para reestablecer
	 * la informacion al estado anterior a la ejecucion del evento
	 * 
	 * @param ruleCtx
	 * @throws Exception
	 */
	public void cancel(RuleContext ruleCtx) throws Exception;

	/**
	 * Método en el que definiremos las operaciones necesarias para realizar una
	 * copia de seguridad antes de ejecutar el evento
	 * 
	 * @param ruleCtx
	 * @throws Exception
	 */
	public void dumpCtx(RuleContext ruleCtx) throws Exception;
}
