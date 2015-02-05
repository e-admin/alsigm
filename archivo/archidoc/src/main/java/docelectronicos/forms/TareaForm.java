package docelectronicos.forms;

import common.forms.CustomForm;

import descripcion.vos.AutoridadVO;
import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocTCapturaVO;
import fondos.model.ElementoCuadroClasificacion;

public class TareaForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idTarea = null;
	String buscarPor = null;
	String idElemento = null;
	String titulo = null;
	String tituloDescriptor = null;
	String codigo = null;
	String idFondo = null;
	String idLista = null;
	String idGestor = null;
	String observaciones = null;
	String busquedaFechaEstado;

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdGestor() {
		return idGestor;
	}

	public void setIdGestor(String idGestor) {
		this.idGestor = idGestor;
	}

	public String getTituloDescriptor() {
		return tituloDescriptor;
	}

	public void setTituloDescriptor(String tituloDescriptor) {
		this.tituloDescriptor = tituloDescriptor;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public String getIdLista() {
		return idLista;
	}

	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getBuscarPor() {
		return buscarPor;
	}

	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @param tareaPO
	 */
	public void updateTareaVO(DocTCapturaVO tareaVO) {
		tareaVO.setIdUsrCaptura(getIdGestor());
		tareaVO.setObservaciones(getObservaciones());
	}

	/**
	 * @param newTarea
	 */
	public void populateTareaVO(DocTCapturaVO newTarea, AutoridadVO autoridadVO) {
		newTarea.setIdUsrCaptura(getIdGestor());
		newTarea.setObservaciones(getObservaciones());
		newTarea.setIdObj(autoridadVO.getId());
		newTarea.setTipoObj(TipoObjeto.DESCRIPTOR);
	}

	public void populateTareaVO(DocTCapturaVO newTarea,
			ElementoCuadroClasificacion elemento) {
		newTarea.setIdUsrCaptura(getIdGestor());
		newTarea.setObservaciones(getObservaciones());
		newTarea.setIdObj(elemento.getId());
		newTarea.setTipoObj(TipoObjeto.ELEMENTO_CF);
	}

	public boolean isBusquedaPorElCuadro() {
		return !getBuscarPor()
				.equals(DocumentosConstants.BUSQUEDA_X_DESCRIPTOR);

	}

	public void resetForCreate() {
		idTarea = null;
		buscarPor = null;
		idElemento = null;
		titulo = null;
		tituloDescriptor = null;
		codigo = null;
		idFondo = null;
		idLista = null;
		idGestor = null;
		observaciones = null;
	}

	public String getBusquedaFechaEstado() {
		return busquedaFechaEstado;
	}

	public void setBusquedaFechaEstado(String busquedaFechaEstado) {
		this.busquedaFechaEstado = busquedaFechaEstado;
	}
}
