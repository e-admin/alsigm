/**
 *
 */
package deposito.forms;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;

import deposito.DepositoConstants;
import deposito.vos.BusquedaUIAnioSerieVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaUISAnioYSerieForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String[] codOrdenAmbito = new String[0];
	private String[] idAmbito = new String[0];
	private String[] nombreAmbito = new String[0];
	private String[] idObjetoAmbito = new String[0];
	private String[] nombreObjetoAmbito = new String[0];

	private String idArchivo = null;
	private String nombreArchivo = null;

	private String productores = null;
	private String formato = null;

	/* Fecha inicial */
	private String fechaCompIni = null;
	private String fechaFormatoIni = null;
	private String fechaAIni = null;
	private String fechaMIni = null;
	private String fechaDIni = null;
	private String fechaSIni = null;
	private String fechaIniFormatoIni = null;
	private String fechaIniAIni = null;
	private String fechaIniMIni = null;
	private String fechaIniDIni = null;
	private String fechaIniSIni = null;
	private String fechaFinFormatoIni = null;
	private String fechaFinAIni = null;
	private String fechaFinMIni = null;
	private String fechaFinDIni = null;
	private String fechaFinSIni = null;

	/* Fecha final */
	private String fechaCompFin = null;
	private String fechaFormatoFin = null;
	private String fechaAFin = null;
	private String fechaMFin = null;
	private String fechaDFin = null;
	private String fechaSFin = null;
	private String fechaIniFormatoFin = null;
	private String fechaIniAFin = null;
	private String fechaIniMFin = null;
	private String fechaIniDFin = null;
	private String fechaIniSFin = null;
	private String fechaFinFormatoFin = null;
	private String fechaFinAFin = null;
	private String fechaFinMFin = null;
	private String fechaFinDFin = null;
	private String fechaFinSFin = null;

	private String idUbicacion = null;
	private String[] idAmbitoDeposito = new String[0];
	private String[] codOrdenAmbitoDeposito = new String[0];
	private String[] nombreAmbitoDeposito = new String[0];

	public String[] getIdAmbitoDeposito() {
		return idAmbitoDeposito;
	}

	public void setIdAmbitoDeposito(String[] idAmbitoDeposito) {
		this.idAmbitoDeposito = idAmbitoDeposito;
	}

	public String[] getCodOrdenAmbitoDeposito() {
		return codOrdenAmbitoDeposito;
	}

	public void setCodOrdenAmbitoDeposito(String[] codOrdenAmbitoDeposito) {
		this.codOrdenAmbitoDeposito = codOrdenAmbitoDeposito;
	}

	public String[] getNombreAmbitoDeposito() {
		return nombreAmbitoDeposito;
	}

	public void setNombreAmbitoDeposito(String[] nombreAmbitoDeposito) {
		this.nombreAmbitoDeposito = nombreAmbitoDeposito;
	}

	public String[] getCodOrdenAmbito() {
		return codOrdenAmbito;
	}

	public void setCodOrdenAmbito(String[] codOrdenAmbito) {
		this.codOrdenAmbito = codOrdenAmbito;
	}

	public String[] getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(String[] idAmbito) {
		this.idAmbito = idAmbito;
	}

	public String[] getNombreAmbito() {
		return nombreAmbito;
	}

	public void setNombreAmbito(String[] nombreAmbito) {
		this.nombreAmbito = nombreAmbito;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getProductores() {
		return productores;
	}

	public void setProductores(String productores) {
		this.productores = productores;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getFechaCompIni() {
		return fechaCompIni;
	}

	public void setFechaCompIni(String fechaCompIni) {
		this.fechaCompIni = fechaCompIni;
	}

	public String getFechaFormatoIni() {
		return fechaFormatoIni;
	}

	public void setFechaFormatoIni(String fechaFormatoIni) {
		this.fechaFormatoIni = fechaFormatoIni;
	}

	public String getFechaAIni() {
		return fechaAIni;
	}

	public void setFechaAIni(String fechaAIni) {
		this.fechaAIni = fechaAIni;
	}

	public String getFechaMIni() {
		return fechaMIni;
	}

	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	public String getFechaDIni() {
		return fechaDIni;
	}

	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	public String getFechaSIni() {
		return fechaSIni;
	}

	public void setFechaSIni(String fechaSIni) {
		this.fechaSIni = fechaSIni;
	}

	public String getFechaIniFormatoIni() {
		return fechaIniFormatoIni;
	}

	public void setFechaIniFormatoIni(String fechaIniFormatoIni) {
		this.fechaIniFormatoIni = fechaIniFormatoIni;
	}

	public String getFechaIniAIni() {
		return fechaIniAIni;
	}

	public void setFechaIniAIni(String fechaIniAIni) {
		this.fechaIniAIni = fechaIniAIni;
	}

	public String getFechaIniMIni() {
		return fechaIniMIni;
	}

	public void setFechaIniMIni(String fechaIniMIni) {
		this.fechaIniMIni = fechaIniMIni;
	}

	public String getFechaIniDIni() {
		return fechaIniDIni;
	}

	public void setFechaIniDIni(String fechaIniDIni) {
		this.fechaIniDIni = fechaIniDIni;
	}

	public String getFechaIniSIni() {
		return fechaIniSIni;
	}

	public void setFechaIniSIni(String fechaIniSIni) {
		this.fechaIniSIni = fechaIniSIni;
	}

	public String getFechaFinFormatoIni() {
		return fechaFinFormatoIni;
	}

	public void setFechaFinFormatoIni(String fechaFinFormatoIni) {
		this.fechaFinFormatoIni = fechaFinFormatoIni;
	}

	public String getFechaFinAIni() {
		return fechaFinAIni;
	}

	public void setFechaFinAIni(String fechaFinAIni) {
		this.fechaFinAIni = fechaFinAIni;
	}

	public String getFechaFinMIni() {
		return fechaFinMIni;
	}

	public void setFechaFinMIni(String fechaFinMIni) {
		this.fechaFinMIni = fechaFinMIni;
	}

	public String getFechaFinDIni() {
		return fechaFinDIni;
	}

	public void setFechaFinDIni(String fechaFinDIni) {
		this.fechaFinDIni = fechaFinDIni;
	}

	public String getFechaFinSIni() {
		return fechaFinSIni;
	}

	public void setFechaFinSIni(String fechaFinSIni) {
		this.fechaFinSIni = fechaFinSIni;
	}

	public String getFechaCompFin() {
		return fechaCompFin;
	}

	public void setFechaCompFin(String fechaCompFin) {
		this.fechaCompFin = fechaCompFin;
	}

	public String getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	public void setFechaFormatoFin(String fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	public String getFechaAFin() {
		return fechaAFin;
	}

	public void setFechaAFin(String fechaAFin) {
		this.fechaAFin = fechaAFin;
	}

	public String getFechaMFin() {
		return fechaMFin;
	}

	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	public String getFechaDFin() {
		return fechaDFin;
	}

	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	public String getFechaSFin() {
		return fechaSFin;
	}

	public void setFechaSFin(String fechaSFin) {
		this.fechaSFin = fechaSFin;
	}

	public String getFechaIniFormatoFin() {
		return fechaIniFormatoFin;
	}

	public void setFechaIniFormatoFin(String fechaIniFormatoFin) {
		this.fechaIniFormatoFin = fechaIniFormatoFin;
	}

	public String getFechaIniAFin() {
		return fechaIniAFin;
	}

	public void setFechaIniAFin(String fechaIniAFin) {
		this.fechaIniAFin = fechaIniAFin;
	}

	public String getFechaIniMFin() {
		return fechaIniMFin;
	}

	public void setFechaIniMFin(String fechaIniMFin) {
		this.fechaIniMFin = fechaIniMFin;
	}

	public String getFechaIniDFin() {
		return fechaIniDFin;
	}

	public void setFechaIniDFin(String fechaIniDFin) {
		this.fechaIniDFin = fechaIniDFin;
	}

	public String getFechaIniSFin() {
		return fechaIniSFin;
	}

	public void setFechaIniSFin(String fechaIniSFin) {
		this.fechaIniSFin = fechaIniSFin;
	}

	public String getFechaFinFormatoFin() {
		return fechaFinFormatoFin;
	}

	public void setFechaFinFormatoFin(String fechaFinFormatoFin) {
		this.fechaFinFormatoFin = fechaFinFormatoFin;
	}

	public String getFechaFinAFin() {
		return fechaFinAFin;
	}

	public void setFechaFinAFin(String fechaFinAFin) {
		this.fechaFinAFin = fechaFinAFin;
	}

	public String getFechaFinMFin() {
		return fechaFinMFin;
	}

	public void setFechaFinMFin(String fechaFinMFin) {
		this.fechaFinMFin = fechaFinMFin;
	}

	public String getFechaFinDFin() {
		return fechaFinDFin;
	}

	public void setFechaFinDFin(String fechaFinDFin) {
		this.fechaFinDFin = fechaFinDFin;
	}

	public String getFechaFinSFin() {
		return fechaFinSFin;
	}

	public void setFechaFinSFin(String fechaFinSFin) {
		this.fechaFinSFin = fechaFinSFin;
	}

	public void setIdObjetoAmbito(String[] idObjetoAmbito) {
		this.idObjetoAmbito = idObjetoAmbito;
	}

	public String[] getIdObjetoAmbito() {
		return idObjetoAmbito;
	}

	public void populate(BusquedaUIAnioSerieVO vo, AppUser appUser) {

		if (StringUtils.isNotBlank(getIdArchivo())) {
			vo.setIdsArchivo(new String[] { getIdArchivo() });
		} else {
			vo.setIdsArchivo(appUser.getIdsArchivosUser());
		}

		if (ArrayUtils.isNotEmptyOrBlank(getIdObjetoAmbito())) {
			vo.setIdsAmbitoSeries(getIdObjetoAmbito());
		}

		if (ArrayUtils.isNotEmptyOrBlank(getIdAmbitoDeposito())) {
			vo.setIdsAmbitoDeposito(getIdAmbitoDeposito());
			;
		}

		vo.setIdUbicacion(getIdUbicacion());
		vo.setIdFormato(getFormato());
		vo.setProductores(getArrayProductores());

		/* Fecha Extrema Inicial 3 */
		CustomDateRange rangeIni = CustomDateFormat
				.getDateRange(getFechaCompIni(), new CustomDate(
						getFechaFormatoIni(), getFechaAIni(), getFechaMIni(),
						getFechaDIni(), getFechaSIni()),
						new CustomDate(getFechaIniFormatoIni(),
								getFechaIniAIni(), getFechaIniMIni(),
								getFechaIniDIni(), getFechaIniSIni()),
						new CustomDate(getFechaFinFormatoIni(),
								getFechaFinAIni(), getFechaFinMIni(),
								getFechaFinDIni(), getFechaFinSIni()));

		vo.setFechaExtremaInicialIni(rangeIni.getInitialDate());
		vo.setFechaExtremaInicialFin(rangeIni.getFinalDate());
		vo.setFechaExtremaInicialOperador(getFechaCompIni());

		/* Fecha Extrema Final 4 */
		CustomDateRange rangeFin = CustomDateFormat
				.getDateRange(getFechaCompFin(), new CustomDate(
						getFechaFormatoFin(), getFechaAFin(), getFechaMFin(),
						getFechaDFin(), getFechaSFin()),
						new CustomDate(getFechaIniFormatoFin(),
								getFechaIniAFin(), getFechaIniMFin(),
								getFechaIniDFin(), getFechaIniSFin()),
						new CustomDate(getFechaFinFormatoFin(),
								getFechaFinAFin(), getFechaFinMFin(),
								getFechaFinDFin(), getFechaFinSFin()));

		vo.setFechaExtremaFinalIni(rangeFin.getInitialDate());
		vo.setFechaExtremaFinalFin(rangeFin.getFinalDate());
		vo.setFechaExtremaFinalOperador(getFechaCompFin());

	}

	public ActionErrors validate(Locale locale) {
		ActionErrors errors = new ActionErrors();

		// Comprobar que se ha seleccionado al menos un ámbito
		if (ArrayUtils.isEmptyOrBlank(idObjetoAmbito)) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(DepositoConstants.AMBITO, locale)));
		}

		// //Comprobar que se ha seleccionado al menos un ámbito
		// if(StringUtils.isBlank(this.idArchivo)){
		// errors.add(Constants.ERROR_REQUIRED,
		// new ActionError(Constants.ERROR_REQUIRED,
		// Messages.getString(ArchivoDetails.DEPOSITO_UI_ARCHIVO,locale)));
		// }

		// Comprobar Fechas

		// FECHA EXTREMA INICIAL (3)

		validateCamposFecha(Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO, locale,
				errors, fechaCompIni, fechaFormatoIni, fechaDIni, fechaMIni,
				fechaAIni, fechaSIni, fechaIniFormatoIni, fechaIniDIni,
				fechaIniMIni, fechaIniAIni, fechaIniSIni, fechaFinFormatoIni,
				fechaFinDIni, fechaFinMIni, fechaFinAIni, fechaFinSIni);

		// validateCamposFecha(fechaDIni, fechaMIni, fechaAIni,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO, locale, errors);
		//
		// validateCamposFecha(fechaIniDIni, fechaIniMIni, fechaIniAIni,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO, locale, errors);
		//
		// validateCamposFecha(fechaFinDIni, fechaFinMIni, fechaFinAIni,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO, locale, errors);

		// FECHA EXTREMA FINAL (4)
		validateCamposFecha(Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL, locale,
				errors, fechaCompFin, fechaFormatoFin, fechaDFin, fechaMFin,
				fechaAFin, fechaSFin, fechaIniFormatoFin, fechaIniDFin,
				fechaIniMFin, fechaIniAFin, fechaIniSFin, fechaFinFormatoFin,
				fechaFinDFin, fechaFinMFin, fechaFinAFin, fechaFinSFin);

		// validateCamposFecha(fechaDFin, fechaMFin, fechaAFin,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL, locale, errors);
		//
		// validateCamposFecha(fechaIniDFin, fechaIniMFin, fechaIniAFin,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL, locale, errors);
		//
		// validateCamposFecha(fechaFinDFin, fechaFinMFin, fechaFinAFin,
		// Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL, locale, errors);

		return errors;
	}

	public void reset(ActionMapping mappings, HttpServletRequest request) {
		super.reset(mappings, request);

		codOrdenAmbito = null;
		idAmbito = null;
		nombreAmbito = null;
		idObjetoAmbito = null;
		nombreObjetoAmbito = null;
		idAmbitoDeposito = null;
		codOrdenAmbitoDeposito = null;
		nombreAmbitoDeposito = null;

		idArchivo = null;
		nombreArchivo = null;

		productores = null;
		formato = null;

		/* Fecha inicial */
		fechaCompIni = null;
		fechaFormatoIni = null;
		fechaAIni = null;
		fechaMIni = null;
		fechaDIni = null;
		fechaSIni = null;
		fechaIniFormatoIni = null;
		fechaIniAIni = null;
		fechaIniMIni = null;
		fechaIniDIni = null;
		fechaIniSIni = null;
		fechaFinFormatoIni = null;
		fechaFinAIni = null;
		fechaFinMIni = null;
		fechaFinDIni = null;
		fechaFinSIni = null;

		/* Fecha final */
		fechaCompFin = null;
		fechaFormatoFin = null;
		fechaAFin = null;
		fechaMFin = null;
		fechaDFin = null;
		fechaSFin = null;
		fechaIniFormatoFin = null;
		fechaIniAFin = null;
		fechaIniMFin = null;
		fechaIniDFin = null;
		fechaIniSFin = null;
		fechaFinFormatoFin = null;
		fechaFinAFin = null;
		fechaFinMFin = null;
		fechaFinDFin = null;
		fechaFinSFin = null;

		idUbicacion = null;

	}

	public String[] getArrayProductores() {
		String productoresText = new String(this.productores);

		if (StringUtils.isNotBlank(this.productores)) {
			String[] productoresSeleccionados = productoresText
					.split(Constants.DELIMITER_PARAMETER_FORMBEAN);

			if (ArrayUtils.isNotEmpty(productoresSeleccionados)) {
				String[] idsProductores = new String[productoresSeleccionados.length];

				for (int i = 0; i < productoresSeleccionados.length; i++) {
					String productorSeleccionado = productoresSeleccionados[i];

					if (StringUtils.isNotBlank(productorSeleccionado))
						;
					String[] partes = productorSeleccionado
							.split(Constants.DELIMITER_IDS);

					if (ArrayUtils.isNotEmpty(partes) && partes.length == 2) {
						idsProductores[i] = partes[0];
					}
				}

				return idsProductores;
			}
		}

		return null;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setNombreObjetoAmbito(String[] nombreObjetoAmbito) {
		this.nombreObjetoAmbito = nombreObjetoAmbito;
	}

	public String[] getNombreObjetoAmbito() {
		return nombreObjetoAmbito;
	}

}
