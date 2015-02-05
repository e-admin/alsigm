package ieci.tecdoc.sgm.core.services.estructura_organizativa;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable{

	public static final int LDAP_USER_TYPE = 2;
	public static final int SICRES_USER_TYPE = 1;
	
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
    
    
  
	public Usuario() {
		
	}
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
	
	public Date get_creationDate() {
		return _creationDate;
	}
	public void set_creationDate(Date date) {
		_creationDate = date;
	}
	public int get_creatorId() {
		return _creatorId;
	}
	public void set_creatorId(int id) {
		_creatorId = id;
	}
	public int get_deptId() {
		return _deptId;
	}
	public void set_deptId(int id) {
		_deptId = id;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public boolean is_isChange() {
		return _isChange;
	}
	public void set_isChange(boolean change) {
		_isChange = change;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_oldPassword() {
		return _oldPassword;
	}
	public void set_oldPassword(String password) {
		_oldPassword = password;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public PermisosGenericos get_permsImpl() {
		return _permsImpl;
	}
	public void set_permsImpl(PermisosGenericos impl) {
		_permsImpl = impl;
	}
	public PerfilesGenericos get_profilesImpl() {
		return _profilesImpl;
	}
	public void set_profilesImpl(PerfilesGenericos impl) {
		_profilesImpl = impl;
	}
	public long get_pwdLastUpdTs() {
		return _pwdLastUpdTs;
	}
	public void set_pwdLastUpdTs(long lastUpdTs) {
		_pwdLastUpdTs = lastUpdTs;
	}
	public String get_pwdmbc() {
		return _pwdmbc;
	}
	public void set_pwdmbc(String _pwdmbc) {
		this._pwdmbc = _pwdmbc;
	}
	public int get_pwdminlen() {
		return _pwdminlen;
	}
	public void set_pwdminlen(int _pwdminlen) {
		this._pwdminlen = _pwdminlen;
	}
	public String get_pwdvpcheck() {
		return _pwdvpcheck;
	}
	public void set_pwdvpcheck(String _pwdvpcheck) {
		this._pwdvpcheck = _pwdvpcheck;
	}
	public int get_state() {
		return _state;
	}
	public void set_state(int _state) {
		this._state = _state;
	}
	public Date get_updateDate() {
		return _updateDate;
	}
	public void set_updateDate(Date date) {
		_updateDate = date;
	}
	public int get_updaterId() {
		return _updaterId;
	}
	public void set_updaterId(int id) {
		_updaterId = id;
	}
	public int get_userConnected() {
		return _userConnected;
	}
	public void set_userConnected(int connected) {
		_userConnected = connected;
	}
	public boolean is_wasAdmin() {
		return _wasAdmin;
	}
	public void set_wasAdmin(boolean admin) {
		_wasAdmin = admin;
	}
	
	public PerfilUsuario get_Perfil(int prodId) {
		List perfiles = _profilesImpl.getPerfilesUsuario().getList();
		for(int i=0; i<perfiles.size(); i++) {
			PerfilUsuario perfil = (PerfilUsuario)perfiles.get(i);
			if(perfil.get_product()==prodId) {
				return perfil;
			}
		}
		return null;
	}
    
    
}
