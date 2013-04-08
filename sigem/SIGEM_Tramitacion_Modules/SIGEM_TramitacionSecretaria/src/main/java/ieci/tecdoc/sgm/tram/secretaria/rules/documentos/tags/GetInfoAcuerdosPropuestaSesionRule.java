package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * En la generación de la certificación de acuerdos  de la sesión plenaria para obtener los campos
 * que no sean NI VALIDADOS , NI FECHAS, NI MULTIVALORES de la entidad propuesta-Sesion y Propuesta se utilizará esta regla.
 * @author IECISA
 *
 */
public class GetInfoAcuerdosPropuestaSesionRule implements IRule {

    /**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(GetInfoAcuerdosPropuestaSesionRule.class);
    public void cancel(IRuleContext rulectx) throws ISPACRuleException {


    }

    public Object execute(IRuleContext rulectx) throws ISPACRuleException {
        String field = rulectx.get("property");
        String entity = rulectx.get("entity");

        String valor="";
        IClientContext ctx =rulectx.getClientContext();
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

            if(StringUtils.equals(StringUtils.upperCase(entity),SecretariaConstants.ENTITY_PROPUESTA)){
            		itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA,
            				   " WHERE ID IN ( SELECT "+SecretariaConstants.FIELD_PROPUESTA_SESION_ID_PROPUESTA +
            		            " FROM "+SecretariaConstants.ENTITY_PROPUESTA_SESION +" WHERE  ID="+key+")");

            }
            else{
            		itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PROPUESTA_SESION,
            				" WHERE  ID="+key+")))");
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
            logger.error("Error en GetInfoAcuerdosPropuestaSesionRule:execute", e);
            throw new ISPACRuleException("Error GetInfoAcuerdosPropuestaSesionRule:execute"+e);
        }

    }

    public boolean init(IRuleContext rulectx) throws ISPACRuleException {

        return true;
    }

    public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

        return true;
    }

}
