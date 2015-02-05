package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.engine.type.BooleanTypeHandler;
import com.ibatis.sqlmap.engine.type.JdbcTypeRegistry;

/**
 * <code>TypeHandler</code> que modifica el comportamiento por defecto del
 * <code>BooleanTypeHandler</code> para permitir especificar la transformación a
 * aplicar sobre las escrituras de los valores en base de datos. Esto se
 * consigue especificando uno de los siguientes tipos JDBC:
 * <ul>
 * <li>INTEGER, SMALLINT o NUMERIC: el valor verdadero se representa como un
 * <i>1</i> y el falso como un <i>0</i>.</li>
 * <li>CHAR: el valor verdadero se representa como <i>T</i> y el falso como
 * <i>F</i>.</li>
 * <li>VARCHAR: el valor verdadero se representa como <i>true</i> y el falso
 * como <i>false</i>.</li>
 * </ul>
 * 
 * Si no se especifica ninguno de estos tipos JDBC el comportamiento obtenido
 * será el que da la clase <code>BooleanTypeHandler</code>.
 * 
 * @see BooleanTypeHandler
 * @author IECISA
 * 
 */
public class BooleanTypeHandlerCallback extends BooleanTypeHandler {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			String jdbcType) throws SQLException {

		Boolean value = (Boolean) parameter;

		switch (JdbcTypeRegistry.getType(jdbcType)) {
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.NUMERIC:
			ps.setInt(i, value.booleanValue() ? 1 : 0);
			break;
		case Types.CHAR:
			ps.setString(i, value.booleanValue() ? "T" : "F");
			break;
		case Types.VARCHAR:
			ps.setString(i, value.booleanValue() ? Boolean.TRUE.toString()
					: Boolean.FALSE.toString());
			break;
		default:
			super.setParameter(ps, i, parameter, jdbcType);
		}

	}
}
