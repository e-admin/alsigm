//
//FileName: AxClfhxEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxClfhx;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 * 
 */

public class AxClfhxEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxClfhxEntity.class);

	protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrid,name,type,parentid,info,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static String SELECT_SENTENCE = "SELECT id,fdrid,name,type,parentid,info,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate FROM {0} WHERE id=?";
	protected static String UPDATE_SENTENCE = "UPDATE {0} SET id=?,fdrid=?,name=?,type=?,parentid=?,info=?,stat=?,refcount=?,remarks=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=? WHERE id=?";

	private int fdrId;
	private String name;
	private int type;
	private int parentId;
	private int info;
	private int stat;
	private int refCount;
	private String remarks;
	private int accessType;
	private int acsId;
	private int crtrId;
	private Date crtnDate;
	private int updrId;
	private Date updDate;

	protected String getFinalTableName() {
		return "CLFHX";
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
		int index = 1;

		setId(rs.getInt(index++));
		setFdrId(rs.getInt(index++));
		setName(rs.getString(index++));
		setType(rs.getInt(index++));
		setParentId(rs.getInt(index++));
		setInfo(rs.getInt(index++));
		setStat(rs.getInt(index++));
		setRefCount(rs.getInt(index++));
		setRemarks(rs.getString(index++));
		setAccessType(rs.getInt(index++));
		setAcsId(rs.getInt(index++));
		setCrtrId(rs.getInt(index++));
		setCrtnDate(rs.getDate(index++));
		setUpdrId(rs.getInt(index++));
		setUpdDate(rs.getDate(index++));
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getFdrId());
		ps.setString(index++, getName());
		ps.setInt(index++, getType());
		ps.setInt(index++, getParentId());
		ps.setInt(index++, getInfo());
		ps.setInt(index++, getStat());
		ps.setInt(index++, getRefCount());
		ps.setString(index++, getRemarks());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {
		int index = 1;

		ps.setInt(index++, getId());
		ps.setInt(index++, getFdrId());
		ps.setString(index++, getName());
		ps.setInt(index++, getType());
		ps.setInt(index++, getParentId());
		ps.setInt(index++, getInfo());
		ps.setInt(index++, getStat());
		ps.setInt(index++, getRefCount());
		ps.setString(index++, getRemarks());
		ps.setInt(index++, getAccessType());
		ps.setInt(index++, getAcsId());
		ps.setInt(index++, getCrtrId());
		ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
		ps.setInt(index++, getUpdrId());
		ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));

	}

	public AxPK create(String primaryKeyType, AxClfhx axClfhx, String entidad)
			throws Exception {
		setPkType(primaryKeyType);

		setId(axClfhx.getId());
		setFdrId(axClfhx.getFdrId());
		setName(axClfhx.getName());
		setType(axClfhx.getType());
		setParentId(axClfhx.getParentId());
		setInfo(axClfhx.getInfo());
		setStat(axClfhx.getStat());
		setRefCount(axClfhx.getRefCount());
		setRemarks(axClfhx.getRemarks());
		setAccessType(axClfhx.getAccessType());
		setAcsId(axClfhx.getAcsId());
		setCrtrId(axClfhx.getCrtrId());
		setCrtnDate(axClfhx.getCrtnDate());
		setUpdrId(axClfhx.getUpdrId());
		setUpdDate(axClfhx.getUpdDate());

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

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
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

	public AxClfhx getAxClfhx() {
		AxClfhx axClfhx = new AxClfhx();
		axClfhx.setId(getId());
		axClfhx.setFdrId(getFdrId());
		axClfhx.setName(getName());
		axClfhx.setType(getType());
		axClfhx.setParentId(getParentId());
		axClfhx.setInfo(getInfo());
		axClfhx.setStat(getStat());
		axClfhx.setRefCount(getRefCount());
		axClfhx.setRemarks(getRemarks());
		axClfhx.setAccessType(getAccessType());
		axClfhx.setAcsId(getAcsId());
		axClfhx.setCrtrId(getCrtrId());
		axClfhx.setCrtnDate(getCrtnDate());
		axClfhx.setUpdrId(getUpdrId());
		axClfhx.setUpdDate(getUpdDate());
		return axClfhx;
	}
}