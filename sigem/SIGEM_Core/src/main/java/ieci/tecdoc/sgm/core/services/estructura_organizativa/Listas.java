package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;
import java.util.ArrayList;

public class Listas implements Serializable{
	
	private ArrayList _list;
	
	public Listas(){
		_list=new ArrayList();
	}

	public ArrayList get_list() {
		return _list;
	}

	public void set_list(ArrayList _list) {
		this._list = _list;
	}

	public void add(Lista lista){
		_list.add(lista);
	}
	
	public Lista get(int i) {
		return (Lista)_list.get(i);
	}
	
	public int count() {
		return _list.size();
	}
	
}
