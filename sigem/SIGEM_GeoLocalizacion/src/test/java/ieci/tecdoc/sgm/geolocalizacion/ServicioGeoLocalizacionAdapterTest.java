/**
 * 
 */
package ieci.tecdoc.sgm.geolocalizacion;

import ieci.tecdoc.sgm.core.services.geolocalizacion.GeoLocalizacionServicioException;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Mapas;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Municipios;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Provincias;
import ieci.tecdoc.sgm.core.services.geolocalizacion.TiposVia;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Vias;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ServicioGeoLocalizacionAdapterTest extends TestCase {

	private static final int ID_ENTIDAD_MUNICIPAL = 3; // Entidad que engloba a Cuenca
	private static final int CODIGO_INE_PROVINCIA = 16; // Cuenca
	private static final int CODIGO_INE_MUNICIPIO = 16078; // Cuenca
	private static final int IDENTIFICADOR_VIA = 7556; // SOLANA

	private ServicioGeoLocalizacionAdapter servicio = new ServicioGeoLocalizacionAdapter();

	public void testValidarVia() throws GeoLocalizacionServicioException {
		Vias vias = servicio.validarVia("SOLANA", CODIGO_INE_MUNICIPIO);
		Assert.assertNotNull(vias);
		Assert.assertEquals(1, vias.count());
		Assert.assertEquals(IDENTIFICADOR_VIA, ((Via) vias.get(0)).getIdVia());

		vias = servicio.validarVia("DEL ROMERO", CODIGO_INE_MUNICIPIO);
		Assert.assertNotNull(vias);
		Assert.assertEquals(1, vias.count());
	}

	public void testObtenerProvincias() throws GeoLocalizacionServicioException {

		Provincias provincias = servicio.obtenerProvincias();
		Assert.assertNotNull("No se ha encontrado ninguna provincia",
				provincias);
		Assert.assertTrue("No se ha encontrado ninguna provincia",
				provincias.count() > 0);
	}

	public void testObtenerMunicipios() throws GeoLocalizacionServicioException {
		Municipios municipios = servicio
				.obtenerMunicipios(CODIGO_INE_PROVINCIA);
		Assert.assertNotNull("No se ha encontrado ningún municipio", municipios);
		Assert.assertTrue("No se ha encontrado ningún municipio",
				municipios.count() > 0);
	}

	public void testObtenerTiposDeVia() throws GeoLocalizacionServicioException {

		TiposVia tiposVia = servicio.obtenerTiposDeVia();
		Assert.assertNotNull("No se ha encontrado ningún tipo de vía", tiposVia);
		Assert.assertTrue("No se ha encontrado ningún tipo de vía",
				tiposVia.count() > 0);
	}

	public void testVerPlanosPublicados() throws GeoLocalizacionServicioException {

		Mapas planos = servicio.verPlanosPublicados(ID_ENTIDAD_MUNICIPAL);
		Assert.assertNotNull("No se ha encontrado ningún plano", planos);
		Assert.assertTrue("No se ha encontrado ningún plano",
				planos.count() > 0);
	}

}
