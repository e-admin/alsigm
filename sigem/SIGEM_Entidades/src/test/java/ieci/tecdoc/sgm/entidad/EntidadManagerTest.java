package ieci.tecdoc.sgm.entidad;

import java.util.Iterator;
import java.util.List;

import ieci.tecdoc.sgm.entidades.EntidadesManager;
import ieci.tecdoc.sgm.entidades.beans.CriterioBusquedaEntidades;
import ieci.tecdoc.sgm.entidades.beans.Entidad;
import ieci.tecdoc.sgm.entidades.exception.EntidadException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class EntidadManagerTest extends TestCase {

	public void testNuevaEntidad() {
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador("00001");
		oEntidad.setNombreCorto("Talamanca de Jarama");
		oEntidad.setNombreLargo("Talamanca de Jarama");
		try {
			EntidadesManager.nuevaEntidad(oEntidad);
		} catch (EntidadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(true);
	}
	

	public void testEliminarEntidad() {
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador("00001");
		try {
			EntidadesManager.eliminarEntidad(oEntidad);
		} catch (EntidadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	public void testActualizarEntidad() {
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador("00002");
		oEntidad.setNombreCorto("Talamancus Jaraminensis");
		oEntidad.setNombreLargo("Talamancus Jaraminensis");		
		try {
			EntidadesManager.actualizarEntidad(oEntidad);
		} catch (EntidadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	public void testObtenerEntidad() {
		Entidad oEntidad = null;
		try {
			oEntidad = EntidadesManager.obtenerEntidad("00002");
			System.out.println(oEntidad.getNombreCorto());
		} catch (EntidadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	public void testBuscarEntidades() {
		List oEntidades = null;
		CriterioBusquedaEntidades oCriterio = new CriterioBusquedaEntidades();
		oCriterio.setNombreCorto("man");
		oCriterio.setNombreLargo("man");
		try {
			oEntidades = EntidadesManager.buscarEntidades(oCriterio);
			Iterator oIterador = oEntidades.iterator();
			while(oIterador.hasNext()){
				System.out.println(((Entidad)oIterador.next()).getNombreLargo());				
			}
		} catch (EntidadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(true);
	}
	
}
