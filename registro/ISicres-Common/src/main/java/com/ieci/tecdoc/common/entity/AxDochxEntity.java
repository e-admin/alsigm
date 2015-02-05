//
//FileName: AxDochxEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxDochx;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 * 
 */

public class AxDochxEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxDochxEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrid,archid,name,clfid,type,title,author,keywords,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount,timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,fdrid,archid,name,clfid,type,title,author,keywords,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount,timestamp FROM {0} WHERE id=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,fdrid=?,archid=?,name=?,clfid=?,type=?,title=?,author=?,keywords=?,stat=?,refcount=?,remarks=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=?,accrid=?,accdate=?,acccount=?,timestamp=? WHERE id=?";

	private int fdrId;
	private int archId;
	private String name;
	private int clfId;
	private int type;
	private String title;
	private String author;
	private String keyWords;
	private int stat;
	private int refCount;
	private String remarks;
	private int accessType;
	private int acsId;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;
	private int accrId;
	private Date accDate;
	private int acccount;
	private Date timeStamp;

	protected String getFinalTableName() {
		return "DOCHX";
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
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
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
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
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {
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

	}

	public AxPK create(String primaryKeyType, AxDochx axDochx, String entidad)
			throws Exception {
		setPkType(primaryKeyType);

		setId(axDochx.getId());
		setFdrId(axDochx.getFdrId());
		setArchId(axDochx.getArchId());
		setName(axDochx.getName());
		setClfId(axDochx.getClfId());
		setType(axDochx.getType());
		setTitle(axDochx.getTitle());
		setAuthor(axDochx.getAuthor());
		setKeyWords(axDochx.getKeyWords());
		setStat(axDochx.getStat());
		setRefCount(axDochx.getRefCount());
		setRemarks(axDochx.getRemarks());
		setAccessType(axDochx.getAccessType());
		setAcsId(axDochx.getAcsId());
		setCrtrId(axDochx.getCrtrId());
		setCrtnDate(axDochx.getCrtnDate());
		setUpdrId(axDochx.getUpdrId());
		setUpdDate(axDochx.getUpdDate());
		setAccrId(axDochx.getAccrId());
		setAccDate(axDochx.getAccDate());
		setAcccount(axDochx.getAcccount());
		setTimeStamp(axDochx.getTimeStamp());

		return createFromSentence(INSERT_SENTENCE, entidad);
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

	public AxDochx getAxDochx() {
		AxDochx axDochx = new AxDochx();
		axDochx.setId(getId());
		axDochx.setFdrId(getFdrId());
		axDochx.setArchId(getArchId());
		axDochx.setName(getName());
		axDochx.setClfId(getClfId());
		axDochx.setType(getType());
		axDochx.setTitle(getTitle());
		axDochx.setAuthor(getAuthor());
		axDochx.setKeyWords(getKeyWords());
		axDochx.setStat(getStat());
		axDochx.setRefCount(getRefCount());
		axDochx.setRemarks(getRemarks());
		axDochx.setAccessType(getAccessType());
		axDochx.setAcsId(getAcsId());
		axDochx.setCrtrId(getCrtrId());
		axDochx.setCrtnDate(getCrtnDate());
		axDochx.setUpdrId(getUpdrId());
		axDochx.setUpdDate(getUpdDate());
		axDochx.setAccrId(getAccrId());
		axDochx.setAccDate(getAccDate());
		axDochx.setAcccount(getAcccount());
		axDochx.setTimeStamp(getTimeStamp());
		return axDochx;
	}
}
