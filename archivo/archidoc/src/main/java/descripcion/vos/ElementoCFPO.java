package descripcion.vos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.CampoDescriptivoConfigBusqueda;
import xml.config.ConfiguracionBusquedas;
import xml.config.ConfiguracionBusquedasFactory;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.RestriccionCampoBusqueda;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceRepository;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.db.IReferenciaDBEntity;
import docelectronicos.vos.DocDocumentoVO;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * 
 * Clase para mostrar en la vista elementos del cuadro de clasificación
 * 
 */
public class ElementoCFPO extends ElementoCFVO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ElementoCFPO.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private GestionCuadroClasificacionBI cclfBI = null;
	protected GestionDescripcionBI descBI = null;
	protected GestionDocumentosElectronicosBI documentosBI = null;
	protected IReferenciaDBEntity referenciaDBEntity = null;

	private Busqueda busqueda = null;

	private String nombreRangos = null;
	private boolean yaConsultado = false;

	private String codReferenciaAbreviado = null;

	private Integer numDocumentosElectronicos;

	private List documentosElectronicos;

	private String nombreProductor = null;

	private String nombreCortoProductor = null;

	private Map mapProductores = null;

	private Map mapInteresados = null;

	private List interesados;

	private ProductorElementoCF productorElemento = null;

	private ElementoCuadroClasificacionVO elemento = null;

	public ElementoCFPO(ServiceRepository services, Busqueda busqueda,
			Map mapProductores, Map mapInteresados) {
		this.cclfBI = services.lookupGestionCuadroClasificacionBI();
		this.descBI = services.lookupGestionDescripcionBI();
		this.documentosBI = services.lookupGestionDocumentosElectronicosBI();
		this.busqueda = busqueda;
		this.mapProductores = mapProductores;
		this.mapInteresados = mapInteresados;
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

					String idCampoRangoInicial = getIdCampoRangoInicial();
					String idCampoRangoFinal = getIdCampoRangoFinal();

					if ((idCampoRangoInicial != null)
							&& (idCampoRangoFinal != null)) {
						// LLamar y obtener la lista de rangos
						List ltRangos = cclfBI.getRangosElementoCF(getId(),
								idCampoRangoInicial, idCampoRangoFinal);

						if ((ltRangos != null) && (!ltRangos.isEmpty())) {
							Iterator it = ltRangos.iterator();
							while (it.hasNext()) {
								RangoVO rango = (RangoVO) it.next();
								// ret += rango.getRangoInicial() + "-" +
								// rango.getRangoFinal();
								ret += rango.getRangoInicial()
										+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
										+ rango.getRangoFinal();
								if (it.hasNext())
									// ret += ";<br/>";
									ret += Constants.DELIMITADOR_RANGOS;
							}
						}
					}
					nombreRangos = ret;
				}
			}

			yaConsultado = true;

			return ret;
		}
	}

	/**
	 * Permite obtener del fichero de configuración de búsquedas el id del campo
	 * rango inicial
	 * 
	 * @return Id del campo rango inicial
	 */
	private String getIdCampoRangoInicial() {

		String idCampoRangoInicial = null;
		ConfiguracionBusquedas cbs = (ConfiguracionBusquedas) ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		CampoBusqueda cb = (CampoBusqueda) busqueda.getMapSalida().get(
				CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS);

		if ((cbs != null) && (cb != null)) {
			String idCampoInicialKey = cb.getNombre() + "Inicial";
			CampoDescriptivoConfigBusqueda campoDCBInicial = cbs
					.getCampoDescriptivo(idCampoInicialKey);
			idCampoRangoInicial = (campoDCBInicial == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCBInicial.getValor());
		}

		// Si no encontramos el idCampo en el fichero de búsquedas, lo cojemos
		// del archivo-cfg.xml
		if (idCampoRangoInicial == null)
			idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getRangoInicial();

		return idCampoRangoInicial;
	}

	/**
	 * Permite obtener del fichero de configuración de búsquedas el id del campo
	 * rango final
	 * 
	 * @return Id del campo rango final
	 */
	private String getIdCampoRangoFinal() {

		String idCampoRangoFinal = null;
		ConfiguracionBusquedas cbs = (ConfiguracionBusquedas) ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		CampoBusqueda cb = (CampoBusqueda) busqueda.getMapSalida().get(
				CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS);

		if ((cbs != null) && (cb != null)) {
			String idCampoFinalKey = cb.getNombre() + "Final";
			CampoDescriptivoConfigBusqueda campoDCBFinal = cbs
					.getCampoDescriptivo(idCampoFinalKey);
			idCampoRangoFinal = (campoDCBFinal == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDCBFinal.getValor());
		}

		// Si no encontramos el idCampo en el fichero de búsquedas, lo cojemos
		// del archivo-cfg.xml
		if (idCampoRangoFinal == null)
			idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getRangoFinal();

		return idCampoRangoFinal;
	}

	/**
	 * Permite obtener del fichero de configuración de búsquedas un id del campo
	 * denominado como la restricción del campo de búsqueda al que está asociado
	 * 
	 * @return Id del campo
	 */
	protected String getIdCampoPorNombre(String nombre) {

		String idCampo = null;
		ConfiguracionBusquedas cbs = (ConfiguracionBusquedas) ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		CampoBusqueda cb = (CampoBusqueda) busqueda.getMapSalida().get(nombre);

		if ((cbs != null) && (cb != null)) {
			String idCampoFinalKey = cb.getNombre();
			CampoDescriptivoConfigBusqueda campoDescriptivo = cbs
					.getCampoDescriptivo(idCampoFinalKey);
			idCampo = (campoDescriptivo == null ? RestriccionCampoBusqueda.RESTRICCION_IDCAMPO_TODOS_VALORES
					: campoDescriptivo.getValor());
		}

		return idCampo;
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
		String codigoReferencia = StringUtils.EMPTY;
		CampoBusqueda cb = (CampoBusqueda) busqueda.getMapSalida().get(
				CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA);

		if (cb != null) {
			if (Constants.TRUE_STRING.equalsIgnoreCase(cb.getAbreviado())) {
				String numCaracteres = cb.getNumCaracteres();

				if (StringUtils.isNotEmpty(numCaracteres)) {
					try {
						int numChars = new Integer(numCaracteres).intValue();
						codigoReferencia = StringUtils.truncarCadena(
								getCodReferencia(), numChars,
								Constants.CARACTERES_TRUNCADO);
					} catch (NumberFormatException e) {
						return codigoReferencia;
					}
				}
			}
		}

		return codigoReferencia;
	}

	public String getCodReferencia() {
		String codigoReferencia = super.getCodReferencia();

		if (StringUtils.isEmpty(codigoReferencia)) {
			if (elemento == null) {
				elemento = cclfBI.getElementoCuadroClasificacion(super.getId());
			}

			if (elemento != null) {
				codigoReferencia = elemento.getCodReferencia();
			}
		}

		return codigoReferencia;
	}

	public int getMarcasBloqueoUnidad() {
		int marcaBloqueo = 0;
		if (ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL == getTipo()) {
			marcaBloqueo = cclfBI.getMarcasBloqueo(getId());
		}
		return marcaBloqueo;
	}

	public void setNumDocumentosElectronicos(Integer numDocumentosElectronicos) {
		this.numDocumentosElectronicos = numDocumentosElectronicos;
	}

	public Integer getNumDocumentosElectronicos() {
		if (numDocumentosElectronicos == null) {
			numDocumentosElectronicos = new Integer(getDocumentosElectronicos()
					.size());
		}

		return numDocumentosElectronicos;
	}

	public void setDocumentosElectronicos(List documentosElectronicos) {
		this.documentosElectronicos = documentosElectronicos;
	}

	public List getDocumentosElectronicos() {
		if (documentosElectronicos == null) {
			try {
				documentosElectronicos = documentosBI
						.getDocumentosElementoCuadro(this.getId());
			} catch (Exception e) {
				logger.error("Error al obtener los documentos", e);
			}

			if (documentosElectronicos == null) {
				documentosElectronicos = new ArrayList();
			}
		}

		return documentosElectronicos;
	}

	public String getDefaultDocument() {
		if (getNumDocumentosElectronicos().intValue() == 1) {
			DocDocumentoVO documento = (DocDocumentoVO) getDocumentosElectronicos()
					.get(0);
			if (documento != null) {
				return documento.getId();
			}
		}
		return null;
	}

	public void setInteresados(List interesados) {
		this.interesados = interesados;
	}

	public List getInteresados() {
		if (interesados == null) {
			if (mapInteresados != null) {
				interesados = (List) mapInteresados.get(getId());
			}
			if (interesados == null) {
				interesados = new ArrayList();
			}
		}
		return interesados;
	}

	public boolean isConInteresados() {
		if (ListUtils.isNotEmpty(getInteresados())) {
			return true;
		}

		return false;

	}

	public ProductorElementoCF getProductorElemento() {
		if (productorElemento == null) {
			if (mapProductores != null) {
				productorElemento = (ProductorElementoCF) mapProductores
						.get(getId());
			}

			if (productorElemento == null) {
				productorElemento = new ProductorElementoCF();
			}
		}
		return productorElemento;
	}

	// public String getNombreProductor() {
	// if (StringUtils.isEmpty(nombreProductor)) {
	//
	// // LLamar y obtener la lista de productores
	// String idCampoProductor =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionDescripcion().getProductor();
	// List ltProductores =
	// cclfBI.getProductoresElementoCF(getId(),idCampoProductor);
	//
	// try {
	// Properties params = null;
	// ConfiguracionSistemaArchivo config =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
	// ConfiguracionFondos configuracionFondos =
	// config.getConfiguracionFondos();
	// GestorOrganismos gestorOrganismos =
	// GestorOrganismosFactory.getConnectorById(configuracionFondos.getIdSistGestorOrg(),
	// params);
	//
	// if ((ltProductores != null) && (!ltProductores.isEmpty())) {
	// DescriptorVO descriptorVO = (DescriptorVO)ltProductores.get(0);
	// List organismos =
	// gestorOrganismos.recuperarOrganosAntecesores(descriptorVO.getIdDescrSistExt(),
	// 0);
	// if ((organismos != null) && (!organismos.isEmpty())) {
	// for(int i=0; i<organismos.size(); i++){
	// InfoOrgano organismo = (InfoOrgano) organismos.get(i);
	// nombreProductor += organismo.getNombre() + Constants.SLASH;
	// }
	// InfoOrgano organo =
	// gestorOrganismos.recuperarOrgano(TipoAtributo.IDENTIFICADOR_ORGANO,
	// descriptorVO.getIdDescrSistExt());
	// if(organo != null){
	// nombreProductor += organo.getNombre();
	// }
	// } else {
	// nombreProductor = descriptorVO.getNombre();
	// }
	// }
	// } catch (GestorOrganismosException e) {
	// logger.error(e);
	// } catch (NotAvailableException e) {
	// logger.error(e);
	// }
	// }
	// return nombreProductor;
	// }

	public String getNombreProductor() {
		if (nombreProductor == null) {
			nombreProductor = getProductorElemento().getNombre();

			if (nombreProductor == null) {
				nombreProductor = Constants.STRING_EMPTY;
			}
		}
		return nombreProductor;
	}

	public String getNombreCortoProductor() {
		if (nombreCortoProductor == null) {
			nombreCortoProductor = getNombreProductor();

			if (StringUtils.isNotEmpty(nombreCortoProductor)) {
				String[] partes = nombreCortoProductor.split(Constants.SLASH);

				if (partes != null) {
					nombreCortoProductor = partes[0];
				} else {
					nombreCortoProductor = getNombreProductor();
				}
			}
		}
		return nombreCortoProductor;
	}

	public boolean isProductorDockable() {
		if (StringUtils.isNotEmpty(getNombreProductor())
				&& !getNombreProductor().equals(getNombreCortoProductor())) {
			return true;
		}
		return false;
	}

}