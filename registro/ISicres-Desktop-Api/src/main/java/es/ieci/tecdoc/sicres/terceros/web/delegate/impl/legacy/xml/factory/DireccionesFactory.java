package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Direccion;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Direcciones;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilio;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Domicilios;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.EDireccion;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.EDirecciones;

/**
 *
 * @author IECISA
 *
 */
public class DireccionesFactory {

	/**
	 *
	 * @param direcciones
	 * @return
	 */
	public List<BaseDireccionVO> createDirecciones(Direcciones direcciones) {
		List<BaseDireccionVO> dirs = new ArrayList<BaseDireccionVO>();

		for (Direccion direccion : direcciones.getDirecciones()) {
			if (direccion instanceof Domicilio) {
				dirs.add(createDireccionFisica((Domicilio) direccion));
			} else if (direccion instanceof EDireccion) {
				dirs.add(createDireccionTelematica((EDireccion) direccion));
			}
		}

		return dirs;
	}

	/**
	 *
	 * @param domicilios
	 * @return
	 */
	public List<DireccionFisicaVO> createDireccionesFisicas(
			Domicilios domicilios) {
		List<DireccionFisicaVO> direcciones = new ArrayList<DireccionFisicaVO>();

		for (Domicilio domicilio : domicilios.getDomicilios()) {
			DireccionFisicaVO direccion = createDireccionFisica(domicilio);

			direcciones.add(direccion);
		}

		return direcciones;
	}

	/**
	 *
	 * @param domicilio
	 * @return
	 */
	public DireccionFisicaVO createDireccionFisica(Domicilio domicilio) {
		DireccionFisicaVO direccion = new DireccionFisicaVO();
		if (!StringUtils.isEmpty(domicilio.getId())) {
			direccion.setId(domicilio.getId());
		}
		if (!StringUtils.isEmpty(domicilio.getDireccion())) {
			direccion.setDireccion(domicilio.getDireccion());
		}
		if (!StringUtils.isEmpty(domicilio.getPoblacion())) {
			CiudadVO ciudad = new CiudadVO();
			ciudad.setNombre(domicilio.getPoblacion());
			direccion.setCiudad(ciudad);
		}
		if (!StringUtils.isEmpty(domicilio.getCodigoPostal())) {
			direccion.setCodigoPostal(domicilio.getCodigoPostal());
		}
		if (!StringUtils.isEmpty(domicilio.getProvincia())) {
			ProvinciaVO provincia = new ProvinciaVO();
			provincia.setNombre(domicilio.getProvincia());
			direccion.setProvincia(provincia);
		}
		if (!StringUtils.isEmpty(domicilio.getPreferencia())) {
			if ("1".equals(domicilio.getPreferencia())) {
				direccion.setPrincipal(true);
			}
		}
		return direccion;
	}

	/**
	 *
	 * @param edirecciones
	 * @return
	 */
	public List<DireccionTelematicaVO> createDireccionesTelematicas(
			EDirecciones edirecciones) {
		List<DireccionTelematicaVO> direcciones = new ArrayList<DireccionTelematicaVO>();

		for (EDireccion edireccion : edirecciones.getEdirecciones()) {
			DireccionTelematicaVO direccion = createDireccionTelematica(edireccion);

			direcciones.add(direccion);
		}

		return direcciones;
	}

	/**
	 *
	 * @param edireccion
	 * @return
	 */
	public DireccionTelematicaVO createDireccionTelematica(EDireccion edireccion) {
		DireccionTelematicaVO direccion = new DireccionTelematicaVO();
		if (!StringUtils.isEmpty(edireccion.getId())) {
			direccion.setId(edireccion.getId());
		}
		if (!StringUtils.isEmpty(edireccion.getDireccion())) {
			direccion.setDireccion(edireccion.getDireccion());
		}
		if (!StringUtils.isEmpty(edireccion.getTipo())) {
			TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
			tipoDireccionTelematica.setId(edireccion.getTipo());

			direccion.setTipoDireccionTelematica(tipoDireccionTelematica);
		}
		if (!StringUtils.isEmpty(edireccion.getPreferencia())) {
			if ("1".equals(edireccion.getPreferencia())) {
				direccion.setPrincipal(true);
			}
		}
		return direccion;
	}
}
