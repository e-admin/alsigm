package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GenericMasterDetailEntityApp extends MasterDetailEntityApp {
	
	public GenericMasterDetailEntityApp(ClientContext context)
	{
		super(context);
	}
	
	/**
	 * Valida los parámetros de la aplicación maestro-detalle
	 * y obtiene los registros de la entidad detalle.
	 *
	 * @throws ISPACException
	 */
	public void initiate() throws ISPACException {
		
		super.initiate();
		
		if (getParameters() == null) {
			// No se han declarado los parámetros necesarios para maestro-detalle
			throw new ISPACInfo("exception.entities.form.emptyParams",
								new String[] {mAppName, 
					  			getLabel(mainEntityName + ":" + mainEntityName)});
		}
		
		EntityParameterDef detail = getParameters().getEntityDetail();
		if (detail == null) {
			// No se ha declarado la entidad de detalle
			throw new ISPACInfo("exception.entities.form.entityDetail",
								new String[] {mAppName, 
					  			getLabel(mainEntityName + ":" + mainEntityName)});
		}
		
		String detailEntityTable = detail.getTable();
		if (StringUtils.isBlank(detailEntityTable)) {
			// No se ha indicado <table> en el parámetro
			throw new ISPACInfo("exception.entities.form.paramTable.detail", 
								new String[] {mAppName, 
											  getLabel(mainEntityName + ":" + mainEntityName), 
											  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(detail))});
		}
				
		mItemBeanList = getItemBeanList(detail);
	}

	/**
	 * Obtiene los registros de la entidad detalle
	 * como una lista de ItemBean.
	 * 
	 * @param entity Definición de parámetro de entidad de detalle.
	 * @return Lista con los registros de la entidad detalle.
	 * @throws ISPACException
	 */
	protected List getItemBeanList(EntityParameterDef entity) throws ISPACException {
		
		EntityRelationDef relation = entity.getRelation();
		
		String relationType = relation.getType();
		String masterField = relation.getPrimaryField();
		String detailField = relation.getSecondaryField();
		
		String field = null;
		String fieldValue = null;
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		// Definición de la entidad asociada
		IEntityDef asociatedEntityDef = (IEntityDef) entitiesAPI.getCatalogEntity(entity.getTable());
		mSecondaryEntityId = asociatedEntityDef.getId();

		if (asociatedEntityDef == null) {
			
			// No existe una entidad con la tabla BD declarada
			throw new ISPACInfo("exception.entities.form.paramTable.noExist", 
								new String[] {mAppName, 
											  getLabel(mainEntityName + ":" + mainEntityName), 
											  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(entity))});
		}
		
		if (relationType.equals(EntityAppConstants.RELATION_TYPE_PRIMARY_KEY)) {
						
			String strPrimaryKey = mitem.getString(mPrefixMainEntity + ":" + mainEntityDef.getKeyField());
			
			if (strPrimaryKey == null) {
				
				return new ArrayList();
			}
			else {
				fieldValue = strPrimaryKey;
			}
			
			if (detailField == null) {
				// No se ha indicado <secondary-field> en el parámetro para la entidad detalle
				throw new ISPACInfo("exception.entities.form.param.detailFld", 
									new String[] {mAppName, 
												  getLabel(mainEntityName + ":" + mainEntityName), 
												  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(entity))});
			}
			else {
				field = detailField;
			}
		}
		else if (relationType.equals(EntityAppConstants.RELATION_TYPE_NUMEXP)) {
						
			String strExpedient = mitem.getString(mPrefixMainEntity + ":" + mainEntityDef.getKeyNumExp());
			
			if (strExpedient == null) {
				
				return new ArrayList();
			}
			else {
				fieldValue = strExpedient;
			}
			
			field = asociatedEntityDef.getKeyNumExp();
			
		}
		else if (relationType.equals(EntityAppConstants.RELATION_TYPE_FIELD)) {
			
			if (masterField == null) {
				// No se ha indicado <primary-field> en el parámetro para la entidad maestro
				throw new ISPACInfo("exception.entities.form.param.masterFld", 
									new String[] {mAppName, 
												  getLabel(mainEntityName + ":" + mainEntityName), 
												  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(entity))});
			}
			else {
				fieldValue = mitem.getString(mPrefixMainEntity + ":" + masterField);
			}
			
			if (detailField == null) {
				// No se ha indicado <secondary-field> en el parámetro para la entidad detalle
				throw new ISPACInfo("exception.entities.form.param.detailFld", 
									new String[] {mAppName, 
												  getLabel(mainEntityName + ":" + mainEntityName), 
												  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(entity))});
			}
			else {
				field = detailField;
			}
		}
		else {
			// Tipo de relación no válido
			throw new ISPACInfo("exception.entities.form.param.relationType", 
								new String[] {mAppName, 
											  getLabel(mainEntityName + ":" + mainEntityName), 
											  StringUtils.escapeHtml(ParametersDef.toXmlEntityParameterDef(entity))});
		}
		
		// Comprobar si se trata de un campo numérico o alfanumérico.
		//String query = "WHERE " + field + " = '" + DBUtil.replaceQuotes(fieldValue) + "'";
		String query = "WHERE " + field + " = " + DBUtil.getFieldValue(entitiesAPI.getEntityFieldProperty(
				asociatedEntityDef.getName(), field), fieldValue);
		IItemCollection itemCollection = entitiesAPI.queryEntities(asociatedEntityDef, query);
		
		return CollectionBean.getBeanList(itemCollection);
	}
	
}