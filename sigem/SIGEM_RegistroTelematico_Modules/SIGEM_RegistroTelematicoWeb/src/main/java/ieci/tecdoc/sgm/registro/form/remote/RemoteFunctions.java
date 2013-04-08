package ieci.tecdoc.sgm.registro.form.remote;

import ieci.tecdoc.sgm.autenticacion.util.utilities.TipoDocumento;
import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.registro.form.remote.vo.DocumentoIdentidadVO;
import ieci.tecdoc.sgm.registro.form.remote.vo.ParametrosBaseVO;
import ieci.tecdoc.sgm.registro.form.remote.vo.RetornoVO;

import org.apache.commons.lang.StringUtils;

public class RemoteFunctions {

	/**
	 * Ejemplo de validación remota que devuelve OK.
	 *
	 * @param parametros
	 *            Entidad y locale para los mensajes.
	 * @return Retorno de la validación, indicando si no hay error, y si hay
	 *         error, incluye el mensaje de error.
	 */
	public RetornoVO exampleValidateOK(ParametrosBaseVO parametros) {

		RetornoVO retorno = new RetornoVO();
		retorno.setError(false);

		return retorno;
	}

	/**
	 * Ejemplo de validación remota que devuelve error.
	 *
	 * @param parametros
	 *            Entidad y locale para los mensajes.
	 * @return Retorno de la validación, indicando si no hay error, y si hay
	 *         error, incluye el mensaje de error.
	 */
	public RetornoVO exampleValidateError(ParametrosBaseVO parametros) {

		RetornoVO retorno = new RetornoVO();
		retorno.setError(true);
		retorno.setMessage("Error de validaci\u00f3n, revise el formulario.");

		return retorno;
	}

	/**
	 * Validación del documento de identidad.
	 *
	 * @param parametros
	 *            Documento de identidad a validar, que también incluye la
	 *            entidad y locale para los mensajes.
	 * @return Retorno de la validación, indicando si no hay error, y si hay
	 *         error, incluye el mensaje de error.
	 */
	public RetornoVO validateDocumentoIdentidad(DocumentoIdentidadVO parametros) {

		RetornoVO retorno = new RetornoVO();
		retorno.setError(false);

		if ((parametros != null)
				&& (StringUtils.isNotBlank(parametros.getDocumentoIdentidad()))) {

			int docType = Validador.validateDocumentType(parametros
					.getDocumentoIdentidad());
			if (docType == TipoDocumento.DOC_ERROR) {

				retorno.setError(true);
				retorno.setMessage("El Documento de Identidad '"
						+ parametros.getDocumentoIdentidad()
						+ "' no es v\u00e1lido.");
			}
		}

		return retorno;
	}

}
