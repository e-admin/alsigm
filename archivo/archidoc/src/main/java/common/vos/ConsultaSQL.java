/**
 *
 */
package common.vos;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbUtil;

import common.db.DBUtils;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ConsultaSQL extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DbColumnDef[] columnas = null;
	private String fromDefinition = new String();
	private String whereDefinition = new String();
	private String groupByDefinition = new String();
	private String orderByDefinition = new String();

	public ConsultaSQL(DbColumnDef[] columnas, String fromDefinition,
			String whereDefinition, String groupByDefinition,
			String orderByDefinition) {
		super();
		this.setColumnas(columnas);
		this.fromDefinition = fromDefinition;
		this.whereDefinition = whereDefinition;
		this.groupByDefinition = groupByDefinition;
		this.orderByDefinition = orderByDefinition;
	}

	public ConsultaSQL(DbColumnDef[] columnas, String fromDefinition,
			String whereDefinition, String orderByDefinition) {
		super();
		this.setColumnas(columnas);
		this.fromDefinition = fromDefinition;
		this.whereDefinition = whereDefinition;
		this.orderByDefinition = orderByDefinition;
	}

	public ConsultaSQL(DbColumnDef[] columnas, String fromDefinition,
			String whereDefinition) {
		super();
		this.setColumnas(columnas);
		this.fromDefinition = fromDefinition;
		this.whereDefinition = whereDefinition;
	}

	/* NO GENERADOS */
	public String getSelectCompleta() {
		if (ArrayUtils.isNotEmpty(getColumnas())) {
			return DBUtils.SELECT + DbUtil.getColumnNames(getColumnas());
		}
		return "";
	}

	public String getFromCompleta() {
		if (StringUtils.isNotEmpty(fromDefinition)) {
			return DBUtils.FROM + fromDefinition;
		}
		return "";
	}

	public String getWhereCompleta() {
		if (StringUtils.isNotEmpty(whereDefinition)) {
			return DBUtils.WHERE + whereDefinition;
		}
		return "";
	}

	public String getGroupByCompleta() {
		if (StringUtils.isNotEmpty(groupByDefinition)) {
			return DBUtils.GROUPBY + groupByDefinition;
		}
		return "";
	}

	public String getOrderByCompleta() {
		if (StringUtils.isNotEmpty(orderByDefinition)) {
			return DBUtils.ORDER_BY + orderByDefinition;
		}
		return "";
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getSelectCompleta());
		buffer.append(getFromCompleta());
		buffer.append(getWhereCompleta());
		buffer.append(getGroupByCompleta());
		buffer.append(getOrderByCompleta());
		return buffer.toString();
	}

	/* FIN NO GENERADOS */

	public String getFromDefinition() {
		return fromDefinition;
	}

	public String getWhereDefinition() {
		return whereDefinition;
	}

	public String getGroupByDefinition() {
		return groupByDefinition;
	}

	public String getOrderByDefinition() {
		return orderByDefinition;
	}

	public void setFromDefinition(String fromDefinition) {
		this.fromDefinition = fromDefinition;
	}

	public void setWhereDefinition(String whereDefinition) {
		this.whereDefinition = whereDefinition;
	}

	public void setGroupByDefinition(String groupByDefinition) {
		this.groupByDefinition = groupByDefinition;
	}

	public void setOrderByDefinition(String orderByDefinition) {
		this.orderByDefinition = orderByDefinition;
	}

	public void setColumnas(DbColumnDef[] columnas) {
		this.columnas = columnas;
	}

	public DbColumnDef[] getColumnas() {
		return columnas;
	}

}
