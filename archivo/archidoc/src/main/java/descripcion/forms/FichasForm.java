package descripcion.forms;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.forms.CustomForm;
import common.util.I18nConstants;
import common.util.StringUtils;
import common.util.XmlFacade;

import descripcion.ErrorKeys;
import descripcion.vos.FichaVO;
import es.archigest.framework.core.exceptions.ArchigestException;
import fondos.model.TipoNivelCF;

public class FichasForm extends CustomForm {

	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = -7079150774066684179L;
	private String id = null;
	private String idFichaOrigen = null;
	private String nombre = null;
	private String definicion = null;
	private String tipoNorma = null;
	private String tipoNivel = null;
	private String subTipoNivel = null;
	private String descripcion = null;
	private String[] fichasABorrar = null;
	private String guid = null;

	// Campos Avanzados
	/**
	 * Definción del mapeo de campos.
	 */
	private String definicionMapeo = null;

	/* Para la pantalla de la importación */
	private FormFile fichero;

	/* Para la pantalla de la exportación */
	private String pathFileName;

	public FichaVO populate(FichaVO fichaVO) {
		changeBlankToNull();

		if (fichaVO != null) {
			fichaVO.setId(getId());

			if (StringUtils.isNotEmpty(getGuid())) {
				fichaVO.setId(getGuid());
			}

			fichaVO.setNombre(getNombre());
			fichaVO.setDefinicion(getDefinicion());
			if (tipoNorma != null)
				fichaVO.setTipoNorma(Integer.parseInt(getTipoNorma()));
			if (tipoNivel != null) {
				fichaVO.setTipoNivel(Integer.parseInt(getTipoNivel()));
				if(getSubTipoNivel() != null){
					fichaVO.setSubTipoNivel(Integer.parseInt(getSubTipoNivel()));
				}
			}
			fichaVO.setDescripcion(getDescripcion());
		}
		return fichaVO;
	}

	public FichaVO populateImportarDefinicion(FichaVO fichaVO) {
		if (fichaVO != null) {
			try {
				fichaVO.setDefinicion(fichero.getInputStream().toString());
			} catch (FileNotFoundException ex) {
				logger.info(ex.getMessage());
				throw new ArchigestException(ex.getMessage());
			} catch (IOException ex) {
				logger.info(ex.getMessage());
				throw new ArchigestException(ex.getMessage());
			}
		}
		return fichaVO;
	}

	public void set(FichaVO fichaVO) {
		if (fichaVO != null) {
			setId(fichaVO.getId());
			setNombre(fichaVO.getNombre());
			setDefinicion(fichaVO.getDefinicion());
			setTipoNorma(String.valueOf(fichaVO.getTipoNorma()));
			setTipoNivel(String.valueOf(fichaVO.getTipoNivel()));
			setSubTipoNivel(String.valueOf(fichaVO.getSubTipoNivel()));
			setDescripcion(fichaVO.getDescripcion());
		}
	}

	public void resetBeforeDelete() {
		setFichasABorrar(null);
	}

	public void changeBlankToNull() {
		setId(StringUtils.changeBlankToNull(getId()));
		setNombre(StringUtils.changeBlankToNull(getNombre()));
		setDefinicion(StringUtils.changeBlankToNull(getDefinicion()));
		setTipoNorma(StringUtils.changeBlankToNull(getTipoNorma()));
		setTipoNivel(StringUtils.changeBlankToNull(getTipoNivel()));
		setDescripcion(StringUtils.changeBlankToNull(getDescripcion()));
		setGuid(StringUtils.changeBlankToNull(getGuid()));
	}

	public void reset() {
		id = null;
		nombre = null;
		definicion = null;
		tipoNorma = null;
		tipoNivel = null;
		descripcion = null;
		fichasABorrar = null;
		guid = null;
	}

	public String getDefinicion() {
		return definicion;
	}

