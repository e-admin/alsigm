package gcontrol.forms;

import gcontrol.vos.ArchivoVO;

import org.apache.commons.lang.StringUtils;

import transferencias.model.TipoSignaturacion;

import common.Constants;
import common.forms.CustomForm;

public class ArchivoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String codigo;
	private String nombre;
	private String idnivel;
	private String idreceptordefecto;
	private String nombrecorto;

	private String idniveloriginal;

	private String permitirEditarCodigoArchivo = Constants.FALSE_STRING;

	private String tiposignaturacion;

	private String nombreSignaturacion;

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
	 * @return el idnivel
	 */
	public String getIdnivel() {
		return idnivel;
	}

	/**
	 * @param idnivel
	 *            el idnivel a establecer
	 */
	public void setIdnivel(String idnivel) {
		this.idnivel = idnivel;
	}

	/**
	 * @return el idreceptordefecto
	 */
	public String getIdreceptordefecto() {
		return idreceptordefecto;
	}

	/**
	 * @param idreceptordefecto
	 *            el idreceptordefecto a establecer
	 */
	public void setIdreceptordefecto(String idreceptordefecto) {
		this.idreceptordefecto = idreceptordefecto;
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
	 * @return el idniveloriginal
	 */
	public String getIdniveloriginal() {
		return idniveloriginal;
	}

	/**
	 * @param idniveloriginal
	 *            el idniveloriginal a establecer
	 */
	public void setIdniveloriginal(String idniveloriginal) {
		this.idniveloriginal = idniveloriginal;
	}

	/**
	 * @return el nombrecorto
	 */
	public String getNombrecorto() {
		return nombrecorto;
	}

	/**
	 * @param nombrecorto
	 *            el nombrecorto a establecer
	 */
	public void setNombrecorto(String nombrecorto) {
		this.nombrecorto = nombrecorto;
	}

	/**
	 * @return el permitirEditarCodigoArchivo
	 */
	public String getPermitirEditarCodigoArchivo() {
		return permitirEditarCodigoArchivo;
	}

	/**
	 * @param permitirEditarCodigoArchivo
	 *            el permitirEditarCodigoArchivo a establecer
	 */
	public void setPermitirEditarCodigoArchivo(
			String permitirEditarCodigoArchivo) {
		this.permitirEditarCodigoArchivo = permitirEditarCodigoArchivo;
	}

	/**
	 * @return el tipo de signaturacion que se esta utilizando
	 */
	public String getTiposignaturacion() {
		return tiposignaturacion;
	}

	/**
	 * @param tiposignaturacion
	 *            el tipo de signaturación a establecer
	 */
	public void setTiposignaturacion(String tiposignaturacion) {
		this.tiposignaturacion = tiposignaturacion;
	}

	/**
	 * @return nombreSignaturacion
	 */
	public String getNombreSignaturacion() {
		return nombreSignaturacion;
	}

	/**
	 * @param nombreSignaturacion
	 *            el nombre del tipo de signaturacion a establecer
	 */
	public void setNombreSignaturacion(String nombreSignaturacion) {
		this.nombreSignaturacion = nombreSignaturacion;
	}

	public void set(ArchivoVO archivoVO) {
		reset();
		if (archivoVO != null) {
			this.id = archivoVO.getId();
			this.codigo = archivoVO.getCodigo();
			this.nombre = archivoVO.getNombre();
			this.idnivel = archivoVO.getIdnivel();
			this.idreceptordefecto = archivoVO.getIdreceptordefecto();
			this.nombrecorto = archivoVO.getNombrecorto();
			this.tiposignaturacion = String.valueOf(archivoVO
					.getTiposignaturacion());
		}
	}

	public void populate(ArchivoVO archivoVO) {
		archivoVO.setId(this.id);
		archivoVO.setCodigo(this.codigo);
		archivoVO.setNombre(this.nombre);
		archivoVO.setIdnivel(this.idnivel);
		archivoVO.setIdreceptordefecto(this.idreceptordefecto);
		archivoVO.setNombrecorto(this.nombrecorto);
		archivoVO
				.setTiposignaturacion(TipoSignaturacion.SIGNATURACION_INDEPENDIENTE_DE_HUECO
						.getIdentificador());
		if (!StringUtils.isEmpty(this.tiposignaturacion))
			archivoVO.setTiposignaturacion(Integer
					.parseInt(this.tiposignaturacion));
	}

	public void reset() {
		this.id = null;
		this.nombre = null;
		this.codigo = null;
		this.idnivel = null;
		this.idreceptordefecto = null;
		this.idniveloriginal = null;
		this.nombrecorto = null;
		this.tiposignaturacion = null;
	}

}
