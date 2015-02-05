package ieci.tdw.ispac.designer.client.objetos;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Condition implements IsSerializable {
	
	
	//Identificador de la condicion

	String nombre;
	List condicionesSimples;
	public Condition(int id, String nombre) {
		super();
		
		this.nombre = nombre;
	}
	public Condition() {
		super();
		
		this.condicionesSimples=new ArrayList();
		// TODO Auto-generated constructor stub
	}
	public Condition( String nombre, List condicionesSimples) {
		super();
		
		this.nombre = nombre;
		this.condicionesSimples=new ArrayList();
		this.condicionesSimples = condicionesSimples;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List getCondicionesSimples() {
		return condicionesSimples;
	}
	public void setCondicionesSimples(List condicionesSimples) {
		this.condicionesSimples = condicionesSimples;
	}
	
	
	

}
