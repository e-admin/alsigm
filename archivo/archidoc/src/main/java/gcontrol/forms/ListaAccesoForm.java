package gcontrol.forms;

import gcontrol.model.TipoDestinatario;
import gcontrol.vos.ListaAccesoVO;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en la gestion de listas de acceso
 */
public class ListaAccesoForm extends CustomForm {

	private static final long serialVersionUID = 1L;
	String idListaAcceso = null;
	int tipoLista;
	String nombre = null;
	String descripcion = null;
	String[] listasAccesoSeleccionadas = null;
	int[] permisosSeleccionados = null;
	Map permisos = new LinkedHashMap();
	String idElementoSeleccionado;
	int tipoElementoSeleccionado;
	String nombreABuscar;
	String[] destinatariosSeleccionados = null;
	String[] destinatariosSeleccionadosABorrar = null;
	/**
	 * Se utliza para las búsquedas.
	 */
	int[] tiposLista;

	/**
	 * @return Returns the destinatariosSeleccionadosABorrar.
	 */
	public String[] getDestinatariosSeleccionadosABorrar() {
		return destinatariosSeleccionadosABorrar;
	}

	/**
	 * @param destinatariosSeleccionadosABorrar
	 *            The destinatariosSeleccionadosABorrar to set.
	 */
	public void setDestinatariosSeleccionadosABorrar(
			String[] destinatariosSeleccionadosABorrar) {
		this.destinatariosSeleccionadosABorrar = destinatariosSeleccionadosABorrar;
	}

	/**
	 * @return Returns the destinatariosSeleccionados.
	 */
	public String[] getDestinatariosSeleccionados() {
		return destinatariosSeleccionados;
	}

	/**
	 * @param destinatariosSeleccionados
	 *            The destinatariosSeleccionados to set.
	 */
	public void setDestinatariosSeleccionados(
			String[] destinatariosSeleccionados) {
		this.destinatariosSeleccionados = destinatariosSeleccionados;
	}

	/**
	 * @return Returns the nombreABuscar.
	 */
	public String getNombreABuscar() {
		return nombreABuscar;
	}

	/**
	 * @param nombreABuscar
	 *            The nombreABuscar to set.
	 */
	public void setNombreABuscar(String nombreABuscar) {
		this.nombreABuscar = nombreABuscar;
	}

	public List getListaPermisos() {
		Object[] obj = permisos.keySet().toArray();
		if (obj.length > 0)
			return Arrays.asList(obj);
		return null;
	}

	public Map getValoresPermisos() {
		return permisos;
	}

	// public List getPermisosAceptados(){
	// List ret = new ArrayList();
	// List permisos = getPermisos();
	// for (Iterator itPermisos = permisos.iterator(); itPermisos.hasNext();) {
	// Integer perm = (Integer)itPermisos.next();
	// if (getPermiso(perm.toString()).booleanValue()){
	// ret.add(perm);
	// }
	// }
	// return ret.size()>0?ret:null;
	// }

	/**
	 * @return Returns the idElementoSelecccionado.
	 */
	public String getIdElementoSeleccionado() {
		return idElementoSeleccionado;
	}

	/**
	 * @param idElementoSelecccionado
	 *            The idElementoSelecccionado to set.
	 */
	public void setIdElementoSeleccionado(String idElementoSeleccionado) {
		this.idElementoSeleccionado = idElementoSeleccionado;
	}

	/**
	 * @return Returns the tipoElementoSeleccionado.
	 */
	public int getTipoElementoSeleccionado() {
		return tipoElementoSeleccionado;
	}

	public boolean isTipoElementoUsuario() {
		return getTipoElementoSeleccionado() == TipoDestinatario.USUARIO;
	}

	public boolean isTipoElementoGrupo() {
		return getTipoElementoSeleccionado() == TipoDestinatario.GRUPO;
	}

	public boolean isTipoElementoOrgano() {
		return getTipoElementoSeleccionado() == TipoDestinatario.ORGANO;
	}

