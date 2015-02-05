package deposito.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import deposito.ClaseAccesoDepositoElectronico;
import deposito.DepositoConstants;
import deposito.vos.DepositoElectronicoVO;

/**
 * Formulario para la recogida de datos de depósitos electrónicos.
 */
public class DepositoElectronicoForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del depósito electrónico en la aplicación. */
	private String id = null;

	/** Identificador externo del depósito electrónico. */
	private String idExt = null;

	/** Nombre del depósito electrónico. */
	private String nombre = null;

	/** Descripción del depósito electrónico. */
	private String descripcion = null;

	/** Flag que indica si los documentos están firmados. */
	private String usoFirma = null;

	/** Clase de acceso. */
	private int claseAcceso = ClaseAccesoDepositoElectronico.WSDL_CON_CLAVE;

	/** URI del WSDL del servicio web. */
	private String uri = null;

	/** Usuario del servicio web. */
	private String usuario = null;

	/** Clave del usuario del servicio web. */
	private String clave = null;

	/** Confirmación de la clave del usuario del servicio web. */
	private String confirmacionClave = null;

	/** Lista de depósitos seleccionados. */
	private String[] depositosSeleccionados = new String[0];

	/**
	 * Constructor.
	 */
	public DepositoElectronicoForm() {
		super();
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		depositosSeleccionados = new String[0];
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(DepositoElectronicoVO vo) {
		if (vo != null) {
			setId(vo.getId());
			setIdExt(vo.getIdExt());
			setNombre(vo.getNombre());
			setDescripcion(vo.getDescripcion());
			setUsoFirma(vo.getUsoFirma());
			setClaseAcceso(vo.getClaseAcceso());
			setUri(vo.getUri());
			setUsuario(vo.getUsuario());
			setClave(vo.getClave());
			setConfirmacionClave(vo.getClave());
		}
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(DepositoElectronicoVO vo) {
		if (vo != null) {
			vo.setId(this.id);
			vo.setIdExt(this.idExt);
			vo.setNombre(this.nombre);
			vo.setDescripcion(this.descripcion);
			vo.setUsoFirma(this.usoFirma);
			vo.setClaseAcceso(this.getClaseAcceso());
			vo.setUri(this.getUri());
			vo.setUsuario(this.getUsuario());
			vo.setClave(this.getClave());
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

		// Id Externo
		if (StringUtils.isBlank(idExt)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.DEPOSITO_ELECTRONICO_IDEXT,
									request.getLocale())));
		}

		// Uso firma
		if (StringUtils.isBlank(usoFirma)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.DEPOSITO_ELECTRONICO_USO_FIRMA,
									request.getLocale())));
		}

		// URI WSDL
		if (StringUtils.isBlank(uri)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.DEPOSITO_ELECTRONICO_WS_WSDL,
									request.getLocale())));
		}

		// Confirmación de la clave
		if (StringUtils.isNotBlank(clave)
				&& StringUtils.isBlank(confirmacionClave)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(DepositoConstants.CLAVE_CONFIRMACION,
									request.getLocale())));
		} else if (StringUtils.isNotBlank(clave)
				&& !clave.equals(confirmacionClave)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_CONFIRMACION_CLAVE));
		}

		if (errors.isEmpty()) {
			// usuario y uri no pueden contener "<" o ">"
			checkCadena(request,
					DepositoConstants.DEPOSITO_ELECTRONICO_WS_WSDL, uri,
					new String[] { "<", ">" }, errors);
			checkCadena(request, DepositoConstants.USUARIO, usuario,
					new String[] { "<", ">" }, errors);
		}
		return errors;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String[] getDepositosSeleccionados() {
		return depositosSeleccionados;
	}

	public void setDepositosSeleccionados(String[] depositosSeleccionados) {
		this.depositosSeleccionados = depositosSeleccionados;
	}

	public int getClaseAcceso() {
		return claseAcceso;
	}

	public void setClaseAcceso(int claseAcceso) {
		this.claseAcceso = claseAcceso;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getConfirmacionClave() {
		return confirmacionClave;
	}

	public void setConfirmacionClave(String confirmacionClave) {
		this.confirmacionClave = confirmacionClave;
	}

	public String getUsoFirma() {
		return usoFirma;
	}

	public void setUsoFirma(String usoFirma) {
		this.usoFirma = usoFirma;
	}

/**
	 * Valida que una cadena no contenga cadenas o caracteres prohibidos.
	 * Ej una cadena para xml no puede tener '<' o '>'
	 *
	 * El mensaje de error generado contiene todas las cadenas prohibidas encontradas
	 * en la cadena
	 *
	 * @param nombreCampo el id de la property que contiene el nombre del campo
	 * @param cadena la cadena a chequear
	 * @param cadenasProhibidas las cadenas que no deben aparecer en 'cadena'
	 * @param errors Un ActionErrors para añadir el error en caso de encontrar
	 * 		  alguna cadena prohibida
	 * @return true si la cadena no contiene cadenasProhibidas. false en otro caso
	 */
	private boolean checkCadena(HttpServletRequest request, String nombreCampo,
			String cadena, String[] cadenasProhibidas, ActionErrors errors) {
		boolean valida = true;
		String cadProhibidasEncontradas = "";
		for (int i = 0; i < cadenasProhibidas.length; i++) {
			if (cadena.indexOf(cadenasProhibidas[i]) != -1) {
				cadProhibidasEncontradas = cadProhibidasEncontradas
						+ cadenasProhibidas[i] + ", ";
				valida = false;
			}
		}

		if (!valida) {
			cadProhibidasEncontradas = cadProhibidasEncontradas.substring(0,
					cadProhibidasEncontradas.length() - 2);
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CARACTER_EN_CAMPO_NO_PERMITIDO,
							Messages.getString(nombreCampo, request.getLocale()),
							cadProhibidasEncontradas));
		}
		return valida;
	}
}
