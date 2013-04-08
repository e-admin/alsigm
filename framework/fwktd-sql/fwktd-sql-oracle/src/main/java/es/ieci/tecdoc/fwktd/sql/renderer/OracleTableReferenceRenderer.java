/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;

/**
 * <code>FromItemRenderer</code> para Oracle
 * 
 */
public class OracleTableReferenceRenderer extends TableReferenceRenderer {

	@Override
	public String decorate(String name) {
		return SqlUtils.doubleQuotes(name);
	}

	@Override
	protected String asDecorator() {
		return " ";
	}
}
