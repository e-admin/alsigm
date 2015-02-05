package descripcion.forms;

import gcontrol.model.NivelAcceso;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.model.TipoDescriptor;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.DescriptorVO;

/**
 * Formulario para la información de un descriptor.
 */
public class DescriptorForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private String idLista = null;
	private String nombreLista = null;
	private int tipo = -1;
	private int estado = -1;
	private String idSistExt = null;
	private String idDescrSistExt = null;
	private Date timestamp = null;
	private String idFichaDescr = null;
	private String nombreFichaDescr = null;
	private String tieneDescr = null;
	private String editClfDocs = "N";
	private String idRepEcm = null;
	private String nombreRepEcm = null;
	private int nivelAcceso = -1;
	private String idLCA = null;
	private String nombreLCA = null;

	/**
	 * Constructor.
	 */
	public DescriptorForm() {
		super();
	}

	/**
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
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
	 * @return Returns the idDescrSistExt.
	 */
	public String getIdDescrSistExt() {
		return idDescrSistExt;
	}

	/**
	 * @param idDescrSistExt
	 *            The idDescrSistExt to set.
	 */
	public void setIdDescrSistExt(String idDescrSistExt) {
		this.idDescrSistExt = idDescrSistExt;
	}

	/**
	 * @return Returns the idFichaDescr.
	 */
	public String getIdFichaDescr() {
		return idFichaDescr;
	}

	/**
	 * @param idFichaDescr
	 *            The idFichaDescr to set.
	 */
	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	/**
	 * @return Returns the idLCA.
	 */
	public String getIdLCA() {
		return idLCA;
	}

	/**
	 * @param idLCA
	 *            The idLCA to set.
	 */
	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	/**
	 * @return Returns the idLista.
	 */
	public String getIdLista() {
		return idLista;
	}

	/**
	 * @param idLista
	 *            The idLista to set.
	 */
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}

	/**
	 * @return Returns the idRepEcm.
	 */
	public String getIdRepEcm() {
		return idRepEcm;
	}

	/**
	 * @param idRepEcm
	 *            The idRepEcm to set.
	 */
	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	/**
	 * @return Returns the idSistExt.
	 */
	public String getIdSistExt() {
		return idSistExt;
	}

	/**
	 * @param idSistExt
	 *            The idSistExt to set.
	 */
	public void setIdSistExt(String idSistExt) {
		this.idSistExt = idSistExt;
	}

	/**
	 * @return Returns the nivelAcceso.
	 */
	public int getNivelAcceso() {
		return nivelAcceso;
	}

	/**
	 * @param nivelAcceso
	 *            The nivelAcceso to set.
	 */
	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the nombreFichaDescr.
	 */
	public String getNombreFichaDescr() {
		return nombreFichaDescr;
	}

	/**
	 * @param nombreFichaDescr
	 *            The nombreFichaDescr to set.
	 */
	public void setNombreFichaDescr(String nombreFichaDescr) {
		this.nombreFichaDescr = nombreFichaDescr;
	}

	/**
	 * @return Returns the nombreRepEcm.
	 */
	public String getNombreRepEcm() {
		return nombreRepEcm;
	}

	/**
	 * @param nombreRepEcm
	 *            The nombreRepEcm to set.
	 */
	public void setNombreRepEcm(String nombreRepEcm) {
		this.nombreRepEcm = nombreRepEcm;
	}

	/**
	 * @return Returns the tieneDescr.
	 */
	public String getTieneDescr() {
		return tieneDescr;
	}

	/**
	 * @param tieneDescr
	 *            The tieneDescr to set.
	 */
	public void setTieneDescr(String tieneDescr) {
		this.tieneDescr = tieneDescr;
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Returns the nombreLista.
	 */
	public String getNombreLista() {
		return nombreLista;
	}

	/**
	 * @param nombreLista
	 *            The nombreLista to set.
	 */
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	/**
	 * @return Returns the nombreLCA.
	 */
	public String getNombreLCA() {
		return nombreLCA;
	}

	/**
	 * @param nombreLCA
	 *            The nombreLCA to set.
	 */
	public void setNombreLCA(String nombreLCA) {
		this.nombreLCA = nombreLCA;
	}

	/**
	 * @return Returns the editClfDocs.
	 */
	public String getEditClfDocs() {
		return editClfDocs;
	}

	/**
	 * @param editClfDocs
	 *            The editClfDocs to set.
	 */
	public void setEditClfDocs(String editClfDocs) {
		this.editClfDocs = editClfDocs;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(DescriptorVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		vo.setIdLista(this.idLista);
		vo.setNombreLista(this.nombreLista);
		vo.setTipo(this.tipo);
		vo.setEstado(this.estado);
		vo.setIdSistExt(this.idSistExt);
		vo.setIdDescrSistExt(this.idDescrSistExt);
		vo.setTimestamp(this.timestamp);
		vo.setIdFichaDescr(this.idFichaDescr);
		vo.setNombreFichaDescr(this.nombreFichaDescr);
		vo.setTieneDescr(this.tieneDescr);
		vo.setEditClfDocs(this.editClfDocs);
		vo.setIdRepEcm(this.idRepEcm);
		vo.setNombreRepEcm(this.nombreRepEcm);
		vo.setNivelAcceso(this.nivelAcceso);
		vo.setIdLCA(this.idLCA);
		vo.setNombreLCA(this.nombreLCA);
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void populate(BusquedaDescriptoresVO vo) {
		if (vo != null) {
			vo.setIdLista(this.idLista);
			vo.setNombre(this.nombre);
			vo.setEstado(this.estado);
		}
	}

	/**
	 * Obtiene la información de un descriptor.
	 * 
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor() {
		DescriptorVO descriptor = new DescriptorVO();
		populate(descriptor);
		return descriptor;
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(DescriptorVO vo) {
		setId(vo.getId());
		setNombre(vo.getNombre());
		setIdLista(vo.getIdLista());
		setNombreLista(vo.getNombreLista());
		setTipo(vo.getTipo());
		setEstado(vo.getEstado());
		setIdSistExt(vo.getIdSistExt());
		setIdDescrSistExt(vo.getIdDescrSistExt());
		setTimestamp(vo.getTimestamp());
		setIdFichaDescr(vo.getIdFichaDescr());
		setNombreFichaDescr(vo.getNombreFichaDescr());
		setTieneDescr(vo.getTieneDescr());
		setEditClfDocs(vo.getEditClfDocs());
		setIdRepEcm(vo.getIdRepEcm());
		setNombreRepEcm(vo.getNombreRepEcm());
		setNivelAcceso(vo.getNivelAcceso());
		setIdLCA(vo.getIdLCA());
		setNombreLCA(vo.getNombreLCA());
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_NOMBRE_DESCRIPTOR,
									request.getLocale())));
		}

		// Estado
		if (estado < 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_ESTADO_DESCRIPTOR,
									request.getLocale())));
		}

		// Si el tipo de descriptor es ENTIDAD, debe tener ficha de descripción
		// asociada
		if ((tipo == TipoDescriptor.ENTIDAD)
				&& StringUtils.isBlank(idFichaDescr)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_FICHAS_DESCR_DESCRIPTOR,
									request.getLocale())));
		}

		// Nivel de acceso
		if (nivelAcceso < 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_NIVEL_ACCESO_DESCRIPTOR,
									request.getLocale())));
		}

		// Si el Nivel de acceso es RESTRINGIDO, debe tener lista de control de
		// acceso
		if ((nivelAcceso == NivelAcceso.RESTRINGIDO)
				&& StringUtils.isBlank(idLCA)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_LISTA_CONTROL_ACCESO_DESCRIPTOR,
									request.getLocale())));
		}

		return errors;
	}

}
