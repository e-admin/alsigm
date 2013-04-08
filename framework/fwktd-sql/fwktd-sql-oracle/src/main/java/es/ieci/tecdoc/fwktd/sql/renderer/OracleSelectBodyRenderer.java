/*
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectBody;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderBy;
import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;

/**
 * <code>SelectBodyRenderer</code> para Oracle
 * 
 * @author IECISA
 */
public class OracleSelectBodyRenderer extends SelectBodyRenderer {

	public void visit(ISelectBody selectBody) {
		/*
		 * Completa el SqlString generado las clases hija de SelectBody
		 * añadiéndole la parte OrderBy y la parte Limit
		 */
		StringBuffer buffer = new StringBuffer(selectBody.getSqlString());

		// Añadimos la parte orderBy
		if (selectBody.getOrderBy() != null) {

			selectBody.getOrderBy().accept(this);
			buffer.append(" ").append(selectBody.getOrderBy().getSqlString());
		}

		/*
		 * Si hay claúsula LIMIT lo que hacemos es meter la consulta como una
		 * subselect dentro de otra que haga el limit utilizando una condición
		 * sobre la columna rownum
		 */
		if (selectBody.getLimit() != null) {

			StringBuffer mainSelect = new StringBuffer();
			mainSelect.append("SELECT * FROM (").append(buffer).append(
					") WHERE rownum < ");
			if (selectBody.getLimit().isRowCountJdbcParameter()) {
				mainSelect.append("?+1");
			} else {
				mainSelect.append(selectBody.getLimit().getRowCount() + 1);
			}

			buffer = mainSelect;
		}

		selectBody.setSqlString(buffer.toString());
	}

	public void visit(OrderBy orderBy) {

		if (orderBy.isRandom()) {
			orderBy.setSqlString("ORDER BY dbms_random.value");

		} else {
			// Si no es aleatorio el orderBy puede generarse según el standard
			super.visit(orderBy);
		}
	}

	@Override
	protected String decorate(String name) {
		return SqlUtils.doubleQuotes(name);
	}
}
