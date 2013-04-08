package transferencias.vos;

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
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.view.POUtils;

import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.DescriptorVO;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UDocEnUIReeaCRPO extends UDocEnUIReeaCRVO implements Transformer {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(UDocEnUIReeaCRPO.class);

	private static final long serialVersionUID = 1L;

	private GestionRelacionesEACRBI relacionEABI = null;
	private GestionCuadroClasificacionBI cuadroBI;
	private GestionDescripcionBI descripcionBI;
	private GestionRelacionesEntregaBI relacionesBI;
	private boolean erronea = false;
	private ElementoCuadroClasificacionVO udocCF;
	private String nombreProductor;
	private String denominacionSerie;

	UDocEnUIReeaCRPO(GestionRelacionesEACRBI relacionEABI,
			GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI,
			GestionRelacionesEntregaBI relacionesBI) {
		this.relacionEABI = relacionEABI;
		this.cuadroBI = cuadroBI;
		this.descripcionBI = descripcionBI;
		this.relacionesBI = relacionesBI;
	}

	public Object transform(Object vo) {
		UDocEnUIReeaCRPO po = new UDocEnUIReeaCRPO(relacionEABI, cuadroBI,
				descripcionBI, relacionesBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static UDocEnUIReeaCRPO getInstance(ServiceRepository services) {
		return new UDocEnUIReeaCRPO(services.lookupGestionRelacionesEACRBI(),
				services.lookupGestionCuadroClasificacionBI(),
				services.lookupGestionDescripcionBI(),
				services.lookupGestionRelacionesBI());
	}

	public void setErronea(boolean erronea) {
		this.erronea = erronea;
	}

	public boolean isErronea() {
		return erronea;
	}

	public String getCodigo() {
		if (udocCF == null) {
			udocCF = cuadroBI.getElementoCuadroClasificacion(getIdUnidadDoc());
		}
		if (udocCF != null) {
			return udocCF.getCodigo();
		}
		return Constants.STRING_EMPTY;
	}

	public String getFechaInicioFormateada() {
		return getFechaIni();
	}

	public String getFechaFinFormateada() {
		return getFechaFin();
	}

	public String getNombreProductor() {

		if (StringUtils.isEmpty(nombreProductor)) {
			// Transferencia entre archivos
			CampoReferenciaVO productor = descripcionBI.getProductor(
					TipoFicha.FICHA_ELEMENTO_CF, this.getIdUnidadDoc());
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
							nombreProductor = descriptor.getNombre();
						} else {
							List organismos = gestorOrganismos
									.recuperarOrganosAntecesores(
											descriptor.getIdDescrSistExt(), 0);
							if ((organismos != null) && (!organismos.isEmpty())) {
								for (int i = 1; i < organismos.size(); i++) {
									InfoOrgano organismo = (InfoOrgano) organismos
											.get(i);
									nombreProductor += organismo.getNombre()
											+ Constants.STRING_NEW_LINE;
								}
								InfoOrgano organo = gestorOrganismos
										.recuperarOrgano(
												TipoAtributo.CODIGO_ORGANO,
												descriptor.getIdDescrSistExt());
								if (organo != null) {
									nombreProductor += organo.getNombre();
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

		return nombreProductor;
	}

	public String getDenominacionSerie() {
		if (StringUtils.isEmpty(denominacionSerie)) {
			RelacionEntregaVO relacionVO = null;
			ElementoCuadroClasificacionVO serie = null;

			if (getIdRelentrega() != null) {
				relacionVO = relacionesBI
						.getRelacionXIdRelacion(getIdRelentrega());
			}

			if (relacionVO == null) {
				// Obtener la serie del identificador de unidad documental
				serie = cuadroBI.getElementoPadre(getIdUnidadDoc());
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
