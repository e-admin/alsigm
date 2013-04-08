package es.ieci.tecdoc.fwktd.dm.invesdoc.service.impl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;


/**
 * Clase que encapsula el identificador de documentos
 * en invesDoc.
 *
 * Esta clase obtiene los valores de carpeta, documento
 * y página que describen el identificador del documento.
 * El identificador viene dado como un string con el
 * siguiente formato:
 *
 * <guid><archive>1</archive><folder>1</folder><document>1</document></guid>
 *
 */
public class InvesdocUid implements Serializable {

	/**
	 * UID de versión.
	 */
	private static final long serialVersionUID = 8073311754729762330L;

	protected int archive = 0;
	protected int folder = 0;
	protected int document = 0;


	/**
	 * Constructor.
	 * @param uid
	 */
	public InvesdocUid() {
		super();
	}

	/**
	 * Constructor.
	 * @param uid UID del documento.
	 * @throws DocumentException si ocurre algún error al parsear el UID.
	 */
	public InvesdocUid(String uid) throws DocumentException {
		this();
		parseUid(uid);
	}

	/**
	 * Constructor.
	 * @param archive Identificador del archivador.
	 * @param folder Identificador de la carpeta.
	 * @param document Identificador del documento.
	 * @throws DocumentException si ocurre algún error al parsear el UID.
	 */
	public InvesdocUid(int archive, int folder, int document) throws DocumentException {
		this();
		setArchive(archive);
		setFolder(folder);
		setDocument(document);
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public int getFolder() {
		return folder;
	}

	public void setFolder(int folder) {
		this.folder = folder;
	}

	public int getDocument() {
		return document;
	}

	public void setDocument(int document) {
		this.document = document;
	}

	public void parseUid(String uid) throws DocumentException {
		if (StringUtils.isNotBlank(uid)) {
			Document doc = DocumentHelper.parseText(uid);
			archive = Integer.parseInt(doc.valueOf("/guid/archive"));
			folder = Integer.parseInt(doc.valueOf("/guid/folder"));
			document = Integer.parseInt(doc.valueOf("/guid/document"));
		}
	}

	public String toUid() {
		return new StringBuffer()
			.append("<guid><archive>")
			.append(getArchive())
			.append("</archive><folder>")
			.append(getFolder())
			.append("</folder><document>")
			.append(getDocument())
			.append("</document></guid>")
			.toString();
	}
}
