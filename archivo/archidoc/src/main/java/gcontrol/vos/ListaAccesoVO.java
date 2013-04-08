package gcontrol.vos;

import java.util.List;

/**
 * Value Object de listas de control de acceso. Las listas de control de acceso
 * permiten limitar el acceso a diferentes objetos gestionados por el sistema:
 * descriptores, elementos del cuadro de clasificación
 */
public class ListaAccesoVO {
	String id;

	String nombre;

	int tipo;

	String descripcion;

	List permisosLista;

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public List getPermisosLista() {
		return permisosLista;
	}

	public void setPermisosLista(List permisosLista) {
		this.permisosLista = permisosLista;
	}

}