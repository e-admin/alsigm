package transferencias.forms;

import java.util.HashMap;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import transferencias.EstadoCotejo;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.UDocElectronicaVO;

import common.forms.CustomForm;

import deposito.global.Constants;

/**
 * Formulario para recogida de datos en el cotejo y signaturación de relaciones
 * de entrega
 * 
 */
public class CotejoysignaturizacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public final static int VALIDA_GUARDAR_FINALIZAR_COTEJO = 1;
	public final static int VALIDA_FINALIZAR_COTEJO = 2;
	private final static String NO_ES_POSIBLE_FIN_COTEJO_UDOCS_PENDIENTES = common.Constants.ERRORS_RELACIONES_FIN_COTEJO_X_UDOCS_PENDIENTES;

	String codigoseleccionada;
	HashMap estadocotejounidaddocumentales = new HashMap();
	HashMap observacioneserror = new HashMap();
	HashMap devolverunidaddocumental = new HashMap();
	HashMap observacionescaja = new HashMap();
	// String depositoseleccionado;
	String[] partescambiadas;
	String[] cajascambiadas;
	String[] cajasabiertas;
	String ultimacajavista;
	String numcajaseleccionada;
	String urlverudoc;
	String idCaja;
	String notasCotejo;
	String devolver;
	String idUdoc;
	String estadoCotejo;
	String idRelacion;

	/**
	 * Si tiene valor 'S' indica que se corrige en archivo
	 */
	String correccionenarchivo;

	String ordenCaja;

	public String getIdUdoc() {
		return idUdoc;
	}

	public void setIdUdoc(String idUdoc) {
		this.idUdoc = idUdoc;
	}

	public String getUrlverudoc() {
		return this.urlverudoc;
	}

	public void setUrlverudoc(String urlverudoc) {
		this.urlverudoc = urlverudoc;
	}

	public String getNumcajaseleccionada() {
		return this.numcajaseleccionada;
	}

	public void setNumcajaseleccionada(String numcajaseleccionada) {
		this.numcajaseleccionada = numcajaseleccionada;
	}

	public String getUltimacajavista() {
		return this.ultimacajavista;
	}

	public void setUltimacajavista(String ultimacajavista) {
		this.ultimacajavista = ultimacajavista;
	}

	public String[] getCajasabiertas() {
		return this.cajasabiertas;
	}

	public void setCajasabiertas(String[] cajasabiertas) {
		this.cajasabiertas = cajasabiertas;
	}

	public String[] getPartescambiadas() {
		return this.partescambiadas;
	}

	public void setPartescambiadas(String[] partescambiadas) {
		this.partescambiadas = partescambiadas;
	}

	public String[] getCajascambiadas() {
		return this.cajascambiadas;
	}

	public void setCajascambiadas(String[] cajascambiadas) {
		this.cajascambiadas = cajascambiadas;
	}

	// public String getDepositoseleccionado() {
	// return depositoseleccionado;
	// }
	// public void setDepositoseleccionado(String depositoseleccionado) {
	// this.depositoseleccionado = depositoseleccionado;
	// }
	public String getCodigoseleccionada() {
		return codigoseleccionada;
	}

	public void setCodigoseleccionada(String codigoseleccionada) {
		this.codigoseleccionada = codigoseleccionada;
	}

	public void setEstadocotejounidaddocumental(String key, String value) {
		estadocotejounidaddocumentales.put(key, value);
	}

	public String getDevolverunidaddocumental(String key) {
		return (String) devolverunidaddocumental.get(key);
	}

	public void setDevolverunidaddocumental(String key, String value) {
		devolverunidaddocumental.put(key, value);
	}

	public String getEstadocotejoudocelectronica(
			UDocElectronicaVO udocElectronica) {
		return (String) estadocotejounidaddocumentales.get(udocElectronica
				.getId());
	}

	public String getEstadocotejounidaddocumental(
			IParteUnidadDocumentalVO parteUdoc) {
		String parteUdocKey = new StringBuffer(parteUdoc.getIdUnidadDoc())
				.append(":").append(parteUdoc.getNumParteUdoc()).toString();
		return (String) estadocotejounidaddocumentales.get(parteUdocKey);
	}

	public String getEstadocotejounidaddocumental(String parteUdocKey) {
		return (String) estadocotejounidaddocumentales.get(parteUdocKey);
	}

	public String getObservacioneserror(IParteUnidadDocumentalVO parteUdoc) {
		String parteUdocKey = new StringBuffer(parteUdoc.getIdUnidadDoc())
				.append(":").append(parteUdoc.getNumParteUdoc()).toString();
		return (String) observacioneserror.get(parteUdocKey);
	}

	public void setObservacioneserror(String key, String value) {
		observacioneserror.put(key, value);
	}

	public String getObservacionescaja(String key) {
		return (String) observacionescaja.get(key);
	}

	public void setObservacionescaja(String key, String value) {
		observacionescaja.put(key, value);
	}

	public boolean existUnidadesDocumentalesPendientes() {
		return estadocotejounidaddocumentales
				.containsValue(EstadoCotejo.PENDIENTE);
	}

	public ActionErrors validate(int tipo) {
		ActionErrors ret = null;
		/*
		 * if (tipo == VALIDA_GUARDAR_FINALIZAR_COTEJO){ if
		 * (depositoseleccionado==null){
		 * 
		 * ret=new ActionErrors(); ret.add(NECESARIO_SELECCIONAR_UBICACION, new
		 * ActionError(NECESARIO_SELECCIONAR_UBICACION)); }
		 * 
		 * }else
		 */

		if (tipo == VALIDA_FINALIZAR_COTEJO) {
			if (existUnidadesDocumentalesPendientes()) {
				ret = new ActionErrors();
				ret.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.ERROR_GENERAL_MESSAGE,
						NO_ES_POSIBLE_FIN_COTEJO_UDOCS_PENDIENTES));
			}
		}
		return ret;
	}

	/*
	 * public ArrayList getUnidadesHuecosSeleccionados(String
	 * idTipoNoAsignable){ ArrayList ret=new ArrayList(); HashMap
	 * mapIdSeleccionados=(HashMap)huecosseleccionados.get(idTipoNoAsignable);
	 * if(mapIdSeleccionados!=null){ Iterator
	 * it=mapIdSeleccionados.keySet().iterator(); while(it.hasNext()){
	 * ret.add((String)it.next()); } } return ret; }
	 */
	/*
	 * partes cambiadas es una array de string con los ids de las partes
	 * cambiadas, estos string seran vacios si la parte no ha cambiado
	 */
	public boolean hayPartesCambiadas() {
		for (int i = 0; i < partescambiadas.length; i++) {
			if (partescambiadas[i].length() > 0)
				return true;
		}
		return false;
	}

	public boolean hayUInstalacionCambiadas() {
		for (int i = 0; i < cajascambiadas.length; i++) {
			if (cajascambiadas[i].length() > 0)
				return true;
		}
		return false;
	}

	/**
	 * @return Returns the idCaja.
	 */
	public String getIdCaja() {
		return idCaja;
	}

	/**
	 * @param idCaja
	 *            The idCaja to set.
	 */
	public void setIdCaja(String idCaja) {
		this.idCaja = idCaja;
	}

	/**
	 * @return Returns the notasCotejo.
	 */
	public String getNotasCotejo() {
		return notasCotejo;
	}

	/**
	 * @param notasCotejo
	 *            The notasCotejo to set.
	 */
	public void setNotasCotejo(String notasCotejo) {
		this.notasCotejo = notasCotejo;
	}

	/**
	 * @return Returns the devolver.
	 */
	public String getDevolver() {
		return devolver;
	}

	/**
	 * @param devolver
	 *            The devolver to set.
	 */
	public void setDevolver(String devolver) {
		this.devolver = devolver;
	}

	public String getEstadoCotejo() {
		return estadoCotejo;
	}

	public void setEstadoCotejo(String estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	/**
	 * @return el correccionenarchivo
	 */
	public String getCorreccionenarchivo() {
		return correccionenarchivo;
	}

	/**
	 * @param correccionenarchivo
	 *            el correccionenarchivo a establecer
	 */
	public void setCorreccionenarchivo(String correccionenarchivo) {
		this.correccionenarchivo = correccionenarchivo;
	}

	/**
	 * @return el ordenCaja
	 */
	public String getOrdenCaja() {
		return ordenCaja;
	}

	/**
	 * @param ordenCaja
	 *            el ordenCaja a establecer
	 */
	public void setOrdenCaja(String ordenCaja) {
		this.ordenCaja = ordenCaja;
	}

	public HashMap getEstadocotejounidaddocumentales() {
		return estadocotejounidaddocumentales;
	}

	public void setEstadocotejounidaddocumentales(
			HashMap estadocotejounidaddocumentales) {
		this.estadocotejounidaddocumentales = estadocotejounidaddocumentales;
	}

	public HashMap getObservacioneserror() {
		return observacioneserror;
	}

	public void setObservacioneserror(HashMap observacioneserror) {
		this.observacioneserror = observacioneserror;
	}

	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

}
