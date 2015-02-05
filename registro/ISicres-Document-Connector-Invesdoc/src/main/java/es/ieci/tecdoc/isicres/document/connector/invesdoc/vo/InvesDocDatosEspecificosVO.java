/**
 * 
 */
package es.ieci.tecdoc.isicres.document.connector.invesdoc.vo;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresDatosEspecificos;

/**
 * @author 66575267
 * 
 * 
 * Este VO contiene los datos especificos necesarios para la implementacion del
 * conector de repositorio documental con invesdoc
 */
public class InvesDocDatosEspecificosVO implements ISicresDatosEspecificos {

	private String entidad;
	private String extension;

	private int listId;
	private int bookId;
	private int fdrid;
	private int docId;

	public void fromXml(String xml) {
		// TODO Auto-generated method stub
	}

	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<DatosEspecificos>");

		sb.append("<Entidad>").append(entidad).append("</entidad>");
		sb.append("<ListId>").append(listId).append("</ListId>");
		sb.append("<BookId>").append(bookId).append("</BookId>");
		sb.append("<FolderId>").append(fdrid).append("</FolderId>");
		sb.append("<DocumentId>").append(docId).append("</DocumentId>");
		sb.append("<Extension>").append(extension).append("</Extension>");

		sb.append("</DatosEspecificos>");
		return sb.toString();
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the listId
	 */
	public int getListId() {
		return listId;
	}

	/**
	 * @param listId
	 *            the listId to set
	 */
	public void setListId(int listId) {
		this.listId = listId;
	}

	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the fdrid
	 */
	public int getFdrid() {
		return fdrid;
	}

	/**
	 * @param fdrid
	 *            the fdrid to set
	 */
	public void setFdrid(int fdrid) {
		this.fdrid = fdrid;
	}

	/**
	 * @return the docId
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

}
