//
//FileName: AxXnidEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxXnid;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author lmvicente
 * @version
 * @since
 * @creationDate 01-mar-2004
 */

public class AxXnidEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxXnidEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,type,parentid) VALUES (?,?,?)";

	protected static String SELECT_SENTENCE = "SELECT id,type,parentid FROM {0} WHERE id=?";

	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,type=?,parentid=? WHERE id=?";

	private int type;

	private int parentId;

	public AxXnidEntity() {
	}

	protected String getFinalTableName() {
		return "XNID";
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
		int index = 1;

		setId(rs.getInt(index++));
		setType(rs.getInt(index++));
		setParentId(rs.getInt(index++));
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getType());
		ps.setInt(index++, getParentId());
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getType());
		ps.setInt(index++, getParentId());

	}

	public void load(String entidad) throws Exception {
		load(SELECT_SENTENCE, null, entidad);
	}

	public void store(String entidad) throws Exception {
		store(UPDATE_SENTENCE, 4, entidad);
	}

	public AxPK create(String primaryKeyType, AxXnid axXnid, String entidad)
			throws Exception {
		setPkType(primaryKeyType);

		setId(axXnid.getId());
		setType(axXnid.getType());
		setParentId(axXnid.getParentId());

		return createFromSentence(INSERT_SENTENCE, entidad);
	}

	public int getNextID(Integer bookID, int parentID, String entidad)
			throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;

		String update = "UPDATE A" + bookID.toString()
				+ "XNID SET ID=ID+1 WHERE TYPE=8 AND PARENTID=?";
		String updateLock = "UPDATE A" + bookID.toString()
				+ "XNID SET ID=ID WHERE TYPE=8 AND PARENTID=?";
		String select = "SELECT ID FROM A" + bookID.toString()
				+ "XNID WHERE TYPE=8 AND PARENTID=?";
		String insert = "INSERT INTO A" + bookID.toString()
				+ "XNID (ID,PARENTID,TYPE) VALUES(1,?,8)";

		try {
			con = BBDDUtils.getConnection(entidad);

			ps = con.prepareStatement(updateLock);
			ps.setInt(1, parentID);
			int affected = ps.executeUpdate();
			BBDDUtils.close(ps);

			if (affected == 0) {
				ps = con.prepareStatement(insert);
				ps.setInt(1, parentID);
				affected = ps.executeUpdate();
				BBDDUtils.close(ps);
			}

			ps = con.prepareStatement(select);
			ps.setInt(1, parentID);
			rs = ps.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
			BBDDUtils.close(ps);
			
			ps = con.prepareStatement(update);
			ps.setInt(1, parentID);
			ps.executeUpdate();
			BBDDUtils.close(ps);
		} catch (SQLException ex) {
			log.fatal("getNextID. Sentence [" + update + "] parentID "
					+ parentID, ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("getNextID. Sentence [" + update + "] parentID "
					+ parentID, ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return result;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public AxXnid getAxXnid() {
		AxXnid axXnid = new AxXnid();
		axXnid.setId(getId());
		axXnid.setParentId(getParentId());
		axXnid.setType(getType());
		return axXnid;
	}

}