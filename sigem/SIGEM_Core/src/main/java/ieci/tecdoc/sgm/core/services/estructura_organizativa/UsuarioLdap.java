package ieci.tecdoc.sgm.core.services.estructura_organizativa;


import java.io.Serializable;
import java.util.List;

public class UsuarioLdap implements Serializable{
	
	private int _id;
	private String _ldapguid;
	private String _ldapfullname;
	private String _firstName;
	private String _secondName;
	private String _surName;
    private PerfilesGenericos _profilesImpl;
    private PermisosGenericos _permsImpl;
  
	public UsuarioLdap() {
		
	}
	public UsuarioLdap(int _id, String _ldapguid, String _ldapfullname, PerfilesGenericos impl, PermisosGenericos impl2) {
		this._id = _id;
		this._ldapguid = _ldapguid;
		this._ldapfullname = _ldapfullname;
		_profilesImpl = impl;
		_permsImpl = impl2;
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
	public String get_firstName() {
		return _firstName;
	}
	public void set_firstName(String name) {
		_firstName = name;
	}
	public String get_secondName() {
		return _secondName;
	}
	public void set_secondName(String name) {
		_secondName = name;
	}
	public String get_surName() {
		return _surName;
	}
	public void set_surName(String name) {
		_surName = name;
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
