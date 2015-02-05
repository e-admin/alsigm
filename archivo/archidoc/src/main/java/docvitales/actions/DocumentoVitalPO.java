package docvitales.actions;

import java.util.Arrays;

import common.util.StringUtils;

import docelectronicos.DocumentosConstants;
import docvitales.vos.DocumentoVitalExtVO;

/**
 * Información de presentacion de los documentos vitales.
 * 
 * @see DocumentoVitalExtVO
 */
public class DocumentoVitalPO extends DocumentoVitalExtVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DocumentoVitalPO() {
		super();
	}

	/**
	 * Indica si el documento vital es una imagen.
	 * 
	 * @return Si el documento vital es una imagen.
	 */
	public boolean isImagen() {
		boolean returnValue = false;
		if (StringUtils.isNotBlank(getExtFich()))
			returnValue = Arrays.binarySearch(
					DocumentosConstants.IMAGE_EXTENSIONS, getExtFich()
							.toUpperCase()) >= 0;
		return returnValue;
	}

	/**
	 * Obtiene el nombre completo del fichero.
	 * 
	 * @return Nombre completo del fichero.
	 */
	public String getNombreCompletoFichero() {
		StringBuffer fileName = new StringBuffer();

		if (StringUtils.isNotBlank(getNombreOrgFich()))
			fileName.append(getNombreOrgFich());
		else
			fileName.append("unknown");

		if (StringUtils.isNotBlank(getExtFich()))
			fileName.append(".").append(getExtFich());

		return fileName.toString();
	}

}
