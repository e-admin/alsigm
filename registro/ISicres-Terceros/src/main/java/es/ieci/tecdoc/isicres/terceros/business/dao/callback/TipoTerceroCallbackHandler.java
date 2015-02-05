package es.ieci.tecdoc.isicres.terceros.business.dao.callback;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 *
 * @author IECISA
 *
 */
public class TipoTerceroCallbackHandler implements TypeHandlerCallback {

	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		setter.setInt(Integer.valueOf((String) parameter));
	}

	public Object getResult(ResultGetter getter) throws SQLException {
		int tipoTercero = getter.getInt();
		return (tipoTercero == 0) ? TERCERO_NO_VALIDADO : TERCERO_VALIDADO;
	}

	public Object valueOf(String s) {
		return s;
	}

	protected static final String TERCERO_VALIDADO = "VALIDADO";
	protected static final String TERCERO_NO_VALIDADO = "NO_VALIDADO";
}
