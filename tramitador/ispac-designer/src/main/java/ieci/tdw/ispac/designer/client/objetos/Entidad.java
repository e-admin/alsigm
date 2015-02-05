package ieci.tdw.ispac.designer.client.objetos;

import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Entidad implements IsSerializable {
	
//Identificador de la Entidad	
//int id;
//Nombre de la entidad que se utilizará para construir la sql
String nombre;
// Map de campos asociados a una entidad , la clave es el nombre del campo 
Map campos;
//Descripcion de la tabla en función del idioma, será el nombre que el usuario vea
//y no el de la bbdd , la clave es el ide de la entidad
Map descripcion;

public Entidad(int id, String nombre, Map campos, Map descripcion) {
	super();
	//this.id = id;
	this.nombre = nombre;
	this.campos = campos;
	this.descripcion = descripcion;
}/*
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}*/
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Map getCampos() {
	return campos;
}
public void setCampos(Map campos) {
	this.campos = campos;
}
public Entidad() {
	super();

}
public Entidad( String nombre, Map campos) {
	super();
	//this.id = id;
	this.nombre = nombre;
	this.campos = campos;
}
public Map getDescripcion() {
	return descripcion;
}
public void setDescripcion(Map descripcion) {
	this.descripcion = descripcion;
}
	
	
}
