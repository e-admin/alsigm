package docelectronicos.actions;

import fondos.utils.FondosUtils;
import fondos.vos.ElementoCuadroClasificacionVO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.UsuarioVO;

import org.apache.log4j.Logger;

import se.usuarios.AppUser;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

import docelectronicos.DocumentosConstants;
import docelectronicos.EstadoTareaCaptura;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.vos.DocTCapturaVO;

public class DocTCapturaPO extends DocTCapturaVO {

	ServiceRepository serviceRepository = null;

	GestionControlUsuariosBI usuarioService = null;
	GestionDocumentosElectronicosBI documentosService = null;
	UsuarioPO usuarioPO = null;
	String nombreEstado = null;
	String nombreTipoObjeto = null;
	String idFondoObj = null;
	AppUser user = null;
	protected final Logger logger = Logger.getLogger(getClass());

	/**
	 * @return Returns the usuarioService.
	 */
	public GestionControlUsuariosBI getUsuarioService() {
		if (usuarioService == null)
			usuarioService = serviceRepository.lookupGestionControlUsuariosBI();
		return usuarioService;
	}

	public GestionDocumentosElectronicosBI getDocumentosService() {
		if (documentosService == null)
			documentosService = serviceRepository
					.lookupGestionDocumentosElectronicosBI();
		return documentosService;
	}

	public DocTCapturaPO(ServiceRepository serviceRepository,
			DocTCapturaVO capturaVO) {
		POUtils.copyVOProperties(this, capturaVO);
		this.serviceRepository = serviceRepository;
	}

	public String getNombreEstado() {
		return new StringBuffer(
				DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_ESTADO
						+ ".").append(getEstado()).toString();
	}

	public String getNombreTipoObjeto() {
		if (nombreTipoObjeto == null) {
			nombreTipoObjeto = new StringBuffer()
					.append(DocumentosConstants.LABEL_DOCUMENTOS_DIGITALIZACION_TIPO_OBJ
							+ ".").append(getTipoObj()).toString();
		}
		return nombreTipoObjeto;
	}

	public UsuarioPO getUsuarioCaptura() {
		if (usuarioPO == null) {
			UsuarioVO usuarioVO = getUsuarioService().getUsuario(
					getIdUsrCaptura());
			usuarioPO = new UsuarioPO(usuarioVO, serviceRepository);
		}
		return usuarioPO;
	}

	public boolean isFinalizable() {
		try {
			getDocumentosService().checkCapturaFinalizable(getIdObj(),
					getTipoObj(), getEstado());
			getDocumentosService().checkPermisosParaFinalizarTarea(this);
		} catch (DocElectronicosException e) {
			return false;
		}
		return true;
	}

	public boolean isValidacionFinalizable() {
		try {
			getDocumentosService().checkValidacionCapturaFinalizable(this);
		} catch (DocElectronicosException e) {
			return false;
		}
		return true;
	}

	public boolean isValidable() {
		try {
			getDocumentosService().checkCapturaValidable(this);
		} catch (DocElectronicosException e) {
			return false;
		}
		return true;
	}

	public boolean isFinalizada() {
		return getEstado() == EstadoTareaCaptura.FINALIZADA.getValue();
	}

	public boolean isEliminable() {
		try {
			getDocumentosService().checkTareaEliminable(this);
		} catch (DocElectronicosException e) {
			return false;
		}
		return true;

	}

	public boolean isModificablePorElementos() {
		try {
			getDocumentosService().checkTareaModificablePorElementos(this);
		} catch (DocElectronicosException e) {
			return false;
		} catch (Throwable e) {
			return false;
		}
		return true;

	}

	public String getNombreUsuarioCompleto() {
		return new StringBuffer(super.getApellidosUsuario() == null ? ""
				: super.getApellidosUsuario())
				.append(super.getApellidosUsuario() == null ? "" : ", ")
				.append(super.getNombreUsuario()).toString();
	}

	public String getCodReferenciaPersonalizado() {
		if (user == null)
			return getCodRefObj();
		if (idFondoObj == null) {
			ElementoCuadroClasificacionVO elem = serviceRepository
					.lookupGestionCuadroClasificacionBI()
					.getElementoCuadroClasificacion(getIdObj());
			idFondoObj = elem.getIdFondo();
		}
		try {
			return FondosUtils.getCodReferenciaPorSecciones(user, idFondoObj,
					getCodRefObj(), serviceRepository);
		} catch (Exception e) {
			logger.error(
					"Error al obtener el código de referencia personalizado del Documento de Captura.",
					e);
			return null;
		}
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
}
