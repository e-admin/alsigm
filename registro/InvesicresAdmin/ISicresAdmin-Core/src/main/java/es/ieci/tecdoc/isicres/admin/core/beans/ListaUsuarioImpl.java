package es.ieci.tecdoc.isicres.admin.core.beans;

public class ListaUsuarioImpl {

	public static final int TIPO_SUPER_USUARIO=3;
	public static final int TIPO_USUARIO=1;
	public static final int TIPO_SIN_PERMISOS=0;
	
	private int id;
	private String nombre;
	private int type;
	private String code;
	private String firstName;
	private String secondName;
	private String surName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	

}
