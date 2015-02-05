/*
 * Created on 02-jun-2004
 *
 */
package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACRuleException;


public interface IRule
{
	  /**
	   *Función de inicialización del evento.
	   *@param docTXContext Documento XML con la descripción del contexto de ejecución del evento.
	   *@param itx Interface para solicitar transacciones de ISPAC
	   *@return true si la inicialización se ha ejecutado correctamente
	   */

	  public boolean init(IRuleContext rulectx)
	  	throws ISPACRuleException;

	  /**
	   *Función de validación del evento.
	   *@return La función debe devolver false si, por alguna regla de negocio, el evento no
	   *está autorizado (por ejemplo, falta por cumplimentar algún campo obligatorio)
	   */
	  public boolean validate(IRuleContext rulectx)
	  	throws ISPACRuleException;

	  /**
	   *Función de personalización del evento.
	   *Aquí se definen las acciones asociadas al evento
	   *@return Se debe devolver un objeto si el tipo de evento lo necesita
	   *(calculo de responsables, etc)
	   */
	  public Object execute(IRuleContext rulectx)
	  	throws ISPACRuleException;

	  /**
	   *El sistema invoca esta función si se encuentra con algún problema
	   *durante la ejecución del evento. Permitiría deshacer operaciones
	   *realizadas en el método execute()
	   */
	  public void cancel(IRuleContext rulectx)
	  	throws ISPACRuleException;
}
