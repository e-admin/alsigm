package docelectronicos.forms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.DocDocumentoVO;

/**
 * Formulario para la información de un documento electrónico.
 */
public class DocumentoForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del documento. */
	private String id = null;

	/** Nombre del documento. */
	private String nombre = null;

	/** Identificador del clasificador padre. */
	private String idClfPadre = null;

	/** Tamaño del fichero. */
	private double tamanoFich = 0;

	/** Nombre original del fichero. */
	private String nombreOrgFich = null;

	/** Extensión del fichero. */
	private String extFich = null;

	/** Identificador del fichero. */
	private String idFich = null;

	/** Estado del clasificador. */
	private int estado = -1;

	/** Descripción del clasificador. */
	private String descripcion = null;

	/** Identificador del objeto que contiene el documento. */
	private String idObjeto = null;

	/** Tipo de objeto que contiene el documento. */
	private int tipoObjeto = TipoObjeto.DESCRIPTOR;

	/** Fichero del documento. */
	private FormFile fichero = null;

	/** Nombre del depósito electrónico. */
	private String nombreDeposito = null;

	private String idRepEcm = null;

	/**
	 * Constructor.
	 */
	public DocumentoForm() {
		super();
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * @return Returns the idClfPadre.
	 */
	public String getIdClfPadre() {
		return idClfPadre;
	}

	/**
	 * @param idClfPadre
	 *            The idClfPadre to set.
	 */
	public void setIdClfPadre(String idClfPadre) {
		this.idClfPadre = idClfPadre;
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
	 * @return Returns the idObjeto.
	 */
	public String getIdObjeto() {
		return idObjeto;
	}

	/**
	 * @param idObjeto
	 *            The idObjeto to set.
	 */
	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
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
	 * @return Returns the fichero.
	 */
	public FormFile getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *            The fichero to set.
	 */
	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return Returns the tipoObjeto.
	 */
	public int getTipoObjeto() {
		return tipoObjeto;
	}

	/**
	 * @param tipoObjeto
	 *            The tipoObjeto to set.
	 */
	public void setTipoObjeto(int tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	/**
	 * @return Returns the nombreDeposito.
	 */
	public String getNombreDeposito() {
		return nombreDeposito;
	}

	/**
	 * @param nombreDeposito
	 *            The nombreDeposito to set.
	 */
	public void setNombreDeposito(String nombreDeposito) {
		this.nombreDeposito = nombreDeposito;
	}

	/**
	 * Construye un VO con la información del formulario.
	 *
	 * @param vo
	 *            Value Object.
	 */
	public void populate(DocDocumentoVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		// vo.setIdClfPadre(this.idClfPadre);

		// Añadido porque Postgre no reconoce '' como null
		if (this.idClfPadre.equals(Constants.STRING_EMPTY))
			vo.setIdClfPadre(null);
		else
			vo.setIdClfPadre(this.idClfPadre);

		vo.setIdObjeto(this.idObjeto);
		vo.setTipoObjeto(this.tipoObjeto);
		vo.setTamanoFich(this.tamanoFich);
		vo.setNombreOrgFich(this.nombreOrgFich);
		vo.setExtFich(this.extFich);
		vo.setIdFich(this.idFich);
		vo.setEstado(this.estado);
		vo.setDescripcion(this.descripcion);
		vo.setIdRepEcm(this.idRepEcm);
	}

	/**
	 * Construye un VO con la información del formulario.
	 *
	 * @param vo
	 *            Value Object.
	 * @throws IOException
	 *             si ocurre algún error con el fichero.
	 */
	public void populate(DocDocumentoExtVO vo) throws IOException {
		populate((DocDocumentoVO) vo);

		if ((fichero != null) && StringUtils.isNotBlank(fichero.getFileName())) {
			String fullFileName = fichero.getFileName();

			String nombre = FilenameUtils.getBaseName(fullFileName);
			String extension = FilenameUtils.getExtension(fullFileName);

			vo.setNombreOrgFich(nombre);
			vo.setExtFich(extension);
			vo.setTamanoFich(fichero.getFileSize());
			vo.setContenido(fichero.getFileData());
		}

	}

	/**
	 * Coge la información del VO.
	 *
	 * @param vo
	 *            Value Object.
	 */
	public void set(DocDocumentoVO vo) {
		if (vo != null) {
			setId(vo.getId());
			setNombre(vo.getNombre());
			setIdClfPadre(vo.getIdClfPadre());
			setIdObjeto(vo.getIdObjeto());
			setTipoObjeto(vo.getTipoObjeto());
			setTamanoFich(vo.getTamanoFich());
			setNombreOrgFich(vo.getNombreOrgFich());
			setExtFich(vo.getExtFich());
			setIdFich(vo.getIdFich());
			setEstado(vo.getEstado());
			setDescripcion(vo.getDescripcion());
			setNombreDeposito(vo.getNombreDeposito());
			setIdRepEcm(vo.getIdRepEcm());
		}
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
									DocumentosConstants.LABEL_DOCUMENTOS_DOCUMENTO_NOMBRE,
									request.getLocale())));
		}

		if (StringUtils.isBlank(id)
				&& ((fichero == null) || StringUtils.isBlank(fichero
						.getFileName()))) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosConstants.LABEL_DOCUMENTOS_DOCUMENTO_NUEVO_DOC,
									request.getLocale())));
		} else {
			String fullFileName = fichero.getFileName();

			if (StringUtils.isNotBlank(fullFileName)) {

				String nombre = FilenameUtils.getName(fullFileName);
				String extension = FilenameUtils.getExtension(fullFileName);

				if (StringUtils.isBlank(nombre)) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							DocumentosConstants.ERROR_NOMBRE_FICHERO_INVALIDO));
				}

				if (StringUtils.isBlank(extension)
						|| extension.length() > DocumentosConstants.LONGITUD_MAX_EXTENSION

				) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosConstants.ERROR_EXTENSION_FICHERO_INVALIDA));
				}
			}
		}

		return errors;
	}

	public boolean getEsImagen() {
		if (StringUtils.isNotBlank(extFich)) {
			for (int i = 0; i < DocumentosConstants.IMAGE_EXTENSIONS.length; i++) {
				if (DocumentosConstants.IMAGE_EXTENSIONS[i]
						.equalsIgnoreCase(extFich))
					return true;
			}
		}
		return false;
	}

	/**
	 * @param idRepEcm
	 *            el objeto idRepEcm a fijar
	 */
	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	/**
	 * @return el objeto idRepEcm
	 */
	public String getIdRepEcm() {
		return idRepEcm;
	}
}
