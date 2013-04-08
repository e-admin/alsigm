package transferencias.vos;

import transferencias.EstadoCotejo;

import common.Constants;
import common.db.DBUtils;
import common.vos.BaseVO;
import common.vos.IKeyId;

public class UnidadInstalacionBaseVO extends BaseVO implements
		IUnidadInstalacionVO, IKeyId {

	private static final long serialVersionUID = 1L;

	// private boolean devolver = false;
	private boolean changed;
	private String id;
	private String idRelEntrega = null;
	private int orden;
	private String idFormato = null;
	private String signaturaUI = null;
	private String notasCotejo = null;
	private int estadoCotejo = EstadoCotejo.PENDIENTE.getIdentificador();
	private String devolucion = Constants.FALSE_STRING;
	private boolean borrable = false;
	private String iduiubicada = null;

	public UnidadInstalacionBaseVO(String id, String idRelEntrega, int orden,
			String idFormato, String signaturaUI, String notasCotejo,
			int estadoCotejo, boolean devolucion) {
		super();
		this.id = id;
		this.idRelEntrega = idRelEntrega;
		this.orden = orden;
		this.idFormato = idFormato;
		this.signaturaUI = signaturaUI;
		this.notasCotejo = notasCotejo;
		this.estadoCotejo = estadoCotejo;
		this.devolucion = DBUtils.getStringValue(devolucion);
	}

	public UnidadInstalacionBaseVO(String idRelEntrega, String idFormato) {
		super();
		this.idRelEntrega = idRelEntrega;
		this.idFormato = idFormato;
	}

	public UnidadInstalacionBaseVO() {
		super();
	}

	/**
	 * @return el devolver
	 */
	public boolean isDevolver() {
		return DBUtils.getBooleanValue(devolucion);
	}

	/**
	 * @return el changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed
	 *            el changed a fijar
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el idRelEntrega
	 */
	public String getIdRelEntrega() {
		return idRelEntrega;
	}

	/**
	 * @param idRelEntrega
	 *            el idRelEntrega a fijar
	 */
	public void setIdRelEntrega(String idRelEntrega) {
		this.idRelEntrega = idRelEntrega;
	}

	/**
	 * @return el orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            el orden a fijar
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return el idFormato
	 */
	public String getIdFormato() {
		return idFormato;
	}

	/**
	 * @param idFormato
	 *            el idFormato a fijar
	 */
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	/**
	 * @return el signaturaUI
	 */
	public String getSignaturaUI() {
		return signaturaUI;
	}

	/**
	 * @param signaturaUI
	 *            el signaturaUI a fijar
	 */
	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}

	/**
	 * @return el notasCotejo
	 */
	public String getNotasCotejo() {
		return notasCotejo;
	}

	/**
	 * @param notasCotejo
	 *            el notasCotejo a fijar
	 */
	public void setNotasCotejo(String notasCotejo) {
		this.notasCotejo = notasCotejo;
	}

	/**
	 * @return el estadoCotejo
	 */
	public int getEstadoCotejo() {
		return estadoCotejo;
	}

	/**
	 * 
	 * POR COMPATIBILIDAD CON JSP
	 * 
	 * @return el estadoCotejo
	 */
	public int getEstadocotejo() {
		return estadoCotejo;
	}

	/**
	 * @param estadoCotejo
	 *            el estadoCotejo a fijar
	 */
	public void setEstadoCotejo(int estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	/**
	 * @return el devolucion
	 */
	public String getDevolucion() {
		return devolucion;
	}

	/**
	 * @param devolucion
	 *            el devolucion a fijar
	 */
	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}

	public boolean isConErrores() {
		return EstadoCotejo.isConErrores(estadoCotejo);
	}

	public boolean isPendiente() {
		return EstadoCotejo.isPendiente(estadoCotejo);
	}

	public boolean isRevisada() {
		return EstadoCotejo.isRevisada(estadoCotejo);
	}

	public void setIduiubicada(String iduiubicada) {
		this.iduiubicada = iduiubicada;
	}

	public String getIduiubicada() {
		return iduiubicada;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadInstalacionBaseVO other = (UnidadInstalacionBaseVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IUnidadInstalacionVO#getSignaturaUIOrigen()
	 */
	public String getSignaturaUIOrigen() {
		return getSignaturaUI();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IUnidadInstalacionVO#getNumeroUI()
	 */
	public String getNumeroUI() {
		return new Integer(orden).toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IUnidadInstalacionVO#setDevolver(boolean)
	 */
	public void setDevolver(boolean devolver) {
		this.devolucion = DBUtils.getStringValue(devolver);
	}

	public boolean isBorrable() {
		return borrable;
	}

	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}
}