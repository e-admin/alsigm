package valoracion.model;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

import se.autenticacion.idoc.InvesDocConnector;
import se.usuarios.ServiceClient;
import valoracion.vos.ValoracionSerieVO;

import common.Constants;
import common.Globals;
import common.bi.GestionCuadroClasificacionBI;
import common.exceptions.ArchivoModelException;
import common.util.DateUtils;

import fondos.model.IdentificacionSerie;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;

/**
 * Entidad representativa de una serie documental que incorpora funcionalidad
 * necesaria para el proceso de identificación
 */
public class ValoracionSerie extends IdentificacionSerie {

	/*
	 * class InfoProcedimiento implements InfoBProcedimiento { String codigo =
	 * null; String nombre = null; String sistemaProductor = null;
	 * 
	 * public InfoProcedimiento(String codigo, String nombre, String
	 * sistemaProductor) { this.codigo = codigo; this.nombre = nombre;
	 * this.sistemaProductor = sistemaProductor; } public String getCodigo() {
	 * return codigo; } public String getCodSistProductor() { return
	 * sistemaProductor; } public String getId() { return codigo; } public
	 * String getNombre() { return nombre; } public String
	 * getNombreSistProductor() { return null; } }
	 * 
	 * transient GestionSeriesBI _serviceSeries = null; transient
	 * GestionFondosBI _serviceFondos = null; transient GestionControlUsuariosBI
	 * _serviceUsuarios = null; transient GestionUnidadDocumentalBI
	 * _serviceUdocs = null;
	 * 
	 * ServiceRepository services = null; ServiceClient serviceClient = null;
	 */
	/*
	 * boolean permitidoEditarProcedimientoAsociado = false; Boolean
	 * permitidoVincularProcedimiento = null; List soportes = null; UsuarioVO
	 * gestor = null;
	 */

	// SerieVO serie = null;
	// <Datos_Serie><Contexto><Ancestro></Ancestro></Contexto>
	// String idSerie = null;
	List ancestrosSerie = null;
	// <CodigoDenominacion></CodigoDenominacion>
	String codigoSerie = null;
	String identificacion = null;

	// String denominacionSerie = null;
	// </Datos_Serie>

	/*
	 * EntidadProductoraVO entidad = null; InfoProductorSerie
	 * productorEntidadProductora = null;
	 */

	// <Contenido_Serie><Identificacion_Serie>
	/*
	 * String definicion = null; String tramites = null; String normativa =
	 * null; String docsBasicosUnidadDocumental = null; InfoBProcedimiento
	 * procedimiento = null;
	 */
	// </Identificacion_Serie></Contenido_Serie>

	// <Descripcion_Serie>
	String numeroUnidadesDocumentales = null;
	String numeroUnidadesInstalacion = null;
	String volumen = null;
	Date fechaInicial = null;
	Date fechaFinal = null;
	// </Descripcion_Serie>

	// <Productores>
	List productoresvigentes = null;
	List productoreshistoricos = null;
	// </Productores>

	GestionCuadroClasificacionBI cuadroBI = null;

	ValoracionSerie(String infoSerie, SerieVO serie, ServiceClient serviceClient) {

		// Rellenamos los datos de Identificación de la serie
		super(serie, serviceClient);

		// Rellenamos los datos de Valoración de la serie
		this.parseIdentificacionValoracion(infoSerie);
	}

