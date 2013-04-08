
package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * En la sesión plenaria o junta se necesita conocer los recursos que puede interponer el interesado de la propuesta
 * @author iecisa
 *
 */
public class GetRecursosPropuestaSesionRule implements IRule
{
	/**
     * Logger de la clase.
     */
    private static final Logger logger = Logger.getLogger(GetRecursosPropuestaSesionRule.class);


    public boolean init(IRuleContext rctx) throws ISPACRuleException
    {
        return true;
    }

    public boolean validate(IRuleContext rctx) throws ISPACRuleException
    {
        return true;
    }

    public Object execute(IRuleContext rctx) throws ISPACRuleException
    {

    	IClientContext cct = null;
        try
        {

			String code = ICatalogAPI.VALOR_FIELD_NAME.toUpperCase();
			cct= rctx.getClientContext();
			IInvesflowAPI invesflowAPI = cct.getAPI();
	        IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
        	String value = ICatalogAPI.SUSTITUTO_FIELD_NAME.toUpperCase();
            String multivaluefieldseparator = rctx.get("multivaluefieldseparator");
            String sqlquery="" ;
            String regId=cct.getStateContext().getKey()+"";
            DbCnt cnt=cct.getConnection();

            //Si estamos en el contexto del trámite el reg de la entidad se obtiene del contexto del documento
			if(cct.getStateContext().getEntity()==SpacEntities.SPAC_DT_TRAMITES){

				NodeList nodeList=rctx.getDocContext().getElementsByTagName("_regentityid");

				if (nodeList != null)
				{	Node regentityid = nodeList.item(0);
					//Obtengo el primer hijo que es el que tiene el id
					if(regentityid!=null){
						regentityid=regentityid.getFirstChild();
						if(regentityid!=null){
						//Obtengo el valor
						regId=regentityid.getNodeValue();
						}
					}
				}

			}
            if(logger.isDebugEnabled()){
            	Log.debug(" Ejecutando regla GetRecursosPropuestaSesionRule regiId:"+regId );
            }


            sqlquery+=" WHERE  "+SecretariaConstants.ENTITY_PARTICIPANTES_AUX+"."+SecretariaConstants.FIELD_PARTICIPANTES_AUX_ID_PARTICIPANTE+
            	" IN (SELECT "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION+"."+SecretariaConstants.FIELD_PARTICIPANTES_SESIONES_ID_PARTICIPANTE_PROPUESTA+
            		" FROM SPAC_DT_INTERVINIENTES ,  " +SecretariaConstants.ENTITY_PARTICIPANTES_SESION+" "+
            		" WHERE SPAC_DT_INTERVINIENTES.ID="+regId +
            		" AND SPAC_DT_INTERVINIENTES.ID="
            		+SecretariaConstants.ENTITY_PARTICIPANTES_SESION+"."+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE+")";

            IItemCollection participantes_propuesta_sesion=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_PARTICIPANTES_AUX,sqlquery);


			if (participantes_propuesta_sesion.next()) {

				IItem entity = participantes_propuesta_sesion.value();

				CTEntityDAO ctentity=EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt,SecretariaConstants.ENTITY_PARTICIPANTES_AUX);
				EntityDef entityDef = EntityDef.parseEntityDef(ctentity.getDefinition());
				EntityField entityField = entityDef.getField(SecretariaConstants.FIELD_PARTICIPANTES_AUX_RECURSOS);
				//el campo es multivalor se sacan todos los sustitutos de todos los campos
				DbResultSet dbrs = null;
				String stmt = null;
				StringBuffer buffer = new StringBuffer();
				try{
					String multivalueTable = MultivalueTable.getInstance().composeMultivalueTableName(ctentity.getName(), entityField.getType().getJdbcType());
					stmt = "SELECT "+SecretariaConstants.TBL_NAME_RECURSOS+".SUSTITUTO "
							  +" FROM  " + multivalueTable + ",  "+SecretariaConstants.TBL_NAME_RECURSOS
							  +" WHERE " + multivalueTable + "." + MultivalueTable.FIELD_FIELD + " = '" + DBUtil.replaceQuotes(entityField.getPhysicalName().toUpperCase())
							  +"'  AND " + multivalueTable + "." + MultivalueTable.FIELD_REG +" = " +entity.getKeyInt()
							  +"   AND " + multivalueTable + "." + MultivalueTable.FIELD_VALUE + " =  "+SecretariaConstants.TBL_NAME_RECURSOS+"."+code;
						dbrs = cct.getConnection().executeQuery(stmt);
						while(dbrs.getResultSet().next()){
							buffer.append(dbrs.getResultSet().getString(value));
							if (StringUtils.isEmpty(multivaluefieldseparator)){
								buffer.append("\n");
							}else{
								buffer.append(StringUtils.unescapeJava(multivaluefieldseparator));
							}
						}
					} catch (SQLException e) {
						throw new ISPACRuleException(e);
					}finally{
				        if (dbrs != null) {
				        	dbrs.close();
				        }
				    }
					return buffer.toString();
			}

			return "";
        }
        catch(ISPACException e)
        {
            throw new ISPACRuleException("Error obteniendo el valor sustituto.",e);
        }
        finally{
        	//cct.releaseAllConnections(); No porque estamos dentro de una transaccion
        }
    }

    public void cancel(IRuleContext rctx) throws ISPACRuleException
    {

    }
}
