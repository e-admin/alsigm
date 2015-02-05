package ieci.tdw.ispac.designer.client.objetos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Regla implements IsSerializable {

	private int id = -1;
	private String nombre = null; 
	private String descripcion = null;
	private String clase = null;
	private int tipo = -1;
	
	
	/**
	 * Constructor.
	 */
	public Regla() {}

	/**
	 * Constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param clase
	 * @param tipo
	 */
	public Regla(int id, String nombre, String descripcion, String clase, int tipo) {
		this();
		setId(id);
		setNombre(nombre);
		setDescripcion(descripcion);
		setClase(clase);
		setTipo(tipo);
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


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getClase() {
		return clase;
	}


	public void setClase(String clase) {
		this.clase = clase;
	}


	public int getTipo() {
		return tipo;
	}


	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
