package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class DocumentoRegistroVO extends BaseDocumentoVO {

	private static final long serialVersionUID = -5076577710514579315L;

	private static final String DEFAULT_DOCUMENT_NAME = "Documento";

	/**
	 * identificador del registro al que pertenece el documento
	 */
	protected IdentificadorRegistroVO idRegistro;

	/**
	 * nombre lógico del documento
	 */
	protected String name;

	/**
	 * lista de tipo <code>PaginaDocumentoRegistroVO<code>
	 */
	protected List paginas;

	public DocumentoRegistroVO() {
		paginas = new ArrayList();
	}

	public List getPaginas() {
		return paginas;
	}

	public void setPaginas(List paginas) {
		this.paginas = paginas;
	}

	public IdentificadorRegistroVO getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(IdentificadorRegistroVO idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getName() {
		if (StringUtils.isEmpty(this.name)) {
			return DEFAULT_DOCUMENT_NAME;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
