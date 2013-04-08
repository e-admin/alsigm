package transferencias.vos;

import se.instituciones.GestorOrganismos;
import se.usuarios.AppUser;
import transferencias.electronicas.common.SistemaTramitador;
import transferencias.electronicas.serie.Productor;
import transferencias.electronicas.udoc.ContenidoUDocXML;
import ws.transferencias.vo.TramitadorInfo;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionWsTransferencias;

import common.util.ArrayUtils;

import descripcion.vos.DescriptorVO;
import fondos.actions.FondoPO;
import fondos.model.TipoProductor;
import fondos.vos.SerieVO;
import gcontrol.vos.CAOrganoVO;

public class TransferenciaElectronicaInfo {
	private AppUser appUser;

	private String anio;

	private ContenidoUDocXML contenidoUDocXML;

	private SistemaTramitador sistemaTramitador;

	private String codigoProcedimiento;

	private int verificarUnicidad=0;

	private FondoPO fondo;

	private SerieVO serieVO;
	private PrevisionVO previsionVO;
	private DetallePrevisionVO detallePrevisionVO;
	private RelacionEntregaVO relacionEntregaVO;

	private GestorOrganismos gestorOrganismos;

	private DescriptorVO descriptorProductorSerieVO;

	private CAOrganoVO organoProductorSerie;

	private String idUnidadDocumental = null;

	private boolean insertarFichaSerie = false;

	private String[] idsInternosDocumentos = new String[0];

	private String[] idsClasificadoresDocumentosCreados = new String[0];


	/**
	 * Valor del elementocf una vez valida la unidad documental en el cuadro
	 */
	private String idElementoCF= null;

	/**
	 * @return el anioTransferencia
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @return el anioTransferencia
	 */
	public int getAnioAsInt() {
		return new Integer(anio).intValue();
	}

	/**
	 * @param anioExpediente
	 *            el anioExpediente a fijar
	 */
	public void setAnio(String anioExpediente) {
		this.anio = anioExpediente;
	}

	/**
	 * @param anioExpediente
	 *            el anioExpediente a fijar
	 */
	public void setAnio(int anioExpediente) {
		this.anio = String.valueOf(anioExpediente);
	}

	/**
	 * @return el contenidoUDocXML
	 */
	public ContenidoUDocXML getContenidoUDocXML() {
		return contenidoUDocXML;
	}

	/**
	 * @param contenidoUDocXML
	 *            el contenidoUDocXML a fijar
	 */
	public void setContenidoUDocXML(ContenidoUDocXML contenidoUDocXML) {
		this.contenidoUDocXML = contenidoUDocXML;
	}

