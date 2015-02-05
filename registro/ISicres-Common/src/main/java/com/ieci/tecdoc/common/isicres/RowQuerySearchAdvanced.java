package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ieci.tecdoc.common.keys.IDocKeys;

public class RowQuerySearchAdvanced implements Serializable {

	private FieldSearchAvanced fieldSearchAvanced;
	private Integer idoperator;
	private String valueWhere;
	private String nexo;
	private Integer rowId;

	public RowQuerySearchAdvanced() {
		super();
	}

	public FieldSearchAvanced getFieldSearchAvanced() {
		return fieldSearchAvanced;
	}

	public void setFieldSearchAvanced(FieldSearchAvanced fieldSearchAvanced) {
		this.fieldSearchAvanced = fieldSearchAvanced;
	}

	public Integer getIdoperator() {
		return idoperator;
	}

	public void setIdoperator(Integer idoperator) {
		this.idoperator = idoperator;
	}

	public String getValueWhere() {
		return valueWhere;
	}

	public void setValueWhere(String valueWhere) {
		this.valueWhere = valueWhere;
	}

	public String getNexo() {
		return nexo;
	}

	public void setNexo(String nexo) {
		this.nexo = nexo;
	}

	/**
	 * @return the rowId
	 */
	public Integer getRowId() {
		return rowId;
	}

	/**
	 * @param rowId
	 *            the rowId to set
	 */
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public static List transformToList(QuerySearchAvanced querySearchAdvanced) {
		List rowList = new ArrayList();

		if (querySearchAdvanced.getFieldSearchAvanced() != null
				&& querySearchAdvanced.getFieldSearchAvanced().length > 0) {
			FieldSearchAvanced[] fieldSearchAdvanced = querySearchAdvanced
					.getFieldSearchAvanced();

			for (int i = 0; i < fieldSearchAdvanced.length; i++) {
				FieldSearchAvanced fieldSearch = fieldSearchAdvanced[i];
				int operator = querySearchAdvanced.getIdOperator()[i];
				String filtro = querySearchAdvanced.getValueWhere()[i];
				String nexo = querySearchAdvanced.getNexo()[i];

				RowQuerySearchAdvanced row = new RowQuerySearchAdvanced();
				row.setFieldSearchAvanced(fieldSearch);
				row.setIdoperator(new Integer(operator));
				row.setValueWhere(filtro);
				row.setNexo(nexo);
				row.setRowId(new Integer(i));

				rowList.add(row);
			}
		}

		return rowList;
	}

	public static Map transformToMap(QuerySearchAvanced querySearchAdvanced) {
		Map rowMap = new HashMap();

		if (querySearchAdvanced.getFieldSearchAvanced() != null
				&& querySearchAdvanced.getFieldSearchAvanced().length > 0) {
			FieldSearchAvanced[] fieldSearchAdvanced = querySearchAdvanced
					.getFieldSearchAvanced();

			for (int i = 0; i < fieldSearchAdvanced.length; i++) {
				FieldSearchAvanced fieldSearch = fieldSearchAdvanced[i];

				if (fieldSearch != null) {
					int operator = querySearchAdvanced.getIdOperator()[i];
					String filtro = querySearchAdvanced.getValueWhere()[i];
					String nexo = querySearchAdvanced.getNexo()[i];

					RowQuerySearchAdvanced row = new RowQuerySearchAdvanced();
					row.setFieldSearchAvanced(fieldSearch);
					row.setIdoperator(new Integer(operator));
					row.setValueWhere(filtro);
					row.setNexo(nexo);
					row.setRowId(new Integer(i));

					// List fieldRows = (List)rowMap.get(new
					// Integer(fieldSearch.getFieldConf().getFieldId()));
					List fieldRows = (List) rowMap.get(IDocKeys.IDOC_FLD_PREFIX
							+ fieldSearch.getFieldConf().getFieldId());
					if (fieldRows == null) {
						fieldRows = new ArrayList();
					}
					fieldRows.add(row);
					rowMap.put(IDocKeys.IDOC_FLD_PREFIX
							+ fieldSearch.getFieldConf().getFieldId(),
							fieldRows);
					// rowMap.put(new
					// Integer(fieldSearch.getFieldConf().getFieldId()),
					// fieldRows);
				}
			}
		}

		return rowMap;
	}
}
