package ieci.tecdoc.sgm.core.services.estructura_organizativa;


import java.io.Serializable;
import java.util.ArrayList;

public class UsuariosLdap implements Serializable{
	
	private ArrayList _list;
	
	public UsuariosLdap(){
		_list=new ArrayList();
	}

	public ArrayList get_list() {
		return _list;
	}

	public void set_list(ArrayList _list) {
		this._list = _list;
	}

	public void add(UsuarioLdap userLdap){
		_list.add(userLdap);
	}
	
	public UsuarioLdap get(int i) {
		return (UsuarioLdap)_list.get(i);
	}
	
	public int count() {
		return _list.size();
	}
}
