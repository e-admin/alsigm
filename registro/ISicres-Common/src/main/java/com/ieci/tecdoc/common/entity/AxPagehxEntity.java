//
//FileName: AxPagehxEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxPagehx;
import com.ieci.tecdoc.common.keys.ServerKeys;

/**
 * @author 79426599
 * 
 */

public class AxPagehxEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxPagehxEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrid,name,sortorder,docid,fileid,volid,loc,annid,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,fdrid,name,sortorder,docid,fileid,volid,loc,annid,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount FROM {0} WHERE id=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,fdrid=?,name=?,sortorder=?,docid=?,fileid=?,volid=?,loc=?,annid=?,stat=?,refcount=?,remarks=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=?,accrid=?,accdate=?,acccount=? WHERE id=?";

	private int fdrId;
	private String name;
	private int sortOrder;
	private int docId;
	private int fileId;
	private int volId;
	private String loc;
	private int annId;
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

	protected String getFinalTableName() {
		return "PAGEHX";
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
		int index = 1;

		setId(rs.getInt(index++));
		setFdrId(rs.getInt(index++));
		setName(rs.getString(index++));
		setSortOrder(rs.getInt(index++));
		setDocId(rs.getInt(index++));
		setFileId(rs.getInt(index++));
		setVolId(rs.getInt(index++));
		setLoc(rs.getString(index++));
		setAnnId(rs.getInt(index++));
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
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getFdrId());
		ps.setString(index++, getName());
		ps.setInt(index++, getSortOrder());
		ps.setInt(index++, getDocId());
		ps.setInt(index++, getFileId());
		ps.setInt(index++, getVolId());
		ps.setString(index++, getLoc());
		ps.setInt(index++, getAnnId());
		ps.setInt(index++, getStat());
		ps.setInt(index++, getRefCount());
		ps.setString(index++, getRemarks());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, new java.sql.Date(getCrtnDate().getTime()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, new java.sql.Date(getUpdDate().getTime()));
		ps.setInt(index++, getAccrId());
		ps.setDate(index++, new java.sql.Date(getAccDate().getTime()));
		ps.setInt(index++, getAcccount());
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getFdrId());
		ps.setString(index++, getName());
		ps.setInt(index++, getSortOrder());
		ps.setInt(index++, getDocId());
		ps.setInt(index++, getFileId());
		ps.setInt(index++, getVolId());
		ps.setString(index++, getLoc());
		ps.setInt(index++, getAnnId());
		ps.setInt(index++, getStat());
		ps.setInt(index++, getRefCount());
		ps.setString(index++, getRemarks());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, new java.sql.Date(getCrtnDate().getTime()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, new java.sql.Date(getUpdDate().getTime()));
		ps.setInt(index++, getAccrId());
		ps.setDate(index++, new java.sql.Date(getAccDate().getTime()));
		ps.setInt(index++, getAcccount());

	}

	public AxPK create(String type, AxPagehx axPagehx, String entidad)
			throws Exception {
		setPkType(type);

		setId(axPagehx.getId());
		setFdrId(axPagehx.getFdrId());
		setName(axPagehx.getName());
		setSortOrder(axPagehx.getSortOrder());
		setDocId(axPagehx.getDocId());
		setFileId(axPagehx.getFileId());
		setVolId(axPagehx.getVolId());
		setLoc(axPagehx.getLoc());
		setAnnId(axPagehx.getAnnId());
		setStat(axPagehx.getStat());
		setRefCount(axPagehx.getRefCount());
		setRemarks(axPagehx.getRemarks());
		setAccessType(axPagehx.getAccessType());
		setAcsId(axPagehx.getAcsId());
		setCrtrId(axPagehx.getCrtrId());
		setCrtnDate(axPagehx.getCrtnDate());
		setUpdrId(axPagehx.getUpdrId());
		setUpdDate(axPagehx.getUpdDate());
		setAccrId(axPagehx.getAccrId());
		setAccDate(axPagehx.getAccDate());
		setAcccount(axPagehx.getAcccount());

		return createFromSentence(INSERT_SENTENCE, entidad);
	}

	public int getFdrId() {
		return fdrId;
	}

	public void setFdrId(int fdrId) {
		this.fdrId = fdrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getVolId() {
		return volId;
	}

	public void setVolId(int volId) {
		this.volId = volId;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getAnnId() {
		return annId;
	}

	public void setAnnId(int annId) {
		this.annId = annId;
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

	public AxPagehx getAxPagehx() {
		AxPagehx axPagehx = new AxPagehx();
		axPagehx.setId(getId());
		axPagehx.setFdrId(getFdrId());
		axPagehx.setName(getName());
		axPagehx.setSortOrder(getSortOrder());
		axPagehx.setDocId(getDocId());
		axPagehx.setFileId(getFileId());
		axPagehx.setVolId(getVolId());
		axPagehx.setLoc(getLoc());
		axPagehx.setAnnId(getAnnId());
		axPagehx.setStat(getStat());
		axPagehx.setRefCount(getRefCount());
		axPagehx.setRemarks(getRemarks());
		axPagehx.setAccessType(getAccessType());
		axPagehx.setAcsId(getAcsId());
		axPagehx.setCrtrId(getCrtrId());
		axPagehx.setCrtnDate(getCrtnDate());
		axPagehx.setUpdrId(getUpdrId());
		axPagehx.setUpdDate(getUpdDate());
		axPagehx.setAccrId(getAccrId());
		axPagehx.setAccDate(getAccDate());
		axPagehx.setAcccount(getAcccount());
		return axPagehx;
	}

}
