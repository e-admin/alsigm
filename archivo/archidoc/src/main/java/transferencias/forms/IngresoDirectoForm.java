package transferencias.forms;

import transferencias.vos.RelacionEntregaVO;

import common.forms.CustomForm;

/**
 * Formulario de recogida de datos en la creación y edición de ingresos directos
 */
public class IngresoDirectoForm extends CustomForm {

	private static final long serialVersionUID = 1L;
	String id;
	String idArchivo;
	String idFondo;
	String serie;
	String nombreSerie;
	String idFormato;
	String formaDocumental;
	String observaciones;
	String idDescriptorProductor;
	String nombreProductor;
	String nombreArchivo;
	String idNivelDocumental;
	String idFicha;

	/**
	 * Identificador de la Revisión de Documentación.
	 */
	String idRevDoc;

	boolean permitidoModificarFormato = false;

	/**
	 * @return el formaDocumental
	 */
	public String getFormaDocumental() {
		return formaDocumental;
	}

	/**
	 * @param formaDocumental
	 *            el formaDocumental a establecer
	 */
	public void setFormaDocumental(String formaDocumental) {
		this.formaDocumental = formaDocumental;
	}

	/**
	 * @return el idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            el idArchivo a establecer
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return el idFondo
	 */
	public String getIdFondo() {
		return idFondo;
	}

	/**
	 * @param idFondo
	 *            el idFondo a establecer
	 */
	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	/**
	 * @return el idFormato
	 */
	public String getIdFormato() {
		return idFormato;
	}

	/**
	 * @param idFormato
	 *            el idFormato a establecer
	 */
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	/**
	 * @return el serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            el serie a establecer
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return el observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            el observaciones a establecer
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return el nombreSerie
	 */
	public String getNombreSerie() {
		return nombreSerie;
	}

	/**
	 * @param nombreSerie
	 *            el nombreSerie a establecer
	 */
	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el idDescriptorProductor
	 */
	public String getIdDescriptorProductor() {
		return idDescriptorProductor;
	}

	/**
	 * @param idDescriptorProductor
	 *            el idDescriptorProductor a establecer
	 */
	public void setIdDescriptorProductor(String idDescriptorProductor) {
		this.idDescriptorProductor = idDescriptorProductor;
	}

	/**
	 * @return el nombreProductor
	 */
	public String getNombreProductor() {
		return nombreProductor;
	}

	/**
	 * @param nombreProductor
	 *            el nombreProductor a establecer
	 */
	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	/**
	 * @return el nombrArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombrArchivo
	 *            el nombrArchivo a establecer
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * Establece los parámetros del formulario en un VO
	 * 
	 * @param vo
	 *            Vo a rellenar
	 * @return RelacionEntregaVO
	 */
	public RelacionEntregaVO populate(RelacionEntregaVO vo) {
		vo.setIdarchivoreceptor(this.getIdArchivo());
		vo.setIdfondodestino(this.getIdFondo());
		vo.setIdseriedestino(this.getSerie());
		vo.setIdformatoui(this.getIdFormato());
		vo.setTipoDocumental(this.getFormaDocumental());
		vo.setObservaciones(this.getObservaciones());
		vo.setIddescrorgproductor(this.getIdDescriptorProductor());
		vo.setIdNivelDocumental(vo.getIdNivelDocumental());
		vo.setIdFicha(vo.getIdFicha());
		return vo;
	}

	/**
	 * Estable en el formulario los campos del VO
	 * 
	 * @param vo
	 *            VO
	 */
	public void set(RelacionEntregaVO vo) {
		this.setId(vo.getId());
		this.setIdArchivo(vo.getIdarchivoreceptor());
		this.setIdFondo(vo.getIdfondodestino());
		this.setSerie(vo.getIdseriedestino());
		this.setIdFormato(vo.getIdformatoui());
		this.setFormaDocumental(vo.getTipoDocumental());
		this.setObservaciones(vo.getObservaciones());
		this.setIdNivelDocumental(vo.getIdNivelDocumental());
		this.setIdFicha(vo.getIdFicha());
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return the permitidoModificarFormato
	 */
	public boolean isPermitidoModificarFormato() {
		return permitidoModificarFormato;
	}

	/**
	 * @param permitidoModificarFormato
	 *            the permitidoModificarFormato to set
	 */
	public void setPermitidoModificarFormato(boolean permitidoModificarFormato) {
		this.permitidoModificarFormato = permitidoModificarFormato;
	}

	/**
	 * @return the idRevDoc
	 */
	public String getIdRevDoc() {
		return idRevDoc;
	}

	/**
	 * @param idRevDoc
	 *            the idRevDoc to set
	 */
	public void setIdRevDoc(String idRevDoc) {
		this.idRevDoc = idRevDoc;
	}
}