package docelectronicos.vos;

import java.util.Arrays;

import common.Constants;
import common.util.StringUtils;

/**
 * Información del fichero de un documento electrónico.
 */
public class DocDocumentoExtVO extends DocDocumentoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Contenido del fichero. */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public DocDocumentoExtVO() {
		super();
	}

	/**
	 * @return Returns the contenido.
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * @param contenido
	 *            The contenido to set.
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getFileName() {
		StringBuffer fileName = new StringBuffer().append(getNombreOrgFich());

		if (StringUtils.isNotBlank(getExtFich()))
			fileName.append(Constants.SEPARADOR_EXTENSION_FICHERO).append(
					getExtFich());

		return fileName.toString();
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DocDocumentoExtVO ["
				+ ", getFileName()=" + getFileName() + ", getNombreDeposito()="
				+ getNombreDeposito() + ", getDescripcion()="
				+ getDescripcion() + ", getEstado()=" + getEstado()
				+ ", getId()=" + getId() + ", getIdClfPadre()="
				+ getIdClfPadre() + ", getNombre()=" + getNombre()
				+ ", getExtFich()=" + getExtFich() + ", getIdFich()="
				+ getIdFich() + ", getNombreOrgFich()=" + getNombreOrgFich()
				+ ", getTamanoFich()=" + getTamanoFich() + ", getIdObjeto()="
				+ getIdObjeto() + ", getTipoObjeto()=" + getTipoObjeto()
				+ ", getIdExtDeposito()=" + getIdExtDeposito()
				+ ", childItems()=" + childItems() + ", isLeaf()=" + isLeaf()
				+ ", getIdRepEcm()=" + getIdRepEcm() + ", getItemId()="
				+ getItemId() + ", getItemName()=" + getItemName()
				+ ", getParent()=" + getParent() + ", getNodeType()="
				+ getNodeType() + ", getItemPath()=" + getItemPath()
				+ ", toString()=" + super.toString() + ", toXML()=" + toXML()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}


}
