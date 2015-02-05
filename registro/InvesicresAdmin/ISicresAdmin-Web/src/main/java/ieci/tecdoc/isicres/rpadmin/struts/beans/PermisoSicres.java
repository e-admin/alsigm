package ieci.tecdoc.isicres.rpadmin.struts.beans;


public class PermisoSicres {
	
	private static final long serialVersionUID = 1L;
	public static final int TIPO_USUARIO = 1;
	public static final int TIPO_OFICINA = 2;
	
	public static final int CENTRAL = 0;
	public static final int POR_OFICINA = 2;	
	
	private String id;
	private String tipo;
	private String nombre;
	private String consultar;
	private String crear;
	private String modificar;
	private String idLibro; 
	private int numeracion;
	
	public String getCrear() {
		return crear;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getConsultar() {
		return consultar;
	}

	public void setConsultar(String consultar) {
		this.consultar = consultar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModificar() {
		return modificar;
	}

	public void setModificar(String modificar) {
		this.modificar = modificar;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setCrear(String crear) {
		this.crear = crear;
	}
	public String getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}
	public int getNumeracion() {
		return numeracion;
	}
	public void setNumeracion(int numeracion) {
		this.numeracion = numeracion;
	}
}