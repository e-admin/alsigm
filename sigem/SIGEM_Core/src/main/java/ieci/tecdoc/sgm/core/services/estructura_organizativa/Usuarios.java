package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuarios implements Serializable{
	
	private ArrayList _list;
	
	public Usuarios(){
		_list=new ArrayList();
	}

	public ArrayList get_list() {
		return _list;
	}

	public void set_list(ArrayList _list) {
		this._list = _list;
	}

	public void add(Usuario usuario){
		_list.add(usuario);
	}
	
	public Usuario get(int i) {
		return (Usuario)_list.get(i);
	}
	
	public int count() {
		return _list.size();
	}
}
