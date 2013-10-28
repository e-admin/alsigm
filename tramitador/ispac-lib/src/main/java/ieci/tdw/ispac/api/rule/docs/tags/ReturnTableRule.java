package ieci.tdw.ispac.api.rule.docs.tags;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.templates.TemplateTableInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class ReturnTableRule implements IRule {

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		// Atributos de <ispactag rule=...> necesarios para la regla
		String entity = rulectx.get("entity");
		String[] columns = StringUtils.split(rulectx.get("columns"), ';');
		String[] titleColumns = StringUtils.split(rulectx.get("titleColumns"), ';');
		String query = rulectx.get("sqlquery");

		TemplateTableInfo tableInfo = null;

		try {
			IItemCollection itemcol = rulectx.getClientContext().getAPI().getEntitiesAPI().getEntities(entity, rulectx.getNumExp(), query);
			tableInfo = new TemplateTableInfo(entity, columns, titleColumns);
			tableInfo.setResults(itemcol);
		} catch (ISPACException e) {
			throw new ISPACRuleException(e);
		}

		return tableInfo;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}
}