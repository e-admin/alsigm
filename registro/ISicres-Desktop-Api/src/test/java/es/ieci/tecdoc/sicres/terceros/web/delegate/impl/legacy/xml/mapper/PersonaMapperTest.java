package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.PersonaMapper;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo.Persona;

/**
 *
 * @author IECISA
 *
 */
public class PersonaMapperTest extends TestCase {

	public void testUnmarshall() throws Exception {
		PersonaMapper mapper = new PersonaMapper();
		String xml = FileUtils
				.readFileToString(new File(
						getClass()
								.getResource(
										"/es/ieci/tecdoc/sicres/terceros/web/delegate/impl/legacy/xml/mapper/persona.xml")
								.toURI()));

		Persona persona = (Persona) mapper.unmarshall(xml);

		Assert.assertNotNull(persona);
		Assert.assertEquals(1, persona.getDomicilios().getDomicilios().size());
		Assert.assertEquals(2, persona.getEdirecciones().getEdirecciones()
				.size());
	}

	public void testMarshallTerceroFisico() throws Exception {
		TerceroValidadoFisicoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");
		tercero.setNombre("nombre");
		tercero.setApellido1("apellido1");
		tercero.setApellido2("apellido2");
		tercero.setNumeroDocumento("12345678Z");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("2");
		tercero.setTipoDocumento(tipoDocumento);

		DireccionFisicaVO direccionFisica = new DireccionFisicaVO();
		direccionFisica.setId("1");
		CiudadVO ciudad1 = new CiudadVO();
		ciudad1.setNombre("ciudad");
		direccionFisica.setCiudad(ciudad1);
		direccionFisica.setCodigoPostal("12345");
		direccionFisica.setDireccion("direccion");
		ProvinciaVO provincia1 = new ProvinciaVO();
		provincia1.setNombre("provincia");
		direccionFisica.setProvincia(provincia1);
		direccionFisica.setPrincipal(true);

		DireccionFisicaVO direccionFisica2 = new DireccionFisicaVO();
		direccionFisica2.setId("2");
		CiudadVO ciudad2 = new CiudadVO();
		ciudad2.setNombre("ciudad2");
		direccionFisica2.setCiudad(ciudad2);
		direccionFisica2.setCodigoPostal("12345");
		direccionFisica2.setDireccion("direccion2");
		ProvinciaVO provincia2 = new ProvinciaVO();
		provincia2.setNombre("provincia2");
		direccionFisica2.setProvincia(provincia2);
		direccionFisica2.setPrincipal(false);

		tercero.getDirecciones().add(direccionFisica);
		tercero.getDirecciones().add(direccionFisica2);

		DireccionTelematicaVO direccionTelematica = new DireccionTelematicaVO();
		direccionTelematica.setId("3");
		direccionTelematica.setDireccion("direccionTelematica");
		direccionTelematica.setPrincipal(true);
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("1");
		direccionTelematica.setTipoDireccionTelematica(tipoDireccionTelematica);

		tercero.getDirecciones().add(direccionTelematica);

		String xml = new PersonaMapper().marshall(tercero, null);

		Assert.assertNotNull(xml);
	}

	public void testMarshallTerceroJuridico() throws Exception {
		TerceroValidadoJuridicoVO tercero = new TerceroValidadoJuridicoVO();
		tercero.setId("1");
		tercero.setNombre("nombre");
		tercero.setNumeroDocumento("12345678Z");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("2");
		tercero.setTipoDocumento(tipoDocumento);

		DireccionFisicaVO direccionFisica = new DireccionFisicaVO();
		direccionFisica.setId("1");
		CiudadVO ciudad = new CiudadVO();
		ciudad.setNombre("ciudad");
		direccionFisica.setCiudad(ciudad);
		direccionFisica.setCodigoPostal("12345");
		direccionFisica.setDireccion("direccion");
		ProvinciaVO provincia = new ProvinciaVO();
		provincia.setNombre("provincia");
		direccionFisica.setProvincia(provincia);
		direccionFisica.setPrincipal(true);

		DireccionFisicaVO direccionFisica2 = new DireccionFisicaVO();
		direccionFisica2.setId("2");
		CiudadVO ciudad2 = new CiudadVO();
		ciudad2.setNombre("ciudad2");
		direccionFisica2.setCodigoPostal("12345");
		direccionFisica2.setDireccion("direccion2");
		ProvinciaVO provincia2 = new ProvinciaVO();
		provincia2.setNombre("provincia2");
		direccionFisica2.setProvincia(provincia2);
		direccionFisica2.setPrincipal(false);

		tercero.getDirecciones().add(direccionFisica);
		tercero.getDirecciones().add(direccionFisica2);

		DireccionTelematicaVO direccionTelematica = new DireccionTelematicaVO();
		direccionTelematica.setId("3");
		direccionTelematica.setDireccion("direccionTelematica");
		direccionTelematica.setPrincipal(true);
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("1");
		direccionTelematica.setTipoDireccionTelematica(tipoDireccionTelematica);

		tercero.getDirecciones().add(direccionTelematica);

		String xml = new PersonaMapper().marshall(tercero, null);

		Assert.assertNotNull(xml);
	}

	public void testMarshallTerceroJuridicoNoCIF() throws Exception {
		TerceroValidadoJuridicoVO tercero = new TerceroValidadoJuridicoVO();
		tercero.setId("1");
		tercero.setNombre("nombre");
		tercero.setNumeroDocumento("12345678Z");

		DireccionFisicaVO direccionFisica = new DireccionFisicaVO();
		direccionFisica.setId("1");
		CiudadVO ciudad1 = new CiudadVO();
		ciudad1.setNombre("ciudad");
		direccionFisica.setCiudad(ciudad1);
		direccionFisica.setCodigoPostal("12345");
		direccionFisica.setDireccion("direccion");
		ProvinciaVO provincia = new ProvinciaVO();
		provincia.setNombre("provincia");
		direccionFisica.setProvincia(provincia);
		direccionFisica.setPrincipal(true);

		DireccionFisicaVO direccionFisica2 = new DireccionFisicaVO();
		direccionFisica2.setId("2");
		CiudadVO ciudad2 = new CiudadVO();
		ciudad2.setNombre("ciudad2");
		direccionFisica2.setCiudad(ciudad2);
		direccionFisica2.setCodigoPostal("12345");
		direccionFisica2.setDireccion("direccion2");
		ProvinciaVO provincia2 = new ProvinciaVO();
		provincia2.setNombre("provincia2");
		direccionFisica2.setProvincia(provincia2);
		direccionFisica2.setPrincipal(false);

		tercero.getDirecciones().add(direccionFisica);
		tercero.getDirecciones().add(direccionFisica2);

		DireccionTelematicaVO direccionTelematica = new DireccionTelematicaVO();
		direccionTelematica.setId("3");
		direccionTelematica.setDireccion("direccionTelematica");
		direccionTelematica.setPrincipal(true);
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("1");
		direccionTelematica.setTipoDireccionTelematica(tipoDireccionTelematica);

		tercero.getDirecciones().add(direccionTelematica);

		String xml = new PersonaMapper().marshall(tercero, null);

		Assert.assertNotNull(xml);
	}
}