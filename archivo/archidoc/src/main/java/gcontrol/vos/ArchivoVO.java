/*
 * Created on 03-nov-2005
 *
 */
package gcontrol.vos;

import org.apache.commons.lang.builder.EqualsBuilder;

import transferencias.model.TipoSignaturacion;

import common.vos.BaseVO;
import common.vos.IKeyValue;

/**
 * @author ABELRL
 * 
 */
public class ArchivoVO extends BaseVO implements IKeyValue {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * ID VARCHAR2(32), CODIGO VARCHAR2(32), NOMBRE VARCHAR2(128), IDNIVEL
	 * VARCHAR(32), IDRECEPTORDEFECTO VARCHAR(32) NOMBRECORTO VARCHAR2(25),
	 * TIPOSIGNATURACION INT
	 */
	String id;

	String codigo;

	String nombre;

	String idnivel;

	String idreceptordefecto;

	String nombrecorto;

	int tiposignaturacion;

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return el tiposignaturacion
	 */
	public int getTiposignaturacion() {
		return tiposignaturacion;
	}

	/**
	 * @param tiposignaturacion
	 *            a establecer
	 */
	public void setTiposignaturacion(int tiposignaturacion) {
		this.tiposignaturacion = tiposignaturacion;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof ArchivoVO))
			return false;
		ArchivoVO castOther = (ArchivoVO) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public boolean isSignaturaAsociadaAHueco() {
		return tiposignaturacion == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
				.getIdentificador();
	}

	public boolean isSignaturaIndependienteDeHueco() {
		return tiposignaturacion == TipoSignaturacion.SIGNATURACION_INDEPENDIENTE_DE_HUECO
				.getIdentificador();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getValue()
	 */
	public String getValue() {
		return this.nombre;
	}
}