package docvitales.forms;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import common.forms.CustomForm;
import common.util.StringUtils;
import common.util.terceros.IThirdPartySearchForm;

import docvitales.EstadoDocumentoVital;
import docvitales.vos.FormDocumentoVitalVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;

/**
 * Formulario para la información de un documento vital.
 */
public class DocumentoVitalForm extends CustomForm implements
		IThirdPartySearchForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Fichero del documento vital */
	private FormFile file = null;

	/** Identificador del documento vital. */
	private String id = null;

	/** Identificador del ciudadano en la BD de Terceros. */
	private String idBdTerceros = null;

	/** Número de identidad del ciudadano. */
	private String numIdentidad = null;

	/** Identidad del ciudadano. */
	private String identidad = null;

	/** Identificador del tipo de documento vital. */
	private String idTipoDocVit = null;

	/** Nombre del tipo de documento vital. */
	private String nombreTipoDocVit = null;

	/** Fecha de caducidad. */
	private Date fechaCad = null;

	/** Estado del documento vital. */
	private int estadoDocVit = EstadoDocumentoVital.PENDIENTE_VALIDACION;

	/** Fecha de creación. */
	private Date fechaCr = null;

	/** Identificador del usuario de creación. */
	private String idUsuarioCr = null;

	/** Nombre del usuario de creación. */
	private String nombreUsuarioCr = null;

	/** Fecha de paso a vigente. */
	private Date fechaVig = null;

	/** Identificador del usuario de paso a vigente. */
	private String idUsuarioVig = null;

	/** Nombre del usuario de paso a vigente. */
	private String nombreUsuarioVig = null;

	/** Número de accesos. */
	private int numAccesos = 0;

	/** Fecha del último acceso. */
	private Date fechaUltAcceso = null;

	/** Tamaño del fichero. */
	private double tamanoFich = 0;

	/** Nombre original del fichero. */
	private String nombreOrgFich = null;

	/** Extensión del fichero. */
	private String extFich = null;

	/** Identificador del fichero. */
	private String idFich = null;

	/** Observaciones. */
	private String observaciones = null;

	/** Criterio de búsqueda. */
	private String thirdPartySearchType = null;
	private String nameSearchToken = null;
	private String surname1SearchToken = null;
	private String surname2SearchToken = null;
	private String companySearchToken = null;
	private String ifSearchToken = null;

	/**
	 * Constructor.
	 */
	public DocumentoVitalForm() {
		super();
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the estadoDocVit.
	 */
	public int getEstadoDocVit() {
		return estadoDocVit;
	}

	/**
	 * @param estadoDocVit
	 *            The estadoDocVit to set.
	 */
	public void setEstadoDocVit(int estadoDocVit) {
		this.estadoDocVit = estadoDocVit;
	}

	/**
	 * @return Returns the extFich.
	 */
	public String getExtFich() {
		return extFich;
	}

	/**
	 * @param extFich
	 *            The extFich to set.
	 */
	public void setExtFich(String extFich) {
		this.extFich = extFich;
	}

	/**
	 * @return Returns the fechaCad.
	 */
	public Date getFechaCad() {
		return fechaCad;
	}

	/**
	 * @param fechaCad
	 *            The fechaCad to set.
	 */
	public void setFechaCad(Date fechaCad) {
		this.fechaCad = fechaCad;
	}

	/**
	 * @return Returns the fechaCr.
	 */
	public Date getFechaCr() {
		return fechaCr;
	}

	/**
	 * @param fechaCr
	 *            The fechaCr to set.
	 */
	public void setFechaCr(Date fechaCr) {
		this.fechaCr = fechaCr;
	}

	/**
	 * @return Returns the fechaUltAcceso.
	 */
	public Date getFechaUltAcceso() {
		return fechaUltAcceso;
	}

	/**
	 * @param fechaUltAcceso
	 *            The fechaUltAcceso to set.
	 */
	public void setFechaUltAcceso(Date fechaUltAcceso) {
		this.fechaUltAcceso = fechaUltAcceso;
	}

	/**
	 * @return Returns the fechaVig.
	 */
	public Date getFechaVig() {
		return fechaVig;
	}

	/**
	 * @param fechaVig
	 *            The fechaVig to set.
	 */
	public void setFechaVig(Date fechaVig) {
		this.fechaVig = fechaVig;
	}

	/**
	 * @return Returns the idBdTerceros.
	 */
	public String getIdBdTerceros() {
		return idBdTerceros;
	}

	/**
	 * @param idBdTerceros
	 *            The idBdTerceros to set.
	 */
	public void setIdBdTerceros(String idBdTerceros) {
		this.idBdTerceros = idBdTerceros;
	}

	/**
	 * @return Returns the identidad.
	 */
	public String getIdentidad() {
		return identidad;
	}

	/**
	 * @param identidad
	 *            The identidad to set.
	 */
	public void setIdentidad(String identidad) {
		this.identidad = identidad;
	}

	/**
	 * @return Returns the idFich.
	 */
	public String getIdFich() {
		return idFich;
	}

	/**
	 * @param idFich
	 *            The idFich to set.
	 */
	public void setIdFich(String idFich) {
		this.idFich = idFich;
	}

	/**
	 * @return Returns the idTipoDocVit.
	 */
	public String getIdTipoDocVit() {
		return idTipoDocVit;
	}

	/**
	 * @param idTipoDocVit
	 *            The idTipoDocVit to set.
	 */
	public void setIdTipoDocVit(String idTipoDocVit) {
		this.idTipoDocVit = idTipoDocVit;
	}

	/**
	 * @return Returns the idUsuarioCr.
	 */
	public String getIdUsuarioCr() {
		return idUsuarioCr;
	}

	/**
	 * @param idUsuarioCr
	 *            The idUsuarioCr to set.
	 */
	public void setIdUsuarioCr(String idUsuarioCr) {
		this.idUsuarioCr = idUsuarioCr;
	}

	/**
	 * @return Returns the idUsuarioVig.
	 */
	public String getIdUsuarioVig() {
		return idUsuarioVig;
	}

	/**
	 * @param idUsuarioVig
	 *            The idUsuarioVig to set.
	 */
	public void setIdUsuarioVig(String idUsuarioVig) {
		this.idUsuarioVig = idUsuarioVig;
	}

	/**
	 * @return Returns the nombreOrgFich.
	 */
	public String getNombreOrgFich() {
		return nombreOrgFich;
	}

	/**
	 * @param nombreOrgFich
	 *            The nombreOrgFich to set.
	 */
	public void setNombreOrgFich(String nombreOrgFich) {
		this.nombreOrgFich = nombreOrgFich;
	}

	/**
	 * @return Returns the nombreTipoDocVit.
	 */
	public String getNombreTipoDocVit() {
		return nombreTipoDocVit;
	}

	/**
	 * @param nombreTipoDocVit
	 *            The nombreTipoDocVit to set.
	 */
	public void setNombreTipoDocVit(String nombreTipoDocVit) {
		this.nombreTipoDocVit = nombreTipoDocVit;
	}

	/**
	 * @return Returns the nombreUsuarioCr.
	 */
	public String getNombreUsuarioCr() {
		return nombreUsuarioCr;
	}

	/**
	 * @param nombreUsuarioCr
	 *            The nombreUsuarioCr to set.
	 */
	public void setNombreUsuarioCr(String nombreUsuarioCr) {
		this.nombreUsuarioCr = nombreUsuarioCr;
	}

	/**
	 * @return Returns the nombreUsuarioVig.
	 */
	public String getNombreUsuarioVig() {
		return nombreUsuarioVig;
	}

	/**
	 * @param nombreUsuarioVig
	 *            The nombreUsuarioVig to set.
	 */
	public void setNombreUsuarioVig(String nombreUsuarioVig) {
		this.nombreUsuarioVig = nombreUsuarioVig;
	}

	/**
	 * @return Returns the numAccesos.
	 */
	public int getNumAccesos() {
		return numAccesos;
	}

	/**
	 * @param numAccesos
	 *            The numAccesos to set.
	 */
	public void setNumAccesos(int numAccesos) {
		this.numAccesos = numAccesos;
	}

	/**
	 * @return Returns the numIdentidad.
	 */
	public String getNumIdentidad() {
		return numIdentidad;
	}

	/**
	 * @param numIdentidad
	 *            The numIdentidad to set.
	 */
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	/**
	 * @return Returns the observaciones.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            The observaciones to set.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return Returns the tamanoFich.
	 */
	public double getTamanoFich() {
		return tamanoFich;
	}

	/**
	 * @param tamanoFich
	 *            The tamanoFich to set.
	 */
	public void setTamanoFich(double tamanoFich) {
		this.tamanoFich = tamanoFich;
	}

	/**
	 * @return Returns the buscarPor.
	 */
	public String getThirdPartySearchType() {
		return thirdPartySearchType;
	}

	/**
	 * @param buscarPor
	 *            The buscarPor to set.
	 */
	public void setThirdPartySearchType(String buscarPor) {
		this.thirdPartySearchType = buscarPor;
	}

	/**
	 * @return Returns the nameSearchToken.
	 */
	public String getNameSearchToken() {
		return nameSearchToken;
	}

	/**
	 * @param nameSearchToken
	 *            The nameSearchToken to set.
	 */
	public void setNameSearchToken(String nameSearchToken) {
		this.nameSearchToken = nameSearchToken;
	}

	/**
	 * @return Returns the niSearchToken.
	 */
	public String getIfSearchToken() {
		return ifSearchToken;
	}

	/**
	 * @param niSearchToken
	 *            The niSearchToken to set.
	 */
	public void setIfSearchToken(String niSearchToken) {
		this.ifSearchToken = niSearchToken;
	}

	/**
	 * @return Returns the surname1SearchToken.
	 */
	public String getSurname1SearchToken() {
		return surname1SearchToken;
	}

	/**
	 * @param surname1SearchToken
	 *            The surname1SearchToken to set.
	 */
	public void setSurname1SearchToken(String surname1SearchToken) {
		this.surname1SearchToken = surname1SearchToken;
	}

	/**
	 * @return Returns the surname2SearchToken.
	 */
	public String getSurname2SearchToken() {
		return surname2SearchToken;
	}

	/**
	 * @param surname2SearchToken
	 *            The surname2SearchToken to set.
	 */
	public void setSurname2SearchToken(String surname2SearchToken) {
		this.surname2SearchToken = surname2SearchToken;
	}

	/**
	 * @return Returns the companySearchToken.
	 */
	public String getCompanySearchToken() {
		return companySearchToken;
	}

	/**
	 * @param companySearchToken
	 *            The companySearchToken to set.
	 */
	public void setCompanySearchToken(String companySearchToken) {
		this.companySearchToken = companySearchToken;
	}

	/**
	 * Establece los valores del formulario.
	 * 
	 * @param documentoVital
	 *            Información del documento vital.
	 */
	public void set(InfoBDocumentoVitalExtVO documentoVital) {
		if (documentoVital != null) {
			setId(documentoVital.getId());
			setIdBdTerceros(documentoVital.getIdBdTerceros());
			setNumIdentidad(documentoVital.getNumIdentidad());
			setIdentidad(documentoVital.getIdentidad());
			setIdTipoDocVit(documentoVital.getIdTipoDocVit());
			setNombreTipoDocVit(documentoVital.getNombreTipoDocVit());
			setFechaCad(documentoVital.getFechaCad());
			setEstadoDocVit(documentoVital.getEstadoDocVit());
			setFechaCr(documentoVital.getFechaCr());
			setIdUsuarioCr(documentoVital.getIdUsuarioCr());
			setNombreUsuarioCr(documentoVital.getNombreUsuarioCr());
			setFechaVig(documentoVital.getFechaVig());
			setIdUsuarioVig(documentoVital.getIdUsuarioVig());
			setNombreUsuarioVig(documentoVital.getNombreUsuarioVig());
			setNumAccesos(documentoVital.getNumAccesos());
			setFechaUltAcceso(documentoVital.getFechaUltAcceso());
			setTamanoFich(documentoVital.getTamanoFich());
			setNombreOrgFich(documentoVital.getNombreOrgFich());
			setExtFich(documentoVital.getExtFich());
			setIdFich(documentoVital.getIdFich());
			setObservaciones(documentoVital.getObservaciones());
		}
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * Establece los valores del formulario.
	 * 
	 * @param documentoVital
	 *            Información del documento vital.
	 */
	public void populate(InfoBDocumentoVitalExtVO documentoVital) {
		if (documentoVital != null) {
			documentoVital.setIdBdTerceros(getIdBdTerceros());
			documentoVital.setNumIdentidad(getNumIdentidad());
			documentoVital.setIdentidad(getIdentidad());
			documentoVital.setIdTipoDocVit(getIdTipoDocVit());
			documentoVital.setFechaCad(getFechaCad());
			documentoVital.setObservaciones(getObservaciones());
		}
	}

	public void populate(FormDocumentoVitalVO documentoVital) {
		if (documentoVital != null) {
			documentoVital.setIdTercero(getIdBdTerceros());
			documentoVital.setFechaCaducidad(getFechaCad());
			documentoVital.setIdTipoDocVit(getIdTipoDocVit());
			documentoVital.setObservaciones(getObservaciones());

			try {
				byte[] contenFile = getFile().getFileData();
				documentoVital.setContenidoFich(contenFile);
			} catch (IOException ioe) {
			}

			String fileName = getFile().getFileName();
			if (StringUtils.isNotBlank(fileName)) {
				int ix = fileName.lastIndexOf(".");
				if (ix > 0) {
					documentoVital.setNombreFich(fileName.substring(0, ix));
					documentoVital.setExtFich(fileName.substring(ix + 1));
				} else {
					documentoVital.setNombreFich(fileName);
					documentoVital.setExtFich("___");
				}
			}
		}
	}

	/**
	 * Valida el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// // Nombre
		// if (StringUtils.isBlank(nombre))
		// {
		// errors.add(ActionErrors.GLOBAL_MESSAGE,
		// new ActionError(Constants.ERROR_REQUIRED,
		// Messages.getString(DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_TIPODOCVITAL_NOMBRE)));
		// }

		return errors;
	}

	public void resetForCreate() {
		setIdBdTerceros(null);
		setNumIdentidad(null);
		setIdentidad(null);
		setIdTipoDocVit(null);
		setFechaCad(null);
		setObservaciones(null);
	}

}