	/**
	 * Constructor con acceso de paquete, solo puede ser instanciado por el
	 * servicio de series
	 * 
	 * @param serie
	 *            Serie a partir del que se crea el objeto extendido
	 * @param serviceClient
	 *            Datos del cliente que solicita la creación del objeto
	 *            extendido
	 */
	ValoracionSerie(ValoracionSerieVO valoracion, SerieVO serie,
			ServiceClient serviceClient) {

		// Rellenamos los datos de Identificación de la serie
		super(serie, serviceClient);

		// Rellenamos la lista de ancestros de la serie
		this.cuadroBI = services.lookupGestionCuadroClasificacionBI();

		// Datos_Serie
		// this.ancestrosSerie = cuadroBI.getAncestors(serie.getId());
		List ancestrosSerieList = cuadroBI.getAncestors(serie.getId());
		if (ancestrosSerieList != null && ancestrosSerieList.size() > 0) {
			this.ancestrosSerie = new ArrayList();
			for (int i = 0; i < ancestrosSerieList.size(); i++) {
				ElementoCuadroClasificacionVO ancestro = (ElementoCuadroClasificacionVO) ancestrosSerieList
						.get(i);

				String codigoAncestro = ancestro.getCodigoTitulo();
				this.ancestrosSerie.add(codigoAncestro);
			}
		}

		this.codigoSerie = serie.getCodigo();

		// Identificación_Serie: se ha rellenado ya al llamar al constructor del
		// padre, le asignamos valor aquí a nuestra variable
		this.identificacion = super.contentToString();

		// Descripción_Serie
		this.numeroUnidadesDocumentales = new Integer(serie.getNumunidaddoc())
				.toString();
		this.numeroUnidadesInstalacion = new Integer(serie.getNumuinstalacion())
				.toString();
		this.volumen = new Double(serie.getVolumen()).toString();
		this.fechaInicial = serie.getFextremainicial();
		this.fechaFinal = serie.getFextremafinal();

		// Productores_Serie
		// Vigentes
		// this.productoresvigentes
		// =_serviceSeries.getProductoresVigentesBySerie(serie.getId());
		List productoresVigentesList = _serviceSeries
				.getProductoresVigentesBySerie(serie.getId());
		if (productoresVigentesList != null
				&& productoresVigentesList.size() > 0) {
			this.productoresvigentes = new ArrayList();
			for (int i = 0; i < productoresVigentesList.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) productoresVigentesList
						.get(i);

				List nodoProductor = new ArrayList();
				nodoProductor.add(productorSerieVO.getNombre());
				nodoProductor.add(new Integer(productorSerieVO
						.getTipoProductor()).toString());
				this.productoresvigentes.add(nodoProductor);
			}
		}

