/*
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.ieci.tecdoc.fwktd.core.model.LabelValue;

/**
 * Clase para mapear ResultSet a las propiedades de un PropertyBean
 * 
 * @author IECISA
 */
public class PropertiesRowMapper implements RowMapper<LabelValue> {

	/**
	 * Realiza el mapeo de un ResultSet a un PropertyBean
	 * 
	 * @param aRs
	 *            ResultSet a mappear
	 * @param aRowNum
	 *            número de columna
	 * @see PropertyBean
	 * @see PropertiesTableDAO
	 */
	public LabelValue mapRow(ResultSet aRs, int aRowNum) throws SQLException {
		LabelValue bean = new LabelValue();
		bean.setLabel(aRs.getString(1));
		bean.setValue(aRs.getString(2));
		
		return bean;
	}

}
