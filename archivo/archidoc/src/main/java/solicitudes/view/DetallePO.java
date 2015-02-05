package solicitudes.view;

import fondos.model.ElementoCuadroClasificacion;
import gcontrol.vos.ArchivoVO;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.vos.ConsultaPO;
import solicitudes.consultas.vos.ConsultaToPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.prestamos.vos.PrestamoPO;
import solicitudes.prestamos.vos.PrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.DetalleVO;
import transferencias.vos.UnidadDocumentalVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.bi.GestionConsultasBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

import descripcion.model.TipoCampo;
import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.RangoVO;

public class DetallePO extends DetalleVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// GestionSistemaBI sistemaBI = null;
	// GestionPrestamosBI prestamosBI = null;
	// GestionConsultasBI consultasBI = null;
	private GestionRelacionesEntregaBI relacionesBI = null;
	private GestionCuadroClasificacionBI cclfBI = null;
	private GestionDescripcionBI descripcionBI = null;
	private String nombreRangos = null;
	private boolean yaConsultado = false;
	private String denominacionExpediente = null;

	ArchivoVO archivo = null;

	private ServiceRepository services = null;
	private Locale locale = null;
	private GestionPrestamosBI prestamosBI = null;
	private GestionConsultasBI consultasBI = null;
	// private Busqueda busqueda = null;
	private String codReferenciaAbreviado = null;

	// DetallePO(GestionSistemaBI sistemaBI, GestionPrestamosBI prestamosBI,
	// GestionConsultasBI consultasBI, GestionRelacionesEntregaBI relacionesBI)
	DetallePO(Locale locale, ServiceRepository services) {
		// this.sistemaBI=sistemaBI;
		this.locale = locale;
		this.services = services;
		this.relacionesBI = services.lookupGestionRelacionesBI();
		this.cclfBI = services.lookupGestionCuadroClasificacionBI();
		this.descripcionBI = services.lookupGestionDescripcionBI();
		this.prestamosBI = services.lookupGestionPrestamosBI();
		this.consultasBI = services.lookupGestionConsultasBI();
	}

	public DetallePO(DetalleVO detalleVO, Locale locale,
			ServiceRepository services) {
		super();
		POUtils.copyVOProperties(this, detalleVO);
		this.services = services;
		this.locale = locale;
		this.relacionesBI = services.lookupGestionRelacionesBI();
		this.cclfBI = services.lookupGestionCuadroClasificacionBI();
		this.descripcionBI = services.lookupGestionDescripcionBI();
		this.prestamosBI = services.lookupGestionPrestamosBI();
		this.consultasBI = services.lookupGestionConsultasBI();
	}

	public UnidadDocumentalVO getUnidadDocumental() {
		return relacionesBI.getUnidadDocumental(this.idudoc);
	}

	/**
	 * Permite obtener los rangos
	 * 
	 * @return Cadena con los rangos
	 */
	public String getNombreRangos() {
		String ret = Constants.STRING_EMPTY;

		if (yaConsultado) {
			return nombreRangos;
		} else {
			// Obtener el nombre de los rangos sólo para fracciones de serie
			if (getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				if (nombreRangos == null) {
					// LLamar y obtener la lista de rangos
					if (this.rangos == null || this.rangos.size() == 0) {
						String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getRangoInicial();
						String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion().getRangoFinal();

						if ((idCampoRangoInicial != null)
								&& (idCampoRangoFinal != null))
							this.rangos = cclfBI.getRangosElementoCF(
									getIdudoc(), idCampoRangoInicial,
									idCampoRangoFinal);
					}

					if ((this.rangos != null) && (!this.rangos.isEmpty())) {
						Iterator it = this.rangos.iterator();
						while (it.hasNext()) {
							RangoVO rango = (RangoVO) it.next();
							ret += rango.getRangoInicial()
									+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
									+ rango.getRangoFinal();
							if (it.hasNext())
								ret += Constants.DELIMITADOR_RANGOS;
						}
					}

					nombreRangos = ret;
				}
			}

			yaConsultado = true;
		}
		return ret;
	}

	public GestionCuadroClasificacionBI getCclfBI() {
		return cclfBI;
	}

	public void setCclfBI(GestionCuadroClasificacionBI cclfBI) {
		this.cclfBI = cclfBI;
	}

	/**
	 * Permite obtener el campo descriptivo denominación de número de expediente
	 * 
	 * @return Cadena con la denominación del número de expediente
	 */
	public String getDenominacionExpediente() {
		String ret = Constants.STRING_EMPTY;

		if (StringUtils.isEmpty(this.denominacionExpediente)) {
			String idCampoDenominacionExpediente = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getDenominacionExpediente();
			if ((idCampoDenominacionExpediente != null)) {
				List campos = descripcionBI.getValues(
						TipoFicha.FICHA_ELEMENTO_CF,
						TipoCampo.TEXTO_CORTO_VALUE, this.idElementoCF,
						idCampoDenominacionExpediente);

				if (campos != null && campos.size() > 0) {
					CampoTextoVO campoTexto = (CampoTextoVO) campos.get(0);
					ret = campoTexto.getValor();
				}

			}
		}

		this.denominacionExpediente = ret;
		return ret;
	}

	public String getCodReferenciaAbreviado() {
		if (codReferenciaAbreviado == null) {
			codReferenciaAbreviado = getCodigoReferenciaAbreviado();
		}
		return codReferenciaAbreviado;
	}

	public void setCodReferenciaAbreviado(String codReferenciaAbreviado) {
		this.codReferenciaAbreviado = codReferenciaAbreviado;
	}

	private String getCodigoReferenciaAbreviado() {
		String codigoReferencia = getCodReferencia();
		String[] codRef = null;
		try {
			if (codigoReferencia != null) {
				codRef = codigoReferencia.split("/");
				codigoReferencia = Constants.CARACTERES_TRUNCADO
						+ codRef[codRef.length - 1];
			}
		} catch (NumberFormatException e) {
			return codigoReferencia;
		}
		return codigoReferencia;
	}

	public String getCodigoTransferencia() {
		if (getTiposolicitud() > 0
				&& getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO_INT) {
			PrestamoVO prestamo = prestamosBI.getPrestamo(getIdsolicitud());
			PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
					locale, services).transform(prestamo);
			return CodigoTransferenciaUtils.getCodigoTransferencia(prestamoPO);
		} else if (getTiposolicitud() > 0
				&& getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT) {
			ConsultaVO consulta = consultasBI.getConsulta(getIdsolicitud());
			ConsultaPO consultaPO = (ConsultaPO) ConsultaToPO.getInstance(
					locale, services).transform(consulta);
			return CodigoTransferenciaUtils.getCodigoTransferencia(consultaPO);
		}
		return null;
	}

	/*
	 * public String getSolicitante(){ if(getTiposolicitud() > 0 &&
	 * getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO_INT) {
	 * PrestamoVO prestamo = prestamosBI.getPrestamo(getIdsolicitud()); return
	 * prestamo.getNusrsolicitante(); }else if(getTiposolicitud() > 0 &&
	 * getTiposolicitud() == SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT) {
	 * ConsultaVO consulta = consultasBI.getConsulta(getIdsolicitud()); return
	 * consulta.getNusrconsultor(); } return null; }
	 */
}