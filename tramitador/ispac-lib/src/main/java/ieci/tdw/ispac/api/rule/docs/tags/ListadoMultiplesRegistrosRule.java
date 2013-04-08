package ieci.tdw.ispac.api.rule.docs.tags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class ListadoMultiplesRegistrosRule implements IRule {
	private final String SEPARADOR_PROPIEDADES_TAG = ";";
	private final String SEPARADOR_CAMPOS = "\t";
	private final String SEPARADOR_REGISTROS = "\n";
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		String[] properties = StringUtils.split(rulectx.get("properties"), SEPARADOR_PROPIEDADES_TAG);
		String fieldSeparator = rulectx.get("fieldseparator");
		String registrySeparator = rulectx.get("registryseparator");
		String entity = rulectx.get("entity");
		String query=rulectx.get("sqlquery");

		String[] codetables = StringUtils.splitPreserveAllTokens(rulectx.get("codetables"), SEPARADOR_PROPIEDADES_TAG);
		String[] codes = StringUtils.splitPreserveAllTokens(rulectx.get("codes"), SEPARADOR_PROPIEDADES_TAG);
		String[] values = StringUtils.splitPreserveAllTokens(rulectx.get("values"), SEPARADOR_PROPIEDADES_TAG);


		if (StringUtils.isEmpty(fieldSeparator)){
			fieldSeparator = SEPARADOR_CAMPOS;
		}else{
			fieldSeparator = StringUtils.unescapeJava(fieldSeparator);
		}
		if (StringUtils.isEmpty(registrySeparator)){
			registrySeparator = SEPARADOR_REGISTROS;
		}else{
			registrySeparator = StringUtils.unescapeJava(registrySeparator);
		}
		int numRegistros = -1;
		if (StringUtils.isNotEmpty(rulectx.get("registries"))){
			numRegistros = rulectx.getInt("registries");
		}

		StringBuffer buffer = new StringBuffer();

		try{

			if (codetables != null && codes != null && values != null){
				IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();

				Map map = new HashMap();
				map.put(entity, entity);
				String fieldNumexp = entitiesAPI.getCatalogEntity(entity).getString("CAMPO_NUMEXP");

				String sqlquery = "WHERE " + entity + "." + fieldNumexp+ " = '" + rulectx.getNumExp() +  "' ";
				for (int i = 0; i < codetables.length; i++) {
					if (StringUtils.isNotEmpty(codetables[i])){
						map.put(codetables[i], codetables[i]);
						sqlquery += " AND " + entity + "." + properties[i] + " = " + codetables[i] + "." + codes[i] + "";
					}
				}
				if(StringUtils.isNotBlank(query)){

					sqlquery+=" AND "+query;
				}

				IItemCollection itemcol = entitiesAPI.queryEntities(map, sqlquery);
				int j = 0;
				for (Iterator iter = itemcol.iterator(); iter.hasNext() && j!= numRegistros;j++) {
					IItem item = (IItem) iter.next();
					for (int i = 0; i < properties.length; i++) {
						if (i!=0){
							buffer.append(fieldSeparator);
						}
						if (StringUtils.isEmpty(values[i])){
							buffer.append(StringUtils.nullToEmpty(item.getString(entity+":"+properties[i])));
						}else{
							buffer.append(StringUtils.nullToEmpty(item.getString(codetables[i]+":"+values[i])));
						}
					}
					buffer.append(registrySeparator);
				}
			}else{
				IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI().getEntitiesAPI();
				IItemCollection itemcol = null;
				if(StringUtils.isEmpty(query)){
					itemcol=entitiesAPI.getEntities(entity, rulectx.getNumExp());
				}
				else{
					itemcol=entitiesAPI.queryEntities(entity, " WHERE NUMEXP='"+DBUtil.replaceQuotes(rulectx.getNumExp())+"' AND "+query);
				}
				int j = 0;
				for (Iterator iter = itemcol.iterator(); iter.hasNext() && j!= numRegistros;j++) {
					IItem item = (IItem) iter.next();
					for (int i = 0; i < properties.length; i++) {
						if (i!=0){
							buffer.append(fieldSeparator);
						}
						buffer.append(StringUtils.nullToEmpty(item.getString(properties[i])));
					}
					buffer.append(registrySeparator);
				}
			}
		}catch(ISPACException e){
			throw new ISPACRuleException(e);
		}
		return buffer.toString();
	}

//	private String filterEscCharacter(String separator) {
//		separator = separator.replaceAll("\\\\t", "\t");
//		separator = separator.replaceAll("\\\\n", "\n");
//		return separator;
//	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

}
