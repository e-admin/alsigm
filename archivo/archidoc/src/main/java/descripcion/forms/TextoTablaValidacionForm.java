package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.bi.GestionDescripcionBI;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.vos.TextoTablaValidacionVO;

/**
 * Formulario para la información de un texto de una tabla de validación.
 */
public class TextoTablaValidacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String valor = null;
	private String idTblVld = null;
	private String nombreTblVld = null;

	/**
	 * Constructor.
	 */
	public TextoTablaValidacionForm() {
		super();
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
	 * @return Returns the idTblVld.
	 */
	public String getIdTblVld() {
		return idTblVld;
	}

	/**
	 * @param idTblVld
	 *            The idTblVld to set.
	 */
	public void setIdTblVld(String idTblVld) {
		this.idTblVld = idTblVld;
	}

	/**
	 * @return Returns the valor.
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            The valor to set.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return Returns the nombreTblVld.
	 */
	public String getNombreTblVld() {
		return nombreTblVld;
	}

	/**
	 * @param nombreTblVld
	 *            The nombreTblVld to set.
	 */
	public void setNombreTblVld(String nombreTblVld) {
		this.nombreTblVld = nombreTblVld;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(TextoTablaValidacionVO vo) {
		vo.setId(this.id);
		vo.setValor(this.valor);
		vo.setIdTblVld(this.idTblVld);
		vo.setNombreTblVld(this.nombreTblVld);
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(TextoTablaValidacionVO vo) {
		setId(vo.getId());
		setValor(vo.getValor());
		setIdTblVld(vo.getIdTblVld());
		setNombreTblVld(vo.getNombreTblVld());
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
			HttpServletRequest request, GestionDescripcionBI descripcionBI) {
		ActionErrors errors = new ActionErrors();

		// Nombre
		if (StringUtils.isBlank(valor)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(ArchivoDetails.DESCRIPCION_CAMPO_VALOR,
									request.getLocale())));
		}

		// ya he existe dicho nombre de valor
		if (descripcionBI != null) {
			TextoTablaValidacionVO textoTblvld = descripcionBI
					.getValorTablaValidacionByValor(valor, idTblVld);
			if (textoTblvld != null
					&& (StringUtils.isBlank(id) || !textoTblvld.getId().equals(
							id))) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_CREAR_ELEMENTO_YA_EXISTE));
			}
		}

		return errors;
	}

}
