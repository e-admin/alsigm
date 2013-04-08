package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;
import java.util.ArrayList;

public class Permisos implements Serializable{

	private ArrayList list;	
	
	public Permisos() {
		list=new ArrayList();
	}
	
	public void add(Permiso permiso){
		list.add(permiso);
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}
	
	public Permiso get(int i) {
		return (Permiso)list.get(i);
	}
	
	public int count() {
		return list.size();
	}
}