		// Históricos
		// this.productoreshistoricos
		// =_serviceSeries.getProductoresHistoricosBySerie(serie.getId());
		List productoresHistoricosList = _serviceSeries
				.getProductoresHistoricosBySerie(serie.getId());
		if (productoresHistoricosList != null
				&& productoresHistoricosList.size() > 0) {
			this.productoreshistoricos = new ArrayList();
			for (int i = 0; i < productoresHistoricosList.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) productoresHistoricosList
						.get(i);

				List nodoProductor = new ArrayList();
				nodoProductor.add(productorSerieVO.getNombre());
				nodoProductor.add(new Integer(productorSerieVO
						.getTipoProductor()).toString());
				this.productoreshistoricos.add(nodoProductor);
			}
		}

	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ret.append("<valoracion_serie version=\"01.00\">");

		// Datos propios de la serie
		ret.append("<datos_serie>");
		// / Lista de ancestros de la serie
		ret.append("<contexto>");
		if (ancestrosSerie != null && ancestrosSerie.size() > 0)
			for (int i = 0; i < ancestrosSerie.size(); i++) {
				ret.append("<ancestro>");
				/*
				 * ElementoCuadroClasificacionVO ancestro =
				 * (ElementoCuadroClasificacionVO)ancestrosSerie.get(i);
				 */

				String codigoAncestro = new String();
				codigoAncestro = (String) ancestrosSerie.get(i);
				// if(StringUtils.isNotEmpty(codigoAncestro)){
				// codigoAncestro =
				// ancestro.getCodigo()+" "+ancestro.getTitulo();
				ret.append(Constants.addCData(codigoAncestro));
				// }
				ret.append("</ancestro>");
			}
		ret.append("</contexto>");

		// / Código + denominación de la serie
		// ret.append("<codigo_titulo_serie>");
		// ret.append(Constants.addCData(codigoSerie + " " + denominacion));
		// ret.append("</codigo_titulo_serie>");

		// Código de la serie
		ret.append("<codigo>");
		ret.append(Constants.addCData(codigoSerie));
		ret.append("</codigo>");

		// Denominación/Título de la serie
		ret.append("<titulo>");
		ret.append(Constants.addCData(denominacion));
		ret.append("</titulo>");

		ret.append("</datos_serie>");

		// Detalles del contenido y descripción de la serie
		ret.append("<contenido_serie>");

		// / Identificación
		ret.append(identificacion);

		// / Descripción
		ret.append("<descripcion_serie>");
		// // Física
		ret.append("<descripcion_fisica>");
		ret.append("<n_unidades_documentales>");
		ret.append(numeroUnidadesDocumentales);
		ret.append("</n_unidades_documentales>");
		ret.append("<n_unidades_instalacion>");
		ret.append(numeroUnidadesInstalacion);
		ret.append("</n_unidades_instalacion>");
		ret.append("<volumen>");
		ret.append(volumen);
		ret.append("</volumen>");
		ret.append("</descripcion_fisica>");

		// // Fechas Extremas
		ret.append("<fechas_extremas>");
		ret.append("<fecha_inicial>");
		if (fechaInicial != null)
			ret.append(DateUtils.formatDate(fechaInicial));
		ret.append("</fecha_inicial>");
		ret.append("<fecha_final>");
		if (fechaFinal != null)
			ret.append(DateUtils.formatDate(fechaFinal));
		ret.append("</fecha_final>");
		ret.append("</fechas_extremas>");
		ret.append("</descripcion_serie>");

		// / Productores de la serie
		ret.append("<productores_serie>");
		ret.append("<productores_vigentes>");
		if (productoresvigentes != null && productoresvigentes.size() > 0)
			for (int i = 0; i < productoresvigentes.size(); i++) {
				List nodoProductor = (List) productoresvigentes.get(i);
				ret.append("<productor><nombre>");
				ret.append(Constants.addCData((String) nodoProductor.get(0)));
				ret.append("</nombre>");
				ret.append("<tipo>");
				ret.append((String) nodoProductor.get(1));
				ret.append("</tipo></productor>");

			}
		ret.append("</productores_vigentes>");

		/*
		 * if (productoresvigentes != null && productoresvigentes.size()>0) for
		 * (int i=0; i<productoresvigentes.size(); i++) { ProductorSerieVO
		 * productorVO = (ProductorSerieVO) productoresvigentes.get(i);
		 * ret.append("<productor><nombre>");
		 * ret.append(Constants.addCData(productorVO.getNombre()));
		 * ret.append("</nombre>"); ret.append("<tipo>");
		 * ret.append(productorVO.getTipoProductor());
		 * ret.append("</tipo></productor>");
		 * 
		 * } ret.append("</productores_vigentes>");
		 */
		ret.append("<productores_historicos>");
		if (productoreshistoricos != null && productoreshistoricos.size() > 0)
			for (int i = 0; i < productoreshistoricos.size(); i++) {
				List nodoProductor = (List) productoreshistoricos.get(i);
				ret.append("<productor><nombre>");
				ret.append(Constants.addCData((String) nodoProductor.get(0)));
				ret.append("</nombre>");
				ret.append("<tipo>");
				ret.append((String) nodoProductor.get(1));
				ret.append("</tipo></productor>");

			}

		/*
		 * if (productoreshistoricos != null && productoreshistoricos.size()>0)
		 * for (int i=0; i<productoreshistoricos.size(); i++) { ProductorSerieVO
		 * productorVO = (ProductorSerieVO) productoreshistoricos.get(i);
		 * ret.append("<productor><nombre>");
		 * ret.append(Constants.addCData(productorVO.getNombre()));
		 * ret.append("</nombre>"); ret.append("<tipo>");
		 * ret.append(productorVO.getTipoProductor());
		 * ret.append("</tipo></productor>");
		 * 
		 * }
		 */
		ret.append("</productores_historicos>");
		ret.append("</productores_serie>");
		ret.append("</contenido_serie>");
		ret.append("</valoracion_serie>");
		return ret.toString();
	}

	/**
	 * Extrae la información de la serie en el momento de cierre del dictamen
	 * del XML que se suministra
	 * 
	 * @param identificacion
	 *            XML con los datos de identificación de la serie
	 */
	private void parseIdentificacionValoracion(String identificacion) {
		if (identificacion != null) {
			try {
				// URL del fichero de reglas
				URL digesterRulesFile = InvesDocConnector.class
						.getClassLoader().getResource(
								Globals.RULES_IDENTIFICACION_SERIE_VALORADA);

				Digester digester = DigesterLoader
						.createDigester(digesterRulesFile);
				digester.push(this);
				digester.parse(new StringReader(identificacion.trim()));
			} catch (Exception e) {
				throw new ArchivoModelException(e,
						"parseIdentificacionValoracion",
						"Error extrayendo identificación de serie en momento de la valoración "
								+ this.serie);
			}
		}
	}

	public List getAncestrosSerie() {
		return ancestrosSerie;
	}

	public void setAncestrosSerie(List ancestrosSerie) {
		this.ancestrosSerie = ancestrosSerie;
	}

	public String getCodigoSerie() {
		return codigoSerie;
	}

	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNumeroUnidadesDocumentales() {
		return numeroUnidadesDocumentales;
	}

	public void setNumeroUnidadesDocumentales(String numeroUnidadesDocumentales) {
		this.numeroUnidadesDocumentales = numeroUnidadesDocumentales;
	}

	public String getNumeroUnidadesInstalacion() {
		return numeroUnidadesInstalacion;
	}

	public void setNumeroUnidadesInstalacion(String numeroUnidadesInstalacion) {
		this.numeroUnidadesInstalacion = numeroUnidadesInstalacion;
	}

	public List getProductoreshistoricos() {
		return productoreshistoricos;
	}

	public void setProductoreshistoricos(List productoreshistoricos) {
		this.productoreshistoricos = productoreshistoricos;
	}

	public List getProductoresvigentes() {
		return productoresvigentes;
	}

	public void setProductoresvigentes(List productoresvigentes) {
		this.productoresvigentes = productoresvigentes;
	}

	public String getVolumen() {
		return volumen;
	}

	/**
	 * Obtiene el Volumen en metros
	 */
	public String getVolumenEnMetros() {
		Float volumenFloat = new Float(volumen);
		float volumenMetros = volumenFloat.floatValue() / 100;
		volumenFloat = new Float(volumenMetros);
		return volumenFloat.toString();
	}

	public void setVolumen(String volumen) {
		this.volumen = volumen;
	}

	/**
	 * Permite añadir un ancestro a la lista de ancestros de la serie en el
	 * momento de cierre de la valoración
	 * 
	 * @param ancestro
	 *            Nombre del ancestro
	 */
	public void addAncestro(String ancestro) {

		if (this.ancestrosSerie == null)
			this.ancestrosSerie = new ArrayList();

		this.ancestrosSerie.add(ancestro);
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = DateUtils.getDate(fechaInicial);
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = DateUtils.getDate(fechaFinal);
	}

	/**
	 * Permite añadir un productor a la lista de productores vigentes de la
	 * serie en el momento de cierre de la valoración
	 * 
	 * @param nombre
	 *            Nombre del productor
	 * @param tipo
	 *            tipo de productor
	 */
	public void addProductorVigente(String nombre, String tipo) {

		if (this.productoresvigentes == null)
			this.productoresvigentes = new ArrayList();

		List nodoProductor = new ArrayList();
		nodoProductor.add(nombre);
		nodoProductor.add(tipo);

		this.productoresvigentes.add(nodoProductor);
	}

	/**
	 * Permite añadir un productor a la lista de productores históricos de la
	 * serie en el momento de cierre de la valoración
	 * 
	 * @param nombre
	 *            Nombre del productor
	 * @param tipo
	 *            tipo de productor
	 */
	public void addProductorHistorico(String nombre, String tipo) {

		if (this.productoreshistoricos == null)
			this.productoreshistoricos = new ArrayList();

		List nodoProductor = new ArrayList();
		nodoProductor.add(nombre);
		nodoProductor.add(tipo);

		this.productoreshistoricos.add(nodoProductor);
	}

	public static List productorSerieVOToListaNombreTipo(List listaProductores) {
		List ret = null;

		if (listaProductores != null && listaProductores.size() > 0) {
			ret = new ArrayList();
			for (int i = 0; i < listaProductores.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductores
						.get(i);

				List nodoProductor = new ArrayList();
				nodoProductor.add(productorSerieVO.getNombre());
				nodoProductor.add(new Integer(productorSerieVO
						.getTipoProductor()).toString());
				ret.add(nodoProductor);
			}
		}

		return ret;
	}
}