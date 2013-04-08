package ieci.tecdoc.sgm.tram.secretaria.rules.libro;



import java.util.Iterator;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.helper.RuleHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import org.apache.log4j.Logger;

public abstract class SetNumHojasRule implements IRule {

	private static final Logger logger = Logger
			.getLogger(SetNumHojasRule.class);
	protected IClientContext ctx = null;


	IItemCollection documentos = null;
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	/*Inicialmente se pensó en realizar el calculo en el evento al firmar.
	 * Para no depender del proceso de firma se hará en el evento cerrar trámite
	 * public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		ctx = rulectx.getClientContext();
		int keyDocument = rulectx.getInt("ID_DOCUMENTO");

		try {
			IInvesflowAPI invesFlowAPI = ctx.getAPI();
			IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
			ISignAPI signAPI = invesFlowAPI.getSignAPI();
			IItemCollection itemcol = entitiesAPI.queryEntities(
					"SPAC_DT_DOCUMENTOS", "WHERE  ID=" + keyDocument);
			IItem document = itemcol.value();

			if (document.getString("ID_TPDOC").equalsIgnoreCase(getIdTpDoc())) {
				ctx = rulectx.getClientContext();
				String numexp=document.getString("NUMEXP");
				// Obtener el registro
				itemcol = null;
				int id_reg_ent = document.getInt("ID_REG_ENTIDAD");
				if (id_reg_ent != 0) {
					itemcol = entitiesAPI.queryEntities(getEntityName(),
							" WHERE NUMEXP='"
									+ DBUtil.replaceQuotes(numexp)
									+ "' AND ID=" + id_reg_ent);
				} else {
					itemcol = entitiesAPI.getEntities(getEntityName(), rulectx
							.getNumExp());
				}
				if (itemcol.next()) {
					IItem item = itemcol.value();
					// Si el campo  tiene valor no lo volvemos a actualizar
					if (StringUtils.isBlank(item
							.getString(getFieldNumHojasName()))) {
						int num_hojas = signAPI
								.getNumHojasDocumentSigned((document.getString("INFOPAG_RDE")));
						item.set(getFieldNumHojasName(), num_hojas);
						item.store(ctx);
					}
				} else {
					logger.warn("No hay registro de la entidad "
							+ getEntityName());

				}
			}

		} catch (Exception e) {
			logger.warn("SetNumHojasRule:execute" + e);
			throw new ISPACRuleException(e);
		}

		return null;
	}*/


	 public Object execute(IRuleContext rulectx) throws ISPACRuleException {


		 Iterator itr = documentos.iterator();
		 IInvesflowAPI invesflowAPI = ctx.getAPI();

		 try {
		 IEntitiesAPI entitiesAPI= invesflowAPI.getEntitiesAPI();
		 ISignAPI signAPI = invesflowAPI.getSignAPI();
			while(itr.hasNext()){
				IItem document=(IItem) itr.next();

				String numexp=document.getString("NUMEXP");
				// Obtener el registro
				IItemCollection itemcol = null;
				int id_reg_ent = document.getInt("ID_REG_ENTIDAD");
				if (id_reg_ent != 0) {
					itemcol = entitiesAPI.queryEntities(getEntityName(),
							" WHERE NUMEXP='"
									+ DBUtil.replaceQuotes(numexp)
									+ "' AND ID=" + id_reg_ent);
				} else {
					itemcol = entitiesAPI.getEntities(getEntityName(), rulectx
							.getNumExp());
				}
				if (itemcol.next()) {
					IItem item = itemcol.value();
				// Si el campo  tiene valor no lo volvemos a actualizar
				if (StringUtils.isBlank(item
						.getString(getFieldNumHojasName()))) {
					int num_hojas = signAPI
							.getNumHojasDocumentSigned((document.getString("INFOPAG_RDE")));
					item.set(getFieldNumHojasName(), num_hojas);
					item.store(ctx);
				}

			}
			}
		 }catch (Exception e) {
				logger.warn("SetNumHojasRule:execute" + e);
				throw new ISPACRuleException(e);
			}
		 return null;

	 }


	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		if (logger.isDebugEnabled()) {
			logger.debug("Inicio AvisoEmailRule: validate");
		}
		int idTramite = rulectx.getTaskId();
		ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		try{
			IEntitiesAPI entitiesAPI=invesflowAPI.getEntitiesAPI();
		    documentos=entitiesAPI.getEntities(SpacEntities.SPAC_DT_DOCUMENTOS, ctx.getStateContext().getNumexp(), " ID_TRAMITE="+idTramite+" AND ID_TPDOC="+getIdTpDoc()+" AND  INFOPAG_RDE IS NOT NULL");
			if(documentos==null || !documentos.next()){
				   rulectx.setInfoMessage(SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(),getKeyMessage()));
				return false;
			}


		} catch (ISPACException e) {
			logger.warn("Error al ejecutar la regla SetNumHojasRule", e);
			throw new ISPACRuleException(e);
		}
		return true;
	}

	public String getEntityName() {
		return SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR;
	}

	public String getFieldNumHojasName() {
		return SecretariaConstants.FIELD_DILIGENCIA_C_ERROR_NUM_HOJAS;
	}

	public abstract String getIdTpDoc() throws ISPACException;

	public abstract String getKeyMessage();

}
