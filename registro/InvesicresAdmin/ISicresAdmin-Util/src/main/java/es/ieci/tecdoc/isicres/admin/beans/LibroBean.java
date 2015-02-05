package es.ieci.tecdoc.isicres.admin.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extensión de Libro de registro para la gestión de libros y archivadores
 * 
 * 
 */
public class LibroBean {

	public static final int LIBRO_ENTRADA = 1;
	public static final int LIBRO_SALIDA = 2;
	public static final int NUMERACION_CENTRAL = 0;
	public static final int NUMERACION_POR_OFICINA = 2;
	public static final int AUTENTICACION_IMAGENES_NO = 0;
	public static final int AUTENTICACION_IMAGENES_SI = 1;
	public static final int ABRIR_LIBRO = 0;
	public static final int CERRAR_LIBRO = 1;

	private int id;
	private String nombre;
	private int tipo;
	private int idLista;
	private String nombreLista;
	private int numeracion;
	private int autenticacion;
	private int estado;
	private Date fechaCierre;
	private String usuarioCierre;

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * @return
	 */
	public int getAutenticacion() {
		return autenticacion;
	}

	/**
	 * @param autenticacion
	 */
	public void setAutenticacion(int autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * @return
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return
	 */
	public int getNumeracion() {
		return numeracion;
	}

	/**
	 * @param numeracion
	 */
	public void setNumeracion(int numeracion) {
		this.numeracion = numeracion;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return
	 */
	public int getIdLista() {
		return idLista;
	}

	/**
	 * @param idLista
	 */
	public void setIdLista(int idLista) {
		this.idLista = idLista;
	}

	/**
	 * @return
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * @param fechaCierre
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	/**
	 * @return
	 */
	public String getFechaCierreVista() {
		if (fechaCierre == null) {
			return "";
		} else {
			return dateFormatter.format(fechaCierre);
		}
	}

	/**
	 * @param fechaCierreVista
	 * @throws ParseException
	 */
	public void setFechaCierreVista(String fechaCierreVista)
			throws ParseException {
		if (fechaCierreVista == null || "".equals(fechaCierreVista)) {
			fechaCierre = null;
		} else {
			fechaCierre = dateFormatter.parse(fechaCierreVista);
		}
	}

	/**
	 * @return
	 */
	public String getUsuarioCierre() {
		return usuarioCierre;
	}

	/**
	 * @param usuarioCierre
	 */
	public void setUsuarioCierre(String usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

	/**
	 * @return
	 */
	public String getNombreLista() {
		return nombreLista;
	}

	/**
	 * @param nombreLista
	 */
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

}
