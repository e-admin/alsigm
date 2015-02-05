/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * TypeHandlerCallback para guardar la información de una consulta en una lista
 * de Map<String, Object>.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GenericResultMapTypeHandlerCallback implements TypeHandlerCallback {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#getResult(com.ibatis.sqlmap.client.extensions.ResultGetter)
	 */
	public List<Map<String, Object>> getResult(ResultGetter getter)
			throws SQLException {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		ResultSet rs = getter.getResultSet();
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		List<String> colNames = new ArrayList<String>();

		// Obtener los nombres de las columnas en MAYÚSCULAS. El índice empieza
		// en 1.
		for (int i = 1; i < numColumns + 1; i++) {
			colNames.add(rsmd.getColumnName(i).toUpperCase());
		}

		Map<String, Object> row;

		// do while loop is used here since in iBatis, the resultset begins at
		// the first record unlike jdbc where the cursor is positioned before
		// the first record.
		do {

			row = new HashMap<String, Object>();

			for (int colIndex = 1; colIndex < numColumns + 1; colIndex++) {
				row.put(colNames.get(colIndex - 1),
						getColumnValue(rs, colIndex, rsmd));
			}

			if (row.size() != 0) {
				result.add(row);
			}

		} while (rs.next());

		return result;
	}

	protected Object getColumnValue(ResultSet rs, int colIndex,
			ResultSetMetaData rsmd) throws SQLException {

		Object value = null;

		// Tipo de la columna
		int colType = rsmd.getColumnType(colIndex);
		switch (colType) {
		
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			
			/*
			 * Nota: en Oracle, el método "getObject" no devuelve la información
			 * de la hora en las columnas de tipo DATE.
			 */
			value = rs.getTimestamp(colIndex);
			break;

		default:
			value = rs.getObject(colIndex);
			break;
		}

		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#setParameter(com.ibatis.sqlmap.client.extensions.ParameterSetter,
	 *      java.lang.Object)
	 */
	public void setParameter(ParameterSetter setter, Object parameter) {
		// Not Used
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#valueOf(java.lang.String)
	 */
	public Object valueOf(String s) {
		// Not Used
		return null;
	}

}
