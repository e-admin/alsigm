package fondos.model;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.IOrganoProductor;
import se.procedimientos.IProcedimiento;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import se.util.MapUtil;
import util.CollectionUtils;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorCatalogo;

import common.ConfigConstants;
import common.Constants;
import common.Globals;
import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.exceptions.ArchivoModelException;
import common.util.ListUtils;
import common.vos.UniqueGuid;

import fondos.actions.GestionIdentificacionAction;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.IInfoProductorSerie;
import fondos.vos.InfoOrganoProductorSerie;
import fondos.vos.InfoProductorSerie;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import gcontrol.model.NombreOrganoFormat;
import gcontrol.vos.UsuarioVO;

/**
 * Entidad representativa de una serie documental que incorpora funcionalidad
 * necesaria para el proceso de identificación
 */
public class IdentificacionSerie {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GestionIdentificacionAction.class);

    class InfoExtendida {
        String tipoDocumentacion = "";
        String volumenPrevisionAnual = "";
        String soportePrevisionAnual = "";

        public InfoExtendida(String tipoDocumentacion,
                String volumenPrevisionAnual, String soportePrevisionAnual) {
            this.tipoDocumentacion = tipoDocumentacion;
            this.volumenPrevisionAnual = volumenPrevisionAnual;
            this.soportePrevisionAnual = soportePrevisionAnual;
        }

        public String getSoportePrevisionAnual() {
            return soportePrevisionAnual;
        }

        public void setSoportePrevisionAnual(String soportePrevisionAnual) {
            this.soportePrevisionAnual = soportePrevisionAnual;
        }

        public String getTipoDocumentacion() {
            return tipoDocumentacion;
        }

        public void setTipoDocumentacion(String tipoDocumentacion) {
            this.tipoDocumentacion = tipoDocumentacion;
        }

        public String getVolumenPrevisionAnual() {
            return volumenPrevisionAnual;
        }

        public void setVolumenPrevisionAnual(String volumenPrevisionAnual) {
            this.volumenPrevisionAnual = volumenPrevisionAnual;
        }
    }

    class InfoProcedimiento implements InfoBProcedimiento {
        /**
         *
         */
        private static final long serialVersionUID = -5524323941815665754L;
        String codigo = null;
        String nombre = null;
        String sistemaProductor = null;

        public InfoProcedimiento(String codigo, String nombre,
                String sistemaProductor) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.sistemaProductor = sistemaProductor;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getCodSistProductor() {
            return sistemaProductor;
        }

        public String getId() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getNombreSistProductor() {
            return null;
        }
    }

    protected transient GestionSeriesBI _serviceSeries = null;
    protected transient GestionFondosBI _serviceFondos = null;
    protected transient GestionControlUsuariosBI _serviceUsuarios = null;
    protected transient GestionUnidadDocumentalBI _serviceUdocs = null;

    protected ServiceRepository services = null;
    protected ServiceClient serviceClient = null;

    protected boolean permitidoEditarProcedimientoAsociado = false;
    protected Boolean permitidoVincularProcedimiento = null;

    protected SerieVO serie = null;
    protected UsuarioVO gestor = null;
    protected EntidadProductoraVO entidad = null;
    protected InfoProductorSerie productorEntidadProductora = null;
    protected List soportes = null;

    protected String denominacion = null;
    protected String definicion = null;
    protected String tramites = null;
    protected String normativa = null;
    protected String docsBasicosUnidadDocumental = null;

    // protected List productoresserie = null;

    /**
     * Contiene todos los productores vigentes e históricos de la serie.
     */
    protected Map infoProductoresSerie = new LinkedHashMap();

    /**
     * @param infoProductoresSerie
     *            el infoProductoresSerie a fijar
     */
    public void setInfoProductoresSerie(Map infoProductoresSerie) {
        this.infoProductoresSerie = infoProductoresSerie;
    }

    /**
     * @return el infoProductoresSerie
     */
    public Map getInfoProductoresSerie() {
        return this.infoProductoresSerie;
    }

    public List getListaInfoProductoresSerie() {
        return MapUtil.toList(infoProductoresSerie);
    }

    /**
     * Obtiene la Lista de Productores Serie a Eliminar
     *
     * @return Lista de {@link InfoProductorSerie}
     */
    public List getInfoProductoresSerieAEliminar() {
        List infoProductoresEliminados = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            InfoProductorSerie infoProductorSerie = (InfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie != null && infoProductorSerie.isEliminado()) {
                infoProductoresEliminados.add(infoProductorSerie);
            }
        }

        return infoProductoresEliminados;
    }

    public List getInfoProductoresNoEliminados() {
        List infoProductoresNoEliminados = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            InfoProductorSerie infoProductorSerie = (InfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie != null && !infoProductorSerie.isEliminado()) {
                infoProductoresNoEliminados.add(infoProductorSerie);
            }
        }

        return infoProductoresNoEliminados;
    }

    /**
     * Obtiene la Lista de Productores Serie a Eliminar
     *
     * @return Lista de {@link InfoProductorSerie}
     */
    public List getInfoProductoresSerieACrear() {
        List infoProductoresACrear = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie != null && infoProductorSerie.isNuevo()) {
                infoProductoresACrear.add(infoProductorSerie);
            }
        }

        return infoProductoresACrear;
    }

    public List getInfoProductoresSerieAModificar() {
        List infoProductoresAModificar = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie != null && infoProductorSerie.isModificado()) {
                infoProductoresAModificar.add(infoProductorSerie);
            }
        }

        return infoProductoresAModificar;
    }

    public List getInfoProductoresSustituidos() {
        List infoProductoresSustituidos = new ArrayList();

        List infoProductoresHistoricos = getInfoProductoresHistoricos();

        for (Iterator iterator = infoProductoresHistoricos.iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie != null
            // && infoProductorSerie.isHistorico()
            // && infoProductorSerie.isSustituidoPorVigente()
            ) {
                infoProductoresSustituidos.add(infoProductorSerie);
            }
        }

        return infoProductoresSustituidos;
    }

    protected String tipoDocumentacion;
    protected String volumenPrevisionAnual;
    protected String soportePrevisionAnual;

    protected InfoExtendida infoExtendida = null;
    // List registros = null;

    protected InfoBProcedimiento procedimiento = null;

    public String getIdSerie() {
        if (serie != null) {
            return serie.getId();
        } else
            return null;
    }

    public IdentificacionSerie(){
    	super();
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
    public IdentificacionSerie(SerieVO serie, ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
        this.services = ServiceRepository.getInstance(serviceClient);
        _serviceSeries = services.lookupGestionSeriesBI();
        _serviceFondos = services.lookupGestionFondosBI();
        _serviceUsuarios = services.lookupGestionControlUsuariosBI();
        _serviceUdocs = services.lookupGestionUnidadDocumentalBI();
        this.serie = serie;
        this.parseIdentificacion(serie.getIdentificacion());
        List productores = _serviceSeries.getProductoresVigentesBySerie(serie
                .getId());

        if (productores != null && productores.size() > 0) {
            // this.productoresserie = new ArrayList();
            // ProductorSerieVO unProductor = null;
            // InfoProductorSerie infoProductor = null;
            for (Iterator i = productores.iterator(); i.hasNext();) {
                ProductorSerieVO productor = (ProductorSerieVO) i.next();

                if (productor != null
                        && StringUtils.isEmpty(productor.getGuid())) {
                    productor.setGuid(getGuid());
                }
                addProductorSerie(productor);
            }
        }

        List allProductores = _serviceSeries.getProductoresSerie(serie.getId());

        if (allProductores != null && allProductores.size() > 0) {
            this.infoProductoresSerie = new LinkedHashMap();
            for (Iterator i = allProductores.iterator(); i.hasNext();) {
                ProductorSerieVO productor = (ProductorSerieVO) i.next();

                if (productor != null
                        && StringUtils.isEmpty(productor.getGuid())) {
                    productor.setGuid(getGuid());
                }
                addInfoProductorSerie(productor);
            }
        }

        this.denominacion = serie.getTitulo();
        this.permitidoEditarProcedimientoAsociado = (serie.getEstadoserie() == EstadoSerie.NO_VIGENTE);

        EntidadProductoraVO entidadProductora = getEntidadProductoraFondo();
        if (entidadProductora.getIdSistExt() != null)
            productorEntidadProductora = new InfoOrganoProductorSerie(
                    ProductorSerieVO.TIPO_ENTIDAD,
                    entidadProductora.getIdSistExt(),
                    entidadProductora.getIdProductorEnSistGestor(),
                    entidadProductora.getNombre(),
                    entidadProductora.getCodigo());
        else
            productorEntidadProductora = new InfoProductorSerie(
                    ProductorSerieVO.TIPO_ENTIDAD,
                    entidadProductora.getNombre());
        productorEntidadProductora.setIdDescriptor(entidadProductora.getId());
    }

    public void addProductorSerie(ProductorSerieVO productorSerie) {

        InfoProductorSerie infoProductor = getInfoProductorSerie(productorSerie);

        // if(ListUtils.isEmpty(productoresserie)){
        // productoresserie=new ArrayList();
        // }

        addInfoProductorSerie(infoProductor);
    }

    public void addInfoProductorSerie(ProductorSerieVO productorSerie) {
        InfoProductorSerie infoProductorSerie = getInfoProductorSerie(productorSerie);
        addInfoProductorSerie(infoProductorSerie);
    }

    public void addInfoProductorSerie(IInfoProductorSerie infoProductorSerie) {
        infoProductoresSerie.put(infoProductorSerie.getGuid(),
                infoProductorSerie);
    }

    public InfoProductorSerie getInfoProductorSerie(String guid) {
        return (InfoProductorSerie) infoProductoresSerie.get(guid);
    }

    public ProductorSerieVO getProductorSerie(String guid) {
        IInfoProductorSerie infoProductorSerie = getInfoProductorSerie(guid);

        if (infoProductorSerie != null) {
            return infoProductorSerie.getProductorSerieVO();
        }
        return null;
    }

    public InfoProductorSerie getInfoProductorSerie(
            ProductorSerieVO productorSerie) {
        InfoProductorSerie infoProductor = new InfoProductorSerie(
                productorSerie.getTipoProductor(), productorSerie.getNombre());

        infoProductor.setProductorSerieVO(productorSerie);
        infoProductor.setIdDescriptor(productorSerie.getId());
        if (productorSerie.getIdprocedimiento() != null)
            infoProductor.setPuedeSerEliminado(false);
        else if (_serviceUdocs.getNumUdocByProductor(productorSerie.getId()) > 0)
            infoProductor.setPuedeSerEliminado(false);
        infoProductor.setFechaInicio(productorSerie.getFechaInicial());
        infoProductor.setFechaFin(productorSerie.getFechaFinal());
        infoProductor.setGuid(productorSerie.getGuid());

        infoProductor.setMarcas(productorSerie.getMarcas());

        return infoProductor;
    }

    public IInfoProductorSerie createInfoProductorSerie(
            ProductorSerieVO productorSerie) {
        IInfoProductorSerie infoProductor = new InfoProductorSerie(
                productorSerie.getTipoProductor(), productorSerie.getNombre());
        infoProductor.setIdDescriptor(productorSerie.getId());
        if (productorSerie.getIdprocedimiento() != null)
            infoProductor.setPuedeSerEliminado(false);
        else if (_serviceUdocs.getNumUdocByProductor(productorSerie.getId()) > 0)
            infoProductor.setPuedeSerEliminado(false);
        infoProductor.setFechaInicio(productorSerie.getFechaInicial());
        infoProductor.setFechaFin(productorSerie.getFechaFinal());
        infoProductor.setMarcas(productorSerie.getMarcas());
        return infoProductor;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDefinicion() {
        return this.definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public String getDocsBasicosUnidadDocumental() {
        return this.docsBasicosUnidadDocumental;
    }

    public void setDocsBasicosUnidadDocumental(
            String docsBasicosUnidadDocumental) {
        this.docsBasicosUnidadDocumental = docsBasicosUnidadDocumental;
    }

    public String getNormativa() {
        return this.normativa;
    }

    public void setNormativa(String normativa) {
        this.normativa = normativa;
    }

    public String getTramites() {
        return this.tramites;
    }

    public void setTramites(String tramites) {
        this.tramites = tramites;
    }

    // public void addProductoresSerie(List productores) {
    // if (this.productoresserie == null)
    // this.productoresserie = new ArrayList();
    // this.productoresserie.addAll(productores);
    // }

    // public List getProductoresSerie() {
    // return this.productoresserie;
    // }

    public void removeProductor(String guid) {
        infoProductoresSerie.remove(guid);
    }

    /**
     * Obtiene la lista de Productores Vigentes
     *
     * @return Lista de {@link ProductorSerieVO}
     */
    public List getProductoresVigentes() {
        List listaVigentes = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie.isVigente()) {
                listaVigentes.add(infoProductorSerie);
            }
        }

        return listaVigentes;
    }

    /**
     * Obtiene la lista de Productores Vigentes
     *
     * @return Lista de {@link InfoProductorSerie};
     */
    public List getInfoProductoresVigentes() {
        List listaVigentes = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();
            if (infoProductorSerie.isVigente()) {
                listaVigentes.add(infoProductorSerie);
            }
        }

        return listaVigentes;
    }

    /**
     * Obtiene la lista de productores históricos
     *
     * @return Lista de {@link InfoProductorSerie}
     */
    public List getInfoProductoresHistoricos() {
        List listaHistoricos = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();
            if (infoProductorSerie.isHistorico()) {
                listaHistoricos.add(infoProductorSerie);
            }
        }

        return listaHistoricos;
    }

    /**
     * Obtiene la lista de productores históricos
     *
     * @return Lista de {@link InfoProductorSerie}
     */
    public List getInfoProductoresVigentesAElegir() {
        List lista = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie.isVigenteSeleccionable()) {
                lista.add(infoProductorSerie);
            }
        }

        return lista;
    }

    /**
     * Obtiene la lista de productores históricos
     *
     * @return Lista de {@link ProductorSerieVO}
     */
    public List getProductoresHistoricos() {
        List listaHistoricos = new ArrayList();

        for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                .hasNext();) {
            IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                    .next();

            if (infoProductorSerie.isHistorico()) {
                listaHistoricos.add(infoProductorSerie.getProductorSerieVO());
            }
        }

        return listaHistoricos;
    }

    //
    // public void removeProductorHistorico(String guid){
    // int posAEliminar = -1;
    //
    // if(ListUtils.isNotEmpty(this.productoresserie)){
    // for (int i=0; i<this.productoresserie.size(); i++){
    // InfoProductorSerie infoProductorSerie = (InfoProductorSerie)
    // this.productoresserie.get(i);
    // if(infoProductorSerie.isHistorico()
    // && guid.equals(infoProductorSerie.getGuid())){
    // posAEliminar = i;
    // }
    // }
    // }
    //
    // if(posAEliminar > -1){
    // this.productoresserie.remove(posAEliminar);
    // }
    // }
    //
    //
    // public List getProductoresPasadosAHistoricos(){
    // List listaHistoricos = new ArrayList();
    //
    // if(ListUtils.isNotEmpty(this.productoresserie)){
    // for (int i=0; i<this.productoresserie.size(); i++){
    // InfoProductorSerie infoProductorSerie = (InfoProductorSerie)
    // this.productoresserie.get(i);
    //
    // if(infoProductorSerie.isPasadoAHistorico()){
    // listaHistoricos.add(infoProductorSerie);
    // }
    // }
    // }
    //
    // return listaHistoricos;
    // }

    public void setProductoresSerie(List productores) {

        if (ListUtils.isNotEmpty(productores)) {
            for (Iterator iterator = productores.iterator(); iterator.hasNext();) {
                UniqueGuid productor = (UniqueGuid) iterator.next();

                infoProductoresSerie.put(productor.getGuid(), productor);
            }
        }
    }

    public void setProcedimiento(String codigo, String nombre,
            String sistemaProductor) {
        procedimiento = new InfoProcedimiento(codigo, nombre, sistemaProductor);
    }

    public void setProcedimiento(InfoBProcedimiento procedimiento,
            List productores) {
        this.procedimiento = procedimiento;
        setProductoresSerie(productores);
    }

    // public void addRegistro(String registro) {
    // if (this.registros == null)
    // this.registros = new ArrayList();
    // this.registros.add(registro);
    // }

    public InfoExtendida getInfoExtendida() {
        return infoExtendida;
    }

    public void setInfoExtendida(String tipoDocumentacion,
            String volumenPrevsionAnual, String soportePrevisionAnual) {
        this.infoExtendida = new InfoExtendida(tipoDocumentacion,
                volumenPrevsionAnual, soportePrevisionAnual);
        this.volumenPrevisionAnual = volumenPrevsionAnual;
        this.tipoDocumentacion = tipoDocumentacion;
        this.soportePrevisionAnual = soportePrevisionAnual;
    }

    public String getSoportePrevisionAnual() {
        return soportePrevisionAnual;
    }

    public void setSoportePrevisionAnual(String soportePrevisionAnual) {
        this.soportePrevisionAnual = soportePrevisionAnual;
    }

    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    public void setTipoDocumentacion(String tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }

    public String getVolumenPrevisionAnual() {
        return volumenPrevisionAnual;
    }

    public void setVolumenPrevisionAnual(String volumenPrevisionAnual) {
        this.volumenPrevisionAnual = volumenPrevisionAnual;
    }

    public InfoBProcedimiento getProcedimiento() {
        return procedimiento;
    }

    public void deleteProductoresSerie() {
        // if (productoresserie != null)
        // productoresserie.clear();
        // productoresserie = null;

        if (infoProductoresSerie != null) {
            infoProductoresSerie.clear();
        }

        infoProductoresSerie = new LinkedHashMap();

    }

    public String toString() {
        StringBuffer ret = new StringBuffer();

        ret.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        ret.append(this.contentToString());

        return ret.toString();
    }

    public String contentToString() {
        StringBuffer ret = new StringBuffer();

        ret.append("<identificacion_serie version=\"01.00\">");
        ret.append("<definicion>").append(Constants.addCData(definicion))
                .append("</definicion>");
        ret.append("<tramites>").append(Constants.addCData(tramites))
                .append("</tramites>");
        ret.append("<normativa>").append(Constants.addCData(normativa))
                .append("</normativa>");
        ret.append("<docs_basicos_unidad_documental>")
                .append(Constants.addCData(docsBasicosUnidadDocumental))
                .append("</docs_basicos_unidad_documental>");
        if (procedimiento != null) {
            ret.append("<procedimiento>");
            ret.append("<identificacion>")
                    .append(Constants.addCData(procedimiento.getId()))
                    .append("</identificacion>");
            ret.append("<nombre>")
                    .append(Constants.addCData(procedimiento.getNombre()))
                    .append("</nombre>");
            ret.append("<codigo_sistema_productor>")
                    .append(Constants.addCData(procedimiento
                            .getCodSistProductor()))
                    .append("</codigo_sistema_productor>");
            ret.append("</procedimiento>");
        }

        if (infoExtendida != null
                && ConfigConstants.getInstance()
                        .getMostrarInformacionIdentificacionExtendia()) {
            ret.append("<informacion_extendida>");
            ret.append("<tipo_documentacion>");
            ret.append(infoExtendida.getTipoDocumentacion());
            ret.append("</tipo_documentacion>");
            ret.append("<prevision_anual>");
            ret.append("<volumen>");
            ret.append(Constants.addCData(infoExtendida
                    .getVolumenPrevisionAnual()));
            ret.append("</volumen>");
            ret.append("<soporte>");
            ret.append(Constants.addCData(infoExtendida
                    .getSoportePrevisionAnual()));
            ret.append("</soporte>");
            ret.append("</prevision_anual>");
            ret.append("</informacion_extendida>");
        }

        ret.append("</identificacion_serie>");

        return ret.toString();
    }

    public boolean isWithoutValues() {
        return StringUtils.isBlank(definicion) && StringUtils.isBlank(tramites)
                && StringUtils.isBlank(normativa)
                && StringUtils.isBlank(docsBasicosUnidadDocumental)
                && infoProductoresSerie == null
                // && registros == null
                && procedimiento == null;
    }

    protected GestionSeriesBI getSerieBI() {
        if (_serviceSeries == null)
            _serviceSeries = services.lookupGestionSeriesBI();
        return _serviceSeries;
    }

    private GestionFondosBI getFondosBI() {
        if (_serviceFondos == null)
            _serviceFondos = services.lookupGestionFondosBI();
        return _serviceFondos;
    }

    protected GestionUnidadDocumentalBI getUdocsBI() {
        if (_serviceUdocs == null)
            _serviceUdocs = services.lookupGestionUnidadDocumentalBI();
        return _serviceUdocs;
    }

    protected GestionControlUsuariosBI getUsuariosBI() {
        if (_serviceUsuarios == null)
            _serviceUsuarios = services.lookupGestionControlUsuariosBI();
        return _serviceUsuarios;
    }

    public EntidadProductoraVO getEntidadProductoraFondo() {
        if (entidad == null)
            entidad = getFondosBI().getEntidadProductoraFondo(
                    serie.getIdFondo());
        return entidad;
    }

    /**
     * Determina si está permitido asociar un procedimiento a la serie durante
     * su identificación.
     *
     * @return <b>true </b> caso de que la vinculación de procedimiento es
     *         posible y <b>false </b> cuando no lo es
     */
    public boolean getPermitidoVincularProcedimiento() {
        if (permitidoVincularProcedimiento == null) {
            EntidadProductoraVO entidadProductora = getEntidadProductoraFondo();
            boolean returnValue = false;
            if (entidadProductora.getTipoentidad() == TipoEntidad.INSTITUCION
                    .getIdentificador()) {
                SistemaGestorCatalogo gestorCatalago = ConfiguracionSistemaArchivoFactory
                        .getConfiguracionSistemaArchivo()
                        .getSistemaGestorCatalogo();

                if(StringUtils.isNotEmpty(gestorCatalago.getIdSistGestorOrg())){
                	returnValue = gestorCatalago.getIdSistGestorOrg()
                        .equalsIgnoreCase(entidadProductora.getIdSistExt());
                }
                else{
                	logger.warn("No se ha definido sistema de gestor de catalogo");
                	 permitidoVincularProcedimiento = Boolean.FALSE;
                }
            }
            permitidoVincularProcedimiento = new Boolean(returnValue);

        }
        return permitidoVincularProcedimiento.booleanValue();
    }

    /**
     * Determina si está permitido alterar la vinculación actualmente
     * establecida entre la serie y procedimiento administrativo
     *
     * @return <b>true </b> si es posible realizar modificaciones y <b>false
     *         </b> si no lo es
     */
    public boolean getPermitidoEditarProcedimientoAsociado() {
        return permitidoEditarProcedimientoAsociado;
    }

    /**
     * Determinada si es posible elegir productores para la serie durante su
     * identificación o si el único productor posible va a ser la entidad
     * productora del fondo al que pertenece la serie
     *
     * @return <b>true </b> caso de que la selección de productores sea posible
     *         y <b>false </b> cuando no lo es
     */
    public boolean getPermitidaSeleccionProductores() {
        boolean permitidaSeleccionProductores = true;
        EntidadProductoraVO entidadProductoraFondo = getEntidadProductoraFondo();
        if (entidadProductoraFondo.getTipoentidad() == TipoEntidad.PERSONA
                .getIdentificador())
            permitidaSeleccionProductores = false;
        else {
            if (getProcedimiento() != null)
                permitidaSeleccionProductores = false;
        }
        return permitidaSeleccionProductores;
    }

    /**
     * Desvincula el procedimiento actualmente asociado a la serie
     */
    public void desvincularProcedimiento() {
        setProcedimiento(null, null);
        this.definicion = null;
        this.normativa = null;
        this.docsBasicosUnidadDocumental = null;
        this.tramites = null;
        deleteProductoresSerie();
    }

    public void vincularAProcedimientoExterno(IProcedimiento procedimiento)  {
        this.procedimiento = procedimiento.getInformacionBasica();
        this.definicion = procedimiento.getObjeto();
        this.normativa = procedimiento.getNormativa();
        this.docsBasicosUnidadDocumental = procedimiento.getDocumentosBasicos();
        this.tramites = procedimiento.getTramites();
    }

    /**
     * Vincula la serie al procedimiento que se suministra. La vinculación de
     * una serie documental con un procedimiento administrativo hace que los
     * datos identificativos de la serie (Normativa, Trámites, ...) se obtengan
     * de los definidos para el procedimiento
     *
     * @param procedimiento
     *            Procedimiento con el que vincular la serie
     * @throws NotAvailableException
     *             Caso de que el sistema gestor del catálogo de procedimientos
     *             no implemente alguna de las funcionalidades necesaria para la
     *             vinculación de la serie con el procedimiento
     * @throws GestorOrganismosException
     *             Caso que se produzca algún error durante la obtención de la
     *             información que se necesita del procedimiento desde el
     *             Sistema que gestiona el catálogo de procedimientos
     */
    public void vincularAProcedimiento(IProcedimiento procedimiento,
            ServiceClient serviceClient) throws NotAvailableException,
            GestorOrganismosException {
        desvincularProcedimiento();
        this.procedimiento = procedimiento.getInformacionBasica();
        this.definicion = procedimiento.getObjeto();
        this.normativa = procedimiento.getNormativa();
        this.docsBasicosUnidadDocumental = procedimiento.getDocumentosBasicos();
        this.tramites = procedimiento.getTramites();
        // this.productoresserie = new ArrayList();
        this.infoProductoresSerie = new LinkedHashMap();

        // Obtener los productores de la serie
        List productoresProcedimiento = procedimiento.getOrganosProductores();
        if (!CollectionUtils.isEmpty(productoresProcedimiento)) {
            String sistemaGestorOrganizacion = ConfiguracionSistemaArchivoFactory
                    .getConfiguracionSistemaArchivo().getConfiguracionFondos()
                    .getIdSistGestorOrg();

            // Obtener la entidad para el usuario conectado
            Properties params = null;

            if ((serviceClient != null)
                    && (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
                params = new Properties();
                params.put(MultiEntityConstants.ENTITY_PARAM,
                        serviceClient.getEntity());
            }

            GestorOrganismos gestorOrganismos = GestorOrganismosFactory
                    .getConnectorById(sistemaGestorOrganizacion, params);
            InfoOrganoProductorSerie unProductor = null;
            for (Iterator i = productoresProcedimiento.iterator(); i.hasNext();) {
                IOrganoProductor organoProductor = (IOrganoProductor) i.next();

                InfoOrgano datosOrgano = gestorOrganismos.recuperarOrgano(
                        TipoAtributo.IDENTIFICADOR_ORGANO,
                        organoProductor.getId());

                if (datosOrgano == null) {
                    datosOrgano = gestorOrganismos
                            .recuperarOrgano(TipoAtributo.CODIGO_ORGANO,
                                    organoProductor.getId());
                }

                if (datosOrgano != null) {
                    unProductor = new InfoOrganoProductorSerie(
                            ProductorSerieVO.TIPO_ORG_DEP,
                            sistemaGestorOrganizacion, datosOrgano.getId(),
                            NombreOrganoFormat.formatearNombreLargo(
                                    datosOrgano, gestorOrganismos
                                            .recuperarOrganosAntecesores(
                                                    datosOrgano.getId(), 0)),
                            datosOrgano.getCodigo());
                    unProductor.setPuedeSerEliminado(false);
                    if (organoProductor.getInicioProduccion() != null) {
                        unProductor.setFechaInicio(organoProductor
                                .getInicioProduccion());
                    }
                    addInfoProductorSerie(unProductor);

                } else {
                    throw new GestorOrganismosException(
                            Constants.ERROR_ORGANO_NO_ENCONTRADO);
                }
            }
        } else {
            // Si no hay productores de la serie, coger la entidad productora
            // del fondo
            EntidadProductoraVO entidadProductora = getEntidadProductoraFondo();
            InfoProductorSerie productorSerie = new InfoProductorSerie(
                    ProductorSerieVO.TIPO_ENTIDAD,
                    entidadProductora.getNombre());
            productorSerie.setIdDescriptor(entidadProductora.getId());
            productorSerie.setPuedeSerEliminado(false);
            addInfoProductorSerie(productorSerie);
        }
    }

    /**
     * Verifica si es posible editar los datos de la identificación o no
     *
     * @return <b>true</b> si los datos de la identificación (Normativa,
     *         Trámites, ...) pueden ser modificados y <b>false</b> en caso
     *         contrario
     */
    public boolean getPermitidaEdicionDatos() {
        return procedimiento == null;
    }

    /**
     * Extrae la información de identificación de la serie del XML que se
     * suministra
     *
     * @param identificacion
     *            XML con los datos de identificación de la serie
     */
    private void parseIdentificacion(String identificacion) {
        if (identificacion != null) {
            try {
                // ConfiguracionFondos config =
                // ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionFondos();
                URL digesterRulesFile = getClass().getClassLoader()
                        .getResource(Globals.RULES_IDENTIFICACION_SERIE);
                Digester digester = DigesterLoader
                        .createDigester(digesterRulesFile);
                digester.push(this);
                digester.parse(new StringReader(identificacion.trim()));
            } catch (Exception e) {
                throw new ArchivoModelException(e, "parseIdentificacion",
                        "Error extrayendo identificación de serie "
                                + this.serie);
            }
        }
    }

    /**
     * Incorpora la entidad productora del fondo al que pertenece la serie como
     * productor de la serie
     */
    public void incorporarEntidadProductoraComoProductor() {

        IInfoProductorSerie infoProductorSerie = getInfoProductorSerie(productorEntidadProductora
                .getGuid());

        if (infoProductorSerie == null) {
            infoProductoresSerie.put(productorEntidadProductora.getGuid(),
                    productorEntidadProductora);
        }
        //
        // if (productoresserie == null)
        // productoresserie = new ArrayList();
        //
        // if (!productoresserie.contains(productorEntidadProductora))

    }

    /**
     * Comprueba si las características de la serie permiten la incorporación de
     * la entidad productora del fondo al que pertenece la serie como productor
     * o no
     *
     * @return <b>true</b> si la incorporación como productor de la entidad
     *         productora es posible y <b>false</b> en caso contrario
     */
    public boolean getPermitidoIncorporarProductorEntidad() {
        boolean permitidoIncorporarProductorEntidad = true;

        IInfoProductorSerie infoProductorSerieEntidadProductora = getInfoProductorSerie(productorEntidadProductora
                .getGuid());

        boolean productorEntidadProductoraEnLista = false;
        if (infoProductorSerieEntidadProductora != null) {
            productorEntidadProductoraEnLista = true;
        }

        if (infoProductoresSerie != null) {
            permitidoIncorporarProductorEntidad = !productorEntidadProductoraEnLista;

            // Comprobar que la entidad productora del fondo no se ha sustituido
            // por una histórica
            for (Iterator iterator = getListaInfoProductoresSerie().iterator(); iterator
                    .hasNext();) {
                IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
                        .next();

                if (productorEntidadProductora.getIdDescriptor().equals(
                        infoProductorSerie.getSustituyeAHistorico())) {
                    permitidoIncorporarProductorEntidad = false;
                }
            }

        }
        return permitidoIncorporarProductorEntidad;
    }

    /**
     * Comprueba si se pueden realizar modificaciones sobre la identificación de
     * la serie
     *
     * @return <b>true</b> si es posible realizar modificaciones y <b>false</b>
     *         en caso contrario
     */
    public boolean getPuedeSerModificada() {
        boolean puedeSerModificada = false;
        if (serie.getEstadoserie() == EstadoSerie.NO_VIGENTE
                || serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO)
            if ((serviceClient != null && StringUtils.equals(
                    serviceClient.getId(), serie.getIdusrgestor()))
                    || (serviceClient
                            .hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)))
                puedeSerModificada = true;
        return puedeSerModificada;
    }

    /**
     * Comprueba si la identificación de la serie está en condiciones de ser
     * refrescada de manera que sean actualizados de manera automática aquellos
     * datos que han sido importados desde algún sistema externo y que por tanto
     * pueden haber variado
     *
     * @return <b>true</b> si es posible realizar el refresco <b>false</b> en
     *         caso contrario
     */
    public boolean getPuedeSerRefrescada() {
        boolean puedeSerRefrescada = false;
        if (serie.getEstadoserie() == EstadoSerie.VIGENTE
                && StringUtils.isNotBlank(getEntidadProductoraFondo()
                        .getIdSistExt())
                && serviceClient != null
                && (StringUtils.equals(serviceClient.getId(),
                        serie.getIdusrgestor()) || (serviceClient
                        .hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))))
            puedeSerRefrescada = true;
        return puedeSerRefrescada;
    }

    private String getGuid() {
        String guid;
        try {
            guid = _serviceSeries.getGuid();
        } catch (Exception e) {
            guid = "";
        }
        return guid;
    }
}
