package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.ParamIdMapper;

/**
 *
 * @author IECISA
 *
 */
public class ParamIdMapperTest extends TestCase {

	public void testEmptyParamId() throws Exception {
		ParamIdMapper mapper = new ParamIdMapper();
		String xml = mapper.marshall(null, null);

		Assert.assertNotNull(xml);
		Assert.assertEquals(
				xml,
				FileUtils.readFileToString(new File(getClass().getResource(
						"paramidEmpty.xml").toURI())));
	}

	public void testCompleteParamId() throws Exception {
		ParamIdMapper mapper = new ParamIdMapper();

		Map<String, Object> context = new HashMap<String, Object>();
		ConfiguracionUsuarioVO cu = new ConfiguracionUsuarioVO();
		cu.setIdEntidad("23");
		cu.setSessionID("JJ=)!=JJOP209IL");
		context.put("configuracionUsuario", cu);
		Entity entity = new Entity();
		entity.setId("1");
		String xml = mapper.marshall(entity, context);

		Assert.assertNotNull(xml);
		Assert.assertEquals(
				xml,
				FileUtils.readFileToString(new File(getClass().getResource(
						"paramidComplete.xml").toURI())));
	}

}
