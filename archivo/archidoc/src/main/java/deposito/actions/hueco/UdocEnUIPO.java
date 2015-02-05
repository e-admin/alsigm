package deposito.actions.hueco;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.util.StringUtils;

import deposito.vos.UDocEnUiDepositoVO;
import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.DescriptorVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.UnidadDocumentalVO;

/**
 * Datos de presentación de una unidad documental ubicada en el depósito físico
 */
public class UdocEnUIPO extends UDocEnUiDepositoVO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UdocEnUIPO.class);

	ServiceRepository services = null;
	GestionUnidadDocumentalBI udocBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionDescripcionBI descripcionBI = null;

	UnidadDocumentalVO udoc = null;
	String titulo = null;
	String nombreProductor = Constants.STRING_EMPTY;
	String fechaInicioFormateada = Constants.STRING_EMPTY;
	String fechaFinFormateada = Constants.STRING_EMPTY;
	String denominacionSerie = Constants.STRING_EMPTY;

	UdocEnUIPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public String getTitulo() {
		if (titulo == null)
			if (isValidada()) {
				UnidadDocumentalVO udocVO = getUnidadDocumental();
				if (udocVO != null)
					titulo = udocVO.getTitulo();
				else
					titulo = "";
			} else {
				transferencias.vos.UnidadDocumentalVO udocVO = getRelacionBI()
						.getUnidadDocumental(getIdudocre());
				if (udocVO != null)
					titulo = udocVO.getAsunto();
				else
					titulo = "";
			}
		return titulo;
	}

	public UnidadDocumentalVO getUnidadDocumental() {
		if (udoc == null)
			udoc = getUdocBI().getUnidadDocumental(getIdunidaddoc());
		return udoc;
	}

	private GestionUnidadDocumentalBI getUdocBI() {
		if (udocBI == null)
			udocBI = services.lookupGestionUnidadDocumentalBI();
		return udocBI;
	}

	private GestionRelacionesEntregaBI getRelacionBI() {
		if (relacionBI == null)
			relacionBI = services.lookupGestionRelacionesBI();
		return relacionBI;
	}

	private GestionDescripcionBI getDescripcionBI() {
		if (descripcionBI == null)
			descripcionBI = services.lookupGestionDescripcionBI();
		return descripcionBI;
	}

	private GestionCuadroClasificacionBI getCuadroBI() {
		if (cuadroBI == null)
			cuadroBI = services.lookupGestionCuadroClasificacionBI();
		return cuadroBI;
	}

	public String getCodigo() {
		UnidadDocumentalVO udocVO = getUnidadDocumental();
		if (udocVO != null)
			return udocVO.getCodigo();

		return Constants.STRING_EMPTY;
	}

	public String getNumExp() {
		UnidadDocumentalVO udocVO = getUnidadDocumental();
		if (udocVO != null)
			return udocVO.getNumExp();
		return Constants.STRING_EMPTY;
	}

	public String getNombreProductor() {
		if (StringUtils.isEmpty(nombreProductor)) {

			// LLamar y obtener la lista de productores
			String idCampoProductor = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getProductor();
			List ltProductores = getCuadroBI().getProductoresElementoCF(
					getIdunidaddoc(), idCampoProductor);

			try {
				Properties params = null;
				ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo();
				ConfiguracionFondos configuracionFondos = config
						.getConfiguracionFondos();
				GestorOrganismos gestorOrganismos = GestorOrganismosFactory
						.getConnectorById(
								configuracionFondos.getIdSistGestorOrg(),
								params);

				if ((ltProductores != null) && (!ltProductores.isEmpty())) {
					DescriptorVO descriptorVO = (DescriptorVO) ltProductores
							.get(0);
					List organismos = gestorOrganismos
							.recuperarOrganosAntecesores(
									descriptorVO.getIdDescrSistExt(), 0);
					if ((organismos != null) && (!organismos.isEmpty())) {
						for (int i = 1; i < organismos.size(); i++) {
							InfoOrgano organismo = (InfoOrgano) organismos
									.get(i);
							nombreProductor += organismo.getNombre()
									+ Constants.SLASH;
						}
						InfoOrgano organo = gestorOrganismos.recuperarOrgano(
								TipoAtributo.IDENTIFICADOR_ORGANO,
								descriptorVO.getIdDescrSistExt());
						if (organo != null) {
							nombreProductor += organo.getNombre();
						}
					} else {
						nombreProductor = descriptorVO.getNombre();
					}
				}
			} catch (GestorOrganismosException e) {
				logger.error(e);
			} catch (NotAvailableException e) {
				logger.error(e);
			}
		}
		return nombreProductor;
	}

	public String getFechaInicioFormateada() {
		if (StringUtils.isEmpty(fechaInicioFormateada)) {
			Date fechaInicial = getDescripcionBI().getFechaInicial(
					TipoFicha.FICHA_ELEMENTO_CF, getIdunidaddoc());
			if (fechaInicial != null) {
				fechaInicioFormateada = DateUtils.formatDate(fechaInicial);
			}
		}

		return fechaInicioFormateada;
	}

	public String getFechaFinFormateada() {
		if (StringUtils.isEmpty(fechaFinFormateada)) {
			Date fechaFinal = getDescripcionBI().getFechaFinal(
					TipoFicha.FICHA_ELEMENTO_CF, getIdunidaddoc());
			if (fechaFinal != null) {
				fechaFinFormateada = DateUtils.formatDate(fechaFinal);
			}
		}

		return fechaFinFormateada;
	}

	public String getDenominacionSerie() {
		if (StringUtils.isEmpty(denominacionSerie)) {
			ElementoCuadroClasificacionVO serie = getCuadroBI()
					.getElementoPadre(getIdunidaddoc());
			if (serie != null) {
				denominacionSerie = serie.getCodigo() + " - "
						+ serie.getTitulo();
			}
		}

		return denominacionSerie;
	}

}