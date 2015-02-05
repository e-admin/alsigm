package ieci.tdw.ispac.designer.client.objetos;

import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CampoEntidad implements IsSerializable{

	//Identificador del campo
	int id;
	//Este nombre sera el que se utiliza para construir la SQL , pero 
	//el usuario vera la descripción que corresponda en función del idioma
	String nombre; 
	//tipo String=1 , entero=2 ....
	int tipo;
	//Almacena las descripciones del nombre del campo en los distintos idiomas permitidos
	Map descripcion;
	public CampoEntidad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Map getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(Map descripcion) {
		this.descripcion = descripcion;
	}
	public CampoEntidad(int id, String nombre, int tipo, Map descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}
	
	
	
}
