package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentos {

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public void addTipoDocumento(TipoDocumento tipoDocumento) {
		if (null == getTipoDocumentos()) {
			setTipoDocumentos(new ArrayList<TipoDocumento>());
		}
		getTipoDocumentos().add(tipoDocumento);
	}

	protected List<TipoDocumento> tipoDocumentos;
}
