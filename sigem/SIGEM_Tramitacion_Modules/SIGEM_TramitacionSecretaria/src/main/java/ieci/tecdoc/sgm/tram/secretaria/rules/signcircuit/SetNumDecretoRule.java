package ieci.tecdoc.sgm.tram.secretaria.rules.signcircuit;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.manager.ContadorManager;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
*
*Comprueba que el firmante es el mismo que se indica en la variable
*de sistema UID_ALCALDE , en caso de que sea el alcalde se asigna
*el número de decreto y se actualiza el campo num_hojas
*
*La información que nos proporciona en contexto de la regla en la versión 6.1 de invesFlow es:
*
*<ul>
*<li>ID_INSTANCIA_PASO</li>
*<li>ID_CIRCUITO</li>
*<li>ID_INSTANCIA_CIRCUITO</li>
*<li>ID_DOCUMENTO</li>
*<li>ID_PASO</li>
*/
public class SetNumDecretoRule implements IRule{






	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SetNumDecretoRule.class);





	public void cancel(IRuleContext rulectx) throws ISPACRuleException {


	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		IClientContext ctx= rulectx.getClientContext();
		try {
			String uid_firmante="";

			IInvesflowAPI invesFlowAPI= ctx.getAPI();
			ICatalogAPI catalogAPI=invesFlowAPI.getCatalogAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			ISignAPI    signAPI = invesFlowAPI.getSignAPI();
			String id_tpdoc_decreto=catalogAPI.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_MODELO_DECRETO);
			String id_instancia_circuito=rulectx.get("ID_INSTANCIA_CIRCUITO");
			String id_circuito=rulectx.get("ID_CIRCUITO");
			String id_documento=rulectx.get("ID_DOCUMENTO");
			String id_paso=rulectx.get("ID_PASO");
			String nameFirmante=rulectx.get("FIRMANTE");
			String infopagRde="";
			IItem documento=null;
			File sourceFile=null;


			String numexp=rulectx.getNumExp();



			if(StringUtils.isBlank(id_tpdoc_decreto)){
				if(logger.isDebugEnabled()){
				logger.debug("La variable de sistema ID_TPDOC_MODELO_DECRETO no tiene ningún valor ");
				}
				return null;
			}
			//Comprobar que el documento que estamos firmando es un decreto
			IItemCollection itemcol = entitiesAPI.queryEntities("SPAC_DT_DOCUMENTOS" ,
					" WHERE ID ="+id_documento);

			if(itemcol.next() ){
				documento=itemcol.value();
				if(!StringUtils.equals(id_tpdoc_decreto, documento.getString("ID_TPDOC"))){
				if(logger.isDebugEnabled()){
					logger.debug("El tipo documental no es un decreto");
					}
					return null;
				}
				infopagRde=documento.getString("INFOPAG_RDE");
				numexp=documento.getString("NUMEXP");
			}
			//Comprobamos que el expediente sea del proc de tramitación de decreto

			 String codPcd= ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_TRAMITACION_DECRETOS);
			itemcol = entitiesAPI.queryEntities("SPAC_EXPEDIENTES" , " WHERE CODPROCEDIMIENTO ='"+codPcd+"' AND NUMEXP='"+numexp+"'");
			if(!itemcol.next()){
				if(logger.isDebugEnabled()){
					logger.debug("No es un expediente del procedimiento de tramitación de decretos, luego no asignamos número de decreto ");
					}
					return null;
			}
			//Obtenemos el id_firmante
			itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS, "WHERE " +
					"ID_INSTANCIA_CIRCUITO ="+id_instancia_circuito+
					" AND ID_CIRCUITO="+id_circuito+
					" AND ID_DOCUMENTO="+id_documento+
					" AND ID_PASO="+id_paso+"");

				IItem decreto=null;
				uid_firmante=itemcol.value().getString("ID_FIRMANTE");
				try {

					//Obtenemos el decreto a actualizar
					itemcol=entitiesAPI.queryEntities(SecretariaConstants.ENTITY_DECRETO, " WHERE NUMEXP='"+DBUtil.replaceQuotes(numexp)+"'");
					if(!itemcol.next()){
						if(logger.isDebugEnabled()){
							logger.debug("No hay registro en la entidad sec_decreto para el expediente, se crea"+rulectx.getNumExp());
						}
						//creamos el registro
						decreto= entitiesAPI.createEntity(SecretariaConstants.ENTITY_DECRETO, numexp);

					}
					else{
						decreto=itemcol.value();
					}
					//Si el decreto aún no ha sido generado y está firmando el alcalde generamos el decreto
					if(StringUtils.isBlank(decreto.getString(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO))&&
						isUIDAlcalde(ctx, uid_firmante)){
							String numDecreto="";
							if(logger.isDebugEnabled()){
								logger.debug("El alcalde ha firmado el documento, vamos a asignar el número de decreto");
							}
							numDecreto=ContadorManager.getContador(ctx, SecretariaConstants.TIPO_CONTADOR_DECRETO);
							decreto.set(SecretariaConstants.FIELD_DECRETO_NUM_DECRETO, numDecreto);
							decreto.set(SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA,DateUtil.getToday());
							decreto.set(SecretariaConstants.FIELD_DECRETO_ALCALDIA, nameFirmante);
							if(logger.isDebugEnabled()){
								logger.debug("Número de decreto"+numDecreto);
								}

					}
					//A partir del paso 2 ya tenemos el documento firmado por el primer participante del circuito
					//por lo que el documento ya está convertido a pdf y podemos contar el num de hojas.
					//además se supone que SIEMPRE el segundo firmante será el secretario.
					if(id_paso.equalsIgnoreCase("2") && StringUtils.isNotBlank(infopagRde)  ){
						int pageCount = signAPI.getNumHojasDocumentSigned(infopagRde);

						if(logger.isDebugEnabled()){
							logger.debug("SetNumDecretoRule:execute número de páginas del decreto"+pageCount);
						}
						decreto.set(SecretariaConstants.FIELD_DECRETO_NUM_HOJAS, pageCount);
						decreto.set(SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA_SECRETARIA,DateUtil.getToday());
						decreto.set(SecretariaConstants.FIELD_DECRETO_SECRETARIA, nameFirmante);
					}
					decreto.store(ctx);
				}
		        catch (Exception e) {

		        	logger.warn("Error en la regla SetNumDecretoRule", e);

				}finally {
					if(sourceFile!=null){
						FileTemporaryManager.getInstance().delete(sourceFile);
					}
				//Quien lo invoca ya esta en un contexto transacional	ctx.endTX(bCommit);
				}
		} catch (ISPACException e) {
			logger.warn("Error en la regla SetNumDecretoRule", e);
			throw new ISPACRuleException(e);
		}


		return null;

	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {


		return true;
	}

	/**
	 * Comprueba si el firmante actual esta en lista de personas que pueden llegar  a generar un decreto
	 * La variable de sistema UID_ALCALDE es un xml con la definición de los uid's cuya firma da lugar a generar un decreto
	 * El formato del XML es el siguiente
	 * <?xml version='1.0' encoding='ISO-8859-1'?>
	 *  <firmantes>
	 *     <firmante>1-116</firmante>
	 *    <firmante>1-120</firmante>
	 * </firmantes>
	 *
	 * Donde 1-116 y 1-120 son uid's
	 *
	 * @param ctx
	 * @param uid_firmante
	 * @return
	 * @throws ISPACRuleException
	 *
	 */
	private boolean isUIDAlcalde (IClientContext ctx , String uid_firmante) throws ISPACRuleException{

		try {
			String uid_alcalde= ConfigurationMgr.getVarGlobal(ctx, SecretariaConstants.UID_ALCALDE).trim();
			if( StringUtils.isBlank(uid_alcalde)){
				if(logger.isDebugEnabled()){
					logger.debug("La variable de sistema UID_ALCALDE no tiene ningún valor, por lo que nunca se llegarán a generar decretos. " );
				}
				return false;
			}
			else {
				XmlFacade xmlFacade = new XmlFacade(uid_alcalde);

				if (xmlFacade != null) {
					List  firmantes = xmlFacade.getList("firmantes/firmante");
					if (firmantes != null) {
						int i = 0;
						String firmante = "";
						for (i = 0; i < firmantes.size(); i++) {

							firmante = (String) firmantes.get(i);
							if (StringUtils.equalsIgnoreCase(uid_firmante,
									firmante)) {
								return true;

							}
						}
					}

				}
			}

		} catch (ISPACException e) {
			logger.error("Error en la regla SetNumDecretoRule:isUIDAlcalde", e);
			throw new ISPACRuleException(e);
		}
		return false;
	}


}