	/**
	 * @return el idProcedimiento
	 */
	public String getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	/**
	 * @param idProcedimiento
	 *            el idProcedimiento a fijar
	 */
	public void setCodigoProcedimiento(String codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;

		if (contenidoUDocXML != null) {
			contenidoUDocXML.setCodigoProcedimiento(codigoProcedimiento);
		}
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setSistemaTramitador(TramitadorInfo tramitadorInfo) {
		if (tramitadorInfo != null) {
			setSistemaTramitador(new SistemaTramitador(
					tramitadorInfo.getCodigo(), tramitadorInfo.getNombre()));
		}
	}

	public void setSistemaTramitador(SistemaTramitador sistemaTramitador) {
		this.sistemaTramitador = sistemaTramitador;
		if (contenidoUDocXML != null) {
			contenidoUDocXML.setSistemaTramitador(sistemaTramitador);
		}
	}

	public SistemaTramitador getSistemaTramitador() {
		return sistemaTramitador;
	}

	/**
	 * @return el objeto fondoVO
	 */
	public FondoPO getFondo() {
		return fondo;
	}

	/**
	 * @param fondoVO
	 *            el objeto fondoVO a fijar
	 */
	public void setFondo(FondoPO fondoPO) {
		this.fondo = fondoPO;
	}

	/**
	 * @return el objeto serieVO
	 */
	public SerieVO getSerieVO() {
		return serieVO;
	}

	/**
	 * @param serieVO
	 *            el objeto serieVO a fijar
	 */
	public void setSerieVO(SerieVO serieVO) {
		this.serieVO = serieVO;
	}

	/**
	 * @return el objeto previsionVO
	 */
	public PrevisionVO getPrevisionVO() {
		return previsionVO;
	}

	/**
	 * @param previsionVO
	 *            el objeto previsionVO a fijar
	 */
	public void setPrevisionVO(PrevisionVO previsionVO) {
		this.previsionVO = previsionVO;
	}

	/**
	 * @return el objeto detallePrevisionVO
	 */
	public DetallePrevisionVO getDetallePrevisionVO() {
		return detallePrevisionVO;
	}

	/**
	 * @param detallePrevisionVO
	 *            el objeto detallePrevisionVO a fijar
	 */
	public void setDetallePrevisionVO(DetallePrevisionVO detallePrevisionVO) {
		this.detallePrevisionVO = detallePrevisionVO;
	}

	/**
	 * @return el objeto relacionEntregaVO
	 */
	public RelacionEntregaVO getRelacionEntregaVO() {
		return relacionEntregaVO;
	}

	/**
	 * @param relacionEntregaVO
	 *            el objeto relacionEntregaVO a fijar
	 */
	public void setRelacionEntregaVO(RelacionEntregaVO relacionEntregaVO) {
		this.relacionEntregaVO = relacionEntregaVO;
	}

	/**
	 * @return el objeto idOrganoRemitente
	 */
	public String getIdOrganoRemitente() {
		return getOrganoProductorSerie().getIdOrg();
	}

	public String getCodSistemaProductor() {
		return getSistemaTramitador().getId();
	}

	public String getNombreSistProductor() {
		return getSistemaTramitador().getNombre();
	}

	public String getIdNivelDocumental() {
		return getConfiguracionWSTransferencias().getIdNivelUdoc();
	}

	public String getIdFichaUdoc() {
		return getSerieVO().getIdFichaDescrPrefUdoc(getIdNivelDocumental());
	}

	public String getIdRepEcmUdoc(){
		return getSerieVO().getIdRepEcmPrefUdoc(getIdNivelDocumental());
	}

	public String getIdArchivoReceptor() {
		return getFondo().getIdArchivo();
	}

	public String getIdFondoDestino() {
		return getFondo().getId();
	}

	public String getIdUsuarioGestor() {
		return getAppUser().getId();
	}

	public String getIdPrevision() {
		return getPrevisionVO().getId();
	}

	public String getIdSerie() {
		return getSerieVO().getId();
	}

	public Productor getProductor(){
		if(contenidoUDocXML != null){
			return contenidoUDocXML.getProductor();
		}
		return null;
	}

	public String getIdListaDescriptorOrganizacion(){
		return ConfiguracionSistemaArchivoFactory
	    .getConfiguracionSistemaArchivo().getConfiguracionGeneral()
		.getListaDescriptoresEntidad(TipoProductor.BDORGANIZACION.getIdentificador())
		.getId();

	}


	public String getIdSistGestorOrg(){
		return 	ConfiguracionSistemaArchivoFactory
	    .getConfiguracionSistemaArchivo().getConfiguracionFondos()
	    .getIdSistGestorOrg();
	}

	/**
	 * @param gestorOrganismos el objeto gestorOrganismos a fijar
	 */
	public void setGestorOrganismos(GestorOrganismos gestorOrganismos) {
		this.gestorOrganismos = gestorOrganismos;
	}

	/**
	 * @return el objeto gestorOrganismos
	 */
	public GestorOrganismos getGestorOrganismos() {
		return gestorOrganismos;
	}


	public ConfiguracionSistemaArchivo getConfiguracionSistemaArchivo(){
		return ConfiguracionSistemaArchivoFactory
		.getConfiguracionSistemaArchivo();
	}

	public ConfiguracionFondos getConfiguracionFondos(){

		ConfiguracionFondos configuracionFondos = getConfiguracionSistemaArchivo()
				.getConfiguracionFondos();

		return configuracionFondos;
	}

	public ConfiguracionWsTransferencias getConfiguracionWSTransferencias(){
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();

		ConfiguracionWsTransferencias configuracionWS = config
				.getConfiguracionWsTransferencias();

		return configuracionWS;
	}

	/**
	 * @param descriptorProductorSerieVO el objeto descriptorProductorSerieVO a fijar
	 */
	public void setDescriptorProductorSerieVO(DescriptorVO descriptorProductorSerieVO) {
		this.descriptorProductorSerieVO = descriptorProductorSerieVO;
	}

	/**
	 * @return el objeto descriptorProductorSerieVO
	 */
	public DescriptorVO getDescriptorProductorSerieVO() {
		return descriptorProductorSerieVO;
	}

	/**
	 * @param organoProductorSerie el objeto organoProductorSerie a fijar
	 */
	public void setOrganoProductorSerie(CAOrganoVO organoProductorSerie) {
		this.organoProductorSerie = organoProductorSerie;
	}

	/**
	 * @return el objeto organoProductorSerie
	 */
	public CAOrganoVO getOrganoProductorSerie() {
		return organoProductorSerie;
	}

	/**
	 * @param idUnidadDocumental el objeto idUnidadDocumental a fijar
	 */
	public void setIdUnidadDocumental(String idUnidadDocumental) {
		this.idUnidadDocumental = idUnidadDocumental;
	}

	/**
	 * @return el objeto idUnidadDocumental
	 */
	public String getIdUnidadDocumental() {
		return idUnidadDocumental;
	}

	/**
	 * @param insertarFichaSerie el objeto insertarFichaSerie a fijar
	 */
	public void setInsertarFichaSerie(boolean insertarFichaSerie) {
		this.insertarFichaSerie = insertarFichaSerie;
	}

	/**
	 * @return el objeto insertarFichaSerie
	 */
	public boolean isInsertarFichaSerie() {
		return insertarFichaSerie;
	}

	/**
	 * @param idElementoCF el objeto idElementoCF a fijar
	 */
	public void setIdElementoCF(String idElementoCF) {
		this.idElementoCF = idElementoCF;
	}

	/**
	 * @return el objeto idElementoCF
	 */
	public String getIdElementoCF() {
		return idElementoCF;
	}

	public void addIdInternoDocumento(String id) {
		idsInternosDocumentos = (String[]) ArrayUtils.add(idsInternosDocumentos, id);
	}

	/**
	 * @param idsInternosDocumentos el objeto idsInternosDocumentos a fijar
	 */
	public void setIdsInternosDocumentos(String[] idsInternosDocumentos) {
		this.idsInternosDocumentos = idsInternosDocumentos;
	}

	/**
	 * @return el objeto idsInternosDocumentos
	 */
	public String[] getIdsInternosDocumentos() {
		return idsInternosDocumentos;
	}

	/**
	 * @param idsClasificadoresDocumentosCreados el objeto idsClasificadoresDocumentosCreados a fijar
	 */
	public void setIdsClasificadoresDocumentosCreados(
			String[] idsClasificadoresDocumentosCreados) {
		this.idsClasificadoresDocumentosCreados = idsClasificadoresDocumentosCreados;
	}

	/**
	 * @return el objeto idsClasificadoresDocumentosCreados
	 */
	public String[] getIdsClasificadoresDocumentosCreados() {
		return idsClasificadoresDocumentosCreados;
	}

	public void addIdClasificadoresDocumentosCreados(String id){
		idsClasificadoresDocumentosCreados = (String[]) ArrayUtils.add(idsClasificadoresDocumentosCreados, id);
	}

	/**
	 * @param verificarUnicidad el objeto verificarUnicidad a fijar
	 */
	public void setVerificarUnicidad(int verificarUnicidad) {
		this.verificarUnicidad = verificarUnicidad;
	}

	/**
	 * @return el objeto verificarUnicidad
	 */
	public int getVerificarUnicidad() {
		return verificarUnicidad;
	}
}
