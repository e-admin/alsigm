package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class Lista implements Serializable
{
	private static final Logger _logger = Logger.getLogger(Lista.class);
	
	private int id;
	private String name;
	private int flags;
	private int state;
	private String remarks;
	private int creatorId;
	private Date creationDate;
	private int updaterId;
	private Date updateDate;
	private int volId;
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(int updaterId) {
		this.updaterId = updaterId;
	}
	public int getVolId() {
		return volId;
	}
	public void setVolId(int volId) {
		this.volId = volId;
	}
}
