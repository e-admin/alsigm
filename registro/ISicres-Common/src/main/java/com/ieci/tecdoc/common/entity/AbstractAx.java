//
//FileName: AbstractAxEntity.java 
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

public abstract class AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AbstractAx.class);

	protected static String DELETE_SENTENCE = "DELETE FROM {0} WHERE id=?";
	protected static String FINDBY_SENTENCE = "SELECT id FROM {0} WHERE id=?";

	protected String pkType = null;
	protected int id;

	public AxPK getPrimaryKey() {
		return new AxPK(pkType, id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	protected String getPkType() {
		return pkType;
	}

	protected void setPkType(String pkType) {
		this.pkType = pkType;
	}

	protected abstract String getFinalTableName();

	protected String getSentence(String sentence) {
		return MessageFormat.format(sentence, new String[] { getTableName() });
	}

	protected String getTableName() {
		if (getPkType() != null) {
			return getTableName(getPkType());
		} else {
			return null;
		}
	}

	protected String getTableName(String primaryKeyType) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("A");
		buffer.append(primaryKeyType);
		buffer.append(getFinalTableName());
		return buffer.toString();
	}

	protected void loadEntityPrimaryKey(AxPK primaryKey) {
		setPkType(primaryKey.getType());
		setId(primaryKey.getId());
	}

	protected abstract void assignLoad(ResultSet rs, String entidad)
			throws SQLException;

	public void load(String sentence, AxPK axPK, String entidad)
			throws Exception {
		loadEntityPrimaryKey(axPK);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence));
			ps.setInt(1, getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new Exception();
			}
			assignLoad(rs, entidad);
		} catch (SQLException ex) {
			log.error("Error en método load PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método load PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	protected abstract void assignStore(PreparedStatement ps, String entidad)
			throws SQLException;

	protected abstract void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException;

	public void store(String sentence, int idIndex, String entidad)
			throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence));
			assignStore(ps, entidad);
			ps.setInt(idIndex, getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			log.error("Error en método store.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método store.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public void store(String sentence, int idIndex, String entidad,
			String dataBaseType) throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence));
			assignStore(ps, entidad, dataBaseType);
			ps.setInt(idIndex, getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			log.error("Error en método store.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método store.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxPK createFromSentence(String sentence, String entidad)
			throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence));
			assignStore(ps, entidad);
			ps.executeUpdate();
			return getPrimaryKey();
		} catch (SQLException ex) {
			log.error("Error en método createFromSentence.PK ["
					+ getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método createFromSentence.PK ["
					+ getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

}
