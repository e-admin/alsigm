package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class UsuarioLdap extends RetornoServicio{

	private int _id;
	private String _ldapguid;
	private String _ldapfullname;
    private PerfilesGenericos _profilesImpl;
    private PermisosGenericos _permsImpl;
        
  	public UsuarioLdap() { }
	
	public UsuarioLdap(int _id, String _ldapguid, String _ldapfullname, PerfilesGenericos impl, PermisosGenericos impl2) {
		this._id = _id;
		this._ldapguid = _ldapguid;
		this._ldapfullname = _ldapfullname;
		this._profilesImpl = impl;
		this._permsImpl = impl2;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_ldapguid() {
		return _ldapguid;
	}

	public void set_ldapguid(String _ldapguid) {
		this._ldapguid = _ldapguid;
	}

	public String get_ldapfullname() {
		return _ldapfullname;
	}

	public void set_ldapfullname(String _ldapfullname) {
		this._ldapfullname = _ldapfullname;
	}

	public PerfilesGenericos get_profilesImpl() {
		return _profilesImpl;
	}

	public void set_profilesImpl(PerfilesGenericos impl) {
		_profilesImpl = impl;
	}

	public PermisosGenericos get_permsImpl() {
		return _permsImpl;
	}

	public void set_permsImpl(PermisosGenericos impl) {
		_permsImpl = impl;
	}
}