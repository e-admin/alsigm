package transferencias.actions;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import transferencias.TipoTransferencia;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.ProductorUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.exceptions.ConfigException;
import common.util.DateUtils;
import common.util.StringUtils;
import common.view.POUtils;

import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.DescriptorVO;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Información de presentacion de las unidades documentales en la unidad de
 * instalación.
 * 
 * @see ParteUnidadDocumentalVO
 */
public class ParteUnidadDocumentalPO extends ParteUnidadDocumentalVO implements
		Transformer {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(ParteUnidadDocumentalPO.class);

	GestionRelacionesEntregaBI relacionBI;
	GestionUnidadDocumentalBI udocBI;
	GestionCuadroClasificacionBI cuadroBI;
	GestionDescripcionBI descripcionBI;

	ElementoCuadroClasificacionVO udocCF;
	transferencias.vos.UnidadDocumentalVO udocRE;
	String denominacionSerie = Constants.STRING_EMPTY;
	Integer tipoTransferencia = null;

	ParteUnidadDocumentalPO(GestionRelacionesEntregaBI relacionBI,
			GestionUnidadDocumentalBI udocBI,
			GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI, Integer tipoTransferencia) {
		this.relacionBI = relacionBI;
		this.udocBI = udocBI;
		this.cuadroBI = cuadroBI;
		this.descripcionBI = descripcionBI;
		this.tipoTransferencia = tipoTransferencia;
	}

	public Object transform(Object vo) {
		ParteUnidadDocumentalPO po = new ParteUnidadDocumentalPO(relacionBI,
				udocBI, cuadroBI, descripcionBI, tipoTransferencia);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static ParteUnidadDocumentalPO getInstance(
			ServiceRepository services, Integer tipoTransferencia) {
		return new ParteUnidadDocumentalPO(
				services.lookupGestionRelacionesBI(),
				services.lookupGestionUnidadDocumentalBI(),
				services.lookupGestionCuadroClasificacionBI(),
				services.lookupGestionDescripcionBI(), tipoTransferencia);
	}

	public int getOrden() {
		logger.debug("Obteniendo orden de la relación de entrega.");

		if (udocRE == null)
			udocRE = relacionBI.getUnidadDocumental(this.idUnidadDoc);

		if (udocRE != null)
			return udocRE.getOrden();

		return -1;
	}

	public String getFechaInicioFormateada() {
		logger.debug("Obteniendo orden de la relación de entrega.");

		Date fechaInicio = null;

		if (udocRE == null) {
			udocRE = relacionBI.getUnidadDocumental(this.idUnidadDoc);
		}

		if (udocRE == null) {
			// Si es una transferencia entre archivos coger la fecha de las de
			// descripción
			fechaInicio = descripcionBI.getFechaInicial(
					TipoFicha.FICHA_ELEMENTO_CF, this.idUnidadDoc);
		} else {
			fechaInicio = udocRE.getFechaInicio();
		}

		return DateUtils.formatDate(fechaInicio);
	}

	public String getFechaFinFormateada() {
		logger.debug("Obteniendo orden de la relación de entrega.");

		Date fechaFin = null;

		if (udocRE == null) {
			udocRE = relacionBI.getUnidadDocumental(this.idUnidadDoc);
		}

		if (udocRE == null) {
			// Si es una transferencia entre archivos coger la fecha de las de
			// descripción
			fechaFin = descripcionBI.getFechaFinal(TipoFicha.FICHA_ELEMENTO_CF,
					this.idUnidadDoc);
		} else {
			fechaFin = udocRE.getFechaFin();
		}

		return DateUtils.formatDate(fechaFin);
	}

	public String getCodigo() {

		if (udocCF == null) {
			if (tipoTransferencia != null
					&& TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador() == tipoTransferencia
							.intValue()) {
				udocCF = cuadroBI
						.getElementoCuadroClasificacion(this.idUnidadDoc);
			} else {
				udocCF = udocBI.getUdocXUdocEnTransferencia(this.idUnidadDoc);
			}
		}

		if (udocCF != null) {
			return udocCF.getCodigo();
		}

		return Constants.STRING_EMPTY;
	}

	public String getNombreProductor() {

		if (udocRE == null)
			udocRE = relacionBI.getUnidadDocumental(this.idUnidadDoc);

		String nombre = Constants.STRING_EMPTY;
		if ((udocRE != null) && (udocRE.getProductor() != null)) {
			ProductorUnidadDocumentalVO productor = udocRE.getProductor();
			if (productor != null) {
				DescriptorVO descriptorVO = descripcionBI
						.getDescriptor(productor.getId());
				if (descriptorVO != null) {
					try {
						Properties params = null;
						ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo();
						ConfiguracionFondos configuracionFondos = config
								.getConfiguracionFondos();
						GestorOrganismos gestorOrganismos = GestorOrganismosFactory
								.getConnectorById(configuracionFondos
										.getIdSistGestorOrg(), params);

						List organismos = gestorOrganismos
								.recuperarOrganosAntecesores(
										descriptorVO.getIdDescrSistExt(), 0);
						if ((organismos != null) && (!organismos.isEmpty())) {
							for (int i = 1; i < organismos.size(); i++) {
								InfoOrgano organismo = (InfoOrgano) organismos
										.get(i);
								nombre += organismo.getNombre()
										+ Constants.SLASH;
							}
							InfoOrgano organo = gestorOrganismos
									.recuperarOrgano(
											TipoAtributo.IDENTIFICADOR_ORGANO,
											descriptorVO.getIdDescrSistExt());
							if (organo != null) {
								nombre += organo.getNombre();
							}
						} else {
							nombre = descriptorVO.getNombre();
						}
					} catch (ConfigException e) {
						logger.error(e);
					} catch (GestorOrganismosException e) {
						logger.error(e);
					} catch (NotAvailableException e) {
						logger.error(e);
					}
				} else {
					nombre = productor.getNombre();
				}
			}
		} else {
			// Transferencia entre archivos
			CampoReferenciaVO productor = descripcionBI.getProductor(
					TipoFicha.FICHA_ELEMENTO_CF, this.idUnidadDoc);
			if (productor != null) {
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

					DescriptorVO descriptor = descripcionBI
							.getDescriptor(productor.getIdObjRef());
					if (descriptor != null) {
						if (descriptor.getIdDescrSistExt() == null) {
							nombre = descriptor.getNombre();
						} else {
							List organismos = gestorOrganismos
									.recuperarOrganosAntecesores(
											descriptor.getIdDescrSistExt(), 0);
							if ((organismos != null) && (!organismos.isEmpty())) {
								for (int i = 1; i < organismos.size(); i++) {
									InfoOrgano organismo = (InfoOrgano) organismos
											.get(i);
									nombre += organismo.getNombre()
											+ Constants.STRING_NEW_LINE;
								}
								InfoOrgano organo = gestorOrganismos
										.recuperarOrgano(
												TipoAtributo.CODIGO_ORGANO,
												descriptor.getIdDescrSistExt());
								if (organo != null) {
									nombre += organo.getNombre();
								}
							}
						}
					}
				} catch (GestorOrganismosException e) {
					logger.error(e);
				} catch (NotAvailableException e) {
					logger.error(e);
				}
			}
		}

		return nombre;
	}

	public String getDenominacionSerie() {
		if (StringUtils.isEmpty(denominacionSerie)) {
			RelacionEntregaVO relacionVO = null;
			ElementoCuadroClasificacionVO serie = null;

			if (this.idRelentrega != null) {
				relacionVO = relacionBI
						.getRelacionXIdRelacion(this.idRelentrega);
			}

			if (relacionVO == null) {
				// Obtener la serie del identificador de unidad documental
				serie = cuadroBI.getElementoPadre(this.idUnidadDoc);
			} else {
				serie = cuadroBI.getElementoCuadroClasificacion(relacionVO
						.getIdseriedestino());
			}

			if (serie != null) {
				denominacionSerie = serie.getCodigo() + " - "
						+ serie.getTitulo();
			}
		}

		return denominacionSerie;
	}

}
