package es.ieci.tecdoc.fwktd.dir3.api.vo.old;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Información de una unidad orgánica.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class UnidadOrganicaVO extends Entity {

	private static final long serialVersionUID = 5515960788820716930L;

	/*
	 * Datos Identificativos y Estructurales
	 */

	private String nombre;
	private String siglas;
	private NivelAdministracionVO nivelAdministracion;
	private TipoEntidadPublicaVO tipoEntidadPublica;
	private TipoUnidadOrganicaVO tipoUnidadOrganica;
	private PoderVO poder;
	private Long nivelJerarquico;
	private String idUnidadOrganicaSuperiorJerarquica;
	private String idUnidadOrganicaPrincipal;
	private String entidadDerechoPublico;
	private String idUnidadPrincipalEntidadDerechoPublico;
	private ObservacionVO observacionesGeneral;

	/*
	 * Ámbito Territorial de Ejercicio de Competencias
	 */

	private AmbitoTerritorialVO ambitoTerritorialCompetencias;
	private EntidadGeograficaVO entidadGeografica;
	private PaisVO pais;
	private ComunidadAutonomaVO comunidadAutonoma;
	private ProvinciaVO provincia;
	private IslaVO isla;
	private LocalidadVO municipio;
	private LocalidadVO entidadLocalMenor;
	private String idLocalidadExtranjera;
	private String competenciasUnidad;
	private String disposicionLegalCompetenciasUnidad;

	/*
	 * Datos de Locaclización de la Unidad Orgánica
	 */

	private String mismaDireccionUnidadSuperior;
	private Long idDireccion;

	/*
	 * Datos de Vigencia de la Unidad Orgánica
	 */

	private EstadoEntidadVO estadoEntidad;
	private Date fechaAltaOficial;
	private Date fechaBajaOficial;
	private Date fechaExtincion;
	private Date fechaAnulacion;
	private ObservacionVO observacionesBaja;

	/*
	 * Datos de auditoría y control
	 */

	private String idFuente;
	private String idExternoUnidadOrganica;
	private Date fechaAltaSistema;
	private Date fechaUltimaActualizacion;
	// Usuario que ha dado el alta
	// Usuario que ha realizado la última actualización
/*
	N_ID_OBSERVACION_CONTACTO       NUMBER(6)           NULL,
 */

	/**
	 * Constructor.
	 */
	public UnidadOrganicaVO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public NivelAdministracionVO getNivelAdministracion() {
		return nivelAdministracion;
	}

	public void setNivelAdministracion(NivelAdministracionVO nivelAdministracion) {
		this.nivelAdministracion = nivelAdministracion;
	}

	public TipoEntidadPublicaVO getTipoEntidadPublica() {
		return tipoEntidadPublica;
	}

	public void setTipoEntidadPublica(TipoEntidadPublicaVO tipoEntidadPublica) {
		this.tipoEntidadPublica = tipoEntidadPublica;
	}

	public TipoUnidadOrganicaVO getTipoUnidadOrganica() {
		return tipoUnidadOrganica;
	}

	public void setTipoUnidadOrganica(TipoUnidadOrganicaVO tipoUnidadOrganica) {
		this.tipoUnidadOrganica = tipoUnidadOrganica;
	}

	public PoderVO getPoder() {
		return poder;
	}

	public void setPoder(PoderVO poder) {
		this.poder = poder;
	}

	public Long getNivelJerarquico() {
		return nivelJerarquico;
	}

	public void setNivelJerarquico(Long nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	public String getIdUnidadOrganicaSuperiorJerarquica() {
		return idUnidadOrganicaSuperiorJerarquica;
	}

	public void setIdUnidadOrganicaSuperiorJerarquica(
			String idUnidadOrganicaSuperiorJerarquica) {
		this.idUnidadOrganicaSuperiorJerarquica = idUnidadOrganicaSuperiorJerarquica;
	}

	public String getIdUnidadOrganicaPrincipal() {
		return idUnidadOrganicaPrincipal;
	}

	public void setIdUnidadOrganicaPrincipal(String idUnidadOrganicaPrincipal) {
		this.idUnidadOrganicaPrincipal = idUnidadOrganicaPrincipal;
	}

	public String getEntidadDerechoPublico() {
		return entidadDerechoPublico;
	}

	public void setEntidadDerechoPublico(String entidadDerechoPublico) {
		this.entidadDerechoPublico = entidadDerechoPublico;
	}

	public String getIdUnidadPrincipalEntidadDerechoPublico() {
		return idUnidadPrincipalEntidadDerechoPublico;
	}

	public void setIdUnidadPrincipalEntidadDerechoPublico(
			String idUnidadPrincipalEntidadDerechoPublico) {
		this.idUnidadPrincipalEntidadDerechoPublico = idUnidadPrincipalEntidadDerechoPublico;
	}

	public AmbitoTerritorialVO getAmbitoTerritorialCompetencias() {
		return ambitoTerritorialCompetencias;
	}

	public void setAmbitoTerritorialCompetencias(
			AmbitoTerritorialVO ambitoTerritorialCompetencias) {
		this.ambitoTerritorialCompetencias = ambitoTerritorialCompetencias;
	}

	public ObservacionVO getObservacionesGeneral() {
		return observacionesGeneral;
	}

	public void setObservacionesGeneral(ObservacionVO observacionesGeneral) {
		this.observacionesGeneral = observacionesGeneral;
	}

	public EntidadGeograficaVO getEntidadGeografica() {
		return entidadGeografica;
	}

	public void setEntidadGeografica(EntidadGeograficaVO entidadGeografica) {
		this.entidadGeografica = entidadGeografica;
	}

	public PaisVO getPais() {
		return pais;
	}

	public void setPais(PaisVO pais) {
		this.pais = pais;
	}

	public ComunidadAutonomaVO getComunidadAutonoma() {
		return comunidadAutonoma;
	}

	public void setComunidadAutonoma(ComunidadAutonomaVO comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public IslaVO getIsla() {
		return isla;
	}

	public void setIsla(IslaVO isla) {
		this.isla = isla;
	}

	public LocalidadVO getMunicipio() {
		return municipio;
	}

	public void setMunicipio(LocalidadVO municipio) {
		this.municipio = municipio;
	}

	public LocalidadVO getEntidadLocalMenor() {
		return entidadLocalMenor;
	}

	public void setEntidadLocalMenor(LocalidadVO entidadLocalMenor) {
		this.entidadLocalMenor = entidadLocalMenor;
	}

	public String getIdLocalidadExtranjera() {
		return idLocalidadExtranjera;
	}

	public void setIdLocalidadExtranjera(String idLocalidadExtranjera) {
		this.idLocalidadExtranjera = idLocalidadExtranjera;
	}

	public String getCompetenciasUnidad() {
		return competenciasUnidad;
	}

	public void setCompetenciasUnidad(String competenciasUnidad) {
		this.competenciasUnidad = competenciasUnidad;
	}

	public String getDisposicionLegalCompetenciasUnidad() {
		return disposicionLegalCompetenciasUnidad;
	}

	public void setDisposicionLegalCompetenciasUnidad(
			String disposicionLegalCompetenciasUnidad) {
		this.disposicionLegalCompetenciasUnidad = disposicionLegalCompetenciasUnidad;
	}

	public String getMismaDireccionUnidadSuperior() {
		return mismaDireccionUnidadSuperior;
	}

	public void setMismaDireccionUnidadSuperior(String mismaDireccionUnidadSuperior) {
		this.mismaDireccionUnidadSuperior = mismaDireccionUnidadSuperior;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public EstadoEntidadVO getEstadoEntidad() {
		return estadoEntidad;
	}

	public void setEstadoEntidad(EstadoEntidadVO estadoEntidad) {
		this.estadoEntidad = estadoEntidad;
	}

	public Date getFechaAltaOficial() {
		return fechaAltaOficial;
	}

	public void setFechaAltaOficial(Date fechaAltaOficial) {
		this.fechaAltaOficial = fechaAltaOficial;
	}

	public Date getFechaBajaOficial() {
		return fechaBajaOficial;
	}

	public void setFechaBajaOficial(Date fechaBajaOficial) {
		this.fechaBajaOficial = fechaBajaOficial;
	}

	public Date getFechaExtincion() {
		return fechaExtincion;
	}

	public void setFechaExtincion(Date fechaExtincion) {
		this.fechaExtincion = fechaExtincion;
	}

	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	public ObservacionVO getObservacionesBaja() {
		return observacionesBaja;
	}

	public void setObservacionesBaja(ObservacionVO observacionesBaja) {
		this.observacionesBaja = observacionesBaja;
	}

	public String getIdFuente() {
		return idFuente;
	}

	public void setIdFuente(String idFuente) {
		this.idFuente = idFuente;
	}

	public String getIdExternoUnidadOrganica() {
		return idExternoUnidadOrganica;
	}

	public void setIdExternoUnidadOrganica(String idExternoUnidadOrganica) {
		this.idExternoUnidadOrganica = idExternoUnidadOrganica;
	}

	public Date getFechaAltaSistema() {
		return fechaAltaSistema;
	}

	public void setFechaAltaSistema(Date fechaAltaSistema) {
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
}