	/**
	 * @param tipoElementoSeleccionado
	 *            The tipoElementoSeleccionado to set.
	 */
	public void setTipoElementoSeleccionado(String tipoElementoSeleccionado) {
		this.tipoElementoSeleccionado = Integer
				.parseInt(tipoElementoSeleccionado);
	}

	public void setTipoElementoSeleccionado(int tipoElementoSeleccionado) {
		this.tipoElementoSeleccionado = tipoElementoSeleccionado;
	}

	public Boolean getPermiso(String key) {
		return (Boolean) permisos.get(new Integer(key));
	}

	public void setPermiso(String key, String value) {
		setPermisoAux(Integer.parseInt(key), Boolean.valueOf(value)
				.booleanValue());
	}

	public void setPermisosSeleccionados(int[] perms) {
		permisosSeleccionados = perms;
	}

	public int[] getPermisosSeleccionados() {
		return permisosSeleccionados;
	}

	private void setPermisoAux(int key, boolean value) {
		permisos.put(new Integer(key), new Boolean(value));
	}

	// /**
	// * @return Returns the usuariosSeleccionados.
	// */
	// public String[] getUsuariosSeleccionados() {
	// return usuariosSeleccionados;
	// }
	// /**
	// * @param usuariosSeleccionados The usuariosSeleccionados to set.
	// */
	// public void setUsuariosSeleccionados(String[] usuariosSeleccionados) {
	// this.usuariosSeleccionados = usuariosSeleccionados;
	// }
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
	 * @return Returns the idListaAcceso.
	 */
	public String getIdListaAcceso() {
		return idListaAcceso;
	}

	/**
	 * @param idListaAcceso
	 *            The idListaAcceso to set.
	 */
	public void setIdListaAcceso(String idListaAcceso) {
		this.idListaAcceso = idListaAcceso;
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
	 * @return Returns the tipoLista.
	 */
	public int getTipoLista() {
		return tipoLista;
	}

	/**
	 * @param tipoLista
	 *            The tipoLista to set.
	 */
	public void setTipoLista(int tipoLista) {
		this.tipoLista = tipoLista;
	}

	/**
	 * @return Returns the listasAccesoSeleccionadas.
	 */
	public String[] getListasAccesoSeleccionadas() {
		return listasAccesoSeleccionadas;
	}

	/**
	 * @param listasAccesoSeleccionadas
	 *            The listasAccesoSeleccionadas to set.
	 */
	public void setListasAccesoSeleccionadas(String[] listasAccesoSeleccionadas) {
		this.listasAccesoSeleccionadas = listasAccesoSeleccionadas;
	}

	/**
	 * @return el tiposLista
	 */
	public int[] getTiposLista() {
		return tiposLista;
	}

	/**
	 * @param tiposLista
	 *            el tiposLista a establecer
	 */
	public void setTiposLista(int[] tiposLista) {
		this.tiposLista = tiposLista;
	}

	/**
     * 
     */
	public void resetParaNuevaLista() {
		idListaAcceso = null;
		tipoLista = Integer.MIN_VALUE;
		nombre = null;
		descripcion = null;
		listasAccesoSeleccionadas = null;
		// usuariosSeleccionados = null;
		permisos = new LinkedHashMap();
		idElementoSeleccionado = null;
		tipoElementoSeleccionado = Integer.MIN_VALUE;
		nombreABuscar = null;
	}

	public void resetParaNuevoDestinatario() {
		permisos = new LinkedHashMap();
		idElementoSeleccionado = null;
		nombreABuscar = null;
	}

	public void populateByLista(ListaAccesoVO listaAcceso) {
		setIdListaAcceso(listaAcceso.getId());
		setDescripcion(listaAcceso.getDescripcion());
		setNombre(listaAcceso.getNombre());
		setTipoLista(listaAcceso.getTipo());
	}

	/**
     * 
     */
	public void resetVerElemento() {
		setIdElementoSeleccionado(null);
		setDestinatariosSeleccionadosABorrar(null);
	}

	public void resetPermisos() {
		permisos = new LinkedHashMap();
	}
}
