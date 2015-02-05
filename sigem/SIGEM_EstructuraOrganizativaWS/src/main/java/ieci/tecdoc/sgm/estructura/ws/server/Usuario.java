package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.Date;

public class Usuario extends RetornoServicio{

	private int _userConnected;
	private int _id;
	private String _name;
	private String _password;
	private String _oldPassword;
    private boolean _isChange;
    private String _pwdmbc;
    private String _pwdvpcheck;
    private String _description;
    private int _deptId;
    private int _state;
    private PerfilesGenericos _profilesImpl;
    private PermisosGenericos _permsImpl;
    private boolean _wasAdmin;
    private int _creatorId;
    private Date _creationDate;
    private int _updaterId;
    private Date _updateDate;
    private long _pwdLastUpdTs;
    private int _pwdminlen;
    
    
  	public Usuario() { }
	
	public Usuario(int connected, int _id, String _name, String _password, String password, boolean change, String _pwdmbc, String _pwdvpcheck, String _description, int id, int _state, PerfilesGenericos impl, PermisosGenericos impl2, boolean admin, int id2, Date date, int id3, Date date2, long lastUpdTs, int _pwdminlen) {
		_userConnected = connected;
		this._id = _id;
		this._name = _name;
		this._password = _password;
		_oldPassword = password;
		_isChange = change;
		this._pwdmbc = _pwdmbc;
		this._pwdvpcheck = _pwdvpcheck;
		this._description = _description;
		_deptId = id;
		this._state = _state;
		_profilesImpl = impl;
		_permsImpl = impl2;
		_wasAdmin = admin;
		_creatorId = id2;
		_creationDate = date;
		_updaterId = id3;
		_updateDate = date2;
		_pwdLastUpdTs = lastUpdTs;
		this._pwdminlen = _pwdminlen;
	}
	
	public Date getCreationDate() {
		return _creationDate;
	}
	public void setCreationDate(Date date) {
		_creationDate = date;
	}
	public int getCreatorId() {
		return _creatorId;
	}
	public void setCreatorId(int id) {
		_creatorId = id;
	}
	public int getDeptId() {
		return _deptId;
	}
	public void setDeptId(int id) {
		_deptId = id;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String _description) {
		this._description = _description;
	}
	public int getId() {
		return _id;
	}
	public void setId(int _id) {
		this._id = _id;
	}
	public boolean isIsChange() {
		return _isChange;
	}
	public void setIsChange(boolean change) {
		_isChange = change;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getOldPassword() {
		return _oldPassword;
	}
	public void setOldPassword(String password) {
		_oldPassword = password;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String _password) {
		this._password = _password;
	}
	public PermisosGenericos getPerms() {
		return _permsImpl;
	}
	public void setPerms(PermisosGenericos impl) {
		_permsImpl = impl;
	}
	public PerfilesGenericos getProfiles() {
		return _profilesImpl;
	}
	public void setProfiles(PerfilesGenericos impl) {
		_profilesImpl = impl;
	}
	public long getPwdLastUpdTs() {
		return _pwdLastUpdTs;
	}
	public void setPwdLastUpdTs(long lastUpdTs) {
		_pwdLastUpdTs = lastUpdTs;
	}
	public String getPwdmbc() {
		return _pwdmbc;
	}
	public void setPwdmbc(String _pwdmbc) {
		this._pwdmbc = _pwdmbc;
	}
	public int getPwdminlen() {
		return _pwdminlen;
	}
	public void setPwdminlen(int _pwdminlen) {
		this._pwdminlen = _pwdminlen;
	}
	public String getPwdvpcheck() {
		return _pwdvpcheck;
	}
	public void setPwdvpcheck(String _pwdvpcheck) {
		this._pwdvpcheck = _pwdvpcheck;
	}
	public int getState() {
		return _state;
	}
	public void setState(int _state) {
		this._state = _state;
	}
	public Date getUpdateDate() {
		return _updateDate;
	}
	public void setUpdateDate(Date date) {
		_updateDate = date;
	}
	public int getUpdaterId() {
		return _updaterId;
	}
	public void setUpdaterId(int id) {
		_updaterId = id;
	}
	public int getUserConnected() {
		return _userConnected;
	}
	public void setUserConnected(int connected) {
		_userConnected = connected;
	}
	public boolean isWasAdmin() {
		return _wasAdmin;
	}
	public void setWasAdmin(boolean admin) {
		_wasAdmin = admin;
	}
	

    
    
}
