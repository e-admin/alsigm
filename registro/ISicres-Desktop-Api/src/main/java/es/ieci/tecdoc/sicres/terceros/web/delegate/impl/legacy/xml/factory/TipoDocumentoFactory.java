package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDocumento;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.TipoDocumentos;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentoFactory {

	/**
	 *
	 * @param tipoDocumentos
	 * @return
	 */
	public List<TipoDocumentoIdentificativoTerceroVO> createTipoDocumentos(
			TipoDocumentos tipoDocumentos) {
		List<TipoDocumentoIdentificativoTerceroVO> tipos = new ArrayList<TipoDocumentoIdentificativoTerceroVO>();

		for (TipoDocumento tipoDocumento : tipoDocumentos.getTipoDocumentos()) {
			TipoDocumentoIdentificativoTerceroVO tipo = new TipoDocumentoIdentificativoTerceroVO();

			if (!StringUtils.isEmpty(tipoDocumento.getId())) {
				tipo.setId(tipoDocumento.getId());
			}
			if (!StringUtils.isEmpty(tipoDocumento.getCodigo())) {
				tipo.setCodigo(tipoDocumento.getCodigo());
			}
			if (!StringUtils.isEmpty(tipoDocumento.getDescripcion())) {
				tipo.setDescripcion(tipoDocumento.getDescripcion());
			}

			tipos.add(tipo);
		}

		return tipos;
	}

//	public List<TipoDocumentoIdentificativoTerceroVO> createTipoDocumentos(TipoDocumentos tipoDocumentos,){
//
//	}
}
