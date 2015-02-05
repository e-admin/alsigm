package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * En la generación de las notificación de acuerdos de la sesión plenaria para obtener los campos
 * que no sean NI VALIDADOS , NI FECHAS, NI MULTIVALORES de la entidad propuesta-Sesion y Propuesta se utilizará esta regla.
 * @author IECISA
 *
 */
public class GetInfoNotificacionAcuerdosPropuestaSesionRule implements IRule {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(GetInfoNotificacionAcuerdosPropuestaSesionRule.class);
    public void cancel(IRuleContext rulectx) throws ISPACRuleException {


    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        String field = rulectx.get("property");
        String entity = rulectx.get("entity");



        String valor="";
        IClientContext ctx =rulectx.getClientContext();
        int taskId=ctx.getStateContext().getTaskId();
        int entityId=ctx.getStateContext().getEntity();
        int key= ctx.getStateContext().getKey();
        if (StringUtils.isBlank(entity) ||
        		(!StringUtils.equals(StringUtils.upperCase(entity), SecretariaConstants.ENTITY_PROPUESTA) &&
        		 !StringUtils.equals(StringUtils.upperCase(entity), SecretariaConstants.ENTITY_PROPUESTA_SESION))){
                logger.warn("La propiedad entity solo soporta los valores : "+
                		SecretariaConstants.ENTITY_PROPUESTA+" y "+
                		SecretariaConstants.ENTITY_PROPUESTA_SESION);

            return "";
        }
        try {
            IInvesflowAPI invesflowAPI = ctx.getAPI();
            IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
            IItemCollection itemcol=null;
            //Estamos en el contexto de un participante
            if(SpacEntities.SPAC_DT_INTERVINIENTES==entityId){
            	//SEC_PARTIC_SESION_PLENARIA tiene el ID_PARTICIPANTE y el ID_PROPUESTA
            	if(StringUtils.equals(StringUtils.upperCase(entity), SecretariaConstants.ENTITY_PROPUESTA)){
            		itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA,
            				   " WHERE ID IN ( SELECT "+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PROPUESTA +
            		            " FROM "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION +" WHERE "+
            		            SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE+" IN (SELECT ID "+
            		            " FROM SPAC_DT_INTERVINIENTES WHERE NUMEXP='"+rulectx.getNumExp()+"' AND ID="+key+"))");

            	}
            	else{


            		itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA_SESION,
            				" WHERE ID_PROPUESTA IN ("+
                    		" SELECT ID FROM "+SecretariaConstants.ENTITY_PROPUESTA+ " WHERE ID IN ("+
                    			"SELECT "+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PROPUESTA +
                    			" FROM "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION +" WHERE "+
                    			SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE+" IN ("+
                    				" SELECT ID  FROM SPAC_DT_INTERVINIENTES WHERE NUMEXP='"+rulectx.getNumExp()+"' AND ID="+key+")))");
            	}

            }
            //Contexto documentos
            else if(SpacEntities.SPAC_DT_TRAMITES==entityId){
            	if(StringUtils.equals(StringUtils.upperCase(entity), SecretariaConstants.ENTITY_PROPUESTA)){

            		  itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA,
            				  " WHERE  ID IN ("+
            				  	"SELECT "+SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_PROPUESTA+
            				  	" FROM "+SecretariaConstants.ENTITY_NOT_ACUERDOS_PROPUESTA+" WHERE "+
                                  SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_TRAMITE+"="+taskId+
                                  " AND NUMEXP='"+rulectx.getNumExp()+"')");
            	}
            	else{
            		  itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA_SESION,
                              " WHERE NUMEXP='"+rulectx.getNumExp()+"' AND "+
                              SecretariaConstants.FIELD_PROPUESTA_SESION_ID_PROPUESTA+" IN ("+
                              " SELECT "+SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_PROPUESTA+
                              " FROM "+SecretariaConstants.ENTITY_NOT_ACUERDOS_PROPUESTA+" WHERE "+
                              SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_TRAMITE+"="+taskId+")");
            	}

            }


            if (StringUtils.isBlank(field)) {
                if(logger.isDebugEnabled()){
                    logger.debug("No se ha especificado propiedad luego el valor será vacío");
                }

            }
            if(itemcol.next()){

                    valor=itemcol.value().getString(field);
                    if(StringUtils.isBlank(valor)){
                        valor="";
                    }


            }
            else{
                if(logger.isDebugEnabled()){
                    logger.debug("No hay registro de la entidad "+
                               entity);
                }
            }

            return valor;

        } catch (Exception e) {
            logger.error("Error en GetInfoNotificacionAcuerdosPropuestaSesionRule:execute", e);
            throw new ISPACRuleException("Error GetInfoNotificacionAcuerdosPropuestaSesionRule:execute"+e);
        }

    }

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {

        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

        return true;
    }

}
