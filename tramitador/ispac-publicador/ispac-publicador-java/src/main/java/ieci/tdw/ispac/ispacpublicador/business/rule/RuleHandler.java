/*
 * Created on 11-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.rule;

import ieci.tdw.ispac.ispacpublicador.business.action.ActionError;
import ieci.tdw.ispac.ispacpublicador.business.action.ActionFactory;
import ieci.tdw.ispac.ispacpublicador.business.action.ActionHandler;
import ieci.tdw.ispac.ispacpublicador.business.action.IAction;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.condition.ConditionError;
import ieci.tdw.ispac.ispacpublicador.business.condition.ConditionFactory;
import ieci.tdw.ispac.ispacpublicador.business.condition.ConditionHandler;
import ieci.tdw.ispac.ispacpublicador.business.condition.ICondition;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ConditionException;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ErrorCode;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.vo.RuleVO;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * Clase encargada de manejar un reglas.
 * @author Ildefonso Noreña
 *
 */
public class RuleHandler {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(RuleHandler.class);


    /**
     * Información de Contenxto de ejecución de la regla
     */
    private RuleContext rctx;

    /**
     * Construye un nuevo RuleHandler inicializando el Contexto de la regla
     * @param rctx Contexto de ejecución de la regla
     */
    public RuleHandler(RuleContext rctx){
        this.rctx = rctx;
    }

    /**
     * Recorrerá cada una de las reglas incluidas en <code>'list'</code> y
     * las irá procesando.
     * @param list lista de reglas a procesar
     */
    public void processsRuleList(List list){
        boolean continuar = true;
        //Procesamos las reglas mientras haya reglas y mientras no se produzca algun error en la ejecucion de alguna
        for (Iterator iter = list.iterator(); iter.hasNext() && continuar;) {
            RuleVO vo = (RuleVO) iter.next();

            if (logger.isInfoEnabled())
                logger.info("Procesando Regla: "+vo.getId());

            //Procesamos la regla
            continuar = processRule(vo);
        }
    }

    /**
     * @param vo Value Object que contiene la información de la regla a procesar
     * @return true si la regla se ha procesado correctamente, false en caso contrario
     */
    private boolean processRule(RuleVO vo){
        ICondition condition = null;
        IAction action = null;
        try {
            //Si el id de la condicion = -1, se considera que no es necesario evaluar la condicion, se pasaria directamente a ejecutar la accion, en caso contraro...
            if (vo.getIdCondicion() != -1){
                //...se procedera a evaluar la condicion
                ConditionFactory conditionFactory = new ConditionFactory();
                //Obtenemos una instanacia de la Condicion segun su id
                condition = conditionFactory.instanceCondition(vo.getIdCondicion());

                ConditionHandler conditionHdlr = new ConditionHandler();
                //Si la evaluacion de la condicion da resultado negativo...
                if (!conditionHdlr.evaluate(condition, rctx, vo))
                    //... cortamos el procesamiento de esta regla ya que no se podra ejecutar la accion
                    return true;
            }

	        //Procesando la Accion
	        ActionFactory actionFactory = new ActionFactory();
	        //Obtenemos una instanacia de la Accion segun su id
	        action = actionFactory.instanceAction(vo.getIdAccion());

            if (logger.isInfoEnabled())
                logger.info("Ejecutando la accion: "+action.getClass().getName());

            //Construimos el Conexto con los atributos a pasar a la accion obtenidos de la regla
            AttributeContext attCtx = new AttributeContext(vo.getAtributos());
            ActionHandler actionHdlr = new ActionHandler();
            return actionHdlr.execute(action, rctx, attCtx, vo);

        } catch (ConditionException e) {
            RuleError error = new ConditionError(vo.getId(), vo.getIdCondicion(), condition.getClass().getName(), e.getMessage(), ErrorCode.ERROR_ACCION);
            rctx.addMessage(error);
            RenderException.show(logger, e, "La evaluación de la condición ha producido un error: ");
            return false;
        } catch (ActionException e) {
            RuleError error = new ActionError(vo.getId(),vo.getIdAccion(), action.getClass().getName(), e.getMessage(), ErrorCode.ERROR_CONDICION);
            rctx.addMessage(error);
            RenderException.show(logger, e, "La ejecución de la acción ha producido un error: ");
            return false;
        } catch (Exception e) {
            RuleError error = new ActionError(vo.getId(),vo.getIdAccion(), action.getClass().getName(), e.getMessage(), ErrorCode.ERROR_DESCONOCIDO);
            rctx.addMessage(error);
            RenderException.show(logger, e, "El procesamiento de la regla ha producido un error: ");
            return false;
        }
    }
}