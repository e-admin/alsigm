package gcontrol.forms;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en la gestion de grupos
 */
public class GrupoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String nombre = null;
	String idGrupo = null;
	String archivoCustodia = null;
	String descripcion = null;
	String[] gruposSeleccionados = null;
	String nombreUsuario = null;
	String[] usuarioSeleccionado = null;
	boolean perteneceAArchivo = false;

	boolean personalizarSecciones = false;
	boolean ocultarPaisProvincia = false;
	boolean ocultarArchivoCodigoClasificadores = false;

	String ejemploPaisProvincia = null;
	String ejemploArchivoCodigo = null;
	String ejemploCodigo = null;
	String delimitador = null;

	/**
	 * @return Returns the idGrupo.
	 */
	public String getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo
	 *            The idGrupo to set.
	 */
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return Returns the archivoCustodia.
	 */
	public String getArchivoCustodia() {
		return archivoCustodia;
	}

	/**
	 * @param archivoCustodia
	 *            The archivoCustodia to set.
	 */
	public void setArchivoCustodia(String archivoCustodia) {
		this.archivoCustodia = archivoCustodia;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the gruposSeleccionados.
	 */
	public String[] getGruposSeleccionados() {
		return gruposSeleccionados;
	}

	/**
	 * @param gruposSeleccionados
	 *            The gruposSeleccionados to set.
	 */
	public void setGruposSeleccionados(String[] gruposSeleccionados) {
		this.gruposSeleccionados = gruposSeleccionados;
	}

	/**
	 * @return Returns the nombreUsuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            The nombreUsuario to set.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return Returns the usuarioSeleccionado.
	 */
	public String[] getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	/**
	 * @param usuarioSeleccionado
	 *            The usuarioSeleccionado to set.
	 */
	public void setUsuarioSeleccionado(String[] usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public boolean isPerteneceAArchivo() {
		return perteneceAArchivo;
	}

	public void setPerteneceAArchivo(boolean perteneceAArchivo) {
		this.perteneceAArchivo = perteneceAArchivo;
	}

	/**
	 * @return the ocultarPaisProvincia
	 */
	public boolean isOcultarPaisProvincia() {
		return ocultarPaisProvincia;
	}

	/**
	 * @param ocultarPaisProvincia
	 *            the ocultarPaisProvincia to set
	 */
	public void setOcultarPaisProvincia(boolean ocultarPaisProvincia) {
		this.ocultarPaisProvincia = ocultarPaisProvincia;
	}

	/**
	 * @return the ocultarArchivoCodigoClasificadores
	 */
	public boolean isOcultarArchivoCodigoClasificadores() {
		return ocultarArchivoCodigoClasificadores;
	}

	/**
	 * @param ocultarArchivoCodigoClasificadores
	 *            the ocultarArchivoCodigoClasificadores to set
	 */
	public void setOcultarArchivoCodigoClasificadores(
			boolean ocultarArchivoCodigoClasificadores) {
		this.ocultarArchivoCodigoClasificadores = ocultarArchivoCodigoClasificadores;
	}

	/**
	 * @return the personalizarSecciones
	 */
	public boolean isPersonalizarSecciones() {
		return personalizarSecciones;
	}

	/**
	 * @param personalizarSecciones
	 *            the personalizarSecciones to set
	 */
	public void setPersonalizarSecciones(boolean personalizarSecciones) {
		this.personalizarSecciones = personalizarSecciones;
	}

	/**
	 * @return the ejemploPaisProvincia
	 */
	public String getEjemploPaisProvincia() {
		return ejemploPaisProvincia;
	}

	/**
	 * @param ejemploPaisProvincia
	 *            the ejemploPaisProvincia to set
	 */
	public void setEjemploPaisProvincia(String ejemploPaisProvincia) {
		this.ejemploPaisProvincia = ejemploPaisProvincia;
	}

	/**
	 * @return the ejemploArchivoCodigo
	 */
	public String getEjemploArchivoCodigo() {
		return ejemploArchivoCodigo;
	}

	/**
	 * @param ejemploArchivoCodigo
	 *            the ejemploArchivoCodigo to set
	 */
	public void setEjemploArchivoCodigo(String ejemploArchivoCodigo) {
		this.ejemploArchivoCodigo = ejemploArchivoCodigo;
	}

	/**
	 * @return the ejemploCodigo
	 */
	public String getEjemploCodigo() {
		return ejemploCodigo;
	}

	/**
	 * @param ejemploCodigo
	 *            the ejemploCodigo to set
	 */
	public void setEjemploCodigo(String ejemploCodigo) {
		this.ejemploCodigo = ejemploCodigo;
	}

	/**
	 * @return the delimitador
	 */
	public String getDelimitador() {
		return delimitador;
	}

	/**
	 * @param delimitador
	 *            the delimitador to set
	 */
	public void setDelimitador(String delimitador) {
		this.delimitador = delimitador;
	}

}
