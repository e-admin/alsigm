package ieci.tecdoc.isicres.rpadmin.struts.forms;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;

public class LibroForm extends RPAdminActionForm {

	private static final long serialVersionUID = 1L;
	public static final int LIBRO_ENTRADA = 1;
	public static final int LIBRO_SALIDA = 2;

	private String id;
	private String nombre;
	private String tipo;
	private String numeracion;
	private String autenticacion;
	private int estado;
	private String idLista;
	private String nombreLista;
	private boolean actualizableASicres3;

	// Para las oficinas asociadas (permisos)
	private List permisos = new Vector();

	public String getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(String autenticacion) {
		this.autenticacion = autenticacion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getNumeracion() {
		return numeracion;
	}
	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIdLista() {
		return idLista;
	}
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}
	public String getNombreLista() {
		return nombreLista;
	}
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}
	public int count() {
		return permisos.size();
	}

	public PermisoSicres getPermisos(int index) {
		if(index>=permisos.size()) {
			for(int i=permisos.size();i<=index;i++){
				permisos.add(new PermisoSicres());
			}
		}
		return (PermisoSicres)permisos.get(index);
	}

	public List getLista() {
		return permisos;
	}

	public void setLista(List permisos) {
		this.permisos = permisos;
	}

	public boolean isActualizableASicres3() {
		return actualizableASicres3;
	}
	public void setActualizableASicres3(boolean actualizableASicres3) {
		this.actualizableASicres3 = actualizableASicres3;
	}

	public String[] getAttrsToUpper() {
		return null;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
	}


}