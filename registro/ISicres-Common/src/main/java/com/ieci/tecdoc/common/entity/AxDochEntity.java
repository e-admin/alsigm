//
//FileName: AxDochEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 * 
 */

public class AxDochEntity implements ServerKeys {

	protected static final Logger log = Logger.getLogger(AxDochEntity.class);

	protected static String FINDBY_SENTENCE = "SELECT id FROM {0} WHERE fdrId=? and id=?";
	protected static String DELETE_SENTENCE = "DELETE FROM {0} WHERE fdrId=? and id=?";

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrid,archid,name,clfid,type,title,author,keywords,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount,timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,fdrid,archid,name,clfid,type,title,author,keywords,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount,timestamp FROM {0} WHERE fdrid=? and id=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET archid=?,name=?,clfid=?,type=?,title=?,author=?,keywords=?,stat=?,refcount=?,remarks=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=?,accrid=?,accdate=?,acccount=?,timestamp=? WHERE fdrid=? and id=?";
	protected static String SELECT_SENTENCE_FDRID = "SELECT id FROM {0} WHERE fdrid=? ORDER BY name";

	private String typePK = null;

	private int id;
	private int fdrId;
	private int archId;
	private String name;
	private int clfId = 0;
	private int type = 1;
	private String title = null;
	private String author = null;
	private String keyWords = null;
	private int stat = 0;
	private int refCount = 0;
	private String remarks = null;
	private int accessType = 1;
	private int acsId = (Integer.MAX_VALUE) - 1;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	private int accrId;
	private Date accDate;
	private int acccount;
	private Date timeStamp;

	public AxDochEntity() {
	}

	protected String getFinalTableName() {
		return "A" + typePK + "DOCH";
	}

	public AxPKById getPrimaryKey() {
		return new AxPKById(typePK, fdrId, id);
	}

	protected void loadEntityPrimaryKey(AxPKById axPKById) {
		this.typePK = axPKById.getType();
		setFdrId(axPKById.getFdrId());
		setId(axPKById.getId());
	}

