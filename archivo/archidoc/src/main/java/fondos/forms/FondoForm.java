package fondos.forms;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ApplicationResourcesConstants;
import common.ConfigConstants;
import common.Constants;
import common.Messages;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.FondoVO;

/**
 * Formulario para la recogida de datos en la creación y edición de fondos
 * documentales
 */
public class FondoForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idFondo;
	String codigo;
	String denominacion;
	String codigopais;
	String codigoautonomia;
	String codigoarchivo;
	int tipoFondo;
	String codOrdenacion;

	int tipoEntidad;
	boolean institucionEnSistemaExterno;
	String idInstitucionEnSistemaExterno;
	String idDescriptorEntidadProductora;
	String nombreEntidadProductora;

	String idnivelacrear;
	String idnivelpadre;
	String codigoclasificadorpadre;

	String idelementocf;
	String idclpadre;
	String tokenBusquedaEntidad;

	int nivelAcceso;
	String idLCA;

	public String getIdclpadre() {
		return this.idclpadre;
	}

	public void setIdclpadre(String idclpadre) {
		this.idclpadre = idclpadre;
	}

	public String getIdelementocf() {
		return this.idelementocf;
	}

	public void setIdelementocf(String idelementcf) {
		this.idelementocf = idelementcf;
	}

	public String getIdnivelacrear() {
		return this.idnivelacrear;
	}

	public void setIdnivelacrear(String idNivelACrear) {
		this.idnivelacrear = idNivelACrear;
	}

	public String getIdnivelpadre() {
		return this.idnivelpadre;
	}

	public void setIdnivelpadre(String idClasificadorPadre) {
		this.idnivelpadre = idClasificadorPadre;
	}

	public String getCodigoarchivo() {
		return this.codigoarchivo;
	}

	public void setCodigoarchivo(String codigoarchivo) {
		this.codigoarchivo = codigoarchivo;
	}

	public String getCodigoclasificadorpadre() {
		return this.codigoclasificadorpadre;
	}

	public void setCodigoclasificadorpadre(String codigoclasificadorfondo) {
		this.codigoclasificadorpadre = codigoclasificadorfondo;
	}

	public String getCodigopais() {
		return this.codigopais;
	}

	public void setCodigopais(String codigopais) {
		this.codigopais = codigopais;
	}

	public String getCodigoautonomia() {
		return this.codigoautonomia;
	}

	public void setCodigoautonomia(String codigoprovincia) {
		this.codigoautonomia = codigoprovincia;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public int getTipoEntidad() {
		return this.tipoEntidad;
	}

	public void setTipoEntidad(int tipoproductora) {
		this.tipoEntidad = tipoproductora;
	}

	public FondoVO populateFondoVOInEdit(FondoVO fondo) {
		// fondo.setFinalCodigo(getCodigoProductora());//se pone en servicio
		fondo.setCodPais(codigopais);
		fondo.setCodComunidad(codigoautonomia);
		fondo.setCodArchivo(codigoarchivo);
		fondo.setDenominacion(denominacion);

		return fondo;
	}

	public void fillForm(FondoVO fondo, EntidadProductoraVO entidadProductora) {
		setCodigopais(fondo.getCodPais());
		setCodigoautonomia(fondo.getCodComunidad());
		setCodigoarchivo(fondo.getCodArchivo());
		setCodigo(fondo.getCodigo());
		setDenominacion(fondo.getDenominacion());
		setIdnivelacrear(fondo.getIdNivel());
		setIdnivelpadre(fondo.getIdPadre());
		setCodOrdenacion(fondo.getOrdPos());

		setTipoEntidad(entidadProductora.getTipoentidad());
		setNombreEntidadProductora(entidadProductora.getNombre());
		setInstitucionEnSistemaExterno(entidadProductora.isFrombdexterna());
		setIdDescriptorEntidadProductora(entidadProductora.getId());
		setIdInstitucionEnSistemaExterno(entidadProductora.getIdDescrSistExt());
	}

	public ActionErrors validate(Locale locale) {
		ActionErrors errors = new ActionErrors();

		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();

		String caracteresProhibidos = Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION
				+ delimitadorCodigoReferencia
				+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL;

		if (StringUtils.isBlank(idFondo)) {
			if (GenericValidator.isBlankOrNull(idnivelacrear))
				errors.add(Constants.ERROR_REQUIRED, new ActionError(
						Constants.ERROR_REQUIRED, Messages.getString(ApplicationResourcesConstants.LABEL_TIPO_DE_FONDO, locale)));
		}

		if (Constants.hasForbidenChars(codigo, caracteresProhibidos)) {
			errors.add(Constants.ERROR_INVALID_CHARACTERS, new ActionError(
					Constants.ERROR_INVALID_CHARACTERS, Messages.getString(Constants.ETIQUETA_CODIGO, locale),
					caracteresProhibidos));
		}

		if (StringUtils.isBlank(idFondo)
				&& GenericValidator.isBlankOrNull(codigo)) {
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, Messages.getString(Constants.ETIQUETA_CODIGO, locale)));
		}

		if (GenericValidator.isBlankOrNull(denominacion)){
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, Messages.getString(ApplicationResourcesConstants.LABEL_DENOMINACION, locale)));
		}

		if (StringUtils.isBlank(idFondo)) {
			if (GenericValidator.isBlankOrNull(idDescriptorEntidadProductora)
					&& GenericValidator
							.isBlankOrNull(idInstitucionEnSistemaExterno))
				errors.add(Constants.ERROR_REQUIRED, new ActionError(
						Constants.ERROR_REQUIRED, Messages.getString(ApplicationResourcesConstants.LABEL_ENTIDAD_PRODUCTORA, locale)));
		}

		if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
			if (StringUtils.isEmpty(getCodOrdenacion())) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(
										Constants.ETIQUETA_CODIGO_ORDENACION,
										getSessionLocale())));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public String getTokenBusquedaEntidad() {
		return tokenBusquedaEntidad;
	}

	public void setTokenBusquedaEntidad(String tokenBusquedaEntidad) {
		this.tokenBusquedaEntidad = tokenBusquedaEntidad;
	}

	public String getIdInstitucionEnSistemaExterno() {
		return idInstitucionEnSistemaExterno;
	}

	public void setIdInstitucionEnSistemaExterno(
			String idInstitucionEnSistemaExterno) {
		this.idInstitucionEnSistemaExterno = idInstitucionEnSistemaExterno;
	}

	public boolean isInstitucionEnSistemaExterno() {
		return institucionEnSistemaExterno;
	}

	public void setInstitucionEnSistemaExterno(
			boolean institucionEnSistemaExterno) {
		this.institucionEnSistemaExterno = institucionEnSistemaExterno;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreEntidadProductora() {
		return nombreEntidadProductora;
	}

	public void setNombreEntidadProductora(String nombreEntidadProductora) {
		this.nombreEntidadProductora = nombreEntidadProductora;
	}

	public String getIdDescriptorEntidadProductora() {
		return idDescriptorEntidadProductora;
	}

	public void setIdDescriptorEntidadProductora(
			String idDescriptorEntidadProductora) {
		this.idDescriptorEntidadProductora = idDescriptorEntidadProductora;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public int getTipoFondo() {
		return tipoFondo;
	}

	public void setTipoFondo(int tipoFondo) {
		this.tipoFondo = tipoFondo;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getCodOrdenacion() {
		return codOrdenacion;
	}

	public void setCodOrdenacion(String codOrdenacion) {
		this.codOrdenacion = codOrdenacion;
	}
}
