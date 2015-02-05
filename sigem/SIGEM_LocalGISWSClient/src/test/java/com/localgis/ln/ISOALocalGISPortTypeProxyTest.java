/**
 * 
 */
package com.localgis.ln;

import ieci.tecdoc.sgm.localizador.hadler.HandlerLocalizador;

import java.net.URL;
import java.rmi.RemoteException;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.localgis.model.ot.ArrayOfMunicipioOT;
import com.localgis.model.ot.ArrayOfParcelaOT;
import com.localgis.model.ot.ArrayOfProvinciaOT;
import com.localgis.model.ot.ArrayOfTipoViaOT;
import com.localgis.web.core.model.ArrayOfLocalgisMap;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ISOALocalGISPortTypeProxyTest extends TestCase {

	private static final String SERVICE_URL = "http://localgis.mityc.es:8080/SOALocalGIS/services/ISOALocalGIS";
	private static final int ID_ENTIDAD = 3; // Entidad que engloba a Cuenca
	private static final int CODIGO_INE_PROVINCIA = 16; // Cuenca
	private static final int CODIGO_INE_MUNICIPIO = 16078; // Cuenca
	private static final String REFERENCIA_CATASTRAL = "3747819WK7334F";

	public ISOALocalGISPortType getISOALocalGISPortType() {

		try {
			ISOALocalGISLocator locator = new ISOALocalGISLocator();
			ISOALocalGISHttpBindingStub stub = (ISOALocalGISHttpBindingStub) locator
					.getISOALocalGISHttpPort(new URL(SERVICE_URL));
			stub.setHandler(new HandlerLocalizador("UsernameToken",
					"syssuperuser", "sysgeopass", "PasswordText"));

			return stub;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void testValidarReferencia() throws RemoteException {
		Assert.assertTrue(
				"No se ha validado la referencia catastral",
				getISOALocalGISPortType().validarReferencia(
						REFERENCIA_CATASTRAL));
		Assert.assertFalse(getISOALocalGISPortType().validarReferencia("MIREF"));
	}
	
	public void testConsultarCatastro() throws RemoteException {
		ArrayOfParcelaOT parcelas = getISOALocalGISPortType()
				.consultarCatastro(REFERENCIA_CATASTRAL);
		Assert.assertNotNull("No se ha encontrado ninguna parcela", parcelas);
		Assert.assertNotNull("No se ha encontrado ninguna parcela",
				parcelas.getParcelaOT());
		Assert.assertTrue("No se ha encontrado ninguna parcela",
				parcelas.getParcelaOT().length > 0);
	}

	public void testObtenerProvincias() throws RemoteException {

		ArrayOfProvinciaOT provincias = getISOALocalGISPortType()
				.obtenerProvincias();
		Assert.assertNotNull("No se ha encontrado ninguna provincia",
				provincias);
		Assert.assertNotNull("No se ha encontrado ninguna provincia",
				provincias.getProvinciaOT());
		Assert.assertTrue("No se ha encontrado ninguna provincia",
				provincias.getProvinciaOT().length > 0);
	}

	public void testObtenerMunicipios() throws RemoteException {
		ArrayOfMunicipioOT municipios = getISOALocalGISPortType()
				.obtenerMunicipios(CODIGO_INE_PROVINCIA);
		Assert.assertNotNull("No se ha encontrado ningún municipio", municipios);
		Assert.assertNotNull("No se ha encontrado ningún municipio",
				municipios.getMunicipioOT());
		Assert.assertTrue("No se ha encontrado ningún municipio",
				municipios.getMunicipioOT().length > 0);
	}

	public void testObtenerTiposDeVia() throws RemoteException {

		ArrayOfTipoViaOT tiposVia = getISOALocalGISPortType()
				.obtenerTiposDeVia();
		Assert.assertNotNull("No se ha encontrado ningún tipo de vía", tiposVia);
		Assert.assertNotNull("No se ha encontrado ningún tipo de vía",
				tiposVia.getTipoViaOT());
		Assert.assertTrue("No se ha encontrado ningún tipo de vía",
				tiposVia.getTipoViaOT().length > 0);
	}

	public void testVerPlanosPublicados() throws RemoteException {
		
		ArrayOfLocalgisMap planos = getISOALocalGISPortType()
				.verPlanosPublicados(ID_ENTIDAD);
		Assert.assertNotNull("No se ha encontrado ningún plano publicado",
				planos);
		Assert.assertNotNull("No se ha encontrado ningún plano publicado",
				planos.getLocalgisMap());
		Assert.assertTrue("No se ha encontrado ningún plano publicado",
				planos.getLocalgisMap().length > 0);
	}

	public void testVerPlanoPorReferenciaCatastral() {
	}

	public void testVerPlanoPorIdNumeroPolicia() {
	}

	public void testVerPlanoPorIdVia() {
	}

	public void testVerPlanoPorCoordenadas() {
	}

}
