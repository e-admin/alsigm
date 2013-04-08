package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibatis.sqlmap.engine.type.JdbcTypeRegistry;
import com.ibatis.sqlmap.engine.type.StringTypeHandler;

/**
 * Extensión de <code>StringTypeHandler</code> para gestionar identificadores
 * numéricos como <code>String</code>.
 *
 * @author IECISA
 * @see StringTypeHandler
 */
public class StringTypeHandlerCallback extends StringTypeHandler {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			String jdbcType) throws SQLException {

		switch (JdbcTypeRegistry.getType(jdbcType)) {

		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			ps.setInt(i, Integer.parseInt((String) parameter));
			break;

		case Types.BIGINT:
			ps.setLong(i, Long.parseLong((String) parameter));
			break;

		case Types.FLOAT:
			ps.setFloat(i, Float.parseFloat((String) parameter));
			break;

		case Types.NUMERIC:
		case Types.DECIMAL:
		case Types.DOUBLE:
			ps.setDouble(i, Double.parseDouble((String) parameter));
			break;

		case Types.TIMESTAMP:

			Date date = null;
			try {
				date = (Date) formatter.parse((String) parameter);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
			
			Timestamp tm = new Timestamp(date.getTime());
			ps.setTimestamp(i, tm, null);
			break;
			
		default:
			ps.setString(i, ((String) parameter));
			break;
		}
	}
}