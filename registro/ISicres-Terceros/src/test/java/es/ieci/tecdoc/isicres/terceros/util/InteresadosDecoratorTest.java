package es.ieci.tecdoc.isicres.terceros.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 *
 * @author IECISA
 *
 */
public class InteresadosDecoratorTest extends TestCase {

	public void testCadenaAListaInteresados() throws Exception {
		String cadena = "1#Apellido1 Nombre#1#Direccion#1#1#Razon Social#1#Direccion#1";

		InteresadosDecorator decorator = new InteresadosDecorator();
		List<InteresadoVO> interesados = decorator.string2interesados(cadena);

		Assert.assertNotNull(interesados);
		Assert.assertEquals(1, interesados.size());
		InteresadoVO interesado = interesados.get(0);
		Assert.assertEquals("1", interesado.getTercero().getId());
		Assert.assertEquals("Apellido1 Nombre", interesado.getTercero()
				.getDescripcion());
		Assert.assertEquals("1", interesado.getDireccionNotificacion().getId());
		Assert.assertEquals("Direccion", interesado.getDireccionNotificacion()
				.getDireccion());
		Assert.assertEquals(DireccionType.TELEMATICA, interesado
				.getDireccionNotificacion().getTipo());
		Assert.assertEquals("1", interesado.getRepresentante()
				.getRepresentante().getId());
		Assert.assertEquals("Razon Social", interesado.getRepresentante()
				.getRepresentante().getDescripcion());
		Assert.assertEquals("1", interesado.getRepresentante()
				.getDireccionNotificacion().getId());
		Assert.assertEquals("Direccion", interesado.getRepresentante()
				.getDireccionNotificacion().getDireccion());
		Assert.assertEquals(DireccionType.TELEMATICA, interesado
				.getRepresentante().getDireccionNotificacion().getTipo());
	}

	public void testListaInteresados2String() throws Exception {
		List<InteresadoVO> interesados = new ArrayList<InteresadoVO>();

		InteresadoVO interesado = new InteresadoVO();

		interesados.add(interesado);
		interesados.add(new InteresadoVO());

		InteresadosDecorator decorator = new InteresadosDecorator();
		String result = decorator.interesados2string(interesados);

		Assert.assertFalse(StringUtils.isEmpty(result));
	}

	public void testInteresadoNoValidado2String() throws Exception {
		List<InteresadoVO> interesados = new ArrayList<InteresadoVO>();

		InteresadoVO interesado = new InteresadoVO();
		interesado.setNombre("No Validado");
		BaseTerceroVO tercero = new BaseTerceroVO();
		tercero.setId("0");
		interesado.setTercero(tercero);
		interesados.add(interesado);

		InteresadosDecorator decorator = new InteresadosDecorator();
		String result = decorator.interesados2string(interesados);

		Assert.assertEquals("0#No Validado########", result);

	}

}
