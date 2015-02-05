//
//FileName: AxFdrhEEntity.java
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

import com.ieci.tecdoc.common.isicres.AxFdrh;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 * 
 */

public class AxFdrhEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxFdrhEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,verstat,refcount,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,verstat,refcount,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount FROM {0} WHERE id=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,verstat=?,refcount=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=?,accrid=?,accdate=?,acccount=? WHERE id=?";

	protected static String UPDATE_ACCESS_CONTROL = "UPDATE A{0}FDRH SET accrid=?, accdate=?, acccount=acccount+1 WHERE id=?";

	private int verStat;
	private int refCount;
	private int accessType;
	private int acsId;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	private int accrId;
	private Date accDate;
	private int acccount;

	public AxFdrhEntity() {
	}

	protected String getFinalTableName() {
		return "FDRH";
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
		int index = 1;

		setId(rs.getInt(index++));
		setVerStat(rs.getInt(index++));
		setRefCount(rs.getInt(index++));
		setAccessType(rs.getInt(index++));
		setAcsId(rs.getInt(index++));
		setCrtrId(rs.getInt(index++));
		setCrtnDate(rs.getDate(index++));
		setUpdrId(rs.getInt(index++));
		setUpdDate(rs.getDate(index++));
		setAccrId(rs.getInt(index++));
		setAccDate(rs.getDate(index++));
		setAcccount(rs.getInt(index++));
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getVerStat());
		ps.setInt(index++, getRefCount());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
		ps.setInt(index++, getAccrId());
		ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
		ps.setInt(index++, getAcccount());
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getVerStat());
		ps.setInt(index++, getRefCount());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
		ps.setInt(index++, getAccrId());
		ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
		ps.setInt(index++, getAcccount());
	}

	public AxPK create(String type, AxFdrh axFdrh, String entidad)
			throws Exception {
		setPkType(type);

		setId(axFdrh.getId());
		setVerStat(axFdrh.getVerStat());
		setRefCount(axFdrh.getRefCount());
		setAccessType(axFdrh.getAccessType());
		setAcsId(axFdrh.getAcsId());
		setCrtrId(axFdrh.getCrtrId());
		setCrtnDate(axFdrh.getCrtnDate());
		setUpdrId(axFdrh.getUpdrId());
		setUpdDate(axFdrh.getUpdDate());
		setAccrId(axFdrh.getAccrId());
		setAccDate(axFdrh.getAccDate());
		setAcccount(axFdrh.getAcccount());

		return createFromSentence(INSERT_SENTENCE, entidad);
	}

	public int updateAccessControl(Integer bookID, int userId,
			Timestamp updateDate, int fdrid, String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(MessageFormat.format(
					UPDATE_ACCESS_CONTROL, new Object[] { bookID }));
			ps.setInt(1, userId);
			ps.setTimestamp(2, updateDate);
			ps.setInt(3, fdrid);

			result = ps.executeUpdate();
		} catch (SQLException ex) {
			log.fatal("updateAccessControl. Sentence ["
					+ MessageFormat.format(UPDATE_ACCESS_CONTROL,
							new Object[] { bookID }), ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("updateAccessControl. Sentence ["
					+ MessageFormat.format(UPDATE_ACCESS_CONTROL,
							new Object[] { bookID }), ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return result;
	}

	public int getVerStat() {
		return verStat;
	}

	public void setVerStat(int verStat) {
		this.verStat = verStat;
	}

	public int getRefCount() {
		return refCount;
	}

	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	public int getAcsId() {
		return acsId;
	}

	public void setAcsId(int acsId) {
		this.acsId = acsId;
	}

	public int getCrtrId() {
		return crtrId;
	}

	public void setCrtrId(int crtrId) {
		this.crtrId = crtrId;
	}

	public Date getCrtnDate() {
		return crtnDate;
	}

	public void setCrtnDate(Date crtnDate) {
		this.crtnDate = crtnDate;
	}

	public int getUpdrId() {
		return updrId;
	}

	public void setUpdrId(int updrId) {
		this.updrId = updrId;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public int getAccrId() {
		return accrId;
	}

	public void setAccrId(int accrId) {
		this.accrId = accrId;
	}

	public Date getAccDate() {
		return accDate;
	}

	public void setAccDate(Date accDate) {
		this.accDate = accDate;
	}

	public int getAcccount() {
		return acccount;
	}

	public void setAcccount(int acccount) {
		this.acccount = acccount;
	}

	public AxFdrh getAxFdrh() {
		AxFdrh axFdrh = new AxFdrh();
		axFdrh.setId(getId());
		axFdrh.setVerStat(getVerStat());
		axFdrh.setRefCount(getRefCount());
		axFdrh.setAccessType(getAccessType());
		axFdrh.setAcsId(getAcsId());
		axFdrh.setCrtrId(getCrtrId());
		axFdrh.setCrtnDate(getCrtnDate());
		axFdrh.setUpdrId(getUpdrId());
		axFdrh.setUpdDate(getUpdDate());
		axFdrh.setAccrId(getAccrId());
		axFdrh.setAccDate(getAccDate());
		axFdrh.setAcccount(getAcccount());
		return axFdrh;
	}

}