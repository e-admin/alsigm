package es.ieci.tecdoc.fwktd.core.util.velocity;

import org.apache.velocity.VelocityContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;
import es.ieci.tecdoc.fwktd.util.velocity.VelocityException;

@RunWith(JUnit4.class)
public class VelocityEngineTest {

	@Test(expected = VelocityException.class)
	public void testTemplateNotFound() {
		VelocityEngine engine = new VelocityEngine();
		engine.merge("foo", new VelocityContext());
	}

	@Test
	public void testSimpleMergeWithoutContext() {
		VelocityEngine engine = new VelocityEngine();
		String result = engine.merge(
				"es/ieci/tecdoc/fwktd/core/util/velocity/foo.vm",
				new VelocityContext());

		Assert.assertNotNull(result);
		Assert.assertEquals("Hola mundo!!", result);
	}

	@Test
	public void testSimpleMergeWithContext() {
		VelocityEngine engine = new VelocityEngine();

		VelocityContext ctx = new VelocityContext();
		ctx.put("saludo", "Hola mundo!!");

		String result = engine.merge(
				"es/ieci/tecdoc/fwktd/core/util/velocity/greeting.vm", ctx);

		Assert.assertNotNull(result);
		Assert.assertEquals("Hola mundo!!", result);
	}
}