	public void remove(String entidad) throws Exception {
		// loadEntityPrimaryKey();

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(DELETE_SENTENCE,	new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, getFdrId());
			ps.setInt(2, getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			log.error("Error en método remove.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método remove.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public void load(AxPKById axPKById, String entidad) throws Exception {
		loadEntityPrimaryKey(axPKById);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(SELECT_SENTENCE, new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, getFdrId());
			ps.setInt(2, getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new Exception();
			}
			int index = 1;
			setId(rs.getInt(index++));
			setFdrId(rs.getInt(index++));
			setArchId(rs.getInt(index++));
			setName(rs.getString(index++));
			setClfId(rs.getInt(index++));
			setType(rs.getInt(index++));
			setTitle(rs.getString(index++));
			setAuthor(rs.getString(index++));
			setKeyWords(rs.getString(index++));
			setStat(rs.getInt(index++));
			setRefCount(rs.getInt(index++));
			setRemarks(rs.getString(index++));
			setAccessType(rs.getInt(index++));
			setAcsId(rs.getInt(index++));
			setCrtrId(rs.getInt(index++));
			setCrtnDate(rs.getDate(index++));
			setUpdrId(rs.getInt(index++));
			setUpdDate(rs.getDate(index++));
			setAccrId(rs.getInt(index++));
			setAccDate(rs.getDate(index++));
			setAcccount(rs.getInt(index++));
			setTimeStamp(rs.getDate(index++));
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

	public void store(String entidad) throws Exception {
		// loadEntityContextPrimaryKey();

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(UPDATE_SENTENCE, new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			int index = 1;
			ps.setInt(index++, getArchId());
			ps.setString(index++, getName());
			ps.setInt(index++, getClfId());
			ps.setInt(index++, getType());
			ps.setString(index++, getTitle());
			ps.setString(index++, getAuthor());
			ps.setString(index++, getKeyWords());
			ps.setInt(index++, getStat());
			ps.setInt(index++, getRefCount());
			ps.setString(index++, getRemarks());
			ps.setInt(index++, getAccessType());
			ps.setInt(index++, getAcsId());
			ps.setInt(index++, getCrtrId());
			ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
			ps.setInt(index++, getUpdrId());
			ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
			ps.setInt(index++, getAccrId());
			ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
			ps.setInt(index++, getAcccount());
			ps.setDate(index++, BBDDUtils.getDate(getTimeStamp()));

			ps.setInt(index++, getId());
			ps.setInt(index++, getFdrId());

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

	public AxPKById create(AxPKById pk, int user, String name, Timestamp timestamp, String entidad) throws Exception {
		this.typePK = pk.getType();
		this.fdrId = pk.getFdrId();
		this.id = pk.getId();

		this.archId = Integer.parseInt(this.typePK);
		this.name = name;
		this.crtrId = user;
		this.crtnDate = new Date(timestamp.getTime());
		this.updrId = user;
		this.updDate = new Date(timestamp.getTime());
		this.accrId = user;
		this.accDate = new Date(timestamp.getTime());
		this.acccount = 1;
		this.timeStamp = new Date(timestamp.getTime());

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(INSERT_SENTENCE, new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			int index = 1;
			ps.setInt(index++, getId());
			ps.setInt(index++, getFdrId());
			ps.setInt(index++, getArchId());
			ps.setString(index++, getName());
			ps.setInt(index++, getClfId());
			ps.setInt(index++, getType());
			ps.setString(index++, getTitle());
			ps.setString(index++, getAuthor());
			ps.setString(index++, getKeyWords());
			ps.setInt(index++, getStat());
			ps.setInt(index++, getRefCount());
			ps.setString(index++, getRemarks());
			ps.setInt(index++, getAccessType());
			ps.setInt(index++, getAcsId());
			ps.setInt(index++, getCrtrId());
			ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
			ps.setInt(index++, getUpdrId());
			ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
			ps.setInt(index++, getAccrId());
			ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
			ps.setInt(index++, getAcccount());
			ps.setDate(index++, BBDDUtils.getDate(getTimeStamp()));

			ps.executeUpdate();
			return getPrimaryKey();
		} catch (SQLException ex) {
			log.error("Error en método create.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método create.PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxPKById findByPrimaryKey(AxPKById pk, String entidad) throws Exception {
		this.typePK = pk.getType();
		this.fdrId = pk.getFdrId();
		this.id = pk.getId();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(FINDBY_SENTENCE, new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, pk.getFdrId());
			ps.setInt(2, pk.getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new Exception();
			}
		} catch (SQLException ex) {
			log.error("Error en método ejbFindByPrimaryKey de un BMP. PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método ejbFindByPrimaryKey de un BMP. PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return new AxPKById(pk.getType(), pk.getFdrId(), pk.getId());
	}

	public Collection findByFdrid(Integer bookID, int fdrid, String entidad) throws Exception {
		this.typePK = bookID.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List result = new ArrayList();

		try {
			con = BBDDUtils.getConnection(entidad);
			String sentence = MessageFormat.format(SELECT_SENTENCE_FDRID, new String[] { getFinalTableName() });
			ps = con.prepareStatement(sentence);
			ps.setInt(1, fdrid);
			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new AxPKById(this.typePK, fdrid, rs.getInt("id")));
			}
		} catch (SQLException ex) {
			log.error("Error en método ejbFindByFdrid de un BMP. PK [" + bookID + "] fdrid [" + fdrid + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.error("Error en método ejbFindByFdrid de un BMP. PK [" + bookID + "] fdrid [" + fdrid + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return result;
	}

	public int lookForName(String bookID, int fdrid, String name, String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		String select = "SELECT ID FROM A" + bookID.toString() + "DOCH WHERE FDRID=? AND NAME=?";

		try {
			con = BBDDUtils.getConnection(entidad);

			ps = con.prepareStatement(select);
			ps.setInt(1, fdrid);
			ps.setString(2, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException ex) {
			log.fatal("findName. Sentence [" + select + "] name " + name, ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("findName. Sentence [" + select + "] name " + name, ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		/*
		 * if (result == -1) { if (log.isDebugEnabled()) { log.debug("result -1 => " +
		 * result); } throw new FinderException(); } if (log.isDebugEnabled()) {
		 * log.debug("result 2 => " + result); }
		 */

		return result;
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

	public int getArchId() {
		return archId;
	}

	public void setArchId(int archId) {
		this.archId = archId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClfId() {
		return clfId;
	}

	public void setClfId(int clfId) {
		this.clfId = clfId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public int getRefCount() {
		return refCount;
	}

	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public AxDoch getAxDoch() {
		AxDoch axDoch = new AxDoch();
		axDoch.setId(getId());
		axDoch.setFdrId(getFdrId());
		axDoch.setArchId(getArchId());
		axDoch.setName(getName());
		axDoch.setClfId(getClfId());
		axDoch.setType(getType());
		axDoch.setTitle(getTitle());
		axDoch.setAuthor(getAuthor());
		axDoch.setKeyWords(getKeyWords());
		axDoch.setStat(getStat());
		axDoch.setRefCount(getRefCount());
		axDoch.setRemarks(getRemarks());
		axDoch.setAccessType(getAccessType());
		axDoch.setAcsId(getAcsId());
		axDoch.setCrtrId(getCrtrId());
		axDoch.setCrtnDate(getCrtnDate());
		axDoch.setUpdrId(getUpdrId());
		axDoch.setUpdDate(getUpdDate());
		axDoch.setAccrId(getAccrId());
		axDoch.setAccDate(getAccDate());
		axDoch.setAcccount(getAcccount());
		axDoch.setTimeStamp(getTimeStamp());
		return axDoch;
	}
}