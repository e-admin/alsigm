/*
 * Created on 01-jun-2005
 *
 */
package transferencias.vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import transferencias.EstadoCotejo;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.util.DateUtils;
import common.view.IVisitedRowVO;

import deposito.vos.UDocEnUiDepositoVO;

/**
 * Value Object con la informacion referente a una unidad documental incorporada
 * a una unidad de instalacion En necesario contemplar el hecho de que las
 * unidades documentales pueden ser divididas en partes y cada una de esas
 * partes asignadas a una unidad de instalacion diferente
 */
public class ParteUnidadDocumentalVO implements IVisitedRowVO,
		IParteUnidadDocumentalVO {
	protected String asunto;
	protected String expediente;
	protected int totalPartes;
	protected String idRelentrega;
	protected String idUnidadDoc;
	protected String idUinstalacionRe;
	protected int posUdocEnUI;
	protected String udocCompleta;
	protected int estadoCotejo;
	protected String notasCotejo;
	protected String signaturaUdoc;
	protected int numParteUdoc;
	protected String descContenido;
	protected Date fechaInicio;
	protected Date fechaFin;
	protected int posCaja;
	// protected int numUI;
	protected String sigorigen;
	protected String signaturaUI;

	/**
	 * Propiedad de estilo de la fila para las listas.
	 */
	private String rowStyle = IVisitedRowVO.CSS_FILA_NORMAL;

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getIdRelentrega()
	 */
	public String getIdRelentrega() {
		return this.idRelentrega;
	}

	public void setIdRelentrega(String idRelentrega) {
		this.idRelentrega = idRelentrega;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getIdUinstalacionRe()
	 */
	public String getIdUinstalacionRe() {
		return this.idUinstalacionRe;
	}

	public void setIdUinstalacionRe(String idUinstalacionRe) {
		this.idUinstalacionRe = idUinstalacionRe;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getIdUnidadDoc()
	 */
	public String getIdUnidadDoc() {
		return this.idUnidadDoc;
	}

	public void setIdUnidadDoc(String idUnidadDoc) {
		this.idUnidadDoc = idUnidadDoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getNotasCotejo()
	 */
	public String getNotasCotejo() {
		return this.notasCotejo;
	}

	public void setNotasCotejo(String notasCotejo) {
		this.notasCotejo = notasCotejo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getNumParteUdoc()
	 */
	public int getNumParteUdoc() {
		return this.numParteUdoc;
	}

	public void setNumParteUdoc(int numParteUdoc) {
		this.numParteUdoc = numParteUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getPosUdocEnUI()
	 */
	public int getPosUdocEnUI() {
		return this.posUdocEnUI;
	}

	public void setPosUdocEnUI(int posUdocEnUI) {
		this.posUdocEnUI = posUdocEnUI;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getSignaturaUdoc()
	 */
	public String getSignaturaUdoc() {
		return this.signaturaUdoc;
	}

	public void setSignaturaUdoc(String signaturaUdoc) {
		this.signaturaUdoc = signaturaUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getUdocCompleta()
	 */
	public String getUdocCompleta() {
		return this.udocCompleta;
	}

	public void setUdocCompleta(String udocCompleta) {
		this.udocCompleta = udocCompleta;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getEstadoCotejo()
	 */
	public int getEstadoCotejo() {
		return estadoCotejo;
	}

	public void setEstadoCotejo(int estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getAsunto()
	 */
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getExpediente()
	 */
	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public int getTotalPartes() {
		return totalPartes;
	}

	public void setTotalPartes(int totalPartes) {
		this.totalPartes = totalPartes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#isConErrores()
	 */
	public boolean isConErrores() {
		return this.estadoCotejo == EstadoCotejo.ERRORES.getIdentificador();
	}

	public boolean isPendiente() {
		return this.estadoCotejo == EstadoCotejo.PENDIENTE.getIdentificador();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#isRevisada()
	 */
	public boolean isRevisada() {
		return this.estadoCotejo == EstadoCotejo.REVISADA.getIdentificador();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getDescContenido()
	 */
	public String getDescContenido() {
		return descContenido;
	}

	public void setDescContenido(String descContenido) {
		this.descContenido = descContenido;
	}

	boolean flagChanged = false;

	public boolean isFlagChanged() {
		return this.flagChanged;
	}

	public void setFlagChanged(boolean flagChanged) {
		this.flagChanged = flagChanged;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (this.getClass().isAssignableFrom(obj.getClass())) {
			IParteUnidadDocumentalVO parteUdoc = (IParteUnidadDocumentalVO) obj;
			if (parteUdoc.getIdUnidadDoc().equals(this.getIdUnidadDoc())
					&& parteUdoc.getNumParteUdoc() == this.getNumParteUdoc())
				result = true;
		}
		return result;
	}

	public static List getPartesUdoc(UnidadDocumentalVO unidadDocumental) {
		int numeroPartes = unidadDocumental.getNumeroPartes();
		List partes = new ArrayList();
		for (int i = 1; i <= numeroPartes; i++)
			partes.add(generateParteUdoc(unidadDocumental, i, null));
		return partes;
	}

	public static ParteUnidadDocumentalVO generateParteUdoc(
			UnidadDocumentalVO unidadDocumental, int numeroParte,
			String signatura) {
		ParteUnidadDocumentalVO parteUdoc = new ParteUnidadDocumentalVO();
		parteUdoc.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());
		parteUdoc.setIdRelentrega(unidadDocumental.getIdRelEntrega());
		parteUdoc.setIdUnidadDoc(unidadDocumental.getId());
		parteUdoc.setAsunto(unidadDocumental.getAsunto());
		parteUdoc.setExpediente(unidadDocumental.getNumeroExpediente());
		parteUdoc.setTotalPartes(unidadDocumental.getNumeroPartes());
		parteUdoc.setNumParteUdoc(numeroParte);
		parteUdoc.setSignaturaUdoc(signatura);

		// TODO: Revisar que esto es correcto Añadido por error en transferencia
		// Extraordinaria Sin Signatura de formato irregular
		parteUdoc
				.setUdocCompleta(unidadDocumental.getNumeroPartes() == 1 ? Constants.TRUE_STRING
						: Constants.FALSE_STRING);
		return parteUdoc;
	}

	// public static ParteUnidadDocumentalVO createParteUdoc(String
	// idUnidadDocumental,
	// String idRelacion, int numeroParte, String signatura) {
	// ParteUnidadDocumentalVO parteUdoc = new ParteUnidadDocumentalVO();
	// parteUdoc.setEstadoCotejo(EstadoCotejo.PENDIENTE.getIdentificador());
	// parteUdoc.setIdRelentrega(idRelacion);
	// parteUdoc.setIdUnidadDoc(idUnidadDocumental);
	// parteUdoc.setNumParteUdoc(numeroParte);
	// parteUdoc.setSignaturaUdoc(signatura);
	// return parteUdoc;
	// }

	public static ParteUnidadDocumentalVO createParteUdoc(
			UnidadDocumentalVO unidadDocumental, String signatura) {
		int numeroParte = unidadDocumental.getNumeroPartes() + 1;
		return generateParteUdoc(unidadDocumental, numeroParte, signatura);
	}

	public boolean isParteFinal() {
		return getNumParteUdoc() == getTotalPartes();
	}

	public boolean isPrimeraParte() {
		return getNumParteUdoc() == 1;
	}

	public boolean isParteIntermedia() {
		return !isParteFinal() && !isPrimeraParte();
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getFechaFin()
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getFechaInicio()
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getPosCaja() {
		return posCaja;
	}

	public void setPosCaja(int posCaja) {
		this.posCaja = posCaja;
	}

	public String getSigorigen() {
		return sigorigen;
	}

	public void setSigorigen(String sigorigen) {
		this.sigorigen = sigorigen;
	}

	public String getSignaturaUI() {
		return signaturaUI;
	}

	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}

	// Método utilizado para poder copiar la signatura que se devuelve en el
	// objeto UDocEnUiDepositoVO
	// en un objeto de tipo ParteUnidadDocumentalVO por compatibilidad con las
	// cartelas de UDocs de Archivo
	public static IParteUnidadDocumentalVO copyProperties(
			UDocEnUiDepositoVO udocEnUIDeposito) {

		ParteUnidadDocumentalVO parteUDocVO = new ParteUnidadDocumentalVO();

		parteUDocVO.setSignaturaUdoc(udocEnUIDeposito.getSignaturaudoc());
		parteUDocVO.setIdUnidadDoc(udocEnUIDeposito.getIdunidaddoc());

		return parteUDocVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getParteExp()
	 */
	public String getParteExp() {
		return CodigoTransferenciaUtils.getParteUnidadDocumental(numParteUdoc,
				totalPartes);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getValorFechaFin()
	 */
	public String getValorFechaFin() {
		return DateUtils.formatDate(fechaFin);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.vos.IParteUnidadDocumentalVO#getValorFechaInicio()
	 */
	public String getValorFechaInicio() {
		return DateUtils.formatDate(fechaInicio);
	}
}