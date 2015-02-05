package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Persona;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Personas;

/**
 *
 * @author IECISA
 *
 */
public class TercerosFactory {

	/**
	 *
	 * @param personas
	 * @return
	 */
	public List<TerceroValidadoVO> createTerceros(Personas personas) {
		List<TerceroValidadoVO> terceros = new ArrayList<TerceroValidadoVO>();

		for (Persona persona : personas.getPersonas()) {
			if ("1".equals(persona.getTipo())) {
				terceros.add(createTerceroFisico(persona));
			} else if ("2".equals(persona.getTipo())) {
				terceros.add(createTerceroJuridico(persona));
			}
		}
		return terceros;
	}

	/**
	 *
	 * @param persona
	 * @return
	 */
	public TerceroValidadoVO createTercero(Persona persona) {
		if ("".equals(persona.getTipo())) {
			return createTerceroFisico(persona);
		} else if ("".equals(persona.getTipo())) {
			return createTerceroJuridico(persona);
		}
		return null;
	}

	/**
	 *
	 * @param persona
	 * @return
	 */
	protected TerceroValidadoVO createTerceroJuridico(Persona persona) {
		TerceroValidadoJuridicoVO tercero = new TerceroValidadoJuridicoVO();
		tercero.setId(persona.getId());
		tercero.setNombre(persona.getNombre());
		tercero.setNumeroDocumento(persona.getNif());

		return tercero;
	}

	/**
	 *
	 * @param persona
	 * @return
	 */
	protected TerceroValidadoFisicoVO createTerceroFisico(Persona persona) {
		TerceroValidadoFisicoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId(persona.getId());
		tercero.setNombre(persona.getNombre());
		tercero.setApellido1(persona.getApellido1());
		tercero.setApellido2(persona.getApellido2());
		tercero.setNumeroDocumento(persona.getNif());

		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tercero.setTipoDocumento(tipoDocumento);

		return tercero;
	}
}
