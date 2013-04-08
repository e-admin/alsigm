package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.typehandler;

import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoRegistroEnumVO;

/**
 * TypeHandler de Ibatis para convertir en el retorno de las consultas SQL
 * los valores de un estado de registro (un integer) en un <code>EstadoRegistroEnumVO</code>
 * Y lo mismo en sentido inverso cuando se quiere pasar como parámetro un <code>EstadoRegistroEnumVO</code>
 * a una SQL
 */
public class EstadoRegistroEnumTypeHandler implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
			int value = getter.getInt();
		   if (getter.wasNull()) {
		   return null;
		   }
		   EstadoRegistroEnumVO estado = EstadoRegistroEnumVO.getEnum(value);
		   
		   return estado;

	}

	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		if(parameter==null)
		{
			setter.setNull(Types.INTEGER);
		}
		else
		{
			EstadoRegistroEnumVO estado = (EstadoRegistroEnumVO)parameter;
			setter.setInt(estado.getValue());
		}

	}

	public Object valueOf(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
