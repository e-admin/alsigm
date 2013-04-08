package organizacion.view.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import organizacion.model.vo.OrganizacionVO;
import organizacion.persistence.db.IOrganizacionDBEntity;

import common.forms.CustomForm;
import common.util.DateUtils;

public class OrganizacionForm extends CustomForm {

	private static final long serialVersionUID = 8096882810005214215L;

	private String id;
	private String codigo;
	private String nombre;
	private String tipo;
	private String nivel;
	private String idPadre;
	private String estado;
	private String inicio;
	private String fin;
	private String descripcion;

	private String nombrePadre;
	String idUsrSisExtGestorSeleccionado;
	private String[] usuariosSeleccionados;
	private boolean vigente;
	private String searchTokenUsuario = null;

	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            el codigo a establecer
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            el estado a establecer
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return el fin
	 */
	public String getFin() {
		return fin;
	}

	/**
	 * @param fin
	 *            el fin a establecer
	 */
	public void setFin(String fin) {
		this.fin = fin;
	}

	/**
	 * @return el inicio
	 */
	public String getInicio() {
		return inicio;
	}

	/**
	 * @param inicio
	 *            el inicio a establecer
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return el idOrganizacion
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el idOrganizacion a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el idPadre
	 */
	public String getIdPadre() {
		return idPadre;
	}

	/**
	 * @param idPadre
	 *            el idPadre a establecer
	 */
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * @return el nivel
	 */
	public String getNivel() {
		return nivel;
	}

	/**
	 * @param nivel
	 *            el nivel a establecer
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            el tipo a establecer
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return el nombrePadre
	 */
	public String getNombrePadre() {
		return nombrePadre;
	}

	/**
	 * @param nombrePadre
	 *            el nombrePadre a establecer
	 */
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	/**
	 * @return el usuariosSeleccionados
	 */
	public String[] getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	/**
	 * @param usuariosSeleccionados
	 *            el usuariosSeleccionados a establecer
	 */
	public void setUsuariosSeleccionados(String[] usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	/**
	 * @return el vigente
	 */
	public boolean isVigente() {
		return vigente;
	}

	/**
	 * @param vigente
	 *            el vigente a establecer
	 */
	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return el searchTokenUsuario
	 */
	public String getSearchTokenUsuario() {
		return searchTokenUsuario;
	}

	/**
	 * @param searchTokenUsuario
	 *            el searchTokenUsuario a establecer
	 */
	public void setSearchTokenUsuario(String searchTokenUsuario) {
		this.searchTokenUsuario = searchTokenUsuario;
	}

	/**
	 * @return el idUsrSisExtGestorSeleccionado
	 */
	public String getIdUsrSisExtGestorSeleccionado() {
		return idUsrSisExtGestorSeleccionado;
	}

	/**
	 * @param idUsrSisExtGestorSeleccionado
	 *            el idUsrSisExtGestorSeleccionado a establecer
	 */
	public void setIdUsrSisExtGestorSeleccionado(
			String idUsrSisExtGestorSeleccionado) {
		this.idUsrSisExtGestorSeleccionado = idUsrSisExtGestorSeleccionado;
	}

	public void set(OrganizacionVO organizacionVO) {
		if (organizacionVO != null) {
			setId(organizacionVO.getId());
			setCodigo(organizacionVO.getCodigo());
			setNombre(organizacionVO.getNombre());
			setTipo(String.valueOf(organizacionVO.getTipo()));
			setNivel(String.valueOf(organizacionVO.getNivel()));
			setIdPadre(organizacionVO.getIdOrgPadre());
			setEstado(String.valueOf(organizacionVO.getEstado()));
			setInicio(DateUtils.formatDate(organizacionVO.getFiniciovigencia()));
			setFin(DateUtils.formatDate(organizacionVO.getFfinvigencia()));
			setDescripcion(organizacionVO.getDescripcion());
			if (IOrganizacionDBEntity.VIGENTE.equals(String
					.valueOf(organizacionVO.getEstado())))
				setVigente(true);
			else
				setVigente(false);
		}
	}

	public void populate(OrganizacionVO organizacionVO) {
		if (organizacionVO != null
				&& IOrganizacionDBEntity.BORRADOR.equals(organizacionVO
						.getEstado().toString())) {
			organizacionVO.setCodigo(this.codigo);
			organizacionVO.setNombre(this.nombre);
			organizacionVO.setDescripcion(this.descripcion);
		} else {
			organizacionVO.setNombre(this.nombre);
			organizacionVO.setDescripcion(this.descripcion);
			organizacionVO.setFiniciovigencia(DateUtils.getDate(this.inicio));
		}
	}

	public void populateGeneral(OrganizacionVO organizacionVO) {
		if (organizacionVO != null) {
			organizacionVO.setId(this.id);
			organizacionVO.setCodigo(this.codigo);
			organizacionVO.setNombre(this.nombre);
			organizacionVO.setTipo(new Integer(this.tipo));
			organizacionVO.setNivel(new Integer(this.nivel));
			organizacionVO.setIdOrgPadre(this.idPadre);
			organizacionVO.setEstado(new Integer(this.estado));
			organizacionVO.setFiniciovigencia(DateUtils.getDate(this.inicio));
			organizacionVO.setFfinvigencia(DateUtils.getDate(this.fin));
			organizacionVO.setDescripcion(this.descripcion);
		}
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	public void clear() {
		this.id = null;
		this.codigo = null;
		this.nombre = null;
		this.tipo = null;
		this.nivel = null;
		this.idPadre = null;
		this.estado = null;
		this.inicio = null;
		this.fin = null;
		this.descripcion = null;
		this.vigente = false;
	}
}