	public void setDefinicion(String definicion) {
		this.definicion = definicion;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(String tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public String getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(String tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public String[] getFichasABorrar() {
		return fichasABorrar;
	}

	public void setFichasABorrar(String[] fichasABorrar) {
		this.fichasABorrar = fichasABorrar;
	}

	public FormFile getFichero() {
		return fichero;
	}

	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}

	public String getPathFileName() {
		return pathFileName;
	}

	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	public void setSubTipoNivel(String subTipoNivel) {
		this.subTipoNivel = subTipoNivel;
	}

	public String getSubTipoNivel() {
		return subTipoNivel;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}

	public ActionErrors validate(HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(getNombre())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, common.Messages
							.getString(I18nConstants.LABEL_NOMBRE,
									request.getLocale())));
		}

		if (StringUtils.isNotEmpty(getTipoNivel())
				&& getTipoNivel().equals(
						TipoNivelCF.UNIDAD_DOCUMENTAL
								.getIdentificadorAsString())
				&& StringUtils.isEmpty(getSubTipoNivel())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, common.Messages
							.getString(I18nConstants.LABEL_SUBTIPO,
									request.getLocale())));
		}

		if (StringUtils.isEmpty(getDefinicion())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, common.Messages
							.getString(I18nConstants.LABEL_DEFINICION,
									request.getLocale())));
		} else {
			try {
				new XmlFacade(getDefinicion(), ConfiguracionArchivoManager
						.getInstance().getPathXSD(
								ConfiguracionArchivoManager.XSD_FICHAS));

			} catch (IllegalArgumentException ex) {
				errors.add(
						ErrorKeys.ERROR_XML_DEFINICION_FICHA_NO_BIEN_FORMADO,
						new ActionError(
								ErrorKeys.ERROR_XML_DEFINICION_FICHA_NO_BIEN_FORMADO,
								ex.getMessage()));
			}
		}

		return errors;
	}

	public ActionErrors validateDuplicar(HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(getNombre())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, common.Messages
							.getString(I18nConstants.LABEL_NOMBRE,
									request.getLocale())));
		}
		return errors;
	}


	/**
	 * Validación de la sección avanzado (Mapeos de descripción)
	 *
	 * @param request
	 * @return
	 */
	public ActionErrors validateAvanzado(HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(getDefinicion())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, common.Messages
							.getString(I18nConstants.LABEL_DEFINICION,
									request.getLocale())));
		} else {
			try {
				new XmlFacade(
						getDefinicionMapeo(),
						ConfiguracionArchivoManager
								.getInstance()
								.getPathXSD(
										ConfiguracionArchivoManager.XSD_MAPEO_DESCRICRIPCION));

			} catch (IllegalArgumentException ex) {
				errors.add(
						ErrorKeys.ERROR_XML_MAPEO_DESCRIPCION_NO_BIEN_FORMADO,
						new ActionError(
								ErrorKeys.ERROR_XML_MAPEO_DESCRIPCION_NO_BIEN_FORMADO,
								ex.getMessage()));
			}
		}

		return errors;
	}

	public void setDefinicionMapeo(String definicionMapeo) {
		this.definicionMapeo = definicionMapeo;
	}

	public String getDefinicionMapeo() {
		return definicionMapeo;
	}

	/**
	 * Indica si tiene edición avazanda
	 *
	 * @return true si es unidad documental
	 */
	public boolean isConEdicionAvanzada() {
		return (String
				.valueOf(TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador())
				.equals(tipoNivel));
	}

	/**
	 * Obtiene el nombre final del fichero a exportar
	 * @param prefijo
	 * @param nombreFichero
	 * @return
	 */
	public String getNombreFicheroExportXml(String prefijo, String nombreFichero) {
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotEmpty(prefijo)) {
			str.append(prefijo);
		}

		str.append(nombreFichero).append(Constants.SEPARADOR_EXTENSION_FICHERO)
				.append("xml");

		return str.toString();

	}

	public void setIdFichaOrigen(String idFichaOrigen) {
		this.idFichaOrigen = idFichaOrigen;
	}

	public String getIdFichaOrigen() {
		return idFichaOrigen;
	}

}
