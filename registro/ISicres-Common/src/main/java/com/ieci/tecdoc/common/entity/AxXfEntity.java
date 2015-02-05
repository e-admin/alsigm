//
// FileName: AxXfEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.extension.StringClobType;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.AxXfPK;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 * 
 */

public class AxXfEntity implements ServerKeys {

	protected static final Logger log = Logger.getLogger(AxXfEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrId,fldId,text,timestamp) VALUES (?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,text,timestamp FROM {0} WHERE fdrId=? and fldId=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,text=?,timestamp=? WHERE fdrId=? and fldId=?";
	protected static String FINDBY_SENTENCE = "SELECT id FROM {0} WHERE fdrId=? and fldId=?";
	protected static String DELETE_SENTENCE = "DELETE FROM {0} WHERE fdrId=? and fldId=?";

	private String type = null;
	private int id;
	private int fdrId;
	private int fldId;
	private String text;
	private Date timeStamp;

	public AxXfEntity() {
	}

	protected String getFinalTableName() {
		return "A" + type + "XF";
	}

	public AxXfPK getPrimaryKey() {
		return new AxXfPK(type, fdrId, fldId);
	}

	protected void loadEntityPrimaryKey(AxXfPK pk) {
		this.type = pk.getType();
		setFdrId(pk.getFdrId());
		setFldId(pk.getFldId());
	}

	public void remove(AxXfPK pk, String entidad) throws Exception {
		loadEntityPrimaryKey(pk);

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(DELETE_SENTENCE,
					new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, getFdrId());
			ps.setInt(2, getFldId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			log
					.error("Error en método remove.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log
					.error("Error en método remove.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public void load(String entidad) throws Exception {
		// loadEntityContextPrimaryKey();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(SELECT_SENTENCE,
					new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, getFdrId());
			ps.setInt(2, getFldId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new Exception();
			}
			setId(rs.getInt(1));
			StringClobType stringClobType = new StringClobType();
			String[] names = { "TEXT" };
			Object o = stringClobType.nullSafeGet(rs, names, null);
			String text = (String) o;
			setText(text);
			setTimeStamp(rs.getDate(3));
		} catch (SQLException ex) {
			log.error("Error en método load.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método load.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxXfPK create(AxXfPK pk, int id, String value, Timestamp timestamp,
			String entidad, String dataBaseType) throws Exception {
		this.type = pk.getType();
		this.fdrId = pk.getFdrId();
		this.fldId = pk.getFldId();
		this.id = id;
		this.text = value;
		this.timeStamp = timestamp;

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(INSERT_SENTENCE,
					new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, id);
			ps.setInt(2, getFdrId());
			ps.setInt(3, getFldId());
			StringClobType stringClobType = new StringClobType();
			if (dataBaseType.equals("DB2")) {
				stringClobType.nullSafeSet1(ps, value, 4);
			} else {
				stringClobType.nullSafeSet(ps, value, 4);
			}
			// ps.setString(4, value);
			ps.setDate(5, BBDDUtils.getDate(timestamp));

			ps.executeUpdate();
			return getPrimaryKey();
		} catch (SQLException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxXfPK findByPrimaryKey(AxXfPK pk, String entidad) throws Exception {
		this.type = pk.getType();
		this.fdrId = pk.getFdrId();
		this.fldId = pk.getFldId();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(FINDBY_SENTENCE,
					new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, pk.getFdrId());
			ps.setInt(2, pk.getFldId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new Exception();
			}
		} catch (SQLException ex) {
			log.error("Error en método findByPrimaryKey. PK ["
					+ getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método findByPrimaryKey. PK ["
					+ getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return new AxXfPK(pk.getType(), pk.getFdrId(), pk.getFldId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFdrId() {
		return fdrId;
	}

	public void setFdrId(int fdrId) {
		this.fdrId = fdrId;
	}

	public int getFldId() {
		return fldId;
	}

	public void setFldId(int fldId) {
		this.fldId = fldId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public AxXf getAxXf() {
		AxXf axXf = new AxXf();
		axXf.setId(getId());
		axXf.setFdrId(getFdrId());
		axXf.setFldId(getFldId());
		axXf.setText(getText());
		axXf.setTimeStamp(getTimeStamp());
		return axXf;
	}

}
