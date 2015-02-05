package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**

 * @author iecisa
 *
 */
public class GetOrdenDelDiaRule extends GetPropuestasSesionRule{

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GetOrdenDelDiaRule.class);

	public String composeResult(IItemCollection itemcol) throws Exception {

		String result="";
		int ordinal=1;
		int subordinal=1;
		String clasificacionPropuestaAnt="";
		String textoClf="";
		boolean hayUrgencias=false;
		IEntitiesAPI entitiesAPI=ctx.getAPI().getEntitiesAPI();

		while(itemcol.next()){
			IItem propuestaSesion=itemcol.value();
			 String clf=propuestaSesion.getString(
							SecretariaConstants.ENTITY_PROPUESTA+":"+
							SecretariaConstants.FIELD_PROPUESTA_CLASIFICACION);

			 String titulo=propuestaSesion.getString(
						  SecretariaConstants.ENTITY_PROPUESTA+":"+
						  SecretariaConstants.FIELD_PROPUESTA_TITULO);
			 String tipoPropuesta=propuestaSesion.getString( SecretariaConstants.ENTITY_PROPUESTA+":"+
					  SecretariaConstants.FIELD_PROPUESTA_TIPO_PROPUESTA);
			 if(!hayUrgencias && StringUtils.equalsIgnoreCase(tipoPropuesta, SecretariaConstants.VALUE_TIPO_PROPUESTA_URGENTE))
			 {
				 hayUrgencias=true;
			 }
			 if(!StringUtils.equalsIgnoreCase(clasificacionPropuestaAnt, clf)){
				 if(logger.isDebugEnabled()){
					 logger.debug("Vamos a tratar una propuesta con una nueva clasificación: "+clf);
				 }
				 ordinal+=1;
				 subordinal=1;
				 clasificacionPropuestaAnt=clf;
				 IItemCollection itemcolClfPropuesta=entitiesAPI.queryEntities(
						 							SecretariaConstants.ENTITY_TBL_CLF_PROPUESTA  ,
						 							" WHERE VALOR='"+DBUtil.replaceQuotes(clf)+"'");
				 if(itemcolClfPropuesta.next()){
					 textoClf=itemcolClfPropuesta.value().getString("SUSTITUTO");
					 result+=ordinal+"º.-"+textoClf+"\n";
				 }
				 else{
					 logger.warn("En la tabla de validacion "+ SecretariaConstants.ENTITY_TBL_CLF_PROPUESTA+
							 	" No hay literal asocaido con el valor "+clf+
							 	"\n El campo clasificación de la propuesta con numexp"+propuestaSesion.getString( SecretariaConstants.ENTITY_PROPUESTA+":"+
							 	"NUMEXP")+" utiliza dicho valor");
					 return "ERROR";
				 }
			 }

			 result+="\tº\t"+ordinal+"."+subordinal+".-"+titulo+"\n";
			 subordinal+=1;


		}
		if(!hayUrgencias){
			ordinal+=1;
			 IItemCollection itemcolClfPropuesta=entitiesAPI.queryEntities(
						SecretariaConstants.ENTITY_TBL_CLF_PROPUESTA  ,
						" WHERE VALOR='"+DBUtil.replaceQuotes(SecretariaConstants.VALUE_CLF_PROPUESTA_URGENTE)+"'");
				if(itemcolClfPropuesta.next()){
					textoClf=itemcolClfPropuesta.value().getString("SUSTITUTO");
					result+=ordinal+"º.-"+textoClf+"\n";
				}
				else{
					 logger.warn("El asunto de urgencia se incluye siempre en el odel día , el valor para este asunto es "+
							 	SecretariaConstants.VALUE_CLF_PROPUESTA_URGENTE+
							 	" pero en la tabla de validación "+
							 	SecretariaConstants.ENTITY_TBL_CLF_PROPUESTA +
							 	" no hay valor asociado ");
					return "ERROR";
				}
		}

		return result;
	}


}
