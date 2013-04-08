package transferencias.vos;

import transferencias.EstadoCotejo;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.db.DBUtils;
import common.view.IVisitedRowVO;
import common.vos.BaseVO;
import common.vos.IKeyId;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UDocEnUIReeaCRVO extends BaseVO implements IKeyId,
		IParteUnidadDocumentalVO {

	private static final long serialVersionUID = 1L;

	private String id = new String();
	private String idUDocOrigen = new String();
	private String idRelEntrega = new String();
	private String idUnidadDoc = new String();
	private String idUIReeaCR = new String();
	private String signaturaUDoc = new String();
	private int posUdocEnUI;
	private int numParte;
	private String udocCompleta = new String();
	private String asunto = new String();
	private String numExp = new String();
	private String fechaIni = new String();
	private String fechaFin = new String();
	private String signaturaUdocOrigen = new String();
	private String descripcion = new String();
	private int posCaja;

	private String rowStyle = IVisitedRowVO.CSS_FILA_NORMAL;

	private int totalPartes = -1;
	private int estadoCotejo = EstadoCotejo.PENDIENTE.getIdentificador();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUDocOrigen() {
		return idUDocOrigen;
	}

	public void setIdUDocOrigen(String idUDocOrigen) {
		this.idUDocOrigen = idUDocOrigen;
	}

	public String getIdRelEntrega() {
		return idRelEntrega;
	}

	public void setIdRelEntrega(String idRelEntrega) {
		this.idRelEntrega = idRelEntrega;
	}

	public String getIdUnidadDoc() {
		return idUnidadDoc;
	}

	public void setIdUnidadDoc(String idUnidadDoc) {
		this.idUnidadDoc = idUnidadDoc;
	}

	public String getIdUIReeaCR() {
		return idUIReeaCR;
	}

	public void setIdUIReeaCR(String idUIReeaCR) {
		this.idUIReeaCR = idUIReeaCR;
	}

	public String getSignaturaUDoc() {
		return signaturaUDoc;
	}

	public void setSignaturaUDoc(String signaturaUDoc) {
		this.signaturaUDoc = signaturaUDoc;
	}

	public int getPosUdocEnUI() {
		return posUdocEnUI;
	}

	public void setPosUdocEnUI(int posUdocEnUI) {
		this.posUdocEnUI = posUdocEnUI;
	}

	public int getNumParte() {
		return numParte;
	}

	public void setNumParte(int numParte) {
		this.numParte = numParte;
	}

	public String getUdocCompleta() {
		return udocCompleta;
	}

	public void setUdocCompleta(String udocCompleta) {
		this.udocCompleta = udocCompleta;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getSignaturaUdocOrigen() {
		return signaturaUdocOrigen;
	}

	public void setSignaturaUdocOrigen(String signaturaUdocOrigen) {
		this.signaturaUdocOrigen = signaturaUdocOrigen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstadoCotejo(int estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	public void setTotalPartes(int totalPartes) {
		this.totalPartes = totalPartes;
	}

	public int getTotalPartes() {
		return totalPartes;
	}

	// METODOS IMPLEMENTADOS
	public boolean isCompleta() {
		return DBUtils.getBooleanValue(udocCompleta);
	}

	public void setCompleta(boolean udocCompleta) {
		this.udocCompleta = DBUtils.getStringValue(udocCompleta);
	}

	public boolean isValidada() {
		return idUnidadDoc != null;
	}

	// FIN METODOS IMPLEMENTADOS
	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getDescContenido()
	 */
	public String getDescContenido() {
		return descripcion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getEstadoCotejo()
	 */
	public int getEstadoCotejo() {
		return estadoCotejo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getExpediente()
	 */
	public String getExpediente() {
		return getNumExp();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getIdRelentrega()
	 */
	public String getIdRelentrega() {
		return idRelEntrega;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getNotasCotejo()
	 */
	public String getNotasCotejo() {
		return Constants.STRING_EMPTY;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getNumParteUdoc()
	 */
	public int getNumParteUdoc() {
		return numParte;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getParteExp()
	 */
	public String getParteExp() {
		return CodigoTransferenciaUtils.getParteUnidadDocumental(numParte,
				totalPartes);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getSignaturaUdoc()
	 */
	public String getSignaturaUdoc() {
		return signaturaUDoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getValorFechaFin()
	 */
	public String getValorFechaFin() {
		return fechaFin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getValorFechaInicio()
	 */
	public String getValorFechaInicio() {
		return fechaIni;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#isConErrores()
	 */
	public boolean isConErrores() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#isRevisada()
	 */
	public boolean isRevisada() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#setExpediente(java.lang.String)
	 */
	public void setExpediente(String expediente) {
		this.numExp = expediente;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#setNotasCotejo(java.lang.String)
	 */
	public void setNotasCotejo(String notasCotejo) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#setSignaturaUdoc(java.lang.String)
	 */
	public void setSignaturaUdoc(String signaturaUdoc) {
		this.signaturaUDoc = signaturaUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getIdUinstalacionRe()
	 */
	public String getIdUinstalacionRe() {
		return getIdUinstalacionRe();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getRowStyle()
	 */
	public String getRowStyle() {
		return rowStyle;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#setRowStyle(java.lang.String)
	 */
	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	public void setPosCaja(int posCaja) {
		this.posCaja = posCaja;
	}

	public int getPosCaja() {
		return posCaja;
	}

}