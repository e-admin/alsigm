package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl.typehandler;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.StringTypeHandler;



public class StringByteArrayTypeHandlerCallback extends StringTypeHandler {
	protected final String CHARSET="UTF8";
	
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			String jdbcType) throws SQLException {
		
		try {
			if(parameter!=null){
				ps.setBytes(i, ((String) parameter).getBytes(CHARSET));
			}
		} catch (UnsupportedEncodingException e) {
			throw new SQLException(e.getLocalizedMessage());
		}
		
		
	}

	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		Object s=null;
		try {
			byte[] data=rs.getBytes(columnName);
			if(data!=null){
				s = new String(rs.getBytes(columnName),CHARSET);
			}
		} catch (UnsupportedEncodingException e) {
			throw new SQLException(e.getLocalizedMessage());
		}
		if (rs.wasNull()) {
			return null;
		} else {
			return s;
		}
	}

	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Object s=null;
		try {
			byte[] data=rs.getBytes(columnIndex);
			if(data!=null){
				s = new String(rs.getBytes(columnIndex),CHARSET);
			}
		} catch (UnsupportedEncodingException e) {
			throw new SQLException(e.getLocalizedMessage());
		}
		if (rs.wasNull()) {
			return null;
		} else {
			return s;
		}
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Object s=null;
		try {
			byte[] data=cs.getBytes(columnIndex);
			if(data!=null){
				s = new String(cs.getBytes(columnIndex),CHARSET);
			}
		} catch (UnsupportedEncodingException e) {
			throw new SQLException(e.getLocalizedMessage());
		}
		if (cs.wasNull()) {
			return null;
		} else {
			return s;
		}
	}

	public Object valueOf(String s) {
		try {
			return s.getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
