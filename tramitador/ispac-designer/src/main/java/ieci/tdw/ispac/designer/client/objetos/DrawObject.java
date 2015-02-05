package ieci.tdw.ispac.designer.client.objetos;

import ieci.tdw.ispac.designer.client.helper.varDefs;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DrawObject implements IsSerializable {
	
	private int id;
	private String nombre; 
	private int left;
	private int top;
	private String descripcion;
	private boolean desplegado;
	
	/*
	 * Atributo utilizado para las fases, 
	 * estado=0 No iniciado
	 * estado=1 En curso
	 * estado=2 Realizada
	 * */
	private int estado=varDefs.NO_INICIADO;
	
	//Propiedad solo informada cuando estemos ante un nodo
	private int tipo;
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
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	
	public DrawObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DrawObject(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.desplegado=false;
		
	}
	public DrawObject(int id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion=descripcion;
		this.desplegado=false;
		
	}
	public DrawObject(int id, String nombre, int left, int top) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.desplegado=false;
		this.left = left;
		this.top = top;
	}
	public DrawObject(int id, String nombre, int left, int top , int tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.left = left;
		this.top = top;
		this.desplegado=false;
		this.tipo=tipo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isDesplegado() {
		return desplegado;
	}
	public void setDesplegado(boolean desplegado) {
		this.desplegado = desplegado;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}



}
