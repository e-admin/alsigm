package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;
import java.util.ArrayList;

public class PerfilesUsuario implements Serializable{
	
	private ArrayList list;
	
	public PerfilesUsuario() {
		list=new ArrayList();
	}
	
	public void add(PerfilUsuario perfilUsuario){
		list.add(perfilUsuario);
	}
	
	public PerfilUsuario get(int i){
		return (PerfilUsuario)list.get(i);
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public int count() {
		return list.size();
	}
}
