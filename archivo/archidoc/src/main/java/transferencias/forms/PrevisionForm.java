package transferencias.forms;

import org.apache.log4j.Logger;

import transferencias.TipoTransferencia;
import transferencias.vos.PrevisionVO;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de información de una previsión
 * 
 */
public class PrevisionForm extends CustomForm {
	private static final long serialVersionUID = 1L;

	/** Logger de la clase */
	protected static final Logger logger = Logger
			.getLogger(PrevisionForm.class);

	int tipooperacion;
	String idfondo;
	String fechainitrans;
	String fechafintrans;
	int tipotransferencia = -1;
	int tipoprevision = -1;
	String numuinstalacion;
	String idprevision;
	String observaciones;
	String previsionesseleccionadas[];
	String motivorechazo;
	String idarchivoremitente;
	String idarchivoreceptor;

	public String getMotivorechazo() {
		return this.motivorechazo;
	}

	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
	}

	public String[] getPrevisionesseleccionadas() {
		return this.previsionesseleccionadas;
	}

	public void setPrevisionesseleccionadas(String[] previsionesseleccionadas) {
		this.previsionesseleccionadas = previsionesseleccionadas;
		this.idprevision = previsionesseleccionadas[0];
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return Returns the idprevision.
	 */
	public String getIdprevision() {
		return idprevision;
	}

	/**
	 * @param idprevision
	 *            The idprevision to set.
	 */
	public void setIdprevision(String idprevision) {
		this.idprevision = idprevision;
	}

	/**
	 * @return Returns the numuinstalacion.
	 */
	public String getNumuinstalacion() {
		return numuinstalacion;
	}

	/**
	 * @param numuinstalacion
	 *            The numuinstalacion to set.
	 */
	public void setNumuinstalacion(String numuinstalacion) {
		this.numuinstalacion = numuinstalacion;
	}

	/**
	 * @return Returns the tipoprevision.
	 */
	public int getTipoprevision() {
		return tipoprevision;
	}

	/**
	 * @param tipoprevision
	 *            The tipoprevision to set.
	 */
	public void setTipoprevision(int tipoprevision) {
		this.tipoprevision = tipoprevision;
	}

	/**
	 * @return Returns the tipotransferencia.
	 */
	public int getTipotransferencia() {
		return tipotransferencia;
	}

	/**
	 * @param tipotransferencia
	 *            The tipotransferencia to set.
	 */
	public void setTipotransferencia(int tipotransferencia) {
		this.tipotransferencia = tipotransferencia;
	}

	/**
	 * @return Returns the codfondo.
	 */
	public String getIdfondo() {
		return idfondo;
	}

	/**
	 * @param codfondo
	 *            The codfondo to set.
	 */
	public void setIdfondo(String codfondo) {
		this.idfondo = codfondo;
	}

	/**
	 * @return Returns the fechafintrans.
	 */
	public String getFechafintrans() {
		return fechafintrans;
	}

	/**
	 * @param fechafintrans
	 *            The fechafintrans to set.
	 */
	public void setFechafintrans(String fechafintrans) {
		this.fechafintrans = fechafintrans;
	}

	/**
	 * @return Returns the fechainitrans.
	 */
	public String getFechainitrans() {
		return fechainitrans;
	}

	/**
	 * @param fechainitrans
	 *            The fechainitrans to set.
	 */
	public void setFechainitrans(String fechainitrans) {
		this.fechainitrans = fechainitrans;
	}

	/**
	 * @return Returns the tipooperacion.
	 */
	public int getTipooperacion() {
		return tipooperacion;
	}

	/**
	 * @return el idarchivoreceptor
	 */
	public String getIdarchivoreceptor() {
		return idarchivoreceptor;
	}

	/**
	 * @param idarchivoreceptor
	 *            el idarchivoreceptor a establecer
	 */
	public void setIdarchivoreceptor(String idarchivoreceptor) {
		this.idarchivoreceptor = idarchivoreceptor;
	}

	/**
	 * @return el idarchivoremitente
	 */
	public String getIdarchivoremitente() {
		return idarchivoremitente;
	}

	/**
	 * @param idarchivoremitente
	 *            el idarchivoremitente a establecer
	 */
	public void setIdarchivoremitente(String idarchivoremitente) {
		this.idarchivoremitente = idarchivoremitente;
	}

	/**
	 * @param tipooperacion
	 *            The tipooperacion to set.
	 */
	public void setTipooperacion(int tipooperacion) {
		this.tipooperacion = tipooperacion;
		switch (tipooperacion) {
		case PrevisionVO.TRANSFERENCIA_ORDINARIA:
			setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
			setTipotransferencia(TipoTransferencia.ORDINARIA.getIdentificador());
			break;
		case PrevisionVO.TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_DETALLADA:
			setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
			setTipotransferencia(TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
					.getIdentificador());
			break;
		case PrevisionVO.TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_NODETALLADA:
			setTipoprevision(PrevisionVO.PREVISION_NODETALLADA);
			setTipotransferencia(TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
					.getIdentificador());
			break;
		case PrevisionVO.TRANSFERENCIA_EXTRAORDINARIA_SIGNATURIZADA:
			setTipoprevision(PrevisionVO.PREVISION_NODETALLADA);
			setTipotransferencia(TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
					.getIdentificador());
			break;
		case PrevisionVO.TRANSFERENCIA_ENTRE_ARCHIVOS:
			setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
			setTipotransferencia(TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador());
			break;
		}
	}
}